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
import java.util.function.Predicate;

/**
 * Gebo.ai comment agent
 *
 * This interface defines a Data Access Object (DAO) pattern for runtime configuration 
 * management using a generic type {@code ConfigTypes}. It provides methods to retrieve 
 * configurations and perform queries based on conditions.
 *
 * @param <ConfigTypes> the type of the configuration objects managed by the DAO.
 */
public interface IGRuntimeConfigurationDao<ConfigTypes> {

    /**
     * Retrieves all configuration objects.
     *
     * @return a list of all configurations.
     */
    public List<ConfigTypes> getConfigurations();

    /**
     * Finds a single configuration object that matches a given predicate.
     *
     * @param predicate a condition to be matched by the configuration object.
     * @return the first configuration object satisfying the predicate, or {@code null} if none found.
     */
    public default ConfigTypes findByPredicate(Predicate<ConfigTypes> predicate) {
        List<ConfigTypes> configs = getConfigurations();
        if (configs != null)
            // Iterate over configurations to find match
            for (ConfigTypes configTypes : configs) {
                if (predicate.test(configTypes)) {
                    return configTypes;
                }
            }
        return null;
    }

    /**
     * Finds a list of configuration objects that match a given predicate.
     *
     * @param predicate a condition to be matched by the configuration objects.
     * @return a list of configuration objects that satisfy the predicate.
     */
    public default List<ConfigTypes> findListByPredicate(Predicate<ConfigTypes> predicate) {
        List<ConfigTypes> returned = new ArrayList<ConfigTypes>();
        List<ConfigTypes> configs = getConfigurations();
        if (configs != null)
            // Accumulate all configurations that match the predicate
            for (ConfigTypes configTypes : configs) {
                if (predicate.test(configTypes)) {
                    returned.add(configTypes);
                }
            }
        return returned;
    }

    /**
     * Finds a configuration object by its unique code.
     *
     * @param code the unique code of the configuration.
     * @return the configuration object with the specified code.
     */
    public ConfigTypes findByCode(String code);
}