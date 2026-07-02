package ai.gebo.llms.abstraction.layer.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private long latencyMin;
	private long latencyMax;
	private long latencyAvg;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private long nrRequests;
	private int year;
	private int month;
	private int day;

}
