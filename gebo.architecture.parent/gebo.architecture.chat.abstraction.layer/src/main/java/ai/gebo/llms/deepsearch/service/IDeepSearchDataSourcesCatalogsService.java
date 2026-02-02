package ai.gebo.llms.deepsearch.service;

import java.util.List;

public interface IDeepSearchDataSourcesCatalogsService {
	public List<String> findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String code);
}
