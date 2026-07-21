/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration for the global internal-topology coordinator. All overridable under
 * {@code gebo.microservices.global-internal-topology.*}. The schedule properties are
 * also referenced by the {@code @Scheduled} annotation on the service.
 */
@Data
@ConfigurationProperties(prefix = "gebo.microservices.global-internal-topology")
public class GeboGlobalInternalTopologyProperties {

	/** Base path of each microservice's InternalMessagingTopologyController. */
	private String pollBasePath = "api/admin/InternalMessagingTopologyController";

	/** Delay before the first poll, so the cluster can finish coming up (ms). */
	private long initialDelayMs = 45000;

	/** Delay between the end of one poll cycle and the start of the next (ms). */
	private long pollIntervalMs = 120000;

	/** WebClient max in-memory buffer for a per-microservice topology payload. */
	private int maxInMemorySizeBytes = 4 * 1024 * 1024;
}
