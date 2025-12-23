package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.TypedInputStream;
import ai.gebo.architecture.utils.MimeTypes;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceStandardState;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;

public class DeepSearchDataSourceServiceWrapper<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType>
		extends GAbstractDeepSearchDataSourceService<CustomSearchResultExtractionDataType> {
	protected final ISearchService<CustomSearchResultExtractionDataType> searchService;
	protected final int maxSearchesReturnedPerSystem;
	protected final IGDocumentReferenceFactory documentReferenceFactory;
	protected final IGDocumentReferenceIngestionHandler ingestionHandler;
	protected final DeepSearchDefaultConfig deepSearchDefaultConfig;
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchDataSourceServiceWrapper.class);

	public DeepSearchDataSourceServiceWrapper(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			Class<CustomSearchResultExtractionDataType> customContentExtractionType,
			ISearchService<CustomSearchResultExtractionDataType> searchService,
			IGDocumentReferenceFactory documentReferenceFactory, IGDocumentReferenceIngestionHandler ingestionHandler,
			DeepSearchDefaultConfig deepSearchDefaultConfig) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao, customContentExtractionType);
		this.searchService = searchService;
		this.maxSearchesReturnedPerSystem = deepSearchDefaultConfig.getMaxExternalSourcesSearchResults();
		this.documentReferenceFactory = documentReferenceFactory;
		this.ingestionHandler = ingestionHandler;
		this.deepSearchDefaultConfig = deepSearchDefaultConfig;
	}

	@Override
	public DataSourceExecutionTime getExecutionTime() {

		return DataSourceExecutionTime.RUNS_AFTER_DOCUMENTS_SEARCH;
	}

	@Override
	public String getHandlerId() {

		return searchService.getId();
	}

	@Override
	public boolean isEnabled(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request) {
		if (searchService.isEnabled()) {
			List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
			return systems != null && !systems.isEmpty();
		}
		return false;
	}

	@Override
	public String getDescription(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request) {

		return searchService.getDescription();
	}

	@Override
	protected CustomSearchResultExtractionDataType customStructureConsolidation(
			CustomSearchResultExtractionDataType actualData,
			CustomSearchResultExtractionDataType currentConsolidation) {

		return searchService.aggregate(actualData, currentConsolidation);
	}

	@Override
	protected List<SearchResult> extractAdditionalReferencesToScan(CustomSearchResultExtractionDataType returned,
			DeepSearchDataSourceStandardState state) {

		return List.of();
	}

	@Override
	protected List<ConsolidationInput> loadDocumentFragments(SearchResult actualSearchResultToLoad,
			DeepSearchRequest request, int maxTokens)
			throws IOException, GeboIngestionException, GeboContentHandlerSystemException {
		if (actualSearchResultToLoad.getResultReference() == null) {
			LOGGER.warn("Search result=>" + actualSearchResultToLoad.toString()
					+ " does not have a resultReference and cannot be read");
			return List.of();
		} else {
			boolean handledExtension = actualSearchResultToLoad.getResultReference().getExtension() != null
					&& ingestionHandler
							.isHandledExtension(actualSearchResultToLoad.getResultReference().getExtension());
			boolean handledContentType = actualSearchResultToLoad.getResultReference().getContentType() != null
					&& ingestionHandler
							.isHandledContentType(actualSearchResultToLoad.getResultReference().getExtension());
			if (handledExtension || handledContentType) {
				InputStream is = null;
				try {
					TypedInputStream tInputStream = searchService.loadSearchResult(actualSearchResultToLoad);
					is = tInputStream.getInputStream();
					String contentType = tInputStream.getContentType();
					if (contentType == null) {
						contentType = actualSearchResultToLoad.getResultReference().getContentType();
					}
					String extension = contentType != null ? "." + MimeTypes.getDefaultExt(contentType)
							: actualSearchResultToLoad.getResultReference().getExtension();
					GDocumentReference documentReference = documentReferenceFactory.createReference(
							actualSearchResultToLoad.getResultReference().getUri(),
							actualSearchResultToLoad.getResultReference().getName(), contentType, extension,
							actualSearchResultToLoad.getResultReference().getSize());
					IngestionHandlerData ingested = ingestionHandler.handleContent(documentReference, is);
					if (ingested.isUnmanagedContent())
						return List.of();
					else {
						return ingested.getStream().map(x -> {

							ConsolidationInput data = new ConsolidationInput(
									actualSearchResultToLoad.getResultReference().getName(),
									actualSearchResultToLoad.getResultReference().getUri(),
									actualSearchResultToLoad.getResultReference().getTitle(), x.getText());

							return data;
						}).toList();
					}
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (Throwable th) {
						}
					}
				}
			} else
				return List.of();

		}

	}

	@Override
	protected List<SearchResult> executeSearch(SearchQuery query, DeepSearchRequest request) throws IOException {
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
		for (SearchableSystemMetaData searchableSystemMetaData : systems) {
			List<SearchResult> searches = searchService.search(query, searchableSystemMetaData,
					maxSearchesReturnedPerSystem);
			searches.forEach(x -> {
				x.setSystemHandlerId(searchService.getId());
				x.setSystemConfigurationCode(searchableSystemMetaData.getCode());
			});
			results.addAll(searches);
		}
		return results;
	}

	@Override
	protected String createExtractSearchQueriesPrompt(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel) {
		String originalPrompt = deepSearchDefaultConfig.getSearchQueryExtractionPrompt();

		return deepSearchDefaultConfig.getSearchQueryExtractionPrompt();
	}

}
