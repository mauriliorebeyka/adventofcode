package adventofcode.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.config.ExecutorBeanDefinitionParser;
import org.springframework.util.StopWatch;

import adventofcode.web.dto.ChallengeDTO;
import adventofcode.web.dto.ChallengeSolutionDTO;

public class ChallengeExecutorProcessor {

	private static final Logger LOG = LogManager.getLogger();

	private AbstractChallenge<?> challenge;

	private long timeout;

	private boolean parallel;

	public ChallengeExecutorProcessor(AbstractChallenge<?> s, long timeout, boolean parallel) {
		this.challenge = s;
		this.timeout = timeout;
		this.parallel = parallel;
	}

	private RunnableSolution callChallenge(Method m) {
		return new RunnableSolution(challenge, m);
	}

	public ChallengeDTO processAllChallenges() {
		List<RunnableSolution> callables = Stream.of(challenge.getClass().getDeclaredMethods())
				.filter(m -> m.getAnnotation(ChallengeSolution.class) != null).map(this::callChallenge)
				.collect(Collectors.toList());
		ChallengeDTO challenge = new ChallengeDTO();
		if (parallel) {
			challenge.setSolutions(submitParallel(callables));
		} else {
			challenge.setSolutions(submitSequential(callables));
		}
		return challenge;
	}

	private List<ChallengeSolutionDTO> submitParallel(List<RunnableSolution> callables) {
		LOG.info("Solving all challenges in parallel");
		ExecutorService executor = Executors.newFixedThreadPool(callables.size());
		callables.stream().map(executor::submit).collect(Collectors.toList());
		executor.shutdown();
		try {
			executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			return callables.stream().map(RunnableSolution::getChallengeSolutionDTO).collect(Collectors.toList());
		} catch (InterruptedException e) {
			LOG.info("Thread interruped: {}",callables);
		}
		return Collections.emptyList();
	}

	private List<ChallengeSolutionDTO> submitSequential(List<RunnableSolution> callables) {
		LOG.info("Solving all challenges sequentially");
		List<ChallengeSolutionDTO> challenges = new ArrayList<>();
		for (RunnableSolution callable : callables) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(callable);
			executor.shutdown();
			try {
				executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				LOG.info("Thread interruped: {}",callable);
			}
			challenges.add(callable.getChallengeSolutionDTO());
		}
		return challenges;
	}

	private class RunnableSolution implements Runnable {
		private ChallengeSolutionDTO challengeSolutionDTO;

		private AbstractChallenge<?> abstractChallenge;

		private Method method;

		private boolean finished;

		public RunnableSolution(AbstractChallenge<?> abstractChallenge, Method method) {
			this.abstractChallenge = abstractChallenge;
			this.method = method;
			this.challengeSolutionDTO = new ChallengeSolutionDTO();
			challengeSolutionDTO.setName(method.getName());
			this.finished = false;
		}

		@Override
		public void run() {
			StopWatch stopwatch = new StopWatch();
			stopwatch.start();
			String answer;
			try {
				answer = (String) method.invoke(abstractChallenge);
				challengeSolutionDTO.setValue(answer);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOG.error("Error while calling challenge solution", e);
			}
			stopwatch.stop();
			LOG.info("Finishing {}",this);
			finished = true;
			challengeSolutionDTO.setRunTime(stopwatch.getLastTaskTimeMillis());
		}

		public ChallengeSolutionDTO getChallengeSolutionDTO() {
			LOG.info("Retrieving results for {}",this);
			if (!finished) {
				challengeSolutionDTO.setRunTime(timeout);
				challengeSolutionDTO.setValue("TIMEOUT");
			}
			return challengeSolutionDTO;
		}
		
		public String toString() {
			return "Year: %s Day: %s method: %s".formatted(abstractChallenge.getYear(), abstractChallenge.getDay(), method.getName());
		}
	}
}
