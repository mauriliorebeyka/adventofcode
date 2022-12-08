package adventofcode.utils;

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
public class SolutionFactory {

	private static final Logger LOG = LogManager.getLogger(SolutionFactory.class);

	private static final String PACKAGE_NAME = "adventofcode";
	
	public AbstractSolution<?> getSolution(int year, int day) {
		if (year == 0 || day == 0) {
			return getLatestSolution();
		}
		Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner(true));
		List<Class<? extends AbstractSolution>> classList = reflections.getSubTypesOf(AbstractSolution.class).stream()
				.filter(c -> classValidForDate(c, year, day)).collect(Collectors.toList());
		return createSolution(classList);
	}

	public AbstractSolution<?> getLatestSolution() {
		Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner());
		List<Class<? extends AbstractSolution>> classList = reflections.getSubTypesOf(AbstractSolution.class).stream()
				.sorted(new SolutionComparator()).collect(Collectors.toList());

		int y = classList.get(0).getAnnotation(SolutionDetails.class).year();
		int d = classList.get(0).getAnnotation(SolutionDetails.class).day();
		return createSolution(classList.stream().filter(c -> classValidForDate(c, y, d)).collect(Collectors.toList()));
	}

	public List<AbstractSolution<?>> listSolutions() {
		Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner());
		return reflections.getSubTypesOf(AbstractSolution.class).stream()
				.sorted(new SolutionComparator()).map(s -> createSolution(Arrays.asList(s))).collect(Collectors.toList());
	}
	
	private AbstractSolution<?> createSolution(List<Class<? extends AbstractSolution>> classList) {
		if (classList.isEmpty()) {
			LOG.error("No Class found to create");
			return null;
		}
		Class<? extends AbstractSolution> clazz = null;
		if (classList.size() > 1) {
			LOG.warn("More than one solution for the same date, first solution found will be used");
			LOG.debug("Complete list of solutions for the requested date: {}",classList);
		}
		clazz = classList.get(0);
		try {
			int y = clazz.getAnnotation(SolutionDetails.class).year();
			int d = clazz.getAnnotation(SolutionDetails.class).day();
			LOG.debug("Creating solution for : Year {} Day {}", y, d);
			LOG.debug("Solution class: {}", clazz.getName());
			return clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.error("Error creating Solution", e);
		}
		return null;
	}

	private static final boolean classValidForDate(Class<? extends AbstractSolution> clazz, int year, int day) {
		SolutionDetails details = clazz.getAnnotation(SolutionDetails.class);
		return details != null && details.year() == year && details.day() == day;
	}
}
