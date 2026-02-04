package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.IChatRequestFactory;
import ai.gebo.system.ingestion.GeboIngestionException;

/**********************************************************************************************
 * There are 2 levels of session cache with the same operator signatures, the
 * 1st ChatFullSessionState is the full session rappresentation with all
 * retrieved documents. The 2nd is the ShrinkedSessionState meant to adapt to
 * the target in term of tokens budget, calling update features on the main
 * session state triggers resizes on the 2nd, changing llm tokens budget will
 * trigger adaptations
 * 
 * @param <SessionType>
 */
public interface IGGenericalSessionStateService<SessionType extends IChatRequestFactory> {
	public default SessionType retrieveState(GUserChatContext context, int targetTokenBudget) {
		return retrieveState(context.getCode());
	}

	public SessionType retrieveState(String id);

	public void deleteState(String id);

	public SessionType addRequestToState(GeboChatRequest request, GUserChatContext context, int targetTokenBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException;

	public SessionType addInteractionToState(GeboChatRequest request, GeboChatResponse response,
			GUserChatContext context, int targetTokenBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException;
}
