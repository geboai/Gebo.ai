/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfoData;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGGenericalChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.model.DocumentMetaInfos;
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
	protected final static ObjectMapper mapper = new ObjectMapper(); // JSON object mapper for
																		// serialization/deserialization
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass()); // Logger for logging events

	final protected IGChatModelRuntimeConfigurationDao chatModelConfigurations; // DAO for fetching chat model
																				// configurations

	final protected IGToolCallbackSourceRepositoryPattern callbacksRepoPattern; // Repository pattern for tool callbacks

	final protected IGPersistentObjectManager persistenceManager; // Manager for handling persistence operations

	final protected GUserChatContextRepository userContextRepository; // Repository for user chat context data
	final protected GeboChatPromptsConfigs promptConfigs;
	final protected IGPromptConfigDao promptsDao;

	final protected InteractionsContextService interactionsContext;

	final protected IGSecurityService securityService;

	final protected IGChatResponseParsingFixerServiceRepository fixerServiceRepository;

	/**
	 * Inner class representing a system message containing document data.
	 */
	protected static class DocumentMessage extends SystemMessage {

		/**
		 * Constructs a DocumentMessage based on the provided Document.
		 *
		 * @param document The document containing data to be rendered in a message
		 */
		protected DocumentMessage(Document document) {
			super(renderData(document));
		}

		/**
		 * Renders document data into a string format suitable for a message.
		 *
		 * @param document The document to be rendered
		 * @return A string representation of the document's data
		 */
		private static String renderData(Document document) {
			StringBuffer data = new StringBuffer();
			if (document.getMetadata() != null) {
				String id = document.getId();
				String title = (String) document.getMetadata().get(DocumentMetaInfos.TITLE);
				String subtitle = (String) document.getMetadata().get(DocumentMetaInfos.SUBTITLE);
				// Metadata includes categorization and cataloging criteria
				String metadata = (String) document.getMetadata().get(DocumentMetaInfos.GEBO_EMBEDDING_METADATA);
				String code = (String) document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
				Object page = document.getMetadata().get(DocumentMetaInfos.CONTENT_PAGE);

				Map<String, Object> meta = new LinkedHashMap<>();
				if (id != null)
					meta.put("fragment-id", id); // Always useful
				if (code != null)
					meta.put("content-code", code);
				if (title != null)
					meta.put("title", title);
				if (subtitle != null)
					meta.put("subtitle", subtitle);
				if (metadata != null)
					meta.put("tags", metadata);
				if (page != null)
					meta.put("page_hint", page);

				// Emit a block that starts with ===META=== and ends with ===ENDMETA===.
				if (!meta.isEmpty()) {
					data.append("===META===\n");
					meta.forEach((k, v) -> {
						if (v != null && !v.toString().isBlank())
							data.append(k).append(": ").append(v).append('\n');
					});
					data.append("===ENDMETA===\n\n"); // Blank line separates the body
				}
			}
			if (document.getText() != null) {
				data.append("====BEGIN-CONTENT====\n");
				data.append(document.getText());
				data.append("\n");
				data.append("====END-CONTENT====\n\n");
			}
			return data.toString();
		}
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
	 * Constructs a list of Message objects from chat interactions and documents.
	 *
	 * @param messages List of chat interactions
	 * @param docs     List of documents
	 * @return A list of constructed Message objects
	 */
	protected List<Message> getMessages(List<ChatInteractions> messages, List<Document> docs) {

		List<Message> message_list = new ArrayList<>();
		if (messages != null) {
			for (ChatInteractions chatInteraction : messages) {
				GeboChatRequest request = chatInteraction.getRequest();
				if (request != null) {
					UserMessage _request = new UserMessage(request.getQuery());
					message_list.add(_request);
				}
				GeboTemplatedChatResponse response = chatInteraction.getResponse();
				if (response != null) {
					AssistantMessage _response = new AssistantMessage(stringhify(response.getQueryResponse()));
					message_list.add(_response);
				}
			}
		}
		if (docs != null) {
			for (Document document : docs) {
				message_list.add(new DocumentMessage(document));
			}
		}
		return message_list;
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
	 * @param messages              List of chat interactions
	 * @param docrefs               List of document references
	 * @param docs                  List of documents
	 * @return Updated GeboChatResponse
	 * @throws LLMConfigException if a configuration error occurs
	 */
	protected GeboChatResponse callChatClient(IGConfigurableChatModel configurableChatModel, final Prompt prompt,
			final KBContext context, final GeboChatRequest request, final GeboChatResponse response,
			final List<ChatInteractions> messages, final List<GResponseDocumentRef> docrefs, List<Document> docs)
			throws LLMConfigException {
		ChatClient client = configurableChatModel.getChatClient();
		ChatResponse chatresponse = client.prompt(prompt).user(request.getQuery()).messages(getMessages(messages, docs))
				.call().chatResponse();
		AssistantMessage callResponseObject = chatresponse.getResult().getOutput();
		String responseText = callResponseObject.getText();
		response.setQueryResponse(responseText);
		response.setCalledFunctions(context.getCalledFunctions());
		response.setDocumentsRef(docrefs);
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
	 * @param messages              List of chat interactions
	 * @param docrefs               List of document references
	 * @param docs                  List of documents
	 * @param responseType          Class of the response type
	 * @return Updated GeboTemplatedChatResponse
	 * @throws LLMConfigException if a configuration error occurs
	 */
	protected <ResponseType> GeboTemplatedChatResponse<ResponseType> callTemplatedChatClient(
			IGConfigurableChatModel configurableChatModel, final Prompt prompt, final KBContext context,
			final GeboChatRequest request, final GeboTemplatedChatResponse<ResponseType> response,
			final List<ChatInteractions> messages, final List<GResponseDocumentRef> docrefs, List<Document> docs,
			Class<ResponseType> responseType) throws LLMConfigException {
		ChatClient client = configurableChatModel.getChatClient();
		if (responseType.equals(String.class)) {
			ChatResponse chatresponse = client.prompt(prompt).user(request.getQuery())
					.messages(getMessages(messages, docs)).call().chatResponse();
			AssistantMessage callResponseObject = chatresponse.getResult().getOutput();
			String responseText = callResponseObject.getText();
			response.setQueryResponse((ResponseType) responseText);
		} else {
			ResponseType entityEntry = client.prompt(prompt).user(request.getQuery())
					.messages(getMessages(messages, docs)).call().entity(responseType);
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
	 * @param userContext           User chat context to update
	 * @param messages              List of chat interactions
	 * @param contextdocs
	 * @param docrefs               List of document references
	 * @param docs                  List of documents
	 * @return A Flux of GeboChatMessageEnvelope representing the streamed messages
	 * @throws LLMConfigException if a configuration error occurs
	 */
	protected Flux<GeboChatMessageEnvelope> streamChatClient(IGConfigurableChatModel configurableChatModel,
			final Prompt prompt, final KBContext context, final GeboChatRequest request,
			final GeboChatResponse response, final GUserChatContext userContext, final List<ChatInteractions> messages,
			List<Document> contextdocs) throws LLMConfigException {

		ChatClient client = configurableChatModel.getChatClient();
		final Map<String, Object> toolsContext = ToolCallbackDeclarationUtil.newToolContextEnvironment(context);
		try {

			final List<Document> docs = request.getDocuments() != null ? request.getDocuments().aiDocumentsList()
					: List.of();
			ArrayList<Document> allDocs = new ArrayList<>(docs);
			if (contextdocs != null) {
				allDocs.addAll(contextdocs);
			}

			Flux<ChatResponse> res = client.prompt(prompt).user(request.getQuery())
					.messages(getMessages(messages, allDocs)).toolContext(toolsContext).stream().chatResponse();
			return composeFlux(res, context, request, response, userContext, toolsContext);
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
	 * @param res          Flux of chat responses
	 * @param context      Knowledge base context
	 * @param request      Request object
	 * @param response     Response object to update
	 * @param userContext  User chat context to update
	 * @param docrefs      List of document references
	 * @param toolsContext Context for tools involved in chat
	 * @return A Flux of GeboChatMessageEnvelope representing the whole stream
	 */
	protected Flux<GeboChatMessageEnvelope> composeFlux(Flux<ChatResponse> res, final KBContext context,
			final GeboChatRequest request, final GeboChatResponse response, final GUserChatContext userContext,
			Map<String, Object> toolsContext) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Beginning composeFlux(....)");
		}
		final List<GResponseDocumentRef> docrefs = request.getDocuments() != null
				? GResponseDocumentRef.from(request.getDocuments())
				: List.of();
		final Map<String, ToolCall> toolCalls = new HashMap<>();
		final StringBuffer buffer = new StringBuffer();
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
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending a String content:" + contentSegment.toString());
			}
			envelope.setContent(contentSegment.toString());
			buffer.append(envelope.getContent());
			return returned;
		}).onErrorResume(exc -> {
			GeboChatMessageEnvelope<GUserMessage> exceptionEnvelope = new GeboChatMessageEnvelope<GUserMessage>();
			GUserMessage userMessage = GUserMessage.errorMessage("Error while streaming chat respose", exc);
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
			response.setQueryResponse(responseText);
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
				List<ChatInteractions> interactions = userContext.getInteractions();
				if (interactions == null) {
					interactions = new ArrayList<ChatInteractions>();
				}
				ChatInteractions interaction = new ChatInteractions();
				interaction.setRequest(request);
				interaction.setResponse(response);
				interactions.add(interaction);
				userContext.setInteractions(interactions);
				userContext.setDateModified(new Date());
				userContextRepository.save(userContext);
				LLMtInteractionContextThreadLocal.Context.remove();
			} catch (Throwable th) {
				LOGGER.error("Error saving user context", th);
			}
			return finalEnvelope;
		});
		Flux<GeboChatMessageEnvelope> responseFlux = startFlux.concatWith(bodyFlux).concatWith(trailingFlux)
				.concatWithValues(GeboChatMessageEnvelope.FINAL_MESSAGE);

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

	
}