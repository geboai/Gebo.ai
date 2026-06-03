package ai.gebo.llms.chat.agent.standardtools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.llms.chat.agent.standardtools.model.NativeSearchParam;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample.SearchResultSampleList;
import lombok.AllArgsConstructor;
import lombok.Setter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;

@AllArgsConstructor
public class NativeSearchServiceWrapperTool extends AbstractSearchServiceWrapperTool {
	private final INativeSearchService wrapped;

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
			final String functionName = this.wrapped.getProductId() + "NativeSearch";
			final String description = this.wrapped.getProductId() + " searching function";
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
		final String functionName = this.wrapped.getProductId() + "NativeSearch";
		final String description = this.wrapped.getProductId() + " searching function";
		ToolReference toolReference = new ToolReference(toTool());
		return toolReference;
	}

	@Setter
	public static class WrappedNativeSearcher<NativeObjectType extends INativeQueryObject> {
		private INativeSearchService<?, NativeObjectType> wrapped;
		private NativeSearchServiceWrapperTool wrappedTool;
		private String toolName;
		private String toolDescription;
		private Class<NativeSearchParam<NativeObjectType>> paramType;

		public SearchResultSampleList search(NativeSearchParam<NativeObjectType> param) {

			List<SearchResult> results = new ArrayList<>();

			try {
				List<SearchableSystemMetaData> systems = wrapped.getSearchableSystems();
				for (SearchableSystemMetaData searchableSystemMetaData : systems) {
					List<SearchResult> found;
					found = wrapped.nativeSearch(param.getQuery(), searchableSystemMetaData, param.getTopK());
					results.addAll(found);
				}
			} catch (IOException | SearchServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
