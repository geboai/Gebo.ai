/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;

/**
 * The provider-neutral web search tool ({@value AbstractWebSearchServiceImpl#WEB_SEARCH_TOOL_NAME}):
 * it searches the web with the enabled web search provider (Google, Brave, SerpApi,
 * Tavily, SearXNG...), reads the pages found and answers with their contents
 * ranked against the search objective given by the model (see
 * {@link SearchToolContentPipeline}), instead of the bare result snippets.
 * <p>
 * Only one web search tool is exposed whatever the number of providers configured:
 * the first enabled provider serves it. The tool is kept out of the default agents
 * network's automatic tool mounting, where the web is searched by the dedicated
 * web search agents.
 * <p>
 * Like every external source, the web has to be enabled explicitly: the tool
 * applies the same access check as deep search and the web search agents
 * ({@code IGExternalSearchSecurityService}), so until an admin has saved the deep
 * search configuration only admins can use it, and afterwards the configured
 * per-user / per-group access applies.
 */
@Service
public class WebSearchToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSearchToolSource.class);
	public static final String WEB_SEARCH_TOOL_SOURCE = "standard-web-search-tool-source";
	static final String WEB_SEARCH_TOOL_DESCRIPTION = AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_DESCRIPTION
			+ " The pages found are read, and their contents are ranked against your search objective.";
	private final ISearchServiceRepositoryPattern searchServicesRepoPattern;
	private final SearchToolContentPipeline pipeline;

	public WebSearchToolSource(ISearchServiceRepositoryPattern searchServicesRepoPattern,
			SearchToolContentPipeline pipeline) {
		this.searchServicesRepoPattern = searchServicesRepoPattern;
		this.pipeline = pipeline;
	}

	@Override
	public String getId() {
		return WEB_SEARCH_TOOL_SOURCE;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.INTERNET_BROWSING;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		AbstractSearchServiceWrapperTool tool = webSearchTool();
		return tool != null ? List.of(tool.toToolReference()) : List.of();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		AbstractSearchServiceWrapperTool tool = webSearchTool();
		return tool != null ? List.of(tool.toTool()) : List.of();
	}

	/** The web search sends the query to its provider and has the ranker score the pages read. */
	@Override
	public List<ToolDataFlowTarget> getDataFlowTargets(String toolName) {
		final AbstractSearchServiceWrapperTool tool = webSearchTool();
		if (tool == null || !tool.getToolName().equals(toolName)) {
			return List.of();
		}
		return List.of(ToolDataFlowTarget.searchService(tool.getWrapped().getId(), "Web search: query sent to the provider"),
				ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.RANKER_MODEL, "Web search: ranking of the pages read"));
	}

	/**
	 * The web search tool over the first enabled web search provider, null when none
	 * is: searched with the provider's own query structure when it has one (site,
	 * freshness, language...), with plain text otherwise.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	AbstractSearchServiceWrapperTool webSearchTool() {
		@SuppressWarnings("rawtypes")
		List<ISearchService> implementations = searchServicesRepoPattern.getImplementations();
		if (implementations != null) {
			for (ISearchService<?> service : implementations) {
				if (!(service instanceof AbstractWebSearchServiceImpl)) {
					continue;
				}
				try {
					if (!service.isEnabled()) {
						continue;
					}
				} catch (Throwable th) {
					LOGGER.warn("Cannot tell whether web search provider {} is enabled, skipping it", service.getId(),
							th);
					continue;
				}
				if (service instanceof INativeSearchService nativeService) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Web search tool served by the provider:" + service.getId()
								+ " with its native query:" + nativeService.getNativeSearchDataStructureType().getName());
					}
					return new NativeSearchServiceWrapperTool(pipeline, nativeService,
							AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_NAME, WEB_SEARCH_TOOL_DESCRIPTION);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Web search tool served by the provider:" + service.getId());
				}
				return new SearchServiceWrapperTool(pipeline, service, AbstractWebSearchServiceImpl.WEB_SEARCH_TOOL_NAME,
						WEB_SEARCH_TOOL_DESCRIPTION);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("No web search provider enabled, no web search tool exposed");
		}
		return null;
	}
}
