package ai.gebo.atlassian.confluence.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.CleanQueryUtil;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceContentApi;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceFullContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSearchPageResponseSearchResult;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSearchPageResponseSearchResult.CloudConfluenceSearchResult;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.IGConfluenceVirtualFilesystemConsumingService;
import ai.gebo.atlassian.confluence.handler.config.ConfluenceHandlerConfig;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNativePositionObject;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNavigationCoordinates;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResourceReference;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResultsExtractionData;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceAttachmentApi;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceContentApi;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSearchPageResponseSearchResult;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSearchPageResponseSearchResult.OnPremiseConfluenceSearchResult;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseFullContent;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;

@Service
public class ConfluenceSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<ConfluenceResultsExtractionData, GConfluenceSystem, GConfluenceProjectEndpoint, ConfluenceNativePositionObject, ConfluenceNavigationCoordinates, ConfluenceResourceReference, IGConfluenceVirtualFilesystemConsumingService> {
	private static final String HTML = ".html";
	private static final String TEXT_HTML = "text/html";
	final ConfluenceConnectionFactory confluenceConnectionFactory;
	final ConfluenceHandlerConfig config;
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceSearchService.class);

	public ConfluenceSearchService(ConfluenceConnectionFactory confluenceConnectionFactory,
			GConfluenceRemoteVirtualFilesystemConsumingServiceImpl virtualFileSystemConsumingService,
			ConfluenceContentManagementHandlerImpl contentManagementSystemHandler, ConfluenceHandlerConfig config) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler);
		this.confluenceConnectionFactory = confluenceConnectionFactory;
		this.config = config;
	}

	@Override
	public String getDescription() {

		return "Confluence Search";
	}
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ATLASSIAN_CONFLUENCE_MODULE;
	}
	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		query = CleanQueryUtil.cleanQuery(query);
		if (system.getSystemConfigurationReference() instanceof GConfluenceSystem confluenceSystem) {

			try {
				boolean isCql = query.getQueryText() != null && query.getQueryText().toLowerCase().contains("cql=");
				switch (confluenceSystem.getConfluenceVersion()) {
				case CLOUD: {
					CloudConfluenceConnection connection = confluenceConnectionFactory
							.getCloudConnection(confluenceSystem);
					CloudConfluenceContentApi contentApi = new CloudConfluenceContentApi(connection);
					CloudConfluenceSearchPageResponseSearchResult data = isCql
							? contentApi.searchByCql(query.getQueryText(), nEntryLimit)
							: contentApi.searchFullText(query.getQueryText(), nEntryLimit);

					List<SearchResult> list = encodeCloudResults(data, connection, contentApi);
					setOriginOn(list);
					return list;

				}
				case ONPREMISE7X: {
					OnPremiseConfluenceConnection connection = confluenceConnectionFactory
							.getOnPremiseConnection(confluenceSystem);
					OnPremiseConfluenceContentApi contentApi = new OnPremiseConfluenceContentApi(connection);
					OnPremiseConfluenceSearchPageResponseSearchResult data = isCql
							? contentApi.searchByCql(query.getQueryText(), nEntryLimit)
							: contentApi.searchFullText(query.getQueryText(), nEntryLimit);
					List<SearchResult> list = encodeOnPremiseResults(data, connection, contentApi);
					setOriginOn(list);
					return list;
				}
				}
			} catch (GeboCryptSecretException e) {
				throw new SearchServiceException("Problems in crypt subsystem", e);
			} catch (GeboRestIntegrationException e) {
				throw new SearchServiceException("Problems accessing confluence system", e);
			}

		}
		return List.of();
	}

	private List<SearchResult> encodeOnPremiseResults(OnPremiseConfluenceSearchPageResponseSearchResult data,
			OnPremiseConfluenceConnection connection, OnPremiseConfluenceContentApi contentApi)
			throws GeboRestIntegrationException {
		List<SearchResult> outList = new ArrayList<SearchResult>();
		if (data != null && data.getResults() != null) {

			for (OnPremiseConfluenceSearchResult result : data.getResults()) {
				if (result.getContent() != null) {
					SearchResult searchResult = new SearchResult();
					searchResult.setDescriptiveText(result.getTitle());
					searchResult.setResultReference(new SearchResultReference());
					searchResult.getResultReference().setId(result.getContent().getId());
					searchResult.getResultReference().setTitle(result.getContent().getTitle());
					searchResult.getResultReference().setUri(result.getUrl());
					searchResult.setNavigationReference(new VFilesystemReference());
					searchResult.getNavigationReference().root = result.getContent() != null
							&& result.getContent().getSpace() != null
									? ConfluenceNavigationUtil.encodeSpace(result.getContent().getSpace().getKey(),
											result.getContent().getSpace().getName(),
											result.getContent().getSpace().get_expandable())
									: null;
					if (result.getContent().getType() != null) {
						switch (result.getContent().getType()) {
						case "attachment": {
							searchResult.getNavigationReference().path = ConfluenceNavigationUtil
									.encodeAsAttachment(result.getContent().getId(), result.getContent().getTitle());
							outList.add(searchResult);
							// Loading attachment meta data to add content type and other meta informations

						}
							break;
						case "page": {
							searchResult.getNavigationReference().path = ConfluenceNavigationUtil
									.encodeAsFolder(result.getContent());
							searchResult.getResultReference().setContentType(TEXT_HTML);
							searchResult.getResultReference().setExtension(HTML);
							outList.add(searchResult);
							searchResult.getChilds().addAll(encodeOnPremiseChilds(result.getContent().getId(),
									contentApi, searchResult.getNavigationReference()));
						}
							break;
						case "blogpost": {
							searchResult.getNavigationReference().path = ConfluenceNavigationUtil
									.encodeAsFolder(result.getContent());
							outList.add(searchResult);
							searchResult.getResultReference().setContentType(TEXT_HTML);
							searchResult.getResultReference().setExtension(HTML);
							searchResult.getChilds().addAll(encodeOnPremiseChilds(result.getContent().getId(),
									contentApi, searchResult.getNavigationReference()));

						}
							break;
						case "comment": {
							continue;
						}

						}
					}
				}
			}
		}
		return outList;
	}

	private List<SearchResult> encodeOnPremiseChilds(String parentContentId, OnPremiseConfluenceContentApi contentApi,
			VFilesystemReference vFilesystemReference) throws GeboRestIntegrationException {

		OnPremiseFullContent fullContents = contentApi.getFullContent(parentContentId);
		List<SearchResult> results = new ArrayList<SearchResult>();

		if (fullContents.getRootContent() != null) {
			PathInfo page = ConfluenceNavigationUtil.encodeAsPage(fullContents.getRootContent());
			SearchResult searchResult = new SearchResult();
			searchResult.setDescriptiveText(page.name);
			searchResult.setNavigationReference(new VFilesystemReference());
			searchResult.getNavigationReference().root = vFilesystemReference.root;
			searchResult.getNavigationReference().path = ConfluenceNavigationUtil.combine(vFilesystemReference.path,
					page);
			searchResult.setResultReference(new SearchResultReference());
			searchResult.getResultReference().setName(page.name);
			searchResult.getResultReference().setContentType(TEXT_HTML);
			searchResult.getResultReference().setExtension(HTML);
			results.add(searchResult);
		}
		// ATTACHMENT WILL NOT BE LISTED BECAUSE CANNOT SELECT AND RETRIEVE THEM

		if (fullContents.getAttachmentsList() != null && fullContents.getAttachmentsList().getResults() != null) {
			for (OnPremiseConfluenceAttachmentItem attach : fullContents.getAttachmentsList().getResults()) {
				PathInfo attachment = ConfluenceNavigationUtil.encodeAsAttachment(attach);
				SearchResult searchResult = new SearchResult();
				searchResult.setDescriptiveText(attachment.name);
				searchResult.setNavigationReference(new VFilesystemReference());
				searchResult.getNavigationReference().root = vFilesystemReference.root;
				searchResult.getNavigationReference().path = ConfluenceNavigationUtil.combine(vFilesystemReference.path,
						ConfluenceNavigationUtil.encodeAsAttachment(attach));
				searchResult.setResultReference(new SearchResultReference());
				searchResult.getResultReference().setName(attachment.name);
				if (attach.getMetadata() != null && attach.getMetadata().getMediaType() != null) {
					searchResult.getResultReference().setContentType(attach.getMetadata().getMediaType());
				} else if (attach.getExtensions() != null && attach.getExtensions().getMediaType() != null) {
					searchResult.getResultReference().setContentType(attach.getExtensions().getMediaType());
				}
				results.add(searchResult);
			}
		}

		if (fullContents.getChildPagesList() != null && fullContents.getChildPagesList().getResults() != null) {
			for (OnPremiseConfluenceContentItem childpage : fullContents.getChildPagesList().getResults()) {
				PathInfo page = ConfluenceNavigationUtil.encodeAsPage(childpage);
				SearchResult searchResult = new SearchResult();
				searchResult.setDescriptiveText(page.name);
				searchResult.setNavigationReference(new VFilesystemReference());
				searchResult.getNavigationReference().root = vFilesystemReference.root;
				searchResult.getNavigationReference().path = ConfluenceNavigationUtil.combine(vFilesystemReference.path,
						page);
				searchResult.setResultReference(new SearchResultReference());
				searchResult.getResultReference().setName(page.name);
				searchResult.getResultReference().setContentType(TEXT_HTML);
				searchResult.getResultReference().setExtension(HTML);
				results.add(searchResult);
			}
		}
		return results;
	}

	private List<SearchResult> encodeCloudResults(CloudConfluenceSearchPageResponseSearchResult data,
			CloudConfluenceConnection connection, CloudConfluenceContentApi contentApi)
			throws GeboRestIntegrationException {
		List<SearchResult> outList = new ArrayList<SearchResult>();
		if (data != null && data.getResults() != null) {

			for (CloudConfluenceSearchResult result : data.getResults()) {
				if (result.getContent() != null) {
					SearchResult searchResult = new SearchResult();
					searchResult.setDescriptiveText(result.getTitle());
					searchResult.setResultReference(new SearchResultReference());
					searchResult.getResultReference().setId(result.getContent().getId());
					searchResult.getResultReference().setTitle(result.getContent().getTitle());
					searchResult.getResultReference().setUri(result.getUrl());
					searchResult.setNavigationReference(new VFilesystemReference());
					searchResult.getNavigationReference().root = result.getContent() != null
							&& result.getContent().getSpace() != null
									? ConfluenceNavigationUtil.encodeSpace(result.getContent().getSpace().getKey(),
											result.getContent().getSpace().getName(),
											result.getContent().getSpace().get_expandable())
									: null;
					if (result.getContent().getType() != null) {
						switch (result.getContent().getType()) {
						case "attachment": {
							searchResult.getNavigationReference().path = ConfluenceNavigationUtil
									.encodeAsAttachment(result.getContent().getId(), result.getContent().getTitle());
							outList.add(searchResult);

						}
							break;
						case "page": {
							searchResult.getNavigationReference().path = ConfluenceNavigationUtil
									.encodeAsFolder(result.getContent());
							outList.add(searchResult);
							searchResult.getChilds().addAll(encodeCloudChilds(result.getContent().getId(), contentApi,
									searchResult.getNavigationReference()));
						}
							break;
						case "blogpost": {
							searchResult.getNavigationReference().path = ConfluenceNavigationUtil
									.encodeAsFolder(result.getContent());
							outList.add(searchResult);
							searchResult.getChilds().addAll(encodeCloudChilds(result.getContent().getId(), contentApi,
									searchResult.getNavigationReference()));

						}
							break;
						case "comment": {
							continue;
						}

						}
					}
				}
			}
		}
		return outList;
	}

	private Collection<? extends SearchResult> encodeCloudChilds(String parentContentId,
			CloudConfluenceContentApi contentApi, VFilesystemReference vFilesystemReference)
			throws GeboRestIntegrationException {
		CloudConfluenceFullContent fullContents = contentApi.getFullContent(parentContentId);
		List<SearchResult> results = new ArrayList<SearchResult>();

		if (fullContents.getRootContent() != null) {
			PathInfo page = ConfluenceNavigationUtil.encodeAsPage(fullContents.getRootContent());
			SearchResult searchResult = new SearchResult();
			searchResult.setDescriptiveText(page.name);
			searchResult.setNavigationReference(new VFilesystemReference());
			searchResult.getNavigationReference().root = vFilesystemReference.root;
			searchResult.getNavigationReference().path = ConfluenceNavigationUtil.combine(vFilesystemReference.path,
					page);
			searchResult.setResultReference(new SearchResultReference());
			searchResult.getResultReference().setName(page.name);
			searchResult.getResultReference().setContentType(TEXT_HTML);
			searchResult.getResultReference().setExtension(HTML);
			results.add(searchResult);
		}
		// ATTACHMENT WILL NOT BE LISTED BECAUSE CANNOT SELECT AND RETRIEVE THEM

		if (fullContents.getAttachmentsList() != null && fullContents.getAttachmentsList().getResults() != null) {
			for (CloudConfluenceAttachmentItem attach : fullContents.getAttachmentsList().getResults()) {
				PathInfo attachment = ConfluenceNavigationUtil.encodeAsAttachment(attach);
				SearchResult searchResult = new SearchResult();
				searchResult.setDescriptiveText(attachment.name);
				searchResult.setNavigationReference(new VFilesystemReference());
				searchResult.getNavigationReference().root = vFilesystemReference.root;
				searchResult.getNavigationReference().path = ConfluenceNavigationUtil.combine(vFilesystemReference.path,
						ConfluenceNavigationUtil.encodeAsAttachment(attach));
				searchResult.setResultReference(new SearchResultReference());
				searchResult.getResultReference().setName(attachment.name);
				if (attach.getMetadata() != null && attach.getMetadata().getMediaType() != null) {
					searchResult.getResultReference().setContentType(attach.getMetadata().getMediaType());
				} else if (attach.getExtensions() != null && attach.getExtensions().getMediaType() != null) {
					searchResult.getResultReference().setContentType(attach.getExtensions().getMediaType());
				}
				results.add(searchResult);
			}
		}

		if (fullContents.getChildPagesList() != null && fullContents.getChildPagesList().getResults() != null) {
			for (CloudConfluenceContentItem childpage : fullContents.getChildPagesList().getResults()) {
				PathInfo page = ConfluenceNavigationUtil.encodeAsPage(childpage);
				SearchResult searchResult = new SearchResult();
				searchResult.setDescriptiveText(page.name);
				searchResult.setNavigationReference(new VFilesystemReference());
				searchResult.getNavigationReference().root = vFilesystemReference.root;
				searchResult.getNavigationReference().path = ConfluenceNavigationUtil.combine(vFilesystemReference.path,
						page);
				searchResult.setResultReference(new SearchResultReference());
				searchResult.getResultReference().setName(page.name);
				searchResult.getResultReference().setContentType(TEXT_HTML);
				searchResult.getResultReference().setExtension(HTML);
				results.add(searchResult);
			}
		}
		return results;
	}

	@Override
	public Class<ConfluenceResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {

		return ConfluenceResultsExtractionData.class;
	}

	@Override
	public ConfluenceResultsExtractionData aggregate(ConfluenceResultsExtractionData oldConsolidated,
			ConfluenceResultsExtractionData consolidated) {
		ConfluenceResultsExtractionData data = new ConfluenceResultsExtractionData();
		data.setExtractedRelevantContent(consolidated != null ? consolidated.getExtractedRelevantContent() : null);
		data.setContentIsRelevant(consolidated.getContentIsRelevant());
		return data;
	}

	@Override
	public String getQueriesExtractionPrompt() {

		return config.getQueryExtractionPrompt();
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			ConfluenceResultsExtractionData extractedData) {
		// TODO Auto-generated method stub
		return null;
	}

}
