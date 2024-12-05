package adventofcode.utils;

public enum Direction {

	UP(0, -1),

	UPPER_RIGHT(1, -1),

	RIGHT(1, 0),

	LOWER_RIGHT(1, 1),

	DOWN(0, 1),

	LOWER_LEFT(-1, 1),

	LEFT(-1, 0),

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