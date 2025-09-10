package ai.gebo.application.messaging.workflow;

import java.util.List;
import java.util.function.Function;

import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.model.GMessagingComponentRef;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GStandardWorkflowStep {
	DOCUMENT_DISCOVERY(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef("generical-endpoing-module", "generical-endpoint-component",
					GStandardWorkflow.INGESTION.name(), "document_discovery"),
			new DocumentDiscoveryForwards(), true, true),
	TOKENIZATION(GStandardWorkflow.INGESTION, new GMessagingComponentRef(GStandardModulesConstraints.TOKENIZER_MODULE,
			GStandardModulesConstraints.TOKENIZER_COMPONENT, GStandardWorkflow.INGESTION.name(), "tokenization"),
			new TokenizationForwards(), false, true),

	EMBEDDING(GStandardWorkflow.INGESTION, new GMessagingComponentRef(GStandardModulesConstraints.VECTORIZATOR_MODULE,
			GStandardModulesConstraints.VECTORIZATION_COMPONENT, GStandardWorkflow.INGESTION.name(), "embedding"),
			new VoidForwards(), false, false),

	GRAPHEXTRACTION(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef(GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE,
					GStandardModulesConstraints.KNOWLEDGE_GRAPH_COMPONENT, GStandardWorkflow.INGESTION.name(),
					"graphextraction"),
			new VoidForwards(), false, false),
	FULLTEXT_INDEXING(GStandardWorkflow.INGESTION,
			new GMessagingComponentRef(GStandardModulesConstraints.FULLTEXT_MODULE,
					GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT, GStandardWorkflow.INGESTION.name(),
					"fulltext_indexing"),
			new VoidForwards(), false, false);

	private final GStandardWorkflow workflow;
	private final GMessagingComponentRef targetComponent;
	private final Function<IGMessagePayloadType, List<GMessagingComponentRef>> onProcessedForwardComponents;
	private final boolean workflowStartStep;
	private final boolean mandatoryStep;

	private static final class TokenizationForwards
			implements Function<IGMessagePayloadType, List<GMessagingComponentRef>> {
		@Override
		public List<GMessagingComponentRef> apply(IGMessagePayloadType t) {
			return List.of(getTargetOf("EMBEDDING"),
					getTargetOf("GRAPHEXTRACTION") /* , getTargetOf("FULLTEXT_INDEXING") */);
		}
	};

	private static final class VoidForwards implements Function<IGMessagePayloadType, List<GMessagingComponentRef>> {
		@Override
		public List<GMessagingComponentRef> apply(IGMessagePayloadType t) {
			return List.of();
		}
	};

	private static final class DocumentDiscoveryForwards
			implements Function<IGMessagePayloadType, List<GMessagingComponentRef>> {
		@Override
		public List<GMessagingComponentRef> apply(IGMessagePayloadType t) {
			return List.of(getTargetOf("tokenization"));
		}
	};

	public static final GMessagingComponentRef getTargetOf(String id) {
		GStandardWorkflowStep found = valueOf(id.toUpperCase());
		return found.getTargetComponent();
	}

}
