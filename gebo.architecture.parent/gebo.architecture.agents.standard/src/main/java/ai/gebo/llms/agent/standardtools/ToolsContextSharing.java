/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.HashMap;
import java.util.Map;


import ai.gebo.llms.abstraction.layer.model.ForwardingChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Shares an object of the calling agent with the tools it calls: the request
 * context of its model calls carries the object in its tools context, the tools
 * read it from their {@code ToolContext} (see {@link ToolsFoundDocuments},
 * {@link ToolsProgress}).
 */
final class ToolsContextSharing {
	private ToolsContextSharing() {
	}

	/** The context, its tools context also carrying the value under the key. */
	static IChatRequestContext with(IChatRequestContext context, String key, Object value) {
		return new ForwardingChatRequestContext(context) {
			@Override
			public Map<String, Object> getToolsContext() {
				final Map<String, Object> toolsContext = context.getToolsContext() != null
						? new HashMap<>(context.getToolsContext())
						: new HashMap<>();
				toolsContext.put(key, value);
				return toolsContext;
			}
		};
	}
}
