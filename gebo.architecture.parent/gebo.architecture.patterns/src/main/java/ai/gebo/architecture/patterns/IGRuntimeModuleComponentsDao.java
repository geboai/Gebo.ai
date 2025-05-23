/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ai.gebo.architecture.patterns.model.GModuleTrafficInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;

/**
 * Interface for runtime module components Data Access Object (DAO).
 * This interface extends IGRuntimeConfigurationDao to provide
 * additional methods specific to module components.
 * 
 * Gebo.ai comment agent
 */
public interface IGRuntimeModuleComponentsDao extends IGRuntimeConfigurationDao<IGRuntimeModuleComponent> {
	
    /**
     * Retrieves a list of module usage information.
     *
     * @return a list of module usage information objects.
     */
	public List<GModuleUseInfo> getModuleUseInfo();

    /**
     * Retrieves a list of module traffic information filtered by a minimum date.
     *
     * @param minDate the minimum date to filter module traffic information.
     * @return a list of module traffic information objects.
     */
	public default List<GModuleTrafficInfo> getModuleTrafficInfo(Date minDate) {
		List<GModuleTrafficInfo> out = new ArrayList<GModuleTrafficInfo>(); // Initialize the output list
		List<IGRuntimeModuleComponent> cfgs = getConfigurations(); // Retrieve module configurations
		for (IGRuntimeModuleComponent rmc : cfgs) { // Iterate through each module configuration
			out.addAll(rmc.getModuleTrafficInfo(minDate)); // Add traffic info filtered by minDate
		}
		return out; // Return the compiled list of traffic information
	}

}