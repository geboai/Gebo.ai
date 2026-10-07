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
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
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
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
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
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
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
	final protected IGTextToSpeechModelRuntimeConfigurationDao ttsModelsDao;
	final protected IGTranscriptModelRuntimeConfigurationDao transcriptModelsDao;
	final protected IGSecurityAuditLoggerService securityAuditLoggerService;
	final static JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();

	// Metadata-only by design: model/provider/outcome/latency, never prompt or
	// response content - logging chat bodies would be a PII/secret-leak surface.
	// Takes an already-created SecurityEvent (never calls newSecurityEvent()
	// itself) so newSecurityEvent()'s caller-stack capture points at the real
	// invocation entry point, not at this shared helper.
	private void logChatInvocationEvent(SecurityEvent event, IGConfigurableChatModel configurableChatModel,
			long startMillis, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_INVOCATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_INVOCATION);
		event.setAction(SecurityAuditTaxonomy.Action.LLM_INVOKE_CHAT);
		event.setResourceId(configurableChatModel != null ? configurableChatModel.getCode() : null);
		event.setOutcome(outcome);
		if (configurableChatModel != null && configurableChatModel.getType() != null) {
			event.getDetails().put("provider", configurableChatModel.getType().getCode());
		}
		event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
		securityAuditLoggerService.log(event);
	}

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
		} catch (JacksonException e) {
			LOGGER.error("Exception stringhifying a queryResponse", e);
			return "";
		}
	}

	/**
	 * The quotations of an answer, checked against what the model is given to answer:
	 * the documents of the call (the prompt's documents parameter, the request's
	 * documents when the prompt takes them) and the results of the tools it calls.
	 */
	protected DeepSearchQuotations answerQuotations(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatRequestContext) {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		final List<Document> documents = new ArrayList<>();
		final Object given = params != null ? params.get(IChatRequestContext.DOCUMENTS_PROMPT_PARAM) : null;
		if (given instanceof AIDocumentsSet set) {
			documents.addAll(set.aiDocumentsList());
		} else if (given instanceof Collection<?> collection) {
			for (Object item : collection) {
				if (item instanceof Document document) {
					documents.add(document);
				}
			}
		}
		if (chatRequestContext != null && (prompt == null || prompt.getContextDocuments() == null
				|| prompt.getContextDocuments() == ContextContentRequired.REQUIRED)) {
			final List<Document> requestDocuments = chatRequestContext.getDocuments();
			if (requestDocuments != null) {
				documents.addAll(requestDocuments);
			}
		}
		quotations.addSources(documents);
		if (chatRequestContext != null) {
			quotations.addToolResults(chatRequestContext.getToolCallListener());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Answer quotations checked against " + documents.size() + " document(s)"
					+ (chatRequestContext != null && chatRequestContext.getToolCallListener() != null
							? " and the tools' results"
							: ""));
		}
		return quotations;
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
	protected GeboChatResponse callChatClient(IGConfigurableChatModel configurableChatModel,
			final GPromptTemplateConfig prompt, final KBContext context, final GeboChatRequest request,
			final GeboChatResponse response, IChatRequestContext chatRequestContext, AIDocumentsSet showedDocuments)
			throws LLMConfigException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		try {
			final DeepSearchQuotations quotations = answerQuotations(prompt, Map.of(), chatRequestContext);
			ChatResponse chatresponse = configurableChatModel.response(prompt, Map.of(), chatRequestContext);
			AssistantMessage callResponseObject = chatresponse.getResult().getOutput();
			String responseText = callResponseObject.getText();
			// the quotations the standard way, checked against the answer's sources (best effort)
			response.setQueryResponse(quotations.render(responseText));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Answer with " + quotations.quotes().size() + " verified quotation(s)");
			}
			// the called functions are recorded into the response as the tools run (see
			// recordToolCalls(...))

			response.setDocumentsRef(showedDocuments != null ? GResponseDocumentRef.from(showedDocuments) : List.of());
			logChatInvocationEvent(event, configurableChatModel, startMillis, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return response;
		} catch (RuntimeException | LLMConfigException e) {
			logChatInvocationEvent(event, configurableChatModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Streams chat response and finalizes the GUserChatContext update when
	 * finished.
	 *
	 * @param configurableChatModel Configurable chat model
	 * @param prompt                Chat prompt
	 * @param params                TODO
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
			final GPromptTemplateConfig prompt, Map<String, Object> params, final KBContext context,
			final GeboChatRequest request, final GeboChatResponse response, IChatRequestContext chatRequestContext,
			boolean chatHistoryConsolidation, int historySizeTarget, AIDocumentsSet showedDocuments)
			throws LLMConfigException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		try {
			final DeepSearchQuotations quotations = answerQuotations(prompt, params, chatRequestContext);
			Flux<ChatResponse> res = configurableChatModel.streamResponse(prompt, params, chatRequestContext);
			Flux<GeboChatMessageEnvelope> composed = composeFlux(res, context, request, response,
					chatRequestContext.getToolsContext(), chatHistoryConsolidation, historySizeTarget,
					configurableChatModel, showedDocuments, quotations);
			// Logged once at stream completion/error (not per chunk) to avoid flooding
			// the audit log with one event per streamed token.
			return composed
					.doOnComplete(() -> logChatInvocationEvent(event, configurableChatModel, startMillis,
							SecurityAuditTaxonomy.Outcome.SUCCESS))
					.doOnError(err -> logChatInvocationEvent(event, configurableChatModel, startMillis,
							SecurityAuditTaxonomy.Outcome.FAILURE));
		} catch (Throwable th) {
			LOGGER.error("Error while streaming chat respose", th);
			logChatInvocationEvent(event, configurableChatModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE);
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
		return composeFlux(res, context, request, response, toolsContext, chatHistoryConsolidation, historySizeTarget,
				configurableChatModel, showedDocuments, null);
	}

	/**
	 * The same, the answer's quotations given the standard way as it streams and as it is
	 * saved (see {@link DeepSearchQuotations}); as the model wrote them when
	 * {@code quotations} is null.
	 */
	protected Flux<GeboChatMessageEnvelope> composeFlux(Flux<ChatResponse> res, final KBContext context,
			final GeboChatRequest request, final GeboChatResponse response, final Map<String, Object> toolsContext,
			boolean chatHistoryConsolidation, int historySizeTarget, IGConfigurableChatModel configurableChatModel,
			AIDocumentsSet showedDocuments, final DeepSearchQuotations quotations) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Beginning composeFlux(....)");
		}
		final List<GResponseDocumentRef> docrefs = showedDocuments != null ? GResponseDocumentRef.from(showedDocuments)
				: List.of();
		final StringBuffer buffer = new StringBuffer();
		final boolean skipThinkingMarkup = configurableChatModel.isApplyThinkingMarkupHandling();
		final DeepSearchQuotations.Streaming quoting = quotations != null ? quotations.streaming() : null;

		Mono<GeboChatMessageEnvelope> startFlux = Mono.fromSupplier(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending a GeboChatResponse opening content");
			}
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
			if (!skipThinkingMarkup || ClientChatCallUtil.isAfterThinking(buffer.toString())) {
				// a quotation still open is held until it closes
				envelope.setContent(quoting != null ? quoting.next(thisText) : thisText);
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
		// what the quotations rendering still holds once the answer ended
		Mono<GeboChatMessageEnvelope> heldFlux = Mono.fromSupplier(() -> {
			final String held = quoting != null ? quoting.rest() : "";
			if (held.isEmpty()) {
				return null;
			}
			GeboChatMessageEnvelope<String> envelope = new GeboChatMessageEnvelope<String>();
			envelope.setContent(held);
			return envelope;
		});
		Mono<GeboChatMessageEnvelope> trailingFlux = Mono.fromSupplier(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending a GeboChatResponse trailing content with lastMessage: true");
			}
			String responseText = buffer.toString();
			response.setThinkingOutputs(ClientChatCallUtil.extractThinking(responseText));
			if (quotations != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Streamed answer with " + quotations.quotes().size() + " verified quotation(s)");
				}
				// saved as it was streamed: the quotations the standard way
				response.setQueryResponse(quotations.render(ClientChatCallUtil.removeThinking(responseText)));
			} else {
				response.setQueryResponse(ClientChatCallUtil.removeThinking(responseText));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Streamed response carries " + response.getCalledFunctions().size()
						+ " recorded called function(s)");
			}
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
		Flux<GeboChatMessageEnvelope> responseFlux = startFlux.concatWith(bodyFlux).concatWith(heldFlux)
				.concatWith(trailingFlux)
				.concatWithValues(GeboChatMessageEnvelope.FINAL_MESSAGE);
		responseFlux = responseFlux.doOnComplete(() -> {
			try {
				this.chatSessionLifecycleService.chatRequestCompleted(request, configurableChatModel);
			} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
				LOGGER.error("Error closing response flux with chatSessionLifecycle code", e);
			}
		}).doFinally(signal -> this.chatSessionLifecycleService.releaseRequest(request));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End composeFlux(....)");
		}
		return responseFlux;
	}

	/**
	 * Makes the tools called while answering the request recorded into the response
	 * (its called functions, filled as the tools run). Within a chat pipeline the
	 * executor already owns the request recorder; a direct model chat has none, so the
	 * response being built here gets its own.
	 */
	protected void recordToolCalls(LLMChatRequestResources requestResources, GeboChatResponse response) {
		if (requestResources == null || response == null || requestResources.getToolCallsListener() != null) {
			return;
		}
		requestResources.setToolCallsListener(ToolCallsListener.appendingTo(response.getCalledFunctions()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Recording the tool calls of a direct chat request into its response");
		}
	}

	public List<GKnowledgeBase> getVisibleKnowledgeBases() {

		return this.knowledgeBaseSecurityService.allVisibleKnowledgebases();
	}

	@Override
	public GeboChatResponse chat(GPromptTemplateConfig overriddenPrompt, LLMChatRequestResources requestResources,
			GeboChatResponse response, IGConfigurableChatModel chatModel) throws GeboChatException, LLMConfigException {
		KBContext kbcontext = new KBContext();
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);
		recordToolCalls(requestResources, response);

		return callChatClient(chatModel, overriddenPrompt, kbcontext, requestResources.getCurrentRequest(), response,
				requestResources.createChatRequestContext(), null);
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamChat(GPromptTemplateConfig overriddenPrompt, Map<String, Object> params,
			LLMChatRequestResources requestResources, GeboChatResponse response, IGConfigurableChatModel chatModel)
			throws GeboChatException, LLMConfigException {
		KBContext kbcontext = new KBContext();
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);
		recordToolCalls(requestResources, response);
		int tokensLength = requestResources.getTokensSize();
		final int contextWindow = chatModel.getContextLength();
		boolean shrink = tokensLength > contextWindow / 2;
		int targetSize = shrink ? contextWindow / 3 : 0;

		AIDocumentsSet allDocuments = requestResources.allDocuments();
		return streamChatClient(chatModel, overriddenPrompt, params, kbcontext, requestResources.getCurrentRequest(),
				response, requestResources.createChatRequestContext(), shrink, targetSize, allDocuments);
	}
}