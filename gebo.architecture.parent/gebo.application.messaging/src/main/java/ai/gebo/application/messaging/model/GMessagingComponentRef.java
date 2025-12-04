package ai.gebo.application.messaging.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GMessagingComponentRef implements Serializable {
	private String moduleId = null;
	private String componentId = null;
	private String workflowId = null;
	private String workflowStepId = null;
	

}