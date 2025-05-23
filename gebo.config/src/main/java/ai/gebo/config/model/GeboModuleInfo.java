/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.model;

import java.util.HashMap;
import java.util.List;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;

/**
 * Class representing the information for a Gebo module.
 * <p>
 * This class stores the configuration details such as module ID, enabled status,
 * and module integration address pertaining to a Gebo module.
 * </p>
 * 
 * @author Gebo.ai Commentor
 * AI generated comments
 */
public final class GeboModuleInfo {
    // ID of the messaging module
    private String messagingModuleId = null;
    // Indicates if the module is enabled
	private Boolean enabled = false;
	// Address specifying the module's integration approach
	private ModuleIntegrationAddress moduleAddress = ModuleIntegrationAddress.MONOLITHIC;

	/**
	 * Default constructor for GeboModuleInfo.
	 */
	public GeboModuleInfo() {
	}

	/**
	 * Retrieves the messaging module ID.
	 * 
	 * @return the messaging module ID
	 */
	public String getMessagingModuleId() {
		return messagingModuleId;
	}

	/**
	 * Sets the messaging module ID.
	 * 
	 * @param messagingModuleId the ID to set for the messaging module
	 */
	public void setMessagingModuleId(String messagingModuleId) {
		this.messagingModuleId = messagingModuleId;
	}

	/**
	 * Retrieves the module integration address.
	 * 
	 * @return the module integration address
	 */
	public ModuleIntegrationAddress getModuleAddress() {
		return moduleAddress;
	}

	/**
	 * Sets the module integration address.
	 * 
	 * @param moduleAddress the address to set for module integration
	 */
	public void setModuleAddress(ModuleIntegrationAddress moduleAddress) {
		this.moduleAddress = moduleAddress;
	}

	/**
	 * Builds and returns a map of standard module entries.
	 * <p>
	 * This method creates a map where each entry consists of module IDs defined in
	 * {@code GStandardModulesConstraints.ALL_MODULES} associated with a newly
	 * instantiated {@code GeboModuleInfo} object.
	 * </p>
	 * 
	 * @return a map of module IDs to {@code GeboModuleInfo} objects
	 */
	public static HashMap<String, GeboModuleInfo> buildStandardModulesEntries() {
		HashMap<String, GeboModuleInfo> modules = new HashMap<String, GeboModuleInfo>();
		// Retrieves a list of all module IDs
		List<String> ids = GStandardModulesConstraints.ALL_MODULES;
		// Iterates over each module ID to create and map a GeboModuleInfo object
		for (String id : ids) {
			GeboModuleInfo info = new GeboModuleInfo();
			info.setMessagingModuleId(id);
			modules.put(id, info);
		}
		return modules;
	}

	/**
	 * Retrieves the enabled status of the module.
	 * 
	 * @return true if the module is enabled, false otherwise
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled status of the module.
	 * 
	 * @param enabled the enabled status to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}