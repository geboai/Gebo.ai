package ai.gebo.atlassian.jira.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.IGJiraVirtualFilesystemConsumingService;
import ai.gebo.atlassian.jira.handler.config.JiraHandlerConfig;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNativePositionObject;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNavigationCoordinates;
import ai.gebo.atlassian.jira.handler.impl.model.JiraResourceReference;
import ai.gebo.atlassian.jira.handler.impl.model.JiraResultsExtractionData;
import ai.gebo.atlassian.jira.handler.search.model.JiraIssuesSearchFilter;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jira.cloud.client.api.IssueSearchApi;
import ai.gebo.jira.cloud.client.api.ProjectsApi;
import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.IssueBean;
import ai.gebo.jira.cloud.client.model.SearchAndReconcileResults;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;

@Service
public class JiraSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<JiraResultsExtractionData, GJiraSystem, GJiraProjectEndpoint, JiraNativePositionObject, JiraNavigationCoordinates, JiraResourceReference, IGJiraVirtualFilesystemConsumingService, JiraBrowsingContext>
		implements INativeSearchService<JiraResultsExtractionData, JiraIssuesSearchFilter> {
	public static final String JIRA_NATIVE_QUERY_EXTRACTION_PROMPT = "jira-native-query-extraction-prompt";
	public static final String JIRA_STANDARD_QUERY_EXTRACTION_PROMPT = "jira-standard-query-extraction-prompt";
	private static final String JQL_PARAM = "jql=";
	private static final String JIRA = "jira";
	final JiraApiClientFactory jiraConnectionFactory;

	public JiraSearchService(JiraApiClientFactory jiraConnectionFactory,
			GJiraRemoteVirtualFilesystemConsumingServiceImpl virtualFileSystemConsumingService,
			JiraContentManagementHandlerImpl contentManagementSystemHandler, JiraHandlerConfig config,
			JiraBrowsingService browsingService) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler, browsingService);
		this.jiraConnectionFactory = jiraConnectionFactory;
	}

	@Override
	public String getDescription() {

		return "Jira Search";
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		query = CleanQueryUtil.cleanQuery(query);
		if (system.getSystemConfigurationReference() instanceof GJiraSystem jiraSystem) {

			return executeJql(system, query.getQueryText(), nEntryLimit);

		}
		return List.of();
	}

	@Override
	public String getId() {

		return JIRA;
	}

	@Override
	public Class<JiraResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {

		return JiraResultsExtractionData.class;
	}

	@Override
	public JiraResultsExtractionData aggregate(JiraResultsExtractionData oldConsolidated,
			JiraResultsExtractionData consolidated) {
		JiraResultsExtractionData data = basicAggregate(oldConsolidated, consolidated, new JiraResultsExtractionData());
		return data;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {

		return JIRA_STANDARD_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ATLASSIAN_JIRA_MODULE;
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			JiraResultsExtractionData extractedData) {

		return null;
	}

	@Override
	protected JiraBrowsingContext createBrowsingContext(GJiraSystem systemType) {

		return JiraBrowsingContext.of(systemType.getCode());
	}

	@Override
	public List<SearchResult> nativeSearch(JiraIssuesSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit, List<CatalogueSample> cataloguesSample) throws IOException, SearchServiceException {
		String jql = JiraJsqlUtil.createJqlString(query);
		return executeJql(system, jql, nEntryLimit);

	}

	@Override
	public Class<JiraIssuesSearchFilter> getNativeSearchDataStructureType() {
		return JiraIssuesSearchFilter.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {

		return JIRA_NATIVE_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData) {

		return Map.of();
	}

	private List<SearchResult> executeJql(SearchableSystemMetaData system, String jql, Integer howmany)
			throws SearchServiceException {
		try {
			List<SearchResult> results = new ArrayList<SearchResult>();
			if (system.getSystemConfigurationReference() instanceof GJiraSystem jiraSystem) {
				ApiClient connection = jiraConnectionFactory.getApiClient(jiraSystem);
				IssueSearchApi searchApi = new IssueSearchApi(connection);
				SearchAndReconcileResults data = searchApi.searchAndReconsileIssuesUsingJql(jql, null, howmany,
						List.of("*all"),
						"fields,renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations",
						null, null, null, null);

				if (data != null && data.getIssues() != null) {
					results = toSearchResults(data.getIssues(), jiraSystem);
				}
			}
			return results;
		} catch (GeboCryptSecretException e) {
			throw new SearchServiceException("Error in secret format", e);
		} finally {

		}
	}

	private List<SearchResult> toSearchResults(List<IssueBean> issues, GJiraSystem jiraSystem) {
		List<SearchResult> out = new ArrayList<SearchResult>();
		for (IssueBean issueBean : issues) {
			SearchResult result = new SearchResult();
			result.setSystemConfigurationCode(jiraSystem.getCode());
			setOriginOn(result);
			String summary = JiraNavigationUtil.get(issueBean, "summary");
			String description = JiraNavigationUtil.get(issueBean, "description");
			result.setId(issueBean.getId());
			result.setNavigationReference(JiraNavigationUtil.toVirtualFilesystemReference(issueBean));
			result.setDescriptiveText(summary != null ? summary : description);
			if (issueBean.getSelf() != null) {
				result.setResultReference(new SearchResultReference());
				result.getResultReference().setUri(issueBean.getSelf());
				result.getResultReference().setName(description);
				result.getResultReference().setTitle(description);
				result.getResultReference().setContentType("text/html");
			}
			if (result.getChilds() == null) {
				result.setChilds(new ArrayList<SearchResult>());
			}
			addAttachments(result.getChilds(), issueBean, jiraSystem);
			addIssueLinks(result.getChilds(), issueBean, jiraSystem);
			out.add(result);
		}
		return out;
	}

	private void addIssueLinks(List<SearchResult> childs, IssueBean issueBean, GJiraSystem jiraSystem) {
		Object issuelinksNode = issueBean.getFields().containsKey("issuelinks");
		if (issuelinksNode != null && issuelinksNode instanceof Collection issuelinks) {
			for (Object entry : issuelinks) {
				if (entry instanceof Map entryasMap) {
					Object outwardIssue = entryasMap.get("outwardIssue");
					if (outwardIssue != null && outwardIssue instanceof Map outwardIssueMap) {
						Object id = outwardIssueMap.get("id");
						Object key = entryasMap.get("key");
						Object self = entryasMap.get("self");
						Object fields = entryasMap.get("fields");
						if (fields != null && fields instanceof Map fieldsMap) {
							Object summary = fieldsMap.get("summary");
							Object description = fieldsMap.get("description");
							SearchResult r = new SearchResult();
							r.setId(id != null ? id.toString() : null);
							r.setDescriptiveText(summary != null ? summary.toString()
									: description != null ? description.toString() : "");
							r.setSystemConfigurationCode(jiraSystem.getCode());
							r.setNestingLevel(1);
							r.setNavigationReference(JiraNavigationUtil.toVirtualFilesystemReferenceFromIssueLink(id,
									key, summary, description));
							if (self != null) {
								r.setResultReference(new SearchResultReference());
								r.getResultReference().setContentType("text/html");
								r.getResultReference().setName(
										summary != null ? summary.toString() : key != null ? key.toString() : "");
								r.getResultReference().setUri(self.toString());
							}
							setOriginOn(r);
							childs.add(r);
						}
					}

				}
			}
		}
	}

	private void addAttachments(List<SearchResult> childs, IssueBean issueBean, GJiraSystem jiraSystem) {
		Object attachmentNode = issueBean.getFields().containsKey("attachment");
		if (attachmentNode != null && attachmentNode instanceof Collection attachments) {
			for (Object entry : attachments) {
				if (entry instanceof Map entryasMap) {
					Object id = entryasMap.get("id");
					Object filename = entryasMap.get("filename");
					if (id != null && filename != null) {
						Object mimeType = entryasMap.get("mimeType");
						Object self = entryasMap.get("self");
						Object size = entryasMap.get("size");
						SearchResult r = new SearchResult();
						r.setId(id != null ? id.toString() : null);
						r.setDescriptiveText(filename != null ? filename.toString() : null);
						r.setSystemConfigurationCode(jiraSystem.getCode());
						r.setNestingLevel(1);
						r.setNavigationReference(
								JiraNavigationUtil.toVirtualFilesystemReferenceFromAttachmentId(id, filename, self));
						if (self != null) {
							r.setResultReference(new SearchResultReference());
							r.getResultReference().setContentType(mimeType != null ? mimeType.toString() : null);
							r.getResultReference().setName(filename != null ? filename.toString() : null);
							r.getResultReference()
									.setSize(size != null && size instanceof Number n ? n.longValue() : null);
							r.getResultReference().setUri(self.toString());
						}
						setOriginOn(r);
						childs.add(r);
					}
				}
			}
		}

	}

}
