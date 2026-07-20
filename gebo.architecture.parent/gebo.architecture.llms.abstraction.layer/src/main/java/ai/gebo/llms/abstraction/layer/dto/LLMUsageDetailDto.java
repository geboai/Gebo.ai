package ai.gebo.llms.abstraction.layer.dto;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.model.ModelType;
import lombok.Data;

/**
 * Public-API shape of {@code ILLMSUsageCrudService.enqueueUsage(...)}, decoupling
 * callers (the usage advisor) from both the persistence entity and the internal
 * messaging payload, which now live in {@code gebo.architecture.compute.workflow}
 * and {@code gebo.core.messages} respectively.
 */
@Data
public class LLMUsageDetailDto {
	private static final String UNKNOWN = "unknown";

	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	private long latency;
	private long inputToken;
	private long outputToken;
	private long totalToken;

	public static LLMUsageDetailDto of(GBaseModelConfig config) {
		LLMUsageDetailDto detail = new LLMUsageDetailDto();
		if (config != null && config.getModelTypeCode() != null) {
			detail.setProviderId(config.getModelTypeCode());
		} else
			detail.setProviderId(UNKNOWN);
		if (config != null && config.getChoosedModel() != null && config.getChoosedModel().getCode() != null) {
			detail.setModel(config.getChoosedModel().getCode());
		} else
			detail.setModel(UNKNOWN);
		if (config instanceof GBaseChatModelConfig) {
			detail.setModelType(ModelType.CHAT);
		} else if (config instanceof GBaseEmbeddingModelConfig) {
			detail.setModelType(ModelType.EMBEDDING);
		} else if (config instanceof GBaseRankerModelConfig) {
			detail.setModelType(ModelType.RANKER);
		} else if (config instanceof GBaseImageModelConfig) {
			detail.setModelType(ModelType.IMAGE);
		} else if (config instanceof GBaseTextToSpeachModelConfig) {
			detail.setModelType(ModelType.TTS);
		} else if (config instanceof GBaseTranscriptModelConfig) {
			detail.setModelType(ModelType.TRANSCRIPT);
		}
		return detail;
	}
}
