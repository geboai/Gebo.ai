/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.scheduling.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.scheduling.repository.ProjectEndpointScheduledTaskRepository;

/**
 * tyr's central scheduler under microservices. tyr may run multiple
 * replicas, and has no existing clustering/leader-election primitive
 * (Hazelcast in this codebase is scoped only to the unrelated LLM
 * models-replication cache), so the tick only actually executes while this
 * instance holds {@link SchedulerLeaderLeaseService}'s Mongo-backed lease.
 *
 * <p>
 * {@code @ConditionalOnMicroservices} alone is not enough: this module isn't
 * exclusive to tyr's classpath (brain gets it transitively via
 * {@code gebo.core}), so {@link SchedulingAuthorityCondition} additionally
 * restricts instantiation to the one service actually meant to be the
 * central scheduler - every other microservice must not register its own
 * copy under the shared {@code scheduler-module.scheduler-component}
 * identity.
 * </p>
 */
@Component
@Scope("singleton")
@ConditionalOnMicroservices
@Conditional(SchedulingAuthorityCondition.class)
public class ClusteredCentralSchedulingService extends AbstractCentralSchedulingService {

	private final SchedulerLeaderLeaseService leaseService;

	public ClusteredCentralSchedulingService(TimesCalculatorService timesCalculationService,
			ProjectEndpointScheduledTaskRepository scheduledProjectEndpointRepo, IGMessageBroker broker,
			IMessageEnvelopeFactory envelopeFactory,
			@Value("${ai.gebo.scheduling.tick-page-size:200}") int tickPageSize,
			SchedulerLeaderLeaseService leaseService) {
		super(timesCalculationService, scheduledProjectEndpointRepo, broker, envelopeFactory, tickPageSize);
		this.leaseService = leaseService;
	}

	@Override
	protected boolean canRunTick() {
		return leaseService.tryAcquireOrRenew();
	}

}
