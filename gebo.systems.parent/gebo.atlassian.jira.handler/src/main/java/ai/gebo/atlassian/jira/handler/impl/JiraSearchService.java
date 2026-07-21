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
import ai.gebo.atlassian.jira.search.api.JiraResultsExtractionData;
import ai.gebo.atlassian.jira.search.api.JiraAdditionalSearchFilter;
import ai.gebo.atlassian.jira.search.api.JiraIssuesSearchFilter;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jira.cloud.client.api.IssueSearchApi;
import ai.gebo.jira.cloud.client.api.ProjectsApi;
import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.IssueBean;
import ai.gebo.jira.cloud.client.model.SearchAndReconcileResults;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.impl.DataStructureJoinUtils;

@Service
public class JiraSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<JiraResultsExtractionData, GJiraSystem, GJiraProjectEndpoint, JiraNativePositionObject, JiraNavigationCoordinates, JiraResourceReference, IGJiraVirtualFilesystemConsumingService, JiraBrowsingContext>
		implements INativeSearchService<JiraResultsExtractionData, JiraIssuesSearchFilter> {
	private static final String END_JIRA_PROJECTS = "END_JIRA_PROJECTS";
	private static final String END_JIRA_PROJECT = "END_JIRA_PROJECT";
	private static final String CODE = "code: ";
	private static final String DESCRIPTION = "description: ";
	private static final String NEWLINE = "\r\n";
	private static final String JIRA_PROJECT = "JIRA_PROJECT";
	private static final String JIRA_PROJECTS = "JIRA_PROJECTS";
	private static final String JIRA_PROJECTS_PROMPT_PARAM = "jiraProjects";
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
		data.setAdditionalJiraSearchIdeas(joinFilters(oldConsolidated, consolidated));
		return data;
	}

	private JiraAdditionalSearchFilter joinFilters(JiraResultsExtractionData oldConsolidated,
			JiraResultsExtractionData consolidated) {
		JiraAdditionalSearchFilter outData = new JiraAdditionalSearchFilter();
		join(oldConsolidated, outData);
		join(consolidated, outData);
		return outData;
	}

	private void join(JiraResultsExtractionData container, JiraAdditionalSearchFilter data) {
		if (container != null && container.getAdditionalJiraSearchIdeas() != null) {
			join(container.getAdditionalJiraSearchIdeas(), data);
		}
	}

	private void join(JiraAdditionalSearchFilter joined, JiraAdditionalSearchFilter data) {
		DataStructureJoinUtils.join(joined.getDescriptionTerms(), data::getDescriptionTerms, data::setDescriptionTerms);
		DataStructureJoinUtils.join(joined.getIssueKeys(), data::getIssueKeys, data::setIssueKeys);
		DataStructureJoinUtils.join(joined.getSummaryTerms(), data::getSummaryTerms, data::setSummaryTerms);
		data.setDescriptionTermsMatchMode(joined.getDescriptionTermsMatchMode());
		data.setSummaryTermsMatchMode(joined.getSummaryTermsMatchMode());
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
			JiraResultsExtractionData extractedData) throws IOException, SearchServiceException {
		SearchResultAnalisysOutcome outcome = null;
		if (extractedData != null && extractedData.getAdditionalJiraSearchIdeas() != null) {
			JiraIssuesSearchFilter searchFilter = new JiraIssuesSearchFilter();
			boolean atLeastASearchFieldIsSet = false;
			atLeastASearchFieldIsSet = DataStructureJoinUtils.doneCopy(extractedData.getAdditionalJiraSearchIdeas().getDescriptionTerms(),
					searchFilter.getIssuesAttributesFilter()::setDescriptionTerms);
			atLeastASearchFieldIsSet |= DataStructureJoinUtils.doneCopy(extractedData.getAdditionalJiraSearchIdeas().getIssueKeys(),
					searchFilter.getIssuesAttributesFilter()::setIssueKeys);
			atLeastASearchFieldIsSet |= DataStructureJoinUtils.doneCopy(extractedData.getAdditionalJiraSearchIdeas().getSummaryTerms(),
					searchFilter.getIssuesAttributesFilter()::setSummaryTerms);
			searchFilter.getIssuesAttributesFilter().setDescriptionTermsMatchMode(
					extractedData.getAdditionalJiraSearchIdeas().getDescriptionTermsMatchMode());
			searchFilter.getIssuesAttributesFilter()
					.setSummaryTermsMatchMode(extractedData.getAdditionalJiraSearchIdeas().getSummaryTermsMatchMode());
			if (atLeastASearchFieldIsSet) {
				List<SearchableSystemMetaData> systems = getSearchableSystems();
				List<SearchResult> searchResults = new ArrayList<SearchResult>();
				for (SearchableSystemMetaData system : systems) {
					List<SearchResult> _searchResults = nativeSearch(searchFilter, system, 50);
					if (_searchResults != null)
						searchResults.addAll(_searchResults);
				}
				outcome = new SearchResultAnalisysOutcome(null, searchResults);

			}
		}
		return outcome;
	}

	@Override
	protected JiraBrowsingContext createBrowsingContext(GJiraSystem systemType) {

		return JiraBrowsingContext.of(systemType.getCode());
	}

	@Override
	public List<SearchResult> nativeSearch(JiraIssuesSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
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
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {

		return Map.of(JIRA_PROJECTS_PROMPT_PARAM, renderJiraProjects(cataloguesSample));
	}

	private Object renderJiraProjects(List<CatalogueSample> cataloguesSample) {
		StringBuffer buffer = new StringBuffer();
		if (cataloguesSample != null && !cataloguesSample.isEmpty()) {
			buffer.append(JIRA_PROJECTS);
			buffer.append(NEWLINE);
			for (CatalogueSample catalogueSample : cataloguesSample) {
				buffer.append(JIRA_PROJECT);
				buffer.append(NEWLINE);
				buffer.append(catalogueSample.getCode());
				buffer.append(NEWLINE);
				buffer.append(DESCRIPTION);
				buffer.append(catalogueSample.getDescription());
				buffer.append(NEWLINE);
				buffer.append(END_JIRA_PROJECT);
				buffer.append(NEWLINE);
			}
			buffer.append(END_JIRA_PROJECTS);
			buffer.append(NEWLINE);
		}
		return buffer.toString();
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

	private SearchResult toSearchResultNode(IssueBean issueBean, GJiraSystem jiraSystem, boolean asFolder) {
		SearchResult result = new SearchResult();
		result.setSystemConfigurationCode(jiraSystem.getCode());
		setOriginOn(result);
		String summary = JiraNavigationUtil.get(issueBean, "summary");
		String description = JiraNavigationUtil.get(issueBean, "description");
		result.setId(issueBean.getId());
		result.setNavigationReference(JiraNavigationUtil.toVirtualFilesystemReference(issueBean, asFolder));
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
		return result;
	}

	private List<SearchResult> toSearchResults(List<IssueBean> issues, GJiraSystem jiraSystem) {
		List<SearchResult> out = new ArrayList<SearchResult>();
		for (IssueBean issueBean : issues) {
			SearchResult result = toSearchResultNode(issueBean, jiraSystem, true);
			result.getChilds().add(toSearchResultNode(issueBean, jiraSystem, false));
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

	@Override
	public String getProductId() {
		
		return JIRA;
	}

}
