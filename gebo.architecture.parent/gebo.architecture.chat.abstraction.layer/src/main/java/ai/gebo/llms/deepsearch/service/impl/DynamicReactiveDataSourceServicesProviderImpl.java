package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.llms.deepsearch.service.ReactiveDeepSearchDataSourceServiceWrapper;
import ai.gebo.llms.deepsearch.service.SearchResultsRankingService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import lombok.AllArgsConstructor;

@Component
@Scope(value = "singleton")
@AllArgsConstructor
public class DynamicReactiveDataSourceServicesProviderImpl implements IGReactiveDynamicDataSourceServicesProvider {
	final ISearchServiceRepositoryPattern searchServicesRepository;
	final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao;
	final IGDocumentReferenceFactory documentReferenceFactory;
	final IGDocumentReferenceIngestionHandler ingestionHandler;
	final IDocumentsChunkService chunkingService;
	final DeepSearchDefaultConfig deepSearchDefaultConfig;
	final IGeboThreadManager threadManager;
	final SearchResultsRankingService rankingService;
	final IGPromptConfigDao promptsDao;
	private final static Logger LOGGER = LoggerFactory.getLogger(DynamicReactiveDataSourceServicesProviderImpl.class);

	@Override
	public List<IGReactiveDeepSearchDataSourceService> getDynamicDeepSearchServices() {
		List<ISearchService> services = searchServicesRepository.findImplementations(x -> {
			try {
				return x.isEnabled();
			} catch (Throwable e) {
				LOGGER.error("Exception calling isEnabled()", e);
				return false;
			}
		});

		List<IGReactiveDeepSearchDataSourceService> wrappers = new ArrayList<IGReactiveDeepSearchDataSourceService>();
		for (ISearchService iSearchService : services) {
			ReactiveDeepSearchDataSourceServiceWrapper wrapper;
			try {
				wrapper = new ReactiveDeepSearchDataSourceServiceWrapper(chatModelsConfigDao, embeddingModelsRuntimeDao,
						iSearchService.getCustomResultsAggregationDataType(), iSearchService, documentReferenceFactory,
						ingestionHandler, deepSearchDefaultConfig, chunkingService, threadManager, rankingService, promptsDao);
				wrappers.add(wrapper);
			} catch (Throwable e) {
				LOGGER.error("Exception in getDynamicDeepSearchServices()", e);
			}

		}
		return wrappers;
	}

}
