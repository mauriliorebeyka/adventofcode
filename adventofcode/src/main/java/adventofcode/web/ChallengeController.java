package adventofcode.web;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adventofcode.challenge.AbstractChallenge;
import adventofcode.challenge.ChallengeExecutorProcessor;
import adventofcode.challenge.ChallengeFactory;
import adventofcode.challenge.InputReader;
import adventofcode.exceptions.InputParseException;
import adventofcode.web.dto.ChallengeDTO;
import adventofcode.web.dto.ChallengeDTO.ChallengeDTOBuilder;

@RestController
public class ChallengeController {

	@Autowired
	private ChallengeFactory solutionFactory;

	@Autowired
	private InputReader inputReader;

	@GetMapping("/solution")
	public ChallengeDTO solution(@RequestParam(value = "year", defaultValue = "0") int year,
			@RequestParam(value = "day", defaultValue = "0") int day,
			@RequestParam(value = "timeout", defaultValue = "10000") long timeout,
			@RequestParam(value = "parallel", defaultValue = "true") boolean parallel) {
		AbstractChallenge<?> solution = solutionFactory.getSolution(year, day);
		try {
			solution.init(inputReader);
		} catch (InputParseException e) {
			return ChallengeDTO.builder().name(e.getMessage()).build();
		}
		if (!Objects.isNull(solution)) {
			return toSolutionDto(solution, true, timeout, parallel);
		} else {
			return ChallengeDTO.builder().name("Unable to find solution").year(year).day(day).build();
		}
	}

	@GetMapping("/list")
	public List<ChallengeDTO> list(@RequestParam(value = "timeout", defaultValue = "10000") long timeout) {
		List<AbstractChallenge<?>> list = solutionFactory.listSolutions();
		return list.stream().map(sol -> this.toSolutionDto(sol, false, timeout, true)).collect(Collectors.toList());
	}

	private ChallengeDTO toSolutionDto(AbstractChallenge<?> solution, boolean execute, long timeout, boolean parallel) {
		ChallengeDTOBuilder builder = ChallengeDTO.builder().name(solution.getClass().getName())
				.year(solution.getYear()).day(solution.getDay())
				.href("/solution?year=%s&day=%s".formatted(solution.getYear(), solution.getDay()));
		if (execute) {
			ChallengeExecutorProcessor executor = new ChallengeExecutorProcessor(solution, timeout, parallel);
			ChallengeDTO solutionDTO = executor.processAllChallenges();
			builder.solutions(solutionDTO.getSolutions());
		}
		return builder.build();
	}
}
