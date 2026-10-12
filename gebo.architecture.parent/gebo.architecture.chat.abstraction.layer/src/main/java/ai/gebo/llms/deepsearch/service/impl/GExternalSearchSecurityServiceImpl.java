package ai.gebo.llms.deepsearch.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.DeepSearchDataSourceAccess;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * The one access check of the external search sources (the internal knowledge base
 * excluded), shared by the deep search, the search agents and the search tools.
 * <p>
 * Admins can search every configured source. For the other users the users/groups
 * access of the deep search configuration applies where it is configured: per data
 * source when the configuration is per data source, otherwise for the whole
 * configuration. Where it is not configured (no configuration saved, no row for the
 * data source, nobody given access) the source is open to everyone when
 * {@link DeepSearchConfig#getExternalSourceSearchEnabledByDefault()} is true, its
 * default. A source is searchable only when it is enabled and has systems.
 */
@Service
@AllArgsConstructor
public class GExternalSearchSecurityServiceImpl implements IGExternalSearchSecurityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GExternalSearchSecurityServiceImpl.class);
	private final IGSecurityService securityService;
	private final IGDeepSearchConfigProvider configProvider;

	@Override
	public boolean isEnabledForCurrentUser(ISearchService searchService) throws SearchServiceException {
		DeepSearchConfig deepSearchConfig = configProvider.get();
		final boolean admin = securityService.isCurrentUserAdmin();
		final boolean userCanAccess = admin ? securityService.isCanAccess(deepSearchConfig, true)
				: nonAdminCanAccess(deepSearchConfig, searchService.getId());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("isEnabledForCurrentUser(...) source:" + searchService.getId() + " admin:" + admin
					+ " userCanAccess:" + userCanAccess);
		}
		if (searchService.isEnabled() && userCanAccess) {
			List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
			return systems != null && !systems.isEmpty();
		}
		return false;
	}

	/** Where the access to an external source is decided. */
	public static enum AccessOrigin {
		/** No deep search configuration saved: the default applies. */
		NO_CONFIGURATION,
		/** The source's row of the per data source grid gives someone access. */
		PER_DATA_SOURCE,
		/** The per data source grid is in use, nobody given access in the source's row: the default applies. */
		PER_DATA_SOURCE_NOT_SET,
		/** The configuration gives someone access to every source. */
		EVERY_SOURCE,
		/** The configuration gives nobody access: the default applies. */
		NOT_SET
	}

	/**
	 * The rule deciding whether a user who is not an admin may search an external
	 * source: the users/groups of {@link #governing()}, or, when it is null, the
	 * default {@link #openByDefault()}.
	 */
	public static record AccessRule(IGObjectWithSecurity governing, boolean openByDefault, AccessOrigin origin) {
	}

	/**
	 * The rule applying to an external source, the same one
	 * {@link #isEnabledForCurrentUser(ISearchService)} checks for a user who is not
	 * an admin: shared so what the data-flow register shows and what a search is
	 * allowed cannot differ.
	 *
	 * @param deepSearchConfig the deep search configuration, may be null
	 * @param dataSourceId     the search service id
	 * @return the rule
	 */
	public static AccessRule accessRule(DeepSearchConfig deepSearchConfig, String dataSourceId) {
		final boolean openByDefault = openByDefault(deepSearchConfig);
		if (deepSearchConfig == null || deepSearchConfig instanceof DeepSearchDefaultConfig) {
			return new AccessRule(null, openByDefault, AccessOrigin.NO_CONFIGURATION);
		}
		final List<DeepSearchDataSourceAccess> accesses = deepSearchConfig.getDataSourcesAccesses();
		final boolean perDataSource = Boolean.TRUE.equals(deepSearchConfig.getPerDataSourceConfigured())
				&& accesses != null && !accesses.isEmpty();
		if (perDataSource) {
			final DeepSearchDataSourceAccess gridCell = accesses.stream()
					.filter(x -> x.getDataSourceId() != null && x.getDataSourceId().equals(dataSourceId)).findFirst()
					.orElse(null);
			if (gridCell != null && accessConfigured(gridCell.getAccessibleToAll(), gridCell.getAccessibleUsers(),
					gridCell.getAccessibleGroups())) {
				return new AccessRule(gridCell, openByDefault, AccessOrigin.PER_DATA_SOURCE);
			}
			return new AccessRule(null, openByDefault, AccessOrigin.PER_DATA_SOURCE_NOT_SET);
		}
		if (accessConfigured(deepSearchConfig.getAccessibleToAll(), deepSearchConfig.getAccessibleUsers(),
				deepSearchConfig.getAccessibleGroups())) {
			return new AccessRule(deepSearchConfig, openByDefault, AccessOrigin.EVERY_SOURCE);
		}
		return new AccessRule(null, openByDefault, AccessOrigin.NOT_SET);
	}

	/**
	 * Whether a user who is not an admin may search the data source: the configured
	 * users/groups access, or the default when it is not configured.
	 */
	boolean nonAdminCanAccess(DeepSearchConfig deepSearchConfig, String dataSourceId) {
		final AccessRule rule = accessRule(deepSearchConfig, dataSourceId);
		final boolean openByDefault = rule.openByDefault();
		switch (rule.origin()) {
		case NO_CONFIGURATION:
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No deep search configuration saved, external source:" + dataSourceId
						+ " open by default:" + openByDefault);
			}
			return openByDefault;
		case PER_DATA_SOURCE: {
			final boolean canAccess = securityService.isCanAccess(rule.governing(), true);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("External source:" + dataSourceId + " access configured per data source, can access:"
						+ canAccess);
			}
			return canAccess;
		}
		case PER_DATA_SOURCE_NOT_SET:
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("External source:" + dataSourceId
						+ " access not configured in the per data source grid, open by default:" + openByDefault);
			}
			return openByDefault;
		case EVERY_SOURCE: {
			final boolean canAccess = securityService.isCanAccess(rule.governing(), true);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("External source:" + dataSourceId + " access configured for every source, can access:"
						+ canAccess);
			}
			return canAccess;
		}
		default:
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("External source:" + dataSourceId + " users/groups access not configured, open by default:"
						+ openByDefault);
			}
			return openByDefault;
		}
	}

	/** The external sources open to everyone where the access is not configured: true when not set. */
	static boolean openByDefault(DeepSearchConfig deepSearchConfig) {
		return deepSearchConfig == null || deepSearchConfig.getExternalSourceSearchEnabledByDefault() == null
				|| deepSearchConfig.getExternalSourceSearchEnabledByDefault();
	}

	/** Whether someone is given access: everyone, or some users or groups. */
	static boolean accessConfigured(Boolean accessibleToAll, List<String> users, List<String> groups) {
		return Boolean.TRUE.equals(accessibleToAll) || (users != null && !users.isEmpty())
				|| (groups != null && !groups.isEmpty());
	}

}
