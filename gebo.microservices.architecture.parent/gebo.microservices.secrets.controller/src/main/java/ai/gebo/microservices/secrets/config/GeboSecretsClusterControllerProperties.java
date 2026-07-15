/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the secrets cluster endpoints, bound from
 * {@code ai.gebo.secrets.cluster.*}:
 *
 * <pre>
 * ai.gebo.secrets.cluster:
 *   enabled: true
 *   base-path: api/cluster/SecretsController
 * </pre>
 *
 * <p>
 * <b>Who may call</b> is not configured here: it is the shared
 * {@code ai.gebo.cluster.participants.*} (see
 * {@code GeboClusterParticipantsProperties}), because the same live-discovery
 * membership admits callers to every cluster-internal controller - secrets,
 * security and any future pair - and having one such rule per module is how the
 * rules drift apart.
 * </p>
 */
@ConfigurationProperties(prefix = "ai.gebo.secrets.cluster")
public class GeboSecretsClusterControllerProperties {

	/**
	 * Whether the cluster endpoints are exposed at all. Turning this off in a service
	 * that hosts the secrets implementation keeps the secrets purely local.
	 */
	private boolean enabled = true;

	/** Base path the cluster endpoints are served under, and the path the guard covers. */
	private String basePath = "api/cluster/SecretsController";

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
