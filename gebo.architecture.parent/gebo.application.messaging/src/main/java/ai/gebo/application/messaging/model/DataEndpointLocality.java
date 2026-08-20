/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

/**
 * How far data travels to reach a {@link DataEndpoint}.
 *
 * <p>
 * This is the distinction the endpoint locator alone cannot make:
 * {@code http://ollama:11434} and {@code https://api.openai.com} are both just
 * a {@code GBaseModelConfig.getBaseUrl()}, but only one of them sends the
 * customer's content to a third party. Only the reporting component knows which
 * of its endpoints is which, so it is set at the source rather than guessed
 * from the hostname by the screen.
 * </p>
 */
public enum DataEndpointLocality {

	/**
	 * Inside this deployment - a store or model that ships and runs with the
	 * installation and is reachable only from it. The Mongo, Qdrant, OpenSearch
	 * and Neo4j containers of a standard compose deployment, a local Ollama or
	 * ONNX embedding model, a self-hosted SearXNG.
	 */
	LOCAL_DEPLOYMENT,

	/**
	 * Inside the organization's own network but not part of this deployment - a
	 * corporate Git, SharePoint, Confluence, Jira or filesystem the installation
	 * ingests from, or a self-operated model server. Data leaves the deployment
	 * but stays under the same operator.
	 */
	SAME_NETWORK,

	/**
	 * A third-party service outside the organization. Content sent here is a
	 * transfer to an external processor and is the datum a GDPR Art. 44 / Art. 46
	 * review starts from - the hosted LLM providers ({@code gebo.llms.openai},
	 * {@code gebo.llms.anthropic3}, {@code gebo.llms.aws-bedrock},
	 * {@code gebo.llms.google_vertex}, {@code gebo.llms.mistral},
	 * {@code gebo.llms.deepseek}) and the hosted web-search providers.
	 */
	EXTERNAL_PROVIDER;

	/**
	 * A deliberately conservative hint for infrastructure stores, whose locality
	 * depends on where the operator pointed them rather than on anything the
	 * reporting component decides.
	 *
	 * <p>
	 * Returns {@link #LOCAL_DEPLOYMENT} only for a host that cannot be anything
	 * else - a loopback address, or a single-label name, which in every shipped
	 * deployment is a docker-compose service name on the internal network
	 * ({@code mongo}, {@code qdrant}, {@code opensearch}, {@code neo4j}). For
	 * anything with a dot in it - a real DNS name, a managed cloud endpoint - it
	 * returns null, because from here the two are indistinguishable and claiming
	 * "local" for a managed cloud store would be exactly the wrong error in a
	 * transfer review.
	 * </p>
	 *
	 * <p>
	 * A component that genuinely <em>knows</em> must set the value explicitly and
	 * not call this: a configured OpenAI or Anthropic endpoint is an
	 * {@link #EXTERNAL_PROVIDER} by construction, whatever its hostname looks
	 * like.
	 * </p>
	 *
	 * @param locator a sanitized locator, as stored on {@code DataEndpoint}
	 * @return {@link #LOCAL_DEPLOYMENT}, or null when it cannot be established
	 */
	public static DataEndpointLocality hintFromLocator(String locator) {
		if (locator == null) {
			return null;
		}
		String host = locator.trim();
		int schemeEnd = host.indexOf("://");
		if (schemeEnd >= 0) {
			host = host.substring(schemeEnd + 3);
		}
		int pathStart = host.indexOf('/');
		if (pathStart >= 0) {
			host = host.substring(0, pathStart);
		}
		// A multi-host list cannot be characterized as a whole; judge the first.
		int listEnd = host.indexOf(',');
		if (listEnd >= 0) {
			host = host.substring(0, listEnd);
		}
		int portStart = host.lastIndexOf(':');
		if (portStart >= 0) {
			host = host.substring(0, portStart);
		}
		host = host.trim();
		if (host.isEmpty()) {
			return null;
		}
		if (host.equalsIgnoreCase("localhost") || host.equals("127.0.0.1") || host.equals("::1")
				|| host.equals("0.0.0.0")) {
			return LOCAL_DEPLOYMENT;
		}
		return host.indexOf('.') < 0 ? LOCAL_DEPLOYMENT : null;
	}
}
