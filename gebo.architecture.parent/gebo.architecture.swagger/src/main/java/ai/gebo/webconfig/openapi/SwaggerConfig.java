/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.webconfig.openapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.openapi.GeboOpenAITypeDecoration;

/**
 * Gebo.ai comment agent
 * Configuration class for Swagger to customize operation IDs in API documentation.
 *
 * <p>
 * The OpenAPI defaults shared by every application shipping this module live in
 * {@link GeboOpenApiDefaultsAutoConfiguration} instead, so that they reach even
 * the services which do not component scan this package.
 */
@Configuration
public class SwaggerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);
    /** The suffix springdoc appends to an operationId already taken: "_1", "_2"... */
    private static final Pattern DUPLICATED_OPERATION_ID = Pattern.compile(".*_\\d+$");

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
                Class<?> _type = annotation.decorationClass();
                // Set the operation ID in the format of methodName plus the decoration class’s simple name.
                operation.setOperationId(methodName + _type.getSimpleName());
            }
            // Return the customized operation.
            return operation;
        };
    }

    /**
     * Reports the operationIds springdoc had to suffix because their name was already
     * taken: swagger-codegen turns them into numbered methods (delete1, findByCode2)
     * whose numbering changes as controllers are added, breaking the generated
     * clients. Every controller method must carry a unique, decorated name
     * (operation + handled type, or {@link GeboOpenAITypeDecoration}).
     */
    @Bean
    public OpenApiCustomizer duplicatedOperationIdsReporter() {
        return openApi -> {
            if (openApi.getPaths() == null) {
                return;
            }
            List<String> duplicated = new ArrayList<>();
            openApi.getPaths().forEach((path, item) -> item.readOperationsMap().forEach((method, operation) -> {
                if (operation.getOperationId() != null
                        && DUPLICATED_OPERATION_ID.matcher(operation.getOperationId()).matches()) {
                    duplicated.add(operation.getOperationId() + " (" + method + " " + path + ")");
                }
            }));
            if (!duplicated.isEmpty()) {
                LOGGER.error("OpenAPI operationIds duplicated by controller method names, rename them with a unique"
                        + " decorated name: " + duplicated);
            } else if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("OpenAPI operationIds are all unique");
            }
        };
    }

}