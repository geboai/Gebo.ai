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
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;

/**
 * Shares an object of the calling agent with the tools it calls: the request
 * context of its model calls carries the object in its tools context, the tools
 * read it from their {@code ToolContext} (see {@link ToolsFoundDocuments},
 * {@link ToolsProgress}).
 */
final class ToolsContextSharing {

	private ToolsContextSharing() {
	}

	/**
	 * The given request context, its tools context also carrying the value under the
	 * key: every other value is still read from the given context.
	 */
	static IChatRequestContext with(IChatRequestContext context, String key, Object value) {
		return new IChatRequestContext() {
			@Override
			public String getRequestID() {
				return context.getRequestID();
			}

			@Override
			public String getSessionID() {
				return context.getSessionID();
			}

			@Override
			public String getConsolidatedHistory() {
				return context.getConsolidatedHistory();
			}

			@Override
			public List<IChatSessionEntry> getInteractions() {
				return context.getInteractions();
			}

			@Override
			public List<Document> getDocuments() {
				return context.getDocuments();
			}

			@Override
			public String getActualUserRequest() {
				return context.getActualUserRequest();
			}

			@Override
			public Map<String, Object> getToolsContext() {
				final Map<String, Object> toolsContext = context.getToolsContext() != null
						? new HashMap<>(context.getToolsContext())
						: new HashMap<>();
				toolsContext.put(key, value);
				return toolsContext;
			}

			@Override
			public Map<String, Object> getPipelineInfos() {
				return context.getPipelineInfos();
			}

			@Override
			public ToolCallsListener getToolCallListener() {
				return context.getToolCallListener();
			}
		};
	}
}
