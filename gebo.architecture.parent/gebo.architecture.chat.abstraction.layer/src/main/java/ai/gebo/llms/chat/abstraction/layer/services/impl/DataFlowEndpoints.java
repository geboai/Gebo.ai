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

import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.model.base.GeboComponentInfo;

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
		return modelEndpoint(kind + "-model-" + model.getCode(),
				describeModel(description, model.getDescription(), model.getConfig()), model.getConfig(),
				"chat model");
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

	/** The vector store the vectorizator publishes; linked to when it exists. */
	public static String vectorStoreRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_COMPONENT), "vector-store");
	}

	/** The full-text index the full-text indexer publishes; linked to when it exists. */
	public static String fullTextIndexRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.FULLTEXT_MODULE,
				GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT), "fulltext-index");
	}

	/** The knowledge graph the graph extraction publishes; linked to when it exists. */
	public static String graphStoreRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE,
				GStandardModulesConstraints.KNOWLEDGE_GRAPH_COMPONENT), "graph-store");
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
