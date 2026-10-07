package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public abstract class GAbstractReactiveAgentService<RequestType, ResponseType,  AggregatedResponses>
		extends GAbstractGenericalAgentService
		implements IGReactiveAgentService<RequestType, ResponseType> {

	public GAbstractReactiveAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);

	}

	/** A copy of an agent's model asked for another thinking level. */
	@FunctionalInterface
	protected static interface ThinkingVariant {
		IGConfigurableChatModel with(ChatModelThinkingOption thinking) throws LLMConfigException;
	}

	/**
	 * How to copy the model of each running execution with another thinking level, its
	 * options otherwise the same (tools, their calling manager bound to the execution's
	 * listener, the execution's own tools): weak, an entry goes with its model.
	 */
	private final Map<IGConfigurableChatModel, ThinkingVariant> thinkingVariants = Collections
			.synchronizedMap(new WeakHashMap<>());

	/**
	 * The model of a running execution of this agent ({@code agentModel}, as given to
	 * {@link #createResponse}) asked for another thinking level, null when it is not one.
	 */
	protected IGConfigurableChatModel withThinking(IGConfigurableChatModel agentModel, ChatModelThinkingOption thinking)
			throws LLMConfigException {
		final ThinkingVariant variant = agentModel != null ? thinkingVariants.get(agentModel) : null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("withThinking(...) agent id:" + getId() + " thinking:" + thinking + " known model:"
					+ (variant != null));
		}
		return variant != null ? variant.with(thinking) : null;
	}

	/**
	 * The tools made for one execution of this agent, bound to it, added to the ones
	 * of its configuration: none by default. An agent that lets the model talk to the
	 * user returns the {@code notifyUser} tool (see
	 * {@link #createUserMessageTool(INotificationSink)}).
	 */
	protected List<ToolCallback> additionalTools(AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink) {
		return null;
	}

	@Override
	public Flux<IGPartialOperation<ResponseType>> execute(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, RequestType request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> privateMemory, ReactiveIdentityUtil runAs)
			throws AgentException, LLMConfigException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...) reactive agent service id:" + getId() + " agentConfig code:"
					+ (agentConfig != null ? agentConfig.getCode() : null) + " useDefaultChatModel:"
					+ (agentConfig != null ? agentConfig.getUseDefaultChatModel() : null));
		}
		IGConfigurableChatModel copiedModel = null;
		if (agentConfig.getUseDefaultChatModel() != null && agentConfig.getUseDefaultChatModel()) {
			copiedModel = chatModelsDao.defaultHandler();
		} else {
			copiedModel = chatModelsDao.findByModelReference(agentConfig.getChatModelReference());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive agent id:" + getId() + " resolved base chat model:"
					+ (copiedModel != null ? copiedModel.getCode() : null));
		}
		if (copiedModel == null) {
			LOGGER.warn("Setting backup default chat model for actual Agent");
			copiedModel = chatModelsDao.defaultHandler();
			if (copiedModel == null)
				throw new LLMConfigException("Default chat model not set in the system");
		}
		// this agent's own tool calls, forwarded to the user request's recorder
		final ToolCallsListener callBacksListener = agentToolCallsListener(chatRequestContext);
		List<String> allFunctions = agentConfig.getEnabledFunctions();
		if (agentConfig.getSubscribeAllTools() != null && agentConfig.getSubscribeAllTools()) {
			List<ToolCallback> toolsList = toolsRepositoryPattern.getTools();
			if (toolsList != null) {
				// Honor the auto-mount exclusions (see AgentsToolsAutoMountingConfig) so tools
				// kept out of automatic mounting are not subscribed by reactive agents either.
				allFunctions = filterAutoMountedTools(
						toolsList.stream().map(x -> x.getToolDefinition().name()).toList());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Reactive agent id:" + getId() + " subscribes ALL tools, resolved "
						+ (allFunctions != null ? allFunctions.size() : 0) + " function(s) after auto-mount exclusions");
			}
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Reactive agent id:" + getId() + " enabled function names: " + allFunctions);
			LOGGER.trace("<REACTIVE_AGENT_REQUEST agent=" + getId() + ">");
			LOGGER.trace(String.valueOf(request));
			LOGGER.trace("</REACTIVE_AGENT_REQUEST>");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive agent id:" + getId() + " resolved " + (allFunctions != null ? allFunctions.size() : 0)
					+ " enabled function(s); cloning model with temperature:" + agentConfig.getTemperature() + " topP:"
					+ agentConfig.getTopP() + " thinking:" + agentConfig.getThinking());
		}
		// tools made for this execution, bound to it (e.g. notifyUser, bound to the
		// notification sink)
		final List<ToolCallback> additionalTools = additionalTools(contextAgentPersona, notificationSink);
		if (additionalTools != null && !additionalTools.isEmpty()) {
			allFunctions = allFunctions != null ? new ArrayList<String>(allFunctions) : new ArrayList<String>();
			for (ToolCallback tool : additionalTools) {
				allFunctions.add(tool.getToolDefinition().name());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Reactive agent id:" + getId() + " adds " + additionalTools.size()
						+ " execution tool(s): " + additionalTools.stream().map(x -> x.getToolDefinition().name())
								.toList());
			}
		}
		ChatModelConfigOptions configOptions = new ChatModelConfigOptions(agentConfig.getTemperature(),
				agentConfig.getTopP(), agentConfig.getThinking(), allFunctions,
				createToolCallingManager(callBacksListener, allFunctions, additionalTools, runAs), additionalTools);
		IGConfigurableChatModel agentModel = copiedModel.cloneWithOptions(getId(), configOptions);
		final IGConfigurableChatModel baseModel = copiedModel;
		thinkingVariants.put(agentModel,
				thinking -> baseModel.cloneWithOptions(getId(),
						new ChatModelConfigOptions(configOptions.getTemperature(), configOptions.getTopP(), thinking,
								configOptions.getToolsName(), configOptions.getToolCallingManager(),
								configOptions.getAdditionalTools())));

		final GPromptTemplateConfig agentPrompt = resolvePrompt(agentConfig.getCustomLoopPrompt(),
				agentConfig.getMainLoopPromptUseCode(), false);
		final GAgentRole agentRole = agentRoleDao.findByCode(agentConfig.getAgentRoleCode());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End execute(...) building reactive response flux for agent id:" + getId() + " agentRole:"
					+ (agentRole != null ? agentRole.getCode() : null));
		}
		// the model wraps the tools with the listener of the context it is called with
		Flux<IGPartialOperation<ResponseType>> iteration = createResponse(
				IChatRequestContext.forAgent(chatRequestContext, callBacksListener), agentConfig, request, network,
				contextAgentPersona, notificationSink, session, privateMemory, agentModel, agentRole, agentPrompt, runAs,
				callBacksListener);
		return iteration.subscribeOn(runAs.wrap(Schedulers.boundedElastic()))
				.doOnSubscribe(s -> LOGGER.debug("Begin reactive agentic iteration subscription {} ", getId()))
				.doOnNext(partial -> {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Reactive agent id:" + getId() + " emitted a partial operation, lastMessage:"
								+ (partial != null && partial.isLastMessage()));
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<REACTIVE_AGENT_PARTIAL agent=" + getId() + ">");
						LOGGER.trace(String.valueOf(partial != null ? partial.getData() : null));
						LOGGER.trace("</REACTIVE_AGENT_PARTIAL>");
					}
				}).doOnComplete(() -> LOGGER.debug("End agentic iteration {} ", getId()))
				.doOnError(th -> LOGGER.error("Error in reactive agentic iteration " + getId(), th));
	}

	protected abstract Flux<IGPartialOperation<ResponseType>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, RequestType request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink  notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> mySessionContext, IGConfigurableChatModel agentModel,
			GAgentRole agentRole, GPromptTemplateConfig agentPrompt, ReactiveIdentityUtil runAs,
			ToolCallsListener callBacksListener) throws LLMConfigException, AgentException;

}