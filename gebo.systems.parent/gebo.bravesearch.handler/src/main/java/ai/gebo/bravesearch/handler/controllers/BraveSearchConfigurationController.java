/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.bravesearch.handler.controllers;

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
import ai.gebo.bravesearch.handler.model.BraveSearchConfig;
import ai.gebo.bravesearch.handler.model.GBraveSearchApiCredentials;
import ai.gebo.bravesearch.handler.repository.GBraveSearchApiCredentialsRepository;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/** Admin CRUD + fast setup for Brave web-search credentials. */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/BraveSearchConfigurationController")
@AllArgsConstructor
public class BraveSearchConfigurationController {

	private final GBraveSearchApiCredentialsRepository repository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGeboSecretsAccessService secretAccessService;

	@GetMapping(value = "searchGBraveSearchApiCredentialsByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBraveSearchApiCredentials searchGBraveSearchApiCredentialsByCode(@RequestParam("code") String code) {
		Optional<GBraveSearchApiCredentials> entry = repository.findById(code);
		return entry.orElse(null);
	}

	@PostMapping(value = "insertGBraveSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GBraveSearchApiCredentials insertGBraveSearchApiCredentials(
			@Valid @RequestBody GBraveSearchApiCredentials value) {
		return repository.insert(value);
	}

	@PostMapping(value = "updateGBraveSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GBraveSearchApiCredentials updateGBraveSearchApiCredentials(
			@Valid @RequestBody GBraveSearchApiCredentials value) {
		return repository.save(value);
	}

	@PostMapping(value = "deleteGBraveSearchApiCredentials", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGBraveSearchApiCredentials(@Valid @RequestBody GBraveSearchApiCredentials value) {
		repository.delete(value);
	}

	@GetMapping(value = "getBraveSearchStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentSetupStatus getBraveSearchStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		status.isSetup = repository.count() > 0l;
		return status;
	}

	@GetMapping(value = "getBraveSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBraveSearchApiCredentials> getBraveSearchApiCredentials() {
		return repository.findAll();
	}

	@PostMapping(value = "fastInsertBraveSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GBraveSearchApiCredentials fastInsertBraveSearchApiCredentials(
			@RequestBody @Valid @NotNull BraveSearchConfig config)
			throws GeboCryptSecretException, GeboPersistenceException {
		GeboTokenContent tokenContent = new GeboTokenContent();
		tokenContent.setToken(config.getApiKey());
		tokenContent.setUser("nouser@info.com");
		String secret = secretAccessService.storeSecret(tokenContent, "Brave search credentials", "brave-search");
		GBraveSearchApiCredentials credentials = new GBraveSearchApiCredentials();
		credentials.setSecretCode(secret);
		credentials.setDescription("Brave search credentials");
		return persistentObjectManager.insert(credentials);
	}
}
