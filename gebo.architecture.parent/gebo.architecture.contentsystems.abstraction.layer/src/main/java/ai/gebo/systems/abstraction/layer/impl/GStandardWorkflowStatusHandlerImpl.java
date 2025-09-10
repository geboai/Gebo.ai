package ai.gebo.systems.abstraction.layer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GMessagingComponentRef;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowItem;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;

@Component

public class GStandardWorkflowStatusHandlerImpl extends AbstractWorkflowStatusHandler {

	public GStandardWorkflowStatusHandlerImpl(ContentsBatchProcessedRepository contentsBatchRepo) {
		super(contentsBatchRepo);

	}

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
	public ComputedWorkflowStructure getWorkflowStructure(String workflowType, String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getWorkflowStructure(" + workflowType + "," + workflowId + ")");
		}
		GStandardWorkflow[] data = GStandardWorkflow.values();
		ComputedWorkflowStructure structure = null;
		GStandardWorkflow currentWorkflow = null;
		for (int i = 0; i < data.length; i++) {
			GStandardWorkflow workflow = data[i];
			if (workflow.name().equalsIgnoreCase(workflowId)) {
				currentWorkflow = workflow;
				break;
			}
		}
		if (currentWorkflow != null) {
			final GStandardWorkflow workflow = currentWorkflow;
			List<GStandardWorkflowStep> steps = toList(GStandardWorkflowStep.values());
			steps = steps.stream().filter(x -> x.getWorkflow() == workflow).toList();
			Optional<GStandardWorkflowStep> optionalRootStep = steps.stream().filter(x -> x.isWorkflowStartStep())
					.findAny();
			if (optionalRootStep.isPresent()) {
				structure = new ComputedWorkflowStructure();
				structure.setWorkflowType(workflowType);
				structure.setWorkflowId(workflowId);
				structure.setEnabled(true);
				structure.setDescription(currentWorkflow.name());
				structure.setRootStep(createStep(optionalRootStep.get(), steps, 0, workflowType, workflowId));

			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End getWorkflowStructure(" + workflowType + "," + workflowId + ")");
		}
		return structure;
	}

	private ComputedWorkflowItem createStep(GStandardWorkflowStep step, List<GStandardWorkflowStep> steps, int level,
			String workflowType, String workflowId) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"Begin createStep(" + step.name() + ",..," + level + "," + workflowType + "," + workflowId + ")");
		}
		ComputedWorkflowItem root = new ComputedWorkflowItem();
		root.setWorkflowType(workflowType);
		root.setWorkflowId(workflowId);
		root.setWorkflowStepId(step.name());
		root.setDescription(step.name());
		root.setEnabledStep(true);
		List<GStandardWorkflowStep> childSteps = new ArrayList<GStandardWorkflowStep>();
		List<GMessagingComponentRef> nextSteps = step.getOnProcessedForwardComponents().apply(null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("step=" + step.name() + " nextSteps=" + nextSteps);
		}
		for (GMessagingComponentRef nextComponent : nextSteps) {
			final String stepId = nextComponent.getWorkflowStepId();
			boolean alreadyIn = childSteps.stream().anyMatch(x -> x.name().equalsIgnoreCase(stepId));
			if (!alreadyIn) {
				Optional<GStandardWorkflowStep> thisStep = steps.stream().filter(x -> x.name().equalsIgnoreCase(stepId))
						.findFirst();
				if (thisStep.isPresent()) {
					childSteps.add(thisStep.get());
				}
			}
		}
		for (GStandardWorkflowStep thisStep : childSteps) {
			root.getChilds().add(createStep(thisStep, steps, level + 1, workflowType, workflowId));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"End createStep(" + step.name() + ",..," + level + "," + workflowType + "," + workflowId + ")");
		}
		return root;
	}

	private <T> List<T> toList(T array[]) {
		List<T> data = new ArrayList<T>();
		if (array != null) {
			for (T t : array) {
				data.add(t);
			}
		}
		return data;
	}
}
