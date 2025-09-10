package ai.gebo.systems.abstraction.layer.impl;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowItem;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractWorkflowStatusHandler implements IWorkflowStatusHandler {
	protected final ContentsBatchProcessedRepository contentsBatchRepo;

	@Override
	public ComputedWorkflowResult computeWorkflowStatus(String jobId, String workflowType, String workflowId) {
		ComputedWorkflowStructure structure = getWorkflowStructure(workflowType, workflowId);
		ComputedWorkflowResult result = null;
		if (structure != null) {
			result = new ComputedWorkflowResult();
			result.setWorkflowType(workflowType);
			result.setWorkflowId(workflowId);
			GStandardWorkflowStep[] workflowSteps = GStandardWorkflowStep.values();
			Map<String, ContentsBatchProcessed> aggregated = new HashMap<String, ContentsBatchProcessed>();
			List<GStandardWorkflowStep> steps = new ArrayList<GStandardWorkflowStep>();
			for (GStandardWorkflowStep thisStep : workflowSteps) {
				if (thisStep.getWorkflow().name().equalsIgnoreCase(workflowId)) {
					steps.add(thisStep);
					ContentsBatchProcessed aggregate = new ContentsBatchProcessed();
					aggregate.setJobId(jobId);
					aggregate.setWorkflowType(workflowType);
					aggregate.setWorkflowId(workflowId);
					aggregate.setWorkflowStepId(thisStep.name());
					aggregated.put(thisStep.name().toUpperCase(), aggregate);
				}
			}
			Stream<ContentsBatchProcessed> data = contentsBatchRepo.findByJobId(jobId);
			data.forEach(entry -> {
				if (entry.getWorkflowStepId() != null) {
					ContentsBatchProcessed aggregate = aggregated.get(entry.getWorkflowStepId().toUpperCase());
					if (aggregate != null) {
						aggregate.incrementBy(entry);
					}
				}
			});
			result.setRootStatus(composeStatus(structure.getRootStep(), aggregated));
			this.visitAndCompileStatus(result.getRootStatus());

		}
		return result;

	}

	private ComputedWorkflowStatus composeStatus(ComputedWorkflowItem rootStep,
			Map<String, ContentsBatchProcessed> aggregated) {
		ComputedWorkflowStatus status = new ComputedWorkflowStatus();
		status.setWorkflowType(rootStep.getWorkflowType());
		status.setWorkflowId(rootStep.getWorkflowId());
		status.setWorkflowStepId(rootStep.getWorkflowStepId());
		status.setDescription(rootStep.getDescription());
		status.setEnabledStep(true);
		status.setLevelId(rootStep.getLevelId());

		ContentsBatchProcessed data = aggregated.get(status.getWorkflowStepId());
		if (data != null) {
			status.setBatchDocumentsInput(data.getBatchDocumentsInput());
			status.setBatchDocumentsProcessed(data.getBatchDocumentsProcessed());
			status.setBatchDocumentsProcessingErrors(data.getBatchDocumentsProcessingErrors());
			status.setBatchSentToNextStep(data.getBatchSentToNextStep());
			status.setChunksProcessed(data.getChunksProcessed());
			status.setTokensProcessed(data.getTokensProcessed());
			status.setStartedRunning(data.getBatchDocumentsInput() > 0l);
			if (status.getLevelId() == 0) {
				// The root step is completed when the last message is passed
				status.setCompleted(data.getLastMessage() != null && data.getLastMessage());
			}
			status.setHasErrors(status.getBatchDocumentsProcessingErrors() > 0);
		}
		return status;
	}

	private void visitAndCompileStatus(ComputedWorkflowStatus rootStatus) {
		rootStatus.setHasErrors(rootStatus.getBatchDocumentsProcessingErrors() > 0);

		List<ComputedWorkflowStatus> childs = rootStatus.getChilds();
		for (ComputedWorkflowStatus child : childs) {
			boolean completed = rootStatus.isCompleted()
					&& (rootStatus.getBatchSentToNextStep() == (child.getBatchDocumentsProcessed()
							+ child.getBatchDocumentsProcessingErrors()));
			child.setCompleted(completed);
			visitAndCompileStatus(child);
		}

	}
}
