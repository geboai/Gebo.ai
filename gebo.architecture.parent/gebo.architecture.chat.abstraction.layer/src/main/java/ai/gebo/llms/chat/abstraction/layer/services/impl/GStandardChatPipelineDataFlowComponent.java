/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;

/**
 * A <b>symbolic</b> messaging component that puts the read-side (chat / RAG) data
 * flow into the compliance register.
 *
 * <p>
 * The chat pipeline is a set of services, not a broker component, so on its own
 * it never appears in {@code IGMessageBroker#getSystemsInfo()}. This class plugs a
 * stand-in emitter onto it - it emits nothing, it exists only to be registered by
 * {@code MessageBrokeringAssembler} and to answer
 * {@link #getDataFlowMetaInfos()} - so the query path shows up alongside the
 * ingestion path in the same register.
 * </p>
 *
 * <p>
 * What it reports, grounded in {@code GRagChatServiceImpl} /
 * {@code GDocumentsSearchServiceImpl}: for every configured chat profile, the
 * user's query (personal data) flows to the embedding model to be vectorised and
 * matched against the knowledge base's vector store; optionally to the full-text
 * index when {@code useAlsoKeywordSearch} is set; to the utility
 * ({@code INTERNAL_SERVICES}) model for query rewriting and tool calls; and, with
 * the retrieved knowledge-base content, to the responder ({@code CHAT}) model that
 * writes the answer. Deep search additionally sends the query to the configured
 * external web-search providers. Each model and provider is a distinct endpoint,
 * located by the base URL the query is actually sent to - the GDPR Art. 44 datum
 * for the read side.
 * </p>
 *
 * <p>
 * Every collaborator is resolved lazily at report time through an
 * {@code ObjectProvider}: the chat/LLM/search services form a dense dependency
 * web, and a symbolic reporter must never pull any of it into its own eager
 * construction graph (that is the bean cycle the content-handler reporter also
 * has to avoid). The register is read long after startup, so lazy resolution is
 * both safe and sufficient.
 * </p>
 */
@Component
public class GStandardChatPipelineDataFlowComponent implements IGMessageEmitter {

	public static final String CHAT_PIPELINE_MODULE = "chat-pipeline-module";
	public static final String STANDARD_CHAT_PIPELINE_COMPONENT = "standard-chat-pipeline";

	private final ObjectProvider<ChatProfilesRepository> chatProfilesRepositoryProvider;
	private final ObjectProvider<IGRuntimeChatProfileChatModelDao> chatProfileModelsDaoProvider;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider;
	private final ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider;
	private final ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider;
	private final ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider;

	public GStandardChatPipelineDataFlowComponent(
			@Autowired ObjectProvider<ChatProfilesRepository> chatProfilesRepositoryProvider,
			@Autowired ObjectProvider<IGRuntimeChatProfileChatModelDao> chatProfileModelsDaoProvider,
			@Autowired ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider,
			@Autowired ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider,
			@Autowired ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider,
			@Autowired ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider) {
		this.chatProfilesRepositoryProvider = chatProfilesRepositoryProvider;
		this.chatProfileModelsDaoProvider = chatProfileModelsDaoProvider;
		this.chatModelsDaoProvider = chatModelsDaoProvider;
		this.embeddingModelsDaoProvider = embeddingModelsDaoProvider;
		this.rankerModelsDaoProvider = rankerModelsDaoProvider;
		this.searchServicesProvider = searchServicesProvider;
	}

	@Override
	public String getMessagingModuleId() {
		return CHAT_PIPELINE_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return STANDARD_CHAT_PIPELINE_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		// Symbolic: it never emits real traffic, it only reports its data flows.
		return List.of();
	}

	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		ChatProfilesRepository chatProfilesRepository = chatProfilesRepositoryProvider.getIfAvailable();
		IGRuntimeChatProfileChatModelDao chatProfileModelsDao = chatProfileModelsDaoProvider.getIfAvailable();
		if (chatProfilesRepository == null || chatProfileModelsDao == null) {
			return null;
		}
		IGChatModelRuntimeConfigurationDao chatModelsDao = chatModelsDaoProvider.getIfAvailable();
		IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao = embeddingModelsDaoProvider.getIfAvailable();

		List<GChatProfileConfiguration> profiles;
		try {
			profiles = chatProfilesRepository.findAll();
		} catch (RuntimeException e) {
			return null;
		}
		if (profiles == null || profiles.isEmpty()) {
			return null;
		}

		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		// The utility model is shared across profiles - report it once and connect
		// each profile's query to it.
		DataEndpoint serviceModel = describeChatModel("service", utilityModel(chatModelsDao),
				"Utility model (query rewriting, tool calls)");
		if (serviceModel != null) {
			addUnique(flow, serviceModel);
		}

		// The reranker scores retrieved chunks against the query before they reach
		// the responder; shared across profiles like the utility model.
		DataEndpoint rankerModel = describeRankerModel();
		if (rankerModel != null) {
			addUnique(flow, rankerModel);
		}

		for (GChatProfileConfiguration profile : profiles) {
			if (profile == null || profile.getCode() == null) {
				continue;
			}
			String profileCode = profile.getCode();

			DataEndpoint query = new DataEndpoint();
			query.setId("query-" + profileCode);
			query.setDescription("Chat query - profile '" + profileCode + "'");
			query.setProduct("Chat pipeline");
			query.setEndpoint("chat-profile", profileCode, null, null);
			query.setInput(true);
			query.setTypes(list(MetaEndpointType.CHAT_SESSION));
			query.setPersonalData(false);
			query.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
			flow.getDataEndpoints().add(query);

			// Responder (CHAT) model - gets the query plus retrieved KB content.
			DataEndpoint responder = describeChatModel("chat", responderModel(profile, chatProfileModelsDao),
					"Responder chat model");
			if (responder != null) {
				addUnique(flow, responder);
				link(flow, "answer", profileCode, "RAG answer generation (query + retrieved content)",
						MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, flow.qualifiedId(query.getId()),
						flow.qualifiedId(responder.getId()));
			}
			if (serviceModel != null) {
				link(flow, "rewrite", profileCode, "Query rewriting / tool calls", MetaEndpointType.CHAT_SESSION,
						MetaEndpointType.LLM_ENDPOINT, flow.qualifiedId(query.getId()),
						flow.qualifiedId(serviceModel.getId()));
			}
			if (rankerModel != null) {
				link(flow, "rerank", profileCode, "Reranking retrieved chunks", MetaEndpointType.CHAT_SESSION,
						MetaEndpointType.LLM_ENDPOINT, flow.qualifiedId(query.getId()),
						flow.qualifiedId(rankerModel.getId()));
			}

			// Embedding model - vectorises the query for semantic retrieval.
			DataEndpoint embedding = describeEmbeddingModel(profile, embeddingModelsDao);
			if (embedding != null) {
				addUnique(flow, embedding);
				link(flow, "embed", profileCode, "Query embedding", MetaEndpointType.CHAT_SESSION,
						MetaEndpointType.LLM_ENDPOINT, flow.qualifiedId(query.getId()),
						flow.qualifiedId(embedding.getId()));
				// Semantic retrieval reads the vector store the vectorizator owns.
				link(flow, "semantic-retrieval", profileCode, "Semantic knowledge-base retrieval",
						MetaEndpointType.LLM_ENDPOINT, MetaEndpointType.VECTORIAL_DATABASE,
						flow.qualifiedId(embedding.getId()), vectorStoreRef());
			}

			// Optional lexical retrieval over the full-text index.
			if (Boolean.TRUE.equals(profile.getUseAlsoKeywordSearch())) {
				link(flow, "keyword-retrieval", profileCode, "Full-text knowledge-base retrieval",
						MetaEndpointType.CHAT_SESSION, MetaEndpointType.FULLTEXT_INDEX, flow.qualifiedId(query.getId()),
						fullTextIndexRef());
			}
		}

		addDeepSearchProviders(flow);

		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/**
	 * Adds each enabled external web-search provider as an endpoint the deep-search
	 * query reaches, using the same search-service repository the deep-search
	 * pipeline resolves its sources from.
	 */
	private void addDeepSearchProviders(GDataFlowMetaInfos flow) {
		ISearchServiceRepositoryPattern searchServices = searchServicesProvider.getIfAvailable();
		if (searchServices == null) {
			return;
		}
		List<ISearchService> services;
		try {
			services = searchServices.getImplementations();
		} catch (RuntimeException e) {
			return;
		}
		if (services == null) {
			return;
		}
		boolean queryAdded = false;
		DataEndpoint deepQuery = null;
		for (ISearchService service : services) {
			if (service == null) {
				continue;
			}
			boolean enabled;
			try {
				enabled = service.isEnabled();
			} catch (Exception e) {
				enabled = false;
			}
			if (!enabled) {
				continue;
			}
			if (!queryAdded) {
				deepQuery = new DataEndpoint();
				deepQuery.setId("deep-search-query");
				deepQuery.setDescription("Deep-search query");
				deepQuery.setProduct("Chat pipeline");
				deepQuery.setEndpoint("chat-pipeline", "deep-search", null, null);
				deepQuery.setInput(true);
				deepQuery.setTypes(list(MetaEndpointType.CHAT_SESSION));
				deepQuery.setPersonalData(false);
				deepQuery.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
				flow.getDataEndpoints().add(deepQuery);
				queryAdded = true;
			}
			String product = safe(service.getProductId(), "web search");
			DataEndpoint provider = new DataEndpoint();
			provider.setId("web-search-" + safe(service.getId(), product));
			provider.setDescription(safe(service.getDescription(), product));
			provider.setProduct(product);
			provider.setEndpoint(product + ":" + safe(service.getId(), ""));
			provider.setInput(true);
			provider.setOutput(true);
			provider.setTypes(list(MetaEndpointType.WEB_SEARCH));
			provider.setPersonalData(false);
			// A hosted web-search API is a third party; a self-hosted SearXNG is the
			// local exception, but from here it is indistinguishable, so this errs
			// towards flagging the transfer.
			provider.setLocality(DataEndpointLocality.EXTERNAL_PROVIDER);
			addUnique(flow, provider);
			link(flow, "deep-search", provider.getId(), "Deep search (query sent to external provider)",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.WEB_SEARCH, flow.qualifiedId(deepQuery.getId()),
					flow.qualifiedId(provider.getId()));
		}
	}

	private DataEndpoint describeChatModel(String kind, IGConfigurableChatModel model, String description) {
		if (model == null || model.getConfig() == null) {
			return null;
		}
		GBaseModelConfig config = model.getConfig();
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId(kind + "-model-" + model.getCode());
		endpoint.setDescription(describeModel(description, model.getDescription(), config));
		endpoint.setProduct(providerOf(config, "chat model"));
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

	private DataEndpoint describeEmbeddingModel(GChatProfileConfiguration profile,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao) {
		if (embeddingModelsDao == null) {
			return null;
		}
		IGConfigurableEmbeddingModel model = null;
		try {
			if (profile.getEmbeddingModelReference() != null) {
				model = embeddingModelsDao.findByModelReference(profile.getEmbeddingModelReference());
			}
			if (model == null) {
				model = embeddingModelsDao.defaultHandler();
			}
		} catch (RuntimeException e) {
			return null;
		}
		if (model == null || model.getConfig() == null) {
			return null;
		}
		GBaseModelConfig config = model.getConfig();
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("embedding-model-" + model.getCode());
		endpoint.setDescription(describeModel("Embedding model", model.getDescription(), config));
		endpoint.setProduct(providerOf(config, "embedding model"));
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

	private IGConfigurableChatModel responderModel(GChatProfileConfiguration profile,
			IGRuntimeChatProfileChatModelDao chatProfileModelsDao) {
		try {
			IGChatProfileChatModel resolved = chatProfileModelsDao.getChatModel(profile);
			return resolved != null ? resolved.getChatModel() : null;
		} catch (Exception e) {
			return null;
		}
	}

	private DataEndpoint describeRankerModel() {
		IGRankerModelRuntimeConfigurationDao rankerModelsDao = rankerModelsDaoProvider.getIfAvailable();
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
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("ranker-model-" + model.getCode());
		endpoint.setDescription(describeModel("Reranker model", model.getDescription(), config));
		endpoint.setProduct(providerOf(config, "ranker model"));
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

	private IGConfigurableChatModel utilityModel(IGChatModelRuntimeConfigurationDao chatModelsDao) {
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

	/** The vector store the vectorizator publishes; connected to when it exists. */
	private String vectorStoreRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_COMPONENT), "vector-store");
	}

	private String fullTextIndexRef() {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(GStandardModulesConstraints.FULLTEXT_MODULE,
				GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT), "fulltext-index");
	}

	private void link(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(kind + "-" + key, description, list(from),
				list(to));
		flow.getEngines().add(engine);
		flow.getTransformations()
				.add(DataTransformationInfo.of(kind + "-flow-" + key, description, engine, sourceQualifiedId,
						destQualifiedId));
	}

	private void addUnique(GDataFlowMetaInfos flow, DataEndpoint endpoint) {
		for (DataEndpoint existing : flow.getDataEndpoints()) {
			if (existing.getId() != null && existing.getId().equals(endpoint.getId())) {
				return;
			}
		}
		flow.getDataEndpoints().add(endpoint);
	}

	private DataEndpointLocality localityOf(String locator) {
		DataEndpointLocality hint = DataEndpointLocality.hintFromLocator(locator);
		return hint == DataEndpointLocality.LOCAL_DEPLOYMENT ? DataEndpointLocality.LOCAL_DEPLOYMENT
				: DataEndpointLocality.EXTERNAL_PROVIDER;
	}

	private String providerOf(GBaseModelConfig config, String fallback) {
		GBaseModelChoice choice = config.getChoosedModel();
		if (choice != null && choice.getMetaInfos() != null && notEmpty(choice.getMetaInfos().getProviderId())) {
			return choice.getMetaInfos().getProviderId();
		}
		return notEmpty(config.getModelTypeCode()) ? config.getModelTypeCode() : fallback;
	}

	private String describeModel(String prefix, String modelDescription, GBaseModelConfig config) {
		GBaseModelChoice choice = config.getChoosedModel();
		String modelName = choice != null ? choice.getCode() : null;
		if (notEmpty(modelName)) {
			return prefix + " (" + modelName + ")";
		}
		return notEmpty(modelDescription) ? prefix + " - " + modelDescription : prefix;
	}

	private static boolean notEmpty(String s) {
		return s != null && !s.trim().isEmpty();
	}

	private static String safe(String s, String fallback) {
		return notEmpty(s) ? s : fallback;
	}

	private static List<MetaEndpointType> list(MetaEndpointType... types) {
		return new java.util.ArrayList<MetaEndpointType>(List.of(types));
	}
}
