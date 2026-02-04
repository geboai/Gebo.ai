package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboTemplatedChatResponse;
import lombok.Data;

/**
 * class to represent individual interactions within a chat session. Captures
 * both the request and response aspects of a chat interaction.
 */
@Data
public class ChatInteractions implements ITokensCountable {
	private GeboChatRequest request = null; // Stores the chat request
	private Integer requestNTokens = null; // Number of tokens in the request
	private GeboTemplatedChatResponse response = null; // Stores the chat response
	private Integer responseNTokens = null; // Number of tokens in the response

	

	@Override
	public int getTokensSize() {

		return (requestNTokens != null ? requestNTokens.intValue() : 0)
				+ (responseNTokens != null ? responseNTokens.intValue() : 0);
	}

}