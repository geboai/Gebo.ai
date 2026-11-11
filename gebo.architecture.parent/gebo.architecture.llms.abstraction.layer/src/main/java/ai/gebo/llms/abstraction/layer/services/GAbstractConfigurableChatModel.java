/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
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
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
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
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
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
		implements IGConfigurableChatModel<ModelConfig>, IGProviderDealPricedModel {

	/**
	 * Prices this model by its provider deal; attached by the runtime DAO, null
	 * leaving it priced by the provider API only.
	 */
	private volatile IGProviderDealService providerDealService = null;

	@Override
	public void setProviderDealService(IGProviderDealService providerDealService) {
		this.providerDealService = providerDealService;
	}

	/**
	 * The price the provider deal covering this model's API key gives to its
	 * model code, else the provider API's one; read from an in-memory snapshot.
	 */
	@Override
	public GModelPricingConditions getPricingConditions() {
		return IGProviderDealPricedModel.dealOrProviderApiPricing(providerDealService, this);
	}

	public static final String END_CONTEXT = "END_CONTEXT";
	public static final String BEGIN_CONTEXT = "BEGIN_CONTEXT";
	public static final String NEWLINE = "\r\n";
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/** The logger of the static helpers, which have no runtime subclass to log as. */
	private static final Logger STATIC_LOGGER = LoggerFactory.getLogger(GAbstractConfigurableChatModel.class);
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
	 * down to {@link #configureModel(GBaseChatModelConfig, GChatModelType)}
	 * implementations so Spring AI's built-in chat-model observability actually
	 * reports into it, instead of each vendor building its own disconnected
	 * registry.
	 */
	protected final ObservationRegistry observationRegistry;
	protected static final ObjectMapper mapper = new ObjectMapper();
	/**
	 * The tools made for this use of the model (see
	 * {@link ChatModelConfigOptions#getAdditionalTools()}), declared with the repository
	 * ones when the configuration enables them.
	 */
	protected List<ToolCallback> additionalTools = List.of();
	/**
	 * The names of the tools this model declares: none on a configured model, those given
	 * to a clone by {@link #cloneWithOptions(String, ChatModelConfigOptions)}.
	 */
	protected List<String> toolNames = List.of();
	/** This model with no tools (see {@link #withoutTools()}), made the first time it is asked. */
	private volatile IGConfigurableChatModel<ModelConfig> withoutToolsModel = null;
	/**
	 * The share of what a model call's messages and tools' definitions leave of the
	 * context that its tools' results may take.
	 */
	public static final double TOOLS_ROOM_SHARE = 2.0d / 3.0d;
	/** The models already warned of having no known context length, warned once. */
	private static final java.util.Set<String> UNKNOWN_CONTEXT_LENGTH_WARNED = java.util.concurrent.ConcurrentHashMap
			.newKeySet();

	/**
	 * The models library the parameter rules of the model are read from (see
	 * {@link ModelParameterRules}); null leaves the requests as the configuration makes
	 * them.
	 */
	protected final IGModelsLibraryDao modelsLibrary;
	/**
	 * Fills the placeholders a template names and the caller did not: the time,
	 * the user, the conversation, the run (see {@link IGPromptContextProviders});
	 * null leaves a template with exactly what the caller passed.
	 */
	protected IGPromptContextProviders promptContextProviders = null;

	public void setPromptContextProviders(IGPromptContextProviders promptContextProviders) {
		this.promptContextProviders = promptContextProviders;
	}

	public IGPromptContextProviders getPromptContextProviders() {
		return promptContextProviders;
	}
	/**
	 * The parameter rules of the model the configuration names, resolved each time it is
	 * initialized; null when none covers it.
	 */
	protected ModelParameterRules parameterRules = null;

	protected abstract IGConfigurableChatModel cloneMeWithInjection();

	/**
	 * Default constructor for GAbstractConfigurableChatModel.
	 *
	 * @param modelsLibrary the library the model's parameter rules are read from, may be
	 *                      null
	 */
	public GAbstractConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
			IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
			IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry,
			IGModelsLibraryDao modelsLibrary) {
		this.rendererFactory = rendererFactory;
		this.toolCallbacksRepository = toolCallbacksRepository;
		this.usageAdvisorFactory = usageAdvisorFactory;
		this.observationRegistry = observationRegistry;
		this.modelsLibrary = modelsLibrary;
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
	 * @param config The model configuration to use.
	 * @param type   The type of the chat model.
	 * @return An instance of ChatModelType.
	 * @throws LLMConfigException If configuration fails.
	 */
	protected abstract ChatModelType configureModel(ModelConfig config, GChatModelType type)
			throws LLMConfigException;

	/**
	 * Initializes the chat model with the given configuration and type.
	 * 
	 * @param config The model configuration to use.
	 * @param type   The type of the chat model.
	 * @throws LLMConfigException If initialization fails.
	 */
	@Override
	public void initialize(ModelConfig config, GChatModelType type) throws LLMConfigException {
		this.type = type;
		this.parameterRules = parameterRulesOf(config, type);
		// Deterministic by default: a chat model with no temperature set is initialized at
		// temperature 0, so internal judgement calls (the ranking irrelevance filter,
		// routing, query rewriting) do not vary run to run - where the model takes a
		// temperature at all: the reasoning models refusing the sampling parameters fail
		// every request carrying one. An explicit temperature in the configuration is kept,
		// and sent where the model's parameter rules allow it. A model whose provider
		// recommends its default temperature (Gemini 3) is left at it.
		if (config != null && config.getTemperature() == null && samplingAllowed(config)) {
			if (parameterRules != null && parameterRules.keepsProviderDefaultSampling()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Chat model code=" + config.getCode()
							+ " has no temperature set and its parameter rules keep the provider default: none is sent");
				}
			} else {
				config.setTemperature(0.0d);
			}
		}
		this.config = config;
		final ModelConfig requestConfig = requestConfig(config);
		this.model = configureModel(requestConfig, type);
		Builder builder = ChatClient.builder(configureModel(requestConfig, type));
		// Priced through this model's getPricingConditions(), read when each call ends; a
		// call's answer read from its answer generation, not from a reasoning one; the
		// reasoning of every model round apart from the answer, every provider alike.
		this.chatClient = builder.defaultAdvisors(usageAdvisorFactory.create(config, this::getPricingConditions,
				this::getProviderId), new AnswerFirstGenerations.Advisor(this::isReasoningGeneration),
				thinkingNormalization()).build();
	}

	/**
	 * The parameter rules of the model the configuration names, from the models library;
	 * null when the library is absent, the model unknown or no rule covers it.
	 */
	protected ModelParameterRules parameterRulesOf(ModelConfig config, GChatModelType type) {
		if (config == null || config.getChoosedModel() == null || type == null)
			return null;
		final String modelId = config.getChoosedModel().getCode();
		ModelParameterRules live = null;
		try {
			live = liveParameterRules(config, type);
		} catch (RuntimeException e) {
			LOGGER.error("Cannot read what the models api told of chat model code=" + config.getCode()
					+ ", the models library rules apply alone", e);
		}
		if (modelsLibrary == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat model code=" + config.getCode() + " model=" + modelId
						+ " no models library, live parameter rules: " + live);
			}
			return live;
		}
		try {
			final ModelParameterRules library = modelsLibrary.findParameterRules(type.getProviderId(), modelId);
			final ModelParameterRules rules = ModelParameterRules.merge(live, library);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat model code=" + config.getCode() + " model=" + modelId + " provider="
						+ type.getProviderId() + " parameter rules: " + rules + " (live: " + live + ", library: "
						+ library + ")");
			}
			return rules;
		} catch (RuntimeException e) {
			LOGGER.error("Cannot read the parameter rules of chat model code=" + config.getCode()
					+ ", its requests are made without the library ones", e);
			return live;
		}
	}

	/**
	 * The parameter rules the provider's models api told for the chosen model, kept on
	 * the choice when the model was listed (e.g. the efforts and parameters OpenRouter
	 * publishes per model); null when the provider tells none, the default. They win
	 * over the models library where they say something, see
	 * {@link ModelParameterRules#merge(ModelParameterRules, ModelParameterRules)}.
	 */
	protected ModelParameterRules liveParameterRules(ModelConfig config, GChatModelType type) {
		return null;
	}

	/**
	 * Whether a request of the model may carry the sampling parameters, temperature and
	 * top_p: always, unless its parameter rules refuse them - outright, or while it
	 * reasons, i.e. unless the configuration turns the reasoning off with an effort
	 * {@code none} the model accepts.
	 */
	protected boolean samplingAllowed(ModelConfig config) {
		if (parameterRules == null)
			return true;
		final boolean reasoningOff = config != null && config.getThinking() == ChatModelThinkingOption.NO_THINKING
				&& parameterRules.acceptsEffort(ModelParameterRules.NO_REASONING_EFFORT);
		return parameterRules.samplingAllowed(reasoningOff);
	}

	/**
	 * The configuration the model's requests are made from: the given one, or a copy of
	 * it without the sampling parameters where the model's parameter rules refuse them.
	 * The given configuration is left as it is: it is the one saved, shown and copied.
	 */
	protected ModelConfig requestConfig(ModelConfig config) throws LLMConfigException {
		if (config == null || samplingAllowed(config) || (config.getTemperature() == null && config.getTopP() == null))
			return config;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Chat model code=" + config.getCode() + " sends neither its temperature ("
					+ config.getTemperature() + ") nor its top_p (" + config.getTopP() + ") at thinking "
					+ config.getThinking() + ": refused by " + parameterRules);
		}
		final ModelConfig withoutSampling = copyOf(config);
		withoutSampling.setTemperature(null);
		withoutSampling.setTopP(null);
		return withoutSampling;
	}

	@SuppressWarnings("unchecked")
	private ModelConfig copyOf(ModelConfig config) throws LLMConfigException {
		try {
			return (ModelConfig) mapper.readValue(mapper.writeValueAsString(config).getBytes(), config.getClass());
		} catch (JacksonException e) {
			throw new LLMConfigException("Cannot copy the configuration of chat model code=" + config.getCode(), e);
		}
	}

	/**
	 * The reasoning effort a request sends for a thinking option, as the OpenAI compatible
	 * APIs spell it ({@code reasoning_effort}): {@code none} for no thinking, low, medium
	 * and high as asked, each only when the model's parameter rules list it; null to send
	 * none and leave the provider default. Without rules knowing the model's efforts, low,
	 * medium and high are sent as asked and no thinking sends nothing, the value that
	 * turns the reasoning off differing per backend.
	 */
	protected String reasoningEffortFor(ChatModelThinkingOption option) {
		if (option == null)
			return null;
		final String effort;
		switch (option) {
		case NO_THINKING:
			effort = ModelParameterRules.NO_REASONING_EFFORT;
			break;
		case LOW_THINKING:
			effort = "low";
			break;
		case MEDIUM_THINKING:
			effort = "medium";
			break;
		case HIGH_THINKING:
			effort = "high";
			break;
		default:
			// AUTO: the provider default
			return null;
		}
		if (parameterRules == null || !parameterRules.knowsEfforts())
			return option == ChatModelThinkingOption.NO_THINKING ? null : effort;
		if (parameterRules.acceptsEffort(effort))
			return effort;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Chat model code=" + getCode() + " thinking " + option + ": effort " + effort
					+ " is not among the accepted " + parameterRules.getReasoningEfforts()
					+ ", the provider default is left");
		}
		return null;
	}

	/**
	 * Whether the model takes a reasoning effort value, as the provider spells it: true
	 * unless its parameter rules list its efforts without this one.
	 */
	protected boolean acceptsEffort(String effort) {
		return parameterRules == null || !parameterRules.knowsEfforts() || parameterRules.acceptsEffort(effort);
	}

	@Override
	public List<ToolCallback> wrapTools(ReactiveIdentityUtil runAs, ToolCallsListener toolCallListener) {
		return wrapToolsClosingResults(runAs, toolCallListener, null);
	}

	/**
	 * The enabled tools of this model, each closing its results with
	 * {@code toolsResultsClosing} when not null (see
	 * {@link GPromptTemplateConfig#getToolsResultsPromptTemplate()}).
	 */
	protected List<ToolCallback> wrapToolsClosingResults(ReactiveIdentityUtil runAs,
			ToolCallsListener toolCallListener, String toolsResultsClosing) {
		List<ToolCallback> tools = this.toolCallbacksRepository.getTools(toolNames);
		// a tool made for this use of the model - a library's, bound to the resources of
		// this run - takes the place of a registered tool of its name
		final Set<String> madeForThisUse = new HashSet<>();
		for (ToolCallback toolCallback : additionalTools != null ? additionalTools : List.<ToolCallback>of()) {
			madeForThisUse.add(toolCallback.getToolDefinition().name());
		}
		List<ToolCallback> wrapped = new ArrayList<>();
		for (ToolCallback toolCallback : tools) {
			if (!madeForThisUse.contains(toolCallback.getToolDefinition().name())) {
				wrapped.add(new RunAsToolCallback(toolCallback, runAs, toolCallListener, toolsResultsClosing));
			}
		}
		// the tools made for this use of the model are not in the repository: without this
		// they would be executable but never declared to the model
		int additional = 0;
		for (ToolCallback toolCallback : additionalTools != null ? additionalTools : List.<ToolCallback>of()) {
			final String name = toolCallback.getToolDefinition().name();
			if (toolNames.contains(name) && wrapped.stream().noneMatch(x -> name.equals(x.getToolDefinition().name()))) {
				wrapped.add(new RunAsToolCallback(toolCallback, runAs, toolCallListener, toolsResultsClosing));
				additional++;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("wrapTools(...) model:" + getCode() + " declares " + wrapped.size() + " tool(s), " + additional
					+ " of them made for this use of the model"
					+ (toolsResultsClosing != null ? ", their results closed by the prompt's text" : ""));
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

	/** What {@link #testCall()} asks: answerable in one word by any model. */
	static final String TEST_CALL_PROMPT = "Reply with the single word OK.";

	/**
	 * Sends the provider one user message through this model as it is configured: its
	 * sampling, thinking level, output limit and the definitions of the tools it enables
	 * go with it, as with every request it makes, so the provider judges the request the
	 * model will really send. No tool can run: in Spring AI 2.0.1 a provider's chat model
	 * only declares the tools - the chat client runs them, and this call does not go
	 * through it - so a tool call in the answer is an answer like any other. The call is
	 * recorded in the usage, as any other.
	 */
	@Override
	public void testCall() throws LLMConfigException {
		final long start = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin testCall() model code=" + getCode() + " model=" + safeGetModelCode() + " type="
					+ (type != null ? type.getCode() : null) + " temperature="
					+ (config != null ? config.getTemperature() : null) + " thinking="
					+ (config != null ? config.getThinking() : null) + " tools=" + toolNames.size());
		}
		final ChatResponse response;
		try {
			response = doWithChatModel(chatModel -> chatModel.call(new Prompt(new UserMessage(TEST_CALL_PROMPT))));
		} catch (RuntimeException e) {
			// the stack is logged by whoever set the model, with the message this carries
			LOGGER.warn("Chat model code=" + getCode() + " model=" + safeGetModelCode() + " refused the test call: "
					+ innermostMessage(e));
			throw new LLMConfigException("The chat model " + safeGetModelCode() + " (" + getCode()
					+ ") refused a test call: " + innermostMessage(e), e);
		}
		if (response == null) {
			LOGGER.warn("Chat model code=" + getCode() + " model=" + safeGetModelCode()
					+ " answered the test call with no response");
			throw new LLMConfigException("The chat model " + safeGetModelCode() + " (" + getCode()
					+ ") answered a test call with no response");
		}
		final Generation result = response.getResult();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End testCall() model code=" + getCode() + " answered in "
					+ (System.currentTimeMillis() - start) + "ms, finish reason="
					+ (result != null && result.getMetadata() != null ? result.getMetadata().getFinishReason() : null)
					+ " tool calls="
					+ (result != null && result.getOutput() != null && result.getOutput().hasToolCalls()
							? result.getOutput().getToolCalls().size()
							: 0));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("testCall() model code=" + getCode() + " answer: "
					+ (result != null && result.getOutput() != null ? result.getOutput().getText() : null));
		}
	}

	/**
	 * The message of the deepest cause that has one: the provider's own words, which the
	 * wrappers around them (retries, Spring AI) tend to bury.
	 */
	static String innermostMessage(Throwable error) {
		String message = null;
		final java.util.Set<Throwable> seen = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
		for (Throwable cause = error; cause != null && seen.add(cause); cause = cause.getCause()) {
			if (cause.getMessage() != null && !cause.getMessage().isBlank()) {
				message = cause.getMessage();
			}
		}
		return message != null ? message : error.getClass().getSimpleName();
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
		final Integer contextLength = knownContextLength();
		return contextLength != null ? contextLength : 8192;
	}

	/**
	 * The context length the configuration or the model metadata give, less the
	 * maximum generated tokens (configured, else the default of
	 * {@link #maxGeneratedTokensOf}: what the requests reserve for the generation),
	 * null when neither gives one.
	 */
	protected Integer knownContextLength() {
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
		return usableContextWindow(contextLength, config);
	}

	/**
	 * The part of a context window the prompts may take: the window less the maximum
	 * generated tokens of the configuration (configured, else the default of
	 * {@link #maxGeneratedTokensOf}), the window itself when the cap is unknown or not
	 * smaller than it. Null when the window is unknown or not positive.
	 */
	public static Integer usableContextWindow(Integer contextLength, GBaseChatModelConfig<?> config) {
		final Integer generated = maxGeneratedTokensOf(config);
		if (contextLength != null && generated != null) {
			final int realContextWindow = contextLength.intValue() - generated.intValue();
			if (realContextWindow > 0) {
				if (STATIC_LOGGER.isTraceEnabled()) {
					STATIC_LOGGER.trace("usableContextWindow(...) window:" + contextLength + " less generated tokens:"
							+ generated + " = " + realContextWindow);
				}
				contextLength = realContextWindow;
			}
		}
		return contextLength == null || contextLength.intValue() <= 0 ? null : contextLength;
	}

	/**
	 * The context window of a configuration's model: the configured one, else the one
	 * the chosen model carries or its provider's metadata report. Null when none is
	 * known.
	 */
	public static Integer contextWindowOf(GBaseChatModelConfig<?> config) {
		if (config == null)
			return null;
		Integer contextLength = config.getContextLength();
		if ((contextLength == null || contextLength <= 0) && config.getChoosedModel() instanceof GBaseModelChoice choice) {
			contextLength = choice.getContextLength();
			if ((contextLength == null || contextLength <= 0) && choice.getMetaInfos() != null) {
				contextLength = choice.getMetaInfos().getContextLength();
			}
		}
		return contextLength == null || contextLength <= 0 ? null : contextLength;
	}

	/**
	 * The default maximum of generated tokens for a context window, when the
	 * configuration sets none: a share of the window, by steps - 4K below 30K tokens,
	 * 8K up to 120K, 10K above. Null when the window is unknown.
	 */
	public static Integer defaultMaxGeneratedTokens(Integer contextWindow) {
		if (contextWindow == null || contextWindow <= 0)
			return null;
		if (contextWindow < 30_000)
			return 4096;
		if (contextWindow <= 120_000)
			return 8192;
		return 10240;
	}

	/**
	 * The maximum of generated tokens of a configuration: the configured one, else the
	 * default its model's context window gives. Null when neither is known.
	 */
	public static Integer maxGeneratedTokensOf(GBaseChatModelConfig<?> config) {
		if (config == null)
			return null;
		if (config.getMaxGeneratedTokens() != null && config.getMaxGeneratedTokens() > 0)
			return config.getMaxGeneratedTokens();
		return defaultMaxGeneratedTokens(contextWindowOf(config));
	}

	/**
	 * Whether a generation of a blocking call's response is the model's reasoning rather
	 * than its answer. False by default: the providers whose Spring AI model returns its
	 * reasoning as generations of their own say which ones they are, so that the answer
	 * is read from the answer generation ({@link AnswerFirstGenerations}).
	 */
	protected boolean isReasoningGeneration(Generation generation) {
		return false;
	}

	/**
	 * How the provider's Spring AI model gives the reasoning apart from the answer. The
	 * reasoning parts of the assistant message by default, as every Spring AI model keeps
	 * it since 2.1 (a model keeping it in the reasoning_content metadata, the way before
	 * the parts, is read there): a provider keeping it otherwise gives its own.
	 */
	protected IGReasoningExtractor reasoningExtractor() {
		return IGReasoningExtractor.PARTS;
	}

	/** The advisor giving every model round's answer and reasoning the same shape. */
	ThinkingNormalizationAdvisor thinkingNormalization() {
		return new ThinkingNormalizationAdvisor(reasoningExtractor(), this::isApplyThinkingMarkupHandling,
				this::isReasoningGeneration);
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
		return prepareCall(prompt, params, chatContext, runAs, null);
	}

	@Override
	public RequestSpec prepareCall(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatContext, ReactiveIdentityUtil runAs, GeboToolCalling toolCalling) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin prepareCall(" + prompt.getPromptUse() + ", ...,...)"
					+ (toolCalling != null ? " with " + toolCalling : ""));
		}
		long tokens = 0;
		// the caller's parameters, plus what the providers fill of the placeholders the
		// template names and the caller did not
		final Map<String, Object> completed = promptContextProviders != null
				? promptContextProviders.complete(prompt, params, chatContext)
				: (params != null ? params : Map.of());
		// Here prompt, documents and consolidated history
		List<Message> messages = new ArrayList<Message>();
		SystemMessage systemMessage = this.createSystemMessage(prompt, completed, chatContext);
		messages.add(systemMessage);
		if (prompt.getChatHistory() == null || prompt.getChatHistory() == ContextContentRequired.REQUIRED) {
			List<Message> history = this.createHistoryMessages(chatContext);
			messages.addAll(history);
		}
		UserMessage lastUserMessage = this.createLastUserMessage(prompt, completed, chatContext);
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

		ChatClientRequestSpec reqObject = getChatClient().prompt();

		// the controlling software's recorder, when it gave one, records beside the
		// request's: neither takes the place of the other
		final ToolCallsListener toolCallListener = toolCalling != null && toolCalling.getListener() != null
				? ToolCallsListener.both(toolCalling.getListener(), chatContext.getToolCallListener())
				: chatContext.getToolCallListener();
		final String toolsResultsClosing = createToolsResultsClosing(prompt, completed, chatContext);
		List<ToolCallback> tools = wrapToolsClosingResults(runAs, toolCallListener, toolsResultsClosing);
		if (toolCalling != null && !toolCalling.getMandatoryTools().isEmpty()) {
			tools = withMandatoryTools(tools, toolCalling.getMandatoryTools(), runAs, toolCallListener,
					toolsResultsClosing);
		}
		reqObject = reqObject.tools(tools);
		// chat histroy in user, assistant format
		reqObject = reqObject.messages(messages);
		// tools call environment
		Map<String, Object> toolContext = chatContext.getToolsContext();
		if (toolCalling != null) {
			// what the controlling software loaded for the tools, and the budget it set
			toolContext = toolCalling.toolsContext(toolContext);
		}
		if (!tools.isEmpty()) {
			toolContext = withToolsRoom(toolContext, messages, tools, LOGGER.isDebugEnabled() ? tokens : -1);
		}
		if (toolContext != null) {
			reqObject = reqObject.toolContext(toolContext);
		}
		if (toolCalling != null) {
			// the call's own tool calling loop, in place of the one the chat client
			// registers by default (see GeboToolCallingAdvisor)
			reqObject = reqObject.advisors(toolCalling.advisor(observationRegistry));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End prepareCall(" + prompt.getPromptUse() + ", ...,...)");
		}
		return new RequestSpec(reqObject, tokens, config != null ? config.getCode() : "<<empty model>>");
	}

	@Override
	public List<String> getToolNames() {
		return toolNames;
	}

	@Override
	public IGConfigurableChatModel<ModelConfig> withoutTools() throws LLMConfigException {
		if (!hasTools()) {
			return this;
		}
		if (withoutToolsModel == null) {
			withoutToolsModel = cloneWithOptions("", new ChatModelConfigOptions(null, null, null, List.of(), List.of()));
		}
		return withoutToolsModel;
	}

	/** Whether this model declares any tool: from the repository or made for this use of it. */
	private boolean hasTools() {
		if (toolNames.isEmpty()) {
			return false;
		}
		return !toolCallbacksRepository.getTools(toolNames).isEmpty() || (additionalTools != null
				&& additionalTools.stream().anyMatch(tool -> toolNames.contains(tool.getToolDefinition().name())));
	}

	/**
	 * The enabled tool names found neither among {@code resolved}, the tools the
	 * repository gave for them, nor among the tools made for this use of the model. A
	 * tool made for this use - a library's, bound to the resources of the run, or one
	 * made for the execution, such as notifyUser - is not in the repository by design:
	 * it is declared to every call (see {@link #wrapToolsClosingResults}), so it is not
	 * missing.
	 */
	protected List<String> unresolvedToolNames(Collection<ToolCallback> resolved) {
		final Set<String> found = new HashSet<String>();
		for (ToolCallback tool : resolved != null ? resolved : List.<ToolCallback>of()) {
			found.add(tool.getToolDefinition().name());
		}
		for (ToolCallback tool : additionalTools != null ? additionalTools : List.<ToolCallback>of()) {
			found.add(tool.getToolDefinition().name());
		}
		final List<String> unresolved = new ArrayList<String>();
		for (String name : toolNames) {
			if (!found.contains(name)) {
				unresolved.add(name);
			}
		}
		return unresolved;
	}

	/**
	 * What a provider tells when the repository resolved none of the enabled tools, so
	 * that the model is configured with no tools of its own: a warning naming the tools
	 * found nowhere, the ones the model will never declare; when every one is made for
	 * this use of the model, only a debug line, since each call declares them.
	 */
	protected void logToolsNotRegistered(Logger logger, String modelCode, Collection<ToolCallback> resolved) {
		final List<String> unresolved = unresolvedToolNames(resolved);
		if (!unresolved.isEmpty()) {
			logger.warn("Chat model " + modelCode + " enables " + toolNames.size() + " tool(s) but "
					+ unresolved.size() + " of them resolved to no callback, neither registered nor made for this"
					+ " use of it, so it is configured without them: " + unresolved);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Chat model " + modelCode + " declares its " + toolNames.size()
					+ " tool(s) on each call: none is registered, all of them are made for this use of it");
		}
	}

	/**
	 * The declared tools with the controlling software's mandatory ones, wrapped as the
	 * others are: a declared tool of the same name gives way to the mandatory instance,
	 * the one bound to this call.
	 */
	protected List<ToolCallback> withMandatoryTools(List<ToolCallback> declared, List<ToolCallback> mandatory,
			ReactiveIdentityUtil runAs, ToolCallsListener toolCallListener, String toolsResultsClosing) {
		final List<ToolCallback> out = new ArrayList<>(declared);
		for (ToolCallback tool : mandatory) {
			final String name = tool.getToolDefinition().name();
			if (out.removeIf(x -> name.equals(x.getToolDefinition().name())) && LOGGER.isDebugEnabled()) {
				LOGGER.debug("prepareCall(...) model:" + getCode() + " the mandatory tool " + name
						+ " takes the place of the configured one");
			}
			out.add(new RunAsToolCallback(tool, runAs, toolCallListener, toolsResultsClosing));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("prepareCall(...) model:" + getCode() + " declares " + mandatory.size()
					+ " mandatory tool(s) with " + declared.size() + " configured one(s): "
					+ out.stream().map(x -> x.getToolDefinition().name()).toList());
		}
		return out;
	}

	/**
	 * The tools context of a model call with tools, carrying the room its context leaves
	 * to the tools' results (see {@link ToolsTokenBudget}): two thirds ({@link #TOOLS_ROOM_SHARE})
	 * of what the messages and the tools' definitions leave of the context length, the
	 * rest left to the answer, the tool calls' arguments and the model's messages
	 * between the tool rounds. A budget an agent shares through the request context is
	 * capped to it, never replaced, so the smaller of the two applies. Without a known
	 * context length no room is set (the 8192 of {@link #getContextLength()} is a guess):
	 * the tools are left to their own sizes, as before.
	 *
	 * @param messagesTokens the tokens of the messages when already counted, -1 when not
	 * @return a copy of the given tools context (never the caller's map)
	 */
	protected Map<String, Object> withToolsRoom(Map<String, Object> toolsContext, List<Message> messages,
			List<ToolCallback> tools, long messagesTokens) {
		if (knownContextLength() == null) {
			if (UNKNOWN_CONTEXT_LENGTH_WARNED.add(String.valueOf(getCode()))) {
				LOGGER.warn("Chat model " + getCode() + " has no known context length: its tools' results are not "
						+ "bounded by the room left in its context");
			}
			return toolsContext;
		}
		final int contextLength = getContextLength();
		long used = messagesTokens;
		if (used < 0) {
			used = 0;
			for (Message message : messages) {
				used += ITokensCountable.stringsTokensSize(message.getText());
			}
		}
		long definitions = 0;
		for (ToolCallback tool : tools) {
			definitions += ITokensCountable.stringsTokensSize(tool.getToolDefinition().name(),
					tool.getToolDefinition().description(), tool.getToolDefinition().inputSchema());
		}
		final int room = (int) Math.max(0,
				Math.min(Integer.MAX_VALUE, (long) ((contextLength - used - definitions) * TOOLS_ROOM_SHARE)));
		final Map<String, Object> withRoom = toolsContext != null ? new HashMap<>(toolsContext) : new HashMap<>();
		final ToolsTokenBudget shared = ToolsTokenBudget.from(withRoom);
		if (shared != null) {
			shared.capTo(room);
		} else {
			withRoom.put(ToolsTokenBudget.TOOLS_CONTEXT_KEY, new ToolsTokenBudget(room));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("withToolsRoom(...) model:" + getCode() + " context:" + contextLength + " messages:" + used
					+ " tools definitions:" + definitions + " (tok) room for " + tools.size() + " tool(s) results:"
					+ room + " (tok)" + (shared != null ? " capping the shared budget, now " + shared.left() : ""));
		}
		return withRoom;
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

	protected final static String COMPRESSED_HISTORY_ONLY_MESSAGE_CHAT_TEMPLATE = "BEGIN_CONSOLIDATED_HISTORY\r\n{"
			+ IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM + "}\r\nEND_CONSOLIDATED_HISTORY\r\n";

	protected final static String COMPRESSED_HISTORY_FIRST_MESSAGE_CHAT_TEMPLATE = "BEGIN_CONSOLIDATED_HISTORY\r\n{"
			+ IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM
			+ "}\r\nEND_CONSOLIDATED_HISTORY\r\nUSER-QUESTION={" + IChatRequestContext.USER_QUESTION_PROMPT_PARAM
			+ "}\r\n";

	protected List<Message> createCompressedHistory(IChatRequestContext chatContext) {
		List<Message> messages = new ArrayList<>();
		String consolidated = chatContext.getConsolidatedHistory();
		List<IChatSessionEntry> interactions = chatContext.getInteractions() != null ? chatContext.getInteractions()
				: List.of();
		if (interactions.isEmpty()) {
			// only the summary of the older turns is left: it is the whole history
			PromptTemplate template = new PromptTemplate(COMPRESSED_HISTORY_ONLY_MESSAGE_CHAT_TEMPLATE);
			template.add(IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM, consolidated);
			messages.add(new UserMessage(template.render()));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createCompressedHistory(...) chat history is the consolidated summary only");
			}
			return messages;
		}
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
				String user = interactions.get(i).getUser();
				String assistant = interactions.get(i).getAssistant();
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
		String content = promptTemplate.render(withUserLanguage(allParams, chatContext));
		return new UserMessage(content);
	}

	/**
	 * The parameters with the answer's language ({@code {userLanguage}}): the user's
	 * language detected for the request, or what says to deduce it; a caller's own
	 * value is kept. Every template of every call may name it, never missing.
	 */
	protected static Map<String, Object> withUserLanguage(Map<String, Object> params,
			IChatRequestContext chatContext) {
		final Map<String, Object> withLanguage = params != null ? new HashMap<>(params) : new HashMap<>();
		withLanguage.putIfAbsent(IChatRequestContext.USER_LANGUAGE_PROMPT_PARAM,
				IChatRequestContext.answerLanguage(chatContext));
		return withLanguage;
	}

	/**
	 * The text closing every tool result of this call: the prompt's tools results
	 * template, rendered with the parameters of the user message but the documents
	 * (they are in the user message). The tool calling loop puts the tools' results after
	 * the user message, so when tools run this is the last text the model reads before
	 * answering. Null when the prompt has none.
	 */
	protected String createToolsResultsClosing(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatContext) {
		final String template = prompt.getToolsResultsPromptTemplate();
		if (template == null || template.isBlank()) {
			return null;
		}
		final Map<String, Object> allParams = new HashMap<>(params);
		allParams.remove(IChatRequestContext.DOCUMENTS_PROMPT_PARAM);
		allParams.put(IChatRequestContext.USER_QUESTION_PROMPT_PARAM,
				chatContext != null && chatContext.getActualUserRequest() != null ? chatContext.getActualUserRequest()
						: "");
		allParams.put(IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM,
				chatContext != null && chatContext.getConsolidatedHistory() != null
						? chatContext.getConsolidatedHistory()
						: "");
		final String closing = new PromptTemplate(template).render(withUserLanguage(allParams, chatContext));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createToolsResultsClosing(...) prompt:" + prompt.getPromptUse() + " closes the tools' results"
					+ " with " + closing.length() + " character(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<TOOLS_RESULTS_PROMPT>");
			LOGGER.trace(closing);
			LOGGER.trace("</TOOLS_RESULTS_PROMPT>");
		}
		return closing;
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
		String content = template.render(withUserLanguage(params, chatContext));
		List<String> rules = chatContext != null ? chatContext.getRulesToFollow() : null;
		if (rules != null && !rules.isEmpty()) {
			StringBuilder withRules = new StringBuilder(content);
			withRules.append("\n\nRULES TO FOLLOW, set by the user or by the administrators for the answers given to")
					.append(" the user. They never change an output format required above.\n");
			for (String rule : rules) {
				withRules.append("- ").append(rule).append("\n");
			}
			content = withRules.toString();
		}
		return new SystemMessage(content);
	}

	@Deprecated
	@Override
	public Flux<ChatResponse> streamResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		return responses(promptTemplate, params, chatContext, null);
	}

	@Deprecated
	@Override
	public Flux<ChatResponse> streamResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		return responses(promptTemplate, params, chatContext, toolCalling);
	}

	/** The streamed chunks, normalized (ThinkingNormalizationAdvisor). */
	private Flux<ChatResponse> responses(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		return runAs.doRunAsWithReturnAndException(() -> {
			long timestamp = 0l;
			if (LOGGER.isDebugEnabled()) {
				timestamp = System.currentTimeMillis();
				LOGGER.debug("Begin streamResponse(" + promptTemplate.getPromptUse() + ", ...,...)" + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
			}
			RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs, toolCalling);
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
		return streamStringResponse(promptTemplate, params, chatContext, null);
	}

	@Override
	public Flux<String> streamStringResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		return runAs.doRunAsWithReturnAndException(() -> {
			long timestamp = 0l;
			if (LOGGER.isDebugEnabled()) {
				timestamp = System.currentTimeMillis();
				LOGGER.debug("Begin streamStringResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
			}

			RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs, toolCalling);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End streamStringResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
						+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
						+ chatContext.getPipelineInfos() + "]");
				logPerformances(reqObject, timestamp);
			}

			// the answer only, its reasoning apart (ThinkingNormalizationAdvisor)
			return reqObject.getRequestSpec().stream().chatResponse().map(GChatAnswerChunk::of)
					.map(GChatAnswerChunk::answer).filter(answer -> !answer.isEmpty())
					.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
		});
	}

	@Override
	public Flux<GChatAnswerChunk> streamAnswer(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		return streamAnswer(promptTemplate, params, chatContext, null);
	}

	@Override
	public Flux<GChatAnswerChunk> streamAnswer(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		return responses(promptTemplate, params, chatContext, toolCalling).map(GChatAnswerChunk::of);
	}

	@Override
	public GChatAnswer answer(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		return answer(promptTemplate, params, chatContext, null);
	}

	@Override
	public GChatAnswer answer(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		return GChatAnswer.of(response(promptTemplate, params, chatContext, toolCalling));
	}

	@Override
	public ChatResponse response(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException {
		return response(promptTemplate, params, chatContext, null);
	}

	@Override
	public ChatResponse response(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin response(" + promptTemplate.getPromptUse() + ", ...,...) [session:"
					+ chatContext.getSessionID() + " request:" + chatContext.getRequestID() + " pipelineInfos:"
					+ chatContext.getPipelineInfos() + "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs, toolCalling);
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
		return structuredResponse(promptTemplate, params, chatContext, rt, (GeboToolCalling) null);
	}

	@Override
	public <ResponseType> ResponseType structuredResponse(GPromptTemplateConfig promptTemplate,
			Map<String, Object> params, IChatRequestContext chatContext, Class<ResponseType> rt,
			GeboToolCalling toolCalling) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin structuredResponse(" + promptTemplate.getPromptUse() + ", ...,...,"
					+ (rt != null ? rt.getName() : "NULL") + ")  [session:" + chatContext.getSessionID() + " request: "
					+ chatContext.getRequestID() + " pipelineInfos: " + chatContext.getPipelineInfos() + "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs, toolCalling);
		ResponseType data = toolCalling != null
				? toolCalling.parsedUnlessStopped(() -> reqObject.getRequestSpec().call().entity(rt))
				: reqObject.getRequestSpec().call().entity(rt);
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
		return structuredResponse(promptTemplate, params, chatContext, rt, outputConverter, null);
	}

	@Override
	public <ResponseType> ResponseType structuredResponse(GPromptTemplateConfig promptTemplate,
			Map<String, Object> params, IChatRequestContext chatContext, Class<ResponseType> rt,
			BeanOutputConverter<ResponseType> outputConverter, GeboToolCalling toolCalling)
			throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin structuredResponse(" + promptTemplate.getPromptUse() + ", ...,...,"
					+ (rt != null ? rt.getName() : "NULL") + ",..) " + "[session:" + chatContext.getSessionID()
					+ " request: " + chatContext.getRequestID() + " pipelineInfos: " + chatContext.getPipelineInfos()
					+ "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs, toolCalling);
		ResponseType data = toolCalling != null
				? toolCalling.parsedUnlessStopped(() -> reqObject.getRequestSpec().call().entity(outputConverter))
				: reqObject.getRequestSpec().call().entity(outputConverter);
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
		return textResponse(promptTemplate, params, chatContext, null);
	}

	@Override
	public String textResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext, GeboToolCalling toolCalling) throws LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		long timestamp = 0l;
		if (LOGGER.isDebugEnabled()) {
			timestamp = System.currentTimeMillis();
			LOGGER.debug("Begin textResponse(" + promptTemplate.getPromptUse() + ", ...,...) " + "[session:"
					+ chatContext.getSessionID() + " request: " + chatContext.getRequestID() + " pipelineInfos: "
					+ chatContext.getPipelineInfos() + "]");
		}
		RequestSpec reqObject = prepareCall(promptTemplate, params, chatContext, runAs, toolCalling);
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
		// The raw model bypasses the ChatClient and with it the usage advisor: hand out a
		// recording wrapper so direct calls are accounted as well.
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("doWithChatModel() handing out the usage recording raw model of code=" + getCode());
		}
		return chatModelCalling.call(new AnswerFirstGenerations.Model(usageAdvisorFactory.recording(model, config,
				this::getPricingConditions, this::getProviderId), this::isReasoningGeneration));
	}

	@Override
	public IGConfigurableChatModel<ModelConfig> cloneWithOptions(String codePrefix,
			ChatModelConfigOptions configOptions) throws LLMConfigException {
		try {
			String asString = mapper.writeValueAsString(config);
			ModelConfig modelConfigClone = (ModelConfig) mapper.readValue(asString.getBytes(), config.getClass());
			modelConfigClone.setCode(codePrefix + modelConfigClone.getCode());
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
			if (handler instanceof GAbstractConfigurableChatModel clone) {
				// the clone fills its prompts as this model does
				clone.promptContextProviders = this.promptContextProviders;
				clone.toolNames = configOptions.getToolsName() != null ? List.copyOf(configOptions.getToolsName())
						: List.of();
				if (configOptions.getAdditionalTools() != null) {
					clone.additionalTools = List.copyOf(configOptions.getAdditionalTools());
				}
			}
			if (handler instanceof IGProviderDealPricedModel priced) {
				// The clone is priced like this model, by its provider deal; best effort.
				try {
					priced.setProviderDealService(this.providerDealService);
				} catch (Throwable e) {
					LOGGER.error("Cannot attach the provider deals to a clone of chat model code=" + getCode(), e);
				}
			}
			// the clone is initialised as this model was: its parameter rules (an agent's
			// temperature never reaches a model refusing it), its model and its chat client
			// with the same advisors; the tools it declares reach every call as callbacks,
			// so no tool calling manager of its own is needed
			handler.initialize(modelConfigClone, type);
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