package adventofcode.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class MatrixTest {

	@Test
	public void testCreation() {
		List<List<Integer>> values = new ArrayList<>();
		values.add(List.of(0,1));
		values.add(List.of(3,2));
		Matrix<Integer> matrix = new Matrix<>(values);
		assertEquals(matrix.getValue(0, 0),0);
	}
	
	@Test
	public void testOfCharacter() {
		Matrix<Character> matrix = Matrix.charMatrix(List.of("012","345","678"));
		assertEquals(matrix.getValue(1, 2),'7');
	}
	
	@Test
	public void testGetRange() {
		Matrix<Character> matrix = Matrix.charMatrix(List.of("012","345","678"));
		List<Character> characters = matrix.getRange(0, 0, 4, Direction.DOWN);
		String result = characters.stream().map(c -> c.toString()).collect(Collectors.joining());
		assertEquals("036",result);
		assertEquals(new Point(0,0),matrix.getCoordinates());
	}
	
	@Test
	public void testTraverse() {
		Matrix<Integer> matrix = new Matrix<>(List.of(List.of(0,1,2),List.of(3,4,5),List.of(6,7,8)));
		int expected = 0;
		do {
			assertEquals(expected,matrix.getCurrent());
			expected++;
		} while(matrix.traverse());
		assertEquals(9,expected);
	}
}
