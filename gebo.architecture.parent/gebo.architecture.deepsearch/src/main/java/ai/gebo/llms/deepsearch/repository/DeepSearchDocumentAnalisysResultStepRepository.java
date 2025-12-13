package ai.gebo.llms.deepsearch.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;

public interface DeepSearchDocumentAnalisysResultStepRepository
		extends IGBaseMongoDBRepository<DeepSearchDocumentAnalisysResultStep> {
	public Page<DeepSearchDocumentAnalisysResultStep> findByDeepsearchCode(String deepsearchCode, Pageable pageable);

	public void deleteByDeepsearchCode(String deepSearchCode);

	public List<DeepSearchDocumentAnalisysResultStep> findByDeepsearchCode(String deepSearchCode);

	@Override
	default Class<DeepSearchDocumentAnalisysResultStep> getManagedType() {
		
		return DeepSearchDocumentAnalisysResultStep.class;
	}
}
