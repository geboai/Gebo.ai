package ai.gebo.application.messaging.model;

import java.util.List;
import java.util.function.Function;

import ai.gebo.application.messaging.IGMessagePayloadType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GStandardWorkflowStep {
	DOCUMENT_DISCOVERY(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef("generical-endpoing-module", "generical-endpoint-component",
					GStandardWorkflow.INGESTION.name(), "document_discovery"),
			GStandardWorkflowStep.DOCUMENT_DISCOVERY_FORWARDS, true, true),
	TOKENIZATION(GStandardWorkflow.INGESTION, new GMessagingComponentRef(GStandardModulesConstraints.TOKENIZER_MODULE,
			GStandardModulesConstraints.TOKENIZER_COMPONENT, GStandardWorkflow.INGESTION.name(), "tokenization"),
			GStandardWorkflowStep.TOKENIZATION_FORWARDS, false, true),

	EMBEDDING(GStandardWorkflow.INGESTION, new GMessagingComponentRef(GStandardModulesConstraints.VECTORIZATOR_MODULE,
			GStandardModulesConstraints.VECTORIZATION_COMPONENT, GStandardWorkflow.INGESTION.name(), "embedding"),
			(IGMessagePayloadType envelope) -> {
				return List.of();
			}, false, false),
	GRAPHEXTRACTION(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef(GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE,
					GStandardModulesConstraints.KNOWLEDGE_GRAPH_COMPONENT, GStandardWorkflow.INGESTION.name(),
					"tokenization"),
			(IGMessagePayloadType envelope) -> {
				return List.of();
			}, false, false),
	FULLTEXT_INDEXING(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef(GStandardModulesConstraints.FULLTEXT_MODULE,
					GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT, GStandardWorkflow.INGESTION.name(),
					"fulltext_indexing"),
			(IGMessagePayloadType envelope) -> {
				return List.of();
			}, false, false);

	private final GStandardWorkflow workflow;
	private final GMessagingComponentRef targetComponent;
	private final Function<IGMessagePayloadType, List<GMessagingComponentRef>> onProcessedForwardComponents;
	private final boolean workflowStartStep;
	private final boolean mandatoryStep;
	public static final Function<IGMessagePayloadType, List<GMessagingComponentRef>> TOKENIZATION_FORWARDS = (
			IGMessagePayloadType envelope) -> {
		return List.of(EMBEDDING.getTargetComponent(), GRAPHEXTRACTION.getTargetComponent(),
				FULLTEXT_INDEXING.getTargetComponent());
	};
	public static final Function<IGMessagePayloadType, List<GMessagingComponentRef>> DOCUMENT_DISCOVERY_FORWARDS = (
			IGMessagePayloadType envelope) -> {
		return List.of(TOKENIZATION.getTargetComponent());
	};
}
