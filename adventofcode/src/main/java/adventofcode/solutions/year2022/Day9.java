package adventofcode.solutions.year2022;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import adventofcode.solutions.year2022.Day9.Instruction;
import adventofcode.utils.AbstractChallenge;
import adventofcode.utils.ChallengeDetails;

@ChallengeDetails(year = 2022, day = 9)
public class Day9 extends AbstractChallenge<Instruction> {

	@Override
	public String processA() {
		Point head = new Point(0, 0);
		Point tail = new Point(0, 0);
		Set<Point> previousTails = new HashSet<>();
		previousTails.add(new Point(tail));
		for (Instruction i : input) {
			previousTails.addAll(move(head, tail, i));
//			System.out.println("H: (" + head.x + " " + head.y + ") T: (" + tail.x + " " + tail.y + ") " + i
//					+ previousTails.size());
//			debug(previousTails, head, tail);
		}

		return Integer.toString(previousTails.size());
	}

	@Override
	public String processB() {
		// TODO Auto-generated method stub
		return null;
	}

	private void debug(Set<Point> previousTails, Point head, Point tail) {
//		char[][] matrix = new char[165][375];
		char[][] matrix = new char[6][6];
		for (int i = matrix.length - 1; i >= 0; i--) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = '.';
			}
		}
		for (Point p : previousTails) {
			matrix[p.y][p.x] = '#';
		}
		matrix[tail.y][tail.x] = 'T';
		matrix[head.y][head.x] = 'H';
		for (int i = matrix.length - 1; i >= 0; i--) {
			System.out.println(new String(matrix[i]));
		}
		System.out.println();
	}

	public Set<Point> move(Point head, Point tail, Instruction instruction) {
		Set<Point> tailPositions = new HashSet<>();
		switch (instruction.direction) {
		case "R":
			tailPositions.addAll(moveRight(head, tail, instruction.amount));
			break;
		case "L":
			tailPositions.addAll(moveLeft(head, tail, instruction.amount));
			break;
		case "U":
			tailPositions.addAll(moveUp(head, tail, instruction.amount));
			break;
		case "D":
			tailPositions.addAll(moveDown(head, tail, instruction.amount));
			break;
		}
		return tailPositions;
	}

	public Set<Point> moveRight(Point head, Point tail, int amount) {
		Set<Point> tailPositions = new HashSet<>();
		for (int i = 0; i < amount; i++) {
			head.translate(1, 0);
			if (head.distance(tail) > 1.5) {
				tail.move(head.x - 1, head.y);
				tailPositions.add(new Point(tail));
			}
//			debug(tailPositions, head, tail);
		}
		return tailPositions;
	}

	public Set<Point> moveLeft(Point head, Point tail, int amount) {
		Set<Point> tailPositions = new HashSet<>();
		for (int i = 0; i < amount; i++) {
			head.translate(-1, 0);
			if (head.distance(tail) > 1.5) {
				tail.move(head.x + 1, head.y);
				tailPositions.add(new Point(tail));
			}
//			debug(tailPositions, head, tail);
		}
		return tailPositions;
	}

	public Set<Point> moveUp(Point head, Point tail, int amount) {
		Set<Point> tailPositions = new HashSet<>();
		for (int i = 0; i < amount; i++) {
			head.translate(0, 1);
			if (head.distance(tail) > 1.5) {
				tail.move(head.x, head.y - 1);
				tailPositions.add(new Point(tail));
			}
//			debug(tailPositions, head, tail);
		}
		return tailPositions;
	}

	public Set<Point> moveDown(Point head, Point tail, int amount) {
		Set<Point> tailPositions = new HashSet<>();
		for (int i = 0; i < amount; i++) {
			head.translate(0, -1);
			if (head.distance(tail) > 1.5) {
				tail.move(head.x, head.y + 1);
				tailPositions.add(new Point(tail));
			}
//			debug(tailPositions, head, tail);
		}
		return tailPositions;
	}

	@Override
	public Instruction parse(String entry) {
		return new Instruction(entry.split(" "));
	}

	final class Instruction {

		private String direction;
		private int amount;

		public Instruction(String... data) {
			direction = data[0];
			amount = Integer.parseInt(data[1]);
		}

		public String toString() {
			return direction + " " + Integer.toString(amount) + " - ";
		}
	}

	@Test
	public void test() {
		String raw = """
				R 4
				U 4
				L 3
				D 1
				R 4
				D 1
				L 5
				R 2
								""";
//		String raw = """
//R 4
//L 4
//R 5
//								""";
		setInput(raw);
		System.out.println(processA());
	}
}
