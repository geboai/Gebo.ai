package ai.gebo.architecture.llms.usage.model;

import ai.gebo.core.messages.LLMCallOutcome;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ai.gebo.model.ModelType;
import lombok.Data;

@Data
@Document
public class LLMDailyUsageDetail {
	/**
	 * Stored names of the response time fields, which keep their former "latency*"
	 * names. Raw aggregation pipelines reference these, not the property names.
	 */
	public static final String RESPONSE_TIME_MIN_FIELD = "latencyMin";
	public static final String RESPONSE_TIME_MAX_FIELD = "latencyMax";
	public static final String RESPONSE_TIME_AVG_FIELD = "latencyAvg";

	@Id
	private String id = UUID.randomUUID().toString();
	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	/**
	 * Outcome of the calls aggregated here. Successes, errors and cancellations are
	 * consolidated separately: a timed out call lands at exactly the configured
	 * ceiling, so averaging it together with the successes would drag the mean
	 * toward the timeout and hide it at the same time.
	 */
	private LLMCallOutcome outcome;
	private int year;
	private int month;
	private int day;
	/*
	 * Response time in ms (request issued to response complete). Stored under the
	 * former "latency*" names so the documents written before the rename keep mapping.
	 */
	@Field(RESPONSE_TIME_MIN_FIELD)
	private long responseTimeMin;
	@Field(RESPONSE_TIME_MAX_FIELD)
	private long responseTimeMax;
	@Field(RESPONSE_TIME_AVG_FIELD)
	private long responseTimeAvg;
	/*
	 * Time to first token in ms, over the calls that measured it (streamed chat calls
	 * only). Null when none of the calls aggregated here did.
	 */
	private Long timeToFirstTokenMin;
	private Long timeToFirstTokenMax;
	private Long timeToFirstTokenAvg;
	/** How many of the calls aggregated here measured a time to first token. */
	private long timeToFirstTokenSamples;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private long nrRequests;

}
