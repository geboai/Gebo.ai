/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Selects which participants-enablement resolver a service gets, from its
 * {@code spring.application.name} normalised against the workflow authority id
 * (tyr): tyr resolves from the topology directly and hosts the endpoint; every
 * other service asks tyr. Using the normalised name (dots &rarr; underscores)
 * matches how the topology identifies a running service.
 */
public final class WorkflowAuthorityConditions {

	private WorkflowAuthorityConditions() {
	}

	static boolean isWorkflowAuthority(Environment environment) {
		String appName = environment.getProperty("spring.application.name", "");
		String tyrId = environment.getProperty("gebo.microservices.workflow.steps.tyr-microservice-id", "tyr_gebo_ai");
		return tyrId.equals(appName.replace('.', '_'));
	}

	/** Matches on the workflow authority (tyr). */
	public static final class OnWorkflowAuthority implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return isWorkflowAuthority(context.getEnvironment());
		}
	}

	/** Matches on every service that is not the workflow authority. */
	public static final class OnNotWorkflowAuthority implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return !isWorkflowAuthority(context.getEnvironment());
		}
	}
}
