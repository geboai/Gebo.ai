package ai.gebo.llms.abstraction.layer.dto;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.GProviderDeal;
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

	/**
	 * The real provider of the model called, e.g. "openai" or "regolo.ai": the same for
	 * every model type of the provider.
	 */
	private String providerId;
	/**
	 * The code of the model type called, e.g. "chatgpt-OpenAI": specific to the type
	 * (chat, embedding, ...) of the provider.
	 */
	private String modelTypeCode;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	/** Response time in ms: request issued to response complete. */
	private long responseTime;
	/** Time to first token in ms, for streamed chat calls only; null otherwise. */
	private Long timeToFirstToken;
	/**
	 * Cost of the call in {@link #currencyCode}, from the model's pricing conditions;
	 * null when the model has no pay per use price.
	 */
	private Double cost;
	/** ISO 4217 currency of {@link #cost}. */
	private String currencyCode;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private LLMCallOutcome outcome;
	/**
	 * The API secret code the call went through, the pseudo key "__no-api-key__" for a
	 * model without one: it attributes the traffic to the provider deal covering the
	 * key. Null in the records written before it existed.
	 */
	private String apiSecretCode;

	public static LLMUsageDetailDto of(GBaseModelConfig config) {
		return of(config, null);
	}

	/**
	 * @param providerId the real provider of the model, its
	 *                   {@code IGConfigurableModel.getProviderId()}; null or blank
	 *                   records it as unknown
	 */
	public static LLMUsageDetailDto of(GBaseModelConfig config, String providerId) {
		LLMUsageDetailDto detail = new LLMUsageDetailDto();
		detail.setProviderId(providerId != null && !providerId.isBlank() ? providerId : UNKNOWN);
		if (config != null && config.getModelTypeCode() != null) {
			detail.setModelTypeCode(config.getModelTypeCode());
		} else
			detail.setModelTypeCode(UNKNOWN);
		if (config != null && config.getChoosedModel() != null && config.getChoosedModel().getCode() != null) {
			detail.setModel(config.getChoosedModel().getCode());
		} else
			detail.setModel(UNKNOWN);
		if (config != null) {
			detail.setApiSecretCode(GProviderDeal.coveredKey(config.getApiSecretCode()));
		}
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
