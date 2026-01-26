package ai.gebo.llms.chat.pipelines.model.defaultrouting;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutingDecisionResponse {
	@NotNull
	private RespondingWith responseRouting = null;
}