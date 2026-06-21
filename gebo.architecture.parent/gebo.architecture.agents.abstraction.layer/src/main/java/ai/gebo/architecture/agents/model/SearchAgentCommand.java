package ai.gebo.architecture.agents.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonClassDescription("Search command for search agent")
public class SearchAgentCommand {
	@JsonPropertyDescription("Execute ranking on found documents list")
	private Boolean executeRanking = null;
	@JsonPropertyDescription("Number of documents retrieved")
	private int topK = 20;
	@NotNull
	@JsonPropertyDescription("Search specifications that will be executed by the search agent")
	private String command = null;

}
