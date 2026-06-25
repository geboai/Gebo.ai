package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.service.ISearchCataloguesCacheService;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;

/**
 * Deep-search view over the searchable systems' catalogue caches. It is the
 * search-domain catalogue cache ({@link ISearchCataloguesCacheService}) enriched
 * with the deep-search specific aggregation of active data sources.
 */
public interface IDataSourcesCatalogsService extends ISearchCataloguesCacheService {
	public List<CatalogueSample> findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String code);

	public List<DeepSearchDataSourceMetaInfos> getActiveDeepSearchDataSourceMetaInfos();
}
