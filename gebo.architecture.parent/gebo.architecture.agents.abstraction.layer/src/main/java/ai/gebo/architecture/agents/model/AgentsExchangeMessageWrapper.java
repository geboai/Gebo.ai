package ai.gebo.architecture.agents.model;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.security.services.ReactiveIdentityUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgentsExchangeMessageWrapper extends GBaseMessagePayload {
	@NotNull
	ReactiveIdentityUtil runAs;
	private AgentsExchangeMessage wrapped = null;
}
