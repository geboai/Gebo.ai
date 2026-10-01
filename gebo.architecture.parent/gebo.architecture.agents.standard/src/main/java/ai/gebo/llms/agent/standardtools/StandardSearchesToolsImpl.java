package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import lombok.AllArgsConstructor;

/**
 * Exposes each enabled external search service (Jira, Confluence, SharePoint,
 * content systems...) as a tool answering with well formed contents, ranked
 * against the search objective given by the model (see
 * {@link SearchToolContentPipeline}). The web search services are exposed by the
 * {@link WebSearchToolSource} instead, as the single provider-neutral web search
 * tool.
 * <p>
 * These tools are kept out of the default agents network's automatic tool
 * mounting, where searching is the job of the dedicated search agents.
 */
@Service
@AllArgsConstructor
public class StandardSearchesToolsImpl implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(StandardSearchesToolsImpl.class);
	private static final String STANDARD_SEARCH_TOOLS = "Standard search tools";
	public static final String STANDARD_SEARCHES_TOOLS_SOURCE = "standard-searches-tools-source";
	private static final String SEARCH = "Search";
	private static final String NATIVE_SEARCH = "NativeSearch";
	private final ISearchServiceRepositoryPattern searchServicesRepoPattern;
	private final SearchToolContentPipeline pipeline;
	private final static ToolsCategory category = new ToolsCategory();
	static {
		category.setCode(STANDARD_SEARCHES_TOOLS_SOURCE);
		category.setDescription(STANDARD_SEARCH_TOOLS);
	}

	@Override
	public String getId() {

		return STANDARD_SEARCHES_TOOLS_SOURCE;
	}

	@Override
	public ToolsCategory getToolCategory() {

		return category;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getFullToolReferences() for tool source:" + getId());
		}
		List<ToolReference> references = wrappers().stream().map(AbstractSearchServiceWrapperTool::toToolReference)
				.toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getFullToolReferences() tool source:" + getId() + " exposes " + references.size()
					+ " tool reference(s)");
		}
		return references;
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getToolCallbacks() for tool source:" + getId());
		}
		List<ToolCallback> callbacks = wrappers().stream().map(AbstractSearchServiceWrapperTool::toTool).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getToolCallbacks() tool source:" + getId() + " exposes " + callbacks.size()
					+ " tool callback(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			for (ToolCallback callback : callbacks) {
				LOGGER.trace("Exposed tool: " + callback.getToolDefinition().name() + " - "
						+ callback.getToolDefinition().description());
			}
		}
		return callbacks;
	}

	/**
	 * One tool per enabled non-web search service: native services are searched with
	 * their own query structure, the others with plain text.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<AbstractSearchServiceWrapperTool> wrappers() {
		List<AbstractSearchServiceWrapperTool> wrappers = new ArrayList<>();
		for (ISearchService searchService : searchServicesRepoPattern.getImplementations()) {
			if (searchService == null || searchService instanceof AbstractWebSearchServiceImpl) {
				continue;
			}
			try {
				if (!searchService.isEnabled()) {
					continue;
				}
			} catch (Throwable th) {
				LOGGER.warn("Cannot tell whether search service {} is enabled, excluding it from the tool source",
						searchService.getId(), th);
				continue;
			}
			final String searched = searchService.getDescription() != null
					? searchService.getDescription() + " (" + searchService.getProductId() + ")"
					: searchService.getProductId();
			final String description = "Search " + searched
					+ " and return the contents found, ranked against your search objective.";
			if (searchService instanceof INativeSearchService nativeSearchService) {
				wrappers.add(new NativeSearchServiceWrapperTool(pipeline, nativeSearchService,
						nativeSearchService.getProductId() + NATIVE_SEARCH, description));
			} else {
				wrappers.add(new SearchServiceWrapperTool(pipeline, searchService, searchService.getProductId() + SEARCH,
						description));
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool source:" + getId() + " wraps " + wrappers.size() + " enabled search service(s)");
		}
		return wrappers;
	}
}
