package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceSampledCatalogs;
import ai.gebo.llms.deepsearch.repository.DeepSearchDataSourceSampledCatalogsRepository;
import ai.gebo.llms.deepsearch.service.IDeepSearchDataSourcesCatalogsService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.base.GBaseObject;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class DeepSearchDataSourcesCatalogsService implements IDeepSearchDataSourcesCatalogsService {
	private final DeepSearchDataSourceSampledCatalogsRepository deepSearchDataSourceSampletCatalogsRepo;
	private final ISearchServiceRepositoryPattern searchServicesRepositoryPattern;
	private final IGDeepSearchService deepSearchService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchDataSourcesCatalogsService.class);

	@Scheduled(initialDelay = 1000, fixedRate = 240 * 60000)
	public void onTickCheckCatalogueStatus() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
		Date date = calendar.getTime();
		List<ISearchService> implementations = searchServicesRepositoryPattern.getImplementations();
		if (implementations != null) {
			for (ISearchService searchService : implementations) {
				String messagingModuleId = searchService.getMessagingModuleId();
				String messagingSystemId = searchService.getMessagingSystemId();
				try {
					List<SearchableSystemMetaData> searchableSystems = searchService.getSearchableSystems();
					for (SearchableSystemMetaData meta : searchableSystems) {
						List<DeepSearchDataSourceSampledCatalogs> cataloguesList = deepSearchDataSourceSampletCatalogsRepo
								.findByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
										messagingModuleId, messagingSystemId, meta.getCode());
						boolean run = cataloguesList == null || cataloguesList.isEmpty();
						if (!run && cataloguesList != null) {
							Optional<DeepSearchDataSourceSampledCatalogs> found = cataloguesList.stream()
									.filter(x -> x.getDateModified() != null && x.getDateModified().after(date))
									.findFirst();
							run = found.isEmpty();
						}
						if (run) {
							LOGGER.info("Try sampling catalogues list for: " + messagingModuleId + "."
									+ messagingSystemId + "." + meta.getCode());
							List<String> catalogues = searchService.getCataloguesListSample(meta.getCode());
							LOGGER.info("Extracted catalogues:" + catalogues);
							DeepSearchDataSourceSampledCatalogs catalog = new DeepSearchDataSourceSampledCatalogs();
							catalog.setMessagingModuleId(messagingModuleId);
							catalog.setMessagingSystemId(messagingSystemId);
							catalog.setSystemConfigurationCode(meta.getCode());
							catalog.setHandlerId(searchService.getId());
							catalog.setDescription(searchService.getDescription());
							catalog.recalculateCode();
							catalog.setCatalogs(catalogues);
							catalog.setDateModified(new Date());
							catalog.setDateCreated(new Date());
							deepSearchDataSourceSampletCatalogsRepo.save(catalog);
						}
					}

				} catch (SearchServiceException e) {
					LOGGER.error("Exception catalogue extractin for: " + messagingModuleId + ":" + messagingSystemId,
							e);
				}

			}
		}

	}

	@Override
	public List<String> findCataloguesListByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(
			String messagingModuleId, String messagingSystemId, String code) {
		List<DeepSearchDataSourceSampledCatalogs> data = deepSearchDataSourceSampletCatalogsRepo
				.findByMessagingModuleIdAndMessagingSystemIdAndSystemConfigurationCode(messagingModuleId,
						messagingSystemId, code);
		List<String> catalogue = new ArrayList<String>();
		for (DeepSearchDataSourceSampledCatalogs d : data) {
			if (d.getCatalogs() != null) {
				catalogue.addAll(d.getCatalogs());
			}
		}
		return catalogue;
	}

	@Override
	public List<DeepSearchDataSourceMetaInfos> getActiveDeepSearchDataSourceMetaInfos() {
		Map<String, DeepSearchDataSourceMetaInfos> out = new HashMap<String, DeepSearchDataSourceMetaInfos>();
		List<GBaseObject> handlers = deepSearchService.getDeepSearchActiveHandlers();
		for (GBaseObject gBaseObject : handlers) {
			DeepSearchDataSourceMetaInfos entry = new DeepSearchDataSourceMetaInfos();
			entry.setDescription(gBaseObject.getDescription());
			entry.setHandlerId(gBaseObject.getCode());
			out.put(gBaseObject.getCode(), entry);
		}
		List<String> ids = handlers.stream().map(x -> x.getCode()).toList();
		if (!ids.isEmpty()) {
			List<DeepSearchDataSourceSampledCatalogs> data = deepSearchDataSourceSampletCatalogsRepo
					.findByHandlerIdIn(ids);
			for (DeepSearchDataSourceSampledCatalogs sample : data) {
				if (!out.containsKey(sample.getHandlerId())) {
					out.put(sample.getHandlerId(), new DeepSearchDataSourceMetaInfos());
				}
				DeepSearchDataSourceMetaInfos entry = out.get(sample.getHandlerId());
				entry.setDescription(sample.getDescription());
				entry.setHandlerId(sample.getHandlerId());
				if (sample.getCatalogs() != null)
					entry.getCatalogues().addAll(sample.getCatalogs());
			}
		}
		return new ArrayList<DeepSearchDataSourceMetaInfos>(out.values());
	}
}
