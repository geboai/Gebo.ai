package ai.gebo.jobs.services.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JobWorkflowStepSummary {
	private String workflowType = null;
	private String workflowId = null;
	private String workflowStepId = null;
	private List<JobWorkflowStepSummaryTimeSlotStats> timesamples = new ArrayList<JobWorkflowStepSummaryTimeSlotStats>();
}
