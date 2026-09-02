package ai.gebo.architecture.a2aclients.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.a2aclients.model.A2ARemoteSkill;
import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import org.a2aproject.sdk.spec.Artifact;
import org.a2aproject.sdk.spec.EventKind;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.Task;
import org.a2aproject.sdk.spec.TextPart;

/**
 * Exposes a registered remote A2A agent as a first-class {@code String -> String}
 * network agent, so it can be dropped into any {@code GAgentsNetwork} exactly like
 * a local agent. This is the integration seam: {@code onMessage} translates the
 * inbound exchange message into an A2A {@code message/send} call through the
 * {@link A2AClientConnector} and returns the remote agent's reply as the response
 * contribution.
 * <p>
 * Lightweight by design — it does not extend the LLM-invoking agent base; it only
 * implements {@link IGNetworkAgentService} and resolves its configs/role lazily
 * through the {@link IGRuntimeBinder}, the same way the abstract base resolves
 * {@link #getAccessibleConfigurations()}.
 */
public class RemoteA2ANetworkAgentService implements IGNetworkAgentService<String, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteA2ANetworkAgentService.class);

	private final A2ARemoteAgentConfig remoteConfig;
	private final A2AClientConnector connector;
	private final IGRuntimeBinder runtimeBinder;
	private final String id;

	public RemoteA2ANetworkAgentService(A2ARemoteAgentConfig remoteConfig, A2AClientConnector connector,
			IGRuntimeBinder runtimeBinder) {
		this.remoteConfig = remoteConfig;
		this.connector = connector;
		this.runtimeBinder = runtimeBinder;
		this.id = A2ARemoteServiceIds.serviceId(remoteConfig);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDescription() {
		String desc = remoteConfig.getDescription();
		return (desc != null && !desc.isBlank()) ? desc : "Remote A2A agent " + remoteConfig.getExportingPrefix();
	}

	@Override
	public Class<String> getInputType() {
		return String.class;
	}

	@Override
	public Class<String> getOutputType() {
		return String.class;
	}

	@Override
	public List<GAgentConfig> getAccessibleConfigurations() {
		IAgentConfigDao configsDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		IGSecurityService securityService = runtimeBinder.getImplementationOf(IGSecurityService.class);
		List<GAgentConfig> configs = configsDao.findByAgentServiceId(getId());
		return securityService.filterCanDoAction(configs, true, AclGrantType.EXECUTE);
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = new AgentCapabilities(getDescription());
		List<A2ARemoteSkill> skills = remoteConfig.getSkills();
		if (skills != null) {
			for (A2ARemoteSkill skill : skills) {
				if (skill.getDeletedOnRemote() != null && skill.getDeletedOnRemote()) {
					continue;
				}
				capabilities.addCapability(skill.getName() != null ? skill.getName() : skill.getId());
				capabilities.addResource(AgentCapabilityResource.of(skill.getId(), skill.getName(),
						skill.getDescription()));
			}
		}
		return capabilities;
	}

	@Override
	public List<AgentsExchangeMessage<String>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<String> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext<String, String> mySessionContext,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {

		String inputText = msg.getPayload() != null ? msg.getPayload() : "";
		if (contextAgentPersona.isAllowedToNotifyUser() && notificationSink != null) {
			notificationSink.next("Calling remote A2A agent '" + remoteConfig.getExportingPrefix() + "'",
					NotificationType.INFO);
		}
		try {
			EventKind result = connector.sendMessage(remoteConfig, inputText, session.getId());
			String outputText = extractText(result);
			GAgentRole role = resolveRole(config);
			AgentsExchangeMessage<String> out = new AgentsExchangeMessage<>(session.getId(), MessageSemantic.RESPONSE,
					contextAgentPersona.getNetworkAgentName(), role, msg.getFromAgent(), outputText, 1);
			if (contextAgentPersona.isAllowedToNotifyUser() && notificationSink != null) {
				notificationSink.next("Remote A2A agent '" + remoteConfig.getExportingPrefix() + "' responded",
						NotificationType.INFO);
			}
			return List.of(out);
		} catch (Throwable t) {
			LOGGER.error("Error calling remote A2A agent " + getId(), t);
			throw new AgentException("Remote A2A agent '" + remoteConfig.getExportingPrefix() + "' call failed: "
					+ t.getMessage(), t);
		}
	}

	/**
	 * Resolves the role for the response message from the agent config, falling back
	 * to a synthetic role bound to this service id when none is configured.
	 */
	private GAgentRole resolveRole(GAgentConfig config) {
		if (config != null && config.getAgentRoleCode() != null) {
			IAgentRoleDao roleDao = runtimeBinder.getImplementationOf(IAgentRoleDao.class);
			GAgentRole role = roleDao.findByCode(config.getAgentRoleCode());
			if (role != null) {
				return role;
			}
		}
		GAgentRole synthetic = new GAgentRole();
		synthetic.setCode(getId());
		synthetic.setDescription(getDescription());
		synthetic.setLongExplanation("Remote A2A agent reached over the Agent2Agent protocol");
		return synthetic;
	}

	private String extractText(EventKind result) {
		if (result instanceof Message message) {
			return renderParts(message.parts());
		}
		if (result instanceof Task task) {
			if (task.artifacts() != null) {
				StringBuilder sb = new StringBuilder();
				for (Artifact artifact : task.artifacts()) {
					String rendered = renderParts(artifact.parts());
					if (!rendered.isBlank()) {
						if (sb.length() > 0) {
							sb.append("\n");
						}
						sb.append(rendered);
					}
				}
				if (sb.length() > 0) {
					return sb.toString();
				}
			}
			if (task.status() != null && task.status().message() != null) {
				String rendered = renderParts(task.status().message().parts());
				if (!rendered.isBlank()) {
					return rendered;
				}
			}
			if (task.history() != null && !task.history().isEmpty()) {
				return renderParts(task.history().get(task.history().size() - 1).parts());
			}
		}
		return "";
	}

	private String renderParts(List<Part<?>> parts) {
		if (parts == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Part<?> part : parts) {
			if (part instanceof TextPart textPart) {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(textPart.text());
			}
		}
		return sb.toString();
	}
}
