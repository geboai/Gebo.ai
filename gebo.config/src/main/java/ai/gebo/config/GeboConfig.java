/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.config;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.config.model.GeboModuleInfo;

/**
 * AI generated comments GeboConfig is a configuration class that holds various
 * configuration properties for the Gebo application.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.config")
public class GeboConfig {

	// Indicates whether a custom keystore is used
	private Boolean customKeyStore = false;

	// Indicates if the application setup is complete
	private Boolean setup = false;

	// Indicates if the setup configures the working directory
	private Boolean setupConfiguresWorkdir = false;

	

	// Configuration for various Gebo modules
	private HashMap<String, GeboModuleInfo> modulesConfig = GeboModuleInfo.buildStandardModulesEntries();

	// Indicates whether community modules are enabled
	private Boolean enableCommunityModules = false;

	// Indicates if the application is running in a clustered environment
	private Boolean clustered = false;

	// Specifies the location of config resources
	private String location = "default";

	/**
	 * Constructor for GeboConfig.
	 */
	public GeboConfig() {
	}

	/**
	 * Gets the setup status.
	 * 
	 * @return the setup status
	 */
	public Boolean getSetup() {
		return setup;
	}

	/**
	 * Sets the setup status.
	 * 
	 * @param setup the setup status to set
	 */
	public void setSetup(Boolean setup) {
		this.setup = setup;
	}

	

	/**
	 * Gets the modules configuration.
	 * 
	 * @return the modules configuration
	 */
	public HashMap<String, GeboModuleInfo> getModulesConfig() {
		return modulesConfig;
	}

	/**
	 * Sets the modules configuration.
	 * 
	 * @param modulesConfig the modules configuration to set
	 */
	public void setModulesConfig(HashMap<String, GeboModuleInfo> modulesConfig) {
		this.modulesConfig = modulesConfig;
	}

	/**
	 * Gets the status of community modules enablement.
	 * 
	 * @return the status of community modules enablement
	 */
	public Boolean getEnableCommunityModules() {
		return enableCommunityModules;
	}

	/**
	 * Sets the status of community modules enablement.
	 * 
	 * @param enableCommunityModules the status to set
	 */
	public void setEnableCommunityModules(Boolean enableCommunityModules) {
		this.enableCommunityModules = enableCommunityModules;
	}

	/**
	 * Gets the clustered environment status.
	 * 
	 * @return the clustered environment status
	 */
	public Boolean getClustered() {
		return clustered;
	}

	/**
	 * Sets the clustered environment status.
	 * 
	 * @param clustered the status to set
	 */
	public void setClustered(Boolean clustered) {
		this.clustered = clustered;
	}

	/**
	 * Gets the custom keystore usage status.
	 * 
	 * @return the custom keystore usage status
	 */
	public Boolean getCustomKeyStore() {
		return customKeyStore;
	}

	/**
	 * Sets the custom keystore usage status.
	 * 
	 * @param customKeyStore the status to set
	 */
	public void setCustomKeyStore(Boolean customKeyStore) {
		this.customKeyStore = customKeyStore;
	}

	/**
	 * Gets the location of the configuration.
	 * 
	 * @return the location of the configuration
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location of the configuration.
	 * 
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the setup configuration workdir status.
	 * 
	 * @return the setup configuration workdir status
	 */
	public Boolean getSetupConfiguresWorkdir() {
		return setupConfiguresWorkdir;
	}

	/**
	 * Sets the setup configuration workdir status.
	 * 
	 * @param setupConfiguresWorkdir the status to set
	 */
	public void setSetupConfiguresWorkdir(Boolean setupConfiguresWorkdir) {
		this.setupConfiguresWorkdir = setupConfiguresWorkdir;
	}

	
}