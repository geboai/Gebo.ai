/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import ai.gebo.microservices.topology.GeboCurrentMicroservice;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.GeboModelsReplicationParticipants;
import ai.gebo.microservices.topology.GeboStandardMicroservices;

/**
 * Auto-configuration that publishes the effective {@link GeboMicroservicesTopology}
 * bean by merging the built-in defaults with the deployment / third-party
 * overrides bound from {@code gebo.microservices.topology.*}.
 *
 * <p>
 * Merge rule: start from {@link GeboStandardMicroservices#DEFAULTS} (unless
 * {@code include-defaults=false}), then apply
 * {@link GeboMicroservicesTopologyProperties#getServices()} - a new microservice
 * key adds a service, an existing key replaces that service's whole
 * module-to-systems map. The result is validated by
 * {@link GeboMicroservicesTopology#of}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration
@EnableConfigurationProperties(GeboMicroservicesTopologyProperties.class)
public class GeboMicroservicesTopologyAutoConfiguration {

	/**
	 * @param properties the bound topology overrides
	 * @return the merged, validated topology (defaults + {@code application.yml})
	 */
	@Bean
	@ConditionalOnMissingBean
	public GeboMicroservicesTopology geboMicroservicesTopology(GeboMicroservicesTopologyProperties properties) {
		// Preserve insertion order: defaults first, then any new services from yaml.
		Map<String, Map<String, List<String>>> merged = new LinkedHashMap<>();

		if (properties.isIncludeDefaults()) {
			for (GeboMicroservice service : GeboStandardMicroservices.DEFAULTS) {
				merged.put(service.getApplicationName(), service.getMessagingModules());
			}
		}
		properties.getServices()
				.forEach((microserviceId, modules) -> merged.put(microserviceId, modules == null ? Map.of() : modules));

		List<GeboMicroservice> microservices = merged.entrySet().stream()
				.map(entry -> new GeboMicroservice(entry.getKey(), entry.getValue())).toList();

		return GeboMicroservicesTopology.of(microservices);
	}

	/**
	 * Discovers the currently running microservice from its
	 * {@code spring.application.name}, applying the {@code '.'} &rarr; {@code '_'}
	 * rule to match a topology entry.
	 *
	 * @param applicationName the running app's {@code spring.application.name}
	 * @param topology the resolved topology
	 * @return the current-microservice identity (its topology entry may be empty,
	 *         e.g. for the gateway)
	 */
	@Bean
	@ConditionalOnMissingBean
	public GeboCurrentMicroservice geboCurrentMicroservice(
			@Value("${spring.application.name:}") String applicationName, GeboMicroservicesTopology topology) {
		return new GeboCurrentMicroservice(applicationName, topology);
	}

	/**
	 * The shared set of microservices participating in the LLM models-replication
	 * cache: the {@code gebo.microservices.topology.models-replication-participants}
	 * override when set, otherwise the built-in
	 * {@link GeboStandardMicroservices#DEFAULT_MODELS_REPLICATION_PARTICIPANTS}. A
	 * single source of truth every service reads.
	 *
	 * @param properties the bound topology configuration
	 * @return the effective participant set
	 */
	@Bean
	@ConditionalOnMissingBean
	public GeboModelsReplicationParticipants geboModelsReplicationParticipants(
			GeboMicroservicesTopologyProperties properties) {
		List<String> configured = properties.getModelsReplicationParticipants();
		List<String> effective = configured != null ? configured
				: GeboStandardMicroservices.DEFAULT_MODELS_REPLICATION_PARTICIPANTS;
		return new GeboModelsReplicationParticipants(effective);
	}
}
