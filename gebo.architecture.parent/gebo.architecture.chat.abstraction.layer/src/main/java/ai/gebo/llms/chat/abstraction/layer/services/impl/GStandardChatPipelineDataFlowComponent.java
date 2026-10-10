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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.services.IGFullTextSearchDocumentsCachedDao;
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
import ai.gebo.llms.chat.abstraction.layer.services.impl.DataFlowEndpoints.KnowledgeBaseSearchLegs;

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
 * user's query flows to the embedding model to be vectorised and matched against
 * the knowledge base's vector store, and to the full-text index and the knowledge
 * graph when this installation deploys them (the legs
 * {@code GDocumentsSearchServiceImpl} runs, see
 * {@link DataFlowEndpoints.KnowledgeBaseSearchLegs}); to the utility
 * ({@code INTERNAL_SERVICES}) model for query rewriting and tool calls; and, with
 * the retrieved knowledge-base content, to the responder ({@code CHAT}) model that
 * writes the answer. Deep search ({@code FullReactiveDeepsearchWorker}) searches the
 * knowledge bases the same way and every enabled search service, plans and analyses
 * with the utility model, and writes the answer with the chat's own model. Each
 * model and provider is a distinct endpoint, located by the base URL the query is
 * actually sent to - the GDPR Art. 44 datum for the read side.
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
	private static final Logger LOGGER = LoggerFactory.getLogger(GStandardChatPipelineDataFlowComponent.class);

	public static final String CHAT_PIPELINE_MODULE = "chat-pipeline-module";
	public static final String STANDARD_CHAT_PIPELINE_COMPONENT = "standard-chat-pipeline";

	private final ObjectProvider<ChatProfilesRepository> chatProfilesRepositoryProvider;
	private final ObjectProvider<IGRuntimeChatProfileChatModelDao> chatProfileModelsDaoProvider;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider;
	private final ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider;
	private final ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider;
	private final ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider;
	private final ObjectProvider<IGFullTextSearchDocumentsCachedDao> fullTextSearchProvider;
	private final ObjectProvider<IKnowledgeGraphSearchService> knowledgeGraphSearchProvider;

	public GStandardChatPipelineDataFlowComponent(
			@Autowired ObjectProvider<ChatProfilesRepository> chatProfilesRepositoryProvider,
			@Autowired ObjectProvider<IGRuntimeChatProfileChatModelDao> chatProfileModelsDaoProvider,
			@Autowired ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDaoProvider,
			@Autowired ObjectProvider<IGEmbeddingModelRuntimeConfigurationDao> embeddingModelsDaoProvider,
			@Autowired ObjectProvider<IGRankerModelRuntimeConfigurationDao> rankerModelsDaoProvider,
			@Autowired ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider,
			@Autowired ObjectProvider<IGFullTextSearchDocumentsCachedDao> fullTextSearchProvider,
			@Autowired ObjectProvider<IKnowledgeGraphSearchService> knowledgeGraphSearchProvider) {
		this.chatProfilesRepositoryProvider = chatProfilesRepositoryProvider;
		this.chatProfileModelsDaoProvider = chatProfileModelsDaoProvider;
		this.chatModelsDaoProvider = chatModelsDaoProvider;
		this.embeddingModelsDaoProvider = embeddingModelsDaoProvider;
		this.rankerModelsDaoProvider = rankerModelsDaoProvider;
		this.searchServicesProvider = searchServicesProvider;
		this.fullTextSearchProvider = fullTextSearchProvider;
		this.knowledgeGraphSearchProvider = knowledgeGraphSearchProvider;
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

		// The stores a knowledge-base search reads here: the vector store always, the
		// full-text index and the knowledge graph only when deployed.
		final KnowledgeBaseSearchLegs legs = KnowledgeBaseSearchLegs.deployed(fullTextSearchProvider,
				knowledgeGraphSearchProvider);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reporting the chat data flow of " + profiles.size()
					+ " chat profile(s), knowledge base search legs:" + legs);
		}

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

		// the responder models of the profiles: deep search writes its answer with the
		// chat's own model, which is one of them
		final List<String> responders = new ArrayList<String>();
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

			// The lexical leg runs whenever the full-text search is deployed, whatever the
			// profile says: GDocumentsSearchServiceImpl reads no profile flag for it.
			if (legs.fullText()) {
				link(flow, "keyword-retrieval", profileCode, "Full-text knowledge-base retrieval",
						MetaEndpointType.CHAT_SESSION, MetaEndpointType.FULLTEXT_INDEX, flow.qualifiedId(query.getId()),
						fullTextIndexRef());
			}
			// The graph leg runs whenever the knowledge graph search is deployed.
			if (legs.graph()) {
				link(flow, "graph-retrieval", profileCode, "Knowledge-graph knowledge-base retrieval",
						MetaEndpointType.CHAT_SESSION, MetaEndpointType.GRAPH_DATABASE, flow.qualifiedId(query.getId()),
						DataFlowEndpoints.knowledgeGraphRef());
			}
			if (responder != null && !responders.contains(responder.getId())) {
				responders.add(responder.getId());
			}
		}

		addDeepSearch(flow, legs, serviceModel, rankerModel, responders,
				DataFlowEndpoints.embeddingModel(defaultEmbeddingModel(embeddingModelsDao)));

		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/**
	 * Adds the deep search a chat can run ({@code FullReactiveDeepsearchWorker}): its
	 * query searches the knowledge bases through the knowledge-base search (always a
	 * data source, the default one) and every enabled search service, the documents
	 * the search services return being chunked in the chunk cache for the request and
	 * disposed at its end; the utility model plans the searches and analyses what is
	 * found, the ranker (when configured) ranks the knowledge-base fragments, and the
	 * chat's own model, one of the profiles' responders, writes the answer.
	 */
	private void addDeepSearch(GDataFlowMetaInfos flow, KnowledgeBaseSearchLegs legs, DataEndpoint serviceModel,
			DataEndpoint rankerModel, List<String> responders, DataEndpoint embeddingModel) {
		DataEndpoint deepQuery = new DataEndpoint();
		deepQuery.setId("deep-search-query");
		deepQuery.setDescription("Deep-search query");
		deepQuery.setProduct("Chat pipeline");
		deepQuery.setEndpoint("chat-pipeline", "deep-search", null, null);
		deepQuery.setInput(true);
		deepQuery.setTypes(list(MetaEndpointType.CHAT_SESSION));
		deepQuery.setPersonalData(false);
		deepQuery.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
		flow.getDataEndpoints().add(deepQuery);
		final String deepQueryId = flow.qualifiedId(deepQuery.getId());

		// the knowledge bases, searched as every other reader searches them
		if (embeddingModel != null) {
			addUnique(flow, embeddingModel);
			link(flow, "deep-search-embed", "kb", "Deep search: knowledge-base queries embedding",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, deepQueryId,
					flow.qualifiedId(embeddingModel.getId()));
		}
		link(flow, "deep-search-semantic", "kb", "Deep search: semantic knowledge-base retrieval",
				MetaEndpointType.CHAT_SESSION, MetaEndpointType.VECTORIAL_DATABASE, deepQueryId, vectorStoreRef());
		if (legs.fullText()) {
			link(flow, "deep-search-fulltext", "kb", "Deep search: full-text knowledge-base retrieval",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.FULLTEXT_INDEX, deepQueryId, fullTextIndexRef());
		}
		if (legs.graph()) {
			link(flow, "deep-search-graph", "kb", "Deep search: knowledge-graph knowledge-base retrieval",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.GRAPH_DATABASE, deepQueryId,
					DataFlowEndpoints.knowledgeGraphRef());
		}
		if (rankerModel != null) {
			link(flow, "deep-search-rerank", "kb", "Deep search: ranking of the knowledge-base fragments found",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, deepQueryId,
					flow.qualifiedId(rankerModel.getId()));
		}
		if (serviceModel != null) {
			link(flow, "deep-search-analysis", "service", "Deep search: searches planned and documents found analysed",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, deepQueryId,
					flow.qualifiedId(serviceModel.getId()));
		}
		for (String responder : responders) {
			link(flow, "deep-search-answer", responder, "Deep search: answer written by the chat's own model",
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.LLM_ENDPOINT, deepQueryId,
					flow.qualifiedId(responder));
		}
		final int searchServices = addDeepSearchProviders(flow, deepQueryId);
		if (searchServices > 0) {
			link(flow, "deep-search-chunking", "results",
					"Deep search: documents read from the search services chunked for the request, disposed at its end",
					MetaEndpointType.WEB_SEARCH, MetaEndpointType.CHUNK, deepQueryId,
					GStandardDataFlowEndpoints.chunkCacheRef());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reported the deep search over the knowledge bases (legs:" + legs + "), " + searchServices
					+ " search service(s) and " + responders.size() + " responder model(s)");
		}
	}

	/**
	 * Adds each enabled search service as an endpoint the deep-search query reaches,
	 * using the same search-service repository the deep-search pipeline resolves its
	 * sources from ({@code DynamicReactiveDataSourceServicesProviderImpl}).
	 *
	 * @return how many enabled search services were added
	 */
	private int addDeepSearchProviders(GDataFlowMetaInfos flow, String deepQueryId) {
		ISearchServiceRepositoryPattern searchServices = searchServicesProvider.getIfAvailable();
		if (searchServices == null) {
			return 0;
		}
		List<ISearchService> services;
		try {
			services = searchServices.getImplementations();
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot enumerate the search services for the data flow register", e);
			return 0;
		}
		if (services == null) {
			return 0;
		}
		int added = 0;
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
			added++;
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
					MetaEndpointType.CHAT_SESSION, MetaEndpointType.WEB_SEARCH, deepQueryId,
					flow.qualifiedId(provider.getId()));
		}
		return added;
	}

	/** The default embedding model, the one a search with no profile of its own embeds with. */
	private static IGConfigurableEmbeddingModel defaultEmbeddingModel(
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao) {
		if (embeddingModelsDao == null) {
			return null;
		}
		try {
			return embeddingModelsDao.defaultHandler();
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the default embedding model for the data flow register", e);
			return null;
		}
	}

	private DataEndpoint describeChatModel(String kind, IGConfigurableChatModel model, String description) {
		return DataFlowEndpoints.chatModel(kind, model, description);
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
		return DataFlowEndpoints.embeddingModel(model);
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
		return DataFlowEndpoints.rankerModel(rankerModelsDaoProvider.getIfAvailable());
	}

	private IGConfigurableChatModel utilityModel(IGChatModelRuntimeConfigurationDao chatModelsDao) {
		return DataFlowEndpoints.utilityModel(chatModelsDao);
	}

	/** The vector store the vectorizator publishes; connected to when it exists. */
	private String vectorStoreRef() {
		return DataFlowEndpoints.vectorStoreRef();
	}

	private String fullTextIndexRef() {
		return DataFlowEndpoints.fullTextIndexRef();
	}

	private void link(GDataFlowMetaInfos flow, String kind, String key, String description, MetaEndpointType from,
			MetaEndpointType to, String sourceQualifiedId, String destQualifiedId) {
		DataFlowEndpoints.link(flow, kind, key, description, from, to, sourceQualifiedId, destQualifiedId);
	}

	private void addUnique(GDataFlowMetaInfos flow, DataEndpoint endpoint) {
		DataFlowEndpoints.addUnique(flow, endpoint);
	}

	private DataEndpointLocality localityOf(String locator) {
		return DataFlowEndpoints.localityOf(locator);
	}

	private String providerOf(GBaseModelConfig config, String fallback) {
		return DataFlowEndpoints.providerOf(config, fallback);
	}

	private String describeModel(String prefix, String modelDescription, GBaseModelConfig config) {
		return DataFlowEndpoints.describeModel(prefix, modelDescription, config);
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
