/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.redis.model;

import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;

/**
 * AI generated comments
 * 
 * Configuration class for Redis vector store.
 * This class extends the base vector store configuration and adds
 * Redis-specific connection properties such as host, port, username, and password.
 */
public class RedisConfig extends GBaseVectorStoreConfig {
	// Default Redis connection properties
	private String host = null;
	private Integer port = 6333; // Default Redis port
	private String username = null, password = null;

	/**
	 * Default constructor for RedisConfig.
	 */
	public RedisConfig() {
		
	}

	/**
	 * Gets the Redis host.
	 * 
	 * @return the host address as a String
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the Redis host.
	 * 
	 * @param host the host address to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the Redis port.
	 * 
	 * @return the port number
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * Sets the Redis port.
	 * 
	 * @param port the port number to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * Gets the Redis username for authentication.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the Redis username for authentication.
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the Redis password for authentication.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the Redis password for authentication.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}