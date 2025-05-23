/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.systems;

import ai.gebo.model.base.GBaseVersionableObject;

/**
 * AI generated comments
 * Represents a build system with specific configurations.
 * 
 * @param <ConfigType> The type of configuration used by the build system.
 */
public class GBuildSystem<ConfigType extends GAbstractBuildSystemConfig> extends GBaseVersionableObject {

	/**
	 * Serial version UID for serialization compatibility.
	 */
	private static final long serialVersionUID = -6050028241219594935L;
	
	/**
	 * The type code representing the build system.
	 */
	private String buildSystemTypeCode=null;
	
	/**
	 * The configuration specific to the build system.
	 */
	private ConfigType config=null;
	
	/**
	 * Flag indicating if the build system is readonly.
	 */
	private Boolean readonly=false;
	
	/**
	 * Retrieves the build system type code.
	 * 
	 * @return the build system type code.
	 */
	public String getBuildSystemTypeCode() {
		return buildSystemTypeCode;
	}
	
	/**
	 * Sets the build system type code.
	 * 
	 * @param buildSystemTypeCode the code to set.
	 */
	public void setBuildSystemTypeCode(String buildSystemTypeCode) {
		this.buildSystemTypeCode = buildSystemTypeCode;
	}
	
	/**
	 * Retrieves the configuration of the build system.
	 * 
	 * @return the build system configuration.
	 */
	public ConfigType getConfig() {
		return config;
	}
	
	/**
	 * Sets the configuration for the build system.
	 * 
	 * @param config the configuration to set.
	 */
	public void setConfig(ConfigType config) {
		this.config = config;
	}
	
	/**
	 * Checks if the build system is readonly.
	 * 
	 * @return true if the build system is readonly, false otherwise.
	 */
	public Boolean getReadonly() {
		return readonly;
	}
	
	/**
	 * Sets the readonly status of the build system.
	 * 
	 * @param readonly the readonly status to set.
	 */
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

}