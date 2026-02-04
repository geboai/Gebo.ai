package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.system.ingestion.GeboIngestionException;

/**********************************************************************************
 * Create an in memory image of all documents/messages exchanged in a chat
 * session
 */
public interface IGChatFullSessionStateService extends IGGenericalSessionStateService<ChatFullSessionState>{
	
}
