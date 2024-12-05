package adventofcode.solutions.year2023;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;

@ChallengeDetails(year = 2023, day = 3)
public class Day3 extends StringChallenge {

	@ChallengeSolution
	public String processA() {
		int sum = 0;
		for (int l = 0; l < getInput().size(); l++) {
			String line = getInput().get(l);
			StringBuilder newDigit = new StringBuilder();
			for (int i = 0; i < line.length(); i++) {
				if (Character.isDigit(line.charAt(i))) {
					newDigit.append(line.charAt(i));
				} else {
					if (!newDigit.isEmpty()) {
						String digit = newDigit.toString();
						System.out.println("checking "+digit);
						int start = i - digit.length() - 1;
						start = start < 0 ? 0 : start;
						boolean valid = checkNeighbors(l, start, digit);
						if (!valid && l > 0) {
							valid = checkNeighbors(l-1, start, digit);
						}
						if (!valid && l <getInput().size()-1) {
							valid = checkNeighbors(l+1, start, digit);
						}
						if (valid) {
							System.out.println("Adding "+digit);
							sum += Integer.parseInt(digit);
						}
						newDigit.delete(0, newDigit.length());
					}
				}
			}
			if (!newDigit.isEmpty()) {
				String digit = newDigit.toString();
				System.out.println("checking "+digit);
				int start = line.length() - digit.length() - 1;
				start = start < 0 ? 0 : start;
				boolean valid = checkNeighbors(l, start, digit);
				if (!valid && l > 0) {
					valid = checkNeighbors(l-1, start, digit);
				}
				if (!valid && l <getInput().size()-1) {
					valid = checkNeighbors(l+1, start, digit);
				}
				if (valid) {
					System.out.println("Adding "+digit);
					sum += Integer.parseInt(digit);
				}
				newDigit.delete(0, newDigit.length());
			}
		}
		return Integer.toString(sum);
	}

	private boolean checkNeighbors(int l, int start, String newDigit) {
		String line = getInput().get(l);
		for (int i = start; i < start + newDigit.length()+2 && i < line.length(); i++) {
			if (line.charAt(i) != '.' && !Character.isDigit(line.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	@ChallengeSolution
	public String processB() {
		// TODO Auto-generated method stub
		return null;
	}

}
