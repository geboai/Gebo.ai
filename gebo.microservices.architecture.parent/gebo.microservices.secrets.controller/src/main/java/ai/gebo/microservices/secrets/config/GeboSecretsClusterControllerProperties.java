/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the secrets cluster endpoints, bound from
 * {@code ai.gebo.secrets.cluster.*}:
 *
 * <pre>
 * ai.gebo.secrets.cluster:
 *   enabled: true
 *   base-path: api/cluster/SecretsController
 *   participants:
 *     extra-service-ids: []              # NOT the gateway - see below
 *     additional-allowed-addresses: []   # dev/monolith pinning, e.g. 127.0.0.1
 *     cache-ttl: 30s
 * </pre>
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

	private final Participants participants = new Participants();

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

	public Participants getParticipants() {
		return participants;
	}

	/** Who counts as a cluster participant allowed to call the endpoints. */
	public static class Participants {

		/**
		 * Service ids allowed to call on top of the topology members.
		 *
		 * <p>
		 * <b>Empty by default, and the gateway is deliberately not in it.</b> The guard
		 * identifies a caller by the address it connects from, so admitting the gateway
		 * admits <i>everything the gateway forwards</i> - including a request a browser
		 * made, which reaches heimdall from the gateway's own (registered) address and
		 * would sail through the check. That would hand the secret-content endpoints to
		 * anyone who can reach the edge, which is precisely what this guard exists to
		 * prevent.
		 * </p>
		 *
		 * <p>
		 * Nothing needs it: the default {@code LOAD_BALANCER} addressing strategy has
		 * services call heimdall directly, peer to peer. Add the gateway only for a
		 * deployment that resolves urls through it
		 * ({@code GeboMicroserviceUrlResolver.Strategy#GATEWAY}) - and then only after
		 * making sure the gateway does not route the cluster base path from outside, as
		 * gateway.gebo.ai's own configuration takes care not to.
		 * </p>
		 */
		private List<String> extraServiceIds = new ArrayList<>();

		/**
		 * Addresses always allowed, on top of whatever service discovery reports. Meant
		 * for a monolith / dev run or a test, where callers are not registered. Empty by
		 * default: in a real deployment membership must come from the registry.
		 */
		private List<String> additionalAllowedAddresses = new ArrayList<>();

		/**
		 * How long a discovery snapshot is reused before being refreshed. The check runs
		 * on every request while the registry only changes on the scale of a heartbeat.
		 */
		private Duration cacheTtl = Duration.ofSeconds(30);

		public List<String> getExtraServiceIds() {
			return extraServiceIds;
		}

		public void setExtraServiceIds(List<String> extraServiceIds) {
			this.extraServiceIds = extraServiceIds;
		}

		public List<String> getAdditionalAllowedAddresses() {
			return additionalAllowedAddresses;
		}

		public void setAdditionalAllowedAddresses(List<String> additionalAllowedAddresses) {
			this.additionalAllowedAddresses = additionalAllowedAddresses;
		}

		public Duration getCacheTtl() {
			return cacheTtl;
		}

		public void setCacheTtl(Duration cacheTtl) {
			this.cacheTtl = cacheTtl;
		}
	}
}
