package ai.gebo.llms.deepsearch.repository;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;

public interface DeepSearchDataSourceDocumentResultRepository
		extends IGBaseMongoDBRepository<DeepSearchDataSourceDocumentResult> {
	@Override
	default Class<DeepSearchDataSourceDocumentResult> getManagedType() {

		return DeepSearchDataSourceDocumentResult.class;
	}

	public void deleteByDeepsearchCode(String deepSearchCode);

	public List<DeepSearchDataSourceDocumentResult> findByDeepsearchCode(String deepSearchCode);
}
