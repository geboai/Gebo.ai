/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns.model;

import java.io.Serializable;

/**
 * AI generated comments
 * Represents usage information for a specific module. This class is used to track the state and
 * configuration of a module within the application.
 */
public class GModuleUseInfo implements Serializable {

	/**
	 * Enum representing the type of information available about the module.
	 * EXISTENCE indicates if the module exists, SETUP indicates if the module is set up,
	 * and RUNNING indicates if the module is actively running.
	 */
	public static enum MInfoType {
		EXISTENCE, SETUP, RUNNING
	}

	/**
	 * Enum representing the type of module.
	 * CONTENT for content-related modules, LLMS for language model modules, and OTHER for any other types.
	 */
	public static enum ModuleType {
		CONTENT, LLMS, OTHER
	}

	// Flag indicating whether the module is used.
	private boolean used = false;

	// Identifier of the module.
	private String moduleId = null;

	// Identifier of the handler.
	private String handlerId = null;

	// Code representing the specifications of the module.
	private String specsCode = null;

	// Number representing the configuration settings of the module.
	private int configNumbers = 0;

	// The type of information available about the module.
	private MInfoType infoType = null;

	// The type of module.
	private ModuleType moduleType = null;

	/**
	 * Default constructor initializing a new instance of GModuleUseInfo.
	 */
	public GModuleUseInfo() {

	}

	/**
	 * Copy constructor creating a new instance with values copied from another instance.
	 * 
	 * @param i the instance to copy values from
	 */
	public GModuleUseInfo(GModuleUseInfo i) {
		this.used = i.used;
		this.moduleId = i.moduleId;
		this.handlerId = i.handlerId;
		this.specsCode = i.specsCode;
		this.configNumbers = i.configNumbers;
		this.infoType = infoType;
		this.moduleType = i.moduleType;
	}

	/**
	 * Checks if the module is used.
	 * 
	 * @return true if the module is used, false otherwise
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * Sets whether the module is used.
	 * 
	 * @param used the new usage status of the module
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

	/**
	 * Gets the identifier of the module.
	 * 
	 * @return the module identifier
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * Sets the identifier of the module.
	 * 
	 * @param moduleId the new module identifier
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * Gets the identifier of the handler.
	 * 
	 * @return the handler identifier
	 */
	public String getHandlerId() {
		return handlerId;
	}

	/**
	 * Sets the identifier of the handler.
	 * 
	 * @param handlerId the new handler identifier
	 */
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

	/**
	 * Gets the specifications code of the module.
	 * 
	 * @return the specifications code
	 */
	public String getSpecsCode() {
		return specsCode;
	}

	/**
	 * Sets the specifications code of the module.
	 * 
	 * @param specsCode the new specifications code
	 */
	public void setSpecsCode(String specsCode) {
		this.specsCode = specsCode;
	}

	/**
	 * Gets the number of configuration settings for the module.
	 * 
	 * @return the configuration numbers
	 */
	public int getConfigNumbers() {
		return configNumbers;
	}

	/**
	 * Sets the number of configuration settings for the module.
	 * 
	 * @param configNumbers the new configuration numbers
	 */
	public void setConfigNumbers(int configNumbers) {
		this.configNumbers = configNumbers;
	}

	/**
	 * Returns a string representation of the GModuleUseInfo instance.
	 * 
	 * @return a string containing information about the module's state
	 */
	@Override
	public String toString() {
		return "{moduleId=\'" + moduleId + "\',handlerId=\'" + handlerId + "\',specsCode=\'" + specsCode + "\',used="
				+ used + ",configNumbers=" + configNumbers + ",infoType="
				+ (infoType != null ? infoType.name() : "unkown") + "}";
	}

	/**
	 * Gets the type of information available about the module.
	 * 
	 * @return the information type
	 */
	public MInfoType getInfoType() {
		return infoType;
	}

	/**
	 * Sets the type of information available about the module.
	 * 
	 * @param infoType the new information type
	 */
	public void setInfoType(MInfoType infoType) {
		this.infoType = infoType;
	}

	/**
	 * Gets the type of the module.
	 * 
	 * @return the module type
	 */
	public ModuleType getModuleType() {
		return moduleType;
	}

	/**
	 * Sets the type of the module.
	 * 
	 * @param moduleType the new module type
	 */
	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}

}