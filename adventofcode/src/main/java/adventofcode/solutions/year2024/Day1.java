package adventofcode.solutions.year2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;

@ChallengeDetails(year = 2024, day = 1)
public class Day1 extends StringChallenge {

	public List<Integer> leftSide;
	
	public List<Integer> rightSide;
	
	public Day1() {
		leftSide = new ArrayList<>();
		rightSide = new ArrayList<>();
	}
	
	@ChallengeSolution
	public String processA() {
		Collections.sort(leftSide);
		Collections.sort(rightSide);
		int sum = 0;
		for (int i = 0; i<leftSide.size(); i++) {
			sum += Math.abs(rightSide.get(i) - leftSide.get(i));
		}
		return Integer.toString(sum);
	}

	@ChallengeSolution
	public int processAInt() {
		Collections.sort(leftSide);
		Collections.sort(rightSide);
		int sum = 0;
		for (int i = 0; i<leftSide.size(); i++) {
			sum += Math.abs(rightSide.get(i) - leftSide.get(i));
		}
		return sum;
	}
	
	@ChallengeSolution
	public String processB() {
		int similarity = 0;
		Map<Integer, Long> count = rightSide.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		for (int i = 0; i < leftSide.size(); i++) {
			similarity += leftSide.get(i) * (count.containsKey(leftSide.get(i)) ? count.get(leftSide.get(i)) : 0);
		}
		return Integer.toString(similarity);
	}
	
	@Override
	public String parse(String entry) {
		String[] split = entry.split("   ");
		leftSide.add(Integer.parseInt(split[0]));
		rightSide.add(Integer.parseInt(split[1]));
		return "";
	}
	
}
