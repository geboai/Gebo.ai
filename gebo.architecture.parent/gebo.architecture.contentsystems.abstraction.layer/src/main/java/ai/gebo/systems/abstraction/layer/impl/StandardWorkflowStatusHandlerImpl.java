package ai.gebo.systems.abstraction.layer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StandardWorkflowStatusHandlerImpl implements IWorkflowStatusHandler {
	final ContentsBatchProcessedRepository contentsBatchRepo;

	@Override
	public String getId() {

		return "standard-workflow-handler";
	}

	@Override
	public GWorkflowType getWorkflowType() {

		return GWorkflowType.STANDARD;
	}

	@Override
	public boolean handleWorkflow(String workFlowId) {
		boolean found = false;
		GStandardWorkflow[] data = GStandardWorkflow.values();
		for (GStandardWorkflow gStandardWorkflow : data) {
			found = gStandardWorkflow.name().equalsIgnoreCase(workFlowId) || found;
		}
		return found;
	}

	@Override
	public ComputedWorkflowStatus computeWorkflowStatus(String jobId, String workflowType, String workflowId) {

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
		ContentsBatchProcessed discovery = aggregated
				.get(GStandardWorkflowStep.DOCUMENT_DISCOVERY.name().toUpperCase());
		ContentsBatchProcessed tokenization = aggregated.get(GStandardWorkflowStep.TOKENIZATION.name().toUpperCase());
		ContentsBatchProcessed embedding = aggregated.get(GStandardWorkflowStep.EMBEDDING.name().toUpperCase());
		ContentsBatchProcessed graphextraction = aggregated
				.get(GStandardWorkflowStep.GRAPHEXTRACTION.name().toUpperCase());
		// Todo next calculation is ok for demo but not for production
		long totalDocuments = discovery != null ? discovery.getBatchDocumentsInput() : 0l;
		long totalDocumentsWithErrors = (discovery != null ? discovery.getBatchDocumentsProcessingErrors() : 0l)
				+ Math.max((tokenization != null ? tokenization.getBatchDocumentsProcessingErrors() : 0),
						(graphextraction != null ? graphextraction.getBatchDocumentsProcessingErrors() : 0));
		long totalDocumentsSuccessfull = Math.max(
				(tokenization != null ? tokenization.getBatchDocumentsProcessed() : 0),
				(graphextraction != null ? graphextraction.getBatchDocumentsProcessed() : 0));
		boolean completed = totalDocumentsWithErrors + totalDocumentsSuccessfull > totalDocuments;
		boolean hasErrors = totalDocumentsWithErrors > 0;
		ComputedWorkflowStatus status = new ComputedWorkflowStatus(completed, hasErrors, totalDocuments,
				totalDocumentsWithErrors, totalDocumentsSuccessfull);
		return status;
	}

}
