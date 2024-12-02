package adventofcode.utils;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeLocalRunner {

	private static final Logger LOG = LogManager.getLogger(ChallengeLocalRunner.class);

	@Autowired
	private ChallengeFactory solutionFactory;
	
	@Autowired
	private InputReader inputReader;
	
	public String runSolution(int year, int day) throws Exception {
		AbstractChallenge<?> solution = null;
		if (year == 0 && day == 0) {
			LOG.info("Running latest solution available");
			solution = solutionFactory.getLatestSolution();
		} else {
			LOG.info("Running solution for year {} day {}", year, day);
			solution = solutionFactory.getSolution(year, day);
		}
		solution.init(inputReader);
//		if (solution.getInput() == null || solution.getInput().isEmpty()) {
//			throw new Exception("Invalid input");
//		}
//		String output = solution.processB();
//		if (Objects.isNull(output)) {
//			LOG.info("Process B not yet implemented, running Process A");
//			output = solution.processA();
//		}
//		return output;
		return "";
	}
}
