/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * The IGBuildSystemHandlerRepositoryPattern interface defines a contract for 
 * classes that implement a repository pattern specifically for handling 
 * build system handlers. It extends the IGImplementationsRepositoryPattern 
 * with the type parameter IGBuildSystemHandler, indicating it works with 
 * objects of that type.
 */
public interface IGBuildSystemHandlerRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGBuildSystemHandler> {
    
    // No additional methods are defined in this interface; it inherits all 
    // methods from IGImplementationsRepositoryPattern with IGBuildSystemHandler 
    // as the specific class type.
   
}