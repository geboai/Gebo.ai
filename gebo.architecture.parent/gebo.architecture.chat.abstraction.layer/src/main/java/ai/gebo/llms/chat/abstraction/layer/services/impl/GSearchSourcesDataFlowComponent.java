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
import ai.gebo.application.messaging.model.DataEndpointAccess;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.architecture.search.service.SearchSources;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.impl.GExternalSearchSecurityServiceImpl;
import ai.gebo.llms.deepsearch.service.impl.GExternalSearchSecurityServiceImpl.AccessRule;
import ai.gebo.model.base.GeboComponentInfo;

/**
 * A <b>symbolic</b> messaging component putting the search sources into the
 * compliance register, once for every reader pointing to them: the deep search, and
 * the searcher agents and search tools of the agents networks.
 *
 * <p>
 * A search source is one system a search service searches: a configured Jira,
 * Confluence or SharePoint system searched live (the
 * {@code GAbstractRemoteVirtualFilesystemSearchService}s search the systems their
 * content handler is configured with), a web search account. It is reported when
 * someone can search it, as {@code GExternalSearchSecurityServiceImpl} decides: the
 * search service is enabled and has systems (an administrator can always search
 * them, the other users as the deep search users/groups access says, open to
 * everyone where it is not configured). A system searched live is located by its
 * configured address, a web search service is a third party.
 * </p>
 *
 * <p>
 * A search source holds no personal data by itself: it receives them only from a
 * data source flagged as holding them on the same system, which the content handler
 * links to the live search of its system, since that search returns the content
 * the data source ingests.
 * </p>
 */
@Component
public class GSearchSourcesDataFlowComponent implements IGMessageEmitter {
	private static final Logger LOGGER = LoggerFactory.getLogger(GSearchSourcesDataFlowComponent.class);

	/** One system a search service searches: its id in the register and its type. */
	public static record SearchSource(String qualifiedId, MetaEndpointType type) {
	}

	private final ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider;

	// The deep search configuration, deciding who may search each source. Field
	// injected and optional: without it the sources are reported with no access.
	@Autowired(required = false)
	ObjectProvider<IGDeepSearchConfigProvider> deepSearchConfigProvider;

	public GSearchSourcesDataFlowComponent(
			@Autowired ObjectProvider<ISearchServiceRepositoryPattern> searchServicesProvider) {
		this.searchServicesProvider = searchServicesProvider;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardDataFlowEndpoints.SEARCH_SOURCES_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardDataFlowEndpoints.SEARCH_SOURCES_COMPONENT;
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
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));
		flow.setDescription("Live search sources");
		final List<ISearchService> services = searchableServices(searchServicesProvider.getIfAvailable());
		final DeepSearchConfig deepSearchConfig = deepSearchConfig();
		for (ISearchService service : services) {
			final List<DataEndpointAccess> access = deepSearchConfig != null ? access(service, deepSearchConfig)
					: null;
			for (SearchableSystemMetaData system : SearchSources.systems(service)) {
				DataEndpoint endpoint = endpoint(service, system);
				endpoint.setAccess(access);
				flow.getDataEndpoints().add(endpoint);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reporting " + flow.getDataEndpoints().size() + " search source(s) of " + services.size()
					+ " searchable search service(s)");
		}
		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/** The deep search configuration, null when it cannot be read here. */
	private DeepSearchConfig deepSearchConfig() {
		final IGDeepSearchConfigProvider provider = deepSearchConfigProvider != null
				? deepSearchConfigProvider.getIfAvailable()
				: null;
		if (provider == null) {
			return null;
		}
		try {
			return provider.get();
		} catch (RuntimeException e) {
			LOGGER.error("Cannot read the deep search configuration for the search sources' access", e);
			return null;
		}
	}

	/**
	 * Who may search the systems of a search service, by the same rule a search is
	 * allowed by ({@link GExternalSearchSecurityServiceImpl#accessRule}) - in deep
	 * search, by the search agents and by the search tools alike: the users
	 * and groups of its row of the per data source grid, or of the configuration for
	 * every source, else the default. One rule for every system of the service: the
	 * grid has one row per service.
	 */
	static List<DataEndpointAccess> access(ISearchService service, DeepSearchConfig deepSearchConfig) {
		final AccessRule rule = GExternalSearchSecurityServiceImpl.accessRule(deepSearchConfig, service.getId());
		// deep search, the search agents and the search tools all check it
		final String scope = "Searching it in deep search, with the search agents and with the search tools";
		final DataEndpointAccess access;
		switch (rule.origin()) {
		case PER_DATA_SOURCE:
			access = DataEndpointAccess.of(rule.governing(), "Deep search settings - access to '" + service.getId() + "'", scope,
					DataEndpointAccess.Mechanism.USERS_GROUPS);
			break;
		case EVERY_SOURCE:
			access = DataEndpointAccess.of(rule.governing(), "Deep search settings - access to every external source", scope,
					DataEndpointAccess.Mechanism.USERS_GROUPS);
			break;
		default:
			access = new DataEndpointAccess();
			access.setGrantedBy("Deep search settings - default for external sources");
			access.setScope(scope);
			access.setMechanism(DataEndpointAccess.Mechanism.USERS_GROUPS);
			access.setAccessibleToAll(rule.openByDefault());
			access.setNote(rule.openByDefault()
					? "No users or groups are given access to it, so it is open to every user "
							+ "(externalSourceSearchEnabledByDefault)."
					: "No users or groups are given access to it and external sources are closed by default: "
							+ "only administrators can search it.");
			break;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Search service " + service.getId() + " access: " + rule.origin() + " open by default:"
					+ rule.openByDefault());
		}
		return new ArrayList<DataEndpointAccess>(List.of(access));
	}

	/** The search services someone can search (see {@link SearchSources#searchable}). */
	public static List<ISearchService> searchableServices(ISearchServiceRepositoryPattern searchServices) {
		return SearchSources.searchable(searchServices);
	}

	/** The search sources of a search service, as its readers point to them. */
	public static List<SearchSource> sourcesOf(ISearchService service) {
		final List<SearchSource> out = new ArrayList<>();
		final MetaEndpointType type = typeOf(service);
		for (SearchableSystemMetaData system : SearchSources.systems(service)) {
			out.add(new SearchSource(
					GStandardDataFlowEndpoints.searchSourceRef(service.getId(), SearchSources.systemCode(system)), type));
		}
		return out;
	}

	private static MetaEndpointType typeOf(ISearchService service) {
		return service instanceof AbstractWebSearchServiceImpl ? MetaEndpointType.WEB_SEARCH : MetaEndpointType.DOCUMENTS;
	}

	private static DataEndpoint endpoint(ISearchService service, SearchableSystemMetaData system) {
		final boolean web = service instanceof AbstractWebSearchServiceImpl;
		final String product = notEmpty(service.getProductId()) ? service.getProductId() : "search service";
		final String code = SearchSources.systemCode(system);
		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId(GStandardDataFlowEndpoints.searchSourceId(service.getId(), code));
		final String serviceName = notEmpty(service.getDescription()) ? service.getDescription() : product;
		endpoint.setDescription(notEmpty(system.getDescription()) ? serviceName + " - " + system.getDescription()
				: serviceName);
		endpoint.setProduct(product);
		// a system searched live is where its configuration says; setEndpoint strips any
		// credential from the address
		final String baseUri = system.getSystemConfigurationReference() instanceof GContentManagementSystem configured
				? configured.getBaseUri()
				: null;
		endpoint.setEndpoint(notEmpty(baseUri) ? baseUri : product + ":" + code);
		endpoint.setInput(true);
		endpoint.setOutput(false);
		endpoint.setTypes(new ArrayList<MetaEndpointType>(List.of(typeOf(service))));
		// personal data only from a data source flagged on the same system, by propagation
		endpoint.setPersonalData(false);
		// a hosted web search API is a third party; a self-hosted SearXNG is the local
		// exception, but from here it is indistinguishable, so this errs towards
		// flagging the transfer
		endpoint.setLocality(web ? DataEndpointLocality.EXTERNAL_PROVIDER
				: DataEndpointLocality.hintFromLocator(endpoint.getEndpoint()));
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Search source " + endpoint.getId() + " at " + endpoint.getEndpoint() + " locality:"
					+ endpoint.getLocality());
		}
		return endpoint;
	}

	private static boolean notEmpty(String s) {
		return s != null && !s.trim().isEmpty();
	}
}
