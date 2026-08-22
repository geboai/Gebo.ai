package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.ragsystem.content.graphrag_processor.IGraphRagProcessorMessagesReceiverFactoryComponent;
import ai.gebo.ragsystem.content.graphrag_processor.config.GeboGraphRagProcessorConfig;

@Component

public class GraphextractionProcessorMessagesReceiverFactoryComponent extends GAbstractTimedOutMessageReceiverFactory
		implements IGraphRagProcessorMessagesReceiverFactoryComponent {

	protected final IGRuntimeBinder runtimeBinder;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GraphextractionProcessorMessagesReceiverFactoryComponent.class);

	public class GraphRagProcessorBatchGrouperReceiver extends GNestedBatchAggregatorMessageReceiver {

		public GraphRagProcessorBatchGrouperReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);

		}

	}

	public GraphextractionProcessorMessagesReceiverFactoryComponent(GeboGraphRagProcessorConfig config,
			IGRuntimeBinder runtimeBinder) {
		super(config.getGraphRagProcessorReceiverConfig());
		this.runtimeBinder = runtimeBinder;

	}

	/** Whether the knowledge graph is enabled on this installation. */
	@Value("${ai.gebo.neo4j.enabled:false}")
	private boolean neo4jEnabled;
	/** The graph store's bolt URI; sanitized before it reaches the register. */
	@Value("${spring.neo4j.uri:}")
	private String neo4jUri;
	/** The credential-guard username, reported by reference only, never the password. */
	@Value("${spring.neo4j.authentication.username:}")
	private String neo4jUsername;

	/**
	 * Reports the graph-extraction step: chunks are analysed for entities and
	 * relations, which are written to and retained in the Neo4j knowledge graph.
	 *
	 * <p>
	 * Reported only when {@code ai.gebo.neo4j.enabled} is true, so its presence in
	 * the register is itself the answer to "is graph extraction configured on this
	 * installation" - the same convention the full-text and vector reporters use.
	 * The graph is the third retaining store fed from the chunk cache, alongside the
	 * vector store and the full-text index, and until now it was the one such store
	 * invisible to the compliance register.
	 * </p>
	 */
	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		if (!neo4jEnabled) {
			return null;
		}
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		DataEndpoint graph = new DataEndpoint();
		graph.setId("knowledge-graph");
		graph.setDescription("Knowledge graph of entities and relations extracted from ingested chunks");
		graph.setProduct("Neo4j");
		// setEndpoint sanitizes: any userinfo in the bolt URI is stripped, and the
		// password is never carried - only the username, by reference, below.
		graph.setEndpoint(neo4jUri);
		graph.setTypes(new ArrayList<MetaEndpointType>(List.of(MetaEndpointType.GRAPH_DATABASE)));
		graph.setOutput(true);
		graph.setLocality(DataEndpointLocality.hintFromLocator(graph.getEndpoint()));
		// The graph is derived from the ingested content, so it carries whatever the
		// sources carried; personal-data scope is propagated from the source.
		graph.setPersonalData(false);
		graph.setRetention("Until the source is deleted or re-indexed");
		if (neo4jUsername != null && !neo4jUsername.isEmpty()) {
			graph.setSecretReference("spring.neo4j.authentication.username=" + neo4jUsername);
		}
		flow.getDataEndpoints().add(graph);

		DataTransformationMetaInfo extractor = DataTransformationMetaInfo.of("graph-extractor",
				"Extracts entities and relations for the knowledge graph", List.of(MetaEndpointType.CHUNK),
				List.of(MetaEndpointType.GRAPH_DATABASE));
		flow.getEngines().add(extractor);

		flow.getTransformations().add(DataTransformationInfo.of("graph-extraction",
				"Chunk text is analysed and its entities and relations written to the knowledge graph", extractor,
				GDataFlowMetaInfos.qualifiedId(
						new GeboComponentInfo(GStandardModulesConstraints.TOKENIZER_MODULE,
								GStandardModulesConstraints.TOKENIZER_COMPONENT),
						"chunk-cache"),
				flow.qualifiedId(graph.getId())));
		return flow;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName(),
				GContentsProcessingStatusUpdatePayload.class.getName());
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.KNOWLEDGE_GRAPH_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	@Override
	public IGTimedOutMessageReceiver create() {

		return new GraphRagProcessorBatchGrouperReceiver(
				runtimeBinder.getImplementationOf(GraphextractionProcessorBatchReceiver.class),
				factoryConfig.getFlushThreshold());
	}

}
