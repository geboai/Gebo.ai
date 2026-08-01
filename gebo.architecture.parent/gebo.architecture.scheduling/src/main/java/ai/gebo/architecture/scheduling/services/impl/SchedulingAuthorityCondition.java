/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.scheduling.services.impl;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Matches on the central scheduling authority (tyr), from
 * {@code spring.application.name} normalised against the configured tyr
 * microservice id - mirrors
 * {@code ai.gebo.microservices.workflow.steps.config.WorkflowAuthorityConditions}'s
 * identical need to single out tyr from every other service that happens to
 * share a module's classpath.
 *
 * <p>
 * {@code @ConditionalOnMicroservices} alone is not enough to gate
 * {@link ClusteredCentralSchedulingService}: {@code gebo.architecture.scheduling}
 * is not exclusive to tyr's classpath (e.g. brain gets it transitively via
 * {@code gebo.core}, for {@code ReindexingFrequencyOptionsController}'s
 * {@code IGSchedulingTimeService} usage), so every such service would
 * otherwise instantiate its own "central" scheduler under the shared
 * {@code scheduler-module.scheduler-component} identity - colliding with the
 * real one when the RabbitMQ bridge builds a remote proxy for tyr's actual
 * instance ("The system scheduler-module.scheduler-component is already
 * registered in this broker").
 * </p>
 */
public final class SchedulingAuthorityCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		String appName = environment.getProperty("spring.application.name", "");
		String tyrId = environment.getProperty("ai.gebo.scheduling.tyr-microservice-id", "tyr_gebo_ai");
		return tyrId.equals(appName.replace('.', '_'));
	}
}
