package ai.gebo.llms.deepsearch.repository;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;

public interface DeepSearchDataSourceResponseRepository extends IGBaseMongoDBRepository<DeepSearchDataSourceResponse> {
	@Override
	default Class<DeepSearchDataSourceResponse> getManagedType() {
		return DeepSearchDataSourceResponse.class;
	}
	public void deleteByDeepsearchCode(String deepSearchCode);
	public List<DeepSearchDataSourceResponse> findByDeepsearchCode(String deepSearchCode);
}
