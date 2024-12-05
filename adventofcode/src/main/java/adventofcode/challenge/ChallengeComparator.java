package adventofcode.challenge;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class ChallengeComparator implements Comparator<Class<? extends AbstractChallenge>> {

	@Override
	public int compare(Class<? extends AbstractChallenge> o1, Class<? extends AbstractChallenge> o2) {
		return calculate(o2) - calculate(o1);
	}
	
	private static int calculate(Class<? extends AbstractChallenge> solution) {
		ChallengeDetails o = solution.getAnnotation(ChallengeDetails.class);
		return o == null ? 0 : o.year() * 100 + o.day();
	}
}
