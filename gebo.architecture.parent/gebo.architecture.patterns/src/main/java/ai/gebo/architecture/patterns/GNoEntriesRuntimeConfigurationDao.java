/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

import java.util.List;

/**
 * Gebo.ai comment agent
 * An abstract class that extends GAbstractRuntimeConfigurationDao to provide 
 * a specific type of runtime configuration DAO that initializes with no entries.
 * 
 * @param <ConfigTypes> The type of configuration managed by this DAO
 */
public abstract class GNoEntriesRuntimeConfigurationDao<ConfigTypes>
		extends GAbstractRuntimeConfigurationDao<ConfigTypes> {

    /**
     * Constructor that initializes the configuration DAO with no initial entries 
     * and a null initial configuration state.
     * This constructor passes an empty list to the superclass to signify no entries.
     */
	public GNoEntriesRuntimeConfigurationDao() {
		super(List.of(), null);
	}

}