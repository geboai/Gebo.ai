package ai.gebo.llms.agent.standardtools;

import java.util.List;

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

		return searchServicesRepoPattern.getImplementations().stream().filter(x -> {
			try {
				return x.isEnabled();
			} catch (SearchServiceException e) {
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
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {

		return searchServicesRepoPattern.getImplementations().stream().filter(x -> {
			try {
				return x.isEnabled();
			} catch (SearchServiceException e) {
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
	}

	private ToolCallback createSearchTool(ISearchService searchService) {

		return new SearchServiceWrapperTool(getChunkingService(), searchService).toTool();
	}

	private IDocumentsChunkService getChunkingService() {

		return runtimeBinder.getImplementationOf(IDocumentsChunkService.class);
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
