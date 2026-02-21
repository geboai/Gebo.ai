package ai.gebo.bingsearch.handler.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.bingsearch.handler.config.BingSearchHandlerConfig;
import ai.gebo.bingsearch.handler.repository.GBingSearchApiCredentialsRepository;
import ai.gebo.model.base.GBaseObject;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BingSearchServiceImpl extends AbstractWebSearchServiceImpl {
	private static final String BING_MODULE = "bing-module";
	public static final String BING_SEARCH_SERVICE = "bing-web-search-service";
	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class BingSearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {
	}

	private static final BingSearchSystem SYSTEM_METADATA = new BingSearchSystem();
	private static final List<SearchableSystemMetaData> allSystems = List.of(SYSTEM_METADATA);
	static {
		SYSTEMTYPE.setCode(BING_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("Bing web search service");
		SYSTEM.setCode(BING_SEARCH_SERVICE);
		SYSTEM.setDescription("Google search service");
		SYSTEM_METADATA.setCode(BING_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("Bing search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}
	private final BingSearchApi bingSearchApi;
	private final BingSearchHandlerConfig bingConfig;
	private final GBingSearchApiCredentialsRepository repository;

	@Override
	public boolean isEnabled() {

		return repository.count() > 0l;
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {

		return systemId != null && systemId.equalsIgnoreCase(BING_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getMessagingModuleId() {

		return BING_MODULE;
	}

	@Override
	public String getId() {

		return BING_SEARCH_SERVICE;
	}

	@Override
	public String getDescription() {

		return "Bing web search";
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {

		return List.of(SYSTEM_METADATA);
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {

		return null;
	}

	@Override
	public String getQueriesExtractionPrompt() {

		return bingConfig.getQueryExtractionPrompt();
	}

	@Override
	public List<String> getCataloguesListSample(String configurationCode) throws SearchServiceException {

		return List.of("Search internet with bing search engine");
	}

}
