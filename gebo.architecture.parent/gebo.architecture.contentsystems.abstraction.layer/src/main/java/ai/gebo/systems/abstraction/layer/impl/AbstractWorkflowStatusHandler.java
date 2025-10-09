package ai.gebo.systems.abstraction.layer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStepEnabledHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStepEnabledHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowItem;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessedSummary;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractWorkflowStatusHandler implements IWorkflowStatusHandler {
	protected final ContentsBatchProcessedRepository contentsBatchRepo;
	protected final IWorkflowStepEnabledHandlerRepositoryPattern stepEnabledHandlerRepositoryPattern;
	protected final IGPersistentObjectManager persistentObject;
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected abstract ComputedWorkflowStructure workflowStructureImplementation(String workflowType,
			String workflowId, WorkflowContext context);

	@Override
	public ComputedWorkflowStructure getWorkflowStructure(String workflowType, String workflowId,
			WorkflowContext context) {
		ComputedWorkflowStructure structure = workflowStructureImplementation(workflowType, workflowId,context);
		this.checkEnabledNodes(structure.getRootStep(),context);
		return structure;
	}

	private void checkEnabledNodes(ComputedWorkflowItem rootStep, WorkflowContext context) {
		IWorkflowStepEnabledHandler handler = stepEnabledHandlerRepositoryPattern
				.findByWorkflowsTypeAndWorkflowIdAndWorkflowStepId(GWorkflowType.valueOf(rootStep.getWorkflowType()),
						rootStep.getWorkflowId(), rootStep.getWorkflowStepId());
		rootStep.setEnabledStep(
				handler != null && handler.isEnabled(rootStep.getWorkflowId(), rootStep.getWorkflowStepId(),context));
		if (rootStep.isEnabledStep()) {
			rootStep.getChilds().forEach(childStep -> {
				checkEnabledNodes(childStep,context);
			});
		} else
			rootStep.getChilds().forEach(childStep -> {
				childStep.setEnabledStep(false);
			});

	}

	@Override
	public ComputedWorkflowResult computeWorkflowStatus(String jobId, String workflowType, String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin computeWorkflowStatus(" + jobId + "," + workflowType + "," + workflowId + ")");
		}
		try {
			GJobStatus job = persistentObject.findById(GJobStatus.class, jobId);

			WorkflowContext context = new WorkflowContext(job.getKnowledgeBaseCode(), job.getProjectCode(),
					job.getProjectEndpointReference());
			ComputedWorkflowStructure structure = getWorkflowStructure(workflowType, workflowId, context);
			ComputedWorkflowResult result = null;
			if (structure != null) {
				result = new ComputedWorkflowResult();
				result.setWorkflowType(workflowType);
				result.setWorkflowId(workflowId);
				List<ComputedWorkflowItem> workflowSteps = items(structure.getRootStep());
				Map<String, ContentsBatchProcessed> aggregated = new HashMap<String, ContentsBatchProcessed>();
				List<ComputedWorkflowItem> steps = new ArrayList<ComputedWorkflowItem>();
				for (ComputedWorkflowItem thisStep : workflowSteps) {
					if (thisStep.getWorkflowId().equalsIgnoreCase(workflowId)) {
						steps.add(thisStep);
						ContentsBatchProcessed aggregate = new ContentsBatchProcessed();
						aggregate.setJobId(jobId);
						aggregate.setWorkflowType(workflowType);
						aggregate.setWorkflowId(workflowId);
						aggregate.setWorkflowStepId(thisStep.getWorkflowStepId());

						aggregated.put(thisStep.getWorkflowStepId().toUpperCase(), aggregate);
					}
				}
				List<ContentsBatchProcessedSummary> data = contentsBatchRepo.aggregateByJobId(jobId);
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
				this.checkStatus(result);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End computeWorkflowStatus(" + jobId + "," + workflowType + "," + workflowId + ")");
			}
			return result;
		} catch (GeboPersistenceException e) {
			throw new RuntimeException("Exception while accessing the mongo persistence layer", e);
		}

	}

	private void checkStatus(ComputedWorkflowResult result) {
		List<ComputedWorkflowStatus> items = items(result.getRootStatus());
		result.setFinished(items.stream().allMatch(x -> (x.isCompleted() || !x.isEnabledStep())));
		result.setHasErrors(items.stream().anyMatch(x -> x.isHasErrors()));
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

		ContentsBatchProcessed data = aggregated.get(status.getWorkflowStepId().toUpperCase());
		if (data != null) {
			status.setBatchDocumentsInput(data.getBatchDocumentsInput());
			status.setBatchDocumentsProcessed(data.getBatchDocumentsProcessed());
			status.setBatchDocumentsProcessingErrors(data.getBatchDocumentsProcessingErrors());
			status.setBatchSentToNextStep(data.getBatchSentToNextStep());
			status.setBatchDiscardedInput(data.getBatchDiscardedInput());
			status.setChunksProcessed(data.getChunksProcessed());
			status.setTokensProcessed(data.getTokensProcessed());
			status.setStartedRunning(data.getBatchDocumentsInput() > 0l);
			if (status.getLevelId() == 0) {
				// The root step is completed when the last message is passed
				status.setCompleted(data.getLastMessage() != null && data.getLastMessage());
			}
			status.setHasErrors(status.getBatchDocumentsProcessingErrors() > 0);
		}
		if (rootStep.getChilds() != null && !rootStep.getChilds().isEmpty()) {
			for (ComputedWorkflowItem metaStep : rootStep.getChilds()) {
				status.getChilds().add(composeStatus(metaStep, aggregated));
			}
		}
		return status;
	}

	private void visitAndCompileStatus(ComputedWorkflowStatus rootStatus) {
		rootStatus.setHasErrors(rootStatus.getBatchDocumentsProcessingErrors() > 0);

		List<ComputedWorkflowStatus> childs = rootStatus.getChilds();
		for (ComputedWorkflowStatus child : childs) {
			boolean completed = rootStatus.isCompleted()
					&& (rootStatus.getBatchSentToNextStep() == (child.getBatchDocumentsProcessed()
							+ child.getBatchDiscardedInput() + child.getBatchDocumentsProcessingErrors()));
			child.setCompleted(completed);
			visitAndCompileStatus(child);
		}

	}
}
