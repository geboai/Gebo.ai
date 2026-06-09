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
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

public abstract class GAbstractGenericalNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalAgentService {

	public static final String INPUT_TEMPLATE_PARAM = "INPUT";
	public static final String NETWORK_SCENARY_TEMPLATE_PARAM = "NETWORK_SCENARY";
	protected static final ObjectMapper objectMapper = new ObjectMapper();
	public static final String PRIVATE_CONTEXT_TEMPLATE_PARAM = "PRIVATE_CONTEXT";
	public static final String SHARED_CONTEXT_TEMPLATE_PARAM = "SHARED_CONTEXT";
	public static final String FORMAT = "format";

	public GAbstractGenericalNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			GAgentConfigRepository configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
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

	protected Map<String, Object> createAgentTemplateParams(AgentsNetwork network, GAgentRole agentRole,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, AgentsExchangeMessage<InputType> msg) {
		Map<String, Object> params = new HashMap<>();
		params.put(NETWORK_SCENARY_TEMPLATE_PARAM, createNetworkScenaryDescription(network, agentRole));
		params.put(SHARED_CONTEXT_TEMPLATE_PARAM, render(session));
		params.put(PRIVATE_CONTEXT_TEMPLATE_PARAM, render(mySessionContext));
		params.put(INPUT_TEMPLATE_PARAM, render(msg));
		return params;
	}

	protected Object createNetworkScenaryDescription(AgentsNetwork network, GAgentRole agentRole) {
		// TODO Auto-generated method stub
		return null;
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
