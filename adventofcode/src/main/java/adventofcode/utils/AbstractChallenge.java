package adventofcode.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import adventofcode.exceptions.InputParseException;

public abstract class AbstractChallenge<T> {

	private static final Logger LOG = LogManager.getLogger(AbstractChallenge.class);

	protected List<T> input;

	protected List<T> testInput;
	
	private int year;

	private int day;

	public AbstractChallenge() {
		year = this.getClass().getAnnotation(ChallengeDetails.class).year();
		day = this.getClass().getAnnotation(ChallengeDetails.class).day();
	}
	
	public void init(InputReader inputReader) throws InputParseException {
		LOG.debug("Reading input for year {}, day {}", year, day);
		input = inputReader.readMultipleLines(year, day).stream().map(this::parse).collect(Collectors.toList());
	}

	public abstract T parse(String entry);

	public List<T> getInput() {
		return input;
	}

	public void setTestInput(String input) {
		this.testInput = Stream.of(input.split("\n")).map(this::parse).collect(Collectors.toList());
	}
	
	public final int getYear() {
		return year;
	}

	public final int getDay() {
		return day;
	}


}
