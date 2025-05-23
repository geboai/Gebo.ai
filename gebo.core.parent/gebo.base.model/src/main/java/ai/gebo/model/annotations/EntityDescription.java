/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * EntityDescription is an annotation used to provide
 * metadata about an entity class, specifically a description
 * and an optional entity category.
 */
@Retention(RUNTIME) // Indicates that this annotation is available at runtime.
@Target(TYPE) // Annotation can be applied to classes, interfaces, or enums.
public @interface EntityDescription {
    /**
     * Provides a description of the entity.
     * @return a string representing the description.
     */
    public String description();

    /**
     * Specifies the category of the entity, defaulting to GBaseObject class.
     * @return a class that extends GBaseObject representing the entity category.
     */
    public Class<? extends GBaseObject> entityCategory() default GBaseObject.class;
}