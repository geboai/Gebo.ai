package ai.gebo.workflows.compute.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.ComputeWorkflowEndPayload;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;

/**
 * A job is closed, and its end broadcast, once: by the end of workflow receiver
 * as soon as its workflow computes finished, and never again by the status
 * daemon, which used to close it on its next round and broadcast its end a
 * second time.
 */
class JobEndBroadcastOnceTest {

	private static final String WORKFLOW_TYPE = GWorkflowType.STANDARD.name();
	private static final String WORKFLOW_ID = "INGESTION";
	private static final String JOB = "a-job";

	@Test
	void theCloserClosesOnlyAJobStillProcessing() {
		MongoTemplate mongo = mock(MongoTemplate.class);
		WorkflowStatusEmitter emitter = mock(WorkflowStatusEmitter.class);
		GJobStatus closed = job();
		when(mongo.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
				eq(GJobStatus.class))).thenReturn(closed, (GJobStatus) null);
		GWorkflowJobCloser closer = new GWorkflowJobCloser(mongo, emitter);

		assertTrue(closer.closeAndBroadcastEnded(JOB), "the job still processing is closed");
		assertFalse(closer.closeAndBroadcastEnded(JOB), "a job already closed is not closed again");

		verify(emitter, times(1)).broadcastEnded(closed);
		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> update = ArgumentCaptor.forClass(Update.class);
		verify(mongo, times(2)).findAndModify(query.capture(), update.capture(), any(FindAndModifyOptions.class),
				eq(GJobStatus.class));
		Document matched = query.getValue().getQueryObject();
		assertEquals(JOB, matched.get("_id"), "the job is matched by its code");
		assertEquals(Boolean.TRUE, matched.get("processing"), "only while it is still processing");
		Document set = (Document) update.getValue().getUpdateObject().get("$set");
		assertEquals(Boolean.FALSE, set.get("processing"));
		assertEquals(Boolean.TRUE, set.get("finished"));
		assertTrue(set.get("endDateTime") instanceof Date, "the job gets its end date");
	}

	@Test
	void aJobClosedAtTheEndOfItsWorkflowIsNotBroadcastAgainByTheDaemon() {
		MongoTemplate mongo = mongoClosingOnce();
		WorkflowStatusEmitter emitter = mock(WorkflowStatusEmitter.class);
		GWorkflowJobCloser closer = new GWorkflowJobCloser(mongo, emitter);
		IWorkflowStatusHandlerRepositoryPattern handlers = mock(IWorkflowStatusHandlerRepositoryPattern.class);
		IWorkflowStatusHandler handler = mock(IWorkflowStatusHandler.class);
		when(handlers.findByWorkflowsTypeAndWorkflowId(WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(List.of(handler));
		when(handlers.findByWorkflowsTypeAndWorkflowId(GWorkflowType.STANDARD, WORKFLOW_ID))
				.thenReturn(List.of(handler));
		when(handler.computeWorkflowStatus(JOB, WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(finished());
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IWorkflowStatusHandlerRepositoryPattern.class)).thenReturn(handlers);
		when(binder.getImplementationOf(GWorkflowJobCloser.class)).thenReturn(closer);
		// the daemon listed the job as processing before the receiver closed it
		JobStatusRepository jobs = mock(JobStatusRepository.class);
		GJobStatusItem listed = mock(GJobStatusItem.class);
		when(listed.getCode()).thenReturn(JOB);
		when(listed.getStartDateTime()).thenReturn(new Date());
		when(listed.getWorkflowType()).thenReturn(WORKFLOW_TYPE);
		when(listed.getWorkflowId()).thenReturn(WORKFLOW_ID);
		when(jobs.findByProcessingTrue()).thenReturn(Stream.of(listed));

		new GComputeEndOfWorkflowReceiverFactory(binder, mock(IMessageEnvelopeFactory.class)).create()
				.accept(endOfWorkflow());
		new GWorkflowStatusDeamonServiceImpl(jobs, closer, handlers).checkJobsStatus();

		verify(emitter, times(1)).broadcastEnded(any(GJobStatus.class));
	}

	@Test
	void theDaemonClosesAJobPastItsTimeoutOnce() {
		MongoTemplate mongo = mongoClosingOnce();
		WorkflowStatusEmitter emitter = mock(WorkflowStatusEmitter.class);
		GWorkflowJobCloser closer = new GWorkflowJobCloser(mongo, emitter);
		IWorkflowStatusHandlerRepositoryPattern handlers = mock(IWorkflowStatusHandlerRepositoryPattern.class);
		IWorkflowStatusHandler handler = mock(IWorkflowStatusHandler.class);
		when(handlers.findByWorkflowsTypeAndWorkflowId(GWorkflowType.STANDARD, WORKFLOW_ID))
				.thenReturn(List.of(handler));
		// a job that read nothing is not finished: only its timeout closes it
		ComputedWorkflowResult notFinished = new ComputedWorkflowResult();
		notFinished.setFinished(false);
		notFinished.setRootStatus(new ComputedWorkflowStatus());
		when(handler.computeWorkflowStatus(JOB, WORKFLOW_TYPE, WORKFLOW_ID)).thenReturn(notFinished);
		JobStatusRepository jobs = mock(JobStatusRepository.class);
		GJobStatusItem late = mock(GJobStatusItem.class);
		when(late.getCode()).thenReturn(JOB);
		when(late.getStartDateTime())
				.thenReturn(new Date(System.currentTimeMillis() - GWorkflowStatusDeamonServiceImpl.EXECUTION_TIMEOUT - 1));
		when(late.getWorkflowType()).thenReturn(WORKFLOW_TYPE);
		when(late.getWorkflowId()).thenReturn(WORKFLOW_ID);
		when(jobs.findByProcessingTrue()).thenReturn(Stream.of(late), Stream.of(late));
		GWorkflowStatusDeamonServiceImpl daemon = new GWorkflowStatusDeamonServiceImpl(jobs, closer, handlers);

		daemon.checkJobsStatus();
		daemon.checkJobsStatus();

		verify(emitter, times(1)).broadcastEnded(any(GJobStatus.class));
	}

	@Test
	void aJobGoneIsNotBroadcast() {
		MongoTemplate mongo = mock(MongoTemplate.class);
		WorkflowStatusEmitter emitter = mock(WorkflowStatusEmitter.class);
		// no document matches: the job was deleted
		GWorkflowJobCloser closer = new GWorkflowJobCloser(mongo, emitter);

		assertFalse(closer.closeAndBroadcastEnded("deleted-job"));
		verify(emitter, never()).broadcastEnded(any(GJobStatus.class));
	}

	/**
	 * A MongoTemplate whose job matches the "still processing" update only the
	 * first time, as the real conditional update does.
	 */
	private static MongoTemplate mongoClosingOnce() {
		MongoTemplate mongo = mock(MongoTemplate.class);
		AtomicBoolean processing = new AtomicBoolean(true);
		when(mongo.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
				eq(GJobStatus.class))).thenAnswer(call -> processing.getAndSet(false) ? job() : null);
		return mongo;
	}

	private static GJobStatus job() {
		GJobStatus job = new GJobStatus();
		job.setCode(JOB);
		job.setWorkflowType(WORKFLOW_TYPE);
		job.setWorkflowId(WORKFLOW_ID);
		job.setProcessing(false);
		job.setFinished(true);
		return job;
	}

	private static ComputedWorkflowResult finished() {
		ComputedWorkflowStatus root = new ComputedWorkflowStatus();
		root.setBatchDocumentsInput(1);
		ComputedWorkflowResult done = new ComputedWorkflowResult();
		done.setFinished(true);
		done.setRootStatus(root);
		return done;
	}

	private static GMessageEnvelope<ComputeWorkflowEndPayload> endOfWorkflow() {
		ComputeWorkflowEndPayload payload = new ComputeWorkflowEndPayload();
		payload.setJobId(JOB);
		payload.setWorkflowType(WORKFLOW_TYPE);
		payload.setWorkflowId(WORKFLOW_ID);
		GMessageEnvelope<ComputeWorkflowEndPayload> envelope = new GMessageEnvelope<ComputeWorkflowEndPayload>();
		envelope.setPayload(payload);
		return envelope;
	}
}
