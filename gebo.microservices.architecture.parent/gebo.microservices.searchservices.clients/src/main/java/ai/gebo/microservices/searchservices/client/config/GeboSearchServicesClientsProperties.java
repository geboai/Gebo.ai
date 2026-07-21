/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.searchservices.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration for the search-services REST clients. Each connector's
 * {@link Endpoint} carries the topology microservice id (resolved through
 * {@code GeboMicroserviceUrlResolver}), the controller base path, and the
 * connector's <b>static</b> search-service metadata (id / description / product /
 * messaging-module / prompt codes). The metadata is served locally — it never
 * triggers a remote call — so the repository indexing that runs at startup
 * ({@code SearchServiceRepositoryPatternImpl.getCodeValue -> getId}) works even
 * while the connector microservice is down. Defaults mirror the server-side
 * constants; all are overridable under
 * {@code gebo.microservices.searchservices.client.*}.
 */
@Data
@ConfigurationProperties(prefix = "gebo.microservices.searchservices.client")
public class GeboSearchServicesClientsProperties {

	/** WebClient max in-memory buffer for search-result payloads (default 32 MiB). */
	private int maxInMemorySizeBytes = 32 * 1024 * 1024;

	private Endpoint jira = Endpoint.of("jira_gebo_ai", "api/users/JiraSearchServiceController", "jira", "Jira Search",
			"jira", "jira-module", "jira-standard-query-extraction-prompt", "jira-native-query-extraction-prompt");
	private Endpoint confluence = Endpoint.of("confluence_gebo_ai", "api/users/ConfluenceSearchServiceController",
			"confluence", "Confluence Search", "confluence", "confluence-module",
			"confluence-standard-query-extraction-prompt", "confluence-native-query-extraction-prompt");
	private Endpoint sharepoint = Endpoint.of("sharepoint_gebo_ai", "api/users/SharePointSearchServiceController",
			"sharepoint", "Sharepoint/OneDrive Search", "sharepoint", "sharepoint-module",
			"msgraph-standard-query-extraction-prompt", "msgraph-native-search-prompts-template");
	private Endpoint googledrive = Endpoint.of("googledrive_gebo_ai", "api/users/GoogleDriveSearchServiceController",
			"google-drive", "Google Drive/Workspace Search", "google-drive", "google-drive-module",
			"google-drive-standard-query-extraction-prompt", null);

	@Data
	public static class Endpoint {
		/** Topology microservice id of the content service hosting the search controller. */
		private String microserviceId;
		/** The controller's request-mapping base path. */
		private String basePath;
		/** Static {@code ISearchService} identity/metadata, served locally. */
		private String id;
		private String description;
		private String productId;
		private String messagingModuleId;
		private String queriesGenerationPromptUseCode;
		/** Native-search prompt template code (null for plain, non-native connectors). */
		private String nativePromptTemplateUseCode;

		public Endpoint() {
		}

		static Endpoint of(String microserviceId, String basePath, String id, String description, String productId,
				String messagingModuleId, String queriesGenerationPromptUseCode, String nativePromptTemplateUseCode) {
			Endpoint e = new Endpoint();
			e.microserviceId = microserviceId;
			e.basePath = basePath;
			e.id = id;
			e.description = description;
			e.productId = productId;
			e.messagingModuleId = messagingModuleId;
			e.queriesGenerationPromptUseCode = queriesGenerationPromptUseCode;
			e.nativePromptTemplateUseCode = nativePromptTemplateUseCode;
			return e;
		}
	}
}
