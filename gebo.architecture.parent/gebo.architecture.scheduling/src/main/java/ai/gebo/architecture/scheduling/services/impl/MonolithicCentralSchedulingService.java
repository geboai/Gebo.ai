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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.architecture.environment.conditional.ConditionalOnMonolithic;
import ai.gebo.architecture.scheduling.repository.ProjectEndpointScheduledTaskRepository;

/**
 * The monolith's central scheduler: a single JVM, so the tick always runs -
 * no leader coordination needed.
 */
@Component
@Scope("singleton")
@ConditionalOnMonolithic
public class MonolithicCentralSchedulingService extends AbstractCentralSchedulingService {

	public MonolithicCentralSchedulingService(TimesCalculatorService timesCalculationService,
			ProjectEndpointScheduledTaskRepository scheduledProjectEndpointRepo, IGMessageBroker broker,
			IMessageEnvelopeFactory envelopeFactory,
			@Value("${ai.gebo.scheduling.tick-page-size:200}") int tickPageSize) {
		super(timesCalculationService, scheduledProjectEndpointRepo, broker, envelopeFactory, tickPageSize);
	}

	@Override
	protected boolean canRunTick() {
		return true;
	}

}
