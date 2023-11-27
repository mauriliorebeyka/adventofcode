package adventofcode.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class GroupCollectorTest {

	@Test
	public void test() {
		List<String> input = List.of("1","2","3","","4");
		List<List<String>> result = input.stream().collect(GroupCollector.asLists(s -> !s.isBlank()));
		assertThat(result).hasSize(2).contains(List.of("1","2","3"),List.of("4"));
	}
	
	@Test
	public void testListNonNull() {
		List<String> input = Arrays.asList("1","2","3","2",null,"1","4");
		List<List<String>> result = input.stream().collect(GroupCollector.asLists());
		assertThat(result).hasSize(2).contains(List.of("1","2","3","2"),List.of("1","4"));
	}
	
	@Test
	public void testSet() {
		List<String> input = Arrays.asList("1","2","3","2",null,"1","4");
		List<Set<String>> result = input.stream().collect(new GroupCollector<String, Set<String>>(-1, Objects::nonNull, HashSet<String>::new, HashSet<String>::new));
		assertThat(result).hasSize(2).contains(Set.of("1","2","3"),Set.of("1","4"));
	}
	
	@Test
	public void testTransform() {
		List<String> input = Arrays.asList("1","2","3","2","","1","4");
		List<Integer> result = input.stream().collect(new GroupCollector<String, Integer>(s -> !s.isBlank(), c -> c.stream().mapToInt(Integer::parseInt).sum()));
		assertThat(result).contains(8,5);
	}
	
	@Test
	public void testCombineSize() {
		List<String> input = Arrays.asList("1","2","3","2","1","1","4");
		List<Integer> result = input.stream().collect(new GroupCollector<String, Integer>(3, c -> c.stream().mapToInt(Integer::parseInt).sum()));
		assertThat(result).contains(6,4,4);
	}
	
}
