package ai.gebo.architecture.documents.cache.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.service.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GDocumentReferencePayload;

@Component

public class DocumentChunkingMessagesReceiverFactoryComponent extends GAbstractTimedOutMessageReceiverFactory
		implements IDocumentChunkingMessagesReceiverFactoryComponent {

	protected final IGRuntimeBinder runtimeBinder;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DocumentChunkingMessagesReceiverFactoryComponent.class);

	public class DocumentChunkingBatchGrouperReceiver extends GNestedBatchAggregatorMessageReceiver {

		public DocumentChunkingBatchGrouperReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);

		}

	}

	public DocumentChunkingMessagesReceiverFactoryComponent(GeboDocumentsCacheConfig config,
			IGRuntimeBinder runtimeBinder) {
		super(config.getDocumentChunkerReceiverConfig());
		this.runtimeBinder = runtimeBinder;

	}

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName());
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

		return new DocumentChunkingBatchGrouperReceiver(
				runtimeBinder.getImplementationOf(DocumentChunkingBatchReceiver.class),
				factoryConfig.getFlushThreshold());
	}

}
