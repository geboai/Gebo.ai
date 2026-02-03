package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutingDecisionResponse {
	@NotNull
	private RespondingWith responseRoutingDecision = null;
	private SearchesSuggestions suggestedSearches = null;
	private List<String> expandDocuments = null;
	private List<String> deepSearchDataSourceCodesToAnalyze = new ArrayList<String>();
	private List<String> toolsToUse = null;
	private Float confidence = null;
	private List<String> ambiguityReasons = new ArrayList<String>();
	private Boolean needsRewrite=null;
}