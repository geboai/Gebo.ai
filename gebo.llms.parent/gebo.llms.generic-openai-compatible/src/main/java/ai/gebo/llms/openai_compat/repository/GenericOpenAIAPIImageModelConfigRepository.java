package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelConfig;

public interface GenericOpenAIAPIImageModelConfigRepository
		extends IGBaseMongoDBRepository<GenericOpenAIAPIImageModelConfig> {
	@Override
	default Class<GenericOpenAIAPIImageModelConfig> getManagedType() {

		return GenericOpenAIAPIImageModelConfig.class;
	}
}
