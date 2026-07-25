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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
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
 * {@link GAbstractReplicatorService#replicate}) and outside a deployment whose
 * routing config doesn't mention {@link GJobStatus} - so this is harmless to
 * have on every content-handler's classpath, including the monolith's.
 * </p>
 *
 * <p>
 * <b>Why the module id is a property, not a constant.</b>
 * {@link ai.gebo.microservices.topology.GeboMicroservicesTopology} requires
 * every messaging module to belong to exactly one microservice, so this bean -
 * identically present on every content-handler - cannot register under one
 * shared module id (nothing but the launching service's own already-owned
 * module, e.g. {@code git-module}, {@code shared-filesystem-module}, is valid
 * here). Each content-handler's own {@code application.yml} sets
 * {@code ai.gebo.jobs.replicator.module-id} to that same value declared for it
 * in {@code GeboStandardMicroservices}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@Scope("singleton")
public class GJobStatusReplicatorService extends GAbstractReplicatorService {

	private static final String JOB_STATUS_REPLICATOR = "job-status-replicator";

	private final IGRuntimeBinder runtimeBinder;

	public GJobStatusReplicatorService(
			@Value("${ai.gebo.jobs.replicator.module-id:" + GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_MODULE
					+ "}") String messagingModuleId,
			GeboApplicationArchitecture architecture, IGReplicationsMap replicationsMap,
			IMessageEnvelopeFactory envelopeFactory, IGRuntimeBinder runtimeBinder) {
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
				"GJobStatusReplicatorService cannot replicate an entity of type " + entity.getClass().getName());
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
