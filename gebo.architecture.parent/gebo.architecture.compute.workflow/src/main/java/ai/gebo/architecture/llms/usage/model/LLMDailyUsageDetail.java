package ai.gebo.architecture.llms.usage.model;

import ai.gebo.core.messages.LLMCallOutcome;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.ModelType;
import lombok.Data;

@Data
@Document
public class LLMDailyUsageDetail {
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
	private long latencyMin;
	private long latencyMax;
	private long latencyAvg;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private long nrRequests;

}
