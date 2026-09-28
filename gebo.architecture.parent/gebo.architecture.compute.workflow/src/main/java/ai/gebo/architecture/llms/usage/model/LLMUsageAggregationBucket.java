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
	/** Response time in ms: request issued to response complete. */
	private long responseTimeMin;
	private long responseTimeMax;
	private long responseTimeAvg;
	/**
	 * Time to first token in ms, over the calls that measured it (streamed chat calls
	 * only); null when none of the bucket's calls did.
	 */
	private Long timeToFirstTokenMin;
	private Long timeToFirstTokenMax;
	private Long timeToFirstTokenAvg;
	/** How many of the bucket's calls measured a time to first token. */
	private long timeToFirstTokenSamples;
}
