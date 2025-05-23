/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.knlowledgebase.model.systems.GAbstractBuildSystemConfig;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;

/**
 * Gebo.ai comment agent
 * 
 * This interface extends IGRuntimeConfigurationDao and 
 * provides additional methods specific to build system configurations.
 *
 * @param <ConfigType> the type of the build system configuration
 */
public interface IGBuildSystemConfigurationDao<ConfigType extends GAbstractBuildSystemConfig>
        extends IGRuntimeConfigurationDao<GBuildSystem<ConfigType>> {
    
    /**
     * Finds and returns a GBuildSystem by its code.
     * 
     * @param code the code of the build system to find
     * @return the build system matching the specified code
     */
    public default GBuildSystem<ConfigType> findByCode(String code) {
        // Uses a predicate to filter and find the build system by code
        return this.findByPredicate((x) -> {
            return code != null && x.getCode() != null && x.getCode().equalsIgnoreCase(code);
        });
    }
}