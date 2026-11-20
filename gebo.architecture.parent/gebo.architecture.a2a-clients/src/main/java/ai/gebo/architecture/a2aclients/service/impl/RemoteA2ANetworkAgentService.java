package ai.gebo.architecture.a2aclients.service.impl;

import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.IGConversationFilesKeeper;
import ai.gebo.architecture.ai.files.IGConversationGeneratedFilesStore;
import org.a2aproject.sdk.spec.FileWithUri;
import org.a2aproject.sdk.spec.FileWithBytes;
import org.a2aproject.sdk.spec.FilePart;
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
import ai.gebo.architecture.agents.services.IGNetworkOfAgentsSuspensionHandler;
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
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

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

	/** The remote agent's answer is a RESPONSE, shared in the session context. */
	@Override
	public MessageSemantic getProducedMessageSemantic() {
		return MessageSemantic.RESPONSE;
	}

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
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao, GeboToolCalling toolCalling, IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws LLMConfigException, AgentException {

		String inputText = msg.getPayload() != null ? msg.getPayload() : "";
		if (contextAgentPersona.isAllowedToNotifyUser() && notificationSink != null) {
			notificationSink.next("Calling remote A2A agent '" + remoteConfig.getExportingPrefix() + "'",
					NotificationType.INFO);
		}
		try {
			EventKind result = connector.sendMessage(remoteConfig, inputText, session.getId());
			// as the user the network runs for: a file the agent sent is kept in their conversation
			String outputText = runAs != null
					? runAs.doRunAsWithReturn(() -> extractText(result, chatRequestContext))
					: extractText(result, chatRequestContext);
			GAgentRole role = resolveRole(config);
			AgentsExchangeMessage<String> out = new AgentsExchangeMessage<>(session.getId(),
					getProducedMessageSemantic(), contextAgentPersona.getNetworkAgentName(), role, msg.getFromAgent(),
					outputText, 1);
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
	 * The prompts are built by the remote agent, not here: a role, when the configuration
	 * names one, only tags the response, else {@link #resolveRole(GAgentConfig)} makes one up.
	 */
	@Override
	public boolean isBuildingPrompts() {
		return false;
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

	private String extractText(EventKind result, IChatRequestContext context) {
		if (result instanceof Message message) {
			return renderParts(message.parts(), context);
		}
		if (result instanceof Task task) {
			if (task.artifacts() != null) {
				StringBuilder sb = new StringBuilder();
				for (Artifact artifact : task.artifacts()) {
					String rendered = renderParts(artifact.parts(), context);
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
				String rendered = renderParts(task.status().message().parts(), context);
				if (!rendered.isBlank()) {
					return rendered;
				}
			}
			if (task.history() != null && !task.history().isEmpty()) {
				return renderParts(task.history().get(task.history().size() - 1).parts(), context);
			}
		}
		return "";
	}

	/**
	 * The parts of a remote agent's answer as the network reads them: the texts as
	 * they are; a file sent as bytes is kept in the conversation and named in the
	 * text, so that it is not lost - the user finds it among the conversation's files,
	 * the tools working on files open it, a model that takes such files sees it on
	 * the next requests; a file sent as an address of the agent's own is fetched and
	 * kept the same way, one at an address elsewhere is named with its address.
	 */
	private String renderParts(List<Part<?>> parts, IChatRequestContext context) {
		if (parts == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Part<?> part : parts) {
			String rendered = null;
			if (part instanceof TextPart textPart) {
				rendered = textPart.text();
			} else if (part instanceof FilePart filePart && filePart.file() instanceof FileWithBytes file) {
				rendered = keep(file, context);
			} else if (part instanceof FilePart filePart && filePart.file() instanceof FileWithUri file) {
				rendered = fetchAndKeep(file, context);
			} else if (part != null && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Remote A2A agent " + getId() + " sent a " + part.getClass().getSimpleName()
						+ ", which is not read");
			}
			if (rendered != null) {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(rendered);
			}
		}
		return sb.toString();
	}

	/**
	 * Keeps a file a remote agent sent in the conversation the network works for and
	 * tells where it is; when it cannot be kept - no conversation, no store, unreadable
	 * bytes - it is named with its type.
	 */
	private String keep(FileWithBytes file, IChatRequestContext context) {
		final String described = "[file " + (file.name() != null ? file.name() : "")
				+ (file.mimeType() != null ? " (" + file.mimeType() + ")" : "") + "]";
		if (file.bytes() == null || file.bytes().isBlank()) {
			return described;
		}
		final byte[] data;
		try {
			data = java.util.Base64.getDecoder().decode(file.bytes().trim());
		} catch (IllegalArgumentException notBase64) {
			LOGGER.error("The file " + file.name() + " sent by remote A2A agent " + getId() + " is not readable",
					notBase64);
			return described;
		}
		return keep(file.name(), file.mimeType(), data, described, context);
	}

	/**
	 * A file the remote agent names by address: fetched and kept when the address
	 * is the agent's own, else - or when it cannot be fetched or kept - named with
	 * its address, as the agent gave it.
	 */
	private String fetchAndKeep(FileWithUri file, IChatRequestContext context) {
		final String described = "[file " + (file.name() != null ? file.name() + " " : "") + file.uri()
				+ (file.mimeType() != null ? " (" + file.mimeType() + ")" : "") + "]";
		if (connector == null || context == null || context.getSessionID() == null) {
			return described;
		}
		try {
			final A2AClientConnector.LinkedFile fetched = connector.fetchLinkedFile(remoteConfig, file.uri());
			if (fetched == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Remote A2A agent " + getId() + " named a file at " + file.uri()
							+ ", which is not an address of its own: it is not fetched");
				}
				return described;
			}
			String name = file.name();
			if (name == null || name.isBlank()) {
				final String path = java.net.URI.create(file.uri()).getPath();
				name = path != null && path.lastIndexOf('/') < path.length() - 1
						? path.substring(path.lastIndexOf('/') + 1)
						: null;
			}
			return keep(name, file.mimeType() != null ? file.mimeType() : fetched.contentType(), fetched.bytes(),
					described, context);
		} catch (Exception e) {
			LOGGER.error("The file " + file.uri() + " named by remote A2A agent " + getId() + " cannot be fetched", e);
			return described;
		}
	}

	/** Keeps the bytes in the conversation and tells where; {@code described} when it cannot. */
	private String keep(String name, String mimeType, byte[] data, String described, IChatRequestContext context) {
		final String session = context != null ? context.getSessionID() : null;
		if (session == null) {
			return described;
		}
		try {
			final IGConversationGeneratedFilesStore store = runtimeBinder
					.getImplementationOf(IGConversationGeneratedFilesStore.class);
			if (store == null) {
				return described;
			}
			final GLibraryFile kept = store.store(session,
					name != null && !name.isBlank() ? name : remoteConfig.getExportingPrefix() + "-file", mimeType,
					data);
			final IGConversationFilesKeeper keeper = IGConversationFilesKeeper.of(context.getToolsContext());
			if (keeper != null) {
				keeper.written(kept);
			}
			return "[file kept in this conversation as " + kept.location() + ":" + kept.path() + " ("
					+ kept.contentType() + ", " + kept.size() + " bytes)]";
		} catch (Exception e) {
			LOGGER.error("The file " + name + " sent by remote A2A agent " + getId()
					+ " cannot be kept in session " + session, e);
			return described;
		}
	}
}
