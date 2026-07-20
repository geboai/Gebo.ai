package ai.gebo.architecture.llms.usage.model;

import ai.gebo.model.ModelType;
import lombok.Data;

/**
 * A single aggregated usage row. The dimension fields (providerId, username,
 * model, callerStack, modelType) are populated only for the fields that were
 * present in the drill-down criteria; the others are null because they have
 * been aggregated across. {@code day} is null on the monthly dataset.
 */
@Data
public class LLMUsageAggregationBucket {
	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	private Integer year;
	private Integer month;
	private Integer day;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private long nrRequests;
	private long latencyMin;
	private long latencyMax;
	private long latencyAvg;
}
