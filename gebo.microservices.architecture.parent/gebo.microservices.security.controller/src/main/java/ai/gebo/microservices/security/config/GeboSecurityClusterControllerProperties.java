/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the security directory cluster endpoints, bound from
 * {@code ai.gebo.security.cluster.*}:
 *
 * <pre>
 * ai.gebo.security.cluster:
 *   enabled: true
 *   base-path: api/cluster/SecurityController
 * </pre>
 *
 * <p>
 * <b>Who</b> may call is the shared {@code ai.gebo.cluster.participants.*}, the same
 * live-discovery membership that admits callers to every cluster-internal
 * controller.
 * </p>
 */
@ConfigurationProperties(prefix = "ai.gebo.security.cluster")
public class GeboSecurityClusterControllerProperties {

	/** Whether the directory endpoints are exposed at all. */
	private boolean enabled = true;

	/** Base path they are served under, and the path the guard covers. */
	private String basePath = "api/cluster/SecurityController";

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
}
