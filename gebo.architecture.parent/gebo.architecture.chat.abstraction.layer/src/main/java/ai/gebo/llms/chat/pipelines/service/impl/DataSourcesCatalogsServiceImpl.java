package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IDataSourcesCatalogsService;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.base.GBaseObject;
import lombok.AllArgsConstructor;

/**
 * Deep-search view over the searchable systems' catalogues. The catalogue cache
 * itself is now owned by each search service ({@code ISearchService.getCachedCatalogues});
 * this service no longer persists catalogues — it just reads them through the
 * services and aggregates them for the deep-search data sources.
 */
@Component
@Scope("singleton")
@AllArgsConstructor
public class DataSourcesCatalogsServiceImpl implements IDataSourcesCatalogsService {
	private final ISearchServiceRepositoryPattern searchServicesRepositoryPattern;
	private final IGDeepSearchService deepSearchService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourcesCatalogsServiceImpl.class);

	/**
	 * Periodically warms the per-service catalogue caches: each search service is
	 * asked for its cached catalogues, which makes the content/virtual-filesystem
	 * services sample-and-persist on demand (the others answer live), so the first
	 * real request does not pay the sampling cost.
	 */
	@Scheduled(initialDelay = 1000, fixedRate = 240 * 60000)
	public void onTickWarmCatalogues() {
		List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
		if (implementations != null) {
			for (ISearchService searchService : implementations) {
				try {
					searchService.getCachedCatalogues();
				} catch (Throwable e) {
					LOGGER.error("Exception warming catalogues for " + searchService.getId(), e);
				}
			}
		}
	}

	@Override
	public List<CatalogueSample> findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String code) {
		ISearchService service = findService(messagingModuleId, messagingSystemId);
		if (service == null) {
			return new ArrayList<>();
		}
		try {
			return new ArrayList<>(service.getCachedCatalogues(code));
		} catch (SearchServiceException e) {
			LOGGER.error("Cannot read cached catalogues for " + messagingModuleId + "." + messagingSystemId + "." + code,
					e);
			return new ArrayList<>();
		}
	}

	private ISearchService findService(String messagingModuleId, String messagingSystemId) {
		List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
		if (implementations != null) {
			for (ISearchService service : implementations) {
				if (messagingModuleId != null && messagingModuleId.equals(service.getMessagingModuleId())
						&& messagingSystemId != null && messagingSystemId.equals(service.getMessagingSystemId())) {
					return service;
				}
			}
		}
		return null;
	}

	@Override
	public List<DeepSearchDataSourceMetaInfos> getActiveDeepSearchDataSourceMetaInfos() {
		Map<String, ISearchService> servicesById = new HashMap<>();
		List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
		if (implementations != null) {
			for (ISearchService service : implementations) {
				servicesById.put(service.getId(), service);
			}
		}
		List<DeepSearchDataSourceMetaInfos> out = new ArrayList<>();
		List<GBaseObject> handlers = deepSearchService.getDeepSearchActiveHandlers();
		for (GBaseObject handler : handlers) {
			DeepSearchDataSourceMetaInfos entry = new DeepSearchDataSourceMetaInfos();
			entry.setHandlerId(handler.getCode());
			entry.setDescription(handler.getDescription());
			ISearchService service = servicesById.get(handler.getCode());
			if (service != null) {
				entry.setDescription(service.getDescription());
				try {
					List<CatalogueSample> catalogues = service.getCachedCatalogues();
					if (catalogues != null) {
						entry.getCatalogues().addAll(catalogues);
					}
				} catch (SearchServiceException e) {
					LOGGER.error("Cannot read cached catalogues for handler " + handler.getCode(), e);
				}
			}
			out.add(entry);
		}
		return out;
	}
}
