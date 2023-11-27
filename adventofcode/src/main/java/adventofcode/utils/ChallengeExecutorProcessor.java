package adventofcode.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;

import adventofcode.web.dto.ChallengeDTO;
import adventofcode.web.dto.ChallengeSolutionDTO;

public class ChallengeExecutorProcessor {

	private static final Logger LOG = LogManager.getLogger();

	private AbstractChallenge<?> challenge;

	public ChallengeExecutorProcessor(AbstractChallenge<?> s) {
		this.challenge = s;
	}

	private ChallengeSolutionDTO callChallenge(Method m) {
		ChallengeSolutionDTO challengeSolution = new ChallengeSolutionDTO();
		Callable<String> exec = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return (String) m.invoke(challenge);
			}
		};
		Future<String> future = Executors.newSingleThreadExecutor().submit(exec);
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		try {
			String answer = future.get(10, TimeUnit.SECONDS);
			challengeSolution.setValue(answer);
		} catch (InterruptedException | ExecutionException e) {
			challengeSolution.setValue("Error: "+e.getMessage());
			LOG.warn("Error on calling challenge solution:",e);
		} catch (TimeoutException e) {
			challengeSolution.setValue("Timeout");
		}
		stopwatch.stop();
		challengeSolution.setRunTime(stopwatch.getLastTaskTimeMillis());
		challengeSolution.setName(m.getName());
		return challengeSolution;
	}

	public ChallengeDTO processAllChallenges() {
		List<ChallengeSolutionDTO> challengeSolutions = Stream.of(challenge.getClass().getDeclaredMethods())
				.filter(m -> m.getAnnotation(ChallengeSolution.class) != null).map(this::callChallenge)
				.collect(Collectors.toList());
		ChallengeDTO challenge = new ChallengeDTO();
		challenge.setSolutions(challengeSolutions);
		return challenge;
	}
}
