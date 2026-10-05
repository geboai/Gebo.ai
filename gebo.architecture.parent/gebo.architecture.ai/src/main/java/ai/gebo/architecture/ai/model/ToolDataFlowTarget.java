/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.ai.model;

/**
 * Where a tool sends what the model gives it, or reads what it returns from: what
 * the compliance data-flow register shows of an agent operating the tool. Neutral on
 * purpose (the register's own model lives in another module): the register turns
 * each target into its endpoint.
 *
 * <p>
 * A target says nothing about personal data: in the register personal data come
 * only from the data sources an administrator flags as holding them
 * ({@code GProjectEndpoint.personalData}), and reach a tool's endpoint only by
 * the flows linking it to such a source.
 * </p>
 *
 * @param kind            what the tool reaches
 * @param reference       the instance reached, when the kind has several: the search
 *                        service id, the MCP client code, the platform data name
 * @param product         a human name of the product reached, null to let the register
 *                        name it
 * @param locator         where it is reached (a URL, a command), null when not known
 *                        here
 * @param secretReference the secret it is reached with, null when none
 * @param description     what the tool does with it
 */
public record ToolDataFlowTarget(Kind kind, String reference, String product, String locator,
		String secretReference, String description) {

	public enum Kind {
		/** The knowledge bases' vector store (semantic retrieval). */
		KNOWLEDGE_BASE_VECTOR_STORE,
		/** The knowledge bases' full-text index (lexical retrieval). */
		KNOWLEDGE_BASE_FULLTEXT_INDEX,
		/** The knowledge graph (graph retrieval), when deployed. */
		KNOWLEDGE_BASE_GRAPH_STORE,
		/** The default embedding model (the query is embedded). */
		EMBEDDING_MODEL,
		/** The default ranker model (the contents found are scored). */
		RANKER_MODEL,
		/** The internal services chat model (analyses, judgements). */
		SERVICE_MODEL,
		/** A search service: {@code reference} is its id. */
		SEARCH_SERVICE,
		/** Any internet page (a page read from its URL). */
		INTERNET,
		/** An external MCP server: {@code reference} is its client configuration code. */
		MCP_SERVER,
		/** Data of the platform itself (users, catalogues): {@code reference} names it. */
		PLATFORM_DATA
	}

	public static ToolDataFlowTarget of(Kind kind, String description) {
		return new ToolDataFlowTarget(kind, null, null, null, null, description);
	}

	public static ToolDataFlowTarget searchService(String serviceId, String description) {
		return new ToolDataFlowTarget(Kind.SEARCH_SERVICE, serviceId, null, null, null, description);
	}

	public static ToolDataFlowTarget platformData(String name, String description) {
		return new ToolDataFlowTarget(Kind.PLATFORM_DATA, name, name, null, null, description);
	}
}
