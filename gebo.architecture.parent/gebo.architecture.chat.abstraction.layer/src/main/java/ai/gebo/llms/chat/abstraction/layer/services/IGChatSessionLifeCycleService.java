package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/******************************************************************************************************
 * Mantains the 2 session levels transparently letting adding resources in the
 * chat requests/session transparently always returned an updated and trimmed to
 * tokensBudgetSize LLMChatRequestResources directly usable for llms call. At
 * the beginning of a chat user context life the chat session is created, in the
 * chat interaction user request, resources are added/removed, the assistant
 * response is registered, and once chatRequestCompleted is called the service
 * will choose if generate or update the shrinked session state. A shrinked
 * session state is managed only when the target tokensBudget is reached in a
 * call to chatRequestCompleted, untill that moment adding resources reaching
 * the tokensBudget causes only the returned LLMChatRequestResources to discard
 * oldest references (even if is created from the full or the shrinked session
 * state.
 */
public interface IGChatSessionLifeCycleService {
	public void ensureChatSessionExists(GUserChatSession context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException;

	public void createChatSession(GUserChatSession context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException;

	public void removeChatSession(GUserChatSession context) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addRequestToState(GUserChatSession context, GeboChatRequest request,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException, IOException;

	public LLMChatRequestResources addUploadedDocumentToState(GUserChatSession context, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException, IOException;

	public LLMChatRequestResources removeUploadedDocumentToState(GUserChatSession context, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addChatWithDocumentToState(GUserChatSession context, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources removeChatWithDocumentToState(GUserChatSession context, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addRetrievedDocumentsToState(GUserChatSession context, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources removeRetrievedDocumentsToState(GUserChatSession context, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addLLMGeneratedDocumntsToState(GUserChatSession context,
			LLMGeneratedResource resource, IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException;

	public void addInteractionToState(GUserChatSession context, GeboChatRequest request, GeboChatResponse response) throws GeboChatSessionLifecycleException;

	public void chatRequestCompleted(GUserChatSession context, IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException, LLMConfigException, IOException;

}
