package ai.gebo.architecture.a2aserver.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.repository.A2AServerConfigRepository;
import ai.gebo.architecture.a2aserver.runtime.A2AServerRegistry;
import ai.gebo.architecture.a2aserver.service.A2AServerConfigManagerService;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Default {@link A2AServerConfigManagerService}: persists through the
 * {@link A2AServerConfigRepository}, enforces a unique/URL-safe
 * {@code exportedRelativeUrl} and admin-only mutation, and refreshes the
 * {@link A2AServerRegistry} after every change so the published endpoints track the
 * persisted state without a restart.
 */
@Service
@AllArgsConstructor
public class A2AServerConfigManagerServiceImpl implements A2AServerConfigManagerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AServerConfigManagerServiceImpl.class);

	/** Relative URL: starts alphanumeric, then alphanumerics, '-', '_', '.' or '/'. */
	private static final Pattern URL_PATTERN = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9._/-]*$");

	private final A2AServerConfigRepository repository;
	private final A2AServerRegistry registry;
	private final IGSecurityService securityService;

	@Override
	public OperationStatus<A2AServerConfig> insert(@NotNull @Valid A2AServerConfig config) {
		List<GUserMessage> messages = new ArrayList<>();
		requireAdmin(messages);
		validateUrl(config, messages);
		if (!isBlank(config.getCode()) && repository.findById(config.getCode()).isPresent()) {
			messages.add(GUserMessage.errorMessage("A2A server already exists",
					"An A2A server with code '" + config.getCode() + "' already exists; use update instead"));
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
			A2AServerConfig saved = repository.insert(config);
			registry.reload(saved.getCode());
			messages.add(GUserMessage.successMessage("A2A server created",
					"A2A server '" + saved.getExportedRelativeUrl() + "' has been created"));
			return OperationStatus.of(saved, messages);
		} catch (Throwable t) {
			LOGGER.error("Error inserting A2A server", t);
			messages.add(GUserMessage.errorMessage("Error creating A2A server", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<A2AServerConfig> update(@NotNull @Valid A2AServerConfig config) {
		List<GUserMessage> messages = new ArrayList<>();
		requireAdmin(messages);
		validateUrl(config, messages);
		if (isBlank(config.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing code", "The A2A server to update must have a code"));
		}
		if (hasErrors(messages)) {
			return reject(messages);
		}
		Optional<A2AServerConfig> existingOpt = repository.findById(config.getCode());
		if (existingOpt.isEmpty()) {
			messages.add(GUserMessage.errorMessage("A2A server does not exist",
					"No A2A server with code '" + config.getCode() + "' exists; use insert to create it"));
			return reject(messages);
		}
		try {
			A2AServerConfig existing = existingOpt.get();
			config.setUserCreated(existing.getUserCreated());
			config.setDateCreated(existing.getDateCreated());
			config.setUserModified(currentUsername());
			config.setDateModified(new Date());
			A2AServerConfig saved = repository.save(config);
			registry.reload(saved.getCode());
			messages.add(GUserMessage.successMessage("A2A server updated",
					"A2A server '" + saved.getExportedRelativeUrl() + "' has been updated"));
			return OperationStatus.of(saved, messages);
		} catch (Throwable t) {
			LOGGER.error("Error updating A2A server", t);
			messages.add(GUserMessage.errorMessage("Error updating A2A server", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<Boolean> delete(String code) {
		List<GUserMessage> messages = new ArrayList<>();
		requireAdmin(messages);
		if (isBlank(code)) {
			messages.add(GUserMessage.errorMessage("Missing code", "No A2A server code was provided"));
		}
		if (hasErrors(messages)) {
			return OperationStatus.of((Boolean) null, messages);
		}
		Optional<A2AServerConfig> existingOpt = repository.findById(code);
		if (existingOpt.isEmpty()) {
			messages.add(GUserMessage.errorMessage("A2A server does not exist",
					"No A2A server with code '" + code + "' exists"));
			return OperationStatus.of((Boolean) null, messages);
		}
		try {
			repository.delete(existingOpt.get());
			registry.remove(code);
			messages.add(GUserMessage.successMessage("A2A server deleted", "A2A server '" + code + "' has been deleted"));
			return OperationStatus.of(Boolean.TRUE, messages);
		} catch (Throwable t) {
			LOGGER.error("Error deleting A2A server", t);
			messages.add(GUserMessage.errorMessage("Error deleting A2A server", t));
			return OperationStatus.of((Boolean) null, messages);
		}
	}

	@Override
	public OperationStatus<A2AServerConfig> findByCode(String code) {
		if (isBlank(code)) {
			return OperationStatus.ofError("Missing code", "No A2A server code was provided");
		}
		Optional<A2AServerConfig> found = repository.findById(code);
		if (found.isEmpty()) {
			return OperationStatus.ofError("A2A server not found", "No A2A server with code '" + code + "' exists");
		}
		return OperationStatus.of(found.get());
	}

	@Override
	public List<A2AServerConfig> findAll() {
		return repository.findAll();
	}

	// ---------------------------------------------------------------------
	// helpers
	// ---------------------------------------------------------------------

	private void requireAdmin(List<GUserMessage> messages) {
		if (!securityService.isCurrentUserAdmin()) {
			messages.add(GUserMessage.errorMessage("Not allowed", "Only administrators may manage A2A servers"));
		}
	}

	private void validateUrl(A2AServerConfig config, List<GUserMessage> messages) {
		String url = config != null ? config.getExportedRelativeUrl() : null;
		if (isBlank(url)) {
			messages.add(GUserMessage.errorMessage("Missing url", "exportedRelativeUrl is required"));
			return;
		}
		if (url.endsWith("/") || !URL_PATTERN.matcher(url).matches()) {
			messages.add(GUserMessage.errorMessage("Invalid url", "exportedRelativeUrl '" + url
					+ "' is not a valid relative URL (allowed: letters, digits, '-', '_', '.', '/')"));
			return;
		}
		Optional<A2AServerConfig> existing = repository.findByExportedRelativeUrl(url);
		if (existing.isPresent() && !existing.get().getCode().equals(config.getCode())) {
			messages.add(GUserMessage.errorMessage("Duplicate url",
					"exportedRelativeUrl '" + url + "' is already used by another A2A server"));
		}
	}

	private String currentUsername() {
		UserInfos user = securityService.getCurrentUser();
		return user != null ? user.getUsername() : "system";
	}

	private static boolean hasErrors(List<GUserMessage> messages) {
		return messages.stream().anyMatch(m -> m.getSeverity() == MsgServerity.error);
	}

	private static OperationStatus<A2AServerConfig> reject(List<GUserMessage> messages) {
		return OperationStatus.of((A2AServerConfig) null, messages);
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
