/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.security.services.IGSecurityService;

/**
 * The deep search tools, one per searcher:
 * {@value KnowledgeBaseDeepSearchTool#DEEP_SEARCH_KNOWLEDGE_BASE_TOOL} for the
 * internal knowledge base, {@value #DEEP_SEARCH_WEB_TOOL} for the web (served by the
 * first enabled web search provider, whatever their number) and
 * {@code deepSearch<Product>} for every other enabled search service, as the search
 * tools are. They let an agent operating its own tools, such as the single agent
 * working in a loop, search widely and get an analysis of all the documents found.
 * <p>
 * Whether the current user may search a service is checked when a tool is called,
 * as the search tools do. Kept out of the default network's automatic tool
 * mounting: there the deep searches are the deep search pipelines' job.
 */
@ConditionalOnProperty(prefix = "ai.gebo.agents.standard.deep-search-tools", name = "enabled", havingValue = "true", matchIfMissing = true)
@Service
public class DeepSearchToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchToolSource.class);
	/** The id of this tool source, kept out of the default network's automatic mounting. */
	public static final String DEEP_SEARCH_TOOL_SOURCE = "standard-deep-search-tool-source";
	public static final String DEEP_SEARCH_WEB_TOOL = "deepSearchWeb";
	static final String DEEP_SEARCH_TOOL_PREFIX = "deepSearch";
	private static final int MAX_TOOL_NAME_LENGTH = 64;
	private final static ToolsCategory category = new ToolsCategory();
	static {
		category.setCode(DEEP_SEARCH_TOOL_SOURCE);
		category.setDescription("Deep search tools");
	}
	private final DeepSearchToolsSupport support;
	private final ISearchServiceRepositoryPattern searchServicesRepoPattern;
	// resolved on use: the knowledge base search reaches back to the chat models
	private final ObjectProvider<IGDocumentsSearchService> documentsSearchService;
	private final IGSecurityService securityService;

	public DeepSearchToolSource(DeepSearchToolsSupport support, ISearchServiceRepositoryPattern searchServicesRepoPattern,
			ObjectProvider<IGDocumentsSearchService> documentsSearchService, IGSecurityService securityService) {
		this.support = support;
		this.searchServicesRepoPattern = searchServicesRepoPattern;
		this.documentsSearchService = documentsSearchService;
		this.securityService = securityService;
	}

	@Override
	public String getId() {
		return DEEP_SEARCH_TOOL_SOURCE;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return category;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		return tools().stream().map(tool -> tool.toToolReference()).toList();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getToolCallbacks() for tool source:" + getId());
		}
		List<ToolCallback> callbacks = tools().stream().map(tool -> tool.toTool()).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getToolCallbacks() tool source:" + getId() + " exposes " + callbacks.size()
					+ " deep search tool(s)");
		}
		return callbacks;
	}

	/**
	 * A deep search reaches its source (the knowledge bases' stores, or its search
	 * service) and has the internal services model analyse what it found.
	 */
	@Override
	public List<ToolDataFlowTarget> getDataFlowTargets(String toolName) {
		for (AbstractDeepSearchTool<?> tool : tools()) {
			if (!tool.getToolName().equals(toolName)) {
				continue;
			}
			final List<ToolDataFlowTarget> targets = new ArrayList<>();
			if (tool instanceof SearchServiceDeepSearchTool<?> serviceTool) {
				targets.add(ToolDataFlowTarget.searchService(serviceTool.getService().getId(),
						"Deep search: queries sent to " + serviceTool.getService().getId()));
			} else {
				targets.addAll(InternalKnowledgeBaseSearchToolSource.knowledgeBaseSearchTargets("Deep search"));
			}
			targets.add(ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.SERVICE_MODEL,
					"Deep search: analysis of the documents found"));
			return targets;
		}
		return List.of();
	}

	/**
	 * The internal knowledge base tool, then one tool per enabled search service: a
	 * single one for the web.
	 */
	@SuppressWarnings("rawtypes")
	List<AbstractDeepSearchTool<?>> tools() {
		final List<AbstractDeepSearchTool<?>> tools = new ArrayList<>();
		final IGDocumentsSearchService documentsSearch = documentsSearchService.getIfAvailable();
		if (documentsSearch != null) {
			tools.add(new KnowledgeBaseDeepSearchTool(support, documentsSearch, securityService));
		}
		final Set<String> names = new HashSet<>();
		names.add(KnowledgeBaseDeepSearchTool.DEEP_SEARCH_KNOWLEDGE_BASE_TOOL);
		final List<ISearchService> implementations = searchServicesRepoPattern.getImplementations();
		if (implementations == null) {
			return tools;
		}
		for (ISearchService<?> service : implementations) {
			if (service == null) {
				continue;
			}
			try {
				if (!service.isEnabled()) {
					continue;
				}
			} catch (Throwable th) {
				LOGGER.warn("Cannot tell whether search service {} is enabled, no deep search tool for it",
						service.getId(), th);
				continue;
			}
			final boolean web = service instanceof AbstractWebSearchServiceImpl;
			final String toolName = web ? DEEP_SEARCH_WEB_TOOL : toolName(service.getProductId(), service.getId());
			if (!names.add(toolName)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Search service:" + service.getId() + " not exposed, tool:" + toolName
							+ " already served by another service");
				}
				continue;
			}
			tools.add(SearchServiceDeepSearchTool.of(support, service, toolName, web ? "the web" : describe(service)));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deep search tool:" + toolName + " served by search service:" + service.getId());
			}
		}
		return tools;
	}

	private static String describe(ISearchService<?> service) {
		final String product = service.getProductId();
		if (service.getDescription() == null || service.getDescription().isBlank()) {
			return product != null ? product : service.getId();
		}
		return product != null ? service.getDescription() + " (" + product + ")" : service.getDescription();
	}

	/** {@code deepSearch<Product>}, keeping only the characters a tool name allows. */
	static String toolName(String productId, String serviceId) {
		final String source = productId != null && !productId.isBlank() ? productId : serviceId;
		final StringBuilder name = new StringBuilder(DEEP_SEARCH_TOOL_PREFIX);
		boolean upper = true;
		for (char ch : source.toCharArray()) {
			if (Character.isLetterOrDigit(ch) && ch < 128) {
				name.append(upper ? Character.toUpperCase(ch) : ch);
				upper = false;
			} else {
				upper = true;
			}
		}
		return name.length() > MAX_TOOL_NAME_LENGTH ? name.substring(0, MAX_TOOL_NAME_LENGTH) : name.toString();
	}
}
