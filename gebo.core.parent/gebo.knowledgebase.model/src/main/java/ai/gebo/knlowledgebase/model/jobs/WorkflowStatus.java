package ai.gebo.knlowledgebase.model.jobs;

import lombok.Data;

@Data
public class WorkflowStatus {
	private boolean completed = false;
	private boolean hasErrors = false;
	private long totalDocuments = 0;
	private long totalDocumentsWithErrors = 0;
	private long totalDocumentsSuccessfull = 0;

}
