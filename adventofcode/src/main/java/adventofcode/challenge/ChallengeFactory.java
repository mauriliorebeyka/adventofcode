package adventofcode.challenge;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("rawtypes")
public class ChallengeFactory {

	private static final Logger LOG = LogManager.getLogger(ChallengeFactory.class);

	private static final String PACKAGE_NAME = "adventofcode";
	
	public AbstractChallenge<?> getSolution(int year, int day) {
		if (year == 0 || day == 0) {
			return getLatestSolution();
		}
		Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner(true));
		List<Class<? extends AbstractChallenge>> classList = reflections.getSubTypesOf(AbstractChallenge.class).stream()
				.filter(c -> classValidForDate(c, year, day)).collect(Collectors.toList());
		return createSolution(classList);
	}

	public AbstractChallenge<?> getLatestSolution() {
		Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner());
		List<Class<? extends AbstractChallenge>> classList = reflections.getSubTypesOf(AbstractChallenge.class).stream()
				.sorted(new ChallengeComparator()).collect(Collectors.toList());

		int y = classList.get(0).getAnnotation(ChallengeDetails.class).year();
		int d = classList.get(0).getAnnotation(ChallengeDetails.class).day();
		return createSolution(classList.stream().filter(c -> classValidForDate(c, y, d)).collect(Collectors.toList()));
	}

	public List<AbstractChallenge<?>> listSolutions() {
		Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner());
		return reflections.getSubTypesOf(AbstractChallenge.class).stream()
				.sorted(new ChallengeComparator()).map(s -> createSolution(Arrays.asList(s))).collect(Collectors.toList());
	}
	
	private AbstractChallenge<?> createSolution(List<Class<? extends AbstractChallenge>> classList) {
		if (classList.isEmpty()) {
			LOG.error("No Class found to create");
			return null;
		}
		Class<? extends AbstractChallenge> clazz = null;
		if (classList.size() > 1) {
			LOG.warn("More than one solution for the same date, first solution found will be used");
			LOG.debug("Complete list of solutions for the requested date: {}",classList);
		}
		clazz = classList.get(0);
		try {
			int y = clazz.getAnnotation(ChallengeDetails.class).year();
			int d = clazz.getAnnotation(ChallengeDetails.class).day();
			LOG.debug("Creating solution for : Year {} Day {}", y, d);
			LOG.debug("Solution class: {}", clazz.getName());
			return clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.error("Error creating Solution", e);
		}
		return null;
	}

	private static final boolean classValidForDate(Class<? extends AbstractChallenge> clazz, int year, int day) {
		ChallengeDetails details = clazz.getAnnotation(ChallengeDetails.class);
		return details != null && details.year() == year && details.day() == day;
	}
}
