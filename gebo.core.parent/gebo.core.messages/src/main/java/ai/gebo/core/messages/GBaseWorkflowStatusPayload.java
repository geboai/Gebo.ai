package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import lombok.Data;
@Data
public class GBaseWorkflowStatusPayload extends GBaseMessagePayload {
	private String jobId=null;
	private String workflowType = null;
	private String workflowId = null;
}
