package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIRankerModelConfig;

public interface GenericOpenAIRankerModelConfigRepository extends IGBaseMongoDBRepository<GenericOpenAIAPIRankerModelConfig>{
	@Override
	default Class<GenericOpenAIAPIRankerModelConfig> getManagedType() {
		return GenericOpenAIAPIRankerModelConfig.class;
	}
}
