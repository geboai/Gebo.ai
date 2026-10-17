package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
import ai.gebo.architecture.agents.services.IGAgentsCopyService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCrudService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;

/**
 * Default {@link IGAgentsCopyService} implementation.
 * <p>
 * The copies are stored through the {@link IAgentConfigDao} and
 * {@link IAgentsNetworkDao}; a copy carries its original's code
 * ({@code copiedFromCode}), from which its own code is made on insert
 * ({@link GAgentConfigCopyCodeGenerator}, {@link GAgentsNetworkCopyCodeGenerator}:
 * {@code <original>-<n>}, the first free slot from 1 on), unique among the stored entities and
 * the ones declared by configuration beans. Should a stored copy's code still resolve
 * to a configuration bean, which the DAOs resolve first, the copy is removed and the
 * next copy number is tried. Like {@link GAgentsNetworkCrudServiceImpl}, the collaborators are
 * resolved lazily through the {@link IGRuntimeBinder} on every call, since the
 * runtime agent services and configuration sources are wired late in the startup
 * sequence.
 */
@Service
public class GAgentsCopyServiceImpl implements IGAgentsCopyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GAgentsCopyServiceImpl.class);
	/** The copy numbers tried for a unique description before giving up. */
	static final int MAX_COPY_NUMBER = 100;
	/** The copy marks ending the description of a copy: {@code " (copy)"}, {@code " (copy 2)"}... */
	private static final Pattern COPY_MARKS_PATTERN = Pattern.compile("(?:\\s*\\(copy(?:\\s+\\d+)?\\))+$",
			Pattern.CASE_INSENSITIVE);

	private final IGRuntimeBinder runtimeBinder;

	/** A persistence write of a copy. */
	@FunctionalInterface
	private interface PersistenceCall<T> {
		T apply(T value) throws GeboPersistenceException;
	}

	/** A persistence removal of a copy. */
	@FunctionalInterface
	private interface PersistenceRemoval<T> {
		void apply(T value) throws GeboPersistenceException;
	}

	public GAgentsCopyServiceImpl(@Autowired IGRuntimeBinder runtimeBinder) {
		this.runtimeBinder = runtimeBinder;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agents copy service initialized, DAOs are resolved lazily through the runtime binder");
		}
	}

	@Override
	public OperationStatus<GAgentConfig> copyAgent(String code) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin copyAgent(...) agent code:" + code);
		}
		List<GUserMessage> messages = new ArrayList<>();
		if (isBlank(code)) {
			messages.add(GUserMessage.errorMessage("Missing agent code", "No agent code was provided"));
			return reject(messages);
		}
		final IAgentConfigDao agentDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		GAgentConfig source = agentDao.findByCode(code);
		if (source == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("copyAgent(...) rejected: no agent config with code:" + code);
			}
			messages.add(GUserMessage.errorMessage("Agent does not exist", "No agent with code '" + code + "' exists"));
			return reject(messages);
		}
		try {
			GAgentConfig copy = insertAgentCopy(source, agentDao, descriptionsOf(agentDao.getConfigurations()));
			messages.add(GUserMessage.successMessage("Agent copied", "Agent '" + code + "' has been copied as '"
					+ copy.getCode() + "' (" + copy.getDescription() + ")"));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End copyAgent(...) agent code:" + code + " copied as:" + copy.getCode()
						+ " sourceReadOnly:" + source.getReadOnly());
			}
			return PartialOperationStatus.of(copy, messages);
		} catch (Throwable e) {
			LOGGER.error("Error copying agent code:" + code, e);
			messages.add(GUserMessage.errorMessage("Error copying agent", e));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<GAgentsNetwork> copyAgentsNetwork(String code) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin copyAgentsNetwork(...) network code:" + code);
		}
		List<GUserMessage> messages = new ArrayList<>();
		if (isBlank(code)) {
			messages.add(GUserMessage.errorMessage("Missing network code", "No network code was provided"));
			return reject(messages);
		}
		final IAgentsNetworkDao networkDao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		final IAgentConfigDao agentDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		final IGAgentsNetworkCrudService crudService = runtimeBinder
				.getImplementationOf(IGAgentsNetworkCrudService.class);
		GAgentsNetwork source = networkDao.findByCode(code);
		if (source == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("copyAgentsNetwork(...) rejected: no network with code:" + code);
			}
			messages.add(
					GUserMessage.errorMessage("Network does not exist", "No network with code '" + code + "' exists"));
			return reject(messages);
		}
		// An invalid network is not copied: checked before any copy is stored.
		OperationStatus<GAgentsNetwork> sourceValidation = crudService.validate(source);
		if (sourceValidation.isHasErrorMessages()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("copyAgentsNetwork(...) rejected: network code:" + code + " does not pass the validation");
			}
			messages.add(GUserMessage.errorMessage("Network cannot be copied",
					"The network '" + code + "' does not pass the validation: fix it before copying it"));
			messages.addAll(errorsOf(sourceValidation));
			return reject(messages);
		}
		final List<GAgentConfig> agentCopies = new ArrayList<>();
		GAgentsNetwork networkCopy = null;
		try {
			final Set<String> takenAgentDescriptions = descriptionsOf(agentDao.getConfigurations());
			final Map<String, String> agentCodes = new LinkedHashMap<>();
			for (AgentNetworkParticipant participant : source.getAgents()) {
				final String agentCode = participant.getAgentConfigCode();
				if (agentCodes.containsKey(agentCode)) {
					// The same agent configuration taking part more than once (under different
					// contextual names) is copied once, like the original shares one.
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Agent config:" + agentCode + " takes part more than once, already copied as:"
								+ agentCodes.get(agentCode));
					}
					continue;
				}
				GAgentConfig agent = agentDao.findByCode(agentCode);
				if (agent == null) {
					throw new GeboPersistenceException(
							"The agent config '" + agentCode + "' of the network '" + code + "' does not exist");
				}
				GAgentConfig agentCopy = insertAgentCopy(agent, agentDao, takenAgentDescriptions);
				agentCopies.add(agentCopy);
				agentCodes.put(agentCode, agentCopy.getCode());
			}
			networkCopy = insertNetworkCopy(source, rewiredCopy(source, agentCodes), networkDao);
			// The copy is validated as stored, its participants resolving to the agent copies.
			OperationStatus<GAgentsNetwork> copyValidation = crudService.validate(networkCopy);
			if (copyValidation.isHasErrorMessages()) {
				LOGGER.warn("The copy:{} of the network:{} does not pass the validation, removing it and its {} agent copies",
						networkCopy.getCode(), code, agentCopies.size());
				messages.add(GUserMessage.errorMessage("Network copy is not valid",
						"The copy of the network '" + code + "' does not pass the validation and has been removed"));
				messages.addAll(errorsOf(copyValidation));
				removeCopies(networkCopy, agentCopies, networkDao, agentDao, messages);
				return reject(messages);
			}
			messages.add(GUserMessage.successMessage("Network copied",
					"Agents network '" + code + "' has been copied as '" + networkCopy.getCode() + "' ("
							+ networkCopy.getDescription() + ") with " + agentCopies.size() + " agent copies"));
			messages.add(GUserMessage.infoMessage("Agents copied",
					agentCodes.entrySet().stream().map(x -> "'" + x.getKey() + "' copied as '" + x.getValue() + "'")
							.collect(Collectors.joining(", "))));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End copyAgentsNetwork(...) network code:" + code + " copied as:" + networkCopy.getCode()
						+ " sourceReadOnly:" + source.getReadOnly() + " agent copies:" + agentCodes);
			}
			return PartialOperationStatus.of(networkCopy, messages);
		} catch (Throwable e) {
			LOGGER.error("Error copying agents network code:" + code, e);
			messages.add(GUserMessage.errorMessage("Error copying agents network", e));
			removeCopies(networkCopy, agentCopies, networkDao, agentDao, messages);
			return reject(messages);
		}
	}

	/**
	 * Stores a copy of the given agent configuration, editable and not the default
	 * configuration of its agent service, under a description not in
	 * {@code takenDescriptions}, which gets the stored description.
	 */
	private GAgentConfig insertAgentCopy(GAgentConfig source, IAgentConfigDao agentDao,
			Set<String> takenDescriptions) throws GeboPersistenceException {
		GAgentConfig copy = source.jsonClone();
		// A copy is an entity of its own: editable also when the original is a read-only
		// configuration bean, and never the default configuration of its agent service.
		copy.setReadOnly(false);
		copy.setDefaultConfiguration(null);
		clearAudit(copy);
		// its code is made from the original's (GAgentConfigCopyCodeGenerator)
		copy.setCopiedFromCode(source.getCode());
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_COPY_SOURCE code=" + source.getCode() + ">");
			LOGGER.trace(String.valueOf(source));
			LOGGER.trace("</AGENT_COPY_SOURCE>");
		}
		return insertUniqueCopy("agent", source, copy, takenDescriptions, agentDao::insert, agentDao::findByCode,
				x -> Boolean.TRUE.equals(x.getReadOnly()), agentDao::delete);
	}

	/**
	 * Stores the given network copy under a description not used by any network.
	 */
	private GAgentsNetwork insertNetworkCopy(GAgentsNetwork source, GAgentsNetwork copy, IAgentsNetworkDao networkDao)
			throws GeboPersistenceException {
		return insertUniqueCopy("network", source, copy, descriptionsOf(networkDao.getConfigurations()),
				networkDao::insert, networkDao::findByCode, x -> Boolean.TRUE.equals(x.getReadOnly()),
				networkDao::delete);
	}

	/**
	 * Stores the copy under the first copy description of the source not in
	 * {@code takenDescriptions}, its code being made from the original's on insert. A
	 * stored copy whose code resolves to a configuration bean ({@code declaredByBean})
	 * would be hidden by it: it is removed and the next copy number is tried.
	 */
	private <T extends GBaseObject> T insertUniqueCopy(String kind, GBaseObject source, T copy,
			Set<String> takenDescriptions, PersistenceCall<T> insert, Function<String, T> find,
			Predicate<T> declaredByBean, PersistenceRemoval<T> remove) throws GeboPersistenceException {
		final String baseDescription = baseDescription(source);
		for (int number = 1; number <= MAX_COPY_NUMBER; number++) {
			final String description = copyDescription(baseDescription, number);
			if (takenDescriptions.contains(normalized(description))) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Copy description of the " + kind + ":" + source.getCode() + " already taken:"
							+ description);
				}
				continue;
			}
			copy.setDescription(description);
			copy.setCode(null);
			T stored = insert.apply(copy);
			takenDescriptions.add(normalized(description));
			T resolved = find.apply(stored.getCode());
			if (resolved != null && declaredByBean.test(resolved)) {
				LOGGER.warn("The code:{} generated for the copy of the {}:{} is declared by a configuration bean, trying the next copy number",
						stored.getCode(), kind, source.getCode());
				remove.apply(stored);
				continue;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Stored the copy of the " + kind + ":" + source.getCode() + " as:" + stored.getCode()
						+ " description:" + description);
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<COPY kind=" + kind + " code=" + stored.getCode() + ">");
				LOGGER.trace(String.valueOf(stored));
				LOGGER.trace("</COPY>");
			}
			return stored;
		}
		throw new GeboPersistenceException("No unique copy of the " + kind + " '" + source.getCode() + "' within "
				+ MAX_COPY_NUMBER + " copy numbers");
	}

	/**
	 * Builds the copy of the network on the agent copies: the participants take the
	 * codes of the agent copies, and the communication lists, which name the
	 * participants by their network agent name (built on the agent configuration
	 * code), name the participants of the copy.
	 */
	static GAgentsNetwork rewiredCopy(GAgentsNetwork source, Map<String, String> agentCodes) {
		GAgentsNetwork copy = source.jsonClone();
		copy.setCode(null);
		// A copy is editable also when the original is a read-only configuration bean.
		copy.setReadOnly(false);
		clearAudit(copy);
		// its code is made from the original's (GAgentsNetworkCopyCodeGenerator)
		copy.setCopiedFromCode(source.getCode());
		final Map<String, String> networkAgentNames = new HashMap<>();
		for (AgentNetworkParticipant participant : copy.getAgents()) {
			final String sourceName = participant.getNetworkAgentName();
			participant.setAgentConfigCode(agentCodes.get(participant.getAgentConfigCode()));
			networkAgentNames.put(sourceName, participant.getNetworkAgentName());
		}
		for (AgentNetworkParticipant participant : copy.getAgents()) {
			if (participant.getCommunicationList() != null) {
				participant.setCommunicationList(participant.getCommunicationList().stream()
						.map(name -> networkAgentNames.getOrDefault(name, name))
						.collect(Collectors.toCollection(ArrayList::new)));
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Rewired the copy of the network:" + source.getCode() + " network agent names:"
					+ networkAgentNames);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<NETWORK_COPY source=" + source.getCode() + ">");
			LOGGER.trace(String.valueOf(copy));
			LOGGER.trace("</NETWORK_COPY>");
		}
		return copy;
	}

	/**
	 * Removes what a failed network copy stored, the network copy first; what cannot
	 * be removed is reported.
	 */
	private void removeCopies(GAgentsNetwork networkCopy, List<GAgentConfig> agentCopies, IAgentsNetworkDao networkDao,
			IAgentConfigDao agentDao, List<GUserMessage> messages) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Removing the copies of a failed network copy, network:"
					+ (networkCopy != null ? networkCopy.getCode() : null) + " agents:"
					+ agentCopies.stream().map(GAgentConfig::getCode).toList());
		}
		final List<String> notRemoved = new ArrayList<>();
		if (networkCopy != null && networkCopy.getCode() != null) {
			try {
				networkDao.delete(networkCopy);
			} catch (Throwable e) {
				LOGGER.error("Cannot remove the network copy:" + networkCopy.getCode(), e);
				notRemoved.add(networkCopy.getCode());
			}
		}
		for (GAgentConfig agentCopy : agentCopies) {
			try {
				agentDao.delete(agentCopy);
			} catch (Throwable e) {
				LOGGER.error("Cannot remove the agent copy:" + agentCopy.getCode(), e);
				notRemoved.add(agentCopy.getCode());
			}
		}
		if (!notRemoved.isEmpty()) {
			messages.add(GUserMessage.warnMessage("Copies not removed",
					"These copies of the failed network copy could not be removed: " + notRemoved));
		}
	}

	/** The description of the given copy number of an entity described by base. */
	static String copyDescription(String base, int number) {
		return base + (number == 1 ? " (copy)" : " (copy " + number + ")");
	}

	/**
	 * The description the copies of the source are named after: its code when it has
	 * none. The copy marks ending the description of a copy are removed, so the copy
	 * of a copy takes the next copy number of its root original ({@code "X (copy 2)"},
	 * never {@code "X (copy) (copy)"}).
	 */
	static String baseDescription(GBaseObject source) {
		if (isBlank(source.getDescription())) {
			return source.getCode();
		}
		final String description = source.getDescription().trim();
		final String root = COPY_MARKS_PATTERN.matcher(description).replaceFirst("");
		if (root.isBlank()) {
			// nothing but copy marks: kept as it is
			return description;
		}
		if (LOGGER.isDebugEnabled() && root.length() != description.length()) {
			LOGGER.debug("Copies of:" + source.getCode() + " named after its root description:" + root);
		}
		return root;
	}

	private static Set<String> descriptionsOf(List<? extends GBaseObject> entities) {
		final Set<String> descriptions = new HashSet<>();
		if (entities != null) {
			for (GBaseObject entity : entities) {
				if (entity != null && !isBlank(entity.getDescription())) {
					descriptions.add(normalized(entity.getDescription()));
				}
			}
		}
		return descriptions;
	}

	private static String normalized(String description) {
		return description.trim().toLowerCase(Locale.ROOT);
	}

	private static void clearAudit(GBaseObject copy) {
		copy.setUserCreated(null);
		copy.setDateCreated(null);
		copy.setUserModified(null);
		copy.setDateModified(null);
	}

	private static List<GUserMessage> errorsOf(OperationStatus<?> status) {
		return status.getMessages() == null ? List.of()
				: status.getMessages().stream().filter(x -> x.getSeverity() == MsgServerity.error).toList();
	}

	private static <T> OperationStatus<T> reject(List<GUserMessage> messages) {
		return PartialOperationStatus.of((T) null, messages);
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
