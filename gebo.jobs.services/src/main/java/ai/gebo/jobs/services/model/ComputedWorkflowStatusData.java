package ai.gebo.jobs.services.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class ComputedWorkflowStatusData {
	@Id
	private String id = UUID.randomUUID().toString();
	private String jobId = null;
	private String workflowType = null;
	private String workflowId = null;
	private String workflowStepId = null;
	private String description = null;
	private long batchDocumentsInput;
	private long batchDocumentsProcessingErrors;
	private long batchDocumentsProcessed;
	private long batchSentToNextStep;
	private long batchDiscardedInput = 0;
	private long chunksProcessed = 0l;
	private long tokensProcessed = 0l;
	private boolean completed;
	private boolean hasErrors;
	private boolean startedRunning;
	private int levelId = 0;
	private boolean enabledStep;
	private Date startProcessingTimestamp = null;
	private Date lastProcessingTimestamp = null;
	private int year = 0;
	private int month = 0;
	private int day = 0;
}
