/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.controllers;

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
import ai.gebo.tavilysearch.handler.model.GTavilySearchApiCredentials;
import ai.gebo.tavilysearch.handler.model.TavilySearchConfig;
import ai.gebo.tavilysearch.handler.repository.GTavilySearchApiCredentialsRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/** Admin CRUD + fast setup for Tavily web-search credentials. */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/TavilySearchConfigurationController")
@AllArgsConstructor
public class TavilySearchConfigurationController {

	private final GTavilySearchApiCredentialsRepository repository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGeboSecretsAccessService secretAccessService;

	@GetMapping(value = "searchGTavilySearchApiCredentialsByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GTavilySearchApiCredentials searchGTavilySearchApiCredentialsByCode(@RequestParam("code") String code) {
		Optional<GTavilySearchApiCredentials> entry = repository.findById(code);
		return entry.orElse(null);
	}

	@PostMapping(value = "insertGTavilySearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GTavilySearchApiCredentials insertGTavilySearchApiCredentials(
			@Valid @RequestBody GTavilySearchApiCredentials value) {
		return repository.insert(value);
	}

	@PostMapping(value = "updateGTavilySearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GTavilySearchApiCredentials updateGTavilySearchApiCredentials(
			@Valid @RequestBody GTavilySearchApiCredentials value) {
		return repository.save(value);
	}

	@PostMapping(value = "deleteGTavilySearchApiCredentials", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGTavilySearchApiCredentials(@Valid @RequestBody GTavilySearchApiCredentials value) {
		repository.delete(value);
	}

	@GetMapping(value = "getTavilySearchStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentSetupStatus getTavilySearchStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		status.isSetup = repository.count() > 0l;
		return status;
	}

	@GetMapping(value = "getTavilySearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GTavilySearchApiCredentials> getTavilySearchApiCredentials() {
		return repository.findAll();
	}

	@PostMapping(value = "fastInsertTavilySearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GTavilySearchApiCredentials fastInsertTavilySearchApiCredentials(
			@RequestBody @Valid @NotNull TavilySearchConfig config)
			throws GeboCryptSecretException, GeboPersistenceException {
		GeboTokenContent tokenContent = new GeboTokenContent();
		tokenContent.setToken(config.getApiKey());
		tokenContent.setUser("nouser@info.com");
		String secret = secretAccessService.storeSecret(tokenContent, "Tavily search credentials", "tavily-search");
		GTavilySearchApiCredentials credentials = new GTavilySearchApiCredentials();
		credentials.setSecretCode(secret);
		credentials.setDescription("Tavily search credentials");
		return persistentObjectManager.insert(credentials);
	}
}
