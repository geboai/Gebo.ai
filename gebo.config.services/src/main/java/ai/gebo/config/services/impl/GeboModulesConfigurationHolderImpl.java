/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.services.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.config.GeboConfig;
import ai.gebo.config.model.GeboModuleInfo;
import ai.gebo.config.services.IGeboModulesConfigurationHolder;

/**
 * AI generated comments
 * Implementation of the IGeboModulesConfigurationHolder interface providing
 * configuration management for Gebo modules.
 */
@Service
public class GeboModulesConfigurationHolderImpl implements IGeboModulesConfigurationHolder {
	// Logger instance for logging activities within this class
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboModulesConfigurationHolderImpl.class);

	// Gebo configuration instance
	GeboConfig geboConfig;

	// Map to hold module configurations with their identifiers as keys
	Map<String, GeboModuleInfo> modules = null;

	/**
	 * Constructs the GeboModulesConfigurationHolderImpl using the provided GeboConfig.
	 * Initializes the modules map and sanitizes the configuration.
	 *
	 * @param geboConfig the configuration object containing module configurations
	 */
	public GeboModulesConfigurationHolderImpl(GeboConfig geboConfig) {
		this.geboConfig = geboConfig;
		this.modules = new HashMap<String, GeboModuleInfo>(this.geboConfig.getModulesConfig());
		sanitize();
	}

	/**
	 * Sanitize the configuration by ensuring all required modules are present and
	 * setting default enable/disable states based on constraints.
	 */
	private void sanitize() {
		// Iterate over all standard modules and add them to the map if absent, disabling by default
		for (String m : GStandardModulesConstraints.ALL_MODULES) {
			if (!modules.containsKey(m)) {
				modules.put(m, new GeboModuleInfo());
				modules.get(m).setEnabled(false);
			}
		}
		// Enable specific modules such as the vectorizator and core modules
		if (modules.containsKey(GStandardModulesConstraints.VECTORIZATOR_MODULE)) {
			modules.get(GStandardModulesConstraints.VECTORIZATOR_MODULE).setEnabled(true);
		}
		if (modules.containsKey(GStandardModulesConstraints.CORE_MODULE)) {
			modules.get(GStandardModulesConstraints.CORE_MODULE).setEnabled(true);
		}
		// Enable community modules if the configuration allows it
		if (geboConfig.getEnableCommunityModules() != null && geboConfig.getEnableCommunityModules()) {
			for (String m : GStandardModulesConstraints.COMMUNITY_MODULES) {
				modules.get(m).setEnabled(true);
			}
		}
		// Log the modules that have been enabled
		Collection<GeboModuleInfo> values = modules.values();
		for (GeboModuleInfo minfo : values) {
			if (minfo.getEnabled() != null && minfo.getEnabled()) {
				LOGGER.info("Module: " + minfo.getMessagingModuleId() + " is enabled");
			}
		}

	}

	/**
	 * Retrieves the full configuration map of modules and their respective information.
	 *
	 * @return a map containing module identifiers and their configurations
	 */
	@Override
	public Map<String, GeboModuleInfo> getFullConfiguration() {
		return modules;
	}

	/**
	 * Retrieves the configuration for a specific module identified by the given messagingModuleId.
	 *
	 * @param messagingModuleId the identifier of the module
	 * @return the GeboModuleInfo object associated with the given identifier
	 */
	@Override
	public GeboModuleInfo getModuleConfig(String messagingModuleId) {
		return modules.get(messagingModuleId);
	}

}