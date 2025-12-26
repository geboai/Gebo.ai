package ai.gebo.atlassian.confluence.handler.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceContentApi;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSearchPageResponseSearchResult.CloudConfluenceSearchResult;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.IGConfluenceVirtualFilesystemConsumingService;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNativePositionObject;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNavigationCoordinates;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResourceReference;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResultsExtractionData;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceContentApi;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSearchPageResponseSearchResult.OnPremiseConfluenceSearchResult;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
@Service
public class ConfluenceSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<ConfluenceResultsExtractionData, GConfluenceSystem, GConfluenceProjectEndpoint, ConfluenceNativePositionObject, ConfluenceNavigationCoordinates, ConfluenceResourceReference, IGConfluenceVirtualFilesystemConsumingService> {
	final ConfluenceConnectionFactory confluenceConnectionFactory;

	public ConfluenceSearchService(ConfluenceConnectionFactory confluenceConnectionFactory,
			GConfluenceRemoteVirtualFilesystemConsumingServiceImpl virtualFileSystemConsumingService,
			ConfluenceContentManagementHandlerImpl contentManagementSystemHandler) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler);
		this.confluenceConnectionFactory = confluenceConnectionFactory;
	}

	@Override
	public String getDescription() {

		return "Confluence Search";
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		if (system.getSystemConfigurationReference() instanceof GConfluenceSystem confluenceSystem) {

			try {
				boolean isCql = query.getQueryText() != null && query.getQueryText().toLowerCase().contains("cql=");
				switch (confluenceSystem.getConfluenceVersion()) {
				case CLOUD: {
					CloudConfluenceConnection connection = confluenceConnectionFactory
							.getCloudConnection(confluenceSystem);
					CloudConfluenceContentApi contentApi = new CloudConfluenceContentApi(connection);
					CloudConfluenceSearchResult data = isCql ? contentApi.searchByCql(query.getQueryText(), nEntryLimit)
							: contentApi.searchFullText(query.getQueryText(), nEntryLimit);

					return encodeCloudResults(data, connection);

				}
				case ONPREMISE7X: {
					OnPremiseConfluenceConnection connection = confluenceConnectionFactory
							.getOnPremiseConnection(confluenceSystem);
					OnPremiseConfluenceContentApi contentApi = new OnPremiseConfluenceContentApi(connection);
					OnPremiseConfluenceSearchResult data = isCql
							? contentApi.searchByCql(query.getQueryText(), nEntryLimit)
							: contentApi.searchFullText(query.getQueryText(), nEntryLimit);
					return encodeOnPremiseResults(data, connection);
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

	private List<SearchResult> encodeOnPremiseResults(OnPremiseConfluenceSearchResult data,
			OnPremiseConfluenceConnection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<SearchResult> encodeCloudResults(CloudConfluenceSearchResult data,
			CloudConfluenceConnection connection) {
		// TODO Auto-generated method stub
		return null;
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
		return data;
	}

}
