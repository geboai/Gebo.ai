package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.IChatRequestFactory;

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
	public default SessionType retrieveState(GUserChatContext context) {
		return retrieveState(context.getCode());
	}

	public SessionType retrieveState(String id);

	public void deleteState(String id);

	public SessionType save(SessionType data);

	public SessionType addRequestToState(SessionType session, GeboChatRequest request, int index)
			throws GeboChatSessionLifecycleException;

	public SessionType addUploadedDocumentToState(SessionType session, UserUploadedContent content, AIDocumentReferenceItem ingested, int index)
			throws GeboChatSessionLifecycleException;

	public SessionType removeUploadedDocumentToState(SessionType session, UserUploadedContent content)
			throws GeboChatSessionLifecycleException;

	public SessionType addChatWithDocumentToState(SessionType session, GDocumentReference reference, AIDocumentReferenceItem ingestedDocument, int index)
			throws GeboChatSessionLifecycleException;

	public SessionType removeChatWithDocumentToState(SessionType session, GDocumentReference reference)
			throws GeboChatSessionLifecycleException;

	public SessionType addRetrievedDocumentsToState(SessionType session, AIDocumentsSet retrieved, int index)
			throws GeboChatSessionLifecycleException;

	public SessionType removeRetrievedDocumentsToState(SessionType session, AIDocumentsSet retrieved)
			throws GeboChatSessionLifecycleException;

	public SessionType addLLMGeneratedDocumntsToState(SessionType session, LLMGeneratedResource resource, AIDocumentReferenceItem ingested, int index)
			throws GeboChatSessionLifecycleException;

	public SessionType addInteractionToState(SessionType session, GeboChatRequest request,
			GeboChatResponse response, int index) throws GeboChatSessionLifecycleException;
}
