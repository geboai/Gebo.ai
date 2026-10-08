package ai.gebo.llms.chat.abstraction.layer.services.impl;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import lombok.Data;

@Data
public class SessionShrinkRequestPayload extends GBaseMessagePayload {
	private String userChatSessionCode = null;
	private int tokensBudget = 0;
	// only the chat's minimal context for the budget is prepared (see
	// IGChatSessionStateShrinkerService#prepareMinimalContext), not the chat's state shrunk
	private boolean minimalContextOnly = false;

}
