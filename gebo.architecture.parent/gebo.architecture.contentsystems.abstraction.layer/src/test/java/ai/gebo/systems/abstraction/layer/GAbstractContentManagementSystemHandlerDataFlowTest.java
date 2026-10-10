/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GStandardDataFlowEndpoints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;

/**
 * Pins the link a content handler puts in the register from each of its data
 * sources to the live search of the source's system: a search service searching the
 * configured system the data source is defined on returns the content the data
 * source ingests, so the search source is named as the search sources component
 * names it, and only for that system.
 */
class GAbstractContentManagementSystemHandlerDataFlowTest {

	private GAbstractContentManagementSystemHandler<GContentManagementSystem, GProjectEndpoint, ?> handler;
	private ISearchServiceRepositoryPattern searchServices;
	private GContentManagementSystem jiraProd;

	private static GContentManagementSystem system(String code, String type) {
		GContentManagementSystem system = new GContentManagementSystem();
		system.setCode(code);
		system.setContentManagementSystemType(type);
		system.setBaseUri("https://" + code + ".example.com");
		return system;
	}

	@SuppressWarnings("rawtypes")
	private static SearchableSystemMetaData searched(Object configuration, String code) {
		SearchableSystemMetaData system = new SearchableSystemMetaData();
		system.setCode(code);
		system.setSystemConfigurationReference(configuration);
		return system;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeEach
	void setUp() throws Exception {
		IGProjectEndpointRuntimeConfigurationDao<GProjectEndpoint> endpoints = mock(
				IGProjectEndpointRuntimeConfigurationDao.class);
		handler = mock(GAbstractContentManagementSystemHandler.class,
				withSettings().useConstructor(mock(IGBuildSystemHandlerRepositoryPattern.class),
						mock(IGDocumentReferenceFactory.class), mock(IGContentManagementSystemConfigurationDao.class),
						endpoints, mock(IGLocalPersistentFolderDiscoveryService.class),
						mock(IGPersistentObjectManager.class), mock(IGMessageBroker.class),
						mock(IGDocumentReferenceIngestionHandler.class)).defaultAnswer(CALLS_REAL_METHODS));
		GContentManagementSystemType jiraType = new GContentManagementSystemType();
		jiraType.setCode("jira");
		doReturn(jiraType).when(handler).getHandledSystemType();
		doReturn("jira-module").when(handler).getMessagingModuleId();
		doReturn("jira-handler").when(handler).getMessagingSystemId();

		GProjectEndpoint hr = new GProjectEndpoint();
		hr.setCode("hr-project");
		hr.setDescription("HR project");
		hr.setPersonalData(true);
		when(endpoints.getConfigurations()).thenReturn(List.of(hr));
		jiraProd = system("jira-prod", "jira");
		doReturn(jiraProd).when(handler).getSystem(any());

		searchServices = mock(ISearchServiceRepositoryPattern.class);
		ObjectProvider<ISearchServiceRepositoryPattern> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(searchServices);
		handler.searchServicesProvider = provider;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ISearchService searchService(String id, boolean enabled, SearchableSystemMetaData... systems)
			throws Exception {
		ISearchService service = mock(ISearchService.class);
		when(service.getId()).thenReturn(id);
		when(service.isEnabled()).thenReturn(enabled);
		when(service.getSearchableSystems()).thenReturn((List) List.of(systems));
		return service;
	}

	private List<DataTransformationInfo> liveSearchLinks(GDataFlowMetaInfos flow) {
		return flow.getTransformations().stream().filter(x -> x.getId().startsWith("live-search-flow-")).toList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void aDataSourceIsLinkedToTheLiveSearchOfItsSystemOnly() throws Exception {
		ISearchService jiraSearch = searchService("jira-search", true, searched(jiraProd, "jira-module.x<->jira-prod"),
				searched(system("jira-other", "jira"), "jira-module.x<->jira-other"));
		// another system type with the same code, and a web search account: not the same system
		ISearchService confluenceSearch = searchService("confluence-search", true,
				searched(system("jira-prod", "confluence"), "c"));
		ISearchService google = searchService("google", true, searched(null, "google-account"));
		when(searchServices.getImplementations()).thenReturn((List) List.of(jiraSearch, confluenceSearch, google));

		GDataFlowMetaInfos flow = handler.getDataFlowMetaInfos();

		assertNotNull(flow);
		List<DataTransformationInfo> links = liveSearchLinks(flow);
		assertEquals(1, links.size(), String.valueOf(links));
		assertEquals(flow.qualifiedId("source-hr-project"), links.get(0).getDataSourceId());
		// named as the search sources component names it: the configured system's own code
		assertEquals(GStandardDataFlowEndpoints.searchSourceRef("jira-search", "jira-prod"),
				links.get(0).getDataDestinationId());
		// the content reaches the search source, which passes it on to its readers
		assertEquals(DataTransformationInfo.Carried.CONTENT, links.get(0).getCarried());
		assertTrue(flow.getDataEndpoints().get(0).isPersonalData(), "the flagged data source");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void aSystemNobodyCanSearchIsNotLinked() throws Exception {
		ISearchService disabled = searchService("jira-search", false, searched(jiraProd, "s"));
		when(searchServices.getImplementations()).thenReturn((List) List.of(disabled));

		assertTrue(liveSearchLinks(handler.getDataFlowMetaInfos()).isEmpty());
	}

	@Test
	void withoutSearchServicesTheDataSourceIsReportedAlone() {
		handler.searchServicesProvider = null;

		GDataFlowMetaInfos flow = handler.getDataFlowMetaInfos();

		assertEquals(1, flow.getDataEndpoints().size());
		assertFalse(flow.getTransformations().stream().anyMatch(x -> x.getId().startsWith("live-search-flow-")));
	}
}
