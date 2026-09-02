package ai.gebo.architecture.a2aserver.runtime;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentsNetwork.CommunicationPolicy;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;

/**
 * Runs an exported skill against the agents runtime and returns its text output.
 * <p>
 * Both export kinds go through the one proven path
 * ({@code IGAgentsNetworkServiceFactory.create(...).executeNetwork()}): a
 * {@link A2AExportedAgent.Kind#NETWORK} runs the referenced network; a
 * {@link A2AExportedAgent.Kind#AGENT} is wrapped in a synthesized single-node
 * network (the agent as both input and output node) — a single agent has no
 * standalone run path. Execution is driven through the
 * {@link A2AStringNetworkServiceFactory} (text I/O) under the caller's captured
 * Spring Security identity ({@link ReactiveIdentityUtil#create()}), so the network
 * runs impersonating the inbound principal and honours that principal's ACLs.
 */
@Service
@AllArgsConstructor
public class A2ATaskBridge {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2ATaskBridge.class);

	private final IGRuntimeBinder runtimeBinder;

	/**
	 * Resolves the export entry advertised as {@code skillId} and runs it.
	 *
	 * @param serverConfig the published server config
	 * @param skillId      the A2A skill id from the inbound request
	 * @param inputText    the user text extracted from the inbound A2A message
	 * @param sink         progress notifications sink (streamed to SSE by the caller)
	 * @return the produced text output
	 * @throws AgentException if the skill is unknown or execution fails
	 */
	public String run(A2AServerConfig serverConfig, String skillId, String inputText, INotificationSink sink)
			throws AgentException {
		A2AExportedAgent exported = resolveExport(serverConfig, skillId);
		if (exported == null) {
			throw new AgentException("No exported skill '" + skillId + "' on A2A server '"
					+ serverConfig.getExportedRelativeUrl() + "'");
		}
		GAgentsNetwork network = resolveNetwork(exported);
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		IGAgentsNetworkServiceFactory<String, String, A2AStringNetworkService> factory = factoryRepo()
				.getFactory(A2AStringNetworkService.class);
		if (factory == null) {
			throw new AgentException("A2A string network factory is not available");
		}
		IGAgentsNetworkService<String, String> service = null;
		try {
			service = factory.create(network, sink, String.class, String.class, runAs);
			IChatRequestContext ctx = IChatRequestContext.of(inputText != null ? inputText : "");
			String output = service.executeNetwork(ctx, inputText != null ? inputText : "", new HashMap<>());
			return output != null ? output : "";
		} catch (AgentException ae) {
			throw ae;
		} catch (Throwable t) {
			LOGGER.error("A2A task execution failed for skill '" + skillId + "'", t);
			throw new AgentException("A2A task execution failed: " + t.getMessage(), t);
		} finally {
			if (service != null) {
				service.dispose();
			}
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

	private GAgentsNetwork resolveNetwork(A2AExportedAgent exported) throws AgentException {
		if (exported.getKind() == A2AExportedAgent.Kind.NETWORK) {
			IAgentsNetworkDao networkDao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
			GAgentsNetwork network = networkDao.findByCode(exported.getNetworkCode());
			if (network == null) {
				throw new AgentException("Exported network '" + exported.getNetworkCode() + "' does not exist");
			}
			return network;
		}
		return synthesizeSingleNodeNetwork(exported.getAgentConfigCode());
	}

	/**
	 * Builds an ephemeral single-node network wrapping the given agent config as both
	 * input and output node, run through the A2A string factory.
	 */
	private GAgentsNetwork synthesizeSingleNodeNetwork(String agentConfigCode) throws AgentException {
		if (agentConfigCode == null || agentConfigCode.isBlank()) {
			throw new AgentException("AGENT export requires an agentConfigCode");
		}
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(agentConfigCode);
		participant.setInputNode(true);
		participant.setOutputNode(true);
		participant.setAllowedToNotifyUser(true);
		participant.setCommunicationPolicy(CommunicationPolicy.ALLOW_ALL);

		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode("a2a-single-" + agentConfigCode);
		network.setDescription("Ephemeral single-node A2A network for agent " + agentConfigCode);
		network.setScenarioDescription("Single exported agent invoked directly over A2A");
		network.setAgentsNetworkServiceFactoryId(A2AStringNetworkServiceFactory.FACTORY_ID);
		network.setAgents(List.of(participant));
		network.setMaxLoopIteration(2);
		return network;
	}

	private IGAgentsNetworkServiceFactoryRepositoryPattern factoryRepo() {
		return runtimeBinder.getImplementationOf(IGAgentsNetworkServiceFactoryRepositoryPattern.class);
	}
}
