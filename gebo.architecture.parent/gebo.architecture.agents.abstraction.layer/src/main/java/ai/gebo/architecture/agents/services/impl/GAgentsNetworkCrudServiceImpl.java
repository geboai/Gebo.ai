package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.PartialOperationStatus;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentConfigUsagesProvider;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCrudService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
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
	/** The configurations using agents outside the networks (A2A, MCP exports...). */
	private final List<IGAgentConfigUsagesProvider> agentUsagesProviders;

	public GAgentsNetworkCrudServiceImpl(@Autowired IGRuntimeBinder runtimeBinder,
			@Autowired(required = false) List<IGAgentConfigUsagesProvider> agentUsagesProviders) {
		this.runtimeBinder = runtimeBinder;
		this.agentUsagesProviders = agentUsagesProviders != null ? agentUsagesProviders : List.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agents network CRUD service initialized, DAOs are resolved lazily through the runtime binder,"
					+ " agent usages providers:" + this.agentUsagesProviders.size());
		}
	}

	/**
	 * Dumps the accumulated validation messages. Only the counts are reported at
	 * DEBUG; the full message texts, which carry the network content, go to TRACE.
	 */
	private static void traceMessages(String operation, List<GUserMessage> messages) {
		if (LOGGER.isTraceEnabled() && messages != null) {
			LOGGER.trace("<VALIDATION_MESSAGES operation=" + operation + ">");
			for (GUserMessage message : messages) {
				LOGGER.trace(String.valueOf(message));
			}
			LOGGER.trace("</VALIDATION_MESSAGES>");
		}
	}

	@Override
	public OperationStatus<GAgentsNetwork> validate(GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin validate(...) agents network code:" + (network != null ? network.getCode() : null));
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateStructure(network, messages, true);
		traceMessages("validate", messages);
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
		// The code of a new network is generated from its description by the persistence
		// layer, which refuses a network that already has one: it is not required here,
		// and one given in advance cannot be kept.
		validateStructure(network, messages, false);
		final IAgentsNetworkDao dao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		if (network != null) {
			if (isBlank(network.getDescription())) {
				messages.add(GUserMessage.errorMessage("Missing network description",
						"The network must have a description: its code is generated from it"));
			}
			if (isBlank(network.getCode())) {
				// An empty code is no code: the persistence layer generates one only for null.
				network.setCode(null);
			} else if (dao.findByCode(network.getCode()) != null) {
				messages.add(GUserMessage.errorMessage("Network already exists",
						"A network with code '" + network.getCode() + "' already exists; use update instead"));
			} else {
				messages.add(GUserMessage.errorMessage("Network code is generated",
						"The code of a new network is generated from its description: leave the code '"
								+ network.getCode() + "' empty"));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("insert(...) network description:" + network.getDescription()
						+ " code given in advance:" + network.getCode());
			}
		}
		traceMessages("insert", messages);
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
		validateStructure(network, messages, true);
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
		traceMessages("update", messages);
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
		final IGAgenticChatDefaultNetworkOfAgentsService defaults = runtimeBinder
				.getImplementationOf(IGAgenticChatDefaultNetworkOfAgentsService.class);
		final List<String> defaultFor = defaults == null ? List.of()
				: defaults.getAgenticChatDefaultNetworks().stream()
						.filter(x -> existing.getCode().equals(x.getDefaultChatNetworkOfAgents()))
						.map(x -> String.valueOf(x.getPipelineType())).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("delete(...) network code:" + existing.getCode() + " readOnly:" + existing.getReadOnly()
					+ " system default for:" + defaultFor);
		}
		if (!defaultFor.isEmpty()) {
			messages.add(GUserMessage.warnMessage("Deleting a default chat network",
					"Network '" + network.getCode() + "' is the system default chat network of agents for "
							+ defaultFor + "; once deleted the configured default applies"));
		}
		try {
			dao.delete(existing);
			messages.add(GUserMessage.successMessage("Network deleted",
					"Agents network '" + existing.getCode() + "' has been deleted"));
			// An agent works within its network: the agents only this network used go with it.
			deleteAgentsUsedOnlyBy(existing, dao, messages);
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
	 * Deletes the agent configurations the deleted network used and nothing else
	 * uses: no other network of agents (configuration beans included) and no other
	 * configuration ({@link IGAgentConfigUsagesProvider}, e.g. an A2A or MCP export).
	 * The read-only agents, declared by configuration beans, are never deleted. What
	 * cannot be deleted is reported, the network deletion stands.
	 */
	private void deleteAgentsUsedOnlyBy(GAgentsNetwork deleted, IAgentsNetworkDao networkDao,
			List<GUserMessage> messages) {
		if (deleted.getAgents() == null || deleted.getAgents().isEmpty()) {
			return;
		}
		final IAgentConfigDao configDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		final Set<String> agentCodes = new LinkedHashSet<>();
		for (AgentNetworkParticipant participant : deleted.getAgents()) {
			if (participant != null && !isBlank(participant.getAgentConfigCode())) {
				agentCodes.add(participant.getAgentConfigCode());
			}
		}
		// the networks still there use their agents
		final Set<String> usedByOtherNetworks = new HashSet<>();
		for (GAgentsNetwork other : networkDao.getConfigurations()) {
			if (other == null || other.getAgents() == null
					|| (other.getCode() != null && other.getCode().equals(deleted.getCode()))) {
				continue;
			}
			for (AgentNetworkParticipant participant : other.getAgents()) {
				if (participant != null && participant.getAgentConfigCode() != null) {
					usedByOtherNetworks.add(participant.getAgentConfigCode());
				}
			}
		}
		final List<String> deletedAgents = new ArrayList<>();
		final List<String> keptAgents = new ArrayList<>();
		for (String agentCode : agentCodes) {
			final GAgentConfig agent = configDao.findByCode(agentCode);
			if (agent == null || Boolean.TRUE.equals(agent.getReadOnly())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agent:" + agentCode + " of the deleted network:" + deleted.getCode() + " kept: "
							+ (agent == null ? "it does not exist" : "it is read-only"));
				}
				continue;
			}
			final List<String> usages = new ArrayList<>();
			if (usedByOtherNetworks.contains(agentCode)) {
				usages.add("used by another network of agents");
			}
			for (IGAgentConfigUsagesProvider provider : agentUsagesProviders) {
				usages.addAll(provider.findAgentConfigUsages(agentCode));
			}
			if (!usages.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agent:" + agentCode + " of the deleted network:" + deleted.getCode()
							+ " kept, still " + usages);
				}
				keptAgents.add("'" + agentCode + "' (" + String.join(", ", usages) + ")");
				continue;
			}
			try {
				configDao.delete(agent);
				deletedAgents.add("'" + agentCode + "'");
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Agent:" + agentCode + " used only by the deleted network:" + deleted.getCode()
							+ " deleted");
				}
			} catch (Throwable e) {
				LOGGER.error("Cannot delete the agent:" + agentCode + " used only by the deleted network:"
						+ deleted.getCode(), e);
				messages.add(GUserMessage.warnMessage("Agent not deleted",
						"The agent '" + agentCode + "', used only by the deleted network, could not be deleted: "
								+ e.getMessage()));
			}
		}
		if (!deletedAgents.isEmpty()) {
			messages.add(GUserMessage.successMessage("Agents deleted",
					"The agents used only by the network have been deleted with it: "
							+ String.join(", ", deletedAgents)));
		}
		if (!keptAgents.isEmpty()) {
			messages.add(GUserMessage.infoMessage("Agents kept",
					"These agents of the network are still used elsewhere: " + String.join(", ", keptAgents)));
		}
	}

	/**
	 * Runs the structural validation, accumulating {@link GUserMessage}s into the
	 * given list. The presence of any {@code error} message means the network must
	 * not be persisted. {@code codeRequired} is false for a network to insert, whose
	 * code is generated on insert.
	 */
	private void validateStructure(GAgentsNetwork network, List<GUserMessage> messages, boolean codeRequired) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin validateStructure(...) network code:" + (network != null ? network.getCode() : null)
					+ " codeRequired:" + codeRequired + " participants:"
					+ (network != null && network.getAgents() != null ? network.getAgents().size() : 0));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<VALIDATED_NETWORK>");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</VALIDATED_NETWORK>");
		}
		if (network == null) {
			messages.add(GUserMessage.errorMessage("Network is null", "No agents network was provided"));
			return;
		}
		if (codeRequired && isBlank(network.getCode())) {
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
		// The factory the network declares to run on allocates its participants: what it
		// accepts is what the network can be made of.
		final IGAgentsNetworkServiceFactory<?, ?, ?> factory = resolveNetworkServiceFactory(network, messages);

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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network code:" + network.getCode() + " declares " + participantsByName.size()
					+ " uniquely named participant(s), inputNodes:" + inputNodes + " outputNodes:" + outputNodes);
		}
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
		final List<String> reactiveParticipants = new ArrayList<>();
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
			final IGNetworkAgentService<?, ?> networkService;
			if (service instanceof IGNetworkAgentService<?, ?> directNetworkService) {
				networkService = directNetworkService;
			} else if (service instanceof IGReactiveAgentService<?, ?> reactiveService) {
				networkService = reactiveNetworkAdapter(name, participant, reactiveService, factory, messages);
				if (networkService == null) {
					// The reason has been reported.
					continue;
				}
				reactiveParticipants.add(name);
			} else {
				messages.add(GUserMessage.errorMessage("Service is not a network agent",
						"Agent service '" + service.getId() + "' used by participant '" + name
								+ "' is not a network agent service and cannot take part in a network"));
				continue;
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Participant:" + name + " resolved to network agent service:" + networkService.getId()
						+ " inputType:" + typeName(networkService.getInputType()) + " outputType:"
						+ typeName(networkService.getOutputType()));
			}
			servicesByName.put(name, networkService);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End validateStructure(...) resolved " + servicesByName.size() + " of "
					+ participantsByName.size() + " participant(s) to a concrete network agent service, reactive:"
					+ reactiveParticipants);
		}
		if (factory != null) {
			validateAgainstFactory(factory, participantsByName, servicesByName, reactiveParticipants, messages);
		}
		validateCommunicationEdges(participantsByName, servicesByName, messages);
	}

	/**
	 * The network agent a reactive agent service (one answering the user directly,
	 * like the report writer) takes part in the network as, built like the reactive
	 * output networks build it at runtime: only on an output node, through the
	 * adapter its {@link IGReactiveToNetworkAgentAdapterFactory} creates. Only a
	 * reactive output network factory ({@link GAbstractReactiveOutputAgentsNetworkServiceFactory})
	 * adapts it: the others refuse any agent that is not a network agent.
	 *
	 * @param networkFactory the factory the network declares to run on, null when it
	 *                       is unknown (already reported)
	 * @return the adapter, or null with the reason added to {@code messages}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private IGNetworkAgentService<?, ?> reactiveNetworkAdapter(String name, AgentNetworkParticipant participant,
			IGReactiveAgentService reactiveService, IGAgentsNetworkServiceFactory<?, ?, ?> networkFactory,
			List<GUserMessage> messages) {
		if (networkFactory != null && !(networkFactory instanceof GAbstractReactiveOutputAgentsNetworkServiceFactory)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Participant:" + name + " runs the reactive agent service:" + reactiveService.getId()
						+ " but the network factory:" + networkFactory.getId() + " is not a reactive output one");
			}
			messages.add(GUserMessage.errorMessage("Reactive agent not run by the network factory",
					"Agent service '" + reactiveService.getId() + "' used by participant '" + name
							+ "' answers the user directly: the network service factory '" + networkFactory.getId()
							+ "' runs only network agents"));
			return null;
		}
		if (!participant.isOutputNode()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Participant:" + name + " runs the reactive agent service:" + reactiveService.getId()
						+ " without being an output node");
			}
			messages.add(GUserMessage.errorMessage("Reactive agent not on an output node",
					"Agent service '" + reactiveService.getId() + "' used by participant '" + name
							+ "' answers the user directly: it can take part in a network only as an output node"));
			return null;
		}
		final IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern factories = runtimeBinder
				.getImplementationOf(IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern.class);
		final IGReactiveToNetworkAgentAdapterFactory adapterFactory = factories != null
				? factories.getFactory(reactiveService)
				: null;
		if (adapterFactory == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No network adapter factory for the reactive agent service:" + reactiveService.getId()
						+ " of participant:" + name);
			}
			messages.add(GUserMessage.errorMessage("Reactive agent cannot take part in a network",
					"Agent service '" + reactiveService.getId() + "' used by participant '" + name
							+ "' answers the user directly and no adapter makes it a network agent"));
			return null;
		}
		try {
			IGNetworkAgentService<?, ?> adapter = adapterFactory.create(reactiveService).getAdapter();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Participant:" + name + " runs the reactive agent service:" + reactiveService.getId()
						+ " through the network adapter factory:" + adapterFactory.getClass().getName());
			}
			return adapter;
		} catch (Throwable e) {
			LOGGER.error("Cannot adapt the reactive agent service:" + reactiveService.getId() + " of participant:"
					+ name, e);
			messages.add(GUserMessage.errorMessage("Reactive agent cannot take part in a network",
					"Agent service '" + reactiveService.getId() + "' used by participant '" + name
							+ "' cannot be adapted to a network agent: " + e.getMessage()));
			return null;
		}
	}

	/**
	 * The factory the network declares to run on ({@code agentsNetworkServiceFactoryId}),
	 * the one that allocates its participants when the network is run by its code (an
	 * agents network exported as a tool, for instance).
	 *
	 * @return the factory, or null with the reason added to {@code messages}
	 */
	private IGAgentsNetworkServiceFactory<?, ?, ?> resolveNetworkServiceFactory(GAgentsNetwork network,
			List<GUserMessage> messages) {
		final String factoryId = network.getAgentsNetworkServiceFactoryId();
		if (isBlank(factoryId)) {
			messages.add(GUserMessage.errorMessage("Missing network service factory",
					"The network must declare the network service factory that runs it"));
			return null;
		}
		final IGAgentsNetworkServiceFactoryRepositoryPattern factories = runtimeBinder
				.getImplementationOf(IGAgentsNetworkServiceFactoryRepositoryPattern.class);
		final IGAgentsNetworkServiceFactory<?, ?, ?> factory = factories != null ? factories.findByCode(factoryId)
				: null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network code:" + network.getCode() + " declares the network service factory:" + factoryId
					+ " found:" + (factory != null) + " reactiveOutput:"
					+ (factory instanceof GAbstractReactiveOutputAgentsNetworkServiceFactory));
		}
		if (factory == null) {
			messages.add(GUserMessage.errorMessage("Unknown network service factory",
					"The network service factory '" + factoryId + "' of the network is not registered"));
		}
		return factory;
	}

	/**
	 * Enforces what the network factory checks when it allocates the network: an input
	 * node accepting the network input, an output node producing the network output
	 * and, for a reactive output factory, exactly one reactive output node.
	 */
	private void validateAgainstFactory(IGAgentsNetworkServiceFactory<?, ?, ?> factory,
			Map<String, AgentNetworkParticipant> participantsByName,
			Map<String, IGNetworkAgentService<?, ?>> servicesByName, List<String> reactiveParticipants,
			List<GUserMessage> messages) {
		final Class<?> networkInput = factory.getInputType();
		final Class<?> networkOutput = factory.getOutputType();
		final List<IGNetworkAgentService<?, ?>> inputNodes = new ArrayList<>();
		final List<IGNetworkAgentService<?, ?>> outputNodes = new ArrayList<>();
		for (Map.Entry<String, AgentNetworkParticipant> entry : participantsByName.entrySet()) {
			final IGNetworkAgentService<?, ?> service = servicesByName.get(entry.getKey());
			if (service == null) {
				// Already reported.
				continue;
			}
			if (entry.getValue().isInputNode()) {
				inputNodes.add(service);
			}
			if (entry.getValue().isOutputNode()) {
				outputNodes.add(service);
			}
		}
		final boolean inputAccepted = inputNodes.stream().anyMatch(x -> x.getInputType() != null
				&& networkInput != null && x.getInputType().isAssignableFrom(networkInput));
		final boolean outputProduced = outputNodes.stream().anyMatch(x -> x.getOutputType() != null
				&& networkOutput != null && networkOutput.isAssignableFrom(x.getOutputType()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network factory:" + factory.getId() + " input:" + typeName(networkInput) + " accepted:"
					+ inputAccepted + " output:" + typeName(networkOutput) + " produced:" + outputProduced
					+ " reactive outputs:" + reactiveParticipants);
		}
		if (!inputNodes.isEmpty() && !inputAccepted) {
			messages.add(GUserMessage.errorMessage("Input node not accepting the network input",
					"No input node accepts the input '" + typeName(networkInput) + "' of the network service factory '"
							+ factory.getId() + "'"));
		}
		if (!outputNodes.isEmpty() && !outputProduced) {
			messages.add(GUserMessage.errorMessage("Output node not producing the network output",
					"No output node produces the output '" + typeName(networkOutput)
							+ "' of the network service factory '" + factory.getId() + "'"));
		}
		if (factory instanceof GAbstractReactiveOutputAgentsNetworkServiceFactory && reactiveParticipants.size() != 1) {
			messages.add(GUserMessage.errorMessage("One reactive output node needed",
					"The network service factory '" + factory.getId()
							+ "' streams the answer of exactly one output node answering the user directly: the network has "
							+ reactiveParticipants.size() + " " + reactiveParticipants));
		}
	}

	/**
	 * Enforces how the agents talk to the agents in their communication lists, after
	 * the semantic of the messages they produce
	 * ({@link IGNetworkAgentService#getProducedMessageSemantic()}):
	 * <ul>
	 * <li>an agent delivering its messages ({@code EXECUTE_AND_SHARE_RESULT}) needs at
	 * least one agent to talk to, the runtime refuses to run it otherwise;</li>
	 * <li>a router ({@link IGRoutingNetworkAgentService}) sends each agent a command
	 * typed on that agent's own input: no type to match;</li>
	 * <li>a forwarder sends its own output, which must be consumable as the input of
	 * each agent it talks to;</li>
	 * <li>an agent answering with a {@code RESPONSE} only shares it in the session
	 * context every agent reads: no type to match.</li>
	 * </ul>
	 * Every agent of a communication list must be a participant of the network.
	 */
	private void validateCommunicationEdges(Map<String, AgentNetworkParticipant> participantsByName,
			Map<String, IGNetworkAgentService<?, ?>> servicesByName, List<GUserMessage> messages) {
		for (Map.Entry<String, AgentNetworkParticipant> entry : participantsByName.entrySet()) {
			String sourceName = entry.getKey();
			AgentNetworkParticipant participant = entry.getValue();
			List<String> targets = participant.getCommunicationList();
			IGNetworkAgentService<?, ?> sourceService = servicesByName.get(sourceName);
			final boolean routingSource = sourceService instanceof IGRoutingNetworkAgentService;
			// a source whose semantic is not declared is taken for one delivering, the strict case
			final boolean delivering = sourceService != null
					&& sourceService.getProducedMessageSemantic() != MessageSemantic.RESPONSE;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Validating " + (targets != null ? targets.size() : 0)
						+ " communication edge(s) from participant:" + sourceName + " routingSource:" + routingSource
						+ " delivering:" + delivering);
			}
			if (targets == null || targets.isEmpty()) {
				if (delivering) {
					messages.add(GUserMessage.errorMessage("No agent to talk to",
							"Participant '" + sourceName + "' delivers its messages to the agents it can talk to ("
									+ (routingSource ? "a router" : "a forwarder")
									+ ") but its communication list is empty"));
				}
				continue;
			}
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
				if (!delivering || routingSource) {
					// A RESPONSE is only shared in the session context; a router's commands are
					// typed on each target's input.
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("Edge " + sourceName + " -> " + targetName + " has no input type to match ("
								+ (routingSource ? "router" : "shared response") + ")");
					}
					continue;
				}
				Class<?> sourceOutput = sourceService.getOutputType();
				Class<?> targetInput = targetService.getInputType();
				boolean compatible = sourceOutput != null && targetInput != null
						&& targetInput.isAssignableFrom(sourceOutput);
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("Edge " + sourceName + " -> " + targetName + " outputType:" + typeName(sourceOutput)
							+ " inputType:" + typeName(targetInput) + " compatible:" + compatible);
				}
				if (!compatible) {
					messages.add(GUserMessage.errorMessage("Incompatible communication types",
							"Output type '" + typeName(sourceOutput) + "' of '" + sourceName
									+ "' is not compatible with input type '" + typeName(targetInput) + "' of '"
									+ targetName + "': '" + sourceName
									+ "' delivers its output as the input of the agents it talks to"));
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
