/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;

/**
 * Base REST controller for an {@link INativeSearchService}. Extends
 * {@link BaseSearchController} (so the whole {@code ISearchService} surface is
 * already re-exposed) and adds {@code protected} delegation for the native-search
 * additions, letting a concrete {@code @RestController} subclass map them for
 * USERS/ADMIN. Carries no web annotations of its own.
 *
 * @param <CustomSearchResultExtractionDataType> the connector's extraction data type
 * @param <NativeSearchDataStructure>            the connector's native query/filter type
 */
public abstract class BaseNativeSearchController<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType, NativeSearchDataStructure extends INativeQueryObject>
		extends BaseSearchController<CustomSearchResultExtractionDataType> {

	protected final INativeSearchService<CustomSearchResultExtractionDataType, NativeSearchDataStructure> nativeSearchService;

	protected BaseNativeSearchController(
			INativeSearchService<CustomSearchResultExtractionDataType, NativeSearchDataStructure> nativeSearchService) {
		super(nativeSearchService);
		this.nativeSearchService = nativeSearchService;
	}

	protected List<SearchResult> nativeSearch(NativeSearchDataStructure query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		return nativeSearchService.nativeSearch(query, system, nEntryLimit);
	}

	protected Class<NativeSearchDataStructure> getNativeSearchDataStructureType() {
		return nativeSearchService.getNativeSearchDataStructureType();
	}

	protected String getNativePromptTemplateUseCode() {
		return nativeSearchService.getNativePromptTemplateUseCode();
	}

	protected Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		return nativeSearchService.createCustomTemplateParamsMap(searchableSystemMetaData, cataloguesSample);
	}
}
