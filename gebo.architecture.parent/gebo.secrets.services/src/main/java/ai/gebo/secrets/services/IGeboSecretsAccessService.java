/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.services;

import java.util.List;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.GeboSecretType;

/**
 * AI generated comments
 * Interface to define access services for managing Gebo secrets.
 */
public interface IGeboSecretsAccessService {
	
	/**
	 * Retrieves the content of a secret by its unique identifier.
	 * 
	 * @param id the unique identifier of the secret.
	 * @return the content of the secret as an AbstractGeboSecretContent object.
	 * @throws GeboCryptSecretException if there is an error during the secret retrieval process.
	 */
	public AbstractGeboSecretContent getSecretContentById(String id) throws GeboCryptSecretException;

	/**
	 * Stores a new secret along with its description and context code.
	 * 
	 * @param <SecretType> the type of the secret content.
	 * @param secret the secret content to be stored.
	 * @param description a brief description of the secret.
	 * @param contextCode the context code under which the secret is stored.
	 * @return a unique identifier for the stored secret.
	 * @throws GeboCryptSecretException if there is an error during the secret storage process.
	 */
	public <SecretType extends AbstractGeboSecretContent> String storeSecret(SecretType secret, String description,
			String contextCode) throws GeboCryptSecretException;

	/**
	 * Stores a secret using an existing secret identifier along with its description and context code.
	 * 
	 * @param <SecretType> the type of the secret content.
	 * @param secret the secret content to be stored.
	 * @param description a brief description of the secret.
	 * @param contextCode the context code under which the secret is stored.
	 * @param secretId a unique identifier for the secret.
	 * @throws GeboCryptSecretException if there is an error during the secret storage process.
	 */
	public <SecretType extends AbstractGeboSecretContent> void storeSecret(SecretType secret, String description,
			String contextCode, String secretId) throws GeboCryptSecretException;

	/**
	 * Updates an existing secret's content using its unique code identifier along with 
	 * its description and context code.
	 * 
	 * @param <SecretType> the type of the secret content.
	 * @param secret the new secret content to be updated.
	 * @param description a brief description of the secret.
	 * @param contextCode the context code under which the secret is stored.
	 * @param code the unique code of the secret to be updated.
	 * @throws GeboCryptSecretException if there is an error during the secret update process.
	 */
	public <SecretType extends AbstractGeboSecretContent> void updateSecret(SecretType secret, String description,
			String contextCode, String code) throws GeboCryptSecretException;

	/**
	 * Deletes a secret based on its unique code identifier.
	 * 
	 * @param code the unique code of the secret to be deleted.
	 * @throws GeboCryptSecretException if there is an error during the secret deletion process.
	 */
	public void deleteSecret(String code) throws GeboCryptSecretException;

	/**
	 * Inner class to store key information about a secret.
	 */
	public static class SecretInfo {

		/**
		 * Default constructor for creating an empty SecretInfo object.
		 */
		public SecretInfo() {
		}

		/**
		 * Constructor for creating a SecretInfo object based on a GeboSecret instance.
		 * 
		 * @param secret a GeboSecret object containing the details of the secret.
		 */
		public SecretInfo(GeboSecret secret) {
			this.code = secret.getCode();
			this.description = secret.getDescription();
			this.secretType = secret.getSecretType();
		}

		// Unique identifier for the secret.
		private String code = null;

		// Description for the secret.
		private String description = null;

		// Type of the secret.
		private GeboSecretType secretType = null;

		/**
		 * Retrieves the unique code of the secret.
		 * 
		 * @return the unique code of the secret.
		 */
		public String getCode() {
			return code;
		}

		/**
		 * Sets the unique code of the secret.
		 * 
		 * @param code the unique code to be set.
		 */
		public void setCode(String code) {
			this.code = code;
		}

		/**
		 * Retrieves the description of the secret.
		 * 
		 * @return the description of the secret.
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Sets the description of the secret.
		 * 
		 * @param description the description to be set.
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * Retrieves the type of the secret.
		 * 
		 * @return the secret type.
		 */
		public GeboSecretType getSecretType() {
			return secretType;
		}

		/**
		 * Sets the type of the secret.
		 * 
		 * @param secretType the secret type to be set.
		 */
		public void setSecretType(GeboSecretType secretType) {
			this.secretType = secretType;
		}
	}

	/**
	 * Retrieves a list of secret information based on the provided context code.
	 * 
	 * @param contextCode the context code used to filter the secrets.
	 * @return a list of SecretInfo objects associated with the context code.
	 * @throws GeboCryptSecretException if there is an error during the retrieval process.
	 */
	public List<SecretInfo> getSecretInfoByContextCode(String contextCode) throws GeboCryptSecretException;

	/**
	 * Retrieves information about a secret based on its unique identifier.
	 * 
	 * @param code the unique identifier of the secret.
	 * @return a SecretInfo object containing details about the secret.
	 * @throws GeboCryptSecretException if there is an error during the retrieval process.
	 */
	public SecretInfo getSecretInfoById(String code) throws GeboCryptSecretException;
}