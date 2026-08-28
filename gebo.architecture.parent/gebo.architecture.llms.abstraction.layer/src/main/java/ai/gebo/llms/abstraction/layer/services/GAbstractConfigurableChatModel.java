/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import io.micrometer.observation.ObservationRegistry;
import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * AI generated comments An abstract class representing a configurable chat
 * model. It defines a template for setting up chat models with configuration
 * and model type.
 * 
 * @param <ModelConfig>   The type parameter for model configuration extending
 *                        from GBaseChatModelConfig.
 * @param <ChatModelType> The type parameter for the chat model extending from
 *                        ChatModel.
 */
public abstract class GAbstractConfigurableChatModel<ModelConfig extends GBaseChatModelConfig, ChatModelType extends ChatModel>
		implements IGConfigurableChatModel<ModelConfig> {
	public static final String END_CONTEXT = "END_CONTEXT";
	public static final String BEGIN_CONTEXT = "BEGIN_CONTEXT";
	public static final String NEWLINE = "\r\n";
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	// Configuration for the chat model
	protected ModelConfig config = null;
	// Type of the chat model
	protected GChatModelType type = null;
	// Instance of the chat model
	protected ChatModelType model = null;
	// Client for interacting with the chat model
	protected ChatClient chatClient = null;
	protected final IGDocumentContentRendererProvider rendererFactory;
	protected final IGToolCallbackSourceRepositoryPattern toolCallbacksRepository;
	protected final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	/**
	 * The application's shared Micrometer {@link ObservationRegistry}, passed
	 * down to {@link #configureModel(GBaseChatModelConfig, GChatModelType, ToolCallingManager)}
	 * implementations so Spring AI's built-in chat-model observability actually
	 * reports into it, instead of each vendor building its own disconnected
	 * registry.
	 */
	protected final ObservationRegistry observationRegistry;
	protected static final ObjectMapper mapper = new ObjectMapper();

	protected abstract IGConfigurableChatModel cloneMeWithInjection();

	/**
	 * Default constructor for GAbstractConfigurableChatModel.
	 */
	public GAbstractConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
			IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
			IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry) {
		this.rendererFactory = rendererFactory;
		this.toolCallbacksRepository = toolCallbacksRepository;
		this.usageAdvisorFactory = usageAdvisorFactory;
		this.observationRegistry = observationRegistry;
	}

	/**
	 * Retrieves the code associated with the model configuration.
	 * 
	 * @return The code of the model configuration if available, otherwise null.
	 */
	@Override
	public String getCode() {

		return config != null ? config.getCode() : null;
	}

	/**
	 * Retrieves the description associated with the model configuration.
	 * 
	 * @return The description of the model configuration if available, otherwise
	 *         null.
	 */
	@Override
	public String getDescription() {

		return config != null ? config.getDescription() : null;
	}

	/**
	 * Retrieves the type of chat model.
	 * 
	 * @return The chat model type.
	 */
	@Override
	public GChatModelType getType() {

		return type;
	}

	/**
	 * Abstract method for configuring the chat model. Implementations must define
	 * how the model is configured.
	 * 
	 * @param config            The model configuration to use.
	 * @param type              The type of the chat model.
	 * @param toolsCallsManager TODO
	 * @return An instance of ChatModelType.
	 * @throws LLMConfigException If configuration fails.
	 */
	protected abstract ChatModelType configureModel(ModelConfig config, GChatModelType type,
			ToolCallingManager toolsCallsManager) throws LLMConfigException;

	/**
	 * Initializes the chat model with the given configuration and type.
	 * 
	 * @param config The model configuration to use.
	 * @param type   The type of the chat model.
	 * @throws LLMConfigException If initialization fails.
	 */
	@Override
	public void initialize(ModelConfig config, GChatModelType type) throws LLMConfigException {
		// Deterministic by default: a chat model with no temperature set is initialized at
		// temperature 0, so internal judgement calls (the ranking irrelevance filter,
		// routing, query rewriting) do not vary run to run. An explicit temperature in the
		// configuration is always respected.
		if (config != null && config.getTemperature() == null) {
			config.setTemperature(0.0d);
		}
		this.config = config;
		this.type = type;
		this.model = configureModel(config, type, null);
		Builder builder = ChatClient.builder(configureModel(config, type, null));
		this.chatClient = builder.defaultAdvisors(usageAdvisorFactory.create(config)).build();
	}

	@Override
	public List<ToolCallback> wrapTools(ReactiveIdentityUtil runAs, ToolCallsListener toolCallListener) {
		List<String> toolNames = config.getEnabledFunctions();
		if (toolNames == null)
			toolNames = List.of();
		List<ToolCallback> tools = this.toolCallbacksRepository.getTools(toolNames);

		List<ToolCallback> wrapped = new ArrayList<>();
		for (ToolCallback toolCallback : tools) {
			wrapped.add(new RunAsToolCallback(toolCallback, runAs, toolCallListener));
		}
		return wrapped;
	}

	public static List<ToolCallback> wrapTools(ReactiveIdentityUtil runAs, ToolCallsListener toolCallListener,
			List<String> toolNames, IGToolCallbackSourceRepositoryPattern toolCallbacksRepository) {

		if (toolNames == null)
			toolNames = List.of();
		List<ToolCallback> tools = toolCallbacksRepository.getTools(toolNames);

		List<ToolCallback> wrapped = new ArrayList<>();
		for (ToolCallback toolCallback : tools) {
			wrapped.add(new RunAsToolCallback(toolCallback, runAs, toolCallListener));
		}
		return wrapped;
	}

	/**
	 * Retrieves the current model configuration.
	 * 
	 * @return The current model configuration.
	 */
	@Override
	public ModelConfig getConfig() {

		return config;
	}

	/**
	 * Placeholder method for deleting the model.
	 * 
	 * @throws LLMConfigException If deletion fails.
	 */
	@Override
	public void delete() throws LLMConfigException {

	}

	/**
	 * Reconfigures the model with a new configuration.
	 * 
	 * @param config The new configuration to use.
	 * @throws LLMConfigException If reconfiguration fails.
	 */
	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		this.initialize(config, type);

	}

	/**
	 * Retrieves metadata for the chat model choice.
	 * 
	 * @return An instance of GBaseChatModelChoice or null if unavailable.
	 */
	protected GBaseChatModelChoice getChatModelMeta() {
		return config != null && config.getChoosedModel() != null ? ((GBaseChatModelChoice) config.getChoosedModel())
				: null;
	}

	/**
	 * Checks if the model supports function calls.
	 * 
	 * @return True if function calls are supported, otherwise false.
	 */
	@Override
	public boolean isSupportsFunctionsCall() {
		GBaseChatModelChoice choice = getChatModelMeta();
		if (choice != null && choice.getSupportsFunctionCalls() != null) {
			return choice.getSupportsFunctionCalls();
		}
		return IGConfigurableChatModel.super.isSupportsFunctionsCall();
	}

	/**
	 * Checks if the model supports structured output.
	 * 
	 * @return True if structured output is supported, otherwise false.
	 */
	@Override
	public boolean isSupportsStructuredOutput() {
		GBaseChatModelChoice choice = getChatModelMeta();
		if (choice != null && choice.getSupportsStructuredOutput() != null) {
			return choice.getSupportsStructuredOutput();
		}
		return IGConfigurableChatModel.super.isSupportsStructuredOutput();
	}

	/**
	 * Retrieves the context length for the chat model. Determines the length from
	 * configuration or model choice metadata if not explicitly set.
	 * 
	 * @return The context length, defaulting to 8192 if unspecified.
	 */
	@Override
	public int getContextLength() {
		Integer contextLength = null;
		if (getConfig() != null) {
			contextLength = getConfig().getContextLength();
			if (contextLength == null || contextLength.intValue() == 0) {
				if (getConfig().getChoosedModel() != null
						&& getConfig().getChoosedModel() instanceof GBaseModelChoice choice) {
					contextLength = choice.getContextLength();
					if (contextLength == null || contextLength.intValue() == 0) {
						if (choice.getMetaInfos() != null) {
							contextLength = choice.getMetaInfos().getContextLength();
						}
					}
				} else {
					LOGGER.error("No model choice valorized or wrong class for configurable chat model:" + getCode());
				}
			}

		} else {
			LOGGER.error("No config value for configurable model!!");
		}
		Integer generated = config.getMaxGeneratedTokens();
		if (contextLength != null && generated != null) {
			int contextWindow = contextLength.intValue();
			int generatedTokens = generated.intValue();
			int realContextWindow = contextWindow - generatedTokens;
			if (realContextWindow > 0) {
				contextLength = realContextWindow;
			}
		}
		if (contextLength == null || contextLength.intValue() == 0)
			contextLength = 8192;
		return contextLength;
	}

	/**
	 * Retrieves the chat client associated with the model.
	 * 
	 * @return The chat client for interacting with the model.
	 */
	public ChatClient getChatClient() {
		return chatClient;
	}

	@Override
	public RequestSpec prepareCall(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatContext, ReactiveIdentityUtil runAs) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin prepareCall(" + prompt.getPromptUse() + ", ...,...)");
		}
		long tokens = 0;
		ChatClient client = getChatClient();
		// Here prompt, documents and consolidated history
		List<Message> messages = new ArrayList<Message>();
		SystemMessage systemMessage = this.createSystemMessage(prompt, params, chatContext);
		messages.add(systemMessage);
		if (prompt.getChatHistory() == null || prompt.getChatHistory() == ContextContentRequired.REQUIRED) {
			List<Message> history = this.createHistoryMessages(chatContext);
			messages.addAll(history);
		}
		UserMessage lastUserMessage = this.createLastUserMessage(prompt, params, chatContext);
		messages.add(lastUserMessage);
		if (LOGGER.isDebugEnabled()) {
			int index = 1;
			for (Iterator iterator = messages.iterator(); iterator.hasNext();) {

				Message message = (Message) iterator.next();
				MessageType msgtype = message.getMessageType();
				String _content = message.getText();
				LOGGER.debug("Message: " + index + " type: " + msgtype.name().toUpperCase());
				LOGGER.debug("<" + msgtype.name().toUpperCase() + ">");
				LOGGER.debug(_content);
				LOGGER.debug("</" + msgtype.name().toUpperCase() + ">");
				tokens += ITokensCountable.stringsTokensSize(_content);
				index++;
			}
		}

		ChatClientRequestSpec reqObject = client.prompt();

		if (prompt.getToolsCalling() == null || prompt.getToolsCalling() == ContextContentRequired.REQUIRED) {
			reqObject = reqObject.toolCallbacks(wrapTools(runAs, chatContext.getToolCallListener()));
		} else {
			reqObject = reqObject.toolCallbacks(List.of());
		}
		// chat histroy in user, assistant format
		reqObject = reqObject.messages(messages);
		// tools call environment
		Map<String, Object> toolContext = chatContext.getToolsContext();
		if (toolContext != null) {
			reqObject = reqObject.toolContext(toolContext);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End prepareCall(" + prompt.getPromptUse() + ", ...,...)");
		}
		return new RequestSpec(reqObject, tokens, config != null ? config.getCode() : "<<empty model>>");
	}

	protected List<Message> createCompleteHistory(IChatRequestContext chatContext) {
		List<Message> message_list = new ArrayList<>();
		List<IChatSessionEntry> interactions = chatContext.getInteractions();
		if (interactions != null) {
			for (IChatSessionEntry chatInteraction : interactions) {
				String request = chatInteraction.getUser();
				String assistant = chatInteraction.getAssistant();
				if (request != null) {
					UserMessage _request = new UserMessage(request);
					message_list.add(_request);
				}

				if (assistant != null) {
					AssistantMessage _response = new AssistantMessage(assistant);
					message_list.add(_response);
				}
			}
		}
		return message_list;
	}

	protected List<Message> createHistoryMessages(IChatRequestContext chatContext) {
		String consolidated = chatContext.getConsolidatedHistory();
		if (consolidated == null || consolidated.trim().length() == 0) {
			return this.createCompleteHistory(chatContext);
		} else {
			return this.createCompressedHistory(chatContext);
		}

	}

	protected final static String COMPRESSED_HISTORY_FIRST_MESSAGE_CHAT_TEMPLATE = "BEGIN_CONSOLIDATED_HISTORY\r\n{"
			+ IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM
			+ "}\r\nEND_CONSOLIDATED_HISTORY\\r\\nUSER-QUESTION={" + IChatRequestContext.USER_QUESTION_PROMPT_PARAM
			+ "}\r\n";

	protected List<Message> createCompressedHistory(IChatRequestContext chatContext) {
		List<Message> messages = new ArrayList<>();
		String consolidated = chatContext.getConsolidatedHistory();
		List<IChatSessionEntry> interactions = chatContext.getInteractions();
		for (int i = 0; i < interactions.size(); i++) {
			if (i == 0) {
				String user = interactions.get(0).getUser();
				String assistant = interactions.get(0).getAssistant();
				PromptTemplate template = new PromptTemplate(COMPRESSED_HISTORY_FIRST_MESSAGE_CHAT_TEMPLATE);
				template.add(IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM, consolidated);
				template.add(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, user != null ? user : "");
				UserMessage firstUserMessage = new UserMessage(template.render());
				AssistantMessage firstAssistantMessage = new AssistantMessage(assistant != null ? assistant : "");
				messages.add(firstUserMessage);
				messages.add(firstAssistantMessage);
			} else {
				String user = interactions.get(0).getUser();
				String assistant = interactions.get(0).getAssistant();
				UserMessage userMessage = new UserMessage(user != null ? user : "");
				AssistantMessage assistantMessage = new AssistantMessage(assistant != null ? assistant : "");
				messages.add(userMessage);
				messages.add(assistantMessage);
			}
		}
		return messages;
	}

	protected UserMessage createLastUserMessage(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatContext) {
		String userTemplate = prompt.getUserPromptTemplate();
		PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
		Map<String, Object> allParams = new HashMap<>(params);
		StringBuffer allDocs = new StringBuffer();

		if (params.containsKey(IChatRequestContext.DOCUMENTS_PROMPT_PARAM)) {
			allDocs.append(createDocumentsRendering(params.get(IChatRequestContext.DOCUMENTS_PROMPT_PARAM)));
		}
		if (prompt.getContextDocuments() == null || prompt.getContextDocuments() == ContextContentRequired.REQUIRED) {
			List<Document> documents = chatContext.getDocuments();
			if (documents != null && !documents.isEmpty()) {
				allDocs.append(createDocumentsRendering(documents));
			}
		}
		String docsFragment = allDocs.length() > 0
				? BEGIN_CONTEXT + NEWLINE + (allDocs.toString()) + END_CONTEXT + NEWLINE
				: "";
		allParams.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, docsFragment);
		allParams.put(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, chatContext.getActualUserRequest());
		allParams.put(IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM,
				chatContext.getConsolidatedHistory() != null ? chatContext.getConsolidatedHistory() : "");
		String content = promptTemplate.render(allParams);
		return new UserMessage(content);
	}

	protected String createDocumentsRendering(Object object) {
		if (object instanceof String text) {
			return text;
		}
		if (object instanceof Collection collection) {
			if (collection.isEmpty())
				return "";
			StringBuffer documentsBuffer = new StringBuffer();

			for (Object thisObject : collection) {
				String content = render(thisObject);
				documentsBuffer.append(content);
				documentsBuffer.append(NEWLINE);
			}

			return documentsBuffer.toString();
		}

		return render(object);
	}

	protected String render(Object doc) {
		IGDocumentContentRenderer<Object> handler = this.rendererFactory.get(doc);
		return handler.render(doc);
	}

	protected SystemMessage createSystemMessage(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatContext) {
		String systemTemplate = prompt.getSystemPromptTemplate();
		PromptTemplate template = new PromptTemplate(systemTemplate);
		String content = template.render(params);
		return new SystemMessage(content);
	}

	@Override
	public Flux<ChatResponse> streamResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		return runAs.doRunAsWithReturnAndException(() -> {
			long timestamp = 0l;
			if (LOGGER.isDebugEnabled()) {
				timestamp = System.currentTimeMillis();
				LOGGER.debug("Begin streamResponse(" + promptTemplate.getPromptUse() + ", ...,...)" + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
			}
			RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs);
			Flux<ChatResponse> res = reqObject.getRequestSpec().stream().chatResponse()
					.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End streamResponse(" + promptTemplate.getPromptUse() + ", ...,...)" + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
				logPerformances(reqObject, timestamp);
			}
			return res;
		});
	}

	private void logPerformances(RequestSpec reqObject, long timestamp) {
		if (LOGGER.isDebugEnabled()) {
			long deltaTime = System.currentTimeMillis() - timestamp;
			long tokens = reqObject.getTokensCount();
			double dtokens = tokens;
			double dtime = Math.round(((double) deltaTime) / 10.0) / 100.0;
			double tokenSec = dtime != 0.0 ? dtokens / dtime : 0.0;
			LOGGER.debug("LLM PERFORMANCES: " + reqObject.getModelCode() + " processed: " + tokens + " (tok) in "
					+ dtime + " (sec) => input processing: " + tokenSec + " (token/sec) ");
		}
	}

	@Override
	public Flux<String> streamStringResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		return runAs.doRunAsWithReturnAndException(() -> {
			long timestamp = 0l;
			if (LOGGER.isDebugEnabled()) {
				timestamp = System.currentTimeMillis();
				LOGGER.debug("Begin streamStringResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
			}

			RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End streamStringResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
				logPerformances(reqObject, timestamp);
			}

			return reqObject.getRequestSpec().stream().content().subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
		});
	}

	@Override
	public ChatResponse response(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin response(" + promptTemplate.getPromptUse() + ", ...,...) [session:"
					+ chatContext.getSessionID() + " request:" + chatContext.getRequestID() + " pipelineInfos:"
					+ chatContext.getPipelineInfos() + "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs);
		CallResponseSpec res = reqObject.getRequestSpec().call();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End response(" + promptTemplate.getPromptUse() + ", ...,...)  [session:"
					+ chatContext.getSessionID() + " request:" + chatContext.getRequestID() + " pipelineInfos:"
					+ chatContext.getPipelineInfos() + "]");
			logPerformances(reqObject, timestamp);
		}
		return res.chatResponse();
	}

	@Override
	public <ResponseType> ResponseType structuredResponse(GPromptTemplateConfig promptTemplate,
			Map<String, Object> params, IChatRequestContext chatContext, Class<ResponseType> rt)
			throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin structuredResponse(" + promptTemplate.getPromptUse() + ", ...,...,"
					+ (rt != null ? rt.getName() : "NULL") + ")  [session:" + chatContext.getSessionID() + " request: "
					+ chatContext.getRequestID() + " pipelineInfos: " + chatContext.getPipelineInfos() + "]");
		}
		ChatClient client = getChatClient();
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs);
		CallResponseSpec res = reqObject.getRequestSpec().call();
		ResponseType data = res.entity(rt);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End structuredResponse(" + promptTemplate.getPromptUse() + ", ...,...,"
					+ (rt != null ? rt.getName() : "NULL") + ") " + "[session:" + chatContext.getSessionID()
					+ " request: " + chatContext.getRequestID() + " pipelineInfos: " + chatContext.getPipelineInfos()
					+ "]" + " returned " + data);
			logPerformances(reqObject, timestamp);

		}

		return data;
	}

	@Override
	public <ResponseType> ResponseType structuredResponse(GPromptTemplateConfig promptTemplate,
			Map<String, Object> params, IChatRequestContext chatContext, Class<ResponseType> rt,
			BeanOutputConverter<ResponseType> outputConverter) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin structuredResponse(" + promptTemplate.getPromptUse() + ", ...,...,"
					+ (rt != null ? rt.getName() : "NULL") + ",..) " + "[session:" + chatContext.getSessionID()
					+ " request: " + chatContext.getRequestID() + " pipelineInfos: " + chatContext.getPipelineInfos()
					+ "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs);
		CallResponseSpec res = reqObject.getRequestSpec().call();
		ResponseType data = res.entity(outputConverter);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End structuredResponse(" + promptTemplate.getPromptUse() + ", ...,...,"
					+ (rt != null ? rt.getName() : "NULL") + ",..) " + "[session:" + chatContext.getSessionID()
					+ " request: " + chatContext.getRequestID() + " pipelineInfos: " + chatContext.getPipelineInfos()
					+ "]" + " returned:" + data);
			logPerformances(reqObject, timestamp);
		}
		return data;
	}

	@Override
	public String textResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin textResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
					+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
					+ chatContext.getPipelineInfos() + "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs);
		CallResponseSpec response = reqObject.getRequestSpec().call();
		String out = response.content();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End textResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
					+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
					+ chatContext.getPipelineInfos() + "]" + " returned: " + out);
			logPerformances(reqObject, timestamp);
		}
		return out;
	}

	@Override
	public <T> T doWithChatClient(UseChatClient<T> chatClientCalling) throws LLMConfigException {

		return chatClientCalling.call(chatClient);
	}

	@Override
	public <T> T doWithChatModel(UseChatModel<T> chatModelCalling) throws LLMConfigException {

		return chatModelCalling.call(model);
	}

	@Override
	public IGConfigurableChatModel<ModelConfig> cloneWithOptions(String codePrefix,
			ChatModelConfigOptions configOptions) throws LLMConfigException {
		try {
			String asString = mapper.writeValueAsString(config);
			ModelConfig modelConfigClone = (ModelConfig) mapper.readValue(asString.getBytes(), config.getClass());
			modelConfigClone.setCode(codePrefix + modelConfigClone.getCode());
			modelConfigClone.setEnabledFunctions(
					configOptions.getToolsName() != null ? configOptions.getToolsName() : List.of());
			if (configOptions.getTemperature() != null) {
				modelConfigClone.setTemperature(configOptions.getTemperature());
			}
			if (configOptions.getThinking() != null) {
				modelConfigClone.setThinking(configOptions.getThinking());
			}
			if (configOptions.getTopP() != null) {
				modelConfigClone.setTopP(configOptions.getTopP());
			}
			IGConfigurableChatModel handler = cloneMeWithInjection();
			if (configOptions.getToolCallingManager() == null) {
				handler.initialize(modelConfigClone, type);
			} else if (handler instanceof GAbstractConfigurableChatModel configurableChatModel) {
				configurableChatModel.config = modelConfigClone;
				configurableChatModel.type = this.type;
				configurableChatModel.model = configurableChatModel.configureModel(modelConfigClone, type,
						configOptions.getToolCallingManager());
				configurableChatModel.chatClient = ChatClient
						.builder(configurableChatModel.configureModel(modelConfigClone, type,
								configOptions.getToolCallingManager()))
						.defaultAdvisors(usageAdvisorFactory.create(modelConfigClone)).build();
			} else
				throw new IllegalStateException(
						"The actual configurable chat model is not an GAbstractConfigurableChatModel");

			return handler;
		} catch (JacksonException e) {
			throw new LLMConfigException("Cannot clone model correctly");
		}
	}

	public static List<ToolCallback> wrapTools(ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener,
			List<ToolCallback> additionalTools) {
		List<ToolCallback> wrapped = new ArrayList<ToolCallback>();
		for (ToolCallback toolCallback : additionalTools) {
			wrapped.add(new RunAsToolCallback(toolCallback, runAs, callBacksListener));
		}
		return wrapped;
	}

}