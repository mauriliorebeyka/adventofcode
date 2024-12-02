package adventofcode.solutions.year2023;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import adventofcode.utils.AbstractChallenge;
import adventofcode.utils.ChallengeDetails;
import adventofcode.utils.ChallengeSolution;

@ChallengeDetails(year = 2023, day = 2)
public class Day2 extends AbstractChallenge<Day2.Game> {

	@ChallengeSolution
	public int processA() {
		return getInput().stream().filter(g -> !validGame(g.getRounds(), 12, 13, 14)).mapToInt(Game::getId).sum();
	}

	@ChallengeSolution
	public long processB() {
		return getInput().stream().mapToLong(this::gamePower).reduce(Math::addExact).getAsLong();
	}

	private boolean validGame(List<Round> rounds, int red, int green, int blue) {
		return rounds.stream()
				.anyMatch(r -> (r.getCube().equals("green") && r.getAmount() > green)
						|| (r.getCube().equals("red") && r.getAmount() > red)
						|| (r.getCube().equals("blue") && r.getAmount() > blue));
	}

	public long gamePower(Game game) {
		int red = game.getRounds().stream().filter(r -> r.getCube().equals("red")).mapToInt(r -> r.getAmount()).max()
				.getAsInt();
		int green = game.getRounds().stream().filter(r -> r.getCube().equals("green")).mapToInt(r -> r.getAmount())
				.max().getAsInt();
		int blue = game.getRounds().stream().filter(r -> r.getCube().equals("blue")).mapToInt(r -> r.getAmount()).max()
				.getAsInt();
		return red * green * blue;
	}

	@Override
	public Game parse(String entry) {
		String[] gameId = entry.split(":");
		Game game = new Game(Integer.parseInt(gameId[0].replace("Game ", "")));
		for (String round : gameId[1].split(";")) {
			for (String cube : round.split(",")) {
				String[] cubeInfo = cube.trim().split(" ");
				game.getRounds().add(new Round(cubeInfo[1], Integer.parseInt(cubeInfo[0])));
			}
		}
		return game;
	}

	@Test
	public void test() {
		this.setTestInput("""
				Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
				Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
				Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
				Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
				Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
								""");
		System.out.println(processA());
	}

	class Game {
		private int id;
		private List<Round> rounds;

		public Game(int id) {
			this.id = id;
			this.rounds = new ArrayList<>();
		}

		public int getId() {
			return id;
		}

		public List<Round> getRounds() {
			return rounds;
		}
	}

	class Round {
		private String cube;
		private int amount;

		public Round(String cube, int amount) {
			this.cube = cube;
			this.amount = amount;
		}

		public String getCube() {
			return cube;
		}

		public int getAmount() {
			return amount;
		}
	}
}
