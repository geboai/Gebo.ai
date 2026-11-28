/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.searchservices.client;

import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.glpi.search.api.GlpiResultsExtractionData;
import ai.gebo.glpi.search.api.GlpiSearchFilter;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.searchservices.client.config.GeboSearchServicesClientsProperties.Endpoint;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;

/**
 * Topology-aware REST client for the GLPI native search service hosted on
 * glpi.gebo.ai. Implements
 * {@code INativeSearchService<GlpiResultsExtractionData, GlpiSearchFilter>}.
 */
public class GlpiSearchServiceRestClient
		extends AbstractNativeSearchServiceRestClient<GlpiResultsExtractionData, GlpiSearchFilter> {

	public GlpiSearchServiceRestClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			Endpoint endpoint) {
		super(webClient, urlResolver, tokenPropagator, documentContentStreamer, endpoint,
				GlpiResultsExtractionData.class, GlpiSearchFilter.class);
	}
}
