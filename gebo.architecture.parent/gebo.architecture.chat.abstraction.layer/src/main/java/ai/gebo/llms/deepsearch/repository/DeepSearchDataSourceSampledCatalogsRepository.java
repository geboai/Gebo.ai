package ai.gebo.llms.deepsearch.repository;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceSampledCatalogs;

public interface DeepSearchDataSourceSampledCatalogsRepository
		extends IGBaseMongoDBRepository<DeepSearchDataSourceSampledCatalogs> {
	@Override
	default Class<DeepSearchDataSourceSampledCatalogs> getManagedType() {
		return DeepSearchDataSourceSampledCatalogs.class;
	}

	public List<DeepSearchDataSourceSampledCatalogs> findByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String systemConfigurationCode);

	public List<DeepSearchDataSourceSampledCatalogs> findByHandlerId(String HandlerId);
	public List<DeepSearchDataSourceSampledCatalogs> findByHandlerIdIn(List<String> HandlerId);

}
