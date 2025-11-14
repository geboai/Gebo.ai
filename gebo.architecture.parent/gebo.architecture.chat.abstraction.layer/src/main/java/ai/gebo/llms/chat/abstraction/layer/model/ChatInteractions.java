package ai.gebo.llms.chat.abstraction.layer.model;

import lombok.Data;

/**
 * class to represent individual interactions within a chat session. Captures
 * both the request and response aspects of a chat interaction.
 */

public class ChatInteractions {
	public GeboChatRequest request = null; // Stores the chat request
	public Integer requestNTokens = null; // Number of tokens in the request
	public GeboTemplatedChatResponse response = null; // Stores the chat response
	public Integer responseNTokens = null; // Number of tokens in the response

	public ChatInteractions clientClone() throws CloneNotSupportedException {
		GeboChatRequest clonedrequest = request != null ? (GeboChatRequest) request.clone() : null;
		clonedrequest.setDocuments(null);
		ChatInteractions cloned = new ChatInteractions();
		cloned.request = clonedrequest;
		cloned.requestNTokens = (requestNTokens);
		cloned.response = (response);
		cloned.responseNTokens = (responseNTokens);
		return cloned;
	}
}