/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.base;

import org.springframework.data.mongodb.core.index.HashIndexed;

/**
 * AI generated comments
 * The GObjectRef class serves as a generic reference to an object of type BaseType,
 * which extends GBaseObject. This class helps in storing and retrieving references
 * to objects with their class name, unique code, and description.
 * 
 * @param <BaseType> The type of object that this class references, extending GBaseObject.
 */
public class GObjectRef<BaseType extends GBaseObject> {
    // Fields that are indexed with a hash for efficient lookups in MongoDB
    @HashIndexed
    private String className = null;
    @HashIndexed
    private String code = null;
    
    // Description of the object reference
    private String description = null;

    /**
     * Default constructor for GObjectRef.
     * Initializes a new instance without setting any fields.
     */
    public GObjectRef() {
    }

    /**
     * Initializes a new instance of GObjectRef using a reference object of type
     * BaseType.
     * 
     * @param reference The reference object to initialize this GObjectRef with.
     */
    public GObjectRef(BaseType reference) {
        if (reference != null) {
            this.className = reference.getClass().getName();
            this.code = reference.getCode();
            this.description = reference.getDescription();
        }
    }

    /**
     * Checks if this GObjectRef is equal to another object.
     * Equality is based on matching class name and code.
     * 
     * @param obj The object to compare with this instance.
     * @return true if the given object is equivalent to this instance; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof GObjectRef) {
            GObjectRef ref = (GObjectRef) obj;
            return ref.getCode() != null && getCode() != null && ref.getCode().equals(getCode())
                    && ref.getClassName() != null && getClassName() != null
                    && ref.getClassName().equals(getClassName());
        }
        return super.equals(obj);
    }

    /**
     * Gets the class name of the referenced object.
     * 
     * @return The class name as a string.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the class name for the reference.
     * 
     * @param className The class name to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the unique code of the referenced object.
     * 
     * @return The code as a string.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the unique code for the reference.
     * 
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Creates a new GObjectRef instance for a given object.
     * 
     * @param <BaseType> The type of the object.
     * @param object     The object to create a reference for.
     * @return A new GObjectRef instance pointing to the specified object.
     */
    public static <BaseType extends GBaseObject> GObjectRef<BaseType> of(BaseType object) {
        return new GObjectRef<BaseType>(object);
    }

    /**
     * Returns a string representation of the GObjectRef.
     * Includes the code, className, and description.
     * 
     * @return The string representation of this GObjectRef.
     */
    @Override
    public String toString() {
        return "{code:\"" + code + "\",className:\"" + className + "\",description=\"" + description + "\"}";
    }

    /**
     * Gets the description of the referenced object.
     * 
     * @return The description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for the reference.
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Generates a unique key for the reference, combining its class name and code.
     * 
     * @return A unique key string representing the reference, or null if either class name or code is missing.
     */
    public final String key() {
        if (className == null || code == null) {
            return null;
        }
        return className + "|" + code;
    }
    
}