package adventofcode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import adventofcode.utils.ChallengeLocalRunner;

@SpringBootApplication
public class SolutionWebApplication {

	private static final Logger LOG = LogManager.getLogger(SolutionWebApplication.class);

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(SolutionWebApplication.class, args);
		boolean local = false;
		if (local) {
			try {
				ChallengeLocalRunner localRunner = appContext.getBean(ChallengeLocalRunner.class);
				String output = localRunner.runSolution(2020, 1);
				System.out.println("=======================");
				System.out.println(output);
				System.out.println("=======================");
				SpringApplication.exit(appContext, () -> 0);
			} catch (Exception e) {
				LOG.error("Failure to run solution", e);
			}
		}
	}
}
