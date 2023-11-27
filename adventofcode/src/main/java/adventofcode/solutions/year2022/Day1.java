package adventofcode.solutions.year2022;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import org.junit.jupiter.api.Test;

import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;
import adventofcode.utils.GroupCollector;
import adventofcode.utils.StringChallenge;

@ChallengeDetails(year = 2022, day = 1)
public class Day1 extends StringChallenge {

	@Override
	@ChallengeSolution
	public String processA() {
		Set<Integer> calories = getCaloriesList();
		return Integer.toString(calories.stream().mapToInt(i -> i).max().getAsInt());
	}

	@Override
	@ChallengeSolution
	public String processB() {
		Set<Integer> calores = getCaloriesList();
		int top3 = calores.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(Integer::sum).get().intValue();
		return Integer.toString(top3);
	}

	private Set<Integer> getCaloriesList() {
		int sum = 0;
		Set<Integer> calories = new HashSet<>();
		for (String line : input) {
			if (line.isBlank()) {
				calories.add(sum);
				sum = 0;
			} else {
				sum += Integer.parseInt(line);
			}
		}
		calories.add(sum);
		return calories;
	}

	@Test
	public void testCaloriesList() {
		input = Arrays.asList("1000", "2000", "3000", "", "4000", "", "5000", "6000", "", "7000", "8000", "9000", "",
				"10000");
		Set<Integer> expected = new HashSet<>();
		expected.add(6000);
		expected.add(4000);
		expected.add(11000);
		expected.add(24000);
		expected.add(10000);
		assertEquals(expected, getCaloriesList());

		input.stream().collect(Collector.of(() -> {
			Stack<Set<String>> d = new Stack<Set<String>>();
			d.push(new HashSet<>());
			return d;
		}, (set, next) -> {
			if (!next.isEmpty()) {
				set.peek().add(next);
			} else {
				set.push(new HashSet<>());
			}
		}, (left, right) -> {
			left.addAll(right);
			return left;
		}, s -> {
			return s;
		}, Characteristics.IDENTITY_FINISH));

		input.stream().collect(new GroupCollector<String, Integer>(r -> !r.isEmpty(),
				r -> r.stream().mapToInt(Integer::parseInt).sum())).forEach(System.out::println);
		input.stream().collect(new GroupCollector<String, List<String>>(r -> true, ArrayList::new)).forEach(System.out::println);
	}
}
