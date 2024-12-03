package adventofcode.solutions.year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;
import adventofcode.utils.StringChallenge;

@ChallengeDetails(year = 2024, day = 3)
public class Day3 extends StringChallenge {

	@ChallengeSolution
	public int processA() {
		return input.stream().map(i -> parseInstructions(i, false)).flatMap(List::stream)
				.mapToInt(this::processInstruction).sum();
	}

	@ChallengeSolution
	public int processB() {
		return parseInstructions(input.stream().reduce(String::concat).get(),true).stream()
				.mapToInt(this::processInstruction).sum();
	}
	
	@ChallengeSolution
	public int test() {
		setTestInput("""
				xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
				""");
		int result = 0;
		for (String instruction : parseInstructions(testInput.get(0), true)) {
			System.out.println("Processing %s".formatted(instruction));
			int commaPosition = instruction.indexOf(",");
			int value1 = Integer.parseInt(instruction.substring(4, commaPosition));
			int value2 = Integer.parseInt(instruction.substring(commaPosition + 1, instruction.length() - 1));
			result += value1 * value2;
		}
		return result;
	}

	private int processInstruction(String instruction) {
		int commaPosition = instruction.indexOf(",");
		int value1 = Integer.parseInt(instruction.substring(4, commaPosition));
		int value2 = Integer.parseInt(instruction.substring(commaPosition + 1, instruction.length() - 1));
		return value1 * value2;
	}

	private List<String> parseInstructions(String entry, boolean useEnablers) {
		System.out.println("entry: %s".formatted(entry));
		String baseInstruction = "mul\\(\\d{1,3},\\d{1,3}\\)";
		if (useEnablers) {
			baseInstruction += "|do\\(\\)|don't\\(\\)";
		}
		Pattern pattern = Pattern.compile(baseInstruction);
		Matcher matcher = pattern.matcher(entry);
		List<String> instructions = new ArrayList<>();
		boolean instructionsEnabled = true;
		while (matcher.find()) {
			String instruction = matcher.group();
			System.out.println(instruction);
			if (instruction.equals("do()")) {
				instructionsEnabled = true;
			}
			if (instruction.equals("don't()")) {
				instructionsEnabled = false;
			}
			if (instruction.startsWith("mul(") && instructionsEnabled) {
				instructions.add(matcher.group());
			}
		}
		return instructions;
	}
	
}
