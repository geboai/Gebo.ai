package ai.gebo.architecture.llms.usage.model;

import ai.gebo.model.ModelType;
import lombok.Data;

/**
 * Drill-down criteria for LLM usage aggregation.
 *
 * Every non-null field acts as an equality filter; null fields are aggregated
 * across (they are not used as grouping/filtering criteria).
 */
@Data
public class LLMUsageDrillDownLevel {
	/** The real provider, e.g. "openai". */
	private String providerId;
	/** The model type code, e.g. "chatgpt-OpenAI": one model type of a provider. */
	private String modelTypeCode;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	private Integer year;
	private Integer month;
}
