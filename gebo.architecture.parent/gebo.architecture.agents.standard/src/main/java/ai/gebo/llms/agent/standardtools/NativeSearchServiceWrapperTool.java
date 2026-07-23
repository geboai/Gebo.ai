package ai.gebo.llms.agent.standardtools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.llms.agent.standardtools.model.NativeSearchParam;
import ai.gebo.llms.agent.standardtools.model.SearchResultSample.SearchResultSampleList;
import lombok.Setter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;

public class NativeSearchServiceWrapperTool extends AbstractSearchServiceWrapperTool {
	private static final String NATIVE_SEARCHING_FUNCTION_DESCRIPTION = " native searching function";
	private static final String NATIVE_SEARCH = "NativeSearch";
	private final INativeSearchService wrapped;

	public NativeSearchServiceWrapperTool(IDocumentsChunkService chunkingService, INativeSearchService wrapped) {

		super(chunkingService);
		this.wrapped = wrapped;
	}

	@Override
	public ToolCallback toTool() {
		final TypeDescription.Generic generic = TypeDescription.Generic.Builder
				.parameterizedType(WrappedNativeSearcher.class, wrapped.getNativeSearchDataStructureType()).build();
		final Class<?> dynamicType = new ByteBuddy().subclass(generic).make().load(getClass().getClassLoader())
				.getLoaded();
		try {
			final WrappedNativeSearcher wrapped = (WrappedNativeSearcher) dynamicType.newInstance();
			wrapped.setWrapped(this.wrapped);
			wrapped.setWrappedTool(this);
			final String functionName = this.wrapped.getProductId() + NATIVE_SEARCH;
			final String description = this.wrapped.getProductId() + NATIVE_SEARCHING_FUNCTION_DESCRIPTION;
			final TypeDescription.Generic genericParam = TypeDescription.Generic.Builder
					.parameterizedType(NativeSearchParam.class, this.wrapped.getNativeSearchDataStructureType())
					.build();
			final Class<?> dynamicParamType = new ByteBuddy().subclass(genericParam).make()
					.load(getClass().getClassLoader()).getLoaded();
			wrapped.setToolName(functionName);
			wrapped.setToolDescription(description);
			wrapped.setParamType(dynamicParamType);
			return wrapped.createCallback();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Exception in to tool", e);
		}

	}

	@Override
	public ToolReference toToolReference() {
		final String functionName = this.wrapped.getProductId() + NATIVE_SEARCH;
		final String description = this.wrapped.getProductId() + NATIVE_SEARCHING_FUNCTION_DESCRIPTION;
		ToolReference toolReference = new ToolReference(toTool());
		return toolReference;
	}

	@Setter
	public static class WrappedNativeSearcher<NativeObjectType extends INativeQueryObject> {
		private static final Logger LOGGER = LoggerFactory.getLogger(WrappedNativeSearcher.class);
		private INativeSearchService<?, NativeObjectType> wrapped;
		private NativeSearchServiceWrapperTool wrappedTool;
		private String toolName;
		private String toolDescription;
		private Class<NativeSearchParam<NativeObjectType>> paramType;

		public SearchResultSampleList search(NativeSearchParam<NativeObjectType> param) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Begin native search tool:" + toolName + " topK:"
						+ (param != null ? param.getTopK() : null) + " textSampleTokens:"
						+ (param != null ? param.getTextSampleTokens() : null));
			}
			List<SearchResult> results = new ArrayList<>();

			try {
				List<SearchableSystemMetaData> systems = wrapped.getSearchableSystems();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Native search tool:" + toolName + " querying " + (systems != null ? systems.size() : 0)
							+ " searchable system(s)");
				}
				for (SearchableSystemMetaData searchableSystemMetaData : systems) {
					List<SearchResult> found;
					found = wrapped.nativeSearch(param.getQuery(), searchableSystemMetaData, param.getTopK());
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Native search on system:" + searchableSystemMetaData.getCode() + " returned "
								+ (found != null ? found.size() : 0) + " result(s)");
					}
					results.addAll(found);
				}
			} catch (IOException | SearchServiceException e) {
				LOGGER.error("Exception during native search for tool:" + toolName, e);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End native search tool:" + toolName + " collected " + results.size() + " result(s)");
			}
			return wrappedTool.loadSamples(results, param.getTextSampleTokens());
		}

		public ToolCallback createCallback() {
			final BiFunction<NativeSearchParam<NativeObjectType>, ToolContext, SearchResultSampleList> tool = (p,
					ctx) -> this.search(p);
			return ToolCallbackDeclarationUtil.declare(tool, toolName, toolDescription, paramType,
					SearchResultSampleList.class);
		}
	}

}
