package ai.gebo.architecture.a2aserver.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import lombok.AllArgsConstructor;

/**
 * Bridges an A2A task to the exported network or agent: resolves the skill on
 * the server configuration and runs it through {@link GTextAgentsNetworkRunner}
 * (text in, text out) as the caller whose identity is in the current security
 * context.
 */
@Service
@AllArgsConstructor
public class A2ATaskBridge {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2ATaskBridge.class);
	private static final String SINGLE_AGENT_CODE_PREFIX = "a2a-single-";
	private final GTextAgentsNetworkRunner runner;
	/** Runs an exported network in the environment a chat would have given it. */
	private final A2AChatEnvironmentRunner chatEnvironmentRunner;

	/**
	 * Runs the exported skill as the caller whose identity is in the current
	 * security context: the exported network, or the single exported agent as a
	 * network of its own.
	 */
	public String run(A2AServerConfig serverConfig, String skillId, String inputText, INotificationSink sink)
			throws AgentException {
		A2AExportedAgent exported = resolveExport(serverConfig, skillId);
		if (exported == null) {
			throw new AgentException("No exported skill '" + skillId + "' on A2A server '"
					+ serverConfig.getExportedRelativeUrl() + "'");
		}
		try {
			if (exported.getKind() == A2AExportedAgent.Kind.NETWORK) {
				return runner.runNetwork(exported.getNetworkCode(), inputText, sink, "A2A");
			}
			return runner.runAgent(exported.getAgentConfigCode(), SINGLE_AGENT_CODE_PREFIX, inputText, sink, "A2A");
		} catch (AgentException ae) {
			LOGGER.error("A2A task execution failed for skill '" + skillId + "'", ae);
			throw ae;
		}
	}

	/** The export a skill id names, or null. */
	public A2AExportedAgent exportOf(A2AServerConfig serverConfig, String skillId) {
		return resolveExport(serverConfig, skillId);
	}

	/**
	 * Runs an exported network in the environment of the chat profile the export
	 * states, answering with the frames of the run and the response of its last
	 * one. Only a network has such an environment; a single exported agent is run
	 * the plain way.
	 *
	 * @param chatContextCode the conversation the caller named, or null for a new
	 *                        one
	 * @return the run, or null when this export is not a network
	 */
	public ChatEnvironmentRun runInChatEnvironment(A2AServerConfig serverConfig,
			String skillId, A2AInboundResources inbound, String chatContextCode, INotificationSink sink)
			throws AgentException {
		final A2AExportedAgent exported = resolveExport(serverConfig, skillId);
		if (exported == null) {
			throw new AgentException("No exported skill '" + skillId + "' on A2A server '"
					+ serverConfig.getExportedRelativeUrl() + "'");
		}
		if (exported.getKind() != A2AExportedAgent.Kind.NETWORK) {
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A2A skill '" + skillId + "' runs network:" + exported.getNetworkCode()
					+ " in the environment of chat profile:" + exported.getChatProfileCode() + " conversation:"
					+ chatContextCode);
		}
		return chatEnvironmentRunner.run(exported.getNetworkCode(),
				chatEnvironmentRunner.requestOf(inbound.getText(), exported.getChatProfileCode(), chatContextCode),
				inbound, sink);
	}

	private A2AExportedAgent resolveExport(A2AServerConfig serverConfig, String skillId) {
		if (serverConfig.getExportedAgents() == null) {
			return null;
		}
		for (A2AExportedAgent exported : serverConfig.getExportedAgents()) {
			String id = exported.getSkillName();
			if (id != null && id.equals(skillId)) {
				return exported;
			}
		}
		// Fallback: match by the underlying network/agent code when skillName is unset.
		for (A2AExportedAgent exported : serverConfig.getExportedAgents()) {
			String code = exported.getKind() == A2AExportedAgent.Kind.NETWORK ? exported.getNetworkCode()
					: exported.getAgentConfigCode();
			if (code != null && code.equals(skillId)) {
				return exported;
			}
		}
		return null;
	}
}
