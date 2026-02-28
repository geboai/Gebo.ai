package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;

public interface IDataSourcesCatalogsService {
	public List<CatalogueSample> findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String code);

	public List<DeepSearchDataSourceMetaInfos> getActiveDeepSearchDataSourceMetaInfos();
}
