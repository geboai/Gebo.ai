/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.searxngsearch.handler.model.SearxngSearchConfig;

/** Exposes SearXNG web search as an internet-browsing LLM tool, if configured. */
@Service
public class SearxngSearchFunctionCallbackWrapperSourceImpl implements IGToolCallbackSource {

	@Autowired
	SearxngSearchConfigDaoImpl dao;
	@Autowired
	SearxngSearchApi searxngApi;

	@Override
	public String getId() {
		return this.getClass().getName();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		List<ToolCallback> out = new ArrayList<ToolCallback>();
		List<SearxngSearchConfig> configurations = dao.getConfigurations();
		if (!configurations.isEmpty()) {
			out.add(searxngApi.create(configurations.get(0)));
		}
		return out;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.INTERNET_BROWSING;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		return List.of();
	}
}
