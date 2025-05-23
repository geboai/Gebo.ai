/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

import java.util.Date;
import java.util.List;

import ai.gebo.architecture.patterns.model.GModuleTrafficInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;

/**
 * Gebo.ai comment agent
 * Interface representing a runtime module component in the architecture.
 * It provides methods to retrieve module usage and traffic information.
 */
public interface IGRuntimeModuleComponent {
    
    /**
     * Returns the identifier of the module component, 
     * which by default is the class name.
     *
     * @return String representing the ID of the module
     */
    public default String getId() {
        return this.getClass().getName();
    }

    /**
     * Retrieves a list of module usage information.
     *
     * @return List of GModuleUseInfo representing usage details of the module
     */
    public List<GModuleUseInfo> getModuleUseInfo();

    /**
     * Retrieves a list of module traffic information starting from a specified date.
     * By default, returns an empty list.
     *
     * @param minDate the minimum date from which to consider traffic data
     * @return List of GModuleTrafficInfo representing traffic details of the module
     */
    public default List<GModuleTrafficInfo> getModuleTrafficInfo(Date minDate) {
        // Returns an empty list of module traffic info by default
        return List.of();
    }
}