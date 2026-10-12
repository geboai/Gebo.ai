/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.anthropic.core.JsonField;
import com.anthropic.core.JsonValue;
import com.anthropic.models.beta.models.BetaCapabilitySupport;
import com.anthropic.models.beta.models.BetaEffortCapability;
import com.anthropic.models.beta.models.BetaModelCapabilities;
import com.anthropic.models.beta.models.BetaModelInfo;
import com.anthropic.models.beta.models.BetaThinkingCapability;
import com.anthropic.models.beta.models.BetaThinkingTypes;

import ai.gebo.llms.anthropic.model.AnthropicThinkingSupport;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * Turns the Models API's {@code BetaModelInfo} into a {@link GAnthropicChatModelChoice}.
 * <p>
 * Every field is read through its raw {@code JsonField}: the typed accessors of the
 * sdk throw when the api leaves a field out, and a model the sdk only partly knows
 * must still be listed. The fields newer than the sdk on the classpath (lifecycle,
 * line, deprecated_at, retires_at, thinking.types.disabled) are read from the
 * additional properties, where the sdk keeps what it has no field for.
 */
final class AnthropicModelInfoMapper {

	static final String PROVIDER_ID = "anthropic";
	static final String INFORMATIVE_URL = "https://platform.claude.com/docs/en/about-claude/models/overview";

	static final String LIFECYCLE = "lifecycle";
	static final String LINE = "line";
	static final String MAX_TOKENS = "maxTokens";
	static final String LIFECYCLE_DEPRECATED = "deprecated";

	private AnthropicModelInfoMapper() {
	}

	static GAnthropicChatModelChoice toChoice(BetaModelInfo info) {
		String id = info._id().asKnown().orElse(null);
		String displayName = info._displayName().asKnown().filter(name -> !name.isBlank()).orElse(id);
		Optional<BetaModelCapabilities> capabilities = info._capabilities().asKnown();
		Map<String, JsonValue> extra = info._additionalProperties();

		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(PROVIDER_ID);
		meta.setModelId(id);
		meta.setChatModel(true);
		meta.setEmbeddingModel(false);
		meta.setInformativeUrl(INFORMATIVE_URL);
		// Every claude model takes tools, the api has no capability for it
		meta.setSupportsFunctionCalls(true);
		meta.setContextLength(toInteger(info._maxInputTokens().asKnown()));
		meta.setMaxOutputToken(toInteger(info._maxTokens().asKnown()));
		capabilities.ifPresent(c -> {
			meta.setSupportsStructuredOutput(supported(c._structuredOutputs()));
			meta.setSupportsVision(supported(c._imageInput()));
			meta.setSupportsReasoning(c._thinking().asKnown().map(BetaThinkingCapability::_supported)
					.flatMap(JsonField::asKnown).orElse(null));
		});
		String lifecycle = string(extra, "lifecycle");
		String deprecatedAt = string(extra, "deprecated_at");
		String retiresAt = string(extra, "retires_at");
		boolean deprecated = LIFECYCLE_DEPRECATED.equals(lifecycle);
		meta.setDeprecated(lifecycle != null ? deprecated : null);
		meta.setDeprecationDate(deprecatedAt);
		meta.setRetirementDate(retiresAt);
		meta.setDescription(describe(displayName, deprecated, retiresAt));

		GAnthropicChatModelChoice choice = new GAnthropicChatModelChoice();
		choice.setCode(id);
		choice.setDescription(meta.getDescription());
		choice.setMetaInfos(meta);
		choice.setInformativeUrl(INFORMATIVE_URL);
		choice.setContextLength(meta.getContextLength());
		choice.setSupportsFunctionCalls(meta.getSupportsFunctionCalls());
		choice.setSupportsStructuredOutput(meta.getSupportsStructuredOutput());
		if (lifecycle != null)
			choice.getModelDetails().put(LIFECYCLE, lifecycle);
		String line = string(extra, "line");
		if (line != null)
			choice.getModelDetails().put(LINE, line);
		if (meta.getMaxOutputToken() != null)
			choice.getModelDetails().put(MAX_TOKENS, meta.getMaxOutputToken());
		AnthropicThinkingSupport thinking = thinkingSupport(info);
		if (thinking != null)
			thinking.writeTo(choice.getModelDetails());
		return choice;
	}

	/**
	 * The thinking a model accepts, or null when the api did not report its
	 * capabilities.
	 */
	static AnthropicThinkingSupport thinkingSupport(BetaModelInfo info) {
		Optional<BetaModelCapabilities> capabilities = info._capabilities().asKnown();
		if (capabilities.isEmpty())
			return null;
		Optional<BetaThinkingCapability> thinking = capabilities.get()._thinking().asKnown();
		Optional<BetaThinkingTypes> types = thinking.flatMap(t -> t._types().asKnown());
		if (types.isEmpty())
			return null;
		boolean adaptive = Boolean.TRUE.equals(supported(types.get()._adaptive()));
		boolean budget = Boolean.TRUE.equals(supported(types.get()._enabled()));
		// Missing means accepted: the api reports false exactly when "disabled" is refused
		boolean disabled = !Boolean.FALSE.equals(supportedFlag(types.get()._additionalProperties().get("disabled")));
		Set<String> levels = new LinkedHashSet<>();
		capabilities.get()._effort().asKnown().filter(e -> e._supported().asKnown().orElse(false)).ifPresent(e -> {
			addIfSupported(levels, "low", e._low());
			addIfSupported(levels, "medium", e._medium());
			addIfSupported(levels, "high", e._high());
			addIfSupported(levels, "xhigh", e._xhigh());
			addIfSupported(levels, "max", e._max());
		});
		return new AnthropicThinkingSupport(adaptive, budget, disabled, levels);
	}

	static String describe(String displayName, boolean deprecated, String retiresAt) {
		if (!deprecated)
			return displayName;
		if (retiresAt != null && retiresAt.length() >= 10)
			return displayName + " (deprecated, retires " + retiresAt.substring(0, 10) + ")";
		return displayName + " (deprecated)";
	}

	private static void addIfSupported(Set<String> levels, String level, JsonField<BetaCapabilitySupport> field) {
		if (Boolean.TRUE.equals(supported(field)))
			levels.add(level);
	}

	private static Boolean supported(JsonField<BetaCapabilitySupport> field) {
		return field.asKnown().flatMap(s -> s._supported().asKnown()).orElse(null);
	}

	private static Boolean supportedFlag(JsonValue value) {
		if (value == null)
			return null;
		return plain(value) instanceof Map<?, ?> object && object.get("supported") instanceof Boolean supported
				? supported
				: null;
	}

	private static String string(Map<String, JsonValue> extra, String key) {
		JsonValue value = extra.get(key);
		return value != null && plain(value) instanceof String text ? text : null;
	}

	/**
	 * A raw json value as plain java: Map, List, String, Number, Boolean or null. The
	 * sdk's typed views of a JsonValue lose their generics through its kotlin types.
	 */
	private static Object plain(JsonValue value) {
		try {
			return value.convert(Object.class);
		} catch (RuntimeException e) {
			return null;
		}
	}

	private static Integer toInteger(Optional<Long> value) {
		return value.map(Long::intValue).orElse(null);
	}
}
