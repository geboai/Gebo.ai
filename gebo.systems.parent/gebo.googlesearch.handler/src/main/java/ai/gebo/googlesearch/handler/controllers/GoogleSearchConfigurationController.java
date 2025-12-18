/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.googlesearch.handler.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import ai.gebo.googlesearch.handler.model.GGoogleSearchApiCredentials;
import ai.gebo.googlesearch.handler.model.GoogleSearchConfig;
import ai.gebo.googlesearch.handler.repository.GGoogleSearchApiCredentialsRepository;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Controller responsible for managing Google Search API credentials
 * configuration. This controller provides endpoints for CRUD operations on
 * Google Search API credentials. All endpoints are restricted to users with
 * ADMIN role.
 * 
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/GoogleSearchConfigurationController")
@AllArgsConstructor
public class GoogleSearchConfigurationController {
	/**
	 * Repository for Google Search API credentials data access
	 */

	private final GGoogleSearchApiCredentialsRepository repository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGeboSecretsAccessService secretAccessService;

	/**
	 * Retrieves Google Search API credentials by their unique code identifier
	 * 
	 * @param code The unique identifier for the credentials
	 * @return The matching credentials object or null if not found
	 */
	@GetMapping(value = "searchGGoogleSearchApiCredentialsByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleSearchApiCredentials searchGGoogleSearchApiCredentialsByCode(@RequestParam("code") String code) {
		Optional<GGoogleSearchApiCredentials> entry = repository.findById(code);
		if (entry.isPresent())
			return entry.get();
		else
			return null;
	}

	/**
	 * Inserts new Google Search API credentials into the repository
	 * 
	 * @param value The credentials object to be inserted
	 * @return The saved credentials object
	 */
	@PostMapping(value = "insertGGoogleSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleSearchApiCredentials insertGGoogleSearchApiCredentials(
			@Valid @RequestBody GGoogleSearchApiCredentials value) {
		return repository.insert(value);
	}

	/**
	 * Updates existing Google Search API credentials in the repository
	 * 
	 * @param value The credentials object to be updated
	 * @return The updated credentials object
	 */
	@PostMapping(value = "updateGGoogleSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleSearchApiCredentials updateGGoogleSearchApiCredentials(
			@Valid @RequestBody GGoogleSearchApiCredentials value) {
		return repository.save(value);
	}

	/**
	 * Deletes Google Search API credentials from the repository
	 * 
	 * @param value The credentials object to be deleted
	 */
	@PostMapping(value = "deleteGGoogleSearchApiCredentials", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGGoogleSearchApiCredentials(@Valid @RequestBody GGoogleSearchApiCredentials value) {
		repository.delete(value);
	}

	@GetMapping(value = "getGoogleSearchStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentSetupStatus getGoogleSearchStatus() {
		ComponentSetupStatus status = new ComponentSetupStatus();
		status.isSetup = repository.count() > 0l;
		return status;

	}

	@GetMapping(value = "getGoogleSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GGoogleSearchApiCredentials> getGoogleSearchApiCredentials() {
		return repository.findAll();
	}

	@PostMapping(value = "fastInsertGoogleSearchApiCredentials", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleSearchApiCredentials fastInsertGoogleSearchApiCredentials(
			@RequestBody @Valid @NotNull GoogleSearchConfig config)
			throws GeboCryptSecretException, GeboPersistenceException {
		GeboTokenContent tokenContent = new GeboTokenContent();
		tokenContent.setToken(config.getApiKey());
		tokenContent.setUser("nouser@info.com");
		String secret = secretAccessService.storeSecret(tokenContent, "Google search credentials", "google-search");
		GGoogleSearchApiCredentials credentials = new GGoogleSearchApiCredentials();
		credentials.setSecretCode(secret);
		credentials.setCustomSearchEngineId(config.getCustomSearchEngineId());
		credentials.setDescription("Google search credentials");
		return persistentObjectManager.insert(credentials);
	}
}