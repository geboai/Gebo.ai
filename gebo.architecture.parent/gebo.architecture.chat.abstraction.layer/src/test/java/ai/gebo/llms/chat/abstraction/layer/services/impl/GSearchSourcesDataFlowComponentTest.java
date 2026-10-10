/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

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
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.DeepSearchDataSourceAccess;

/**
 * Pins the search sources put in the register once: one per system of every search
 * service someone can search (enabled, with systems), a configured system searched
 * live located by its own address and named by its own code (as its data sources
 * name it), a web search account a third party, none of them holding personal data
 * by itself.
 */
class GSearchSourcesDataFlowComponentTest {

	@SuppressWarnings("rawtypes")
	private static SearchableSystemMetaData searched(Object configuration, String code, String description) {
		SearchableSystemMetaData system = new SearchableSystemMetaData();
		system.setCode(code);
		system.setDescription(description);
		system.setSystemConfigurationReference(configuration);
		return system;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T extends ISearchService> T service(Class<T> type, String id, boolean enabled,
			SearchableSystemMetaData... systems) throws Exception {
		T service = mock(type);
		when(service.getId()).thenReturn(id);
		when(service.getProductId()).thenReturn(id + "-product");
		when(service.getDescription()).thenReturn(id + " search");
		when(service.isEnabled()).thenReturn(enabled);
		when(service.getSearchableSystems()).thenReturn((List) List.of(systems));
		return service;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static GDataFlowMetaInfos flowOf(ISearchService... services) {
		ISearchServiceRepositoryPattern repository = mock(ISearchServiceRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn((List) List.of(services));
		ObjectProvider<ISearchServiceRepositoryPattern> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(repository);
		return new GSearchSourcesDataFlowComponent(provider).getDataFlowMetaInfos();
	}

	private static DataEndpoint endpoint(GDataFlowMetaInfos flow, String id) {
		return flow.getDataEndpoints().stream().filter(x -> id.equals(x.getId())).findFirst().orElseThrow();
	}

	@Test
	void everySearchableSystemIsReportedOnceAndLocated() throws Exception {
		GContentManagementSystem jira = new GContentManagementSystem();
		jira.setCode("jira-prod");
		jira.setBaseUri("http://localhost:8080");
		ISearchService jiraSearch = service(ISearchService.class, "jira-search", true,
				searched(jira, "jira-module.jira-search<->jira-prod", "Production Jira"));
		AbstractWebSearchServiceImpl google = service(AbstractWebSearchServiceImpl.class, "google", true,
				searched(null, "google-account", null));
		ISearchService disabled = service(ISearchService.class, "disabled", false, searched(null, "x", null));
		ISearchService withoutSystems = service(ISearchService.class, "nowhere", true);

		GDataFlowMetaInfos flow = flowOf(jiraSearch, google, disabled, withoutSystems);

		assertEquals(2, flow.getDataEndpoints().size());
		// named by the configured system's own code, as a data source on it names it
		DataEndpoint live = endpoint(flow, GStandardDataFlowEndpoints.searchSourceId("jira-search", "jira-prod"));
		assertEquals(GStandardDataFlowEndpoints.searchSourceRef("jira-search", "jira-prod"),
				flow.qualifiedId(live.getId()));
		assertEquals("jira-search search - Production Jira", live.getDescription());
		assertEquals(List.of(MetaEndpointType.DOCUMENTS), live.getTypes());
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT, live.getLocality());
		DataEndpoint web = endpoint(flow, GStandardDataFlowEndpoints.searchSourceId("google", "google-account"));
		assertEquals(List.of(MetaEndpointType.WEB_SEARCH), web.getTypes());
		assertEquals(DataEndpointLocality.EXTERNAL_PROVIDER, web.getLocality());
		// personal data only from a data source flagged on the same system, by propagation
		assertFalse(live.isPersonalData());
		assertFalse(web.isPersonalData());
	}

	@Test
	void nothingSearchableNothingReported() throws Exception {
		assertNull(flowOf(service(ISearchService.class, "disabled", false, searched(null, "x", null))));
	}

	@Test
	void withoutDeepSearchSettingsASourceIsOpenToEveryoneByDefault() throws Exception {
		ISearchService jira = service(ISearchService.class, "jira-search", true);

		DataEndpointAccess access = GSearchSourcesDataFlowComponent.access(jira, new DeepSearchDefaultConfig()).get(0);

		assertTrue(access.isAccessibleToAll());
		assertEquals("Deep search settings - default for external sources", access.getGrantedBy());
		assertEquals(DataEndpointAccess.Mechanism.USERS_GROUPS, access.getMechanism());
	}

	@Test
	void theRowOfTheSourceDecidesWhenTheGridIsInUse() throws Exception {
		ISearchService jira = service(ISearchService.class, "jira-search", true);
		DeepSearchConfig config = new DeepSearchConfig();
		config.setAccessibleGroups(List.of("everyone-else"));
		config.setPerDataSourceConfigured(true);
		config.getDataSourcesAccesses()
				.add(new DeepSearchDataSourceAccess(List.of("hr"), List.of("anna@example.com"), null, "jira-search"));

		DataEndpointAccess access = GSearchSourcesDataFlowComponent.access(jira, config).get(0);

		assertEquals("Deep search settings - access to 'jira-search'", access.getGrantedBy());
		assertEquals(List.of("hr"), access.getGroups());
		assertEquals(List.of("anna@example.com"), access.getUsers());
		assertFalse(access.isAccessibleToAll());
	}

	@Test
	void theSettingsForEverySourceDecideWithoutTheGrid() throws Exception {
		ISearchService jira = service(ISearchService.class, "jira-search", true);
		DeepSearchConfig config = new DeepSearchConfig();
		config.setAccessibleGroups(List.of("hr"));
		config.setExternalSourceSearchEnabledByDefault(false);

		DataEndpointAccess access = GSearchSourcesDataFlowComponent.access(jira, config).get(0);

		assertEquals("Deep search settings - access to every external source", access.getGrantedBy());
		assertEquals(List.of("hr"), access.getGroups());
	}
}
