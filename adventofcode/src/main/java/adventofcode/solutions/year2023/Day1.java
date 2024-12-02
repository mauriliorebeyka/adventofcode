package adventofcode.solutions.year2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;
import adventofcode.utils.StringChallenge;

@ChallengeDetails(year = 2023, day = 1)
public class Day1 extends StringChallenge {

	@ChallengeSolution
	public String processA() {
		return Integer.toString(input.stream().mapToInt(this::getCalibration).sum());
	}

	@ChallengeSolution
	public String processB() {
		return Integer.toString(input.stream().mapToInt(this::getRealCalibration).sum());
	}
	
	private int getCalibration(String string) {
		List<String> numbers = string.chars().filter(Character::isDigit).mapToObj(c -> Integer.toString(c - 48))
				.collect(Collectors.toList());
		return Integer.parseInt(numbers.get(0) + numbers.get(numbers.size() - 1));
	}

	
	private int getRealCalibration(String string) {
		List<String> numbers = new ArrayList<>();
		for (int i=0; i<string.length(); i++) {
			String digit = readDigit(string, i);
			if (!digit.isBlank()) {
				numbers.add(digit);
			}
		}
		return Integer.parseInt(numbers.get(0) + numbers.get(numbers.size() - 1));
	}
	
	private String readDigit(String string, int start) {
		if (Character.isDigit(string.charAt(start))) {
			return Character.toString(string.charAt(start));
		}
		if (string.substring(start).startsWith("one")) {
			return "1";
		}
		if (string.substring(start).startsWith("two")) {
			return "2";
		}
		if (string.substring(start).startsWith("three")) {
			return "3";
		}
		if (string.substring(start).startsWith("four")) {
			return "4";
		}
		if (string.substring(start).startsWith("five")) {
			return "5";
		}
		if (string.substring(start).startsWith("six")) {
			return "6";
		}
		if (string.substring(start).startsWith("seven")) {
			return "7";
		}
		if (string.substring(start).startsWith("eight")) {
			return "8";
		}
		if (string.substring(start).startsWith("nine")) {
			return "9";
		}
		return "";
	}

	@Test
	public void test() {
		this.setTestInput("""
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
								""");
		System.out.println(processB());
	}
}
