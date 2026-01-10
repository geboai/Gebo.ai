package ai.gebo.sharepoint.handler.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.CleanQueryUtil;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemConsumingService;
import ai.gebo.sharepoint.handler.config.MicrosoftSharepointHandlerConfig;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphNativePositionObject;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphNavigationCoordinates;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphResourceReference;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftResultsExtractionData;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;

@Service
public class GMicrosoftGraphSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<MicrosoftResultsExtractionData, GSharepointContentManagementSystem, GSharepointProjectEndpoint, MicrosoftGraphNativePositionObject, MicrosoftGraphNavigationCoordinates, MicrosoftGraphResourceReference, IGMicrosoftGraphVirtualFilesystemConsumingService> {
	final GMicrosoftGraphClientFactory msGraphConnectionFactory;
	final MicrosoftSharepointHandlerConfig config;

	public GMicrosoftGraphSearchService(GMicrosoftGraphClientFactory msGraphConnectionFactory,
			GMicrosoftGraphVirtualFilesystemConsumingServiceImpl virtualFileSystemConsumingService,
			GSharepointContentManagementSystemHandlerImpl contentManagementSystemHandler,
			MicrosoftSharepointHandlerConfig config) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler);
		this.msGraphConnectionFactory = msGraphConnectionFactory;
		this.config = config;
	}

	@Override
	public String getDescription() {

		return "Sharepoint/OneDrive Search";
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
		MicrosoftResultsExtractionData data = new MicrosoftResultsExtractionData();
		data.setExtractedRelevantContent(consolidated != null ? consolidated.getExtractedRelevantContent() : null);
		data.setContentIsRelevant(consolidated.getContentIsRelevant());
		return data;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.SHAREPOINT_MODULE;
	}

	@Override
	public String getQueriesExtractionPrompt() {

		return config.getQueryExtractionPrompt();
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			MicrosoftResultsExtractionData extractedData) {
		// TODO Auto-generated method stub
		return null;
	}

}
