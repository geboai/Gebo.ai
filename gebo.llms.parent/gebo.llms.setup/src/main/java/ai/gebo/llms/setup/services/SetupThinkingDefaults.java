/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.setup.services;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;

/**
 * The thinking level the setup gives a chat model its preset leaves without one, by the
 * platform's rule (llms-fast-setup-library/library.yml): a model serving the internal
 * services does not think where it can stop thinking, else thinks low; a chat model
 * thinks medium. Decided on what the model's integration tells it takes
 * ({@link GChatModelParametersSupport}): a model whose integration tells nothing, or
 * that no thinking reaches, keeps its provider's default.
 */
final class SetupThinkingDefaults {

	private SetupThinkingDefaults() {
	}

	/**
	 * The thinking level for a model created for the uses, null to leave its provider's
	 * default.
	 *
	 * @param support what the model takes, as its integration tells; may be null
	 * @param uses    the uses the model is created for; may be null (a chat model)
	 */
	static ChatModelThinkingOption forUses(GChatModelParametersSupport support, List<ChatModelsUses> uses) {
		if (support == null || !support.isKnown() || !support.isThinkingAvailable())
			return null;
		if (uses != null && uses.contains(ChatModelsUses.INTERNAL_SERVICES)) {
			if (support.offers(ChatModelThinkingOption.NO_THINKING))
				return ChatModelThinkingOption.NO_THINKING;
			return support.offers(ChatModelThinkingOption.LOW_THINKING) ? ChatModelThinkingOption.LOW_THINKING : null;
		}
		return support.offers(ChatModelThinkingOption.MEDIUM_THINKING) ? ChatModelThinkingOption.MEDIUM_THINKING
				: null;
	}

	/**
	 * The provider's details a model choice carries (the Anthropic Models API thinking
	 * capabilities, the Ollama /api/show ones), which only the providers' own choice
	 * classes have; null when the choice has none.
	 */
	@SuppressWarnings("unchecked")
	static Map<String, Object> modelDetailsOf(Object choice) {
		if (choice == null)
			return null;
		try {
			final Method getter = choice.getClass().getMethod("getModelDetails");
			final Object details = getter.invoke(choice);
			return details instanceof Map<?, ?> map ? (Map<String, Object>) map : null;
		} catch (NoSuchMethodException e) {
			return null;
		} catch (ReflectiveOperationException | RuntimeException e) {
			return null;
		}
	}
}
