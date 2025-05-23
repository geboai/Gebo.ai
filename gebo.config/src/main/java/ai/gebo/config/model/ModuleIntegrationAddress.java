/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.model;

/**
 * AI generated comments
 * This class represents the configuration for a module integration address,
 * providing various details like host, port, schema protocol, and context.
 * It also indicates whether the integration is monolithic.
 */
public class ModuleIntegrationAddress {

    // A constant instance representing a monolithic module integration address.
    public static final ModuleIntegrationAddress MONOLITHIC = new ModuleIntegrationAddress();

    static {
        // Initialize the MONOLITHIC instance as monolithic
        MONOLITHIC.setMonolithic(true);
    }

    // The host address of the module integration, can be null if not specified.
    String host = null;

    // The port number of the module integration, can be null if not specified.
    Integer port = null;

    // The protocol schema used by the module (e.g., http, https), can be null if not specified.
    String schemaProtocol = null;

    // The base context path for the module, can be null if not specified.
    String baseContext = null;

    // Flag indicating whether this integration is monolithic.
    boolean monolithic = false;

    /** 
     * Returns the host of the module integration.
     * @return the host address, or null if not set.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host address of the module integration.
     * @param host the host address to set.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Returns the port of the module integration.
     * @return the port number, or null if not set.
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Sets the port number of the module integration.
     * @param port the port number to set.
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Returns the schema protocol of the module integration.
     * @return the schema protocol, or null if not set.
     */
    public String getSchemaProtocol() {
        return schemaProtocol;
    }

    /**
     * Sets the schema protocol of the module integration.
     * @param schemaProtocol the schema protocol to set.
     */
    public void setSchemaProtocol(String schemaProtocol) {
        this.schemaProtocol = schemaProtocol;
    }

    /**
     * Returns the base context of the module integration.
     * @return the base context, or null if not set.
     */
    public String getBaseContext() {
        return baseContext;
    }

    /**
     * Sets the base context of the module integration.
     * @param baseContext the base context to set.
     */
    public void setBaseContext(String baseContext) {
        this.baseContext = baseContext;
    }

    /**
     * Checks if the integration is monolithic.
     * @return true if the integration is monolithic, false otherwise.
     */
    public boolean isMonolithic() {
        return monolithic;
    }

    /**
     * Sets whether the integration is monolithic.
     * @param monolithic true to mark as monolithic, false otherwise.
     */
    public void setMonolithic(boolean monolithic) {
        this.monolithic = monolithic;
    }
}