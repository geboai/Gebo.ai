package ai.gebo.architecture.agents.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.converter.BeanOutputConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

public abstract class GAbstractGenericalNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalAgentService {

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
	protected static final ObjectMapper objectMapper = new ObjectMapper();
	
	public GAbstractGenericalNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IAgentConfigDao configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao);

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

	protected Map<String, Object> createAgentTemplateParams(GAgentsNetwork network, GAgentRole agentRole,
			AgentNetworkParticipant contextAgentPersona, AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, AgentsExchangeMessage<InputType> msg,
			IGAgentsNetworkRuntimeDao agentsDao) {
		Map<String, Object> params = new HashMap<>();
		params.put(NETWORK_SCENARY_TEMPLATE_PARAM, createNetworkScenaryDescription(network));
		params.put(AGENT_IDENTITY_TEMPLATE_PARAM, createAgentIdentityDescription(agentRole, contextAgentPersona));
		params.put(AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM,
				createAgentCommunicationCapabilityDescription(agentRole, contextAgentPersona, network, agentsDao));
		params.put(SHARED_CONTEXT_TEMPLATE_PARAM, render(session));
		params.put(PRIVATE_CONTEXT_TEMPLATE_PARAM, render(mySessionContext));
		params.put(INPUT_TEMPLATE_PARAM, render(msg));
		return params;
	}

	protected String createAgentCommunicationCapabilityDescription(GAgentRole agentRole,
			AgentNetworkParticipant contextAgentPersona, GAgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String createAgentIdentityDescription(GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object createNetworkScenaryDescription(GAgentsNetwork network) {
		StringBuffer buffer = new StringBuffer();
		if (network.getScenarioDescription() != null) {
			buffer.append(THE_DESCRIPTION_OF_THE_NETWORK_SCENARIO_IS + network.getScenarioDescription());
			buffer.append(NEWLINE);
		}
		return buffer.toString();
	}

	protected Object render(AgentPrivateSessionContext<InputType, OutputType> mySessionContext) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object render(AgentsCollaborationSessionContext session) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object render(AgentsExchangeMessage<InputType> msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
