package adventofcode.solutions.year2022;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.challenge.AbstractChallenge;
import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.solutions.year2022.Day4.Section;

@ChallengeDetails(year = 2022, day = 4)
public class Day4 extends AbstractChallenge<List<Section>> {

	@ChallengeSolution
	public String processA() {
		return Long.toString(input.stream().filter(s -> s.get(0).inside(s.get(1))).count());
	}

	@ChallengeSolution
	public String processB() {
		return Long.toString(input.stream().filter(s -> s.get(0).overlap(s.get(1))).count());
	}

	@Override
	public List<Section> parse(String input) {
		return Stream.of(input.split(",")).map(a -> new Section(a)).sorted().collect(Collectors.toList());
	}

	@Test
	public void test() {
		String a = """
				2-4,6-8
				2-3,4-5
				5-7,7-9
				2-8,3-7
				6-6,4-6
				2-6,4-8
				""";
		input = Stream.of(a.split("\n")).map(this::parse).collect(Collectors.toList());
	}

	final class Section implements Comparable<Section> {
		int start;
		int end;

		Section(String entry) {
			String[] data = entry.split("-");
			this.start = Integer.parseInt(data[0]);
			this.end = Integer.parseInt(data[1]);
		}

		@Override
		public int compareTo(Section o) {
			return getDistance() - o.getDistance();
		}

		public int getDistance() {
			return end - start;
		}

		public boolean inside(Section other) {
			return (this.start >= other.start) && (this.end <= other.end);
		}
		
		public boolean overlap(Section other) {
			return (this.start >= other.start && this.start <= other.end)
					|| (this.end >= other.start && this.end <= other.end);
		}
	}
}
