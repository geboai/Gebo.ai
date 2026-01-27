package ai.gebo.llms.chat.pipelines.service.defaultsteps.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutingDecisionResponse {
	@NotNull
	private RespondingWith responseRoutingDecision = null;
	private SearchRewritings queryRewritings = null;
	private List<String> expandDocuments = null;
	private List<String> deepSearchDataSourceCodesToAnalyze = new ArrayList<String>();
	private List<String> toolsToUse = null;
}