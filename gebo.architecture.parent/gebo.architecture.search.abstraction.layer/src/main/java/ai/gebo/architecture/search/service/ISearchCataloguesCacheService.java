package ai.gebo.architecture.search.service;

import java.util.List;

import ai.gebo.architecture.search.model.CatalogueSample;

/**
 * Search-domain read access to the <em>cached</em> catalogue samples of the
 * searchable systems.
 * <p>
 * {@link ISearchService#getCataloguesListSample(String)} is the live (possibly
 * remote and expensive) sampler; this contract exposes the periodically sampled,
 * persisted snapshot of those catalogues so consumers can describe a system's
 * catalogs cheaply and without triggering remote calls. Keeping the contract in
 * the search layer lets any consumer (deep-search routing, agent capabilities,
 * ...) read catalogue caches as a search concern, instead of depending on a
 * higher-level pipeline service.
 */
public interface ISearchCataloguesCacheService {

	/**
	 * Returns the cached catalogue samples for a single searchable system,
	 * identified by its messaging module/system and system configuration code.
	 * Returns an empty list when nothing has been sampled yet.
	 */
	List<CatalogueSample> findCachedCatalogues(String messagingModuleId, String messagingSystemId,
			String systemConfigurationCode);
}
