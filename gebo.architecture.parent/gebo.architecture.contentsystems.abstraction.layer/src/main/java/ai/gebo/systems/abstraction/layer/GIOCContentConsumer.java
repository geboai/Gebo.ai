package ai.gebo.systems.abstraction.layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload.ObjectType;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload.MessageFragmentType;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GDocumentReferenceSnapshot;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator.SendEvaluationPolicy;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory.EnricherMappers;

/********************************************************************************************************************
 * Consumer that implements the IOC on the infrastructure via content system
 * handler streaming
 * 
 * @param <SystemIntegrationType>
 * @param <ProjectEndpointType>
 */
class GIOCContentConsumer<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
		implements IGContentConsumer {
	GIOCContentConsumer(EnricherMappers enrich, GJobStatus jobStatus, IGContentDispatchingEvaluator evaluator,
			IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler,
			ProjectEndpointType endpoint, IWorkflowRouter workflowRouter,
			GIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> dispatcher,
			IGUserMessagesConsumer userMessagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			IGContentConsumer documentConsumer, IGMessageBroker broker,
			DocumentReferenceRepository documentReferenceRepository, VirtualFolderRepository virtualFolderRepository) {
		this.enrichers = enrich;
		this.jobStatus = jobStatus;
		this.evaluator = evaluator;
		this.handler = handler;
		this.endpoint = endpoint;
		this.workflowRouter = workflowRouter;
		this.dispatcher = dispatcher;
		this.userMessagesConsumer = userMessagesConsumer;
		this.errorConsumer = errorConsumer;
		this.documentConsumer = documentConsumer;
		this.broker = broker;
		this.documentReferenceRepository = documentReferenceRepository;
		this.virtualFolderRepository = virtualFolderRepository;
	}

	static private final long NBATCH_DOCS_STATSMESSAGE = 50;
	private final GIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> dispatcher;
	private final IWorkflowRouter workflowRouter;
	private final IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler;
	private final SendEvaluationPolicy evaluationPolicy = SendEvaluationPolicy.TIMESTAMP_HASH_POLICY;
	private final IGContentDispatchingEvaluator evaluator;
	private final ProjectEndpointType endpoint;
	private final EnricherMappers enrichers;
	private final GJobStatus jobStatus;
	private final Map<String, Integer> docsCounters = new HashMap<String, Integer>();
	private final Map<String, Object> handlerCache = new HashMap<String, Object>();
	private final IGContentsAccessErrorConsumer errorConsumer;
	private final IGUserMessagesConsumer userMessagesConsumer;
	private final IGContentConsumer documentConsumer;
	private final IGMessageBroker broker;
	private final DocumentReferenceRepository documentReferenceRepository;
	private final VirtualFolderRepository virtualFolderRepository;
	private long batchDocumentInput = 0l, batchSentToNextStep = 0l, batchDocumentsProcessingErrors = 0l,
			batchDocumentsProcessed = 0l;
	protected static final Logger LOGGER = LoggerFactory.getLogger(GIOCContentConsumer.class);

	@Override
	public void accept(GBaseVersionableObject t) {
		enrichers.getContentItemMapper().apply(t);
		if (t != null && t instanceof GAbstractVirtualFilesystemObject) {
			GAbstractVirtualFilesystemObject jobObject = (GAbstractVirtualFilesystemObject) t;
			jobObject.setLastesJobId(jobStatus.getCode());
		}
		if (t instanceof GDocumentReference) {
			GDocumentReference docref = (GDocumentReference) t;

			if (docref.getCode() != null) {
				if (!docsCounters.containsKey(docref.getCode())) {
					docsCounters.put(docref.getCode(), 1);
				} else {
					Integer count = docsCounters.get(docref.getCode());
					count = count.intValue() + 1;
					docsCounters.put(docref.getCode(), count);
					LOGGER.warn("Duplicated code:" + docref.getCode() + " count=" + count);
				}
			}

			// skipped vectorization contents are not sent to vectorization or indexing
			if (docref.getSkippedVectorizationContent() == null || !docref.getSkippedVectorizationContent()) {
				try {

					boolean forwardIngestionHandling = evaluator.isProcessable(evaluationPolicy, docref, handler,
							handlerCache);
					if (forwardIngestionHandling) {
						batchDocumentsProcessed++;
						final GDocumentReferenceSnapshot snapshot = new GDocumentReferenceSnapshot();
						snapshot.setCode(docref.getCode());
						snapshot.setModifiedDate(docref.getModificationDate());
						snapshot.setTokensCount(0);
						snapshot.setFileSize(docref.getFileSize());
						GDocumentReferencePayload payload = new GDocumentReferencePayload();
						payload.setDocumentReference(docref);
						payload.setEndPoint(endpoint);
						payload.setFragmentType(MessageFragmentType.SINGLE_FRAGMENT);
						payload.setRequiresEmbeddingHandshake(true);
						payload.setJobId(jobStatus.getCode());
						enrichers.getContentPayloadMapper().apply(payload);
						workflowRouter.routeToNextSteps(GWorkflowType.STANDARD, GStandardWorkflow.INGESTION.name(),
								GStandardWorkflowStep.DOCUMENT_DISCOVERY.name(), payload, dispatcher);
						batchSentToNextStep++;
					}

				} catch (Throwable throwable) {
					batchDocumentsProcessingErrors++;
					LOGGER.error("Error handling document:" + t.getCode(), throwable);
					userMessagesConsumer.accept(
							GUserMessage.errorMessage("Content: " + docref.getName() + " handling error", throwable));
				}
			}
		}
		try {
			documentConsumer.accept(t);
			if (t instanceof GDocumentReference) {
				batchDocumentInput++;
			}
		} catch (Throwable throwable) {
			LOGGER.error("Error handling standard consumer for element:" + t.getCode(), throwable);
		}
		if (batchSentToNextStep > NBATCH_DOCS_STATSMESSAGE) {
			GContentsProcessingStatusUpdatePayload payload = new GContentsProcessingStatusUpdatePayload();
			payload.setJobId(jobStatus.getCode());
			payload.setWorkflowType(GWorkflowType.STANDARD.name());
			payload.setWorkflowId(GStandardWorkflow.INGESTION.name());
			payload.setWorkflowStepId(GStandardWorkflowStep.DOCUMENT_DISCOVERY.name());
			payload.setBatchDocumentsInput(batchDocumentInput);
			payload.setBatchDocumentsProcessingErrors(batchDocumentsProcessingErrors);
			payload.setBatchDocumentsProcessed(batchDocumentsProcessed);
			payload.setBatchSentToNextStep(batchSentToNextStep);

			payload.setLastMessage(false);
			payload.setTimestamp(new Date());
			GMessageEnvelope<GContentsProcessingStatusUpdatePayload> cmessage = GMessageEnvelope
					.newMessageFrom(this.dispatcher, payload);
			cmessage.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			cmessage.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
			cmessage.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
			batchDocumentInput = 0l;
			batchSentToNextStep = 0l;
			batchDocumentsProcessingErrors = 0l;
			batchDocumentsProcessed = 0l;
			broker.accept(cmessage);
		}
	}

	@Override
	public void endConsuming() {
		documentConsumer.endConsuming();
		if (!errorConsumer.hasErrors()) {
			try {
				Stream<GDocumentReference> documentsToCheckStream = documentReferenceRepository
						.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
								endpoint.getClass().getName(), endpoint.getCode(), jobStatus.getCode());
				Stream<GVirtualFolder> foltersToCheckStream = virtualFolderRepository
						.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
								endpoint.getClass().getName(), endpoint.getCode(), jobStatus.getCode());
				Stream<GAbstractVirtualFilesystemObject> documents = documentsToCheckStream.map(x -> x);
				Stream<GAbstractVirtualFilesystemObject> folders = foltersToCheckStream.map(x -> x);
				Stream<GAbstractVirtualFilesystemObject> updated = handler.checkUpdatedOrDeleted(endpoint,
						Stream.concat(documents, folders), errorConsumer);
				final Map<String, Boolean> deletedDocsCodes = new HashMap<String, Boolean>();
				updated.forEach((x) -> {
					x.setLastesJobId(jobStatus.getCode());
					x.setDateModified(new Date());
					if (x.getDeleted() == null || !x.getDeleted()) {
						accept(x);
					} else if (x instanceof GDocumentReference) {
						GDocumentReference doc = (GDocumentReference) x;
						deletedDocsCodes.put(doc.getCode(), true);
						documentReferenceRepository.save(doc);

					} else {
						accept(x);
					}
				});
				if (!deletedDocsCodes.isEmpty()) {
					GInternalDeletionMessagePayload deletedDocumentPayload = new GInternalDeletionMessagePayload();
					deletedDocumentPayload.setCodes4deletion(new ArrayList<String>(deletedDocsCodes.keySet()));
					deletedDocumentPayload.setObjectsType(ObjectType.DOCUMENTREF);
					GMessageEnvelope<GInternalDeletionMessagePayload> envelope = GMessageEnvelope
							.newMessageFrom(this.dispatcher, deletedDocumentPayload);
					envelope.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
					envelope.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT);
					broker.accept(envelope);
				}
			} catch (Throwable th) {
				LOGGER.error("Error in deletions check", th);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			// Debug logging of received document information for the dispatcher
			LOGGER.debug("CONTENT-HANDLER-RECEIVED-DOCUMENTS:" + docsCounters.keySet());
		}
		GContentsProcessingStatusUpdatePayload payload = new GContentsProcessingStatusUpdatePayload();
		payload.setJobId(jobStatus.getCode());
		payload.setWorkflowType(GWorkflowType.STANDARD.name());
		payload.setWorkflowId(GStandardWorkflow.INGESTION.name());
		payload.setWorkflowStepId(GStandardWorkflowStep.DOCUMENT_DISCOVERY.name());
		payload.setBatchDocumentsInput(batchDocumentInput);
		payload.setBatchDocumentsProcessingErrors(batchDocumentsProcessingErrors);
		payload.setBatchDocumentsProcessed(batchDocumentsProcessed);
		payload.setBatchSentToNextStep(batchSentToNextStep);
		payload.setLastMessage(true);
		payload.setTimestamp(new Date());
		GMessageEnvelope<GContentsProcessingStatusUpdatePayload> cmessage = GMessageEnvelope
				.newMessageFrom(this.dispatcher, payload);
		cmessage.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
		cmessage.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
		cmessage.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
		broker.accept(cmessage);
	}

}
