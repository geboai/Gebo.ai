/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.secrets.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.impl.GeboCryptingServiceImpl;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.repository.GeboSecretRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGeboSecretsExternalStorageService;
import ai.gebo.secrets.services.IGeboSecretsStorageManagementService;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import lombok.AllArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class GeboSecretsStorageManagementServiceImpl implements IGeboSecretsStorageManagementService {

	private final Optional<IGeboSecretsExternalStorageService> externalStorage;
	private final IGeboSecretsAccessService secretsAccessService;
	private final GeboSecretRepository repository;
	private final GeboCryptingServiceImpl cryptService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;
	private static final ObjectMapper mapper = new ObjectMapper();

	// Takes an already-created SecurityEvent - see logSecretEvent's note in
	// GeboSecretsAccessServiceImpl for why this helper never calls
	// newSecurityEvent() itself.
	private void logMigrationEvent(SecurityEvent event, String direction, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.SECRET_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.SECRET_MANAGEMENT);
		event.setAction(SecurityAuditTaxonomy.Action.SECRET_STORAGE_MIGRATE);
		event.setResourceId(direction);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	@Override
	public boolean isMigrationToExternalPossible() {
		return externalStorage.isPresent() && externalStorage.get().isConfigured()
				&& !externalStorage.get().isActiveStorage();
	}

	@Override
	public boolean isMigrationToExternalDone() {
		return externalStorage.isPresent() && externalStorage.get().isConfigured()
				&& externalStorage.get().isActiveStorage();
	}

	@Override
	public void migrateToExternalStorage() throws GeboCryptSecretException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			if (!isMigrationToExternalPossible())
				throw new IllegalStateException("Migration to external storage is not currently possible");

			IGeboSecretsExternalStorageService ext = externalStorage.get();
			List<String> allIds = secretsAccessService.getAllSecretsId();

			for (String id : allIds) {
				AbstractGeboSecretContent content = secretsAccessService.getSecretContentById(id);
				SecretInfo info = secretsAccessService.getSecretInfoById(id);
				ext.storeSecret(content, info.getDescription(), info.getContextCode(), id);
			}

			ext.switchToActiveStorage();
			logMigrationEvent(event, "toExternal", SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException | GeboCryptSecretException e) {
			logMigrationEvent(event, "toExternal", SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public void migrateFromExternalStorage() throws GeboCryptSecretException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			if (!isMigrationToExternalDone())
				throw new IllegalStateException("Migration from external storage is not currently possible");

			IGeboSecretsExternalStorageService ext = externalStorage.get();
			List<String> allIds = ext.getAllSecretsId();

			for (String id : allIds) {
				AbstractGeboSecretContent content = ext.getSecretContentById(id);
				SecretInfo info = ext.getSecretInfoById(id);
				String serialized;
				try {
					serialized = mapper.writeValueAsString(content);
				} catch (JacksonException e) {
					throw new GeboCryptSecretException("exception while serializing secret for migration", e);
				}
				String encrypted = cryptService.crypt(serialized);
				GeboSecret secret = new GeboSecret();
				secret.setCode(id);
				secret.setDescription(info.getDescription());
				secret.setSecretType(info.getSecretType());
				secret.setContextCode(info.getContextCode());
				secret.setSecretContent(encrypted);
				secret.setCreationDate(new Date());
				repository.save(secret);
			}
			logMigrationEvent(event, "fromExternal", SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException | GeboCryptSecretException e) {
			logMigrationEvent(event, "fromExternal", SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}
}