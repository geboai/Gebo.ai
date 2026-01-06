package ai.gebo.googledrive.handlers.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.CleanQueryUtil;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemConsumingService;
import ai.gebo.googledrive.handlers.config.GoogleWorkspaceHandlerConfig;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNativePositionObject;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNavigationCoordinates;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveResourceReference;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveResultsExtractionData;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;

@Service
public class GoogleDriveSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<GoogleDriveResultsExtractionData, GGoogleDriveSystem, GGoogleDriveProjectEndpoint, GoogleDriveNativePositionObject, GoogleDriveNavigationCoordinates, GoogleDriveResourceReference, IGGoogleDriveVirtualFilesystemConsumingService> {
	final GoogleDriveCredentialsFactory googleCredentialsFactory;
	final GoogleWorkspaceHandlerConfig config;

	public GoogleDriveSearchService(GoogleDriveCredentialsFactory googleCredentialsFactory,
			GoogleDriveVirtualFilesystemConsumingService virtualFileSystemConsumingService,
			GGoogleDriveSystemContentHandlerImpl contentManagementSystemHandler, GoogleWorkspaceHandlerConfig config) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler);
		this.googleCredentialsFactory = googleCredentialsFactory;
		this.config = config;
	}

	@Override
	public String getDescription() {

		return "Google Drive/Workspace Search";
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		query=CleanQueryUtil.cleanQuery(query);
		if (system.getSystemConfigurationReference() instanceof GGoogleDriveSystem googleDriveSystem) {

			boolean isCql = query.getQueryText() != null && query.getQueryText().toLowerCase().contains("cql=");

		}
		return List.of();
	}

	@Override
	public Class<GoogleDriveResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {

		return GoogleDriveResultsExtractionData.class;
	}

	@Override
	public GoogleDriveResultsExtractionData aggregate(GoogleDriveResultsExtractionData oldConsolidated,
			GoogleDriveResultsExtractionData consolidated) {
		GoogleDriveResultsExtractionData data = new GoogleDriveResultsExtractionData();
		data.setExtractedRelevantContent(consolidated != null ? consolidated.getExtractedRelevantContent() : null);
		return data;
	}

	@Override
	public String getQueriesExtractionPrompt() {

		return config.getQueryExtractionPrompt();
	}

}
