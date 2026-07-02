package ai.gebo.llms.chat.client.rest.model;

import ai.gebo.llms.abstraction.layer.model.ModelType;
import lombok.Data;

/**
 * Drill-down criteria for LLM usage aggregation.
 *
 * Every non-null field acts as an equality filter; null fields are aggregated
 * across (they are not used as grouping/filtering criteria).
 */
@Data
public class LLMUsageDrillDownLevel {
	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	private Integer year;
	private Integer month;
}
