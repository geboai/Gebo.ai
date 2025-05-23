/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openapi;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Gebo.ai comment agent
 *
 * Annotation to define a decoration for a specific OpenAI type.
 * This annotation is retained at runtime and can be applied to types (classes or interfaces).
 * It contains a single element, decorationClass, which specifies the class that 
 * provides the decoration functionality.
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface GeboOpenAITypeDecoration {
    /**
     * Specifies the class that provides the decoration functionality for the annotated type.
     * 
     * @return the class used for decoration
     */
    public Class decorationClass();
}