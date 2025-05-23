/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.DefaultToolDefinition;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.execution.ToolCallResultConverter;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback.Builder;
import org.springframework.ai.tool.metadata.ToolMetadata;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.core.ParameterizedTypeReference;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;

/**
 * Utility class for declaring tool callbacks for AI applications.
 * AI generated comments
 */
public class ToolCallbackDeclarationUtil {
    private static final String UNIQUE_CONTEXT_ID = "uniqueContextId";

    /**
     * A non-functional class used as a return type placeholder for void functions.
     */
    public static class NoReturnType {
    }

    /**
     * Declares a ToolCallback for a function with no return.
     *
     * @param <T>         the input type of the function
     * @param function    the consumer function to be wrapped
     * @param functionName the name of the function
     * @param description a brief description of the function
     * @param paramType   the class of the function's parameter
     * @return a ToolCallback wrapping the provided function
     */
    public static <T> ToolCallback declare(Consumer<T> function, String functionName, String description,
                                           Class<T> paramType) {
        // Wraps the provided function to fit the required BiFunction interface
        BiFunction<T, ToolContext, NoReturnType> wrappingFunction = (T p, ToolContext c) -> {
            function.accept(p);
            return new NoReturnType();
        };
        
        // Generates the input schema for the function's parameter type
        String inputSchema = JsonSchemaGenerator.generateForType(paramType);
        
        // Defines the tool with the provided name, description, and input schema
        ToolDefinition toolDefinition = new DefaultToolDefinition(functionName, description, inputSchema);
        ToolMetadata toolMetaData = null;
        
        // Wraps the defined tool in a FunctionToolCallback
        FunctionToolCallback<T, NoReturnType> f = new FunctionToolCallback<T, NoReturnType>(toolDefinition,
                toolMetaData, paramType, wrappingFunction, null);

        return f;
    }

    /**
     * Declares a ToolCallback for a function with a return value.
     *
     * @param <T>          the input type of the function
     * @param <R>          the return type of the function
     * @param function     the bi-function to be wrapped
     * @param functionName the name of the function
     * @param description  a brief description of the function
     * @param paramType    the class of the function's parameter
     * @param returnedType the class of the function's return type
     * @return a ToolCallback wrapping the provided function
     */
    public static <T, R> ToolCallback declare(BiFunction<T, ToolContext, R> function, String functionName,
                                              String description, Class<T> paramType, Class<R> returnedType) {
        // Generates the input schema for the function's parameter type
        String inputSchema = JsonSchemaGenerator.generateForType(paramType);

        // Defines the tool with the provided name, description, and input schema
        ToolDefinition toolDefinition = new DefaultToolDefinition(functionName, description, inputSchema);
        ToolMetadata toolMetaData = null;

        // Wraps the defined tool in a FunctionToolCallback
        FunctionToolCallback<T, R> f = new FunctionToolCallback<T, R>(toolDefinition, toolMetaData, paramType, function,
                null);

        return f;
    }

    private final static String FUNCTIONS_CALLED = "GEBO-FUNCTIONS-CALLED";

    /**
     * Creates a new tool context environment with a unique context ID.
     *
     * @return a map representing the tool context environment
     */
    public static Map<String, Object> newToolContextEnvironment() {
        Map<String, Object> context = new HashMap<>();
        context.put(UNIQUE_CONTEXT_ID, UUID.randomUUID().toString());
        return context;
    }

    /**
     * Adds a function call to the tool context.
     *
     * @param c        the tool context
     * @param function the called function to add
     */
    public static void addCallToContext(ToolContext c, CalledFunction function) {

        if (c != null && c.getContext() != null) {
            if (c.getContext().containsKey("CONTEXT")) {
                // Retrieves and updates the context with the called function
                KBContext ctx = (KBContext) c.getContext().get("CONTEXT");
                ctx.getCalledFunctions().add(function);
            }
            String id = (String) c.getContext().get(UNIQUE_CONTEXT_ID);

        }

    }

    /**
     * Creates a new tool context environment with existing KBContext.
     *
     * @param context the existing KBContext to be included
     * @return a map representing the tool context environment
     */
    public static Map<String, Object> newToolContextEnvironment(KBContext context) {
        Map<String, Object> map = newToolContextEnvironment();
        map.put("CONTEXT", context);
        return map;
    }

}