package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;

public interface IDeepSearchDataSourcesCatalogsService {
	public List<String> findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String code);

	public List<DeepSearchDataSourceMetaInfos> getActiveDeepSearchDataSourceMetaInfos();
}
