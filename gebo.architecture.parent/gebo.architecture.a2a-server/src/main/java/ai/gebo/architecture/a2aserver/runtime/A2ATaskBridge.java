package ai.gebo.architecture.a2aserver.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.agents.services.AgentException;
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
				return runner.runNetwork(exported.getNetworkCode(), inputText, sink);
			}
			return runner.runAgent(exported.getAgentConfigCode(), SINGLE_AGENT_CODE_PREFIX, inputText, sink);
		} catch (AgentException ae) {
			LOGGER.error("A2A task execution failed for skill '" + skillId + "'", ae);
			throw ae;
		}
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
