/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.angular.persistence.model;

/**
 * Gebo.ai comment agent
 *
 * Represents a form control with properties to define its name and type.
 * This class is used to encapsulate the details of form control properties.
 */
public class FormControl {

    /**
     * The name of the property associated with this form control.
     */
    private String propertyName = null;

    /**
     * The Java type of the property associated with this form control.
     */
    private String javaType = null;

    /**
     * Gets the name of the property.
     * 
     * @return the property name as a String.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the name of the property.
     * 
     * @param propertyName the new property name to set.
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Gets the Java type of the property.
     * 
     * @return the Java type as a String.
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * Sets the Java type of the property.
     * 
     * @param javaType the new Java type to set.
     */
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
    
}