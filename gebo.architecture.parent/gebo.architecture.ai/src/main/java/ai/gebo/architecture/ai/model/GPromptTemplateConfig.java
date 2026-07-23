/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.ai.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.ToString;

/**
 * AI generated comments GPromptConfig class represents the configuration for a
 * prompt used in chat models. It contains various properties that define the
 * characteristics of the prompt.
 */
@Document
@ToString
public class GPromptTemplateConfig implements Cloneable, ITokensCountable {
	public static final String DEFAULT_LANGUAGE = "en";
	private static final String SEPARATOR = "-";
	private static final String FIELD_PLACEHOLDER = "-|-";
	/** Matches template placeholders in the {placeholder} form. */
	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^{}]+)\\}");
	@Id
	private String code = null;
	private String description = null;
	private String systemPromptTemplate = null;
	@NotNull
	private String userPromptTemplate = null;
	@NotNull
	private ContextContentRequired chatHistory = null;
	@NotNull
	private ContextContentRequired contextDocuments = null;
	@NotNull
	private ContextContentRequired toolsCalling = null;
	@HashIndexed
	private String langCode = DEFAULT_LANGUAGE;
	@HashIndexed
	@NotNull
	private String promptUse = null;
	// internal llm provider handler id
	@HashIndexed
	private String modelProvider = null;
	// model code
	@HashIndexed
	private String modelCode = null;
	private String promptCategory = null;
	private int tokensSize = 0;
	private Boolean configDeclarated;
	private Boolean agentPrompt = null;
	private String agentId = null;
	/**
	 * Placeholders ({placeholder} tokens) discovered in the system/user prompt
	 * templates. Derived and runtime-maintained: it is (re)computed from the
	 * templates and never persisted nor settable from the calling code.
	 */
	@Transient
	@ToString.Exclude
	private transient Map<String, Boolean> placeholders = null;
	private void regenerateCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(value(promptUse));
		buffer.append(SEPARATOR);
		if (langCode == null || langCode.trim().length() == 0) {
			langCode = DEFAULT_LANGUAGE;
		}
		buffer.append(value(langCode));
		buffer.append(SEPARATOR);
		buffer.append(value(modelProvider));
		buffer.append(SEPARATOR);
		buffer.append(value(modelCode));
		this.code = buffer.toString();
	}

	private String value(String s) {
		if (s == null || s.trim().length() == 0)
			return FIELD_PLACEHOLDER;
		return s.trim().toUpperCase();
	}

	/**
	 * Creates a new GPromptConfig instance with a specified prompt. Generates a
	 * random UUID code and assigns it to the new instance.
	 * 
	 * @param prompt2 the prompt to set in the new configuration.
	 * @return a new instance of GPromptConfig with the specified prompt.
	 */
	public static GPromptTemplateConfig of(String systemPrompt, String userPrompt, String promptUse) {
		GPromptTemplateConfig cfg = new GPromptTemplateConfig();
		cfg.setUserPromptTemplate(userPrompt);
		cfg.setSystemPromptTemplate(systemPrompt);
		cfg.setPromptUse(promptUse);
		return cfg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
		regenerateCode();
	}

	public String getPromptUse() {
		return promptUse;
	}

	public void setPromptUse(String promptUse) {
		this.promptUse = promptUse;
		regenerateCode();
	}

	public String getModelProvider() {
		return modelProvider;
	}

	public void setModelProvider(String modelProvider) {
		this.modelProvider = modelProvider;
		regenerateCode();
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelName) {
		this.modelCode = modelName;
		regenerateCode();
	}

	public String getPromptCategory() {
		return promptCategory;
	}

	public void setPromptCategory(String promptCategory) {
		this.promptCategory = promptCategory;
	}

	public Boolean getConfigDeclarated() {
		return configDeclarated;
	}

	public void setConfigDeclarated(Boolean staticDeclarated) {
		this.configDeclarated = staticDeclarated;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Something is going very very wrongly", e);
		}
	}

	public GPromptTemplateConfig copy() {
		return (GPromptTemplateConfig) this.clone();
	}

	public int getTokensSize() {

		return ITokensCountable.stringsTokensSize(userPromptTemplate, systemPromptTemplate);
	}

	public void setTokensSize(int tokensLength) {
		this.tokensSize = tokensLength;
	}

	public String getSystemPromptTemplate() {
		return systemPromptTemplate;
	}

	public void setSystemPromptTemplate(String systemPromptTemplate) {
		this.systemPromptTemplate = systemPromptTemplate;
		invalidatePlaceholders();
	}

	public String getUserPromptTemplate() {
		return userPromptTemplate;
	}

	public void setUserPromptTemplate(String userPromptTemplate) {
		this.userPromptTemplate = userPromptTemplate;
		invalidatePlaceholders();
	}

	/**
	 * Returns the placeholders ({placeholder} tokens) contained in the system and
	 * user prompt templates. The map is lazily (re)computed from the current
	 * template contents and returned as an unmodifiable view, so it is maintained
	 * by this object at runtime but cannot be mutated by the calling code.
	 *
	 * @return an unmodifiable map keyed by placeholder name, each mapped to
	 *         {@code Boolean.TRUE}.
	 */
	public Map<String, Boolean> getPlaceholders() {
		if (placeholders == null) {
			placeholders = computePlaceholders();
		}
		return Collections.unmodifiableMap(placeholders);
	}

	private void invalidatePlaceholders() {
		this.placeholders = null;
	}

	private Map<String, Boolean> computePlaceholders() {
		Map<String, Boolean> found = new LinkedHashMap<>();
		collectPlaceholders(systemPromptTemplate, found);
		collectPlaceholders(userPromptTemplate, found);
		return found;
	}

	private static void collectPlaceholders(String template, Map<String, Boolean> sink) {
		if (template == null || template.isEmpty()) {
			return;
		}
		Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
		while (matcher.find()) {
			String name = matcher.group(1).trim();
			if (!name.isEmpty()) {
				sink.put(name, Boolean.TRUE);
			}
		}
	}

	public ContextContentRequired getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(ContextContentRequired chatHistory) {
		this.chatHistory = chatHistory;
	}

	public ContextContentRequired getContextDocuments() {
		return contextDocuments;
	}

	public void setContextDocuments(ContextContentRequired contextDocuments) {
		this.contextDocuments = contextDocuments;
	}

	public ContextContentRequired getToolsCalling() {
		return toolsCalling;
	}

	public void setToolsCalling(ContextContentRequired toolsCalling) {
		this.toolsCalling = toolsCalling;
	}

	public Boolean getAgentPrompt() {
		return agentPrompt;
	}

	public void setAgentPrompt(Boolean agentPrompt) {
		this.agentPrompt = agentPrompt;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
}