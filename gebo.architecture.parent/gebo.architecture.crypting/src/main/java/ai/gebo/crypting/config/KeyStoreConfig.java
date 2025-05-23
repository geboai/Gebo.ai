/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.config;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * Configuration class for key store settings, used for managing cryptographic operations.
 */
public class KeyStoreConfig {

	@NotNull
	private String keyStoreType = "PKCS12"; // Default key store type

	@NotNull
	private String keyStorePath = null; // Path where the key store is located

	@NotNull
	private String keyAlgorithm = null; // Algorithm used for cryptographic keys

	@NotNull
	private String keyStorePassword = null; // Password for accessing the key store

	@NotNull
	private String keyPassword = null; // Password for accessing the cryptographic key

	@NotNull
	private String keyName = null; // Name of the cryptographic key

	/**
	 * Retrieves the password for the key store.
	 * 
	 * @return the key store password
	 */
	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	/**
	 * Sets a custom password for the key store.
	 * 
	 * @param customKeyStorePassword the custom key store password
	 */
	public void setKeyStorePassword(String customKeyStorePassword) {
		this.keyStorePassword = customKeyStorePassword;
	}

	/**
	 * Retrieves the password for the cryptographic key.
	 * 
	 * @return the key password
	 */
	public String getKeyPassword() {
		return keyPassword;
	}

	/**
	 * Sets a custom password for the cryptographic key.
	 * 
	 * @param customKeyPassword the custom key password
	 */
	public void setKeyPassword(String customKeyPassword) {
		this.keyPassword = customKeyPassword;
	}

	/**
	 * Retrieves the name of the cryptographic key.
	 * 
	 * @return the key name
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * Sets a custom name for the cryptographic key.
	 * 
	 * @param customKeyName the custom key name
	 */
	public void setKeyName(String customKeyName) {
		this.keyName = customKeyName;
	}

	/**
	 * Retrieves the path of the key store.
	 * 
	 * @return the key store path
	 */
	public String getKeyStorePath() {
		return keyStorePath;
	}

	/**
	 * Sets a custom path for the key store.
	 * 
	 * @param customKeyStorePath the custom key store path
	 */
	public void setKeyStorePath(String customKeyStorePath) {
		this.keyStorePath = customKeyStorePath;
	}

	/**
	 * Retrieves the key store type.
	 * 
	 * @return the key store type
	 */
	public String getKeyStoreType() {
		return keyStoreType;
	}

	/**
	 * Sets a custom key store type.
	 * 
	 * @param keyStoreType the custom key store type
	 */
	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	/**
	 * Retrieves the algorithm used for the cryptographic keys.
	 * 
	 * @return the key algorithm
	 */
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	/**
	 * Sets a custom algorithm for the cryptographic keys.
	 * 
	 * @param keyAlgorithm the custom key algorithm
	 */
	public void setKeyAlgorithm(String keyAlgorithm) {
		this.keyAlgorithm = keyAlgorithm;
	}
}