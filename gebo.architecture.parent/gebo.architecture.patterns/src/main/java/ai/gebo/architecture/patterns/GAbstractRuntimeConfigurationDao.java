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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gebo.ai comment agent
 * 
 * Abstract class representing a runtime configuration Data Access Object (DAO).
 * This class provides functionalities to manage and retrieve configurations that may come from static and/or dynamic sources.
 * 
 * @param <ConfigTypes> The type of configuration objects this DAO will manage.
 */
public abstract class GAbstractRuntimeConfigurationDao<ConfigTypes> implements IGRuntimeConfigurationDao<ConfigTypes> {
    
    // List of static configuration objects.
    protected List<ConfigTypes> staticConfigs = null;
    
    // Source of dynamic configuration objects.
    protected IGDynamicConfigurationSource<ConfigTypes> dynamic = null;
    
    /**
     * Constructor initializing the DAO with static and dynamic configuration sources.
     * 
     * @param staticConfigs List of static configurations.
     * @param dynamic Source of dynamic configurations.
     */
    public GAbstractRuntimeConfigurationDao(List<ConfigTypes> staticConfigs, IGDynamicConfigurationSource<ConfigTypes> dynamic) {
        this.staticConfigs = staticConfigs;
        this.dynamic = dynamic;
    }
    
    /**
     * Retrieves a list of dynamic configurations from the dynamic source.
     * If no dynamic source is available, returns an empty list.
     * 
     * @return List of dynamic configurations.
     */
    protected List<ConfigTypes> getDynamicConfigs() {
        return dynamic != null ? dynamic.getConfigurations() : new ArrayList<ConfigTypes>();
    }
    
    /**
     * Combines static and dynamic configurations into a single list.
     * This method overrides the corresponding method from the interface.
     * 
     * @return List containing both static and dynamic configurations.
     */
    @Override
    public List<ConfigTypes> getConfigurations() {
        List<ConfigTypes> returned = new ArrayList<ConfigTypes>();
        List<ConfigTypes> dynamic = getDynamicConfigs();
        if (staticConfigs != null) {
            returned.addAll(staticConfigs);
        }
        if (dynamic != null) {
            returned.addAll(dynamic);
        }
        return returned;
    }
}