/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.impl.AbstractJobLaunchManager;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GObjectRef;

/**
 * Pins that publishing a data source through the REST controllers launches the
 * ingestion with no session parameter, as the job launcher and the scheduler do.
 * A {@code NoContentConsumingSessionParam} there made every handler typed on
 * {@code RemoteVirtualFileSystemContentConsumingSessionParam} (uploads, filesystem,
 * the remote virtual filesystems) fail the ingestion with a ClassCastException.
 */
class PublishSessionParamTest {

	private static GProjectEndpoint endpoint() {
		GProjectEndpoint endpoint = new GProjectEndpoint();
		endpoint.setCode("source");
		endpoint.setParentProjectCode("project");
		return endpoint;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void aDataSourcePublishedFromItsControllerIsReadWithNoSessionParameter() throws Exception {
		IGPersistentObjectManager persistence = mock(IGPersistentObjectManager.class);
		IGGeboIngestionJobQueueService jobs = mock(IGGeboIngestionJobQueueService.class);
		GProjectEndpoint endpoint = endpoint();
		when(persistence.update(endpoint)).thenReturn(endpoint);
		GAbstractSystemsArchitectureController<GContentManagementSystem, GProjectEndpoint> controller = new GAbstractSystemsArchitectureController<>(
				persistence, mock(IGMessageBroker.class), null, null, null, jobs, null);
		controller.envelopeFactory = mock(IMessageEnvelopeFactory.class);
		when(controller.envelopeFactory.newMessageFrom(any(), any())).thenAnswer(i -> new GMessageEnvelope());
		controller.jobLaunchManager = mock(AbstractJobLaunchManager.class);

		OperationStatus status = controller.publish(endpoint);

		assertFalse(status.isHasErrorMessages());
		verify(jobs).createNewAsyncJob(eq(endpoint), isNull(), eq(GWorkflowType.STANDARD.name()),
				eq(GStandardWorkflow.INGESTION.name()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void aCentralizedPublishIsReadWithNoSessionParameter() throws Exception {
		IGPersistentObjectManager persistence = mock(IGPersistentObjectManager.class);
		IGGeboIngestionJobQueueService jobs = mock(IGGeboIngestionJobQueueService.class);
		GProjectEndpoint endpoint = endpoint();
		when(persistence.findByReference(any(GObjectRef.class), eq(GProjectEndpoint.class))).thenReturn(endpoint);

		OperationStatus status = new GenericalPublisherController(jobs, persistence)
				.publishCentralizedEndpoint(GCentralizedProjectEndpoint.of(endpoint));

		assertFalse(status.isHasErrorMessages());
		verify(jobs).createNewAsyncJob(eq(endpoint), isNull(), eq(GWorkflowType.STANDARD.name()),
				eq(GStandardWorkflow.INGESTION.name()));
	}
}
