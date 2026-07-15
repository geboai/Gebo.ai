/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Who counts as a cluster participant - i.e. which callers a cluster-internal
 * controller (secrets, security, ...) will answer at all.
 *
 * <pre>
 * ai.gebo.cluster.participants:
 *   extra-service-ids: []              # NOT the gateway - see below
 *   additional-allowed-addresses: []   # dev/monolith pinning, e.g. 127.0.0.1
 *   cache-ttl: 30s
 * </pre>
 *
 * Gebo.ai comment agent
 */
@ConfigurationProperties(prefix = "ai.gebo.cluster.participants")
public class GeboClusterParticipantsProperties {

	/**
	 * Service ids allowed to call on top of the topology members.
	 *
	 * <p>
	 * <b>Empty by default, and the gateway is deliberately not in it.</b> The guard
	 * identifies a caller by the address it connects from, so admitting the gateway
	 * admits <i>everything the gateway forwards</i> - including a request a browser
	 * made, which reaches the callee from the gateway's own (registered) address and
	 * would sail through the check. That would hand the cluster-internal endpoints to
	 * anyone who can reach the edge, which is precisely what this guard exists to
	 * prevent.
	 * </p>
	 *
	 * <p>
	 * Nothing needs it: the default {@code LOAD_BALANCER} addressing strategy has
	 * services call each other directly, peer to peer. Add the gateway only for a
	 * deployment that resolves urls through it, and then only after making sure the
	 * gateway does not route the cluster base paths from outside.
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
