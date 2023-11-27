package adventofcode.solutions.year2022;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.utils.AbstractChallenge;
import adventofcode.utils.ChallengeDetails;

@ChallengeDetails(year = 2022, day = 8)
public class Day8 extends AbstractChallenge<List<Integer>> {

	@Override
	public String processA() {
		int visible = 0;
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(y).size(); x++) {
				if (visible(x, y, input.get(y).get(x))) {
					visible++;
				}
			}
		}
		return Integer.toString(visible);
	}

	@Override
	public String processB() {
		int scenic = 0;
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(y).size(); x++) {
				int thisTree = scenic(x, y, input.get(y).get(x));
				scenic = thisTree > scenic ? thisTree : scenic;
			}
		}
		return Integer.toString(scenic);
	}

	@Override
	public List<Integer> parse(String entry) {
		return entry.chars().map(i -> Character.getNumericValue(i)).boxed().collect(Collectors.toList());
	}

	public boolean visible(int x, int y, int height) {
		if (x == 0 || y == 0 || x == input.get(y).size() - 1 || y == input.size() - 1) {
			return true;
		}
		if (height == 0) {
			return false;
		}
		return visibleLeft(x, y, height) || visibleRight(x, y, height) || visibleUp(x, y, height)
				|| visibleDown(x, y, height);
//		return scenicLeft(x, y, height) < x || scenicRight(x, y, height) > input.get(y).size() - x - 1
//				|| scenicUp(x, y, height) < y || scenicDown(x, y, height) > input.size() - y - 1;
	}

	public int scenic(int x, int y, int height) {
		if (x == 0 || y == 0 || x == input.get(y).size() - 1 || y == input.size() - 1) {
			return 0;
		}
		return scenicLeft(x, y, height) * scenicRight(x, y, height) * scenicUp(x, y, height) * scenicDown(x, y, height);
	}

	public boolean visibleLeft(int x, int y, int height) {
		for (int i = x - 1; i >= 0; i--) {
			if (input.get(y).get(i) >= height) {
				return false;
			}
		}
		return true;
	}

	public int scenicLeft(int x, int y, int height) {
		for (int i = x - 1; i >= 0; i--) {
			if (input.get(y).get(i) >= height) {
				return x - i;
			}
		}
		return x;
	}

	public boolean visibleRight(int x, int y, int height) {
		for (int i = x + 1; i < input.get(y).size(); i++) {
			if (input.get(y).get(i) >= height) {
				return false;
			}
		}
		return true;
	}

	public int scenicRight(int x, int y, int height) {
		for (int i = x + 1; i < input.get(y).size(); i++) {
			if (input.get(y).get(i) >= height) {
				return i - x;
			}
		}
		return input.get(y).size() - x - 1;
	}

	public boolean visibleUp(int x, int y, int height) {
		for (int i = y - 1; i >= 0; i--) {
			if (input.get(i).get(x) >= height) {
				return false;
			}
		}
		return true;
	}

	public int scenicUp(int x, int y, int height) {
		for (int i = y - 1; i >= 0; i--) {
			if (input.get(i).get(x) >= height) {
				return y - i;
			}
		}
		return y;
	}

	public boolean visibleDown(int x, int y, int height) {
		for (int i = y + 1; i < input.size(); i++) {
			if (input.get(i).get(x) >= height) {
				return false;
			}
		}
		return true;
	}

	public int scenicDown(int x, int y, int height) {
		for (int i = y + 1; i < input.size(); i++) {
			if (input.get(i).get(x) >= height) {
				return i - y;
			}
		}
		return input.size() - y - 1;
	}

	@Test
	public void test() {
		String raw = """
				30373
				25512
				65332
				33549
				35390
								""";
		input = Stream.of(raw.split("\n")).map(this::parse).collect(Collectors.toList());
		System.out.println(processA());
		System.out.println(processB());
	}
}
