/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.secrets.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboGoogleJsonSecretContent;
import ai.gebo.secrets.model.GeboGoogleOauth2SecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboSshKeySecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGeboSecretsAccessService.SecretInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Controller for managing secret operations for administrators. This class
 * provides endpoints for creating and retrieving secrets. Gebo.ai Commentor
 * signature AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/SecretsController")
public class SecretsController {

	// Service to access and manipulate secrets.
	@Autowired
	IGeboSecretsAccessService secretsService;

	/**
	 * Wrapper class to encapsulate secret content along with its metadata such as
	 * context code and description.
	 *
	 * @param <SecretContentType> the type of the secret content
	 */
	public static class SecretWrapper<SecretContentType extends AbstractGeboSecretContent> implements Serializable {

		private String contextCode = null;
		private String description = null;
		private SecretContentType secretContent = null;

		/**
		 * Returns the context code associated with the secret.
		 *
		 * @return a string representing the context code
		 */
		public String getContextCode() {
			return contextCode;
		}

		/**
		 * Sets the context code for the secret.
		 *
		 * @param contextCode the context code to set
		 */
		public void setContextCode(String contextCode) {
			this.contextCode = contextCode;
		}

		/**
		 * Returns the description of the secret.
		 *
		 * @return a string representing the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Sets the description for the secret.
		 *
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * Returns the secret content.
		 *
		 * @return the secret content
		 */
		public SecretContentType getSecretContent() {
			return secretContent;
		}

		/**
		 * Sets the secret content.
		 *
		 * @param secretContent the secret content to set
		 */
		public void setSecretContent(SecretContentType secretContent) {
			this.secretContent = secretContent;
		}
	};

	/**
	 * Default constructor.
	 */
	public SecretsController() {
	}

	/**
	 * Retrieves a list of secrets based on the given context code.
	 *
	 * @param context the context code to search for secrets
	 * @return a list of SecretInfo objects matching the context code
	 * @throws GeboCryptSecretException if an error occurs during decryption
	 */
	@GetMapping(value = "getSecretsByContextCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SecretInfo> getSecretsByContextCode(String context) throws GeboCryptSecretException {
		return secretsService.getSecretInfoByContextCode(context);
	}

	/**
	 * Creates a new username-password secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createUsernamePasswordSecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createUsernamePasswordSecret(@RequestBody SecretWrapper<GeboUsernamePasswordContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

	/**
	 * Creates a new token secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createTokenSecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createTokenSecret(@RequestBody SecretWrapper<GeboTokenContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

	/**
	 * Creates a new SSH key secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createSshKeySecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createSshKeySecret(@RequestBody SecretWrapper<GeboSshKeySecretContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

	/**
	 * Creates a new custom secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createCustomSecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createCustomSecret(@RequestBody SecretWrapper<GeboCustomSecretContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

	/**
	 * Creates a new OAuth2 standard secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createOauth2StandardSecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createOauth2StandardSecret(
			@RequestBody @Valid @NotNull SecretWrapper<GeboOauth2SecretContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

	/**
	 * Creates a new Google OAuth2 secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createGoogleOauth2Secret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createGoogleOauth2Secret(
			@RequestBody @Valid @NotNull SecretWrapper<GeboGoogleOauth2SecretContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

	

	/**
	 * Creates a new Google JSON credentials secret.
	 *
	 * @param content the wrapped content containing secret details
	 * @return the newly created secret information
	 * @throws GeboCryptSecretException if an error occurs during secret creation
	 */
	@PostMapping(value = "createGoogleJsonCredentialsSecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo createGoogleJsonCredentialsSecret(
			@RequestBody @Valid @NotNull SecretWrapper<GeboGoogleJsonSecretContent> content)
			throws GeboCryptSecretException {
		String id = this.secretsService.storeSecret(content.getSecretContent(), content.getDescription(),
				content.getContextCode());
		return secretsService.getSecretInfoById(id);
	}

}