package ai.gebo.llms.chat.abstraction.layer.services.impl;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import lombok.Data;

@Data
public class SessionShrinkRequestPayload extends GBaseMessagePayload {
	private String userChatSessionCode = null;
	private int tokensBudget = 0;

}
