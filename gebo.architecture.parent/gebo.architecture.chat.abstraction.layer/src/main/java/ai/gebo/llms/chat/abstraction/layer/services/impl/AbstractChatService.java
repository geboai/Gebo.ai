/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.ClientChatCallUtil;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGGenericalChatService;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * AI generated comments Provides an abstract implementation of chat services,
 * facilitating integration with different chat models.
 */
@AllArgsConstructor
public abstract class AbstractChatService implements IGGenericalChatService {
	private static final String CONVERSATION_SUMMARY_SO_FAR = "Conversation summary so far:";
	protected final static ObjectMapper mapper = new ObjectMapper(); // JSON object mapper for
																		// serialization/deserialization
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass()); // Logger for logging events
	final protected IGChatModelRuntimeConfigurationDao chatModelConfigurations; // DAO for fetching chat model
																				// configurations

	final protected IGToolCallbackSourceRepositoryPattern callbacksRepoPattern; // Repository pattern for tool callbacks
	final protected IGPersistentObjectManager persistenceManager; // Manager for handling persistence operations
	final protected IGPromptConfigDao promptsDao;
	final protected InteractionsContextService interactionsContext;
	final protected IGSecurityService securityService;
	final protected IGChatResponseParsingFixerServiceRepository fixerServiceRepository;
	final protected IGChatStorageAreaService chatStorageAreaService;
	final protected LLMGeneratedResourceRepository generatedResourceRepository;
	final protected IGKnowledgebaseVisibilityService knowledgeBaseSecurityService;
	final protected IGChatSessionLifeCycleService chatSessionLifecycleService;
	final static JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();

	/**
	 * Retrieves chat model user information based on the provided model code.
	 *
	 * @param modelCode Code of the model
	 * @return GeboChatUserInfo containing model user information
	 * @throws GeboChatException  if a chat-related exception occurs
	 * @throws LLMConfigException if the model configuration is not found
	 */
	@Override
	public GeboChatUserInfo getChatModelUserInfo(String modelCode) throws GeboChatException, LLMConfigException {
		IGConfigurableChatModel model = chatModelConfigurations.findByCode(modelCode);
		if (model == null) {
			throw new LLMConfigException("Chat model: " + modelCode + " does not exist");
		}
		GBaseChatModelConfig config = (GBaseChatModelConfig) model.getConfig();
		GBaseChatModelChoice choice = (GBaseChatModelChoice) config.getChoosedModel();

		List<String> functions = config.getEnabledFunctions();
		List<ToolCategoriesTree> trees = callbacksRepoPattern.getEnabledToolsTree(functions);
		GeboChatUserInfo infos = new GeboChatUserInfo(config.getModelTypeCode(), choice, trees);
		return infos;
	}

	/**
	 * Converts the given query response object to a string, handling JSON
	 * processing exceptions.
	 *
	 * @param queryResponse Object to be converted
	 * @return String representation of the query response
	 */
	private String stringhify(Object queryResponse) {

		try {
			return queryResponse instanceof String ? (String) queryResponse : mapper.writeValueAsString(queryResponse);
		} catch (JsonProcessingException e) {
			LOGGER.error("Exception stringhifying a queryResponse", e);
			return "";
		}
	}

	/**
	 * Calls the chat client, processes the chat response, and updates the
	 * associated GeboChatResponse.
	 *
	 * @param configurableChatModel Configurable chat model
	 * @param prompt                Chat prompt
	 * @param context               Knowledge base context
	 * @param request               Request object
	 * @param response              Response object to update
	 * @param chatRequestContext    TODO
	 * @param showedDocuments       TODO
	 * @return Updated GeboChatResponse
	 * @throws LLMConfigException if a configuration error occurs
	 */
	protected GeboChatResponse callChatClient(IGConfigurableChatModel configurableChatModel, final Prompt prompt,
			final KBContext context, final GeboChatRequest request, final GeboChatResponse response,
			IChatRequestContext chatRequestContext, AIDocumentsSet showedDocuments) throws LLMConfigException {

		ChatResponse chatresponse = configurableChatModel.response(prompt, chatRequestContext);
		AssistantMessage callResponseObject = chatresponse.getResult().getOutput();
		String responseText = callResponseObject.getText();
		response.setQueryResponse(responseText);
		response.setCalledFunctions(context.getCalledFunctions());

		response.setDocumentsRef(showedDocuments != null ? GResponseDocumentRef.from(showedDocuments) : List.of());
		return response;
	}

	/**
	 * Calls the chat client for templated responses and updates the associated
	 * GeboTemplatedChatResponse.
	 *
	 * @param configurableChatModel Configurable chat model
	 * @param prompt                Chat prompt
	 * @param context               Knowledge base context
	 * @param request               Request object
	 * @param response              Response object to update
	 * @param chatRequestContext    TODO
	 * @param rt                    Class of the response type
	 * @return Updated GeboTemplatedChatResponse
	 * @throws LLMConfigException if a configuration error occurs
	 */
	protected <ResponseType> GeboTemplatedChatResponse<ResponseType> callTemplatedChatClient(
			IGConfigurableChatModel configurableChatModel, final Prompt prompt, final KBContext context,
			final GeboChatRequest request, final GeboTemplatedChatResponse<ResponseType> response,
			IChatRequestContext chatRequestContext, Class<ResponseType> rt) throws LLMConfigException {

		if (rt.equals(String.class)) {
			ChatResponse chatresponse = configurableChatModel.response(prompt, chatRequestContext);
			AssistantMessage callResponseObject = chatresponse.getResult().getOutput();
			String responseText = callResponseObject.getText();
			response.setQueryResponse((ResponseType) responseText);
		} else {
			ResponseType entityEntry = (ResponseType) configurableChatModel.structuredResponse(prompt,
					chatRequestContext, rt);
			response.setQueryResponse(entityEntry);
		}
		return response;
	}

	/**
	 * Streams chat response and finalizes the GUserChatContext update when
	 * finished.
	 *
	 * @param configurableChatModel Configurable chat model
	 * @param prompt                Chat prompt
	 * @param context               Knowledge base context
	 * @param request               Request object
	 * @param response              Response object to update
	 * @param chatRequestContext    TODO
	 * @param showedDocuments       TODO
	 * @param docrefs               List of document references
	 * @param docs                  List of documents
	 * @return A Flux of GeboChatMessageEnvelope representing the streamed messages
	 * @throws LLMConfigException if a configuration error occurs
	 */
	protected Flux<GeboChatMessageEnvelope> streamChatClient(IGConfigurableChatModel configurableChatModel,
			final Prompt prompt, final KBContext context, final GeboChatRequest request,
			final GeboChatResponse response, IChatRequestContext chatRequestContext, boolean chatHistoryConsolidation,
			int historySizeTarget, AIDocumentsSet showedDocuments) throws LLMConfigException {

		try {
			Flux<ChatResponse> res = configurableChatModel.streamResponse(prompt, chatRequestContext);
			return composeFlux(res, context, request, response, chatRequestContext.getToolsContext(),
					chatHistoryConsolidation, historySizeTarget, configurableChatModel, showedDocuments);
		} catch (Throwable th) {
			LOGGER.error("", th);
			GUserMessage userMessage = GUserMessage.errorMessage("Error while streaming chat respose", th);
			return Flux.just(new GeboChatMessageEnvelope(userMessage))
					.concatWithValues(GeboChatMessageEnvelope.FINAL_MESSAGE);
		}

	}

	/**
	 * Composes a Flux of GeboChatMessageEnvelope from the streaming chat response.
	 *
	 * @param res                      Flux of chat responses
	 * @param context                  Knowledge base context
	 * @param request                  Request object
	 * @param response                 Response object to update
	 * @param userContext              User chat context to update
	 * @param toolsContext             Context for tools involved in chat
	 * @param chatHistoryConsolidation
	 * @param historySizeTarget
	 * @param configurableChatModel
	 * @param showedDocuments          TODO
	 * @param docrefs                  List of document references
	 * @return A Flux of GeboChatMessageEnvelope representing the whole stream
	 */
	protected Flux<GeboChatMessageEnvelope> composeFlux(Flux<ChatResponse> res, final KBContext context,
			final GeboChatRequest request, final GeboChatResponse response, final Map<String, Object> toolsContext,
			boolean chatHistoryConsolidation, int historySizeTarget, IGConfigurableChatModel configurableChatModel,
			AIDocumentsSet showedDocuments) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Beginning composeFlux(....)");
		}
		final List<GResponseDocumentRef> docrefs = showedDocuments != null ? GResponseDocumentRef.from(showedDocuments)
				: List.of();
		final Map<String, ToolCall> toolCalls = new HashMap<>();
		final StringBuffer buffer = new StringBuffer();
		final boolean skipThinkingMarkup = configurableChatModel.isApplyThinkingMarkupHandling();

		Mono<GeboChatMessageEnvelope> startFlux = Mono.fromSupplier(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending a GeboChatResponse opening content");
			}
			response.setCalledFunctions(context.getCalledFunctions());
			response.setDocumentsRef(docrefs);

			GeboChatMessageEnvelope<GeboChatResponse> startEnvelope = new GeboChatMessageEnvelope<GeboChatResponse>();
			startEnvelope.setContent(response);
			return startEnvelope;
		});

		Flux<GeboChatMessageEnvelope> bodyFlux = res.map(x -> {

			GeboChatMessageEnvelope<String> envelope = new GeboChatMessageEnvelope<String>();
			GeboChatMessageEnvelope returned = envelope;
			final StringBuffer contentSegment = new StringBuffer("");
			if (x != null && x.getResults() != null && !x.getResults().isEmpty()) {
				for (Generation rs : x.getResults()) {
					if (rs.getOutput() != null) {
						MessageType type = rs.getOutput().getMessageType();

						rs.getOutput().getToolCalls().forEach(tc -> {
							toolCalls.put(tc.id(), tc);
						});

						String text = rs.getOutput().getText();
						if (text != null) {
							contentSegment.append(text);
						}
						List<Media> medias = rs.getOutput().getMedia();
						if (medias != null && !medias.isEmpty()) {
							for (Media media : medias) {
								LLMGeneratedResource generatedResource;
								try {
									generatedResource = this.chatStorageAreaService.addMedia(media,
											request.getUserChatContextCode());
									response.getGeneratedResources().add(generatedResource);
								} catch (Throwable e) {
									LOGGER.error("Error receiving media", e);
								}

							}
						}
					}
				}
			}
			String thisText = contentSegment.toString();
			buffer.append(thisText);
			if (!skipThinkingMarkup) {
				envelope.setContent(thisText);
			} else if (ClientChatCallUtil.isAfterThinking(buffer.toString())) {
				envelope.setContent(thisText);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending a String content:" + contentSegment.toString());
			}

			return returned;
		}).onErrorResume(exc -> {
			final String msg = "Error while streaming chat respose";
			LOGGER.error(msg, exc);
			GeboChatMessageEnvelope<GUserMessage> exceptionEnvelope = new GeboChatMessageEnvelope<GUserMessage>();
			GUserMessage userMessage = GUserMessage.errorMessage(msg, exc);			
			exceptionEnvelope.setContent(userMessage);
			return Flux.just(exceptionEnvelope);
		}).filter(x -> {
			return x.getContentObjectType() != null && x.getContent() != null && x.getContent() != null
					&& x.getContent().toString().trim().length() > 0;
		});
		Mono<GeboChatMessageEnvelope> trailingFlux = Mono.fromSupplier(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending a GeboChatResponse trailing content with lastMessage: true");
			}
			String responseText = buffer.toString();
			response.setThinkingOutputs(ClientChatCallUtil.extractThinking(responseText));
			response.setQueryResponse(ClientChatCallUtil.removeThinking(responseText));
			List<CalledFunction> calls = context.getCalledFunctions();
			if (calls == null || calls.isEmpty()) {
				calls = new ArrayList<>(toCalledFunctions(toolCalls.values()));
			}
			response.setCalledFunctions(calls);
			response.setDocumentsRef(docrefs);
			GeboChatMessageEnvelope<GeboChatResponse> finalEnvelope = new GeboChatMessageEnvelope<GeboChatResponse>();
			finalEnvelope.setContent(response); // Use the accumulated text
			finalEnvelope.setLastMessage(true);
			try {

				this.chatSessionLifecycleService.endRequest(request, response);
			} catch (Throwable th) {
				LOGGER.error("Error saving user context", th);
			} finally {
				LLMtInteractionContextThreadLocal.Context.remove();
			}
			return finalEnvelope;
		});
		Flux<GeboChatMessageEnvelope> responseFlux = startFlux.concatWith(bodyFlux).concatWith(trailingFlux)
				.concatWithValues(GeboChatMessageEnvelope.FINAL_MESSAGE);
		responseFlux.doOnComplete(() -> {
			try {
				this.chatSessionLifecycleService.chatRequestCompleted(request, configurableChatModel);
			} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
				LOGGER.error("Error closing response flux with chatSessionLifecycle code", e);
			}
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End composeFlux(....)");
		}
		return responseFlux;
	}

	/**
	 * Converts a collection of ToolCall objects to a list of CalledFunction
	 * objects.
	 *
	 * @param values Collection of ToolCall objects
	 * @return List of CalledFunction objects
	 */
	protected List<CalledFunction> toCalledFunctions(Collection<ToolCall> values) {
		List<CalledFunction> out = new ArrayList<>();
		for (ToolCall toolCall : values) {
			CalledFunction cf = new CalledFunction();
			cf.setFunctionName(toolCall.name());
			cf.setParamsDescription(List.of(toolCall.arguments()));
			out.add(cf);
		}
		return out;
	}

	public List<GKnowledgeBase> getVisibleKnowledgeBases() {

		return this.knowledgeBaseSecurityService.allVisibleKnowledgebases();
	}

	@Override
	public GeboChatResponse chat(String overriddenPrompt, LLMChatRequestResources requestResources,
			GeboChatResponse response, IGConfigurableChatModel chatModel) throws GeboChatException, LLMConfigException {
		KBContext kbcontext = new KBContext();
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);

		return callChatClient(chatModel, new Prompt(overriddenPrompt), kbcontext, requestResources.getCurrentRequest(),
				response, requestResources.createChatRequestContext(), null);
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamChat(String overriddenPrompt, LLMChatRequestResources requestResources,
			GeboChatResponse response, IGConfigurableChatModel chatModel) throws GeboChatException, LLMConfigException {
		KBContext kbcontext = new KBContext();
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);
		int tokensLength = requestResources.getTokensSize();
		final int contextWindow = chatModel.getContextLength();
		boolean shrink = tokensLength > contextWindow / 2;
		int targetSize = shrink ? contextWindow / 3 : 0;

		return streamChatClient(chatModel, new Prompt(overriddenPrompt), kbcontext, requestResources.getCurrentRequest(),
				response, requestResources.createChatRequestContext(), shrink, targetSize, null);
	}
}