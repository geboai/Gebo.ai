package ai.gebo.architecture.agents.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

	/** How thorough a deep search is: how many fragments of each document it reads and how long its analysis is. */
	public enum DeepSearchDepth {
		FOCUSED, BROAD, EXHAUSTIVE
	}

	@JsonPropertyDescription("Run a deep search: the documents the searches find are read widely, analysed batch by batch "
			+ "against the command and the analyses reduced into one; the agent returns that analysis, with the documents "
			+ "it rests on, instead of the fragments found. For a broad question, a synthesis, a report or a comparison "
			+ "that needs many documents read. False or absent for a plain search, returning the most relevant fragments")
	private Boolean deepSearch = null;
	@JsonPropertyDescription("How thorough the deep search is: FOCUSED for a precise answer (a few fragments of each "
			+ "document, a short analysis), BROAD (when not given) for a synthesis, EXHAUSTIVE for a detailed report, a "
			+ "decision or a comparison (many fragments of each document, a long analysis). Ignored when deepSearch is not true")
	private DeepSearchDepth deepSearchDepth = null;

	/** Whether the command asks for a deep search. */
	@JsonIgnore
	public boolean isDeepSearchRequested() {
		return Boolean.TRUE.equals(deepSearch);
	}
}
