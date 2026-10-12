/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.openrouter.client.model.OpenRouterModel;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * The parameter rules of a model from what the OpenRouter models endpoint publishes
 * for it, kept on the choice as its native meta infos when it was listed:
 * <ul>
 * <li>{@code reasoning.supported_efforts}: the efforts the model takes; {@code none}
 * added unless {@code reasoning.mandatory}, OpenRouter documenting that a mandatory
 * model rejects it (openrouter.ai/docs/guides/best-practices/reasoning-tokens,
 * 2026-10-11);</li>
 * <li>{@code supported_parameters} without {@code temperature} (e.g. the
 * {@code :batch} variants): the sampling is rejected.</li>
 * </ul>
 * Null when the native meta infos are not an OpenRouter model's (another provider,
 * or a choice made without listing the models).
 */
final class OpenRouterLiveParameterRules {
	static final String SOURCE = "openrouter.ai/api/v1/models, read when the model was listed";
	private static final JsonMapper MAPPER = JsonMapper.builder()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();

	private OpenRouterLiveParameterRules() {
	}

	static ModelParameterRules of(Object nativeMetaInfos, String providerId) {
		final OpenRouterModel model = asModel(nativeMetaInfos);
		if (model == null || model.getId() == null || model.getSupportedParameters() == null)
			return null;
		final ModelParameterRules rules = new ModelParameterRules();
		rules.setProviderId(providerId);
		rules.setModels(new ArrayList<>(List.of(model.getId())));
		rules.setSource(SOURCE);
		if (!model.getSupportedParameters().contains("temperature"))
			rules.setSampling(Sampling.REJECTED);
		if (model.getReasoning() != null && model.getReasoning().getSupportedEfforts() != null
				&& !model.getReasoning().getSupportedEfforts().isEmpty()) {
			final List<String> efforts = new ArrayList<>(model.getReasoning().getSupportedEfforts());
			if (!Boolean.TRUE.equals(model.getReasoning().getMandatory())
					&& !efforts.contains(ModelParameterRules.NO_REASONING_EFFORT))
				efforts.add(ModelParameterRules.NO_REASONING_EFFORT);
			rules.setReasoningEfforts(efforts);
		}
		return rules;
	}

	/**
	 * The native meta infos as an OpenRouter model: as listed, or as read back from a
	 * saved configuration (a map); null when they are something else.
	 */
	static OpenRouterModel asModel(Object nativeMetaInfos) {
		if (nativeMetaInfos == null)
			return null;
		if (nativeMetaInfos instanceof OpenRouterModel model)
			return model;
		if (!(nativeMetaInfos instanceof java.util.Map<?, ?>))
			return null;
		return MAPPER.convertValue(nativeMetaInfos, OpenRouterModel.class);
	}
}
