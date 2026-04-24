package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIRankerModelConfig;

public interface GenericOpenAIRankerModelConfigRepository extends IGBaseMongoDBRepository<GenericOpenAIRankerModelConfig>{
	@Override
	default Class<GenericOpenAIRankerModelConfig> getManagedType() {
		return GenericOpenAIRankerModelConfig.class;
	}
}
