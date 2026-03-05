package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelConfig;

public interface GenericOpenAIAPITextToSpeechModelConfigRepository
		extends IGBaseMongoDBRepository<GenericOpenAIAPITextToSpeechModelConfig> {
	@Override
	default Class<GenericOpenAIAPITextToSpeechModelConfig> getManagedType() {

		return GenericOpenAIAPITextToSpeechModelConfig.class;
	}
}
