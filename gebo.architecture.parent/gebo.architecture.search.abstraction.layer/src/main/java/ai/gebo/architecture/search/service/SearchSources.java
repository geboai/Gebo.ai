/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.model.base.GBaseObject;

/**
 * The search sources the compliance data-flow register reports: the systems a
 * search service searches, named the same way by the component reporting them and
 * by the content handlers linking their data sources to the live search of their
 * system.
 */
public final class SearchSources {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchSources.class);

	private SearchSources() {
	}

	/**
	 * The search services someone can search: enabled and with systems, the condition
	 * the access check of the external search sources starts from
	 * ({@code GExternalSearchSecurityServiceImpl}); who exactly can search them is the
	 * deep search users/groups access, and an administrator always can.
	 */
	public static List<ISearchService> searchable(ISearchServiceRepositoryPattern searchServices) {
		final List<ISearchService> out = new ArrayList<>();
		if (searchServices == null) {
			return out;
		}
		final List<ISearchService> all;
		try {
			all = searchServices.getImplementations();
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot enumerate the search services for the data flow register", e);
			return out;
		}
		if (all == null) {
			return out;
		}
		for (ISearchService service : all) {
			if (service == null) {
				continue;
			}
			boolean enabled;
			try {
				enabled = service.isEnabled();
			} catch (Exception e) {
				LOGGER.warn("Cannot tell whether search service " + service.getId() + " is enabled", e);
				enabled = false;
			}
			if (enabled && !systems(service).isEmpty()) {
				out.add(service);
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search service:" + service.getId() + " is disabled or has no system");
			}
		}
		return out;
	}

	/** The systems a search service searches, none when they cannot be read. */
	@SuppressWarnings("unchecked")
	public static List<SearchableSystemMetaData> systems(ISearchService service) {
		try {
			final List<SearchableSystemMetaData> systems = service.getSearchableSystems();
			return systems != null ? systems : List.of();
		} catch (Exception e) {
			LOGGER.warn("Cannot read the systems of search service " + service.getId(), e);
			return List.of();
		}
	}

	/**
	 * The code naming a searched system: the configured system's own code when the
	 * search reaches a configured system (the remote-filesystem searches reference
	 * their content handler's systems, so a data source on the system names the same
	 * code), else the code the search service gives the system.
	 */
	public static String systemCode(SearchableSystemMetaData system) {
		if (system.getSystemConfigurationReference() instanceof GBaseObject configured && configured.getCode() != null) {
			return configured.getCode();
		}
		final String code = system.getCode() != null ? system.getCode() : "system";
		return code.replace(ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR, ".");
	}
}
