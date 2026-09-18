package ai.gebo.llms.agent.standardtools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StandardSearchesToolsImpl implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(StandardSearchesToolsImpl.class);
	private static final String STANDARD_SEARCH_TOOLS = "Standard search tools";
	public static final String STANDARD_SEARCHES_TOOLS_SOURCE = "standard-searches-tools-source";
	private final ISearchServiceRepositoryPattern searchServicesRepoPattern;
	private final IGRuntimeBinder runtimeBinder;
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
		List<ToolReference> references = searchServicesRepoPattern.getImplementations().stream().filter(x -> {
			try {
				return x.isEnabled();
			} catch (SearchServiceException e) {
				LOGGER.warn("Cannot tell whether search service {} is enabled, excluding it from the tool source",
						x != null ? x.getId() : null, e);
				return false;
			}
		}).map(searchService -> {
			ToolReference reference = null;
			if (searchService instanceof INativeSearchService nativeSearchService) {
				reference = createNativeSearchToolReference(nativeSearchService);
			} else {
				reference = createSearchToolReference(searchService);
			}
			return reference;

		}).toList();
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
		List<ToolCallback> callbacks = searchServicesRepoPattern.getImplementations().stream().filter(x -> {
			try {
				return x.isEnabled();
			} catch (SearchServiceException e) {
				LOGGER.warn("Cannot tell whether search service {} is enabled, excluding it from the tool source",
						x != null ? x.getId() : null, e);
				return false;
			}
		}).map(searchService -> {
			ToolCallback callBack = null;
			if (searchService instanceof INativeSearchService nativeSearchService) {
				callBack = createNativeSearchTool(nativeSearchService);
			} else {
				callBack = createSearchTool(searchService);
			}
			return callBack;

		}).toList();
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

	private ToolCallback createSearchTool(ISearchService searchService) {

		return new SearchServiceWrapperTool(getChunkingService(), searchService).toTool();
	}

	private IDocumentsChunkService getChunkingService() {
		IDocumentsChunkService chunkingService = runtimeBinder.getImplementationOf(IDocumentsChunkService.class);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Lazily resolved the documents chunk service to "
					+ (chunkingService != null ? chunkingService.getClass().getName() : null));
		}
		return chunkingService;
	}

	private ToolCallback createNativeSearchTool(INativeSearchService nativeSearchService) {

		return new NativeSearchServiceWrapperTool(getChunkingService(), nativeSearchService).toTool();
	}

	private ToolReference createSearchToolReference(ISearchService searchService) {
		return new SearchServiceWrapperTool(getChunkingService(), searchService).toToolReference();
	}

	private ToolReference createNativeSearchToolReference(INativeSearchService nativeSearchService) {
		return new NativeSearchServiceWrapperTool(getChunkingService(), nativeSearchService).toToolReference();
	}
}
