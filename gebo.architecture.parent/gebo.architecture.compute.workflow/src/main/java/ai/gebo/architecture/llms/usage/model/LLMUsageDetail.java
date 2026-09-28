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
	private long inputToken;
	private long outputToken;
	private long totalToken;
	/** How the call ended; null on records written before this field existed. */
	private LLMCallOutcome outcome;
	@HashIndexed
	private long timestamp = System.currentTimeMillis();
}
