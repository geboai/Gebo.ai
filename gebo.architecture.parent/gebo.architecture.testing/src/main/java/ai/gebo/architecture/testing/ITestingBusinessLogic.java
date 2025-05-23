/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing the business logic for testing purposes.
 */
public interface ITestingBusinessLogic {
	
    /**
     * Default method to verify if the current instance aligns with test business logic.
     *
     * @return boolean indicating if this is a test business logic instance (always returns true).
     */
	public default boolean isTestBusinessLogic() {
		return true;
	}
}