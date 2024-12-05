package adventofcode.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Matrix<T> {

	private T[][] matrix;

	private int x;

	private int y;

	public static Matrix<Character> charMatrix(List<String> input) {
		Character[][] matrix = new Character[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			matrix[i] = new Character[input.get(i).length()];
			for (int j = 0; j < input.get(i).length(); j++) {
				matrix[i][j] = input.get(i).charAt(j);
			}
		}
		return new Matrix<Character>(matrix);
	}
	
	public Matrix(T[][] input) {
		matrix = input;
		x = 0;
		y = 0;
	}
	
	public Matrix(List<List<T>> input) {
		matrix = (T[][]) new Object[input.size()][input.get(0).size()];
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < input.get(i).size(); j++) {
				matrix[i][j] = input.get(i).get(j);
			}
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

	public boolean traverse() {
		if (move(Direction.RIGHT)) {
			return true;
		} else {
			setX(0);
			return move(Direction.DOWN);
		}
	}
	
	public T getValue(int x, int y) {
		if (valid(x, y)) {
			return matrix[y][x];
		}
		return null;
	}

	public List<T> getRange(int x, int y, int size, Direction direction) {
		List<T> value = new ArrayList<>();
		int originalX = x;
		int originalY = y;
		if (!move(x,y)) {
			return value;
		}
		for (int i=0; i<size; i++) {
			value.add(getCurrent());
			if (!move(direction)) {
				break;
			}
		}
		move(originalX, originalY);
		return value;
	}
	
	public T getCurrent() {
		return getValue(x, y);
	}
}
