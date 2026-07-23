package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.PartialOperationStatus;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCrudService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGRoutingNetworkAgentService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;

/**
 * Default {@link IGAgentsNetworkCrudService} implementation.
 * <p>
 * The collaborating DAOs ({@link IAgentsNetworkDao}, {@link IAgentConfigDao},
 * {@link IGAgentServiceRuntimeDao}) are resolved lazily through the
 * {@link IGRuntimeBinder} on every call instead of being injected directly: the
 * runtime agent services and configuration sources are wired late in the startup
 * sequence, so a direct dependency here would risk a circular dependency at
 * context initialization time.
 */
@Service
public class GAgentsNetworkCrudServiceImpl implements IGAgentsNetworkCrudService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GAgentsNetworkCrudServiceImpl.class);

	private final IGRuntimeBinder runtimeBinder;

	public GAgentsNetworkCrudServiceImpl(@Autowired IGRuntimeBinder runtimeBinder) {
		this.runtimeBinder = runtimeBinder;
	}

	@Override
	public OperationStatus<GAgentsNetwork> validate(GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin validate(...) agents network code:" + (network != null ? network.getCode() : null));
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateStructure(network, messages);
		if (hasErrors(messages)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End validate(...) network code:" + (network != null ? network.getCode() : null)
						+ " rejected with " + messages.size() + " message(s)");
			}
			return reject(messages);
		}
		messages.add(GUserMessage.successMessage("Network is valid",
				"The agents network passed structural and type-compatibility validation"));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End validate(...) network code:" + network.getCode() + " is valid (" + messages.size()
					+ " message(s))");
		}
		return PartialOperationStatus.of(network, messages);
	}

	@Override
	public OperationStatus<GAgentsNetwork> insert(GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin insert(...) agents network code:" + (network != null ? network.getCode() : null));
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateStructure(network, messages);
		final IAgentsNetworkDao dao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		if (network != null && !isBlank(network.getCode()) && dao.findByCode(network.getCode()) != null) {
			messages.add(GUserMessage.errorMessage("Network already exists",
					"A network with code '" + network.getCode() + "' already exists; use update instead"));
		}
		if (hasErrors(messages)) {
			LOGGER.warn("Rejecting insert of agents network code:{} due to {} validation message(s)",
					network != null ? network.getCode() : null, messages.size());
			return reject(messages);
		}
		try {
			GAgentsNetwork out = dao.insert(network);
			messages.add(GUserMessage.successMessage("Network created",
					"Agents network '" + out.getCode() + "' has been created"));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End insert(...) agents network code:" + out.getCode() + " created");
			}
			return PartialOperationStatus.of(out, messages);
		} catch (Throwable e) {
			LOGGER.error("Error inserting agents network", e);
			messages.add(GUserMessage.errorMessage("Error inserting agents network", e));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<GAgentsNetwork> update(GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin update(...) agents network code:" + (network != null ? network.getCode() : null));
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateStructure(network, messages);
		final IAgentsNetworkDao dao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		if (network != null && !isBlank(network.getCode())) {
			GAgentsNetwork existing = dao.findByCode(network.getCode());
			if (existing == null) {
				messages.add(GUserMessage.errorMessage("Network does not exist",
						"No network with code '" + network.getCode() + "' exists; use insert to create it"));
			} else if (Boolean.TRUE.equals(existing.getReadOnly())) {
				messages.add(GUserMessage.errorMessage("Network is read-only",
						"The network '" + network.getCode() + "' is read-only and cannot be updated"));
			}
		}
		if (hasErrors(messages)) {
			LOGGER.warn("Rejecting update of agents network code:{} due to {} validation message(s)",
					network != null ? network.getCode() : null, messages.size());
			return reject(messages);
		}
		try {
			GAgentsNetwork out = dao.update(network);
			messages.add(GUserMessage.successMessage("Network updated",
					"Agents network '" + out.getCode() + "' has been updated"));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End update(...) agents network code:" + out.getCode() + " updated");
			}
			return PartialOperationStatus.of(out, messages);
		} catch (Throwable e) {
			LOGGER.error("Error updating agents network", e);
			messages.add(GUserMessage.errorMessage("Error updating agents network", e));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<GAgentsNetwork> delete(GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin delete(...) agents network code:" + (network != null ? network.getCode() : null));
		}
		List<GUserMessage> messages = new ArrayList<>();
		if (network == null || isBlank(network.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing network", "No network (or network code) was provided"));
			return reject(messages);
		}
		final IAgentsNetworkDao dao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		GAgentsNetwork existing = dao.findByCode(network.getCode());
		if (existing == null) {
			messages.add(GUserMessage.errorMessage("Network does not exist",
					"No network with code '" + network.getCode() + "' exists"));
			return reject(messages);
		}
		if (Boolean.TRUE.equals(existing.getReadOnly())) {
			messages.add(GUserMessage.errorMessage("Network is read-only",
					"The network '" + network.getCode() + "' is read-only and cannot be deleted"));
			return reject(messages);
		}
		if (Boolean.TRUE.equals(existing.getDefaultUserInteractionNetwork())) {
			messages.add(GUserMessage.warnMessage("Deleting the default chat network",
					"Network '" + network.getCode() + "' is the default user-interaction network; deleting it may"
							+ " disable the default agentic chat experience"));
		}
		try {
			dao.delete(existing);
			messages.add(GUserMessage.successMessage("Network deleted",
					"Agents network '" + existing.getCode() + "' has been deleted"));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End delete(...) agents network code:" + existing.getCode() + " deleted");
			}
			return PartialOperationStatus.of(existing, messages);
		} catch (Throwable e) {
			LOGGER.error("Error deleting agents network", e);
			messages.add(GUserMessage.errorMessage("Error deleting agents network", e));
			return reject(messages);
		}
	}

	/**
	 * Runs the structural validation, accumulating {@link GUserMessage}s into the
	 * given list. The presence of any {@code error} message means the network must
	 * not be persisted.
	 */
	private void validateStructure(GAgentsNetwork network, List<GUserMessage> messages) {
		if (network == null) {
			messages.add(GUserMessage.errorMessage("Network is null", "No agents network was provided"));
			return;
		}
		if (isBlank(network.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing network code", "The network must have a non-empty code"));
		}
		if (isBlank(network.getScenarioDescription())) {
			messages.add(GUserMessage.warnMessage("Missing scenario description",
					"The network has no scenario description; coordinating agents will get no network context"));
		}
		List<AgentNetworkParticipant> agents = network.getAgents();
		if (agents == null || agents.isEmpty()) {
			messages.add(GUserMessage.errorMessage("Network has no agents",
					"The network must declare at least one participating agent"));
			return;
		}

		final IAgentConfigDao configDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		final IGAgentServiceRuntimeDao serviceDao = runtimeBinder.getImplementationOf(IGAgentServiceRuntimeDao.class);

		// Index participants by their network agent name (agentConfigCode + optional
		// contextual name) so communication edges can be resolved against the actual
		// participants of this network, and detect duplicate names.
		final Map<String, AgentNetworkParticipant> participantsByName = new LinkedHashMap<>();
		for (AgentNetworkParticipant participant : agents) {
			if (participant == null || isBlank(participant.getAgentConfigCode())) {
				messages.add(GUserMessage.errorMessage("Invalid participant",
						"A network participant has no agentConfigCode"));
				continue;
			}
			String name = participant.getNetworkAgentName();
			if (participantsByName.containsKey(name)) {
				messages.add(GUserMessage.errorMessage("Duplicate participant",
						"More than one participant resolves to the network agent name '" + name + "'"));
			} else {
				participantsByName.put(name, participant);
			}
		}

		long inputNodes = agents.stream().filter(p -> p != null && p.isInputNode()).count();
		long outputNodes = agents.stream().filter(p -> p != null && p.isOutputNode()).count();
		if (inputNodes == 0) {
			messages.add(GUserMessage.errorMessage("No input node",
					"The network must declare at least one input node"));
		}
		if (outputNodes == 0) {
			messages.add(GUserMessage.errorMessage("No output node",
					"The network must declare at least one output node"));
		}

		// Resolve each participant to its concrete network agent service so input/output
		// types are available for the communication-edge type checks below.
		final Map<String, IGNetworkAgentService<?, ?>> servicesByName = new LinkedHashMap<>();
		for (Map.Entry<String, AgentNetworkParticipant> entry : participantsByName.entrySet()) {
			String name = entry.getKey();
			AgentNetworkParticipant participant = entry.getValue();
			GAgentConfig config = configDao.findByCode(participant.getAgentConfigCode());
			if (config == null) {
				messages.add(GUserMessage.errorMessage("Unknown agent config",
						"Participant '" + name + "' references agent config '" + participant.getAgentConfigCode()
								+ "' which does not exist"));
				continue;
			}
			if (isBlank(config.getAgentServiceId())) {
				messages.add(GUserMessage.errorMessage("Missing agent service",
						"Agent config '" + config.getCode() + "' has no agentServiceId"));
				continue;
			}
			IGGenericAgentService service = serviceDao.findByCode(config.getAgentServiceId());
			if (service == null) {
				messages.add(GUserMessage.errorMessage("Unknown agent service",
						"Agent config '" + config.getCode() + "' references agent service '" + config.getAgentServiceId()
								+ "' which is not registered"));
				continue;
			}
			if (!(service instanceof IGNetworkAgentService<?, ?> networkService)) {
				messages.add(GUserMessage.errorMessage("Service is not a network agent",
						"Agent service '" + service.getId() + "' used by participant '" + name
								+ "' is not a network agent service and cannot take part in a network"));
				continue;
			}
			servicesByName.put(name, networkService);
		}

		validateCommunicationEdges(participantsByName, servicesByName, messages);
	}

	/**
	 * Enforces the core invariant: for every communication edge {@code A -> B} the
	 * output type of {@code A} must be assignable to the input type of {@code B}.
	 * <p>
	 * Routing/coordinator agents ({@link IGRoutingNetworkAgentService}) do not pipe
	 * their own output into a peer; they emit commands typed to each target's own
	 * input. A type mismatch on an edge whose source is a routing agent is therefore
	 * reported as a warning rather than a hard error, so coordinator-style networks
	 * are not rejected.
	 */
	private void validateCommunicationEdges(Map<String, AgentNetworkParticipant> participantsByName,
			Map<String, IGNetworkAgentService<?, ?>> servicesByName, List<GUserMessage> messages) {
		for (Map.Entry<String, AgentNetworkParticipant> entry : participantsByName.entrySet()) {
			String sourceName = entry.getKey();
			AgentNetworkParticipant participant = entry.getValue();
			List<String> targets = participant.getCommunicationList();
			if (targets == null || targets.isEmpty()) {
				continue;
			}
			IGNetworkAgentService<?, ?> sourceService = servicesByName.get(sourceName);
			boolean routingSource = sourceService instanceof IGRoutingNetworkAgentService;
			for (String targetName : targets) {
				if (!participantsByName.containsKey(targetName)) {
					messages.add(GUserMessage.errorMessage("Unknown communication target",
							"Participant '" + sourceName + "' is configured to communicate with '" + targetName
									+ "' which is not a participant of this network"));
					continue;
				}
				IGNetworkAgentService<?, ?> targetService = servicesByName.get(targetName);
				if (sourceService == null || targetService == null) {
					// The missing service has already been reported above.
					continue;
				}
				Class<?> sourceOutput = sourceService.getOutputType();
				Class<?> targetInput = targetService.getInputType();
				boolean compatible = sourceOutput != null && targetInput != null
						&& targetInput.isAssignableFrom(sourceOutput);
				if (compatible) {
					continue;
				}
				String detail = "Output type '" + typeName(sourceOutput) + "' of '" + sourceName
						+ "' is not compatible with input type '" + typeName(targetInput) + "' of '" + targetName + "'";
				if (routingSource) {
					messages.add(GUserMessage.warnMessage(
							"Coordinator output does not directly match target input",
							"Coordinator '" + sourceName + "' messages '" + targetName + "': " + detail
									+ ". This is allowed for routing agents (they emit commands typed to each target's"
									+ " input), but check the routing prompt produces the target's input type"));
				} else {
					messages.add(GUserMessage.errorMessage("Incompatible communication types",
							detail + ". An agent can message another only when its output type can be consumed as the"
									+ " other agent's input type"));
				}
			}
		}
	}

	private static boolean hasErrors(List<GUserMessage> messages) {
		return messages.stream().anyMatch(m -> m.getSeverity() == MsgServerity.error);
	}

	private static OperationStatus<GAgentsNetwork> reject(List<GUserMessage> messages) {
		return PartialOperationStatus.of((GAgentsNetwork) null, messages);
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private static String typeName(Class<?> type) {
		return type != null ? type.getName() : "<unknown>";
	}
}
