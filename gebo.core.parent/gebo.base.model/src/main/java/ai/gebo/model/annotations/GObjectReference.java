/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * This annotation is used to indicate that a field is a reference to a GBaseObject.
 * The type of the object being referenced is specified by the referencedType method.
 */
@Retention(RUNTIME) // Specifies that this annotation is available at runtime.
@Target(FIELD) // Indicates that this annotation can be applied to fields.
public @interface GObjectReference {
    /**
     * Specifies the type of GBaseObject that is being referenced by the annotated field.
     * 
     * @return the class type of the referenced GBaseObject
     */
    public Class<? extends GBaseObject> referencedType();
}