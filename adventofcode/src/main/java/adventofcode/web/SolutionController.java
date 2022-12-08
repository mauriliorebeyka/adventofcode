package adventofcode.web;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adventofcode.utils.AbstractSolution;
import adventofcode.utils.InputReader;
import adventofcode.utils.SolutionExecutorProcessor;
import adventofcode.utils.SolutionFactory;
import adventofcode.web.dto.SolutionDTO;
import adventofcode.web.dto.SolutionDTO.SolutionDTOBuilder;

@RestController
public class SolutionController {

	@Autowired
	private SolutionFactory solutionFactory;
	
	@Autowired
	private InputReader inputReader;
	
	@GetMapping("/solution")
	public SolutionDTO solution(@RequestParam(value = "year", defaultValue = "0") int year,
			@RequestParam(value = "day", defaultValue = "0") int day) {
		AbstractSolution<?> solution = solutionFactory.getSolution(year, day);
		solution.init(inputReader);
		if (!Objects.isNull(solution)) {
			return toSolutionDto(solution, true);
		} else {
			return SolutionDTO.builder().name("Unable to find solution").year(year).day(day).build();
		}
	}
	
	@GetMapping("/list")
	public List<SolutionDTO> list() {
		List<AbstractSolution<?>> list = solutionFactory.listSolutions();
		return list.stream().map(sol -> this.toSolutionDto(sol, false)).collect(Collectors.toList());
	}
	
	private SolutionDTO toSolutionDto(AbstractSolution<?> solution, boolean execute) {
		SolutionDTOBuilder builder = SolutionDTO.builder().name(solution.getClass().getName()).year(solution.getYear())
				.day(solution.getDay()).href("/solution?year=2022&day=5");
		if (execute) {
		SolutionExecutorProcessor executor = new SolutionExecutorProcessor(solution);
		SolutionDTO solutionDTO = executor.processBothSolutions();
		builder.solutionA(solutionDTO.getSolutionA())
				.processingA(solutionDTO.getProcessingA()).solutionB(solutionDTO.getSolutionB())
				.processingB(solutionDTO.getProcessingB());
		}
		return builder.build();
	}
}
