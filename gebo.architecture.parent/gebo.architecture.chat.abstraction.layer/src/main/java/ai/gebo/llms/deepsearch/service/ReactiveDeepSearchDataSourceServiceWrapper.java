package ai.gebo.llms.deepsearch.service;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.service.IDataSourcesCatalogsService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceExtractedSearchQueries;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.DeepSearchDataSourceAccess;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.SearchResultsStepInfo;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;

public class ReactiveDeepSearchDataSourceServiceWrapper<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType>
		extends GAbstractReactiveDeepSearchDataSourceService<CustomSearchResultExtractionDataType> {
	protected final ISearchService<CustomSearchResultExtractionDataType> searchService;
	protected final int maxSearchesReturnedPerSystem;
	protected final IGDocumentReferenceFactory documentReferenceFactory;
	protected final IGDocumentReferenceIngestionHandler ingestionHandler;
	protected final IDataSourcesCatalogsService dataSourcesCatalogsService;
	protected final IGSecurityService securityService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveDeepSearchDataSourceServiceWrapper.class);
	protected final GeboComponentInfo serviceOriginComponent;

	public ReactiveDeepSearchDataSourceServiceWrapper(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			Class<CustomSearchResultExtractionDataType> customContentExtractionType,
			ISearchService<CustomSearchResultExtractionDataType> searchService,
			IGDocumentReferenceFactory documentReferenceFactory, IGDocumentReferenceIngestionHandler ingestionHandler,
			DeepSearchDefaultConfig deepSearchDefaultConfig, IDocumentsChunkService chunkingService,
			IGeboThreadManager threadManager, SearchResultsRankingService rankingService, IGPromptConfigDao promptsDao,
			IDataSourcesCatalogsService dataSourcesCatalogsService, IGSecurityService securityService) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao, chunkingService, customContentExtractionType,
				threadManager, rankingService, deepSearchDefaultConfig, promptsDao);
		this.searchService = searchService;
		this.maxSearchesReturnedPerSystem = deepSearchDefaultConfig.getMaxExternalSourcesSearchResults();
		this.documentReferenceFactory = documentReferenceFactory;
		this.ingestionHandler = ingestionHandler;
		this.serviceOriginComponent = new GeboComponentInfo(searchService.getMessagingModuleId(),
				searchService.getMessagingSystemId());
		this.dataSourcesCatalogsService = dataSourcesCatalogsService;
		this.securityService = securityService;
	}

	@Override
	public String getHandlerId() {

		return searchService.getId();
	}

	protected DeepSearchDataSourceExtractedSearchQueries extractSearchQueries(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String consolidatedText) throws LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extractSearchQueries(...) handler:" + getHandlerId());
		}
		GPromptTemplateConfig prompt = createExtractSearchQueriesPrompt(request, minimalChatContext,
				pastSystemsResponses, deepSearchConfig, serviceModel);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Extracting queries with prompt:" + prompt);
		}
		Map<String, Object> additionalVariables = new HashMap<String, Object>();
		additionalVariables.putAll(CommonChatPromptParamsUtil.preparePromptParameters(minimalChatContext));
		// With latest specialized prompt for each data source the following is not
		// needed
		// additionalVariables.put(DATA_SOURCE_DESCRIPTION, getDescription(chatModel,
		// deepSearchConfig, request));
		DeepSearchDataSourceExtractedSearchQueries searches = super.callLLMWithConsolidationStructuredReturn(
				serviceModel, prompt, minimalChatContext.createChatRequestContext(),
				consolidatedText != null ? consolidatedText : "", additionalVariables,
				DeepSearchDataSourceExtractedSearchQueries.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End extractSearchQueries(...) handler:" + getHandlerId() + " returning " + searches);
		}
		return searches;
	}

	@Override
	public boolean isEnabled(DeepSearchConfig deepSearchConfig) throws SearchServiceException {
		boolean userCanAccess = securityService.isCanAccess(deepSearchConfig, true);
		if (userCanAccess) {
			List<DeepSearchDataSourceAccess> accesses = deepSearchConfig.getDataSourcesAccesses();
			boolean perDataSourceConfigured = deepSearchConfig.getPerDataSourceConfigured() != null
					&& deepSearchConfig.getPerDataSourceConfigured();
			if (accesses != null && !accesses.isEmpty() && !securityService.isCurrentUserAdmin()
					&& perDataSourceConfigured) {
				String thisSystemId = getHandlerId();
				DeepSearchDataSourceAccess gridCell = accesses.stream()
						.filter(x -> x.getDataSourceId() != null && x.getDataSourceId().equals(thisSystemId))
						.findFirst().orElse(null);
				userCanAccess = gridCell != null && securityService.isCanAccess(gridCell, true);
			}
		}
		if (searchService.isEnabled() && userCanAccess) {
			List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
			return systems != null && !systems.isEmpty();
		}
		return false;
	}

	@Override
	public String getDescription(DeepSearchConfig deepSearchConfig) {

		return searchService.getDescription();
	}

	@Override
	protected CustomSearchResultExtractionDataType customStructureConsolidation(
			CustomSearchResultExtractionDataType actualData,
			CustomSearchResultExtractionDataType currentConsolidation) {

		return searchService.aggregate(actualData, currentConsolidation);
	}

	protected List<SearchResult> executeStandardQuerySearch(SearchQuery query, DeepSearchRequest request,
			MinimalChatContext minimalChatContext, int topK) throws IOException, SearchServiceException {
		List<SearchResult> results = new ArrayList<SearchResult>();
		List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
		for (SearchableSystemMetaData searchableSystemMetaData : systems) {
			List<SearchResult> searches = searchService.search(query, searchableSystemMetaData, topK);
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

	protected GPromptTemplateConfig createExtractSearchQueriesPrompt(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel) {
		String useCode = searchService.getQueriesGenerationPromptUseCode();
		if (useCode == null)
			useCode = GeboPromptsLibrary.DEEP_SEARCH_SEARCH_QUERY_EXTRACTION_PROMPT;
		GPromptTemplateConfig prompt = promptsDao.findByPromptUse(useCode);

		return prompt;
	}

	@Override
	protected SearchResultAnalisysOutcome extractRelatedAnalisysReferences(SearchResultsStepInfo actualSearchResultRef,
			CustomSearchResultExtractionDataType returned, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel) {
		SearchResultAnalisysOutcome outcome = null;
		try {
			outcome = searchService.extractRelatedAnalisysReferences(
					actualSearchResultRef.getActualSearchResult().getOriginComponent().getCompleteComponentId() + "<-->"
							+ actualSearchResultRef.getActualSearchResult().getSystemConfigurationCode(),
					returned);
		} catch (Throwable th) {
			LOGGER.error("Exception while trying to execute an additional search or data retrieve", th);
		}
		return outcome;
	}

	protected <T extends INativeQueryObject> List<SearchWithResults> executeNativeSearch(
			INativeSearchService<CustomSearchResultExtractionDataType, T> nativeSearchService,
			DeepSearchRequest request, MinimalChatContext minimalChatContext,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, int topK)
			throws LLMConfigException, IOException, SearchServiceException {
		List<SearchWithResults> queryResults = new ArrayList<SearchWithResults>();
		Class<T> nativeSearchServiceDataType = nativeSearchService.getNativeSearchDataStructureType();
		String promptTemplateCode = nativeSearchService.getNativePromptTemplateUseCode();
		GPromptTemplateConfig prompt = this.promptsDao.findByPromptUse(promptTemplateCode);
		Map<String, Object> promptParams = CommonChatPromptParamsUtil.preparePromptParameters(minimalChatContext);
		List<SearchableSystemMetaData> searchables = this.searchService.getSearchableSystems();
		List<SearchWithResults> allResults = new ArrayList<SearchWithResults>();
		for (SearchableSystemMetaData searchableSystemMetaData : searchables) {
			Map<String, Object> systemTemplateCallParams = new HashMap<String, Object>();
			String messagingModuleId = nativeSearchService.getMessagingModuleId();
			String messageSystemId = nativeSearchService.getMessagingSystemId();
			String systemCode = null;
			List<CatalogueSample> cataloguesSample = List.of();
			if (searchableSystemMetaData.getSystemConfigurationReference() != null) {
				systemCode = searchableSystemMetaData.getCode();
				cataloguesSample = dataSourcesCatalogsService
						.findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
								messagingModuleId, messageSystemId, systemCode);
			}
			Map<String, Object> specificSystemParams = nativeSearchService
					.createCustomTemplateParamsMap(searchableSystemMetaData, cataloguesSample);
			systemTemplateCallParams.putAll(promptParams);
			systemTemplateCallParams.putAll(specificSystemParams);
			T resultingQueryObject = this.callLLMStructuredReturn(serviceModel, prompt,
					minimalChatContext.createChatRequestContext(), systemTemplateCallParams,
					nativeSearchServiceDataType);

			List<SearchResult> data = nativeSearchService.nativeSearch(resultingQueryObject, searchableSystemMetaData,
					topK);
			SearchWithResults swr = new SearchWithResults();
			swr.setResults(flattenSearchResults(data));
			swr.setNativeQueryObject(resultingQueryObject);
			allResults.add(swr);
		}

		return cleanAndRemoveDuplicated(allResults);
	}

	protected List<SearchWithResults> executeStandardQuerySearch(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String consolidated, int topK) throws LLMConfigException {
		List<SearchWithResults> queryResults = new ArrayList<SearchWithResults>();
		DeepSearchDataSourceExtractedSearchQueries searchQueries = extractSearchQueries(request, minimalChatContext,
				pastSystemsResponses, deepSearchConfig, chatModel, serviceModel, "");

		for (SearchQuery query : searchQueries.getSearchQuery()) {
			try {
				List<SearchResult> _results = executeStandardQuerySearch(query, request, minimalChatContext, topK);
				if (_results.isEmpty())
					continue;
				SearchWithResults sr = new SearchWithResults();
				sr.setResults(flattenSearchResults(_results));
				sr.setSearchQuery(query);
				queryResults.add(sr);
			} catch (Throwable th) {
				LOGGER.error("Error executing search", th);
			}
		}

		return cleanAndRemoveDuplicated(queryResults);
	}

	@Override
	protected List<SearchWithResults> executeSearches(DeepSearchRequest request, MinimalChatContext minimalChatContext,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String consolidated, int topK)
			throws LLMConfigException, IOException, SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin executeSearches(...) for " + getHandlerId());
		}
		List<SearchWithResults> results = List.of();
		if (this.searchService instanceof INativeSearchService nativeSearchService) {
			try {
				results = executeNativeSearch(nativeSearchService, request, minimalChatContext, pastSystemsResponses,
						deepSearchConfig, chatModel, serviceModel, topK);
			} catch (Throwable th) {
				LOGGER.error("Error in nativeSearch,try fall back to standard search", th);
				results = executeStandardQuerySearch(request, minimalChatContext, pastSystemsResponses,
						deepSearchConfig, chatModel, serviceModel, consolidated, topK);
			}
		} else {

			results = executeStandardQuerySearch(request, minimalChatContext, pastSystemsResponses, deepSearchConfig,
					chatModel, serviceModel, consolidated, topK);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End executeSearches(...) for " + getHandlerId());
		}

		return results;
	}

	private List<SearchWithResults> excludeFolders(List<SearchWithResults> results) {
		return null;
	}

	@Override
	public String getProductId() {
		return searchService.getProductId();
	}

}
