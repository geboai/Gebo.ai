package ai.gebo.llms.chat.abstraction.layer.session.model;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MinimalChatContext {
	@NotNull
	private GeboChatRequest currentRequest = null;
	@NotNull
	private CSSConsolidatedChatHistory chatHistory = new CSSConsolidatedChatHistory();
}
