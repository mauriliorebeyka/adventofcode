package adventofcode.solutions.year2022;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import adventofcode.challenge.ChallengeDetails;
import adventofcode.challenge.ChallengeSolution;
import adventofcode.challenge.StringChallenge;

@ChallengeDetails(year = 2022, day = 13)
public class Day13 extends StringChallenge {

	@ChallengeSolution
	public String processA() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@ChallengeSolution
	public String processB() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<List<String>> getValues(String s) {
		String values = s.substring(1,s.lastIndexOf(']'));
		for (String ss : values.split(",")) {
			
		}
		List<List<String>> lists = new ArrayList<>();
		return lists;
	}
	
	@Test
	public void test() {
		String a = "[1,2,[4,5],3]";
		for (List<String> l : getValues(a)) {
			for (String s : l) {
				System.out.println(s);
			}
			System.out.println("---");
		}
	}
}
