/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointAccess;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.model.IGObjectWithSecurity;

/**
 * The endpoints the symbolic data-flow reporters (the chat pipeline, the agents
 * networks) describe the same way: the models they call, the knowledge stores they
 * read, and the links between them.
 */
public final class DataFlowEndpoints {

	private DataFlowEndpoints() {
	}

	/** A chat model as an LLM endpoint, null when it has no configuration. */
	public static DataEndpoint chatModel(String kind, IGConfigurableChatModel model, String description) {
		if (model == null || model.getConfig() == null) {
			return null;
		}
		DataEndpoint endpoint = modelEndpoint(kind + "-model-" + model.getCode(),
				describeModel(description, model.getDescription(), model.getConfig()), model.getConfig(),
				"chat model");
		// a chat model's users/groups decide who is offered it for a direct chat, in
		// either access model; a profile or an agent calling it is granted by its own
		if (model.getConfig() instanceof IGObjectWithSecurity secured) {
			DataEndpointAccess access = DataEndpointAccess.of(secured, "Chat model '" + model.getCode() + "'",
					"Picking this model for a direct chat", DataEndpointAccess.Mechanism.USERS_GROUPS);
			access.setNote("A chat profile or an agent using this model is granted by its own access.");
			endpoint.setAccess(new ArrayList<DataEndpointAccess>(List.of(access)));
		}
		return endpoint;
	}

	/** An embedding model as an LLM endpoint, null when it has no configuration. */
	public static DataEndpoint embeddingModel(IGConfigurableEmbeddingModel model) {
		if (model == null || model.getConfig() == null) {
			return null;
		}
		GBaseModelConfig config = model.getConfig();
		return modelEndpoint("embedding-model-" + model.getCode(),
				describeModel("Embedding model", model.getDescription(), config), config, "embedding model");
	}

	/** The default ranker model as an LLM endpoint, null when none is configured. */
	public static DataEndpoint rankerModel(IGRankerModelRuntimeConfigurationDao rankerModelsDao) {
		if (rankerModelsDao == null) {
			return null;
		}
		IGConfigurableRankerModel model;
		try {
			model = rankerModelsDao.defaultHandler();
		} catch (RuntimeException e) {
			return null;
		}
		if (model == null || model.getConfig() == null) {
			return null;
		}
		GBaseModelConfig config = model.getConfig();
		return modelEndpoint("ranker-model-" + model.getCode(),
				describeModel("Reranker model", model.getDescription(), config), config, "ranker model");
	}

	/** The internal services chat model, the default one when none is set for that use. */
	public static IGConfigurableChatModel utilityModel(IGChatModelRuntimeConfigurationDao chatModelsDao) {
		if (chatModelsDao == null) {
			return null;
		}
		try {
			IGConfigurableChatModel model = chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			return model != null ? model : chatModelsDao.defaultHandler();
		} catch (RuntimeException e) {
			return null;
		}
	}

	private static DataEndpoint modelEndpoint(String id, String description, GBaseModelConfig config,
			String fallbackProduct) {
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId(id);
		endpoint.setDescription(description);
		endpoint.setProduct(providerOf(config, fallbackProduct));
		endpoint.setEndpoint(config.getBaseUrl());
		endpoint.setTypes(list(MetaEndpointType.LLM_ENDPOINT));
		endpoint.setInput(true);
		endpoint.setOutput(true);
		endpoint.setPersonalData(false);
		if (notEmpty(config.getApiSecretCode())) {
			endpoint.setSecretReference(config.getApiSecretCode());
		}
		endpoint.setLocality(localityOf(endpoint.getEndpoint()));
		return endpoint;
	}

	/** The vector store the vectorizator publishes. */
	public static String vectorStoreRef() {
		return GStandardDataFlowEndpoints.vectorStoreRef();
	}

	/** The full-text index the full-text indexer publishes. */
	public static String fullTextIndexRef() {
		return GStandardDataFlowEndpoints.fullTextIndexRef();
	}

	/** The knowledge graph the graph extraction publishes. */
	public static String knowledgeGraphRef() {
		return GStandardDataFlowEndpoints.knowledgeGraphRef();
	}

	/**
	 * The legs a knowledge-base search runs on this installation, as
	 * {@code GDocumentsSearchServiceImpl} runs them: the semantic leg over the vector
	 * store always, the lexical leg over the full-text index only when the full-text
	 * search is deployed (OpenSearch, {@code ai.gebo.opensearch.enabled}), the graph
	 * leg over the knowledge graph only when the graph search is (Neo4j,
	 * {@code ai.gebo.neo4j.enabled}). Every reader of the knowledge bases - the chat
	 * profiles, deep search, the agents networks and their tools - searches through
	 * that service, so all of them read exactly these stores.
	 *
	 * @param fullText whether the full-text search is deployed
	 * @param graph    whether the knowledge graph search is deployed
	 */
	public static record KnowledgeBaseSearchLegs(boolean fullText, boolean graph) {

		/**
		 * The legs deployed here, read from the same optional beans the search service
		 * is given ({@code GeboRagSearchConfig}): present when the search runs the leg,
		 * absent when the installation does not deploy it.
		 */
		public static KnowledgeBaseSearchLegs deployed(
				ObjectProvider<IGFullTextSearchDocumentsCachedDao> fullTextSearchProvider,
				ObjectProvider<IKnowledgeGraphSearchService> knowledgeGraphSearchProvider) {
			return new KnowledgeBaseSearchLegs(available(fullTextSearchProvider), available(knowledgeGraphSearchProvider));
		}

		private static boolean available(ObjectProvider<?> provider) {
			try {
				return provider != null && provider.getIfAvailable() != null;
			} catch (RuntimeException e) {
				return false;
			}
		}
	}

	/** A transformation from a source endpoint to a destination one, with its engine. */
	public static void link(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(kind + "-" + key, description, list(from),
				list(to));
		flow.getEngines().add(engine);
		flow.getTransformations().add(DataTransformationInfo.of(kind + "-flow-" + key, description, engine,
				sourceQualifiedId, destQualifiedId));
	}

	/**
	 * A step carrying only a request from a source endpoint to a destination one, with
	 * its engine: the user's question embedded, a search query sent to a search
	 * service. Personal data do not travel along it
	 * ({@link DataTransformationInfo.Carried#REQUEST}).
	 */
	public static void request(GDataFlowMetaInfos flow, String kind, String key, String description,
			MetaEndpointType from, MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(kind + "-" + key, description, list(from),
				list(to));
		flow.getEngines().add(engine);
		flow.getTransformations().add(DataTransformationInfo.request(kind + "-flow-" + key, description, engine,
				sourceQualifiedId, destQualifiedId));
	}

	/**
	 * A step whose destination processes the content and passes it to no one else, with
	 * its engine: a chat's content to the model answering, ranking or analysing it, a
	 * model's tool arguments to a web search provider or an MCP server
	 * ({@link DataTransformationInfo.Carried#PROCESSED}).
	 */
	public static void processed(GDataFlowMetaInfos flow, String kind, String key, String description,
			MetaEndpointType from, MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(kind + "-" + key, description, list(from),
				list(to));
		flow.getEngines().add(engine);
		flow.getTransformations().add(DataTransformationInfo.processed(kind + "-flow-" + key, description, engine,
				sourceQualifiedId, destQualifiedId));
	}

	/** Adds the endpoint unless one with its id is already there. */
	public static void addUnique(GDataFlowMetaInfos flow, DataEndpoint endpoint) {
		for (DataEndpoint existing : flow.getDataEndpoints()) {
			if (existing.getId() != null && existing.getId().equals(endpoint.getId())) {
				return;
			}
		}
		flow.getDataEndpoints().add(endpoint);
	}

	/** Local when the locator says so, an external provider otherwise. */
	public static DataEndpointLocality localityOf(String locator) {
		DataEndpointLocality hint = DataEndpointLocality.hintFromLocator(locator);
		return hint == DataEndpointLocality.LOCAL_DEPLOYMENT ? DataEndpointLocality.LOCAL_DEPLOYMENT
				: DataEndpointLocality.EXTERNAL_PROVIDER;
	}

	public static String providerOf(GBaseModelConfig config, String fallback) {
		GBaseModelChoice choice = config.getChoosedModel();
		if (choice != null && choice.getMetaInfos() != null && notEmpty(choice.getMetaInfos().getProviderId())) {
			return choice.getMetaInfos().getProviderId();
		}
		return notEmpty(config.getModelTypeCode()) ? config.getModelTypeCode() : fallback;
	}

	public static String describeModel(String prefix, String modelDescription, GBaseModelConfig config) {
		GBaseModelChoice choice = config.getChoosedModel();
		String modelName = choice != null ? choice.getCode() : null;
		if (notEmpty(modelName)) {
			return prefix + " (" + modelName + ")";
		}
		return notEmpty(modelDescription) ? prefix + " - " + modelDescription : prefix;
	}

	public static boolean notEmpty(String s) {
		return s != null && !s.trim().isEmpty();
	}

	public static List<MetaEndpointType> list(MetaEndpointType... types) {
		return new ArrayList<MetaEndpointType>(List.of(types));
	}
}
