package adventofcode.utils;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class SolutionComparator implements Comparator<Class<? extends AbstractSolution>> {

	@Override
	public int compare(Class<? extends AbstractSolution> o1, Class<? extends AbstractSolution> o2) {
		return calculate(o2) - calculate(o1);
	}
	
	private static int calculate(Class<? extends AbstractSolution> solution) {
		SolutionDetails o = solution.getAnnotation(SolutionDetails.class);
		return o == null ? 0 : o.year() * 100 + o.day();
	}
}
