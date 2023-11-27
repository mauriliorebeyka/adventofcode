package adventofcode.solutions.year2022;

import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;
import adventofcode.utils.StringChallenge;

@ChallengeDetails(year = 2022, day = 2)
public class Day2 extends StringChallenge {

	@Override
	@ChallengeSolution
	public String processA() {
		return Integer.toString(input.stream().map(this::computeMatch).mapToInt(Integer::intValue).sum());
	}

	@Override
	@ChallengeSolution
	public String processB() {
		return Integer.toString(input.stream().map(this::computeResult).mapToInt(Integer::intValue).sum());
	}

	public int computeMatch(String entry) {
		switch (entry) {
		case "A X":
			return 1 + 3;
		case "A Y":
			return 2 + 6;
		case "A Z":
			return 3 + 0;

		case "B X":
			return 1 + 0;
		case "B Y":
			return 2 + 3;
		case "B Z":
			return 3 + 6;

		case "C X":
			return 1 + 6;
		case "C Y":
			return 2 + 0;
		case "C Z":
			return 3 + 3;
		}

		throw new RuntimeException("Invalid entry: " + entry);
	}

	public int computeResult(String entry) {
		switch (entry) {
		case "A X":
			return 3 + 0;
		case "A Y":
			return 1 + 3;
		case "A Z":
			return 2 + 6;

		case "B X":
			return 1 + 0;
		case "B Y":
			return 2 + 3;
		case "B Z":
			return 3 + 6;

		case "C X":
			return 2 + 0;
		case "C Y":
			return 3 + 3;
		case "C Z":
			return 1 + 6;
		}
		throw new RuntimeException("Invalid entry: " + entry);

	}

}
