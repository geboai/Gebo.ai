/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.models.replication;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Local (per-service) tuning of the LLM models-replication cache, bound from
 * {@code gebo.models.replication.*} in {@code application.yml}.
 * <p>
 * <b>Who participates is NOT configured here</b>: the participant set is part of
 * the shared microservices topology
 * ({@code gebo.microservices.topology.models-replication-participants}, defaulting
 * to {@link ai.gebo.microservices.topology.GeboStandardMicroservices#DEFAULT_MODELS_REPLICATION_PARTICIPANTS}).
 * This class only carries deployment/network tuning, all with working defaults,
 * so a participating service usually needs no {@code gebo.models.replication.*}
 * configuration at all.
 */
@Data
@ConfigurationProperties(prefix = "gebo.models.replication")
public class ModelsReplicationClusterProperties {

	/** Hazelcast port each participant binds to / is reached on. */
	private int port = 5701;

	/**
	 * Whether a member may fall back to the next port if {@link #port} is busy.
	 * Defaults to {@code false} so ports stay deterministic across services.
	 */
	private boolean portAutoIncrement = false;

	/** Logical Hazelcast cluster name shared by all participants. */
	private String clusterName = "gebo-models-cluster";

	/**
	 * Optional overrides of the network host used to reach a participant, keyed by
	 * microservice id (underscore form). When absent, the host is derived from the
	 * microservice id by turning it back into its dotted application-name form
	 * (e.g. {@code brain_gebo_ai} &rarr; {@code brain.gebo.ai}). Use this when the
	 * reachable host differs from the application name (e.g. a Kubernetes service
	 * name).
	 */
	private Map<String, String> hostOverrides = new LinkedHashMap<>();
}
