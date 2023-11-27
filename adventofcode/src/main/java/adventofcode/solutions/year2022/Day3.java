package adventofcode.solutions.year2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.utils.AbstractChallenge;
import adventofcode.utils.GroupCollector;
import adventofcode.utils.ChallengeDetails;

@ChallengeDetails(year = 2022, day = 3)
public class Day3 extends AbstractChallenge<String> {

	@Override
	public String processA() {
		return Integer.toString(input.stream().mapToInt(this::getPriority).sum());
	}

	@Override
	public String processB() {
		List<List<String>> groups = input.stream().collect(new GroupCollector<String, List<String>>(3, ArrayList::new));
		return Integer.toString(groups.stream().mapToInt(this::getBadge).sum());
	}

	public int getPriority(String rucksack) {
		String part1 = rucksack.substring(0, rucksack.length()/2);
		String part2 = rucksack.substring(rucksack.length()/2);
		int ca = part1.chars().filter(c -> part2.indexOf(c) >= 0).findFirst().getAsInt();
		return getPriority(ca);
	}
	
	public int getBadge(List<String> rucksacks) {
		return getPriority(rucksacks.get(0).chars().filter(c -> rucksacks.stream().filter(c1 -> c1.indexOf(c) >= 0).count() == 3).findFirst().getAsInt());
	}
	
	public int getPriority(int ca) {
		return Character.isUpperCase(ca) ? ca - 38 : ca - 96;
	}
	
	@Test
	public void test() {
		String a = """
				vJrwpWtwJgWrhcsFMMfFFhFp
				jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
				PmmdzqPrVvPwwTWBwg
				wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
				ttgJtRGJQctTZtZT
				CrZsJsPPZsGzwwsLwLmpwMDw
				""";
		input = Stream.of(a.split("\n")).collect(Collectors.toList());
//		System.out.println(processA());
		System.out.println(processB());
	}
}
