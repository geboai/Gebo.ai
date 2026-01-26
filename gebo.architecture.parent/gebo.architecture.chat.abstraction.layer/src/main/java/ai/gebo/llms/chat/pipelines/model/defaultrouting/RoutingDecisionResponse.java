package ai.gebo.llms.chat.pipelines.model.defaultrouting;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutingDecisionResponse {
	@NotNull
	private RespondingWith responseRoutingDecision = null;
	private SearchRewritings queryRewritings = null;
}