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
 *
 * This abstract class GSingleObjectRuntimeConfigurationDao represents a special type of data access object (DAO).
 * It is used for handling a single object of runtime configuration within the system.
 * 
 * @param <ConfigTypes> a generic type representing the configuration type that is managed by this DAO.
 */
public abstract class GSingleObjectRuntimeConfigurationDao<ConfigTypes>
		extends GAbstractRuntimeConfigurationDao<ConfigTypes> {

    /**
     * Constructor that initializes this DAO with a single configuration element.
     * This setup ensures that only one configuration object is handled at runtime.
     *
     * @param element the configuration object that is managed by this DAO.
     */
	public GSingleObjectRuntimeConfigurationDao(ConfigTypes element) {
		super(List.of(element), null);
	}

}