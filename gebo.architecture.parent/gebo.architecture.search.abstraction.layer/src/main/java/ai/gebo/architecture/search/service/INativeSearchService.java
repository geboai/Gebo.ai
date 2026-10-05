package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;

public interface INativeSearchService<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType, NativeSearchDataStructure extends INativeQueryObject>
		extends ISearchService<CustomSearchResultExtractionDataType> {
	public List<SearchResult> nativeSearch(NativeSearchDataStructure query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException;

	/**
	 * Searches natively applying the call parameters (timeouts, retries) in the
	 * service's own client software where it can, only for this search: by default a
	 * search without them.
	 */
	public default List<SearchResult> nativeSearch(NativeSearchDataStructure query, SearchableSystemMetaData system,
			int nEntryLimit, SearchCallParameters parameters) throws IOException, SearchServiceException {
		return nativeSearch(query, system, nEntryLimit);
	}

	public Class<NativeSearchDataStructure> getNativeSearchDataStructureType();

	public String getNativePromptTemplateUseCode();

	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample);

}
