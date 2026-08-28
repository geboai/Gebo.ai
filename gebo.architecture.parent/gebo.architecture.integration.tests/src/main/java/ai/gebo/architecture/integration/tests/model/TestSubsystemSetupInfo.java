package ai.gebo.architecture.integration.tests.model;

import ai.gebo.monolithic.api.client.model.GeboGoogleJsonSecretContent;
import ai.gebo.monolithic.api.client.model.GeboOauth2SecretContent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestSubsystemSetupInfo {
	/**
	 * Subsystem the setup secret asks the harness to configure on the freshly
	 * installed Gebo.ai.
	 * <p>
	 * The five {@code *_SEARCH} entries are the web-search providers introduced by
	 * the multi-provider refactoring. They are mutually exclusive: the backend
	 * exposes a provider's web-search tool to the LLM only while that provider has
	 * stored credentials, so the admin wizard clears every provider before storing
	 * the selected one and exactly ONE ends up active. The harness reproduces that
	 * rule - see
	 * {@code AbstractVendorSetupAndUseTest#setupProducts(java.util.List, ai.gebo.monolithic.api.client.model.SecurityHeaderData, String, int)}.
	 */
	public static enum Product {
		/** Google Programmable Search: needs {@code apiKey} + {@code id} (the search engine id). */
		GOOGLE_SEARCH,
		/** Tavily: needs {@code apiKey} only. */
		TAVILY_SEARCH,
		/** Brave Search: needs {@code apiKey} (the subscription token) only. */
		BRAVE_SEARCH,
		/** Self-hosted SearXNG: needs {@code basePath} (instance URL); {@code apiKey} optional. */
		SEARXNG_SEARCH,
		/** SerpApi: needs {@code apiKey} only. */
		SERPAPI_SEARCH,
		CONFLUENCE_ONPREMISE, CONFLUENCE_CLOUD, SHAREPOINT, GOOGLE_WORKSPACE, JIRA_CLOUD;

		/**
		 * @return true for the web-search providers, of which at most one may be
		 *         configured in a single setup secret.
		 */
		public boolean isWebSearchProvider() {
			switch (this) {
			case GOOGLE_SEARCH:
			case TAVILY_SEARCH:
			case BRAVE_SEARCH:
			case SEARXNG_SEARCH:
			case SERPAPI_SEARCH:
				return true;
			default:
				return false;
			}
		}
	};

	@NotNull
	Product product = null;
	String user = null;
	/** Google Programmable Search engine id; unused by the other providers. */
	String id = null;
	/**
	 * API key / token. Required by every product except {@code SEARXNG_SEARCH},
	 * whose self-hosted instances are usually reachable without one - hence no
	 * {@code @NotNull} here.
	 */
	String apiKey = null;
	/** Base URI of the system: Confluence/Jira/Sharepoint site, SearXNG instance. */
	String basePath = null;
	GeboOauth2SecretContent oauth2Credentials = null;
	GeboGoogleJsonSecretContent googleJsonCredentials = null;
}
