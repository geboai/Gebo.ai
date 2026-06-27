package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Consumer;

import ai.gebo.architecture.agents.services.AgentPromptTemplateParams;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.impl.AgentToolCallingManagerFactory;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public abstract class GAbstractGenericalAgentService extends BaseLLMSInvokingService implements IGGenericAgentService {
	public static final String NOTIFY_USER_TOOL_DESCRIPTION = "Tool to interactively notify user of your actual decisions or actions, use short notifications message";
	public static final String NOTIFY_USER_TOOL = "notifyUser";
	public static final String USE_NOTIFY_USER_TOOL_PROMPT_PART = "You are allowed to call the tool: "
			+ NOTIFY_USER_TOOL
			+ " to notify user regarding your actions and decision, be concise and do it from 1 to 4 times.";
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern;
	protected final IGPromptConfigDao promptsDao;
	protected final IGRuntimeBinder runtimeBinder;
	protected final IGSecurityService securityService;
	protected final IAgentRoleDao agentRoleDao;
	protected final IGDocumentContentRendererProvider rendererFactory;

	@Override
	public List<GAgentConfig> getAccessibleConfigurations() {
		IAgentConfigDao configsDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		List<GAgentConfig> configs = configsDao.findByAgentServiceId(getId());
		return securityService.filterCanDoAction(configs, true, AclGrantType.EXECUTE);
	}

	/**
	 * Base capabilities descriptor shared by every concrete agent: the agent
	 * description as the summary plus the tools the configuration enables (the
	 * explicitly enabled functions, or every registered tool when the configuration
	 * subscribes to all of them). Concrete agents call {@code super} and enrich the
	 * returned descriptor with their specific capabilities and catalogs.
	 */
	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Building base agent capabilities for service id:" + getId() + " agentConfig code:"
					+ (agentConfig != null ? agentConfig.getCode() : null));
		}
		AgentCapabilities capabilities = new AgentCapabilities(getDescription());
		appendConfiguredTools(capabilities, agentConfig);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Base agent capabilities for service id:" + getId() + " advertise "
					+ capabilities.getTools().size() + " tool(s)");
		}
		return capabilities;
	}

	/**
	 * Adds the tools/functions reachable with the given configuration to the
	 * capabilities descriptor. Mirrors the tool selection performed in
	 * {@link #getAgentModel(GAgentConfig, ToolCallsListener, INotificationSink, ReactiveIdentityUtil)}:
	 * when {@code subscribeAllTools} is set every registered tool is advertised
	 * (with its description), otherwise only the explicitly enabled functions are.
	 */
	protected void appendConfiguredTools(AgentCapabilities capabilities, GAgentConfig agentConfig) {
		if (agentConfig == null) {
			return;
		}
		if (Boolean.TRUE.equals(agentConfig.getSubscribeAllTools())) {
			List<ToolCallback> toolsList = toolsRepositoryPattern.getTools();
			if (toolsList != null) {
				for (ToolCallback tool : toolsList) {
					if (tool == null || tool.getToolDefinition() == null) {
						continue;
					}
					capabilities.addTool(AgentCapabilityResource.of(tool.getToolDefinition().name(),
							tool.getToolDefinition().name(), tool.getToolDefinition().description()));
				}
			}
		} else if (agentConfig.getEnabledFunctions() != null) {
			for (String functionName : agentConfig.getEnabledFunctions()) {
				if (functionName != null && !functionName.isBlank()) {
					capabilities.addTool(AgentCapabilityResource.of(functionName, functionName, null));
				}
			}
		}
	}

	protected IGConfigurableChatModel getAgentModel(GAgentConfig agentConfig, ToolCallsListener callBacksListener,
			INotificationSink notificationSink, ReactiveIdentityUtil runAs) throws LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getAgentModel(...) for agent service id:" + getId() + " agentConfig code:"
					+ (agentConfig != null ? agentConfig.getCode() : null) + " useDefaultChatModel:"
					+ (agentConfig != null ? agentConfig.getUseDefaultChatModel() : null) + " chatModelReference:"
					+ (agentConfig != null ? agentConfig.getChatModelReference() : null));
		}
		IGConfigurableChatModel copiedModel = null;
		if (agentConfig.getChatModelReference() != null) {
			copiedModel = chatModelsDao.findByModelReference(agentConfig.getChatModelReference());
		} else if (agentConfig.getUseChatModelWithUse() != null) {
			copiedModel = chatModelsDao.findByUsesOrGetDefault(agentConfig.getUseChatModelWithUse());
		} else if (agentConfig.getUseDefaultChatModel() != null && agentConfig.getUseDefaultChatModel()) {
			copiedModel = chatModelsDao.defaultHandler();
		}
		if (copiedModel == null) {
			LOGGER.warn("Setting backup default chat model for actual Agent");
			copiedModel = chatModelsDao.defaultHandler();
			if (copiedModel == null)
				throw new LLMConfigException("Default chat model not set in the system");
		}

		List<String> allFunctions = agentConfig.getEnabledFunctions();
		allFunctions = allFunctions != null ? new ArrayList<String>(allFunctions) : new ArrayList<String>();
		if (agentConfig.getSubscribeAllTools() != null && agentConfig.getSubscribeAllTools()) {
			List<ToolCallback> toolsList = toolsRepositoryPattern.getTools();
			if (toolsList != null) {
				allFunctions = new ArrayList(toolsList.stream().map(x -> x.getToolDefinition().name()).toList());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent subscribes ALL tools, resolved " + (allFunctions != null ? allFunctions.size() : 0)
						+ " functions");
			}
		}
		List<ToolCallback> additionalFunctions = new ArrayList<ToolCallback>();
		if (notificationSink != null) {
			ToolCallback userMessageTool = createUserMessageTool(notificationSink);
			additionalFunctions.add(userMessageTool);
			allFunctions.add(userMessageTool.getToolDefinition().name());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Cloning chat model with temperature:" + agentConfig.getTemperature() + " topP:"
					+ agentConfig.getTopP() + " thinking:" + agentConfig.getThinking() + " enabledFunctions:"
					+ (allFunctions != null ? allFunctions.size() : 0));
		}

		ChatModelConfigOptions configOptions = new ChatModelConfigOptions(agentConfig.getTemperature(),
				agentConfig.getTopP(), agentConfig.getThinking(), allFunctions,
				createToolCallingManager(callBacksListener, allFunctions, additionalFunctions, runAs));
		IGConfigurableChatModel agentModel = copiedModel.cloneWithOptions(getId(), configOptions);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getAgentModel(...) for agent service id:" + getId());
		}
		return agentModel;
	}

	@Data
	@JsonClassDescription("Data structure that rappresents a meaningfull message to be notified to the user")
	public static class UserMessageToolParam {
		@NotNull
		@JsonPropertyDescription("Message showed to the actual user, please be kind, short and user understable")
		String message;
		@NotNull
		@JsonPropertyDescription("INFO for user informations, DEBUG for debug informations")
		INotificationSink.NotificationObject.NotificationType notificationType;
	}

	protected ToolCallback createUserMessageTool(INotificationSink notificationSink) {
		Consumer<UserMessageToolParam> activeConsumer = (param) -> {
			NotificationObject state = new NotificationObject(UUID.randomUUID().toString(), param.getMessage(),
					"pi pi-microchip-ai", param.getNotificationType());
			notificationSink.next(state);
		};
		ToolCallback callBack = ToolCallbackDeclarationUtil.declare(activeConsumer, NOTIFY_USER_TOOL,
				NOTIFY_USER_TOOL_DESCRIPTION, UserMessageToolParam.class);
		return callBack;
	}

	protected ToolCallingManager createToolCallingManager(ToolCallsListener callBacksListener,
			List<String> allFunctions, List<ToolCallback> additionalTools, ReactiveIdentityUtil runAs) {
		List<ToolCallback> wrapped = GAbstractConfigurableChatModel.wrapTools(runAs, callBacksListener, allFunctions,
				toolsRepositoryPattern);
		if (additionalTools != null && !additionalTools.isEmpty()) {
			wrapped = new ArrayList<ToolCallback>(wrapped);
			List<ToolCallback> newWrapped = GAbstractConfigurableChatModel.wrapTools(runAs, callBacksListener,
					additionalTools);
		}
		final Map<String, ToolCallback> map = new HashMap<>();
		for (ToolCallback toolCallback : wrapped) {
			map.put(toolCallback.getToolDefinition().name(), toolCallback);
		}
		return new AgentToolCallingManagerFactory(callBacksListener, allFunctions, wrapped, map).create();
	}

	protected static String extractContent(ChatResponse chatResponse) {
		if (chatResponse == null) {
			return "";
		}

		Generation result = chatResponse.getResult();
		if (result == null || result.getOutput() == null) {
			return "";
		}

		AssistantMessage output = result.getOutput();

		String text = output.getText();
		return text != null ? text : "";
	}

	protected static void inspectMetadata(ChatResponse chatResponse, Logger logger) {
		if (chatResponse == null) {
			return;
		}

		ChatResponseMetadata metadata = chatResponse.getMetadata();

		if (metadata != null) {
			Usage usage = metadata.getUsage();

			if (usage != null) {
				logger.debug("LLM token usage: promptTokens={}, completionTokens={}, totalTokens={}",
						usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
			}

			Object model = metadata.get("model");
			if (model != null) {
				logger.debug("LLM model: {}", model);
			}

			Object id = metadata.get("id");
			if (id != null) {
				logger.debug("LLM response id: {}", id);
			}
		}

		Generation result = chatResponse.getResult();
		if (result != null) {
			ChatGenerationMetadata generationMetadata = result.getMetadata();

			if (generationMetadata != null) {
				String finishReason = generationMetadata.getFinishReason();

				if (finishReason == null) {
					Object rawFinishReason = generationMetadata.get("FINISH_REASON");
					finishReason = Objects.toString(rawFinishReason, null);
				}

				if (finishReason != null) {
					logger.debug("LLM finish reason: {}", finishReason);
				}
			}
		}
	}

	protected static void inspectToolCalls(ChatResponse chatResponse, Vector<Object> rawToolCallsCumulator) {
		if (chatResponse == null) {
			return;
		}

		Generation result = chatResponse.getResult();
		if (result == null || result.getOutput() == null) {
			return;
		}

		AssistantMessage output = result.getOutput();

		/*
		 * Nota: con tool execution gestita internamente da Spring AI, spesso le
		 * tool-call intermedie non sono esposte nello stream applicativo. Spring AI
		 * documenta che, nel framework-controlled tool execution, i messaggi interni di
		 * tool execution non sono esposti all’utente.
		 */
		List<AssistantMessage.ToolCall> toolCalls = output.getToolCalls();

		if (!org.springframework.util.CollectionUtils.isEmpty(toolCalls)) {
			for (AssistantMessage.ToolCall toolCall : toolCalls) {

				rawToolCallsCumulator.add(toolCall);
			}
		}

		Map<String, Object> metadata = output.getMetadata();
		if (metadata != null && !metadata.isEmpty()) {
			Object rawToolCalls = metadata.get("tool_calls");
			if (rawToolCalls == null) {
				rawToolCalls = metadata.get("toolCalls");
			}
			if (rawToolCalls != null)
				rawToolCallsCumulator.add(rawToolCalls);

		}
	}

	protected GPromptTemplateConfig resolvePrompt(GPromptTemplateConfig prompt, String useCode, boolean nullable)
			throws AgentException {
		GPromptTemplateConfig resolved = prompt != null ? prompt
				: useCode != null ? promptsDao.findByPromptUse(useCode) : null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("resolvePrompt(...) inlinePromptProvided:" + (prompt != null) + " useCode:" + useCode
					+ " nullable:" + nullable + " resolved:" + (resolved != null));
		}
		if (resolved == null && !nullable)
			throw new AgentException("Mandatory prompt not present");
		return resolved;
	}

	private static final String END_AGENT_TURN_ITEM = "END AGENT TURN ITEM ";
	private static final String TURN_OUTPUT = "TURN OUTPUT:";
	private static final String TURN_INPUT = "TURN INPUT:";
	private static final String BEGIN_AGENT_TURN_ITEM = "BEGIN AGENT TURN ITEM ";
	private static final String END_ACTUAL_AGENT_CALL_HISTORY = "END ACTUAL AGENT CALL HISTORY";
	private static final String BEGIN_ACTUAL_AGENT_CALL_HISTORY = "BEGIN ACTUAL AGENT CALL HISTORY";
	private static final String END_SHARED_CONTEXT_DELTA = "END SHARED CONTEXT DELTA";
	private static final String BEGIN_SHARED_CONTEXT_DELTA = "BEGIN SHARED CONTEXT DELTA";
	private static final String END_AGENT_CONTEXT_CONTRIBUTION = "END AGENT CONTEXT CONTRIBUTION";
	private static final String BEGIN_CONTEXT_CONTRIBUTION_FROM_AGENT = "BEGIN CONTEXT CONTRIBUTION FROM AGENT:";
	private static final String DESCRIPTION_OF_YOUR_ROLE = "Description of your role: ";
	private static final String NEWLINE = "\r\n";
	private static final String YOU_ARE_AN_AGENT_WITH_ROLE = "Your agent role is: ";
	private static final String THE_DESCRIPTION_OF_THE_NETWORK_SCENARIO_IS = "The description of the network scenario is: ";
	private static final String ACTING_AS_PERSONA = "You are acting as: ";
	private static final String CAN_COMMUNICATE_WITH_AGENTS = "You can communicate with the following agents:";
	private static final String CANNOT_COMMUNICATE_WITH_AGENTS = "You cannot directly communicate with other agents.";
	private static final String ALLOWED_TO_CALL_TOOLS = "You are allowed to call tools.";
	private static final String NOT_ALLOWED_TO_CALL_TOOLS = "You are not allowed to call tools.";
	private static final String ALLOWED_TO_DELEGATE = "You are allowed to delegate tasks to other agents.";
	private static final String NOT_ALLOWED_TO_DELEGATE = "You are not allowed to delegate tasks to other agents.";
	private static final String AGENT_LIST_ITEM_PREFIX = "- ";
	private static final String AGENT_DESCRIPTION_SEPARATOR = ": ";
	private static final String CAPABILITY_INDENT = "    ";
	private static final String CAPABILITIES_BLOCK_BEGIN = "AGENT_CAPABILITIES_BEGIN";
	private static final String CAPABILITIES_BLOCK_END = "AGENT_CAPABILITIES_END";
	private static final String CAPABILITIES_SUMMARY_PREFIX = "summary: ";
	private static final String CAPABILITIES_LABEL = "what this agent can do:";
	private static final String CATALOGS_LABEL = "catalogs/collections it can search:";
	private static final String RESOURCES_LABEL = "systems and resources it can access:";
	private static final String TOOLS_LABEL = "tools it can call:";
	private static final String TRUNCATED_CONTENT_SUFFIX = "...(truncated content)";
	private static final int INPUT_SAMPLE_TOKEN_SIZE = 512;
	protected static final ObjectMapper objectMapper = new ObjectMapper();

	protected String buildRootJsonSchema(Map<String, Class<?>> typesMap) {
		ObjectNode root = objectMapper.createObjectNode();

		root.put("$schema", "https://json-schema.org/draft/2020-12/schema");
		root.put("type", "object");
		root.put("additionalProperties", false);

		ObjectNode properties = root.putObject("properties");
		ArrayNode required = root.putArray("required");

		for (Map.Entry<String, Class<?>> entry : typesMap.entrySet()) {
			String key = entry.getKey();
			Class<?> type = entry.getValue();

			BeanOutputConverter<?> converter = new BeanOutputConverter<>(type);

			String classSchemaAsString = converter.getJsonSchema();

			try {
				JsonNode classSchema = objectMapper.readTree(classSchemaAsString);
				properties.set(key, classSchema);
				required.add(key);
			} catch (Exception e) {
				throw new IllegalStateException(
						"Cannot generate JSON schema for key=" + key + ", type=" + type.getName(), e);
			}
		}

		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot serialize root JSON schema", e);
		}
	}

	protected <InputType, OutputType> Map<String, Object> createAgentTemplateParams(GPromptTemplateConfig prompt,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, Object input,
			IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget) {
		return createAgentTemplateParams(prompt, network, agentRole, contextAgentPersona, session, mySessionContext,
				input, agentsDao, actualContributionNr, tokenBudget, false).get(0);
	}

	protected <InputType, OutputType> List<Map<String, Object>> createAgentTemplateParams(GPromptTemplateConfig prompt,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, Object input,
			IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget, boolean splitByBudget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin createAgentTemplateParams(...) agentRole:"
					+ (agentRole != null ? agentRole.getCode() : null) + " contributionNr:" + actualContributionNr
					+ " tokenBudget:" + tokenBudget + " splitByBudget:" + splitByBudget);
		}
		Map<String, Object> constantParams = new HashMap<>();
		List<Map<String, Object>> vectorized = new ArrayList<Map<String, Object>>();
		final Map<String, Boolean> placeholders = prompt != null ? prompt.getPlaceholders() : Map.of();
		int remainingBudget = tokenBudget;

		if (placeholders.containsKey(AgentPromptTemplateParams.NETWORK_SCENARY_TEMPLATE_PARAM)) {
			String networkScenary = nullToEmpty(createNetworkScenaryDescription(network));
			constantParams.put(AgentPromptTemplateParams.NETWORK_SCENARY_TEMPLATE_PARAM, networkScenary);
			remainingBudget -= ITokensCountable.stringsTokensSize(networkScenary);
		}
		if (placeholders.containsKey(AgentPromptTemplateParams.AGENT_IDENTITY_TEMPLATE_PARAM)) {
			String agentIdentity = nullToEmpty(createAgentIdentityDescription(agentRole, contextAgentPersona));
			constantParams.put(AgentPromptTemplateParams.AGENT_IDENTITY_TEMPLATE_PARAM, agentIdentity);
			remainingBudget -= ITokensCountable.stringsTokensSize(agentIdentity);
		}
		if (placeholders.containsKey(AgentPromptTemplateParams.AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM)) {
			String comunicationCapabilities = nullToEmpty(
					createAgentCommunicationCapabilityDescription(agentRole, contextAgentPersona, network, agentsDao));
			constantParams.put(AgentPromptTemplateParams.AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM,
					comunicationCapabilities);
			remainingBudget -= ITokensCountable.stringsTokensSize(comunicationCapabilities);
		}
		if (placeholders.containsKey(AgentPromptTemplateParams.NETWORK_AGENTS_CAPABILITIES_TEMPLATE_PARAM)) {
			String networkAgentsCapabilities = nullToEmpty(
					createNetworkAgentsCapabilitiesDescription(network, agentsDao));
			constantParams.put(AgentPromptTemplateParams.NETWORK_AGENTS_CAPABILITIES_TEMPLATE_PARAM,
					networkAgentsCapabilities);
			remainingBudget -= ITokensCountable.stringsTokensSize(networkAgentsCapabilities);
		}
		if (placeholders.containsKey(AgentPromptTemplateParams.INPUT_TEMPLATE_PARAM)) {
			String renderedInput = nullToEmpty(renderContributionData(input));
			constantParams.put(AgentPromptTemplateParams.INPUT_TEMPLATE_PARAM, renderedInput);
			remainingBudget -= ITokensCountable.stringsTokensSize(renderedInput);
		}

		if (placeholders.containsKey(AgentPromptTemplateParams.PRIVATE_CONTEXT_TEMPLATE_PARAM)) {
			constantParams.put(AgentPromptTemplateParams.PRIVATE_CONTEXT_TEMPLATE_PARAM,
					nullToEmpty(render(mySessionContext, actualContributionNr, remainingBudget)));
		}
		final int fixedBudget = remainingBudget;
		if (placeholders.containsKey(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM)) {
			RenderedRange iterationValue = null;
			int startedContribution = mySessionContext.getLastContributionTurn() == null ? 0
					: mySessionContext.getLastContributionTurn();
			do {
				// Each shared-context window must still carry the constant agent placeholders
				// (identity, scenario, communication, input, private context); otherwise the
				// system/user templates that declare them render with missing variables.
				Map<String, Object> params = new HashMap<String, Object>(constantParams);
				iterationValue = render(session, startedContribution, actualContributionNr, fixedBudget, splitByBudget);
				String sharedContext = nullToEmpty(iterationValue.getContext());
				params.put(AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, sharedContext);
				startedContribution = iterationValue.getLastContribution();
				vectorized.add(params);
			} while (iterationValue != null && (splitByBudget && !iterationValue.isFinishedContributions()));
		} else {
			vectorized.add(constantParams);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End createAgentTemplateParams(...) produced " + vectorized.size()
					+ " parameter window(s), remainingBudget:" + remainingBudget);
		}
		return vectorized;
	}

	private static String nullToEmpty(String value) {
		return value != null ? value : "";
	}

	/**
	 * Backup rendering strategy for a parameter that has no dedicated
	 * {@link IGDocumentContentRenderer}: falls back to {@link Object#toString()}.
	 * If the actual runtime class of the parameter does not directly implement
	 * {@code toString()}, a warning is logged so such cases can be spotted.
	 */
	protected String genericRender(Object object) {
		if (object == null) {
			return "";
		}
		Class<?> actualClass = object.getClass();
		if (!directlyImplementsToString(actualClass)) {
			LOGGER.warn("The class {} does not directy implement the toString() method", actualClass.getName());
		}
		return object.toString();
	}

	private static boolean directlyImplementsToString(Class<?> type) {
		try {
			type.getDeclaredMethod("toString");
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	/**
	 * Tells whether the given prompt declares the supplied {placeholder} token.
	 */
	protected boolean isPlaceholderDeclared(GPromptTemplateConfig prompt, String placeholder) {
		return prompt != null && prompt.getPlaceholders().containsKey(placeholder);
	}

	protected String createAgentCommunicationCapabilityDescription(GAgentRole agentRole,
			AgentNetworkParticipant contextAgentPersona, GAgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao) {
		if (contextAgentPersona == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		List<String> peers = contextAgentPersona.getCommunicationList();
		if (peers != null && !peers.isEmpty()) {
			buffer.append(CAN_COMMUNICATE_WITH_AGENTS);
			buffer.append(NEWLINE);
			for (String peerCode : peers) {
				RuntimeAgentInfos peer = resolvePeer(peerCode, agentsDao);
				buffer.append(AGENT_LIST_ITEM_PREFIX);
				buffer.append(peerCode);
				String peerDescription = resolvePeerDescription(peer);
				if (peerDescription != null && !peerDescription.isBlank()) {
					buffer.append(AGENT_DESCRIPTION_SEPARATOR);
					buffer.append(peerDescription);
				}
				buffer.append(NEWLINE);
				// The peer's exported capabilities/catalogs/resources/tools are rendered
				// (indented) under each reachable agent so the coordinator can reason about
				// what every peer can actually do. The summary is omitted because it would
				// duplicate the peer description rendered on the line above.
				String peerCapabilities = resolvePeerCapabilities(peer);
				if (!peerCapabilities.isEmpty()) {
					buffer.append(peerCapabilities);
				}
			}
		} else {
			buffer.append(CANNOT_COMMUNICATE_WITH_AGENTS);
			buffer.append(NEWLINE);
		}
		if (contextAgentPersona.isAllowedToNotifyUser()) {
			buffer.append(USE_NOTIFY_USER_TOOL_PROMPT_PART);
			buffer.append(NEWLINE);
		}
		buffer.append(contextAgentPersona.isCanCallTools() ? ALLOWED_TO_CALL_TOOLS : NOT_ALLOWED_TO_CALL_TOOLS);
		buffer.append(NEWLINE);
		buffer.append(contextAgentPersona.isCanCallOtherAgents() ? ALLOWED_TO_DELEGATE : NOT_ALLOWED_TO_DELEGATE);
		buffer.append(NEWLINE);
		return buffer.toString();
	}

	private RuntimeAgentInfos resolvePeer(String peerCode, IGAgentsNetworkRuntimeDao agentsDao) {
		if (agentsDao == null) {
			return null;
		}
		try {
			return agentsDao.findAgentByCode(peerCode);
		} catch (AgentException e) {
			LOGGER.warn("Cannot resolve peer agent '{}' while building communication capability description", peerCode,
					e);
			return null;
		}
	}

	private String resolvePeerDescription(RuntimeAgentInfos peer) {
		if (peer == null) {
			return null;
		}
		if (peer.getConfig() != null && peer.getConfig().getDescription() != null) {
			return peer.getConfig().getDescription();
		}
		return peer.getService() != null ? peer.getService().getDescription() : null;
	}

	private String resolvePeerCapabilities(RuntimeAgentInfos peer) {
		if (peer == null || peer.getService() == null) {
			return "";
		}
		try {
			AgentCapabilities capabilities = peer.getService().getAgentCapabilities(peer.getConfig());
			return renderAgentCapabilities(capabilities, false);
		} catch (Throwable th) {
			LOGGER.warn("Cannot resolve capabilities for peer agent '{}' while building communication description",
					peer.getConfig() != null ? peer.getConfig().getCode() : peer.getService().getId(), th);
			return "";
		}
	}

	/**
	 * Renders an {@link AgentCapabilities} descriptor into the indented text shared
	 * in the network-of-agents description.
	 *
	 * @param includeSummary whether to render the {@code summary} line (omitted
	 *                       when the summary is already shown elsewhere, e.g. as
	 *                       the peer description)
	 */
	protected String renderAgentCapabilities(AgentCapabilities capabilities, boolean includeSummary) {
		if (capabilities == null || capabilities.isEmpty()) {
			return "";
		}
		// The body is rendered first so a capabilities block that ends up carrying no
		// renderable content (e.g. only a suppressed summary) is dropped entirely
		// instead of emitting an empty BEGIN/END envelope.
		StringBuffer body = new StringBuffer();
		if (includeSummary && capabilities.getSummary() != null && !capabilities.getSummary().isBlank()) {
			appendCapabilityLine(body, 1, CAPABILITIES_SUMMARY_PREFIX + capabilities.getSummary());
		}
		if (!capabilities.getCapabilities().isEmpty()) {
			appendCapabilityLine(body, 1, CAPABILITIES_LABEL);
			for (String capability : capabilities.getCapabilities()) {
				appendCapabilityBullet(body, 2, capability, null);
			}
		}
		appendCapabilityResourceList(body, capabilities.getCatalogs(), CATALOGS_LABEL);
		appendCapabilityResourceList(body, capabilities.getResources(), RESOURCES_LABEL);
		appendCapabilityResourceList(body, capabilities.getTools(), TOOLS_LABEL);
		if (body.length() == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		appendCapabilityLine(buffer, 1, CAPABILITIES_BLOCK_BEGIN);
		buffer.append(body);
		appendCapabilityLine(buffer, 1, CAPABILITIES_BLOCK_END);
		return buffer.toString();
	}

	private void appendCapabilityLine(StringBuffer buffer, int depth, String text) {
		buffer.append(CAPABILITY_INDENT.repeat(Math.max(1, depth)));
		buffer.append(text);
		buffer.append(NEWLINE);
	}

	private void appendCapabilityBullet(StringBuffer buffer, int depth, String name, String description) {
		StringBuilder line = new StringBuilder();
		line.append(AGENT_LIST_ITEM_PREFIX);
		if (name != null) {
			line.append(name);
		}
		if (description != null && !description.isBlank()) {
			line.append(AGENT_DESCRIPTION_SEPARATOR);
			line.append(description);
		}
		appendCapabilityLine(buffer, depth, line.toString());
	}

	private void appendCapabilityResourceList(StringBuffer buffer, List<AgentCapabilityResource> resources,
			String label) {
		if (resources == null || resources.isEmpty()) {
			return;
		}
		appendCapabilityLine(buffer, 1, label);
		for (AgentCapabilityResource resource : resources) {
			if (resource == null) {
				continue;
			}
			String name = resource.getName() != null ? resource.getName() : resource.getCode();
			appendCapabilityBullet(buffer, 2, name, resource.getDescription());
		}
	}

	protected String createAgentIdentityDescription(GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona) {
		StringBuffer buffer = new StringBuffer();
		if (contextAgentPersona != null && contextAgentPersona.getAgentContextualName() != null
				&& !contextAgentPersona.getAgentContextualName().isBlank()) {
			buffer.append(ACTING_AS_PERSONA);
			buffer.append(contextAgentPersona.getAgentContextualName());
			buffer.append(NEWLINE);
		}
		if (agentRole != null) {
			if (agentRole.getCode() != null) {
				buffer.append(YOU_ARE_AN_AGENT_WITH_ROLE);
				buffer.append(agentRole.getCode());
				buffer.append(NEWLINE);
			}
			String roleExplanation = agentRole.getLongExplanation() != null ? agentRole.getLongExplanation()
					: agentRole.getDescription();
			if (roleExplanation != null && !roleExplanation.isBlank()) {
				buffer.append(DESCRIPTION_OF_YOUR_ROLE);
				buffer.append(roleExplanation);
				buffer.append(NEWLINE);
			}
		}
		return buffer.toString();
	}

	protected String createNetworkScenaryDescription(GAgentsNetwork network) {
		StringBuffer buffer = new StringBuffer();
		if (network.getScenarioDescription() != null) {
			buffer.append(THE_DESCRIPTION_OF_THE_NETWORK_SCENARIO_IS + network.getScenarioDescription());
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

	/**
	 * Builds a network-wide roster of every participating agent's exported
	 * capabilities, catalogs, resources and tools, regardless of which agents the
	 * current agent can reach. Each participant is resolved through the runtime DAO
	 * (so its concrete service and configuration drive the exported capabilities)
	 * and rendered with the same indented capability block used for reachable
	 * peers. Returns an empty string when the roster cannot be resolved (e.g. no
	 * runtime DAO available), keeping the descriptor best-effort.
	 */
	protected String createNetworkAgentsCapabilitiesDescription(GAgentsNetwork network,
			IGAgentsNetworkRuntimeDao agentsDao) {
		if (network == null || network.getAgents() == null || agentsDao == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (AgentNetworkParticipant participant : network.getAgents()) {
			if (participant == null || participant.getAgentConfigCode() == null) {
				continue;
			}
			RuntimeAgentInfos agent = resolvePeer(participant.getAgentConfigCode(), agentsDao);
			if (agent == null || agent.getService() == null) {
				continue;
			}
			buffer.append(AGENT_LIST_ITEM_PREFIX);
			buffer.append(participant.getAgentConfigCode());
			String description = resolvePeerDescription(agent);
			if (description != null && !description.isBlank()) {
				buffer.append(AGENT_DESCRIPTION_SEPARATOR);
				buffer.append(description);
			}
			buffer.append(NEWLINE);
			String capabilities = resolvePeerCapabilities(agent);
			if (!capabilities.isEmpty()) {
				buffer.append(capabilities);
			}
		}
		return buffer.toString();
	}

	@AllArgsConstructor
	@Getter
	protected static class RenderedRange {
		final int startContribution;
		final int lastContribution;
		final String context;
		final boolean finishedContributions;
	}

	protected RenderedRange render(AgentsCollaborationSessionContext session, Integer lastTurn,
			int actualContributionNr, int remainingBudget, boolean split) {
		final int lastKnowledge = lastTurn == null ? 0 : lastTurn;
		List<AgentProducedSessionContribution> newGeneratedKnowledge = session
				.getSampledContributionsAfter(lastKnowledge);
		if (newGeneratedKnowledge.isEmpty()) {
			return new RenderedRange(0, 0, "", true);
		}
		// Group by agent, preserving first-appearance (chronological) order.
		List<AgentProducedSessionContribution> remainingContributions = session
				.getSampledContributionsAfter(lastKnowledge);
		if (split) {
			return renderBatchedContributions(remainingContributions, remainingBudget);
		} else {
			return renderAllContributions(remainingContributions);
		}

	}

	protected String renderContribution(AgentProducedSessionContribution agentProducedSessionContribution) {
		StringBuffer inner = new StringBuffer();
		String contributionAsString = renderContributionData(agentProducedSessionContribution.getData());
		if (contributionAsString != null && !contributionAsString.isBlank() && !contributionAsString.isEmpty()) {
			inner.append(BEGIN_CONTEXT_CONTRIBUTION_FROM_AGENT);
			inner.append(agentProducedSessionContribution.getAgentName());
			inner.append(NEWLINE);
			inner.append(contributionAsString);
			inner.append(END_AGENT_CONTEXT_CONTRIBUTION);
			inner.append(NEWLINE);
		}
		return inner.toString();
	}

	protected RenderedRange renderAllContributions(List<AgentProducedSessionContribution> remainingContributions) {
		StringBuffer inner = new StringBuffer();
		int minContribution = Integer.MAX_VALUE;
		int maxContribution = 0;
		for (AgentProducedSessionContribution agentProducedSessionContribution : remainingContributions) {
			minContribution = Math.min(agentProducedSessionContribution.getContributionUniqueNr(), minContribution);
			maxContribution = Math.max(agentProducedSessionContribution.getContributionUniqueNr(), maxContribution);
			inner.append(renderContribution(agentProducedSessionContribution));
		}
		StringBuffer buffer = new StringBuffer();
		if (!inner.isEmpty()) {
			buffer.append(BEGIN_SHARED_CONTEXT_DELTA);
			buffer.append(NEWLINE);
			buffer.append(inner.toString());
			buffer.append(END_SHARED_CONTEXT_DELTA);
			buffer.append(NEWLINE);
		}
		return new RenderedRange(minContribution, maxContribution, buffer.toString(), true);
	}

	protected RenderedRange renderBatchedContributions(List<AgentProducedSessionContribution> remainingContributions,
			int remainingBudget) {
		StringBuffer inner = new StringBuffer();
		int minContribution = Integer.MAX_VALUE;
		int maxContribution = 0;
		int insertedSlots = 0;
		for (AgentProducedSessionContribution agentProducedSessionContribution : remainingContributions) {
			String rendered = renderContribution(agentProducedSessionContribution);
			remainingBudget -= ITokensCountable.stringsTokensSize(rendered);
			if (remainingBudget >= 0) {
				minContribution = Math.min(agentProducedSessionContribution.getContributionUniqueNr(), minContribution);
				maxContribution = Math.max(agentProducedSessionContribution.getContributionUniqueNr(), maxContribution);
				inner.append(rendered);
				insertedSlots++;
			}
		}
		StringBuffer buffer = new StringBuffer();
		if (!inner.isEmpty()) {
			buffer.append(BEGIN_SHARED_CONTEXT_DELTA);
			buffer.append(NEWLINE);
			buffer.append(inner.toString());
			buffer.append(END_SHARED_CONTEXT_DELTA);
			buffer.append(NEWLINE);
		}
		return new RenderedRange(minContribution, maxContribution, buffer.toString(),
				insertedSlots == remainingContributions.size());
	}

	private String renderContributionData(Object data) {
		if (data == null) {
			return "";
		}
		IGDocumentContentRenderer<Object> renderer = rendererFactory.get(data);
		return renderer == null ? genericRender(data) : renderer.render(data);
	}

	protected <InputType, OutputType> String render(AgentPrivateSessionContext<InputType, OutputType> mySessionContext,
			int actualContributionNr, int remainingBudget) {
		Vector<AgentPrivateSessionContext<InputType, OutputType>.AgentInteraction> interactions = mySessionContext
				.getInteractions();
		if (interactions == null || interactions.isEmpty()) {
			return "";
		}
		// Render the input and output of every turn, then share the budget
		// proportionally
		// across all rendered pieces (two pieces - input and output - per turn).
		List<String> pieces = new ArrayList<>(interactions.size() * 2);
		for (AgentPrivateSessionContext<InputType, OutputType>.AgentInteraction agentInteraction : interactions) {
			pieces.add(renderHandlingTruncate(agentInteraction.getInputMessage()));
			pieces.add(renderOutput(agentInteraction.getOutput()));
		}
		List<String> fitted = allocateProportional(pieces, remainingBudget);
		StringBuffer buffer = new StringBuffer();
		buffer.append(BEGIN_ACTUAL_AGENT_CALL_HISTORY);
		buffer.append(NEWLINE);
		int index = 1;
		for (int turn = 0; turn < interactions.size(); turn++) {
			String renderedInput = fitted.get(turn * 2);
			String renderedOutput = fitted.get(turn * 2 + 1);
			buffer.append(BEGIN_AGENT_TURN_ITEM + index);
			buffer.append(NEWLINE);
			buffer.append(TURN_INPUT);
			buffer.append(NEWLINE);
			buffer.append(renderedInput);
			buffer.append(NEWLINE);
			buffer.append(TURN_OUTPUT);
			buffer.append(NEWLINE);
			buffer.append(renderedOutput);
			buffer.append(NEWLINE);
			buffer.append(END_AGENT_TURN_ITEM + index);
			buffer.append(NEWLINE);
			index++;
		}
		buffer.append(END_ACTUAL_AGENT_CALL_HISTORY);
		buffer.append(NEWLINE);
		return buffer.toString();
	}

	protected <OutputType> String renderOutput(OutputType output) {
		if (output == null) {
			return "";
		}
		IGDocumentContentRenderer<Object> renderer = rendererFactory.get(output);
		return renderer == null ? genericRender(output) : renderer.render(output);
	}

	protected <InputType> String renderHandlingTruncate(AgentsExchangeMessage<InputType> input) {
		return truncateToTokens(render(input), INPUT_SAMPLE_TOKEN_SIZE);
	}

	/**
	 * Shares a token budget across the given rendered pieces proportionally to
	 * their individual token sizes.
	 */
	private static List<String> allocateProportional(List<String> pieces, int budget) {
		int n = pieces.size();
		int[] sizes = new int[n];
		long total = 0;
		for (int i = 0; i < n; i++) {
			sizes[i] = pieces.get(i) == null ? 0 : ITokensCountable.stringsTokensSize(pieces.get(i));
			total += sizes[i];
		}
		if (total == 0 || total <= budget) {
			return new ArrayList<>(pieces);
		}
		List<String> out = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			int allowance = (int) ((long) budget * sizes[i] / total);
			out.add(truncateToTokens(pieces.get(i), allowance));
		}
		return out;
	}

	/**
	 * Truncates the given text to (approximately) the supplied token allowance.
	 */
	private static String truncateToTokens(String text, int allowanceTokens) {
		if (text == null || text.isEmpty()) {
			return "";
		}
		if (allowanceTokens <= 0) {
			return TRUNCATED_CONTENT_SUFFIX;
		}
		int howManyTokens = ITokensCountable.stringsTokensSize(text);
		if (howManyTokens <= allowanceTokens) {
			return text;
		}
		int approximatedIndex = (int) (((double) allowanceTokens) * 4.2);
		int maxIndex = Math.min(approximatedIndex, text.length());
		return text.substring(0, maxIndex) + TRUNCATED_CONTENT_SUFFIX;
	}

	protected <InputType> String render(AgentsExchangeMessage<InputType> input) {
		Object payload = input.getPayload();
		if (payload == null)
			return "";
		String inputAsString = null;
		IGDocumentContentRenderer<Object> renderer = rendererFactory.get(payload);
		if (renderer == null) {
			inputAsString = genericRender(payload);
		} else {
			inputAsString = renderer.render(payload);
		}
		return inputAsString;
	}

}
