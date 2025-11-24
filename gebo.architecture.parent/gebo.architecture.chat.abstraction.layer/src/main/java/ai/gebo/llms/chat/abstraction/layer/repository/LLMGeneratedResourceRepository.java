package ai.gebo.llms.chat.abstraction.layer.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.chat.abstraction.layer.model.LLMGeneratedResource;

public interface LLMGeneratedResourceRepository extends IGBaseMongoDBRepository<LLMGeneratedResource> {
	
	@Override
	default Class<LLMGeneratedResource> getManagedType() {
		return LLMGeneratedResource.class;
	}
	public void deleteByUserContextCode(String userContextCode);
}
