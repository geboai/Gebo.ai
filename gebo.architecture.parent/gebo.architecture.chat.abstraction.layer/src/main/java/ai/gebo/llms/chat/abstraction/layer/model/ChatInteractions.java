package ai.gebo.llms.chat.abstraction.layer.model;

import lombok.Data;

/**
 * class to represent individual interactions within a chat session. Captures
 * both the request and response aspects of a chat interaction.
 */
@Data
public class ChatInteractions {
	private  GeboChatRequest request = null; // Stores the chat request
	private  Integer requestNTokens = null; // Number of tokens in the request
	private  GeboTemplatedChatResponse response = null; // Stores the chat response
	private  Integer responseNTokens = null; // Number of tokens in the response

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