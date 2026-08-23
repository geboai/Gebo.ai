/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.serpapisearch.handler.model.GSerpapiSearchApiCredentials;
import ai.gebo.serpapisearch.handler.model.SerpapiSearchConfig;
import ai.gebo.serpapisearch.handler.repository.GSerpapiSearchApiCredentialsRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/** Admin CRUD + fast setup for SerpApi web-search credentials. */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/SerpapiSearchConfigurationController")
@AllArgsConstructor
public class SerpapiSearchConfigurationController {

	private final GSerpapiSearchApiCredentialsRepository repository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGeboSecretsAccessService secretAccessService;

	@GetMapping(value = "searchGSerpapiSearchApiCredentialsByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GSerpapiSearchApiCredentials searchGSerpapiSearchApiCredentialsByCode(@RequestParam("code") String code) {
		Optional<GSerpapiSearchApiCredentials> entry = repository.findById(code);
		return entry.orElse(null);
	}

	@PostMapping(value = "insertGSerpapiSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSerpapiSearchApiCredentials insertGSerpapiSearchApiCredentials(
			@Valid @RequestBody GSerpapiSearchApiCredentials value) {
		return repository.insert(value);
	}

	@PostMapping(value = "updateGSerpapiSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSerpapiSearchApiCredentials updateGSerpapiSearchApiCredentials(
			@Valid @RequestBody GSerpapiSearchApiCredentials value) {
		return repository.save(value);
	}

	@PostMapping(value = "deleteGSerpapiSearchApiCredentials", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGSerpapiSearchApiCredentials(@Valid @RequestBody GSerpapiSearchApiCredentials value) {
		repository.delete(value);
	}

	@GetMapping(value = "getSerpapiSearchStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentSetupStatus getSerpapiSearchStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		status.isSetup = repository.count() > 0l;
		return status;
	}

	@GetMapping(value = "getSerpapiSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GSerpapiSearchApiCredentials> getSerpapiSearchApiCredentials() {
		return repository.findAll();
	}

	@PostMapping(value = "fastInsertSerpapiSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSerpapiSearchApiCredentials fastInsertSerpapiSearchApiCredentials(
			@RequestBody @Valid @NotNull SerpapiSearchConfig config)
			throws GeboCryptSecretException, GeboPersistenceException {
		GeboTokenContent tokenContent = new GeboTokenContent();
		tokenContent.setToken(config.getApiKey());
		tokenContent.setUser("nouser@info.com");
		String secret = secretAccessService.storeSecret(tokenContent, "SerpApi search credentials", "serpapi-search");
		GSerpapiSearchApiCredentials credentials = new GSerpapiSearchApiCredentials();
		credentials.setSecretCode(secret);
		credentials.setDescription("SerpApi search credentials");
		return persistentObjectManager.insert(credentials);
	}
}
