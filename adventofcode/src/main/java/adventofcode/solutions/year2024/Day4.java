package adventofcode.solutions.year2024;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;
import adventofcode.utils.Matrix;
import adventofcode.utils.Matrix.Direction;

@ChallengeDetails(year = 2024, day = 4)
public class Day4 extends StringChallenge {

	@ChallengeSolution
	public int test() {
		setTestInput("""
				MMMSXXMASM
				MSAMXMSMSA
				AMXSXMAAMM
				MSAMASMSMX
				XMASAMXAMM
				XXAMMXXAMA
				SMSMSASXSS
				SAXAMASAAA
				MAMMMXMMMM
				MXMXAXMASX
								""");
		Matrix matrix = new Matrix(testInput);
		String foundCharacters = "";
		int foundWords = 0;
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if (matrix.getValue(x, y) == 'X') {
					for (Direction direction : Matrix.Direction.values()) {
						foundCharacters = "";
						matrix.setCoordinates(x, y);
						do {
							foundCharacters += matrix.getCurrent();
						} while (matrix.move(direction) && foundCharacters.length() < 4);
						if (foundCharacters.equals("XMAS")) {
							System.out.println(
									"Found XMAS starting at %s,%s, for %s direction".formatted(x, y, direction));
							foundWords++;
						}
					}
				}
			}
		}
		return foundWords;
	}

	@ChallengeSolution
	public int part1() {
		Matrix matrix = new Matrix(input);
		String foundCharacters = "";
		int foundWords = 0;
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if (matrix.getValue(x, y) == 'X') {
					for (Direction direction : Matrix.Direction.values()) {
						foundCharacters = "";
						matrix.setCoordinates(x, y);
						do {
							foundCharacters += matrix.getCurrent();
						} while (matrix.move(direction) && foundCharacters.length() < 4);
						if (foundCharacters.equals("XMAS")) {
							System.out.println(
									"Found XMAS starting at %s,%s, for %s direction".formatted(x, y, direction));
							foundWords++;
						}
					}
				}
			}
		}
		return foundWords;
	}

	@ChallengeSolution
	public int part2() {
		Matrix matrix = new Matrix(input);
		int foundWords = 0;
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if (matrix.getValue(x, y) == 'A') {
					String diagonal1 = new String(new char[] { matrix.getValue(x - 1, y - 1), matrix.getValue(x, y),
							matrix.getValue(x + 1, y + 1) });
					String diagonal2 = new String(new char[] { matrix.getValue(x - 1, y + 1), matrix.getValue(x, y),
							matrix.getValue(x + 1, y - 1) });
					if ((diagonal1.equals("SAM") || diagonal1.equals("MAS"))
							&& ((diagonal2.equals("SAM") || diagonal2.equals("MAS")))) {
						foundWords++;
					}
				}
			}
		}
		return foundWords;
	}
}
