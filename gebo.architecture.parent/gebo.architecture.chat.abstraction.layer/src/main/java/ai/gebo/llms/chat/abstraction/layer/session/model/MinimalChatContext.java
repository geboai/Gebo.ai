package ai.gebo.llms.chat.abstraction.layer.session.model;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MinimalChatContext implements ITokensCountable {
	@NotNull
	private GeboChatRequest currentRequest = null;
	@NotNull
	private CSSConsolidatedChatHistory chatHistory = new CSSConsolidatedChatHistory();

	@Override
	public int getTokensSize() {

		return ITokensCountable.tokensSize(currentRequest, chatHistory);
	}
}
