package adventofcode.solutions.year2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;

@ChallengeDetails(year = 2022, day = 5)
public class Day5 extends StringChallenge {

	@ChallengeSolution
	public String processA() {
		List<Deque<Character>> stacks = getStacks();
		input.stream().filter(s -> s.startsWith("move")).forEach(s -> this.move(stacks, s, false));
		return stacks.stream().map(s -> Character.toString(s.peekLast())).reduce("", String::concat);
	}

	@ChallengeSolution
	public String processB() {
		List<Deque<Character>> stacks = getStacks();
		input.stream().filter(s -> s.startsWith("move")).forEach(s -> this.move(stacks, s, true));
		return stacks.stream().map(s -> Character.toString(s.peekLast())).reduce("", String::concat);
	}

	public void move(List<Deque<Character>> stacks, String data, boolean retainOrder) {
		String[] parsed = data.split(" ");
		int amount = Integer.parseInt(parsed[1]);
		Deque<Character> origin = stacks.get(Integer.parseInt(parsed[3]) - 1);
		Deque<Character> dest = stacks.get(Integer.parseInt(parsed[5]) - 1);
		if (retainOrder) {
			Deque<Character> temp = new ArrayDeque<Character>();
			for (int i = 0; i < amount; i++) {
				temp.offerLast(origin.pollLast());
			}
			while (!temp.isEmpty()) {
				dest.offerLast(temp.pollLast());
			}
		} else {
			for (int i = 0; i < amount; i++) {
				dest.offerLast(origin.pollLast());
			}
		}
	}

	public List<Deque<Character>> getStacks() {
		int depth = input.indexOf(input.stream().filter(String::isEmpty).findFirst().get()) - 2;
		int numStacks = Integer.parseInt(input.get(depth + 1).split(" ")[input.get(depth + 1).split(" ").length - 1]);
		List<Deque<Character>> stacks = new ArrayList<>();
		for (int i = 0; i < numStacks; i++) {
			stacks.add(new ArrayDeque<>());
		}
		for (int i = 0; i <= depth; i++) {
			String line = input.get(i);
			for (int j = 0; j < numStacks; j++) {
				char crate = line.length() > (1 + (j * 4)) ? line.charAt(1 + (j * 4)) : ' ';
				if (crate != ' ') {
					stacks.get(j).offerFirst(crate);
				}
			}
		}
		return stacks;
	}

	@Test
	public void test() {
		String raw = """
				    [D]
				[N] [C]
				[Z] [M] [P]
				1   2   3

				move 1 from 2 to 1
				move 3 from 1 to 3
				move 2 from 2 to 1
				move 1 from 1 to 2
				""";
		input = Stream.of(raw.split("\n")).collect(Collectors.toList());
		System.out.println(processA());
		System.out.println(processB());
	}
}
