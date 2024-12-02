package adventofcode.solutions.year2024;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import adventofcode.utils.AbstractChallenge;
import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;

@ChallengeDetails(year = 2024, day = 2)
public class Day2 extends AbstractChallenge<List<Integer>> {

	@Override
	public List<Integer> parse(String entry) {
		return Stream.of(entry.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
	}

	@ChallengeSolution
	public long processA() {
		return input.stream().filter(this::isReportSafe).count();
	}

	@ChallengeSolution
	public long processB() {
		return input.stream().filter(r -> isReportSafe(r, 1)).count();
	}

	@ChallengeSolution
	public long test() {
		setTestInput("""
48 46 47 49 51 54 56
1 1 2 3 4 5
1 2 3 4 5 5
5 1 2 3 4 5
1 4 3 2 1
1 6 7 8 9
1 2 3 4 3
9 8 7 6 7
7 10 8 10 11
29 28 27 25 26 25 22 20
				""");
		return testInput.stream().filter(r -> isReportSafe(r, 1)).count();
	}

	private boolean isReportSafe(List<Integer> levels) {
		return isReportSafe(levels, 0);
	}

	private boolean isReportSafe(List<Integer> levels, int toleratedUnsafeLevels) {
		int decreasing = 1;
		if (levels.get(0) < levels.get(1)) {
			decreasing = -1;
		}

		System.out.println("List %s is being tested".formatted(levels));
		for (int i = 0; i < levels.size() - 1; i++) {
			int diff = (levels.get(i) - levels.get(i + 1)) * decreasing;
			if (diff < 1 || diff > 3) {
				if (toleratedUnsafeLevels == 0) {
					System.out.println("List %s is unsafe".formatted(levels));
					return false;
				} else {
					List<Integer> newLevels = levels.stream().collect(Collectors.toList());
					newLevels.remove(i);
					List<Integer> newLevels2 = levels.stream().collect(Collectors.toList());
					newLevels2.remove(i+1);
					List<Integer> newLevels3 = levels.subList(1, levels.size());
					return isReportSafe(newLevels, toleratedUnsafeLevels - 1) || isReportSafe(newLevels2, toleratedUnsafeLevels - 1) || isReportSafe(newLevels3, toleratedUnsafeLevels - 1);
				}
			}
		}
		System.out.println("List %s is safe".formatted(levels));
		return true;
	}
}
