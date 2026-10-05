/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.ai.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.DefaultToolDefinition;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.metadata.ToolMetadata;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;


/**
 * Utility class for declaring tool callbacks for AI applications. AI generated
 * comments
 */
public class ToolCallbackDeclarationUtil {
	/**
	 * Tools context key carrying the id of the user request the tools are called
	 * for: every model call of the same request (agents iterations included) shares
	 * it, so a tool can keep request-scoped state across its invocations.
	 */
	public static final String REQUEST_ID_CONTEXT_KEY = "geboRequestId";

	/**
	 * The id of the user request a tool is called for, or null when the caller did
	 * not provide one in the tools context.
	 */
	public static String requestId(ToolContext toolContext) {
		if (toolContext == null || toolContext.getContext() == null) {
			return null;
		}
		Object value = toolContext.getContext().get(REQUEST_ID_CONTEXT_KEY);
		return value instanceof String id && !id.isBlank() ? id : null;
	}

	/**
	 * Tools context key carrying the codes of the knowledge bases of the chat the
	 * tools are called for: the ones its session's chat profile gives (see
	 * {@code IGChatSessionLifeCycleService#getSessionAvailableKnowledgeBases}), set
	 * once per user request and shared by every model call of it, agents included.
	 */
	public static final String CHAT_KNOWLEDGE_BASES_CONTEXT_KEY = "geboChatKnowledgeBases";

	/**
	 * The codes of the knowledge bases of the chat a tool is called for. Empty when the
	 * chat has none, and when the tool is not called for a chat session: a call
	 * outside a chat has no knowledge base.
	 */
	public static List<String> chatKnowledgeBases(ToolContext toolContext) {
		if (toolContext == null || toolContext.getContext() == null) {
			return List.of();
		}
		Object value = toolContext.getContext().get(CHAT_KNOWLEDGE_BASES_CONTEXT_KEY);
		if (!(value instanceof List<?> codes)) {
			return List.of();
		}
		return codes.stream().filter(code -> code instanceof String text && !text.isBlank()).map(String.class::cast)
				.distinct().toList();
	}

	/**
	 * A non-functional class used as a return type placeholder for void functions.
	 */
	public static class NoReturnType {
	}

	/**
	 * Declares a ToolCallback for a function with no return.
	 *
	 * @param <T>          the input type of the function
	 * @param function     the consumer function to be wrapped
	 * @param functionName the name of the function
	 * @param description  a brief description of the function
	 * @param paramType    the class of the function's parameter
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
		ToolDefinition toolDefinition = ToolDefinition.builder().name(functionName).description(description)
				.inputSchema(inputSchema).build();
		ToolMetadata toolMetaData = ToolMetadata.builder().build();

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

		ToolDefinition toolDefinition = ToolDefinition.builder().name(functionName).description(description)
				.inputSchema(inputSchema).build();
		ToolMetadata toolMetaData = ToolMetadata.builder().build();

		// Wraps the defined tool in a FunctionToolCallback
		FunctionToolCallback<T, R> f = new FunctionToolCallback<T, R>(toolDefinition, toolMetaData, paramType, function,
				null);

		return f;
	}

	/**
	 * Declares a ToolCallback whose parameter is a generic type, e.g.
	 * {@code Param<SomeQuery>} built with
	 * {@code ResolvableType.forClassWithGenerics(Param.class, SomeQuery.class).getType()}:
	 * both the input schema and the deserialization of the model arguments resolve
	 * the type arguments, so no concrete subclass has to be generated.
	 *
	 * @param paramType the (possibly parameterized) type of the function's parameter
	 */
	public static <T, R> ToolCallback declare(BiFunction<T, ToolContext, R> function, String functionName,
			String description, Type paramType) {
		String inputSchema = JsonSchemaGenerator.generateForType(paramType);
		ToolDefinition toolDefinition = ToolDefinition.builder().name(functionName).description(description)
				.inputSchema(inputSchema).build();
		ToolMetadata toolMetaData = ToolMetadata.builder().build();
		return new FunctionToolCallback<T, R>(toolDefinition, toolMetaData, paramType, function, null);
	}

}