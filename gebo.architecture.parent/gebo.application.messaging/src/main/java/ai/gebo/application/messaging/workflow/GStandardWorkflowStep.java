package ai.gebo.application.messaging.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import ai.gebo.application.messaging.model.GMessagingComponentRef;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.application.messaging.workflow.model.WorkflowMessageContext;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GStandardWorkflowStep {
	DOCUMENT_DISCOVERY(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef("generical-endpoing-module", "generical-endpoint-component",
					GStandardWorkflow.INGESTION.name(), "document_discovery"),
			new DocumentDiscoveryForwards(), true, true, "Analyzing documents list"),
	TOKENIZATION(GStandardWorkflow.INGESTION, new GMessagingComponentRef(GStandardModulesConstraints.TOKENIZER_MODULE,
			GStandardModulesConstraints.TOKENIZER_COMPONENT, GStandardWorkflow.INGESTION.name(), "tokenization"),
			new TokenizationForwards(), false, true, "Documents tokenization"),

	EMBEDDING(GStandardWorkflow.INGESTION, new GMessagingComponentRef(GStandardModulesConstraints.VECTORIZATOR_MODULE,
			GStandardModulesConstraints.VECTORIZATION_COMPONENT, GStandardWorkflow.INGESTION.name(), "embedding"),
			new VoidForwards(), false, false, "Semantic indexing (embedding for semantic rag)"),

	GRAPHEXTRACTION(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef(GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE,
					GStandardModulesConstraints.KNOWLEDGE_GRAPH_COMPONENT, GStandardWorkflow.INGESTION.name(),
					"graphextraction"),
			new VoidForwards(), false, false, "Entity/Relations/Events indexing (graphrag)"),
	FULLTEXT_INDEXING(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef(GStandardModulesConstraints.FULLTEXT_MODULE,
					GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT, GStandardWorkflow.INGESTION.name(),
					"fulltext_indexing"),
			new VoidForwards(), false, false, "Full text indexing");

	private final GStandardWorkflow workflow;
	private final GMessagingComponentRef targetComponent;
	private final BiFunction<WorkflowMessageContext, IGRuntimeBinder, List<GMessagingComponentRef>> onProcessedForwardComponents;
	private final boolean workflowStartStep;
	private final boolean mandatoryStep;
	private final String description;

	private static final List<GMessagingComponentRef> verifyEnabledModules(List<String> list, IGRuntimeBinder binder,
			WorkflowContext context) {

		final IWorkflowStepEnabledHandlerRepositoryPattern workflowStepEnablerRepoPattern = binder
				.getImplementationOf(IWorkflowStepEnabledHandlerRepositoryPattern.class);
		if (list == null)
			list = new ArrayList<String>();
		List<GStandardWorkflowStep> steps = list.stream().map(x -> GStandardWorkflowStep.valueOf(x)).toList();
		List<GMessagingComponentRef> out = steps.stream().filter(step -> {
			if (step.isMandatoryStep())
				return true;
			IWorkflowStepEnabledHandler handler = workflowStepEnablerRepoPattern
					.findByWorkflowsTypeAndWorkflowIdAndWorkflowStepId(GWorkflowType.STANDARD,
							GStandardWorkflow.INGESTION.name(), step.name());
			return handler != null && handler.isEnabled(GStandardWorkflow.INGESTION.name(), step.name(), context);
		}).map(y -> y.getTargetComponent()).toList();
		return out;

	}

	private static final class TokenizationForwards
			implements BiFunction<WorkflowMessageContext, IGRuntimeBinder, List<GMessagingComponentRef>> {
		@Override
		public List<GMessagingComponentRef> apply(WorkflowMessageContext t, IGRuntimeBinder binder) {
			return verifyEnabledModules(List.of("EMBEDDING", "GRAPHEXTRACTION", "FULLTEXT_INDEXING"), binder,
					t.getWorkflowContext());
		}
	};

	private static final class VoidForwards
			implements BiFunction<WorkflowMessageContext, IGRuntimeBinder, List<GMessagingComponentRef>> {
		@Override
		public List<GMessagingComponentRef> apply(WorkflowMessageContext t, IGRuntimeBinder binder) {
			return List.of();
		}
	};

	private static final class DocumentDiscoveryForwards
			implements BiFunction<WorkflowMessageContext, IGRuntimeBinder, List<GMessagingComponentRef>> {
		@Override
		public List<GMessagingComponentRef> apply(WorkflowMessageContext t, IGRuntimeBinder binder) {
			return List.of(getTargetOf("tokenization"));
		}
	};

	public static final GMessagingComponentRef getTargetOf(String id) {
		GStandardWorkflowStep found = valueOf(id.toUpperCase());
		return found.getTargetComponent();
	}

}
