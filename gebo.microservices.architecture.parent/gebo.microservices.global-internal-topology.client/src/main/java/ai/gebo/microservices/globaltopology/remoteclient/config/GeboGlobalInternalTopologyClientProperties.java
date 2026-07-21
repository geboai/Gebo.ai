/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.remoteclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration for the global internal-topology client. Overridable under
 * {@code gebo.microservices.global-internal-topology.client.*}.
 */
@Data
@ConfigurationProperties(prefix = "gebo.microservices.global-internal-topology.client")
public class GeboGlobalInternalTopologyClientProperties {

	/** Topology microservice id of the coordinator hosting the global controller (tyr). */
	private String coordinatorMicroserviceId = "tyr_gebo_ai";

	/** The GlobalInternalTopologyController's request-mapping base path. */
	private String basePath = "api/admin/GlobalInternalTopologyController";

	/** WebClient max in-memory buffer for the global topology payload. */
	private int maxInMemorySizeBytes = 8 * 1024 * 1024;
}
