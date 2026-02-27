package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;

public interface INativeSearchService<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType, NativeSearchDataStructure>
		extends ISearchService<CustomSearchResultExtractionDataType> {
	public List<SearchResult> nativeSearch(NativeSearchDataStructure query, SearchableSystemMetaData system,
			int nEntryLimit, List<CatalogueSample> cataloguesSample) throws IOException, SearchServiceException;

	public Class<NativeSearchDataStructure> getNativeSearchDataStructureType();

	public String getNativePromptTemplateUseCode();

	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData);

	
	
	
}
