package ai.gebo.googledrive.handlers.impl;

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
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.GoogleDriveSystemContext;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemBrowser;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemConsumingService;
import ai.gebo.googledrive.handlers.config.GoogleWorkspaceHandlerConfig;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNativePositionObject;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNavigationCoordinates;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveResourceReference;
import ai.gebo.googledrive.search.api.GoogleDriveResultsExtractionData;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;

@Service
public class GoogleDriveSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<GoogleDriveResultsExtractionData, GGoogleDriveSystem, GGoogleDriveProjectEndpoint, GoogleDriveNativePositionObject, GoogleDriveNavigationCoordinates, GoogleDriveResourceReference, IGGoogleDriveVirtualFilesystemConsumingService, GoogleDriveSystemContext> {
	private static final String GOOGLE_DRIVE = "google-drive";
	final GoogleDriveCredentialsFactory googleCredentialsFactory;
	final GoogleWorkspaceHandlerConfig config;

	public GoogleDriveSearchService(GoogleDriveCredentialsFactory googleCredentialsFactory,
			GoogleDriveVirtualFilesystemConsumingService virtualFileSystemConsumingService,
			GGoogleDriveSystemContentHandlerImpl contentManagementSystemHandler, GoogleWorkspaceHandlerConfig config,
			IGGoogleDriveVirtualFilesystemBrowser browsingService) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler, browsingService);
		this.googleCredentialsFactory = googleCredentialsFactory;
		this.config = config;
	}

	@Override
	public String getDescription() {

		return "Google Drive/Workspace Search";
	}

	@Override
	public String getId() {

		return GOOGLE_DRIVE;
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		query = CleanQueryUtil.cleanQuery(query);
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
		GoogleDriveResultsExtractionData data = basicAggregate(oldConsolidated, consolidated,
				new GoogleDriveResultsExtractionData());
		return data;

	}

	@Override
	public String getQueriesGenerationPromptUseCode() {

		return config.getQueryExtractionPrompt();
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			GoogleDriveResultsExtractionData extractedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GOOGLE_DRIVE_MODULE;
	}

	@Override
	protected GoogleDriveSystemContext createBrowsingContext(GGoogleDriveSystem systemType) {

		return GoogleDriveSystemContext.of(systemType.getCode());
	}

	@Override
	public String getProductId() {

		return GOOGLE_DRIVE;
	}

}
