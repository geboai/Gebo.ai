/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

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
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.model.base.GeboComponentInfo;

/**
 * A <b>symbolic</b> messaging component that puts the agent-network responder's
 * data flow into the compliance register.
 *
 * <p>
 * A network of agents can act as the chat responder: its finder agents are wired
 * to the internal knowledge-base search service (semantic over the vector store,
 * lexical over the full-text index) and to the external web-search services. Like
 * the chat pipeline, the network is a set of services rather than a broker
 * component, so this stand-in emitter exists only to report - for each configured
 * network - the query fan-out its finders perform: query -&gt; internal KB search
 * (vector store, full-text index) and query -&gt; each enabled external web-search
 * provider.
 * </p>
 *
 * <p>
 * Every collaborator is resolved lazily through an {@code ObjectProvider}, for the
 * same cycle-avoidance reason as the other symbolic reporters.
 * </p>
 */
@Component
public class GAgentsNetworkDataFlowComponent implements IGMessageEmitter {

	public static final String AGENT_NETWORK_MODULE = "agent-network-module";
	public static final String AGENTS_NETWORK_RESPONDER_COMPONENT = "agents-network-responder";

	private final ObjectProvider<IAgentsNetworkDao> agentsNetworkDaoProvider;
	private final ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider;

	public GAgentsNetworkDataFlowComponent(@Autowired ObjectProvider<IAgentsNetworkDao> agentsNetworkDaoProvider,
			@Autowired ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider) {
		this.agentsNetworkDaoProvider = agentsNetworkDaoProvider;
		this.searchServicesProvider = searchServicesProvider;
	}

	@Override
	public String getMessagingModuleId() {
		return AGENT_NETWORK_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return AGENTS_NETWORK_RESPONDER_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of();
	}

	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		IAgentsNetworkDao agentsNetworkDao = agentsNetworkDaoProvider.getIfAvailable();
		if (agentsNetworkDao == null) {
			return null;
		}
		List<GAgentsNetwork> networks;
		try {
			networks = agentsNetworkDao.getConfigurations();
		} catch (RuntimeException e) {
			return null;
		}
		if (networks == null || networks.isEmpty()) {
			return null;
		}

		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		List<ISearchService> webProviders = enabledWebSearchProviders();

		for (GAgentsNetwork network : networks) {
			if (network == null || network.getCode() == null) {
				continue;
			}
			String code = network.getCode();

			DataEndpoint query = new DataEndpoint();
			query.setId("network-query-" + code);
			query.setDescription("Agent-network query - '" + code + "'");
			query.setProduct("Network of agents");
			query.setEndpoint("agents-network", code, null, null);
			query.setInput(true);
			query.setTypes(list(MetaEndpointType.CHAT_SESSION));
			query.setPersonalData(false);
			query.setLocality(DataEndpointLocality.LOCAL_DEPLOYMENT);
			flow.getDataEndpoints().add(query);

			// Finder agents on the internal knowledge-base search service: semantic
			// over the vector store, lexical over the full-text index. Both are drawn
			// only when the store actually exists (the view drops edges to absent
			// endpoints), keeping this faithful to what is configured.
			link(flow, "kb-semantic", code, "Finder: semantic knowledge-base search", MetaEndpointType.CHAT_SESSION,
					MetaEndpointType.VECTORIAL_DATABASE, flow.qualifiedId(query.getId()), vectorStoreRef());
			link(flow, "kb-fulltext", code, "Finder: full-text knowledge-base search", MetaEndpointType.CHAT_SESSION,
					MetaEndpointType.FULLTEXT_INDEX, flow.qualifiedId(query.getId()), fullTextIndexRef());

			// Finder agents on the external web-search services.
			for (ISearchService provider : webProviders) {
				String providerEndpointId = "web-search-" + safe(provider.getId(), safe(provider.getProductId(), ""));
				addUnique(flow, webProviderEndpoint(provider));
				link(flow, "web-" + providerEndpointId, code, "Finder: external web search", MetaEndpointType.CHAT_SESSION,
						MetaEndpointType.WEB_SEARCH, flow.qualifiedId(query.getId()), flow.qualifiedId(providerEndpointId));
			}
		}

		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	private List<ISearchService> enabledWebSearchProviders() {
		ISearchServiceRepositoryPattern searchServices = searchServicesProvider.getIfAvailable();
		if (searchServices == null) {
			return List.of();
		}
		try {
			List<ISearchService> all = searchServices.getImplementations();
			if (all == null) {
				return List.of();
			}
			return all.stream().filter(s -> {
				try {
					return s != null && s.isEnabled();
				} catch (Exception e) {
					return false;
				}
			}).toList();
		} catch (RuntimeException e) {
			return List.of();
		}
	}

	private DataEndpoint webProviderEndpoint(ISearchService provider) {
		String product = safe(provider.getProductId(), "web search");
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("web-search-" + safe(provider.getId(), product));
		endpoint.setDescription(safe(provider.getDescription(), product));
		endpoint.setProduct(product);
		endpoint.setEndpoint(product + ":" + safe(provider.getId(), ""));
		endpoint.setInput(true);
		endpoint.setOutput(true);
		endpoint.setTypes(list(MetaEndpointType.WEB_SEARCH));
		endpoint.setPersonalData(false);
		endpoint.setLocality(DataEndpointLocality.EXTERNAL_PROVIDER);
		return endpoint;
	}

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

	private static String safe(String s, String fallback) {
		return s != null && !s.trim().isEmpty() ? s : fallback;
	}

	private static List<MetaEndpointType> list(MetaEndpointType... types) {
		return new java.util.ArrayList<MetaEndpointType>(List.of(types));
	}
}
