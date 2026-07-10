package ai.gebo.workflows.compute.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

@Data
@Document
public class ComputedWorkflowStatusData {
	@Id
	private String id = UUID.randomUUID().toString();
	private String jobId = null;
	private GObjectRef<GProjectEndpoint> projectEndpointReference = null;
	/** Parent project of the referred project endpoint. */
	private GObjectRef<GProject> projectReference = null;
	/** Parent knowledge base of the project (its root knowledge base). */
	private GObjectRef<GKnowledgeBase> knowledgeBaseReference = null;
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
	/**
	 * When true the owning workflow job is finished: this snapshot represents the
	 * frozen facts and is not recomputed on subsequent scheduler runs.
	 */
	private boolean finalized = false;
}
