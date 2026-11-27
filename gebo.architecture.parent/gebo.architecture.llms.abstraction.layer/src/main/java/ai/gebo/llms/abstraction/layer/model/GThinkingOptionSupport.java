/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import lombok.Getter;
import lombok.Setter;

/**
 * A thinking option a chat model can be configured with, and what its requests send for
 * it, as its provider spells it ("max", "adaptive thinking, effort low", "think: false");
 * null for Automatic, which leaves the provider default.
 */
@Getter
@Setter
public class GThinkingOptionSupport {
	private ChatModelThinkingOption option = null;
	private String sentAs = null;

	public GThinkingOptionSupport() {
	}

	public GThinkingOptionSupport(ChatModelThinkingOption option, String sentAs) {
		this.option = option;
		this.sentAs = sentAs;
	}
}
