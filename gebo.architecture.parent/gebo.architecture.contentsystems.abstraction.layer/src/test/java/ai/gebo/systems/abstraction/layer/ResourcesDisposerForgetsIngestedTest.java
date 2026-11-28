/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;

/**
 * Pins that deleting a data source, alone or with its project, forgets the
 * ingestion acknowledgements of its contents, and that a failure to forget them
 * does not stop the deletion.
 */
class ResourcesDisposerForgetsIngestedTest {
	private IGPersistentObjectManager persistence;
	private IGBaseMongoDBProjectEndpointRepository<GProjectEndpoint> endpoints;
	private IGContentDispatchingEvaluator evaluator;
	private GAbstractResourcesDisposerFactory<GProjectEndpoint> factory;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
		persistence = mock(IGPersistentObjectManager.class);
		endpoints = mock(IGBaseMongoDBProjectEndpointRepository.class);
		evaluator = mock(IGContentDispatchingEvaluator.class);
		factory = new GAbstractResourcesDisposerFactory<GProjectEndpoint>(
				mock(IGLocalPersistentFolderDiscoveryService.class), mock(IGContentManagementSystemHandler.class),
				endpoints, mock(JobStatusRepository.class), persistence) {

			@Override
			protected boolean isCanBeDisposedResources(GProjectEndpoint endpoint) {
				return false;
			}

			@Override
			protected void disposeResources(GProjectEndpoint endpoint, String contentManagementSystemCode) {
			}

			@Override
			public String getMessagingModuleId() {
				return "test-module";
			}
		};
		factory.ingestionAcknowledgements = evaluator;
	}

	private static GProjectEndpoint endpoint(String project, String code) {
		final GProjectEndpoint endpoint = new GProjectEndpoint();
		endpoint.setParentProjectCode(project);
		endpoint.setCode(code);
		return endpoint;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void deliver(ai.gebo.application.messaging.IGMessagePayloadType payload) {
		final GMessageEnvelope envelope = new GMessageEnvelope();
		envelope.setPayload(payload);
		factory.create().accept(envelope);
	}

	private GDeletedProjectEndpointPayload deletedDataSource(GProjectEndpoint concrete) throws Exception {
		final GCentralizedProjectEndpoint centralized = GCentralizedProjectEndpoint.of(concrete);
		centralized.setRemoteProjectReference(GObjectRef.of(concrete));
		when(persistence.findByReference(any(), any())).thenReturn(concrete);
		return new GDeletedProjectEndpointPayload(centralized);
	}

	@Test
	void aDeletedDataSourceForgetsItsContents() throws Exception {
		final GProjectEndpoint concrete = endpoint("pj", "pj-docs");
		deliver(deletedDataSource(concrete));

		verify(evaluator).forgetIngestedOfDataSource("pj", "pj-docs");
		verify(persistence).delete(concrete);
	}

	@Test
	void aDeletedProjectForgetsTheContentsOfEachOfItsDataSources() {
		when(endpoints.findByParentProjectCode("pj"))
				.thenReturn(List.of(endpoint("pj", "pj-docs"), endpoint("pj", "pj-wiki")));
		final GProject project = new GProject();
		project.setCode("pj");
		final GDeletedProjectPayload payload = new GDeletedProjectPayload();
		payload.setProject(project);
		deliver(payload);

		verify(evaluator).forgetIngestedOfDataSource("pj", "pj-docs");
		verify(evaluator).forgetIngestedOfDataSource("pj", "pj-wiki");
		verify(endpoints).deleteByParentProjectCode("pj");
	}

	@Test
	void aFailureToForgetDoesNotStopTheDeletion() throws Exception {
		when(evaluator.forgetIngestedOfDataSource("pj", "pj-docs")).thenThrow(new IllegalStateException("down"));
		final GProjectEndpoint concrete = endpoint("pj", "pj-docs");
		deliver(deletedDataSource(concrete));

		verify(persistence).delete(concrete);
	}
}
