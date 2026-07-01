/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.security.config.GeneratedApiKeyConfig;
import ai.gebo.security.model.GeneratedApiKey;
import ai.gebo.security.model.GeneratedApiKeyInfo;
import ai.gebo.security.services.IGGeneratedApiKeyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AI generated comments REST controller that lets an admin user generate API
 * keys for the product. An admin is allowed to generate keys that impersonate
 * any other user, and only when the {@link GeneratedApiKeyConfig} enables admin
 * API key generation.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GeneratedAdminApiKeyController")
@AllArgsConstructor
public class GeneratedAdminApiKeyController {
	private final IGGeneratedApiKeyService apiKeyService;
	private final GeneratedApiKeyConfig apiKeyConfig;

	/**
	 * DTO holding the parameters needed to generate an admin API key.
	 */
	@Data
	public static class GenerateAdminGeneratedApiKeyParam {
		@NotNull
		private String description = null;
		@NotNull
		private String impersonatedUser = null;
		@NotNull
		private Date expiration = null;
	}

	/**
	 * Generates an API key that impersonates the given user. The key is only issued
	 * when admin API key generation is enabled in this system.
	 *
	 * @param param the description, impersonated user and expiration of the key to
	 *              generate
	 * @return the generated API key
	 * @throws GeboPersistenceException if the key cannot be persisted
	 */
	@PostMapping(value = "generateAdminGeneratedApiKey", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeneratedApiKey generateAdminGeneratedApiKey(@Valid @RequestBody GenerateAdminGeneratedApiKeyParam param)
			throws GeboPersistenceException {
		return apiKeyService.generateApiKey(param.getDescription(), param.getImpersonatedUser(),
				new java.sql.Date(param.getExpiration().getTime()));
	}

	/**
	 * Tells whether the current admin user is allowed to generate API keys that
	 * impersonate other users.
	 *
	 * @return {@code true} if admin API key generation is enabled in this system
	 */
	@GetMapping(value = "isAdminGeneratedApiKeyGenerationAllowed", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean isAdminGeneratedApiKeyGenerationAllowed() {
		return apiKeyConfig.isAdminApiKeyGenEnabled();
	}

	/**
	 * Deletes the API key identified by the given code. As an admin the underlying
	 * service allows deleting any key.
	 *
	 * @param code the code of the API key to delete
	 * @throws GeboPersistenceException if the key cannot be deleted
	 */
	@PostMapping(value = "deleteAdminGeneratedApiKey", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAdminGeneratedApiKey(@RequestParam("code") String code) throws GeboPersistenceException {
		apiKeyService.deleteApiKey(code);
	}

	/**
	 * Retrieves a paged list of the API keys visible to the current user. As an
	 * admin the underlying service returns all keys in the system.
	 *
	 * @param page the pagination and sorting information
	 * @return a page of API key info entries
	 * @throws GeboPersistenceException if the list cannot be retrieved
	 */
	@PostMapping(value = "getAdminGeneratedApiKeyPagedList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GeneratedApiKeyInfo> getAdminGeneratedApiKeyPagedList(@Valid @RequestBody DataPage page)
			throws GeboPersistenceException {
		return apiKeyService.getApiKeyPagedList(page.toPageable());
	}
}
