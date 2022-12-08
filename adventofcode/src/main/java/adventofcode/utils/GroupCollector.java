package adventofcode.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GroupCollector<T, R> implements Collector<T, Stack<List<T>>, List<R>> {

	private Predicate<T> predicate;
	
	private Function<List<T>, R> transform;
	
	private int size;
	
	public GroupCollector(Predicate<T> predicate, Function<List<T>, R> transform) {
		this.size = -1;
		this.predicate = predicate;
		this.transform = transform;
	}
	
	public GroupCollector(int size, Function<List<T>, R> transform) {
		this.size = size;
		this.predicate = a -> true;
		this.transform = transform;
	}
	
	@Override
	public Supplier<Stack<List<T>>> supplier() {
		return () -> {
			Stack<List<T>> stack = new Stack<>();
			stack.add(new ArrayList<T>());
			return stack;
		};
	}

	@Override
	public BiConsumer<Stack<List<T>>, T> accumulator() {
		return (stack, next) -> {
			if (stack.peek().size() == size || !predicate.test(next)) {
				stack.add(new ArrayList<>());
			}
			if(predicate.test(next)) {
				stack.peek().add(next);
			}
		};
	}

	@Override
	public BinaryOperator<Stack<List<T>>> combiner() {
		return (left, right) -> {
			left.addAll(right);
			return left;
		};
	}

	@Override
	public Function<Stack<List<T>>, List<R>> finisher() {
		return f -> f.stream().map(transform).collect(Collectors.toList());
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.singleton(Characteristics.UNORDERED);
	}

}
