package adventofcode.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adventofcode.challenge.ChallengeFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolutionFactoryTest {

	@Autowired
	private ChallengeFactory solutionFactory;
	
	@Test
	public void test() {
		solutionFactory.getLatestSolution();
	}
}
