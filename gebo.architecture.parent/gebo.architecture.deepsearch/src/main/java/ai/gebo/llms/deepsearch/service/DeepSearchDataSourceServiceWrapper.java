package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.SearchResultsStepInfo;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;

public class DeepSearchDataSourceServiceWrapper<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType>
		extends GAbstractDeepSearchDataSourceService<CustomSearchResultExtractionDataType> {
	protected final ISearchService<CustomSearchResultExtractionDataType> searchService;
	protected final int maxSearchesReturnedPerSystem;
	protected final IGDocumentReferenceFactory documentReferenceFactory;
	protected final IGDocumentReferenceIngestionHandler ingestionHandler;
	protected final DeepSearchDefaultConfig deepSearchDefaultConfig;
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchDataSourceServiceWrapper.class);
	protected final GeboComponentInfo serviceOriginComponent;

	public DeepSearchDataSourceServiceWrapper(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			Class<CustomSearchResultExtractionDataType> customContentExtractionType,
			ISearchService<CustomSearchResultExtractionDataType> searchService,
			IGDocumentReferenceFactory documentReferenceFactory, IGDocumentReferenceIngestionHandler ingestionHandler,
			DeepSearchDefaultConfig deepSearchDefaultConfig, IDocumentsChunkService chunkingService, IGeboThreadManager threadManager) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao, chunkingService, customContentExtractionType, threadManager);
		this.searchService = searchService;
		this.maxSearchesReturnedPerSystem = deepSearchDefaultConfig.getMaxExternalSourcesSearchResults();
		this.documentReferenceFactory = documentReferenceFactory;
		this.ingestionHandler = ingestionHandler;
		this.deepSearchDefaultConfig = deepSearchDefaultConfig;
		this.serviceOriginComponent = new GeboComponentInfo(searchService.getMessagingModuleId(),
				searchService.getMessagingSystemId());
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
			DeepSearchRequest request) throws SearchServiceException {
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
	protected List<SearchResult> extractAdditionalReferencesToScan(CustomSearchResultExtractionDataType returned) {

		return List.of();
	}

	

	@Override
	protected List<SearchResult> executeSearch(SearchQuery query, DeepSearchRequest request)
			throws IOException, SearchServiceException {
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
		for (SearchableSystemMetaData searchableSystemMetaData : systems) {
			List<SearchResult> searches = searchService.search(query, searchableSystemMetaData,
					maxSearchesReturnedPerSystem);
			assign(searches, serviceOriginComponent, searchableSystemMetaData.getCode());
			results.addAll(searches);
		}

		return results;
	}

	private void assign(List<SearchResult> searches, GeboComponentInfo origin, String configCode) {
		searches.forEach(x -> {
			x.setOriginComponent(origin);
			x.setSystemConfigurationCode(configCode);
			assign(x.getChilds(), origin, configCode);
		});
	}

	@Override
	protected String createExtractSearchQueriesPrompt(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel) {

		String standardPrompt = deepSearchDefaultConfig.getSearchQueryExtractionPrompt();
		String wrappedServicePrompt = searchService.getQueriesExtractionPrompt();

		return wrappedServicePrompt != null && wrappedServicePrompt.trim().length() > 0 ? wrappedServicePrompt
				: standardPrompt;
	}

	

	@Override
	protected SearchResultAnalisysOutcome extractRelatedAnalisysReferences(SearchResultsStepInfo actualSearchResultRef,
			CustomSearchResultExtractionDataType returned, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel) {
		return searchService
				.extractRelatedAnalisysReferences(
						actualSearchResultRef.getActualSearchResult().getOriginComponent().getCompleteComponentId()
								+ "<-->" + actualSearchResultRef.getActualSearchResult().getSystemConfigurationCode(),
						returned);
	}

}
