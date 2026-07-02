package ai.gebo.llms.abstraction.layer.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class LLMUsageDetail {
	public static enum ModelType {
		CHAT, EMBEDDING, IMAGE, RANKER, TTS, TRANSCRIPT
	}
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
	private long timestamp = System.currentTimeMillis();

	public static LLMUsageDetail of(GBaseModelConfig config) {
		LLMUsageDetail detail = new LLMUsageDetail();
		if (config != null && config.getModelTypeCode() != null) {
			detail.setProviderId(config.getModelTypeCode());
		} else
			detail.setProviderId("unknown");
		if (config != null && config.getChoosedModel() != null && config.getChoosedModel().getCode() != null) {
			detail.setModel(config.getChoosedModel().getCode());
		} else
			detail.setModel("unknown");
		if (config instanceof GBaseChatModelConfig) {
			detail.setModelType(ModelType.CHAT);
		} else if (config instanceof GBaseEmbeddingModelConfig) {
			detail.setModelType(ModelType.EMBEDDING);
		} else if (config instanceof GBaseRankerModelConfig) {
			detail.setModelType(ModelType.RANKER);
		}
		return detail;
	}
}
