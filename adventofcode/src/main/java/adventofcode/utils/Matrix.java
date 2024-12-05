package adventofcode.utils;

import java.awt.Point;
import java.util.List;

public class Matrix {

	private char[][] matrix;

	private int x;

	private int y;

	public static enum Direction {
		UP(0, -1), UPPER_RIGHT(1, -1), RIGHT(1, 0), LOWER_RIGHT(1, 1), DOWN(0, 1), LOWER_LEFT(-1, 1), LEFT(-1, 0),
		UPPER_LEFT(-1, -1);

		private int deltaX;

		private int deltaY;

		private Direction(int deltaX, int deltaY) {
			this.deltaX = deltaX;
			this.deltaY = deltaY;
		}

		public int getDeltaX() {
			return deltaX;
		}

		public int getDeltaY() {
			return deltaY;
		}
	};

	public Matrix(List<String> input) {
		matrix = new char[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			matrix[i] = input.get(i).toCharArray();
		}
		x = 0;
		y = 0;
	}

	public int getHeight() {
		return matrix.length;
	}

	public int getWidth() {
		return matrix[y].length;
	}

	public boolean setCoordinates(Point point) {
		return setCoordinates(point.x, point.y);
	}

	public boolean setCoordinates(int x, int y) {
		if (setX(x)) {
			return setY(y);
		}
		return false;
	}

	public Point getCoordinates() {
		return new Point(x, y);
	}

	public boolean setX(int x) {
		if (x < 0 || x > matrix[y].length) {
			throw new IllegalArgumentException("Invalid value for x, must be between 0 and %s".formatted(y));
		}
		this.x = x;
		return true;
	}

	public boolean setY(int y) {
		if (y < 0 || y > matrix.length) {
			throw new IllegalArgumentException(
					"Invalid value for y, must be between 0 and %s".formatted(matrix.length));
		}
		this.y = y;
		return true;
	}

	public boolean canMove(Direction direction) {
		boolean valid = false;
		switch (direction) {
		case DOWN -> valid = y < matrix.length;
		case LEFT -> valid = x > 0;
		case LOWER_LEFT -> valid = y < matrix.length && x > 0;
		case LOWER_RIGHT -> valid = y < matrix.length && x < matrix[y + 1].length;
		case RIGHT -> valid = x < matrix[y].length;
		case UP -> valid = y > 0;
		case UPPER_LEFT -> valid = y > 0 && x > 0;
		case UPPER_RIGHT -> valid = y > 0 && x < matrix[y - 1].length;
		}
		return valid;
	}

	public boolean valid(int x, int y) {
		return y >= 0 && y < matrix.length && x >= 0 && x < matrix[y].length;
	}

	public boolean move(int x, int y) {
		if (valid(x, y)) {
			setCoordinates(x, y);
			return true;
		}
		return false;
	}

	public boolean move(Point point) {
		return move(point.x, point.y);
	}

	public boolean move(Direction direction) {
		int newX = this.x + direction.getDeltaX();
		int newY = this.y + direction.getDeltaY();
		return move(newX, newY);
	}

	public boolean move(int amount, Direction direction) {
		int i = 0;
		while (move(direction) && i < amount) {
			i++;
		}
		return i == amount;
	}

	public char getValue(int x, int y) {
		if (valid(x, y)) {
			return matrix[y][x];
		}
		return Character.MIN_VALUE;
	}

	public char getCurrent() {
		return getValue(x, y);
	}
}
