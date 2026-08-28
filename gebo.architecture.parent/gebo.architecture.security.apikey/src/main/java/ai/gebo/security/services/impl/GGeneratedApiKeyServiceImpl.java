package ai.gebo.security.services.impl;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.config.GeneratedApiKeyConfig;
import ai.gebo.security.model.GeneratedApiKey;
import ai.gebo.security.model.GeneratedApiKeyInfo;
import ai.gebo.security.repository.GeneratedApiKeyRepository;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGGeneratedApiKeyService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import io.jsonwebtoken.security.SecurityException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GGeneratedApiKeyServiceImpl implements IGGeneratedApiKeyService {
	private final LocalJwtTokenProvider jwtTokenProvider;
	private final IGSecurityService securityService;
	private final IGPersistentObjectManager persistentObjectManager;
	private final GeneratedApiKeyRepository repository;
	private final GeneratedApiKeyConfig apikeyConfig;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	// Takes an already-created SecurityEvent - see logSecretEvent's note in
	// GeboSecretsAccessServiceImpl for why this helper never calls
	// newSecurityEvent() itself.
	private void logApiKeyEvent(SecurityEvent event, String action, String resourceId, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.API_KEY_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.API_KEY_MANAGEMENT);
		event.setAction(action);
		event.setResourceId(resourceId);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	@Override
	public GeneratedApiKey generateApiKey(String description, String username, Date expiration)
			throws GeboPersistenceException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			if (!apikeyConfig.isAdminApiKeyGenEnabled())
				throw new SecurityException("Api key generation disabled in this system for admin");
			if (!securityService.isCurrentUserAdmin())
				throw new SecurityException("Only admin users can call this service");
			GeneratedApiKey gen = new GeneratedApiKey();
			gen.setImpersonatedUser(username);
			gen.setApiKey(jwtTokenProvider.createToken(username, expiration));
			gen.setExpiration(expiration);
			gen.setDescription(description);

			GeneratedApiKey inserted = this.persistentObjectManager.insert(gen);
			logApiKeyEvent(event, SecurityAuditTaxonomy.Action.APIKEY_GENERATE_ADMIN, username,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return inserted;
		} catch (RuntimeException | GeboPersistenceException e) {
			logApiKeyEvent(event, SecurityAuditTaxonomy.Action.APIKEY_GENERATE_ADMIN, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public void deleteApiKey(String code) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			Optional<GeneratedApiKey> opt = repository.findById(code);
			if (opt.isPresent()) {
				if (securityService.isCurrentUserAdmin()) {
					repository.deleteById(code);
				} else if (securityService.getCurrentUser().getUsername().equals(opt.get().getUserCreated())) {
					repository.deleteById(code);
				} else
					throw new SecurityException("You cannot delete this apiKey");
			}
			logApiKeyEvent(event, SecurityAuditTaxonomy.Action.APIKEY_DELETE, code, SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			logApiKeyEvent(event, SecurityAuditTaxonomy.Action.APIKEY_DELETE, code, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public Page<GeneratedApiKeyInfo> getApiKeyPagedList(Pageable pageable) {
		if (securityService.isCurrentUserAdmin()) {
			return repository.findAllProjectedBy(pageable);
		} else {
			return repository.findProjectedByUserCreated(securityService.getCurrentUser().getUsername(), pageable);
		}

	}

	@Override
	public GeneratedApiKey generateApiKey(String description, Date expiration) throws GeboPersistenceException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String username = null;
		try {
			if (!apikeyConfig.isUserApiKeyGenEnabled())
				throw new SecurityException("Api key generation disabled in this system for users");
			UserInfos user = securityService.getCurrentUser();
			username = user.getUsername();
			GeneratedApiKey gen = new GeneratedApiKey();
			gen.setImpersonatedUser(user.getUsername());
			gen.setApiKey(jwtTokenProvider.createToken(user.getUsername(), expiration));
			gen.setExpiration(expiration);
			gen.setDescription(description);
			GeneratedApiKey inserted = this.persistentObjectManager.insert(gen);
			logApiKeyEvent(event, SecurityAuditTaxonomy.Action.APIKEY_GENERATE_SELF, username,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return inserted;
		} catch (RuntimeException | GeboPersistenceException e) {
			logApiKeyEvent(event, SecurityAuditTaxonomy.Action.APIKEY_GENERATE_SELF, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

}
