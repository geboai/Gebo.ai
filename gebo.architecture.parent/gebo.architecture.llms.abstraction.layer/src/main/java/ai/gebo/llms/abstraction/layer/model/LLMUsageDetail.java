package ai.gebo.llms.abstraction.layer.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class LLMUsageDetail {
	private static final String UNKNOWN = "unknown";

	@Id
	private String id = UUID.randomUUID().toString();

	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	private long latency;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	@HashIndexed
	private long timestamp = System.currentTimeMillis();

	public static LLMUsageDetail of(GBaseModelConfig config) {
		LLMUsageDetail detail = new LLMUsageDetail();
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
