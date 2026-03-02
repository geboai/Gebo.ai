package ai.gebo.sharepoint.handler.impl;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Entity;
import com.microsoft.graph.models.EntityType;
import com.microsoft.graph.models.File;
import com.microsoft.graph.models.ListItem;
import com.microsoft.graph.models.SearchHit;
import com.microsoft.graph.models.SearchHitsContainer;
import com.microsoft.graph.models.SearchRequest;
import com.microsoft.graph.models.SearchResponse;
import com.microsoft.graph.search.query.QueryPostRequestBody;
import com.microsoft.graph.search.query.QueryPostResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.CleanQueryUtil;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemBrowsingService;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemConsumingService;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphNativePositionObject;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphNavigationCoordinates;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphResourceReference;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftResultsExtractionData;
import ai.gebo.sharepoint.handler.search.model.SharePointSearchFilter;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

@Service
public class GMicrosoftGraphSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<MicrosoftResultsExtractionData, GSharepointContentManagementSystem, GSharepointProjectEndpoint, MicrosoftGraphNativePositionObject, MicrosoftGraphNavigationCoordinates, MicrosoftGraphResourceReference, IGMicrosoftGraphVirtualFilesystemConsumingService, SharepointBrowsingContext>
		implements INativeSearchService<MicrosoftResultsExtractionData, SharePointSearchFilter> {
	private static final String MSGRAPH_STANDARD_QUERY_EXTRACTION_PROMPT = "msgraph-standard-query-extraction-prompt";
	private static final String MSGRAPH_NATIVE_SEARCH_PROMPTS_TEMPLATE = "msgraph-native-search-prompts-template";
	private static final String SHAREPOINT = "sharepoint";
	final GMicrosoftGraphClientFactory msGraphConnectionFactory;

	public GMicrosoftGraphSearchService(GMicrosoftGraphClientFactory msGraphConnectionFactory,
			GMicrosoftGraphVirtualFilesystemConsumingServiceImpl virtualFileSystemConsumingService,
			GSharepointContentManagementSystemHandlerImpl contentManagementSystemHandler,
			IGMicrosoftGraphVirtualFilesystemBrowsingService browsingService) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler, browsingService);
		this.msGraphConnectionFactory = msGraphConnectionFactory;

	}

	@Override
	public String getDescription() {

		return "Sharepoint/OneDrive Search";
	}

	@Override
	public String getId() {

		return SHAREPOINT;
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		query = CleanQueryUtil.cleanQuery(query);
		if (system.getSystemConfigurationReference() instanceof GSharepointContentManagementSystem spSystem) {

			boolean isCql = query.getQueryText() != null && query.getQueryText().toLowerCase().contains("cql=");

		}
		return List.of();
	}

	@Override
	public Class<MicrosoftResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {

		return MicrosoftResultsExtractionData.class;
	}

	@Override
	public MicrosoftResultsExtractionData aggregate(MicrosoftResultsExtractionData oldConsolidated,
			MicrosoftResultsExtractionData consolidated) {
		MicrosoftResultsExtractionData data = basicAggregate(oldConsolidated, consolidated,
				new MicrosoftResultsExtractionData());
		return data;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.SHAREPOINT_MODULE;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {

		return MSGRAPH_STANDARD_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			MicrosoftResultsExtractionData extractedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SharepointBrowsingContext createBrowsingContext(GSharepointContentManagementSystem systemType) {

		return SharepointBrowsingContext.of(systemType.getCode());
	}

	@Override
	public List<SearchResult> nativeSearch(SharePointSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		try {
			if (system.getSystemConfigurationReference() instanceof GSharepointContentManagementSystem shsystem) {
				GraphServiceClient graphClient = this.msGraphConnectionFactory.getServiceClient(shsystem);
				String kqlQueryString = SharePointKqlTranslator.createKqlQueryString(query);
				com.microsoft.graph.models.SearchQuery searchQuery = new com.microsoft.graph.models.SearchQuery();
				searchQuery.setQueryString(kqlQueryString);

				SearchRequest request = new SearchRequest();
				// Documents/files + SharePoint pages/list items
				request.setEntityTypes(List.of(EntityType.DriveItem, EntityType.ListItem));
				request.setQuery(searchQuery);

				QueryPostRequestBody body = new QueryPostRequestBody();
				body.setRequests(List.of(request));

				// POST /search/query
				return toSearchResults(graphClient.search().query().post(body), shsystem);
			}
		} catch (GeboCryptSecretException | VirtualFilesystemBrowsingException exc) {
			throw new SearchServiceException("Exception in browsing/search operation", exc);
		}
		throw new SearchServiceException("Wrong system object rappresentation received");
	}

	private List<SearchResult> toSearchResults(QueryPostResponse post, GSharepointContentManagementSystem system) {
		List<SearchResult> outResults = new ArrayList<SearchResult>();
		List<SearchResponse> responses = post.getValue();
		if (responses != null) {
			for (SearchResponse searchResponse : responses) {
				List<SearchHitsContainer> containers = searchResponse.getHitsContainers();
				if (containers == null)
					continue;
				for (SearchHitsContainer container : containers) {
					if (container.getHits() == null)
						continue;

					for (SearchHit hit : container.getHits()) {
						Entity resource = hit.getResource();
						if (resource instanceof DriveItem di) {
							SearchResult sr = toSearchResult(di);
							outResults.add(sr);
						} else if (resource instanceof ListItem li) {
							SearchResult sr = toSearchResult(li);
							outResults.add(sr);
						}
					}
				}
			}
		}
		for (SearchResult sr : outResults) {
			sr.setSystemConfigurationCode(system.getCode());
			setOriginOn(sr);
		}
		return outResults;
	}

	private SearchResult toSearchResult(ListItem li) {
		SearchResult sr = new SearchResult();
		sr.setDescriptiveText(li.getDescription() != null ? li.getDescription() : li.getName());
		sr.setId(li.getId());
		sr.setModificationDate(toDate(li.getLastModifiedDateTime()));
		sr.setChilds(new ArrayList<SearchResult>());
		sr.setNavigationReference(GMicrosoftGraphNavigationUtils.toNavigationReference(li));
		sr.setResultReference(new SearchResultReference());
		sr.getResultReference().setContentType("text/html");
		sr.getResultReference().setName(li.getName());
		sr.getResultReference().setTitle(li.getDescription());
		sr.getResultReference().setUri(li.getWebUrl());
		return sr;
	}

	private Date toDate(OffsetDateTime lastModifiedDateTime) {
		if (lastModifiedDateTime == null) {
			return null;
		}
		return Date.from(lastModifiedDateTime.toInstant());
	}

	private SearchResult toSearchResult(DriveItem li) {
		SearchResult sr = new SearchResult();
		sr.setDescriptiveText(li.getDescription() != null ? li.getDescription() : li.getName());
		sr.setId(li.getId());
		sr.setModificationDate(toDate(li.getLastModifiedDateTime()));
		sr.setChilds(new ArrayList<SearchResult>());
		sr.setNavigationReference(GMicrosoftGraphNavigationUtils.toNavigationReference(li));
		sr.setResultReference(new SearchResultReference());
		sr.getResultReference().setContentType(getContentType(li.getFile()));
		sr.getResultReference().setName(li.getName());
		sr.getResultReference().setTitle(li.getDescription());
		sr.getResultReference().setUri(li.getWebUrl());
		sr.getResultReference().setExtension(getExtension(li.getName()));
		return sr;
	}

	private String getContentType(File file) {

		return file != null ? file.getMimeType() : null;
	}

	private String getExtension(String name) {
		if (name != null) {
			int lastPoint = name.lastIndexOf(".");
			if (lastPoint >= 0) {
				return name.substring(lastPoint).toLowerCase();
			}
		}
		return null;
	}

	@Override
	public Class<SharePointSearchFilter> getNativeSearchDataStructureType() {
		return SharePointSearchFilter.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {

		return MSGRAPH_NATIVE_SEARCH_PROMPTS_TEMPLATE;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {

		return null;
	}

}
