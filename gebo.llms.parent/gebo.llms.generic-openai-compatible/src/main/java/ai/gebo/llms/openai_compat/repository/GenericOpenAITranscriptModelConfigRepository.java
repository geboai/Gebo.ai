package ai.gebo.llms.openai_compat.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAITranscriptModelConfig;

public interface GenericOpenAITranscriptModelConfigRepository
		extends IGBaseMongoDBRepository<GenericOpenAITranscriptModelConfig> {
	@Override
	default Class<GenericOpenAITranscriptModelConfig> getManagedType() {

		return GenericOpenAITranscriptModelConfig.class;
	}
}
