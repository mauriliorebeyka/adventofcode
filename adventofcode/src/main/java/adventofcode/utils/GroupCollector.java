package adventofcode.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GroupCollector<T, R> implements Collector<T, Stack<Collection<T>>, List<R>> {

	public static <T> GroupCollector<T, List<T>> asLists(Predicate<T> predicate) {
		return new GroupCollector<T, List<T>>(predicate, ArrayList<T>::new);
	}
	
	public static <T> GroupCollector<T, List<T>> asLists() {
		return new GroupCollector<T, List<T>>(ArrayList<T>::new);
	}
	
	private Predicate<T> predicate;
	
	private Function<Collection<T>, R> transform;

	private Supplier<Collection<T>> supplier;
	
	private int size;
	
	public GroupCollector(int size, Predicate<T> predicate, Function<Collection<T>, R> transform, Supplier<Collection<T>> supplier) {
		this.size = size;
		this.predicate = predicate;
		this.transform = transform;
		this.supplier = supplier;
	}
	
	public GroupCollector(Predicate<T> predicate, Function<Collection<T>, R> transform) {
		this(-1, predicate, transform, ArrayList<T>::new);
	}
	
	public GroupCollector(int size, Function<Collection<T>, R> transform) {
		this(size, a -> true, transform, ArrayList<T>::new);
	}
	
	public GroupCollector(Function<Collection<T>, R> transform) {
		this(-1, Objects::nonNull, transform, ArrayList<T>::new);
	}
	
	@Override
	public Supplier<Stack<Collection<T>>> supplier() {
		return () -> {
			Stack<Collection<T>> stack = new Stack<>();
			stack.add(supplier.get());
			return stack;
		};
	}

	@Override
	public BiConsumer<Stack<Collection<T>>, T> accumulator() {
		return (stack, next) -> {
			if (stack.peek().size() == size || !predicate.test(next)) {
				stack.add(supplier.get());
			}
			if(predicate.test(next)) {
				stack.peek().add(next);
			}
		};
	}

	@Override
	public BinaryOperator<Stack<Collection<T>>> combiner() {
		return (left, right) -> {
			left.addAll(right);
			return left;
		};
	}

	@Override
	public Function<Stack<Collection<T>>, List<R>> finisher() {
		return f -> f.stream().map(transform).collect(Collectors.toList());
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}

}
