/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents a polymorphic JSON type using Jackson annotations for type information.
 * 
 * The @JsonTypeInfo annotation is used to manage how the type information is included in the serialized JSON.
 * - `use`: specifies how to include type information. In this case, it uses `JsonTypeInfo.Id.CLASS` which includes the Java class name in the JSON.
 * - `include`: specifies how the type information should be included in JSON; here it is included as a property.
 * - `property`: the name of the JSON property that will hold the type information.
 * - `visible`: if set to true, makes the type information available to custom deserializers.
 * 
 * Gebo.ai comment agent
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "__class", visible = true)
public class PolimorphicJsonType {

    /**
     * Default constructor for PolimorphicJsonType class.
     */
    public PolimorphicJsonType() {

    }

}