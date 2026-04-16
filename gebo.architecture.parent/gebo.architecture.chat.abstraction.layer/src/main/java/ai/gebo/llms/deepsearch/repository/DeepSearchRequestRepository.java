package ai.gebo.llms.deepsearch.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;

public interface DeepSearchRequestRepository extends IGBaseMongoDBRepository<DeepSearchRequest> {
	public Page<DeepSearchRequest> findByUsername(String username, Pageable pageable);

	public List<DeepSearchRequest> findByUsername(String username);

	public List<DeepSearchRequest> findByUserChatContextCode(String userChatContextCode);

	@Override
	default Class<DeepSearchRequest> getManagedType() {
		return DeepSearchRequest.class;
	}
}
