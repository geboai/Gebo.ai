package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.ai.converter.BeanOutputConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

public abstract class GAbstractGenericalNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalAgentService {

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
	public static final String AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM = "AGENT_COMUNICATION_CAPABILITY";
	public static final String AGENT_IDENTITY_TEMPLATE_PARAM = "AGENT_IDENTITY";
	public static final String INPUT_TEMPLATE_PARAM = "INPUT";
	public static final String NETWORK_SCENARY_TEMPLATE_PARAM = "NETWORK_SCENARY";
	public static final String PRIVATE_CONTEXT_TEMPLATE_PARAM = "PRIVATE_CONTEXT";
	public static final String SHARED_CONTEXT_TEMPLATE_PARAM = "SHARED_CONTEXT";
	public static final String FORMAT_TEMPLATE_PARAM = "format";

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
	private static final String TRUNCATED_CONTENT_SUFFIX = "...(truncated content)";
	protected static final ObjectMapper objectMapper = new ObjectMapper();
	protected final IGDocumentContentRendererProvider rendererFactory;

	public GAbstractGenericalNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao);
		this.rendererFactory = rendererFactory;

	}

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

				/*
				 * Lo schema del BeanOutputConverter è uno schema completo. Qui lo inseriamo
				 * come schema della proprietà "key".
				 */
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

	protected Map<String, Object> createAgentTemplateParams(GPromptTemplateConfig prompt, GAgentsNetwork network,
			GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, AgentsExchangeMessage<InputType> msg,
			IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget) {
		Map<String, Object> params = new HashMap<>();
		// Only the {placeholder} tokens actually declared in the prompt are populated:
		// this avoids computing (and spending the token budget on) context the template
		// does not reference, and guarantees a non-null value for every declared
		// placeholder so PromptTemplate.render() never fails on a missing/null value.
		final Map<String, Boolean> placeholders = prompt != null ? prompt.getPlaceholders() : Map.of();
		int remainingBudget = tokenBudget;

		if (placeholders.containsKey(NETWORK_SCENARY_TEMPLATE_PARAM)) {
			String networkScenary = nullToEmpty(createNetworkScenaryDescription(network));
			params.put(NETWORK_SCENARY_TEMPLATE_PARAM, networkScenary);
			remainingBudget -= ITokensCountable.stringsTokensSize(networkScenary);
		}
		if (placeholders.containsKey(AGENT_IDENTITY_TEMPLATE_PARAM)) {
			String agentIdentity = nullToEmpty(createAgentIdentityDescription(agentRole, contextAgentPersona));
			params.put(AGENT_IDENTITY_TEMPLATE_PARAM, agentIdentity);
			remainingBudget -= ITokensCountable.stringsTokensSize(agentIdentity);
		}
		if (placeholders.containsKey(AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM)) {
			String comunicationCapabilities = nullToEmpty(
					createAgentCommunicationCapabilityDescription(agentRole, contextAgentPersona, network, agentsDao));
			params.put(AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM, comunicationCapabilities);
			remainingBudget -= ITokensCountable.stringsTokensSize(comunicationCapabilities);
		}
		if (placeholders.containsKey(INPUT_TEMPLATE_PARAM)) {
			String renderedInput = nullToEmpty(render(msg));
			params.put(INPUT_TEMPLATE_PARAM, renderedInput);
			remainingBudget -= ITokensCountable.stringsTokensSize(renderedInput);
		}
		if (placeholders.containsKey(SHARED_CONTEXT_TEMPLATE_PARAM)) {
			String sharedContext = nullToEmpty(
					render(session, mySessionContext.getLastContributionTurn(), actualContributionNr, remainingBudget));
			params.put(SHARED_CONTEXT_TEMPLATE_PARAM, sharedContext);
			remainingBudget -= ITokensCountable.stringsTokensSize(sharedContext);
		}
		if (placeholders.containsKey(PRIVATE_CONTEXT_TEMPLATE_PARAM)) {
			params.put(PRIVATE_CONTEXT_TEMPLATE_PARAM,
					nullToEmpty(render(mySessionContext, actualContributionNr, remainingBudget)));
		}

		return params;
	}

	private static String nullToEmpty(String value) {
		return value != null ? value : "";
	}

	/**
	 * Backup rendering strategy for a parameter that has no dedicated
	 * {@link IGDocumentContentRenderer}: falls back to {@link Object#toString()}.
	 * If the actual runtime class of the parameter does not directly implement
	 * {@code toString()} (i.e. it would expose the default {@link Object} rendering),
	 * a warning is logged so such cases can be spotted and given a proper renderer.
	 *
	 * @param object the parameter to render; may be {@code null}.
	 * @return the {@code toString()} representation, or an empty string when {@code null}.
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
	 * Used to gate the computation/population of template params (including the
	 * structured-output {@value #FORMAT_TEMPLATE_PARAM} placeholder) so that
	 * nothing is computed for a placeholder the template does not reference.
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
				buffer.append(AGENT_LIST_ITEM_PREFIX);
				buffer.append(peerCode);
				String peerDescription = resolvePeerDescription(peerCode, agentsDao);
				if (peerDescription != null && !peerDescription.isBlank()) {
					buffer.append(AGENT_DESCRIPTION_SEPARATOR);
					buffer.append(peerDescription);
				}
				buffer.append(NEWLINE);
			}
		} else {
			buffer.append(CANNOT_COMMUNICATE_WITH_AGENTS);
			buffer.append(NEWLINE);
		}
		buffer.append(contextAgentPersona.isCanCallTools() ? ALLOWED_TO_CALL_TOOLS : NOT_ALLOWED_TO_CALL_TOOLS);
		buffer.append(NEWLINE);
		buffer.append(contextAgentPersona.isCanCallOtherAgents() ? ALLOWED_TO_DELEGATE : NOT_ALLOWED_TO_DELEGATE);
		buffer.append(NEWLINE);
		return buffer.toString();
	}

	private String resolvePeerDescription(String peerCode, IGAgentsNetworkRuntimeDao agentsDao) {
		if (agentsDao == null) {
			return null;
		}
		try {
			RuntimeAgentInfos peer = agentsDao.findAgentByCode(peerCode);
			if (peer == null) {
				return null;
			}
			if (peer.getConfig() != null && peer.getConfig().getDescription() != null) {
				return peer.getConfig().getDescription();
			}
			return peer.getService() != null ? peer.getService().getDescription() : null;
		} catch (AgentException e) {
			LOGGER.warn("Cannot resolve peer agent '{}' while building communication capability description", peerCode,
					e);
			return null;
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

	protected String render(AgentsCollaborationSessionContext session, Integer lastTurn, int actualContributionNr,
			int remainingBudget) {
		final int lastKnowledge = lastTurn == null ? 0 : lastTurn;
		List<AgentProducedSessionContribution> newGeneratedKnowledge = session
				.getSampledContributionsAfter(lastKnowledge);
		if (newGeneratedKnowledge.isEmpty()) {
			return "";
		}
		// Group by agent, preserving first-appearance (chronological) order.
		LinkedHashMap<String, List<AgentProducedSessionContribution>> contributions = new LinkedHashMap<>();
		for (AgentProducedSessionContribution contrib : newGeneratedKnowledge) {
			contributions.computeIfAbsent(contrib.getAgentName(), name -> new ArrayList<>()).add(contrib);
		}
		// Render each agent group body, then share the budget proportionally across groups.
		List<String> agentNames = new ArrayList<>(contributions.keySet());
		List<String> bodies = new ArrayList<>(agentNames.size());
		for (String agentName : agentNames) {
			StringBuffer body = new StringBuffer();
			for (AgentProducedSessionContribution contribution : contributions.get(agentName)) {
				String rendered = renderContributionData(contribution.getData());
				if (rendered.isBlank()) {
					continue;
				}
				body.append(rendered);
				body.append(NEWLINE);
			}
			bodies.add(body.toString());
		}
		List<String> fitted = allocateProportional(bodies, remainingBudget);
		StringBuffer inner = new StringBuffer();
		for (int i = 0; i < agentNames.size(); i++) {
			String body = fitted.get(i);
			if (body == null || body.isBlank()) {
				continue;
			}
			inner.append(BEGIN_CONTEXT_CONTRIBUTION_FROM_AGENT);
			inner.append(agentNames.get(i));
			inner.append(NEWLINE);
			inner.append(body);
			inner.append(END_AGENT_CONTEXT_CONTRIBUTION);
			inner.append(NEWLINE);
		}
		if (inner.length() == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(BEGIN_SHARED_CONTEXT_DELTA);
		buffer.append(NEWLINE);
		buffer.append(inner);
		buffer.append(END_SHARED_CONTEXT_DELTA);
		buffer.append(NEWLINE);
		return buffer.toString();
	}

	private String renderContributionData(Object data) {
		if (data == null) {
			return "";
		}
		IGDocumentContentRenderer<Object> renderer = rendererFactory.get(data);
		return renderer == null ? genericRender(data) : renderer.render(data);
	}

	private final int INPUT_SAMPLE_TOKEN_SIZE = 512;

	protected String render(AgentPrivateSessionContext<InputType, OutputType> mySessionContext,
			int actualContributionNr, int remainingBudget) {
		Vector<AgentPrivateSessionContext<InputType, OutputType>.AgentInteraction> interactions = mySessionContext
				.getInteractions();
		if (interactions == null || interactions.isEmpty()) {
			return "";
		}
		// Render the input and output of every turn, then share the budget proportionally
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

	protected String renderOutput(OutputType output) {
		if (output == null) {
			return "";
		}
		IGDocumentContentRenderer<Object> renderer = rendererFactory.get(output);
		return renderer == null ? genericRender(output) : renderer.render(output);
	}

	protected String renderHandlingTruncate(AgentsExchangeMessage<InputType> input) {
		return truncateToTokens(render(input), INPUT_SAMPLE_TOKEN_SIZE);
	}

	/**
	 * Shares a token budget across the given rendered pieces proportionally to their
	 * individual token sizes. If everything already fits, the pieces are returned
	 * unchanged; otherwise each piece is truncated to its proportional allowance so
	 * the whole section fits within {@code budget}. The returned list preserves order
	 * and size, so callers can reassemble their structure around the fitted pieces.
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
	 * Truncates the given text to (approximately) the supplied token allowance, keeping
	 * the head and appending a truncation marker when content is dropped.
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

	protected String render(AgentsExchangeMessage<InputType> input) {
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
