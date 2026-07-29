/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.jobs.services.impl;

import java.util.List;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.replicator.model.IGReplicationsMap;
import ai.gebo.architecture.replicator.service.GAbstractReplicatorService;
import ai.gebo.core.messages.GAbstractEntityReplicationPayload;
import ai.gebo.core.replication.messages.GJobStatusReplicationPayload;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.model.base.GBaseObject;

/**
 * Replicates a {@link GJobStatus} to whichever service(s)
 * {@code gebo.replicator.routing} configures for it - concretely, tyr, whose
 * {@code JobStatusController} otherwise reads from its own, separate Mongo and
 * never sees a job created on the launching content-handler microservice.
 *
 * <p>
 * A no-op outside {@code ArchitectureType.MICROSERVICES} (see
 * {@link GAbstractReplicatorService#replicate}) - so this is harmless to have
 * on every content-handler's classpath, including the monolith's.
 * </p>
 *
 * <p>
 * {@code messagingSystemId} is fixed ({@code job-status-replicator}) while
 * {@code messagingModuleId} is assignable per concrete subclass - mirroring
 * {@link AbstractJobLaunchManager}/{@link AbstractJobStatusEmitter}:
 * {@link ai.gebo.microservices.topology.GeboMicroservicesTopology} requires
 * every messaging module to belong to exactly one microservice, so this bean
 * cannot register identically on every content-handler under one shared
 * module id. The monolith registers one instance under the shared
 * {@code async-publishing-job-module} constant, while under microservices
 * each content-handler registers its own instance under its own
 * already-owned module id (e.g. {@code git-module}, {@code shared-filesystem-module}).
 * </p>
 *
 * Gebo.ai comment agent
 */
public abstract class AbstractJobStatusReplicatorService extends GAbstractReplicatorService {

	private static final String JOB_STATUS_REPLICATOR = "job-status-replicator";

	private final IGRuntimeBinder runtimeBinder;

	protected AbstractJobStatusReplicatorService(String messagingModuleId, GeboApplicationArchitecture architecture,
			IGReplicationsMap replicationsMap, IMessageEnvelopeFactory envelopeFactory, IGRuntimeBinder runtimeBinder) {
		super(messagingModuleId, JOB_STATUS_REPLICATOR, architecture, replicationsMap, envelopeFactory);
		this.runtimeBinder = runtimeBinder;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(GJobStatusReplicationPayload.class.getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <EntityType extends GBaseObject> GAbstractEntityReplicationPayload<EntityType> createPayload(
			EntityType entity) {
		if (entity instanceof GJobStatus jobStatus) {
			GJobStatusReplicationPayload payload = new GJobStatusReplicationPayload();
			payload.setEntity(jobStatus);
			return (GAbstractEntityReplicationPayload<EntityType>) payload;
		}
		throw new IllegalArgumentException(
				getClass().getSimpleName() + " cannot replicate an entity of type " + entity.getClass().getName());
	}

	@Override
	protected void emit(GMessageEnvelope<?> envelope) {
		// Targeted delivery: the envelope already carries the specific
		// target module/component gebo.replicator.routing resolved for GJobStatus
		// (set by the caller, GAbstractReplicatorService#replicate). accept() routes
		// to that one addressee; broadcast() would fan out to every receiver
		// interested in the payload type, which is not what a replica is.
		runtimeBinder.getImplementationOf(IGMessageBroker.class).accept(envelope);
	}
}
