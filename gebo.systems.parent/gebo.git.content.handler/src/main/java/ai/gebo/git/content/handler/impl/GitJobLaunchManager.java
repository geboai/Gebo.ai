/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.git.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.impl.AbstractJobLaunchManager;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;

/**
 * The Git microservice's own instance of the job-launch receiver,
 * registered under `Git`'s own already-owned
 * {@link GStandardModulesConstraints#GIT_MODULE}, so it is uniquely
 * addressable under the microservices topology instead of colliding with
 * every other content handler's copy under the shared monolithic constant.
 */
@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class GitJobLaunchManager extends AbstractJobLaunchManager {

	public GitJobLaunchManager(IGGeboIngestionJobQueueService jobLauncherController,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker broker,
			JobStatusRepository jobStatusRepositoy) {
		super(jobLauncherController, persistentObjectManager, broker, jobStatusRepositoy,
				GStandardModulesConstraints.GIT_MODULE);
	}
}
