package adventofcode.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTO {

	private String name;
	private int year;
	private int day;
	private long processingA;
	private long processingB;
	private String solutionA;
	private String solutionB;
	private String href;
}
