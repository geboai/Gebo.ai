/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contentsystems.abstraction.layer.test;

/**
 * AI generated comments
 * 
 * This interface defines a test-specific content system handler that extends the generic 
 * content management system handler interface. It serves as a specialized handler for 
 * the test content management system and its associated project endpoints.
 * 
 * The interface doesn't define additional methods beyond what is inherited from the
 * parent interface, but provides specific type parameters for the test environment.
 */
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

public interface ITestContentSystemHandler
		extends IGContentManagementSystemHandler<TestContentManagementSystem, TestProjectEndpoint> {
	
}