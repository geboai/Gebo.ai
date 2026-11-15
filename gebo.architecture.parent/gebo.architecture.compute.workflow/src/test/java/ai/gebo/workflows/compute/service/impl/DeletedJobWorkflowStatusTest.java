package ai.gebo.workflows.compute.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.IWorkflowStepEnabledHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.messages.ComputeWorkflowEndPayload;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.workflows.compute.service.AbstractWorkflowStatusHandler;

/**
 * A job can be deleted (with its endpoint or project, or from the jobs log)
 * while the contents it sent down the workflow are still being processed, so
 * their status updates keep arriving for a job that is gone. There is no status to compute
 * for it and nothing to finish: that must not fail.
 */
class DeletedJobWorkflowStatusTest {

	private static final String WORKFLOW_TYPE = GWorkflowType.STANDARD.name();
	private static final String WORKFLOW_ID = "INGESTION";

	@Test
	void aJobThatNoLongerExistsHasNoStatus() throws Exception {
		ContentsBatchProcessedRepository batches = mock(ContentsBatchProcessedRepository.class);
		IGPersistentObjectManager persistence = mock(IGPersistentObjectManager.class);
		when(persistence.findById(GJobStatus.class, "deleted-job")).thenReturn(null);
		AbstractWorkflowStatusHandler handler = new AbstractWorkflowStatusHandler(batches,
				mock(IWorkflowStepEnabledHandlerRepositoryPattern.class), persistence) {

			@Override
			protected ComputedWorkflowStructure workflowStructureImplementation(String workflowType,
					String workflowId, WorkflowContext context) {
				throw new AssertionError("there is no workflow to compute for a job that is gone");
			}

			@Override
			public String getId() {
				return "test";
			}

			@Override
			public GWorkflowType getWorkflowType() {
				return GWorkflowType.STANDARD;
			}

			@Override
			public boolean handleWorkflow(String workFlowId) {
				return true;
			}
		};

		assertNull(handler.computeWorkflowStatus("deleted-job", WORKFLOW_TYPE, WORKFLOW_ID));
		verifyNoInteractions(batches);
	}

	@Test
	void theEndOfWorkflowCheckOfADeletedJobBroadcastsNothing() {
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		IWorkflowStatusHandlerRepositoryPattern handlers = mock(IWorkflowStatusHandlerRepositoryPattern.class);
		IWorkflowStatusHandler handler = mock(IWorkflowStatusHandler.class);
		when(binder.getImplementationOf(IWorkflowStatusHandlerRepositoryPattern.class)).thenReturn(handlers);
		when(handlers.findByWorkflowsTypeAndWorkflowId(WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(List.of(handler));
		// the handler finds no job: no status
		when(handler.computeWorkflowStatus("deleted-job", WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(null);
		IMessageEnvelopeFactory envelopes = mock(IMessageEnvelopeFactory.class);
		GComputeEndOfWorkflowReceiverFactory factory = new GComputeEndOfWorkflowReceiverFactory(binder, envelopes);

		ComputeWorkflowEndPayload payload = new ComputeWorkflowEndPayload();
		payload.setJobId("deleted-job");
		payload.setWorkflowType(WORKFLOW_TYPE);
		payload.setWorkflowId(WORKFLOW_ID);
		GMessageEnvelope<ComputeWorkflowEndPayload> envelope = new GMessageEnvelope<ComputeWorkflowEndPayload>();
		envelope.setPayload(payload);

		assertDoesNotThrow(() -> factory.create().accept(envelope));
		verify(binder, never()).getImplementationOf(IGMessageBroker.class);
		verifyNoInteractions(envelopes);
	}

	@Test
	void aJobDeletedWhileProcessingDoesNotStopTheOthersFromBeingClosed() throws Exception {
		IGPersistentObjectManager persistence = mock(IGPersistentObjectManager.class);
		JobStatusRepository jobs = mock(JobStatusRepository.class);
		WorkflowStatusEmitter emitter = mock(WorkflowStatusEmitter.class);
		IWorkflowStatusHandlerRepositoryPattern handlers = mock(IWorkflowStatusHandlerRepositoryPattern.class);
		IWorkflowStatusHandler handler = mock(IWorkflowStatusHandler.class);
		when(handlers.findByWorkflowsTypeAndWorkflowId(GWorkflowType.STANDARD, WORKFLOW_ID))
				.thenReturn(List.of(handler));
		// listed as processing, deleted before its status is computed
		GJobStatusItem deleted = processing("deleted-job");
		when(handler.computeWorkflowStatus("deleted-job", WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(null);
		// finished, waiting to be closed
		GJobStatusItem finished = processing("finished-job");
		ComputedWorkflowStatus root = new ComputedWorkflowStatus();
		root.setBatchDocumentsInput(1);
		ComputedWorkflowResult done = new ComputedWorkflowResult();
		done.setFinished(true);
		done.setRootStatus(root);
		when(handler.computeWorkflowStatus("finished-job", WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(done);
		GJobStatus finishedJob = new GJobStatus();
		finishedJob.setCode("finished-job");
		finishedJob.setProcessing(true);
		when(persistence.findById(GJobStatus.class, "finished-job")).thenReturn(finishedJob);
		when(persistence.update(any(GJobStatus.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(jobs.findByProcessingTrue()).thenReturn(Stream.of(deleted, finished));

		new GWorkflowStatusDeamonServiceImpl(persistence, jobs, emitter, handlers).checkJobsStatus();

		verify(persistence).update(finishedJob);
		verify(emitter).broadcastEnded(finishedJob);
		verify(persistence, never()).findById(GJobStatus.class, "deleted-job");
	}

	private static GJobStatusItem processing(String code) {
		GJobStatusItem item = mock(GJobStatusItem.class);
		when(item.getCode()).thenReturn(code);
		when(item.getStartDateTime()).thenReturn(new Date());
		when(item.getWorkflowType()).thenReturn(WORKFLOW_TYPE);
		when(item.getWorkflowId()).thenReturn(WORKFLOW_ID);
		return item;
	}
}
