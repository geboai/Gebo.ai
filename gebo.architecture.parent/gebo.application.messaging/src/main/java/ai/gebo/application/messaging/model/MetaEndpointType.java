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
 * The kind of data an endpoint holds or exchanges.
 *
 * <p>
 * Every constant corresponds to a store or interface this architecture actually
 * configures - the comment on each names the class or module it is reported
 * from, so the enum stays anchored to the deployment rather than drifting into
 * a generic taxonomy.
 * </p>
 */
public enum MetaEndpointType {

	/** Source documents crawled from a content handler's project endpoint. */
	DOCUMENTS,

	/** A general-purpose store - the Mongo of {@code MongoConfig}. */
	DATABASE,

	/** The configured vector store: {@code VectorStoreProduct} QDRANT/MONGO/REDIS. */
	VECTORIAL_DATABASE,

	/** The knowledge graph behind {@code spring.neo4j.uri}. */
	GRAPH_DATABASE,

	/** Chunked document text, as produced by the tokenizer component. */
	CHUNK,

	/**
	 * A full-text index - the OpenSearch of {@code OpenSearchConfig}, which is
	 * neither a {@link #DATABASE} nor a {@link #VECTORIAL_DATABASE}.
	 */
	FULLTEXT_INDEX,

	/**
	 * A model inference endpoint - {@code GBaseModelConfig.getBaseUrl()} for chat,
	 * embedding, ranker, transcript, text-to-speech and image models. Content sent
	 * here leaves the process, and depending on
	 * {@link DataEndpointLocality} the installation.
	 */
	LLM_ENDPOINT,

	/** Object storage, e.g. the bucket behind the {@code aws-s3-module} handler. */
	OBJECT_STORAGE,

	/**
	 * The message broker carrying payloads between microservices - the RabbitMQ of
	 * {@code GeboRabbitMqMessagingProperties}. Data in transit rather than at rest.
	 */
	MESSAGE_BROKER,

	/**
	 * An external web-search provider reached through an
	 * {@code AbstractWebSearchServiceImpl} subclass. Queries leave the
	 * installation.
	 */
	WEB_SEARCH,

	/**
	 * A locally accessible filesystem holding cached or original content - the
	 * case a content handler reports through
	 * {@code IGContentManagementSystemHandler.isContentsOnLocalFilesystem()}.
	 */
	LOCAL_FILESYSTEM,

	/**
	 * Retained chat sessions - user prompts and responses, held under the
	 * retention policy enforced by the {@code session-shrinker} component.
	 */
	CHAT_SESSION
}
