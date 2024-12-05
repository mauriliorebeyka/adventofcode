package adventofcode.solutions.year2022;

import org.junit.Test;

import adventofcode.challenge.AbstractChallenge;
import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.solutions.year2022.Day10.Instruction;

@ChallengeDetails(year = 2022, day = 10)
public class Day10 extends AbstractChallenge<Instruction> {

	@ChallengeSolution
	public String processA() {
		int x = 1;
		int tickCount = 1;
		int sum = 0;
		for (Instruction i : input) {
			sum += tick(++tickCount, x);
			if (i.instruction.equals("addx")) {
				x += i.amount;
				sum += tick(++tickCount, x);
			}
		}
		return Integer.toString(sum);
	}

	@ChallengeSolution
	public String processB() {
		int x = 1;
		int tickCount = 0;
		char[][] screen = new char[6][40];
		for (Instruction i : input) {
			screen[tickCount / 40][tickCount % 40] = spriteMatch(tickCount, x);
			tickCount++;
			if (i.instruction.equals("addx")) {
				screen[tickCount / 40][tickCount % 40] = spriteMatch(tickCount, x);
				x += i.amount;
				tickCount++;
			}
		}
		for (char[] c : screen) {
			System.out.println(new String(c));
		}
		return "";
	}

	public int tick(int tickCount, int x) {
		if (tickCount % 40 == 20) {
			return tickCount * x;
		}
		return 0;
		
	}
	
	public char spriteMatch(int tickCount, int x) {
		int position = tickCount % 40;
		if (position - 1 == x || position == x || position + 1 == x ) {
			return '#';
		}
		return '.';
	}
	
	@Override
	public Instruction parse(String entry) {
		return new Instruction(entry.split(" "));
	}
	
	final class Instruction {
		String instruction;
		int amount;
		
		public Instruction(String... data) {
			instruction = data[0];
			amount = data.length > 1 ? Integer.parseInt(data[1]) : 0;
		}
	}

	@Test
	public void test() {
		String raw = """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
				""";
		setTestInput(raw);
		System.out.println(processB());
	}
}

