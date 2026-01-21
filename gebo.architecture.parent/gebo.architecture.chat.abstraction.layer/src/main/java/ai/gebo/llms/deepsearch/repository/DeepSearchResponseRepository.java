package ai.gebo.llms.deepsearch.repository;

import java.util.Optional;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;

public interface DeepSearchResponseRepository extends IGBaseMongoDBRepository<DeepSearchResponse> {
	public Optional<DeepSearchResponse> findByDeepsearchCode(String deepSearchCode);
	public void deleteByDeepsearchCode(String deepSearchCode);
	@Override
	default Class<DeepSearchResponse> getManagedType() {
		
		return DeepSearchResponse.class;
	}
}
