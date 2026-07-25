/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.workflows.compute.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.replicator.service.GAbstractReplicatorReceiverService;

/**
 * Receives a replicated {@link ai.gebo.knlowledgebase.model.jobs.GJobStatus}
 * (sent by {@code GJobStatusReplicatorService} on the launching content-handler
 * microservice) and upserts it into THIS service's own Mongo, via
 * {@link GAbstractReplicatorReceiverService#accept}, which already implements
 * the full find-then-insert-or-update logic generically - nothing to add here
 * beyond this receiver's own addressable identity.
 *
 * <p>
 * Hosted here, alongside {@link WorkflowStatusEmitter}/
 * {@link GComputeEndOfWorkflowReceiverFactory}: tyr is the microservice that
 * exposes {@code JobStatusController}, and this is what makes the jobs it
 * reports on visible in the first place.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@Scope("singleton")
public class GJobStatusReplicatorReceiverService extends GAbstractReplicatorReceiverService {

	public GJobStatusReplicatorReceiverService(GeboApplicationArchitecture architecture,
			IGPersistentObjectManager persistentObjectManager) {
		// async-publishing-job-module, not workflow-status-module: it's the one tyr
		// already declares in GeboStandardMicroservices' topology (alongside
		// job-status-notifier), which is what makes this receiver discoverable by
		// RabbitMqExternalMessageReceiverProviderSource on every OTHER microservice.
		super(GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_MODULE,
				GStandardModulesConstraints.JOB_STATUS_REPLICATION_RECEIVER, architecture, persistentObjectManager);
	}
}
