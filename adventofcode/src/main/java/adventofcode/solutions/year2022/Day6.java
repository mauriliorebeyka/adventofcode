package adventofcode.solutions.year2022;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;
import adventofcode.utils.StringChallenge;

@ChallengeDetails(year = 2022, day = 6)
public class Day6 extends StringChallenge {

	@Override
	@ChallengeSolution
	public String processA() {
		char[] chars = input.get(0).toCharArray();
		Deque<Character> queue = new ArrayDeque<>();
		int i=0;
		while (getUnique(queue) < 4) {
			rotate(queue, 4, chars[i++]);
		}
		return Integer.toString(i);
	}

	@Override
	@ChallengeSolution
	public String processB() {
		char[] chars = input.get(0).toCharArray();
		Deque<Character> queue = new ArrayDeque<>();
		int i=0;
		while (getUnique(queue) < 14) {
			rotate(queue, 14, chars[i++]);
		}
		return Integer.toString(i);
	}
	
	public void rotate(Deque<Character> queue, int size, Character element) {
		if (queue.size() == size) { 
			queue.pollFirst();
		}
		queue.offerLast(element);
	}
	
	public int getUnique(Deque<Character> queue) {
		return queue.stream().collect(Collectors.toSet()).size();
	}

}
