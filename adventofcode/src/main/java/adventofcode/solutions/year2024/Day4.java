package adventofcode.solutions.year2024;

import java.util.List;
import java.util.stream.Collectors;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;
import adventofcode.utils.Direction;
import adventofcode.utils.Matrix;

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
		Matrix<Character> matrix = Matrix.charMatrix(testInput);
		String foundCharacters = "";
		int foundWords = 0;
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if (matrix.getValue(x, y) == 'X') {
					for (Direction direction : Direction.values()) {
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
		Matrix<Character> matrix = Matrix.charMatrix(input);
		int foundWords = 0;
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if (matrix.getValue(x, y) == 'X') {
					for (Direction direction : Direction.values()) {
						String word = matrix.getRange(x, y, 4, direction).stream().map(c -> c.toString()).collect(Collectors.joining());
						if (word.equals("XMAS")) {
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
		Matrix<Character> matrix = Matrix.charMatrix(input);
		int foundWords = 0;
		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				if (matrix.getValue(x, y) == 'A') {
					List<Character> diagonal1 = matrix.getRange(x-1, y-1, 3, Direction.LOWER_RIGHT);
					List<Character> diagonal2 = matrix.getRange(x-1, y+1, 3, Direction.UPPER_RIGHT);
					List<Character> SM = List.of('S','M');
					if (diagonal1.containsAll(SM) && diagonal2.containsAll(SM)) {
						foundWords++;
					}
				}
			}
		}
		return foundWords;
	}
}
