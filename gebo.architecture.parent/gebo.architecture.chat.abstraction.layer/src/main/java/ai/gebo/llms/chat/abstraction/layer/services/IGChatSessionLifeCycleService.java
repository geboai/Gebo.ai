package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;

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

	public boolean isSessionExisting(GeboChatRequest request);

	public GeboChatResponse createEmptyResponse(GeboChatRequest request) throws GeboChatSessionLifecycleException;

	public void ensureChatSessionExists(GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException;

	public void createChatSession(GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException;

	public void removeChatSession(String code) throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources startRequest(GeboChatRequest request, IGConfigurableChatModel targetChatModel,
			LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException, IOException;

	public void updateRequest(GeboChatRequest request) throws GeboChatSessionLifecycleException, IOException;

	public LLMChatRequestResources addUploadedDocument(GeboChatRequest request, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, IOException;

	public LLMChatRequestResources removeUploadedDocument(GeboChatRequest request, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addChatWithDocument(GeboChatRequest request, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources removeChatWithDocument(GeboChatRequest request, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addRetrievedDocuments(GeboChatRequest request, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources removeRetrievedDocuments(GeboChatRequest request, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException;

	public LLMChatRequestResources addLLMGenerated(GeboChatRequest request, LLMGeneratedResource resource,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException;

	public void endRequest(GeboChatRequest request, GeboChatResponse response) throws GeboChatSessionLifecycleException;

	public List<GKnowledgeBase> getSessionAvailableKnowledgeBases(GeboChatRequest request)
			throws GeboChatSessionLifecycleException;

	public IGConfigurableChatModel getSessionChatModel(GeboChatRequest request)
			throws GeboChatSessionLifecycleException;

	public void chatRequestCompleted(GeboChatRequest request, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException, LLMConfigException, IOException;

	public GUserChatInfo createCleanChatByModel(IGConfigurableChatModel chatModel) throws GeboPersistenceException;

	public GUserChatInfo createCleanChatByChatProfileCode(String chatProfileCode) throws GeboPersistenceException;

	public GUserChatInfo createCleanChatByModelCode(String modelCode) throws GeboPersistenceException;

	public GUserChatInfo suggestChatDescription(String id) throws GeboChatSessionLifecycleException;

	public MinimalChatContext getMinimalChatContext(GeboChatRequest request, int tokensBudget)
			throws GeboChatSessionLifecycleException;

	public void persist(GeboChatRequest request) throws GeboChatSessionLifecycleException;

	public List<IGConfigurableEmbeddingModel> getSessionEmbeddingModels(GeboChatRequest request)
			throws GeboChatSessionLifecycleException;

}
