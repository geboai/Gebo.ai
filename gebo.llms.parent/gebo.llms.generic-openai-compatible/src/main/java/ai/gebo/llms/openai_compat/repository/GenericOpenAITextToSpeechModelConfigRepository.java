package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAITextToSpeechModelConfig;

public interface GenericOpenAITextToSpeechModelConfigRepository
		extends IGBaseMongoDBRepository<GenericOpenAITextToSpeechModelConfig> {
	@Override
	default Class<GenericOpenAITextToSpeechModelConfig> getManagedType() {

		return GenericOpenAITextToSpeechModelConfig.class;
	}
}
