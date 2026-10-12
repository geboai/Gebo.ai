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
	/**
	 * The real provider of the model called, e.g. "openai". Null in the records
	 * written before it existed whose model type is unknown where they are converted.
	 */
	private String providerId;
	/**
	 * The code of the model type called, e.g. "chatgpt-OpenAI". The records written
	 * before it existed carried it under providerId.
	 */
	private String modelTypeCode;
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
	/**
	 * The API secret code the call went through, the pseudo key "__no-api-key__" for a
	 * model without one: it attributes the traffic to the provider deal covering the
	 * key. Null in the records written before it existed.
	 */
	private String apiSecretCode;
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
	/**
	 * Total cost of the priced calls aggregated here, in {@link #currencyCode}; null
	 * when none was priced, or when they were priced in different currencies, which
	 * cannot be summed.
	 */
	private Double cost;
	/** ISO 4217 currency of {@link #cost}. */
	private String currencyCode;
	/** How many of the calls aggregated here had a cost. */
	private long costSamples;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private long nrRequests;

}
