package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IChunkingMessagingComponent;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ChunkingMessagingComponentImpl implements IChunkingMessagingComponent {
	private final IDocumentsChunkService chunkingService;
	private final IChunkingParametersProvider parameterProvider;
	private final IWorkflowRouter workflowRouter;
	private final static Logger LOGGER = LoggerFactory.getLogger(ChunkingMessagingComponentImpl.class);

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
	public void accept(GMessageEnvelope envelope) {
		if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
			ChunkingParams params = parameterProvider.provideChunkingParams(payload.getDocumentReference());
			boolean processed = false;
			try {
				processed = this.chunkingService.prepareChunks(payload.getDocumentReference(), params.getSpecs(),
						params.isEnrichWithMetaData());
				if (processed) {
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), payload, this);
				}
			} catch (Throwable e) {
				LOGGER.error("Fail to prepare & deliver chunks =>" + processed, e);
			}
		}
	}

}
