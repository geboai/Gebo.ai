package ai.gebo.architecture.llms.usage.model;

import ai.gebo.core.messages.LLMCallOutcome;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import ai.gebo.model.ModelType;
import lombok.Data;

@Data
@Document
public class LLMUsageDetail {
	@Id
	private String id = UUID.randomUUID().toString();

	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	/**
	 * Response time in ms: from the request being issued to the response being
	 * complete. Stored under its former name, "latency", so the records written
	 * before the rename keep mapping.
	 */
	@Field("latency")
	private long responseTime;
	/**
	 * Time to first token in ms, the latency of the call in the strict sense. Measured
	 * only for streamed chat calls; null otherwise and on older records.
	 */
	private Long timeToFirstToken;
	/**
	 * Cost of the call in {@link #currencyCode}, from the model's pricing conditions;
	 * null when the model has no pay per use price, and on older records.
	 */
	private Double cost;
	/** ISO 4217 currency of {@link #cost}. */
	private String currencyCode;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	/** How the call ended; null on records written before this field existed. */
	private LLMCallOutcome outcome;
	/**
	 * The API secret code the call went through, the pseudo key "__no-api-key__" for a
	 * model without one: it attributes the traffic to the provider deal covering the
	 * key. Null in the records written before it existed.
	 */
	private String apiSecretCode;
	@HashIndexed
	private long timestamp = System.currentTimeMillis();
}
