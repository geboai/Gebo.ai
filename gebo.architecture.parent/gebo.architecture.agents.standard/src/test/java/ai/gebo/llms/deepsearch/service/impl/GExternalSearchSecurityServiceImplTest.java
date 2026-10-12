/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.DeepSearchDataSourceAccess;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.security.services.IGSecurityService;

/**
 * Pins the access to the external search sources: open to everyone by default
 * where the users/groups access is not configured, the configured access applying
 * where it is, closed by default when the flag is off, and a source searchable only
 * when it is enabled and has systems.
 */
class GExternalSearchSecurityServiceImplTest {
	private IGSecurityService securityService;
	private IGDeepSearchConfigProvider configProvider;
	@SuppressWarnings("rawtypes")
	private ISearchService jira;
	private GExternalSearchSecurityServiceImpl check;

	@BeforeEach
	void setUp() throws Exception {
		securityService = mock(IGSecurityService.class);
		configProvider = mock(IGDeepSearchConfigProvider.class);
		jira = mock(ISearchService.class);
		when(jira.getId()).thenReturn("jira-search-service");
		when(jira.isEnabled()).thenReturn(true);
		when(jira.getSearchableSystems()).thenReturn(List.of(new SearchableSystemMetaData()));
		check = new GExternalSearchSecurityServiceImpl(securityService, configProvider);
	}

	private static DeepSearchConfig saved() {
		DeepSearchConfig config = new DeepSearchConfig();
		config.setCode("saved");
		return config;
	}

	private static DeepSearchDataSourceAccess row(String dataSourceId, Boolean all, List<String> users) {
		return new DeepSearchDataSourceAccess(new ArrayList<>(), users, all, dataSourceId);
	}

	@Test
	void openToEveryoneWhenNoConfigurationIsSaved() throws Exception {
		when(configProvider.get()).thenReturn(new DeepSearchDefaultConfig());

		assertTrue(check.isEnabledForCurrentUser(jira));
	}

	@Test
	void closedByDefaultWhenTheFlagIsOff() throws Exception {
		DeepSearchDefaultConfig defaults = new DeepSearchDefaultConfig();
		defaults.setExternalSourceSearchEnabledByDefault(false);
		when(configProvider.get()).thenReturn(defaults);
		assertFalse(check.isEnabledForCurrentUser(jira));

		DeepSearchConfig config = saved();
		config.setExternalSourceSearchEnabledByDefault(false);
		when(configProvider.get()).thenReturn(config);
		assertFalse(check.isEnabledForCurrentUser(jira));
	}

	@Test
	void aSavedConfigurationWithoutTheFlagIsOpenWhereNobodyIsGivenAccess() throws Exception {
		DeepSearchConfig config = saved();
		config.setAccessibleToAll(false);
		when(configProvider.get()).thenReturn(config);

		assertTrue(check.isEnabledForCurrentUser(jira));
	}

	@Test
	void theConfiguredUsersAndGroupsDecideWhereTheyAreSet() throws Exception {
		DeepSearchConfig config = saved();
		config.setAccessibleUsers(List.of("someone-else"));
		when(configProvider.get()).thenReturn(config);
		when(securityService.isCanAccess(any(), anyBoolean())).thenReturn(false);
		assertFalse(check.isEnabledForCurrentUser(jira));

		when(securityService.isCanAccess(any(), anyBoolean())).thenReturn(true);
		assertTrue(check.isEnabledForCurrentUser(jira));
	}

	@Test
	void perDataSourceTheRowDecidesAndAMissingRowFallsBackToTheFlag() throws Exception {
		DeepSearchConfig config = saved();
		config.setPerDataSourceConfigured(true);
		config.setDataSourcesAccesses(new ArrayList<>(List.of(row("jira-search-service", false, List.of("someone-else")))));
		when(configProvider.get()).thenReturn(config);
		when(securityService.isCanAccess(any(), anyBoolean())).thenReturn(false);
		assertFalse(check.isEnabledForCurrentUser(jira));

		config.setDataSourcesAccesses(new ArrayList<>(List.of(row("confluence-search-service", false, List.of("x")))));
		assertTrue(check.isEnabledForCurrentUser(jira));

		config.setExternalSourceSearchEnabledByDefault(false);
		assertFalse(check.isEnabledForCurrentUser(jira));
	}

	@Test
	void aSourceNotConfiguredIsNeverSearchable() throws Exception {
		when(configProvider.get()).thenReturn(new DeepSearchDefaultConfig());
		when(jira.getSearchableSystems()).thenReturn(List.of());
		assertFalse(check.isEnabledForCurrentUser(jira));

		when(jira.isEnabled()).thenReturn(false);
		assertFalse(check.isEnabledForCurrentUser(jira));
	}

	@Test
	void adminsKeepTheirAccess() throws Exception {
		DeepSearchConfig config = saved();
		config.setAccessibleUsers(List.of("someone-else"));
		config.setExternalSourceSearchEnabledByDefault(false);
		when(configProvider.get()).thenReturn(config);
		when(securityService.isCurrentUserAdmin()).thenReturn(true);
		when(securityService.isCanAccess(any(), anyBoolean())).thenReturn(true);

		assertTrue(check.isEnabledForCurrentUser(jira));
	}
}
