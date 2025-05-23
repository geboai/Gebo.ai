/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.base;

/**
 * AI generated comments
 * A reference class extending GLookupEntry which holds a reference to an object of type GBaseObject or its subclass.
 *
 * @param <Type> The type of object this class will reference, extending GBaseObject.
 */
public class GLookupEntryRef<Type extends GBaseObject> extends GLookupEntry {

    // Holds a reference to a GObjectRef of type defined by Type.
    private GObjectRef<Type> reference = null;

    /**
     * Default constructor for GLookupEntryRef.
     */
    public GLookupEntryRef() {

    }

    /**
     * Retrieves the reference to the GObjectRef.
     *
     * @return The current GObjectRef of the specified type.
     */
    public GObjectRef<Type> getReference() {
        return reference;
    }

    /**
     * Sets the GObjectRef reference.
     *
     * @param reference A GObjectRef of the specified type to be set.
     */
    public void setReference(GObjectRef<Type> reference) {
        this.reference = reference;
    }

    /**
     * Static factory method to create a GLookupEntryRef object from a given object of Type.
     * Initializes the code and description of the returned object, and sets the reference.
     *
     * @param t The object from which to create the GLookupEntryRef.
     * @param <Type> The type of the object extending GBaseObject.
     * @return A new GLookupEntryRef object initialized with the details from the provided object.
     */
    public static <Type extends GBaseObject> GLookupEntryRef<Type> of(Type t) {
        GLookupEntryRef<Type> out = new GLookupEntryRef<>();
        out.setCode(t.getCode());
        out.setDescription(t.getDescription());
        out.reference = GObjectRef.of(t);
        return out;
    }

    /*
    // Uncomment this method if a list-based conversion is needed.
    // Converts a list of GBaseObject types to a list of GLookupEntryRef objects.
    public static <Type1 extends GBaseObject> List<GLookupEntryRef<Type1>> of(List<Type1> t) {
        List<GLookupEntryRef<Type1>> out = new ArrayList<>();
        if (t != null)
            for (Type1 type : t) {
                out.add(of(type));
            }
        return out;
    } 
    */
}