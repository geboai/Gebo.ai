package ai.gebo.architecture.llms.usage.model;

import ai.gebo.core.messages.LLMCallOutcome;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private long latency;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	/** How the call ended; null on records written before this field existed. */
	private LLMCallOutcome outcome;
	@HashIndexed
	private long timestamp = System.currentTimeMillis();
}
