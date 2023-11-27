package adventofcode.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {

	private String name;
	private int year;
	private int day;
	private List<ChallengeSolutionDTO> solutions;
	private String href;
}
