/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptSpecification;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.Prompt;
import io.modelcontextprotocol.spec.McpSchema.PromptArgument;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import lombok.AllArgsConstructor;

/**
 * Builds the MCP prompt specifications exposed by an erogated MCP server from the
 * {@code exportedPrompts} (internal prompt template codes) of its
 * {@link GeboMCPServerConfig}.
 * <p>
 * Each exported {@link GPromptTemplateConfig} becomes an MCP prompt whose arguments
 * are derived from the {@code {placeholder}} tokens of its templates. When the prompt
 * is fetched, the provided argument values are substituted into the system and user
 * templates and returned as a single user message.
 */
@Service
@AllArgsConstructor
public class GeboMcpPromptsProvider {

	private final IGPromptConfigDao promptConfigDao;

	/**
	 * Builds all prompt specifications for the given configuration.
	 *
	 * @param config the MCP server configuration
	 * @return the prompt specifications to register, never {@code null}
	 */
	public List<SyncPromptSpecification> buildPrompts(GeboMCPServerConfig config) {
		List<SyncPromptSpecification> specs = new ArrayList<>();
		if (config.getExportedPrompts() == null) {
			return specs;
		}
		for (String code : config.getExportedPrompts()) {
			GPromptTemplateConfig template = promptConfigDao.findByCode(code);
			if (template == null) {
				continue;
			}
			specs.add(buildSpec(template));
		}
		return specs;
	}

	private SyncPromptSpecification buildSpec(GPromptTemplateConfig template) {
		List<PromptArgument> arguments = new ArrayList<>();
		for (String placeholder : template.getPlaceholders().keySet()) {
			arguments.add(new PromptArgument(placeholder, null, Boolean.FALSE));
		}
		String name = template.getCode();
		String description = template.getDescription() != null ? template.getDescription() : template.getPromptUse();
		Prompt prompt = new Prompt(name, description, arguments);
		return new SyncPromptSpecification(prompt, (exchange, request) -> {
			String rendered = render(template, request.arguments());
			PromptMessage message = new PromptMessage(Role.USER, new TextContent(rendered));
			return new GetPromptResult(description, List.of(message));
		});
	}

	/**
	 * Substitutes the supplied argument values into the system and user templates.
	 */
	private static String render(GPromptTemplateConfig template, Map<String, Object> arguments) {
		StringBuilder sb = new StringBuilder();
		if (template.getSystemPromptTemplate() != null) {
			sb.append(template.getSystemPromptTemplate());
		}
		if (template.getUserPromptTemplate() != null) {
			if (sb.length() > 0) {
				sb.append("\n\n");
			}
			sb.append(template.getUserPromptTemplate());
		}
		String text = sb.toString();
		if (arguments != null) {
			for (Map.Entry<String, Object> entry : arguments.entrySet()) {
				String value = entry.getValue() != null ? entry.getValue().toString() : "";
				text = text.replace("{" + entry.getKey() + "}", value);
			}
		}
		return text;
	}
}
