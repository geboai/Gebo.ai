/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.setup.model.ComponentSetupStatus;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.searxngsearch.handler.model.GSearxngSearchApiCredentials;
import ai.gebo.searxngsearch.handler.model.SearxngSearchConfig;
import ai.gebo.searxngsearch.handler.repository.GSearxngSearchApiCredentialsRepository;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.ConfigurationAuditSupport;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/** Admin CRUD + fast setup for a SearXNG instance (URL required, key optional). */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/SearxngSearchConfigurationController")
@AllArgsConstructor
public class SearxngSearchConfigurationController {

	private final GSearxngSearchApiCredentialsRepository repository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGeboSecretsAccessService secretAccessService;
	/**
	 * The changes to the web search credentials are security events: they decide
	 * which external search service the questions of the users are sent to.
	 */
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	@GetMapping(value = "searchGSearxngSearchApiCredentialsByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GSearxngSearchApiCredentials searchGSearxngSearchApiCredentialsByCode(@RequestParam("code") String code) {
		Optional<GSearxngSearchApiCredentials> entry = repository.findById(code);
		return entry.orElse(null);
	}

	@PostMapping(value = "insertGSearxngSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSearxngSearchApiCredentials insertGSearxngSearchApiCredentials(
			@Valid @RequestBody GSearxngSearchApiCredentials value) {
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.getDetails().put("provider", "searxng");
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_INSERT,
				SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG, value != null ? value.getCode() : null, null,
				() -> repository.insert(value));
	}

	@PostMapping(value = "updateGSearxngSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSearxngSearchApiCredentials updateGSearxngSearchApiCredentials(
			@Valid @RequestBody GSearxngSearchApiCredentials value) {
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.getDetails().put("provider", "searxng");
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_UPDATE,
				SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG, value != null ? value.getCode() : null, null,
				() -> repository.save(value));
	}

	@PostMapping(value = "deleteGSearxngSearchApiCredentials", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGSearxngSearchApiCredentials(@Valid @RequestBody GSearxngSearchApiCredentials value) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.getDetails().put("provider", "searxng");
		ConfigurationAuditSupport.auditedVoid(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_DELETE,
				SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG, value != null ? value.getCode() : null, null,
				() -> repository.delete(value));
	}

	@GetMapping(value = "getSearxngSearchStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentSetupStatus getSearxngSearchStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		status.isSetup = repository.count() > 0l;
		return status;
	}

	@GetMapping(value = "getSearxngSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GSearxngSearchApiCredentials> getSearxngSearchApiCredentials() {
		return repository.findAll();
	}

	@PostMapping(value = "fastInsertSearxngSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSearxngSearchApiCredentials fastInsertSearxngSearchApiCredentials(
			@RequestBody @Valid @NotNull SearxngSearchConfig config)
			throws GeboCryptSecretException, GeboPersistenceException {
		GSearxngSearchApiCredentials credentials = new GSearxngSearchApiCredentials();
		credentials.setBaseUrl(config.getBaseUrl());
		credentials.setDescription("SearXNG search instance");
		if (StringUtils.hasText(config.getApiKey())) {
			GeboTokenContent tokenContent = new GeboTokenContent();
			tokenContent.setToken(config.getApiKey());
			tokenContent.setUser("nouser@info.com");
			String secret = secretAccessService.storeSecret(tokenContent, "SearXNG search credentials",
					"searxng-search");
			credentials.setSecretCode(secret);
		}
		// the API key was stored as a secret above, an event of the secret store; this one
		// records the search configuration created, its code taken from what was saved
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.getDetails().put("provider", "searxng");
		event.getDetails().put("fastSetup", true);
		return ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_INSERT,
				SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG, null, null,
				() -> persistentObjectManager.insert(credentials));
	}
}
