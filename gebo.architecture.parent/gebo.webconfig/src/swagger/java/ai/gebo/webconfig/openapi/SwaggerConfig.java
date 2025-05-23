/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.webconfig.openapi;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ai.gebo.openapi.GeboOpenAITypeDecoration;

/**
 * Gebo.ai comment agent
 * Configuration class for Swagger to customize operation IDs in API documentation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Bean definition for an OperationCustomizer that customizes the operation ID 
     * for Swagger API documentation based on the method and class names.
     *
     * @return an OperationCustomizer which modifies the operation ID 
     *         using the controller's method name and a decoration class name if present.
     */
    @Bean
    public OperationCustomizer customOperationIdFromMethod() {
        return (operation, handlerMethod) -> {
            // Extract the simple name of the class where the method is defined.
            String className = handlerMethod.getBeanType().getSimpleName();
            // Get the name of the method.
            String methodName = handlerMethod.getMethod().getName();
            // Obtain the type of the controller.
            Class<?> controllerType = handlerMethod.getBeanType();
            // Retrieve the GeboOpenAITypeDecoration annotation from the controller type, if present.
            GeboOpenAITypeDecoration annotation = controllerType.getAnnotation(GeboOpenAITypeDecoration.class);
            if (annotation != null) {
                // Get the decoration class from the annotation.
                Class _type = annotation.decorationClass();
                // Set the operation ID in the format of methodName plus the decoration class’s simple name.
                operation.setOperationId(methodName + _type.getSimpleName());
            }
            // Return the customized operation.
            return operation;
        };
    }

}