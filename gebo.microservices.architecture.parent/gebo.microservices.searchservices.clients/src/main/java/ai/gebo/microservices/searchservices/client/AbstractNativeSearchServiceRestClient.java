/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.searchservices.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.search.controller.CustomTemplateParamsRequestBody;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;

/**
 * Native-search variant of {@link AbstractSearchServiceRestClient}. Adds the
 * {@link INativeSearchService} surface: {@code nativeSearch} is remoted;
 * {@link #getNativeSearchDataStructureType()} is answered locally with the
 * connector's shared native-filter {@code Class}.
 *
 * @param <C> the connector's extraction data type
 * @param <N> the connector's native query/filter type
 */
public abstract class AbstractNativeSearchServiceRestClient<C extends BaseSearchResultsExtractionDataType, N extends INativeQueryObject>
		extends AbstractSearchServiceRestClient<C> implements INativeSearchService<C, N> {

	private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() {
	};

	protected final Class<N> nativeType;

	protected AbstractNativeSearchServiceRestClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			String microserviceId, String basePath, Class<C> extractionType, Class<N> nativeType,
			GeboTtlCache metadataCache) {
		super(webClient, urlResolver, tokenPropagator, documentContentStreamer, microserviceId, basePath,
				extractionType, metadataCache);
		this.nativeType = nativeType;
	}

	@Override
	public Class<N> getNativeSearchDataStructureType() {
		return nativeType;
	}

	@Override
	public List<SearchResult> nativeSearch(N query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		String systemId = system != null ? system.getCode() : null;
		return call("nativeSearch",
				() -> webClient.post()
						.uri(uri("nativeSearch", Map.of("systemId", String.valueOf(systemId), "nEntryLimit",
								String.valueOf(nEntryLimit))))
						.headers(this::applyCallerToken).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).bodyValue(query).retrieve().bodyToMono(SEARCH_RESULT_LIST)
						.block());
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return callUnchecked("getNativePromptTemplateUseCode",
				() -> webClient.get().uri(uri("getNativePromptTemplateUseCode")).headers(this::applyCallerToken)
						.retrieve().bodyToMono(String.class).block());
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		CustomTemplateParamsRequestBody body = new CustomTemplateParamsRequestBody();
		body.setSearchableSystemMetaData(searchableSystemMetaData);
		body.setCataloguesSample(cataloguesSample);
		return callUnchecked("createCustomTemplateParamsMap",
				() -> webClient.post().uri(uri("createCustomTemplateParamsMap")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
						.retrieve().bodyToMono(STRING_OBJECT_MAP).block());
	}
}
