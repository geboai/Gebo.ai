/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.searchservices.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.cluster.config.GeboClusterCommonsAutoConfiguration;
import ai.gebo.microservices.searchservices.client.ConfluenceSearchServiceRestClient;
import ai.gebo.microservices.searchservices.client.GoogleDriveSearchServiceRestClient;
import ai.gebo.microservices.searchservices.client.JiraSearchServiceRestClient;
import ai.gebo.microservices.searchservices.client.SharePointSearchServiceRestClient;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * Wires the four per-connector search-service REST clients (Jira, Confluence,
 * SharePoint native; Google Drive plain). Each bean is
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean} on its concrete
 * client type: dropped onto a service that already owns the local search handler
 * (the monolith, or the content microservice itself) it stands aside; dropped
 * onto brain — where none of these handlers exist — it supplies the remote-backed
 * {@code ISearchService} beans that brain's deep-search discovers via
 * {@code ISearchServiceRepositoryPattern}. Runs after the topology resolver and
 * the cluster-commons token propagator exist; the {@link WebClient} carries no
 * base url (the target is resolved per call from the topology).
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class,
		GeboClusterCommonsAutoConfiguration.class })
@EnableConfigurationProperties(GeboSearchServicesClientsProperties.class)
public class GeboSearchServicesClientsAutoConfiguration {

	static final String WEB_CLIENT_BEAN = "geboSearchServicesClientWebClient";

	@Bean(name = WEB_CLIENT_BEAN)
	@ConditionalOnMissingBean(name = WEB_CLIENT_BEAN)
	public WebClient geboSearchServicesClientWebClient(GeboSearchServicesClientsProperties properties) {
		return WebClient.builder()
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getMaxInMemorySizeBytes())).build();
	}

	private GeboTtlCache metadataCache(GeboSearchServicesClientsProperties properties) {
		return new GeboTtlCache(properties.getMetadataCacheTtl(), properties.getMetadataCacheMaxEntries());
	}

	@Bean
	@ConditionalOnMissingBean(JiraSearchServiceRestClient.class)
	public JiraSearchServiceRestClient jiraSearchServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			GeboSearchServicesClientsProperties properties) {
		return new JiraSearchServiceRestClient(webClient, urlResolver, tokenPropagator, documentContentStreamer,
				properties.getJira(), metadataCache(properties));
	}

	@Bean
	@ConditionalOnMissingBean(ConfluenceSearchServiceRestClient.class)
	public ConfluenceSearchServiceRestClient confluenceSearchServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			GeboSearchServicesClientsProperties properties) {
		return new ConfluenceSearchServiceRestClient(webClient, urlResolver, tokenPropagator, documentContentStreamer,
				properties.getConfluence(), metadataCache(properties));
	}

	@Bean
	@ConditionalOnMissingBean(SharePointSearchServiceRestClient.class)
	public SharePointSearchServiceRestClient sharePointSearchServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			GeboSearchServicesClientsProperties properties) {
		return new SharePointSearchServiceRestClient(webClient, urlResolver, tokenPropagator, documentContentStreamer,
				properties.getSharepoint(), metadataCache(properties));
	}

	@Bean
	@ConditionalOnMissingBean(GoogleDriveSearchServiceRestClient.class)
	public GoogleDriveSearchServiceRestClient googleDriveSearchServiceRestClient(
			@Qualifier(WEB_CLIENT_BEAN) WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			GeboSearchServicesClientsProperties properties) {
		return new GoogleDriveSearchServiceRestClient(webClient, urlResolver, tokenPropagator, documentContentStreamer,
				properties.getGoogledrive(), metadataCache(properties));
	}
}
