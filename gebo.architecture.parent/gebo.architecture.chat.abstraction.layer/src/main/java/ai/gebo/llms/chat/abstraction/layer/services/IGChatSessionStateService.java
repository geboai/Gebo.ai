package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;

/**********************************************************************************
 * Create an in memory image of all documents/messages exchanged in a chat
 * session
 */
public interface IGChatSessionStateService {
	public ChatFullSessionState extractState(GeboChatRequest request, GUserChatContext context) throws IOException;
}
