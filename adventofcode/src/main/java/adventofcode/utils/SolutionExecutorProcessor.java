package adventofcode.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.util.StopWatch;

import adventofcode.web.dto.SolutionDTO;

public class SolutionExecutorProcessor implements Callable<SolutionDTO> {

	private AbstractSolution<?> solution;

	private boolean processB;

	public SolutionExecutorProcessor(AbstractSolution<?> s) {
		this.solution = s;
	}

	@Override
	public SolutionDTO call() throws Exception {
		SolutionDTO sol = new SolutionDTO();
		StopWatch watch = new StopWatch();
		watch.start();
		if (processB) {
			sol.setSolutionB(solution.processB());
		} else {
			sol.setSolutionA(solution.processA());
		}
		watch.stop();
		if (processB) {
			sol.setProcessingB(watch.getLastTaskTimeMillis());
		} else {
			sol.setProcessingA(watch.getLastTaskTimeMillis());
		}
		return sol;
	}

	public SolutionDTO call(boolean processB) {
		this.processB = processB;
		SolutionDTO sol = new SolutionDTO();
		Future<SolutionDTO> a = Executors.newSingleThreadExecutor().submit(this);
		try {
			sol = a.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException e) {
			sol.setSolutionA("Error:" + e);
		} catch (TimeoutException e) {
			sol.setSolutionA("TIMEOUT");
		}
		return sol;
	}
	
	public SolutionDTO processBothSolutions() {
		SolutionDTO a = call(false);
		SolutionDTO b = call(true);
		a.setSolutionB(b.getSolutionB());
		a.setProcessingB(b.getProcessingB());
		return a;
	}
}
