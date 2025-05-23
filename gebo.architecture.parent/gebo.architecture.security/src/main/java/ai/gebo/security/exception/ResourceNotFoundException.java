/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Gebo.ai comment agent
 *
 * Exception thrown when a specified resource is not found.
 * This exception results in a HTTP 404 Not Found response.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    // Name of the resource that was not found
    private String resourceName;

    // Name of the field used to locate the resource
    private String fieldName;

    // Value of the field used in the search for the resource
    private Object fieldValue;

    /**
     * Constructs a new ResourceNotFoundException with the specified details.
     *
     * @param resourceName the name of the resource that could not be found
     * @param fieldName the name of the field that was used to identify the resource
     * @param fieldValue the value of the field that was used in the search
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Returns the name of the resource that was not found.
     *
     * @return the resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Returns the name of the field used to identify the resource.
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the value of the field that was used in the search.
     *
     * @return the field value
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}