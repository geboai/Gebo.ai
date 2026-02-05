package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;

/**********************************************************************************
 * Create an in memory image of all documents/messages exchanged in a chat
 * session
 */
public interface IGChatFullSessionStateService extends IGGenericalSessionStateService<ChatFullSessionState>{
	
}
