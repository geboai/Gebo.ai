package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelConfig;

public interface GenericOpenAIAPITranscriptModelConfigRepository
		extends IGBaseMongoDBRepository<GenericOpenAIAPITranscriptModelConfig> {
	@Override
	default Class<GenericOpenAIAPITranscriptModelConfig> getManagedType() {

		return GenericOpenAIAPITranscriptModelConfig.class;
	}
}
