package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIImageModelConfig;

public interface GenericOpenAIImageModelConfigRepository
		extends IGBaseMongoDBRepository<GenericOpenAIImageModelConfig> {
	@Override
	default Class<GenericOpenAIImageModelConfig> getManagedType() {

		return GenericOpenAIImageModelConfig.class;
	}
}
