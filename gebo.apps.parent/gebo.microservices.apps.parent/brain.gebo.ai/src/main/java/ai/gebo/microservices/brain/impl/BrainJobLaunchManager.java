/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.brain.impl;

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
 * Brain's own instance of the job-launch receiver.
 *
 * <p>
 * Brain isn't a content handler - it never owns a concrete
 * {@code GProjectEndpoint} subtype or extends
 * {@code GAbstractSystemsArchitectureController} - but {@code gebo.core}
 * (added to brain's dependencies so it can host the KB/project admin
 * controllers in a microservices deployment) pulls
 * {@code gebo.architecture.contentsystems.abstraction.layer} onto brain's
 * classpath transitively, and with it {@code GGeboIngestionJobQueueServiceImpl},
 * which requires a concrete {@link AbstractJobLaunchManager} bean to satisfy
 * its constructor regardless of whether brain ever actually receives a
 * publish for an endpoint of its own. Registered under brain's own
 * already-owned {@link GStandardModulesConstraints#BRAIN_MODULE}, exactly
 * like each content handler's own subclass.
 */
@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class BrainJobLaunchManager extends AbstractJobLaunchManager {

	public BrainJobLaunchManager(IGGeboIngestionJobQueueService jobLauncherController,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker broker,
			JobStatusRepository jobStatusRepositoy) {
		super(jobLauncherController, persistentObjectManager, broker, jobStatusRepositoy,
				GStandardModulesConstraints.BRAIN_MODULE);
	}
}
