/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.searchservices.client.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration for the search-services REST clients. Each connector's
 * {@link Endpoint} carries the topology microservice id (resolved through
 * {@code GeboMicroserviceUrlResolver}) and the controller base path; both default
 * to the standard values and are overridable under
 * {@code gebo.microservices.searchservices.client.*}.
 */
@Data
@ConfigurationProperties(prefix = "gebo.microservices.searchservices.client")
public class GeboSearchServicesClientsProperties {

	/** WebClient max in-memory buffer for search-result payloads (default 32 MiB). */
	private int maxInMemorySizeBytes = 32 * 1024 * 1024;

	/** TTL for the memoized static metadata (id/description/prompt codes). */
	private Duration metadataCacheTtl = Duration.ofHours(1);

	private int metadataCacheMaxEntries = 256;

	private Endpoint jira = new Endpoint("jira_gebo_ai", "api/users/JiraSearchServiceController");
	private Endpoint confluence = new Endpoint("confluence_gebo_ai", "api/users/ConfluenceSearchServiceController");
	private Endpoint sharepoint = new Endpoint("sharepoint_gebo_ai", "api/users/SharePointSearchServiceController");
	private Endpoint googledrive = new Endpoint("googledrive_gebo_ai", "api/users/GoogleDriveSearchServiceController");

	@Data
	public static class Endpoint {
		/** Topology microservice id of the content service hosting the search controller. */
		private String microserviceId;
		/** The controller's request-mapping base path. */
		private String basePath;

		public Endpoint() {
		}

		public Endpoint(String microserviceId, String basePath) {
			this.microserviceId = microserviceId;
			this.basePath = basePath;
		}
	}
}
