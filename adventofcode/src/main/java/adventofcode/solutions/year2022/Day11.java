package adventofcode.solutions.year2022;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;

@ChallengeDetails(year = 2022, day = 11)
public class Day11 extends StringChallenge {

	@ChallengeSolution
	public String processA() {
		List<Monkey> game = getInitialMonkeys();
		int turns = 20;
		BigInteger divisor = new BigInteger("3");
		BigInteger worryContainer = game.stream().map(i -> new BigInteger(Integer.toString(i.divisor)))
				.reduce(BigInteger::multiply).get();
		for (int i = 0; i < turns; i++) {
			for (Monkey monkey : game) {
				while (!monkey.items.isEmpty()) {
					BigInteger worry = monkey.inspect(monkey.items.pollFirst(), divisor);
					game.get(monkey.getTarget(worry)).items.offerLast(worry.mod(worryContainer));
				}
			}
		}
		return Integer.toString(game.stream().map(m -> m.inspections).sorted(Collections.reverseOrder())
				.mapToInt(i -> i).limit(2).reduce(Math::multiplyExact).getAsInt());
	}

	@ChallengeSolution
	public String processB() {
		List<Monkey> game = getInitialMonkeys();
		int turns = 10000;
		BigInteger divisor = new BigInteger("1");
		BigInteger worryContainer = game.stream().map(i -> new BigInteger(Integer.toString(i.divisor)))
				.reduce(BigInteger::multiply).get();
		for (int i = 0; i < turns; i++) {
			for (Monkey monkey : game) {
				while (!monkey.items.isEmpty()) {
					BigInteger worry = monkey.inspect(monkey.items.pollFirst(), divisor);
					game.get(monkey.getTarget(worry)).items.offerLast(worry.mod(worryContainer));
				}
			}
		}
		String a = game.stream().map(m -> m.inspections).sorted(Collections.reverseOrder())
				.map(i -> new BigInteger(Integer.toString(i))).limit(2).reduce(BigInteger::multiply).get().toString();
		return a;
	}

	public List<Monkey> getInitialMonkeys() {
		List<Monkey> monkeys = new ArrayList<>();
		for (String entry : input) {
			if (entry.trim().isEmpty()) {
				continue;
			}
			String[] data = entry.trim().split(":");
			if (data[0].startsWith("Monkey ")) {
				monkeys.add(new Monkey());
				continue;
			}
			Monkey monkey = monkeys.get(monkeys.size() - 1);
			String[] id = data[1].split(" ");
			switch (data[0]) {
			case "Starting items":
				monkey.items = Stream.of(data[1].split(",")).map(i -> new BigInteger(i.trim()))
						.collect(Collectors.toCollection(ArrayDeque::new));
				break;
			case "Operation":
				monkey.operation = getOperation(id);
				break;
			case "Test":
				monkey.test = i -> i.mod(new BigInteger(id[id.length - 1])).compareTo(BigInteger.ZERO) == 0;
				monkey.divisor = Integer.parseInt(id[id.length - 1]);
				break;
			case "If true":
				monkey.monkeyIdIfTrue = Integer.parseInt(id[id.length - 1]);
				break;
			case "If false":
				monkey.monkeyIdIfFalse = Integer.parseInt(id[id.length - 1]);
				break;
			}
		}
		return monkeys;
	}

	private class Monkey {
		Deque<BigInteger> items;
		Function<BigInteger, BigInteger> operation;
		Predicate<BigInteger> test;
		int divisor;
		int monkeyIdIfTrue;
		int monkeyIdIfFalse;
		int inspections;

		public BigInteger inspect(BigInteger item, BigInteger divisor) {
			inspections++;
			return operation.apply(item).divide(divisor);
		}

		public int getTarget(BigInteger worry) {
			if (test.test(worry)) {
				return monkeyIdIfTrue;
			} else {
				return monkeyIdIfFalse;
			}
		}

		public String toString() {
			return this.items.toString();
		}
	}

	public Function<BigInteger, BigInteger> getOperation(String[] data) {
		BiFunction<BigInteger, BigInteger, BigInteger> operation = null;
		switch (data[4]) {
		case "+":
			operation = BigInteger::add;
			break;
		case "-":
			operation = BigInteger::subtract;
			break;
		case "/":
			operation = BigInteger::divide;
			break;
		case "*":
			operation = BigInteger::multiply;
			break;
		}
		final BiFunction<BigInteger, BigInteger, BigInteger> operation2 = operation;
		if (data[5].equals("old")) {
			return i -> operation2.apply(i, i);
		} else {
			return i -> operation2.apply(i, new BigInteger(data[5]));
		}
	}
	
	@Test
	public void test() {
		String raw = """
				Monkey 0:
				  Starting items: 79, 98
				  Operation: new = old * 19
				  Test: divisible by 23
				    If true: throw to monkey 2
				    If false: throw to monkey 3

				Monkey 1:
				  Starting items: 54, 65, 75, 74
				  Operation: new = old + 6
				  Test: divisible by 19
				    If true: throw to monkey 2
				    If false: throw to monkey 0

				Monkey 2:
				  Starting items: 79, 60, 97
				  Operation: new = old * old
				  Test: divisible by 13
				    If true: throw to monkey 1
				    If false: throw to monkey 3

				Monkey 3:
				  Starting items: 74
				  Operation: new = old + 3
				  Test: divisible by 17
				    If true: throw to monkey 0
				    If false: throw to monkey 1
								""";
		setTestInput(raw);
//		System.out.println(getInitialMonkeys().get(0).test.test(new BigInteger("46")));
		System.out.println(processA());
		System.out.println(processB());
	}
}