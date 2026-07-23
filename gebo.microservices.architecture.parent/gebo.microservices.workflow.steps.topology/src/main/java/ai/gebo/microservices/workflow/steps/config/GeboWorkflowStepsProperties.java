/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration for the topology-driven workflow step enablement. Defaults address
 * tyr as the authority; all are overridable under
 * {@code gebo.microservices.workflow.steps.*}.
 */
@Data
@ConfigurationProperties(prefix = "gebo.microservices.workflow.steps")
public class GeboWorkflowStepsProperties {

	/** Topology microservice id of the workflow authority (tyr). */
	private String tyrMicroserviceId = "tyr_gebo_ai";

	/** The enabled-steps controller's request-mapping base path on tyr. */
	private String enabledStepsBasePath = "api/users/WorkflowParticipantsEnablementController";

	/** WebClient max in-memory buffer for the enabled-steps payload (default 256 KiB). */
	private int maxInMemorySizeBytes = 256 * 1024;

	/**
	 * How long a run's resolved enabled-step set is cached before it is re-fetched
	 * — the bound on how stale a long-running run's participant set can get.
	 */
	private Duration cacheTtl = Duration.ofMinutes(10);

	/** Maximum number of distinct runs cached at once. */
	private int cacheMaxEntries = 4096;
}
