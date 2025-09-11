package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
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

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName(),
				GContentsProcessingStatusUpdatePayload.class.getName());
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.TOKENIZER_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.TOKENIZER_COMPONENT;
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
