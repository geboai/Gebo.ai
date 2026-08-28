package ai.gebo.architecture.a2aclients.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.a2aclients.model.A2ARemoteSkill;
import ai.gebo.architecture.a2aclients.repository.A2ARemoteAgentConfigRepository;
import ai.gebo.architecture.a2aclients.service.A2AClientManagementService;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.a2aproject.sdk.spec.AgentCard;
import org.a2aproject.sdk.spec.AgentSkill;

/**
 * Default {@link A2AClientManagementService}. Discovery is delegated to
 * {@link A2AClientConnector} (transport + auth); persistence and ownership/
 * security stamping reuse the {@link A2ARemoteAgentConfigRepository} and
 * {@link IGSecurityService}, mirroring {@code McpClientManagementServiceImpl}.
 */
@Service
@AllArgsConstructor
public class A2AClientManagementServiceImpl implements A2AClientManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AClientManagementServiceImpl.class);

	private final A2ARemoteAgentConfigRepository repository;
	private final IGSecurityService securityService;
	private final A2AClientConnector connector;

	@Override
	public OperationStatus<A2ARemoteAgentConfig> testAndDiscovery(@NotNull @Valid A2ARemoteAgentConfig config) {
		List<GUserMessage> messages = new ArrayList<>();
		validateConnectionConfig(config, messages);
		if (hasErrors(messages)) {
			return reject(messages);
		}
		boolean firstDiscovery = isBlank(config.getCode()) || repository.findById(config.getCode()).isEmpty();
		try {
			AgentCard card = connector.fetchAgentCard(config);
			List<AgentSkill> remoteSkills = card.skills() != null ? card.skills() : List.of();
			config.setSkills(diffSkills(config.getSkills(), remoteSkills, firstDiscovery));
			messages.add(GUserMessage.successMessage("A2A agent reachable",
					"Discovery completed for '" + card.name() + "': " + size(config.getSkills()) + " skill(s)"
							+ (firstDiscovery ? " (first discovery)" : "")));
			return OperationStatus.of(config, messages);
		} catch (Throwable t) {
			LOGGER.error("Error during A2A testAndDiscovery for code:" + config.getCode(), t);
			messages.add(GUserMessage.errorMessage("Cannot reach the A2A agent", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<A2ARemoteAgentConfig> insert(@NotNull @Valid A2ARemoteAgentConfig config) {
		List<GUserMessage> messages = new ArrayList<>();
		validateConnectionConfig(config, messages);
		if (!isBlank(config.getCode()) && repository.findById(config.getCode()).isPresent()) {
			messages.add(GUserMessage.errorMessage("A2A agent already exists",
					"A remote A2A agent with code '" + config.getCode() + "' already exists; use update instead"));
		}
		if (hasErrors(messages)) {
			return reject(messages);
		}
		try {
			String username = currentUsername();
			Date now = new Date();
			if (isBlank(config.getCode())) {
				config.setCode(UUID.randomUUID().toString());
			}
			config.setUserCreated(username);
			config.setUserModified(username);
			config.setDateCreated(now);
			config.setDateModified(now);
			A2ARemoteAgentConfig saved = repository.insert(config);
			messages.add(GUserMessage.successMessage("A2A agent registered",
					"Remote A2A agent '" + saved.getCode() + "' has been registered"));
			return OperationStatus.of(saved, messages);
		} catch (Throwable t) {
			LOGGER.error("Error inserting A2A agent", t);
			messages.add(GUserMessage.errorMessage("Error registering A2A agent", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<A2ARemoteAgentConfig> update(@NotNull @Valid A2ARemoteAgentConfig config) {
		List<GUserMessage> messages = new ArrayList<>();
		validateConnectionConfig(config, messages);
		if (isBlank(config.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing code", "The A2A agent to update must have a code"));
			return reject(messages);
		}
		Optional<A2ARemoteAgentConfig> existingOpt = repository.findById(config.getCode());
		if (existingOpt.isEmpty()) {
			messages.add(GUserMessage.errorMessage("A2A agent does not exist",
					"No remote A2A agent with code '" + config.getCode() + "' exists; use insert to create it"));
		}
		if (hasErrors(messages)) {
			return reject(messages);
		}
		try {
			A2ARemoteAgentConfig existing = existingOpt.get();
			securityService.checkBeingCreator(existing);
			config.setUserCreated(existing.getUserCreated());
			config.setDateCreated(existing.getDateCreated());
			config.setUserModified(currentUsername());
			config.setDateModified(new Date());
			A2ARemoteAgentConfig saved = repository.save(config);
			messages.add(GUserMessage.successMessage("A2A agent updated",
					"Remote A2A agent '" + saved.getCode() + "' has been updated"));
			return OperationStatus.of(saved, messages);
		} catch (SecurityException se) {
			messages.add(GUserMessage.errorMessage("Not allowed",
					"You are not allowed to update A2A agent '" + config.getCode() + "'"));
			return reject(messages);
		} catch (Throwable t) {
			LOGGER.error("Error updating A2A agent", t);
			messages.add(GUserMessage.errorMessage("Error updating A2A agent", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<Boolean> delete(@NotNull @Valid A2ARemoteAgentConfig config) {
		List<GUserMessage> messages = new ArrayList<>();
		if (config == null || isBlank(config.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing A2A agent", "No A2A agent (or code) was provided"));
			return rejectBoolean(messages);
		}
		Optional<A2ARemoteAgentConfig> existingOpt = repository.findById(config.getCode());
		if (existingOpt.isEmpty()) {
			messages.add(GUserMessage.errorMessage("A2A agent does not exist",
					"No remote A2A agent with code '" + config.getCode() + "' exists"));
			return rejectBoolean(messages);
		}
		try {
			A2ARemoteAgentConfig existing = existingOpt.get();
			securityService.checkBeingCreator(existing);
			repository.delete(existing);
			messages.add(GUserMessage.successMessage("A2A agent deleted",
					"Remote A2A agent '" + existing.getCode() + "' has been deleted"));
			return OperationStatus.of(Boolean.TRUE, messages);
		} catch (SecurityException se) {
			messages.add(GUserMessage.errorMessage("Not allowed",
					"You are not allowed to delete A2A agent '" + config.getCode() + "'"));
			return rejectBoolean(messages);
		} catch (Throwable t) {
			LOGGER.error("Error deleting A2A agent", t);
			messages.add(GUserMessage.errorMessage("Error deleting A2A agent", t));
			return rejectBoolean(messages);
		}
	}

	@Override
	public OperationStatus<A2ARemoteAgentConfig> findByCode(String code) {
		if (isBlank(code)) {
			return OperationStatus.ofError("Missing code", "No A2A agent code was provided");
		}
		Optional<A2ARemoteAgentConfig> found = repository.findById(code);
		if (found.isEmpty()) {
			return OperationStatus.ofError("A2A agent not found", "No remote A2A agent with code '" + code + "' exists");
		}
		return OperationStatus.of(found.get());
	}

	@Override
	public Page<A2ARemoteAgentConfig> list(Pageable pageable) {
		return repository.findAll(pageable != null ? pageable : Pageable.unpaged());
	}

	// ---------------------------------------------------------------------
	// helpers
	// ---------------------------------------------------------------------

	/**
	 * Diffs stored skills against the ones discovered on the remote Agent Card,
	 * setting the {@code addedOnRemote}/{@code deletedOnRemote} flags. Matches by
	 * skill id.
	 */
	private List<A2ARemoteSkill> diffSkills(List<A2ARemoteSkill> stored, List<AgentSkill> remote,
			boolean firstDiscovery) {
		List<A2ARemoteSkill> out = new ArrayList<>();
		Map<String, A2ARemoteSkill> storedById = new LinkedHashMap<>();
		if (stored != null) {
			for (A2ARemoteSkill s : stored) {
				storedById.put(s.getId(), s);
			}
		}
		Map<String, Boolean> seen = new LinkedHashMap<>();
		for (AgentSkill remoteSkill : remote) {
			String id = remoteSkill.id();
			seen.put(id, Boolean.TRUE);
			A2ARemoteSkill existing = firstDiscovery ? null : storedById.get(id);
			if (existing == null) {
				A2ARemoteSkill created = mapSkill(remoteSkill);
				created.setAddedOnRemote(Boolean.TRUE);
				created.setDeletedOnRemote(Boolean.FALSE);
				out.add(created);
			} else {
				existing.setAddedOnRemote(Boolean.FALSE);
				existing.setDeletedOnRemote(Boolean.FALSE);
				out.add(existing);
			}
		}
		if (!firstDiscovery && stored != null) {
			for (A2ARemoteSkill s : stored) {
				if (!seen.containsKey(s.getId())) {
					s.setAddedOnRemote(Boolean.FALSE);
					s.setDeletedOnRemote(Boolean.TRUE);
					out.add(s);
				}
			}
		}
		return out;
	}

	private A2ARemoteSkill mapSkill(AgentSkill skill) {
		A2ARemoteSkill model = new A2ARemoteSkill();
		model.setId(skill.id());
		model.setName(skill.name());
		model.setDescription(skill.description());
		model.setTags(skill.tags());
		model.setInputModes(skill.inputModes());
		model.setOutputModes(skill.outputModes());
		return model;
	}

	private void validateConnectionConfig(A2ARemoteAgentConfig config, List<GUserMessage> messages) {
		if (config == null) {
			messages.add(GUserMessage.errorMessage("Missing config", "No A2A agent configuration was provided"));
			return;
		}
		if (isBlank(config.getBaseUrl())) {
			messages.add(GUserMessage.errorMessage("Missing baseUrl", "baseUrl is required to reach an A2A agent"));
		}
		if (isBlank(config.getExportingPrefix())) {
			messages.add(GUserMessage.errorMessage("Missing exportingPrefix",
					"exportingPrefix is required to namespace the remote agent's local id"));
		}
	}

	private String currentUsername() {
		UserInfos user = securityService.getCurrentUser();
		return user != null ? user.getUsername() : "system";
	}

	private static int size(List<?> list) {
		return list != null ? list.size() : 0;
	}

	private static boolean hasErrors(List<GUserMessage> messages) {
		return messages.stream().anyMatch(m -> m.getSeverity() == MsgServerity.error);
	}

	private static OperationStatus<A2ARemoteAgentConfig> reject(List<GUserMessage> messages) {
		return OperationStatus.of((A2ARemoteAgentConfig) null, messages);
	}

	private static OperationStatus<Boolean> rejectBoolean(List<GUserMessage> messages) {
		return OperationStatus.of((Boolean) null, messages);
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
