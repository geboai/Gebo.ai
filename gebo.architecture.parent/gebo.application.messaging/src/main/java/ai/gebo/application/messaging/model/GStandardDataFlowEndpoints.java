/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import ai.gebo.model.base.GeboComponentInfo;

/**
 * The ids of the stores the standard ingestion pipeline publishes into the
 * data-flow register, shared by the component owning each store and by every
 * component naming it from outside.
 *
 * <p>
 * A cross-component reference resolves only when it spells the owner's endpoint id
 * exactly: the knowledge graph was published as {@code knowledge-graph} while its
 * readers named {@code graph-store}, so their edges never reached it. Owners and
 * readers both take the ids from here.
 * </p>
 */
public final class GStandardDataFlowEndpoints {

	/** The tokenizer's chunk cache and chunking sessions. */
	public static final String CHUNK_CACHE = "chunk-cache";
	/** The vectorizator's vector store, the store every installation has. */
	public static final String VECTOR_STORE = "vector-store";
	/** The full-text indexer's index, deployed with OpenSearch only. */
	public static final String FULLTEXT_INDEX = "fulltext-index";
	/** The graph extraction's knowledge graph, deployed with Neo4j only. */
	public static final String KNOWLEDGE_GRAPH = "knowledge-graph";

	/**
	 * The symbolic component reporting the search sources - every system a search
	 * service searches (a configured Jira, Confluence or SharePoint system searched
	 * live, a web search account) - once, for every reader pointing to them.
	 */
	public static final String SEARCH_SOURCES_MODULE = "search-sources-module";
	public static final String SEARCH_SOURCES_COMPONENT = "search-sources";

	private GStandardDataFlowEndpoints() {
	}

	/** The chunk cache, qualified with the tokenizer component owning it. */
	public static String chunkCacheRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.TOKENIZER_MODULE,
				GStandardModulesConstraints.TOKENIZER_COMPONENT), CHUNK_CACHE);
	}

	/** The vector store, qualified with the vectorizator component owning it. */
	public static String vectorStoreRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_COMPONENT), VECTOR_STORE);
	}

	/** The full-text index, qualified with the full-text indexing component owning it. */
	public static String fullTextIndexRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.FULLTEXT_MODULE,
				GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT), FULLTEXT_INDEX);
	}

	/**
	 * The local id of a search source: one system searched by one search service.
	 *
	 * @param searchServiceId the search service's id
	 * @param systemCode      the code of the system it searches (for a system searched
	 *                        live, the configured system's own code)
	 */
	public static String searchSourceId(String searchServiceId, String systemCode) {
		return "search-" + searchServiceId + "-" + systemCode;
	}

	/** A search source, qualified with the search sources component owning it. */
	public static String searchSourceRef(String searchServiceId, String systemCode) {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(SEARCH_SOURCES_MODULE, SEARCH_SOURCES_COMPONENT),
				searchSourceId(searchServiceId, systemCode));
	}

	/** The knowledge graph, qualified with the graph extraction component owning it. */
	public static String knowledgeGraphRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE,
				GStandardModulesConstraints.KNOWLEDGE_GRAPH_COMPONENT), KNOWLEDGE_GRAPH);
	}
}
