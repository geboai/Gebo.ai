/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.qdrant.model;

import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;

/**
 * AI generated comments
 * Configuration class for Qdrant vector store connection.
 * Extends the base vector store configuration with Qdrant-specific connection parameters.
 */
public class QdrantConfig extends GBaseVectorStoreConfig {
	/** The hostname or IP address of the Qdrant server */
	private String host = null;
	
	/** The port number for the Qdrant server, defaults to 6333 */
	private Integer port = 6333;
	
	/** API key for authentication with the Qdrant server */
	private String apiKey = null;
	
	/** Flag indicating whether to use TLS for the connection */
	private boolean tls = false;

	/**
	 * Gets the hostname of the Qdrant server.
	 * @return the hostname or IP address
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the hostname of the Qdrant server.
	 * @param host the hostname or IP address to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the port number for the Qdrant server.
	 * @return the port number
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * Sets the port number for the Qdrant server.
	 * @param port the port number to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * Gets the API key for Qdrant server authentication.
	 * @return the API key
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Sets the API key for Qdrant server authentication.
	 * @param apiKey the API key to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * Checks if TLS is enabled for the connection.
	 * @return true if TLS is enabled, false otherwise
	 */
	public boolean isTls() {
		return tls;
	}

	/**
	 * Sets whether TLS should be used for the connection.
	 * @param tls true to enable TLS, false to disable
	 */
	public void setTls(boolean tls) {
		this.tls = tls;
	}
}