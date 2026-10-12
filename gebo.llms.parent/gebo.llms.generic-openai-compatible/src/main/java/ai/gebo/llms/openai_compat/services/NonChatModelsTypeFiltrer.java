/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrer;

/**
 * Keeps out of the chat models list of the providers whose {@code /v1/models} tells
 * no model type the models that are not for chatting: speech, transcription,
 * safety classifiers, reward and parsing models. Their ids are matched by name, as
 * the providers name them (2026-10-11):
 * <ul>
 * <li>Groq, console.groq.com/docs/models: speech to text {@code whisper-large-v3*},
 * text to speech {@code canopylabs/orpheus-*}, safety
 * {@code meta-llama/llama-prompt-guard-2-*} and {@code openai/gpt-oss-safeguard-20b};</li>
 * <li>NVIDIA, integrate.api.nvidia.com/v1/models: the guard and content safety models
 * ({@code llama-guard-4-12b}, {@code *-nemoguard-*}, {@code *-safety-guard-*},
 * {@code nemotron-3.5-content-safety}), {@code nemotron-4-340b-reward},
 * {@code nemotron-parse*}, {@code nvclip} and {@code ai-synthetic-video-detector}.</li>
 * </ul>
 * The embedding models stay out as with the default filter ("embed" in the id).
 */
@Service
public class NonChatModelsTypeFiltrer implements ILLMTypeFiltrer {
	private static final Logger LOGGER = LoggerFactory.getLogger(NonChatModelsTypeFiltrer.class);

	/** The chat model types filtered. */
	static final Set<String> TYPES = Set.of("chatmodel-groq", "chatmodel-nvidia");
	/** The id fragments of the models that do not chat, of either provider. */
	static final List<String> NON_CHAT = List.of("embed", "whisper", "orpheus", "-tts", "guard", "content-safety",
			"-reward", "nemotron-parse", "nvclip", "video-detector");

	@Override
	public boolean isDefaultFilter() {
		return false;
	}

	@Override
	public boolean isHandlerSupported(String handlerId) {
		return handlerId != null && TYPES.contains(handlerId);
	}

	@Override
	public <T extends GBaseChatModelChoice> List<T> filterChatModels(List<T> models) {
		if (models == null)
			return List.of();
		return models.stream().filter(x -> {
			final boolean chats = chats(x.getCode());
			if (!chats && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Model {} left out of the chat models: not a chat model", x.getCode());
			}
			return chats;
		}).toList();
	}

	static boolean chats(String code) {
		if (code == null)
			return true;
		final String lower = code.toLowerCase();
		return NON_CHAT.stream().noneMatch(lower::contains);
	}

	@Override
	public <T extends GBaseEmbeddingModelChoice> List<T> filterEmbeddingModels(List<T> models) {
		return models != null
				? models.stream().filter(x -> (x.getCode() != null && x.getCode().toLowerCase().indexOf("embed") >= 0))
						.toList()
				: List.of();
	}
}
