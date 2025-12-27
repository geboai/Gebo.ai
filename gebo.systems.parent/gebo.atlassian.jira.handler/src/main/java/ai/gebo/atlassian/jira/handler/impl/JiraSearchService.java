package ai.gebo.atlassian.jira.handler.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.IGJiraVirtualFilesystemConsumingService;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNativePositionObject;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNavigationCoordinates;
import ai.gebo.atlassian.jira.handler.impl.model.JiraResourceReference;
import ai.gebo.atlassian.jira.handler.impl.model.JiraResultsExtractionData;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;

@Service
public class JiraSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<JiraResultsExtractionData, GJiraSystem, GJiraProjectEndpoint, JiraNativePositionObject, JiraNavigationCoordinates, JiraResourceReference, IGJiraVirtualFilesystemConsumingService> {
	final JiraApiClientFactory jiraConnectionFactory;

	public JiraSearchService(JiraApiClientFactory jiraConnectionFactory,
			GJiraRemoteVirtualFilesystemConsumingServiceImpl virtualFileSystemConsumingService,
			JiraContentManagementHandlerImpl contentManagementSystemHandler) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler);
		this.jiraConnectionFactory = jiraConnectionFactory;
	}

	@Override
	public String getDescription() {

		return "Jira Search";
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		if (system.getSystemConfigurationReference() instanceof GJiraSystem jiraSystem) {

			boolean isCql = query.getQueryText() != null && query.getQueryText().toLowerCase().contains("cql=");

		}
		return List.of();
	}

	

	@Override
	public Class<JiraResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {

		return JiraResultsExtractionData.class;
	}

	@Override
	public JiraResultsExtractionData aggregate(JiraResultsExtractionData oldConsolidated,
			JiraResultsExtractionData consolidated) {
		JiraResultsExtractionData data = new JiraResultsExtractionData();
		data.setExtractedRelevantContent(consolidated != null ? consolidated.getExtractedRelevantContent() : null);
		return data;
	}

}
