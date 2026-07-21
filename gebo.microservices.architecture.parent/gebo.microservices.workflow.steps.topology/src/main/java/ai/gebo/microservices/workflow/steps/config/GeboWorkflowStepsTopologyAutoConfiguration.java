/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.microservices.workflow.steps.IWorkflowParticipantsEnablementResolver;
import ai.gebo.microservices.workflow.steps.config.WorkflowAuthorityConditions.OnNotWorkflowAuthority;
import ai.gebo.microservices.workflow.steps.config.WorkflowAuthorityConditions.OnWorkflowAuthority;
import ai.gebo.microservices.workflow.steps.controller.WorkflowParticipantsEnablementController;
import ai.gebo.microservices.workflow.steps.handler.EmbeddingTopologyWorkflowStepEnabledHandler;
import ai.gebo.microservices.workflow.steps.handler.FullTextTopologyWorkflowStepEnabledHandler;
import ai.gebo.microservices.workflow.steps.handler.GraphextractionTopologyWorkflowStepEnabledHandler;
import ai.gebo.microservices.workflow.steps.resolver.TopologyWorkflowParticipantsEnablementResolver;
import ai.gebo.microservices.workflow.steps.resolver.TyrBackedWorkflowParticipantsEnablementResolver;

/**
 * Wires the topology-driven standard-workflow step enablement for the microservices
 * distribution.
 *
 * <p>
 * The step handlers ({@link EmbeddingTopologyWorkflowStepEnabledHandler} and
 * siblings) are published on every service this module lands on — tyr (which runs
 * the status-tree computation) and the chunker (which runs the tokenization
 * fan-out) — so each has a complete set in its repository pattern and the
 * routing/status logic is unchanged. Their {@code isEnabled} delegates to the
 * {@link IWorkflowParticipantsEnablementResolver} active on the service:
 * </p>
 * <ul>
 * <li>on <b>tyr</b> ({@link OnWorkflowAuthority}) the
 * {@link TopologyWorkflowParticipantsEnablementResolver} resolves from the topology
 * directly, and the {@link WorkflowParticipantsEnablementController} exposes it;</li>
 * <li>everywhere else ({@link OnNotWorkflowAuthority}) the
 * {@link TyrBackedWorkflowParticipantsEnablementResolver} asks tyr once per run and
 * caches.</li>
 * </ul>
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboWorkflowStepsProperties.class)
public class GeboWorkflowStepsTopologyAutoConfiguration {

	static final String CACHE_BEAN = "geboWorkflowStepsEnablementCache";
	static final String WEB_CLIENT_BUILDER_BEAN = "geboWorkflowStepsLbWebClientBuilder";
	static final String WEB_CLIENT_BEAN = "geboWorkflowStepsClientWebClient";

	@Bean(name = CACHE_BEAN)
	@ConditionalOnMissingBean(name = CACHE_BEAN)
	public GeboTtlCache geboWorkflowStepsEnablementCache(GeboWorkflowStepsProperties properties) {
		return new GeboTtlCache(properties.getCacheTtl(), properties.getCacheMaxEntries());
	}

	/**
	 * A dedicated {@link LoadBalanced @LoadBalanced} builder so the enabled-steps call
	 * goes through Spring Cloud LoadBalancer (resolving tyr's discovery service-id)
	 * without disturbing any other WebClient.Builder on the classpath.
	 */
	@Bean(name = WEB_CLIENT_BUILDER_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BUILDER_BEAN)
	@LoadBalanced
	public WebClient.Builder geboWorkflowStepsLbWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboWorkflowStepsClientWebClient(
			@Qualifier(WEB_CLIENT_BUILDER_BEAN) WebClient.Builder builder, GeboWorkflowStepsProperties properties) {
		return builder
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes())).build();
	}

	// ---- resolver: tyr resolves locally + hosts the endpoint ----------------

	@Configuration(proxyBeanMethods = false)
	@Conditional(OnWorkflowAuthority.class)
	static class WorkflowAuthorityConfiguration {

		@Bean
		@ConditionalOnMissingBean(IWorkflowParticipantsEnablementResolver.class)
		public TopologyWorkflowParticipantsEnablementResolver topologyWorkflowParticipantsEnablementResolver(
				GeboMicroservicesTopology topology, @Qualifier(CACHE_BEAN) GeboTtlCache cache) {
			return new TopologyWorkflowParticipantsEnablementResolver(topology, cache);
		}

		@Bean
		public WorkflowParticipantsEnablementController workflowParticipantsEnablementController(
				TopologyWorkflowParticipantsEnablementResolver resolver) {
			return new WorkflowParticipantsEnablementController(resolver);
		}
	}

	// ---- resolver: everyone else asks tyr and caches ------------------------

	@Bean
	@Conditional(OnNotWorkflowAuthority.class)
	@ConditionalOnMissingBean(IWorkflowParticipantsEnablementResolver.class)
	public TyrBackedWorkflowParticipantsEnablementResolver tyrBackedWorkflowParticipantsEnablementResolver(
			@Qualifier(WEB_CLIENT_BEAN) WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, @Qualifier(CACHE_BEAN) GeboTtlCache cache,
			GeboWorkflowStepsProperties properties) {
		return new TyrBackedWorkflowParticipantsEnablementResolver(webClient, urlResolver, tokenPropagator, cache,
				properties);
	}

	// ---- step handlers (present wherever this module lands) -----------------

	@Bean
	public EmbeddingTopologyWorkflowStepEnabledHandler embeddingTopologyWorkflowStepEnabledHandler(
			IWorkflowParticipantsEnablementResolver resolver) {
		return new EmbeddingTopologyWorkflowStepEnabledHandler(resolver);
	}

	@Bean
	public GraphextractionTopologyWorkflowStepEnabledHandler graphextractionTopologyWorkflowStepEnabledHandler(
			IWorkflowParticipantsEnablementResolver resolver) {
		return new GraphextractionTopologyWorkflowStepEnabledHandler(resolver);
	}

	@Bean
	public FullTextTopologyWorkflowStepEnabledHandler fullTextTopologyWorkflowStepEnabledHandler(
			IWorkflowParticipantsEnablementResolver resolver) {
		return new FullTextTopologyWorkflowStepEnabledHandler(resolver);
	}
}
