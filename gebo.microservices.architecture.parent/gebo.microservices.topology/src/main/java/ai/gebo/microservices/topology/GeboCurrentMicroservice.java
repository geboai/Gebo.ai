/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology;

import java.util.Optional;

/**
 * Identity of the <b>currently running</b> microservice, discovered from its
 * {@code spring.application.name} by applying the {@code '.'} &rarr; {@code '_'}
 * normalisation rule ({@link GeboMicroservice#normalizeName(String)}) and looking
 * the result up in the {@link GeboMicroservicesTopology}.
 *
 * <p>
 * Published as a bean by
 * {@link ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration}.
 * {@link #microservice()} is empty when the running app is not part of the
 * topology (e.g. the gateway, which is the routing edge, not a routable
 * backend).
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class GeboCurrentMicroservice {

	private final String applicationName;
	private final String microserviceId;
	private final Optional<GeboMicroservice> microservice;

	/**
	 * @param applicationName the raw {@code spring.application.name} (may be dotted)
	 * @param topology the resolved topology
	 */
	public GeboCurrentMicroservice(String applicationName, GeboMicroservicesTopology topology) {
		this.applicationName = applicationName;
		this.microserviceId = GeboMicroservice.normalizeName(applicationName);
		this.microservice = topology.forApplicationName(applicationName);
	}

	/**
	 * @return the raw {@code spring.application.name} this identity was resolved
	 *         from (may contain dots)
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @return the normalised (dot-free) microservice id of the running app
	 */
	public String getMicroserviceId() {
		return microserviceId;
	}

	/**
	 * @return the topology entry for the running app, or empty if it is not a
	 *         topology member
	 */
	public Optional<GeboMicroservice> microservice() {
		return microservice;
	}

	/**
	 * @return {@code true} if the running app is a member of the topology
	 */
	public boolean isKnown() {
		return microservice.isPresent();
	}

	@Override
	public String toString() {
		return "GeboCurrentMicroservice[" + applicationName + " -> " + microserviceId + ", known=" + isKnown() + "]";
	}
}
