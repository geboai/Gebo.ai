/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.CleanQueryUtil;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.redmine.client.model.Attachment;
import ai.gebo.redmine.client.model.GetIssues200Response;
import ai.gebo.redmine.client.model.GetSearch200Response;
import ai.gebo.redmine.client.model.Issue;
import ai.gebo.redmine.client.model.IssuePriority;
import ai.gebo.redmine.client.model.IssueStatus;
import ai.gebo.redmine.client.model.IssueSummary;
import ai.gebo.redmine.client.model.Search;
import ai.gebo.redmine.client.model.Tracker;
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.IGRedmineVirtualFilesystemConsumingService;
import ai.gebo.redmine.handler.RedmineBrowsingContext;
import ai.gebo.redmine.handler.RedmineResourceReference;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedmineNavigationCoordinates;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.redmine.handler.model.RedmineSection;
import ai.gebo.redmine.search.api.RedmineAdditionalSearchFilter;
import ai.gebo.redmine.search.api.RedmineIssuesSearchFilter;
import ai.gebo.redmine.search.api.RedmineResultsExtractionData;
import ai.gebo.redmine.search.api.TextMatchMode;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.impl.DataStructureJoinUtils;

/**
 * Live search on the Redmine systems. The standard search uses the Redmine
 * full text search over issues and wiki pages; the native search turns a
 * structured filter into issues queries (projects, trackers, statuses,
 * priorities, assignee, dates, numbers, with the text terms as a filter of
 * their own) where every condition holds; text terms with projects only go to
 * the full text search of those projects. Results are issues (with their content and
 * attachments as children) and wiki pages, each with the reference the
 * ingestion encoding reads back.
 */
@Service
public class RedmineSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<RedmineResultsExtractionData, GRedmineSystem, GRedmineProjectEndpoint, RedmineNativePositionObject, RedmineNavigationCoordinates, RedmineResourceReference, IGRedmineVirtualFilesystemConsumingService, RedmineBrowsingContext>
		implements INativeSearchService<RedmineResultsExtractionData, RedmineIssuesSearchFilter> {
	public static final String REDMINE = "redmine";
	public static final String REDMINE_STANDARD_QUERY_EXTRACTION_PROMPT = "redmine-standard-query-extraction-prompt";
	public static final String REDMINE_NATIVE_QUERY_EXTRACTION_PROMPT = "redmine-native-query-extraction-prompt";
	static final String REDMINE_PROJECTS_PROMPT_PARAM = "redmineProjects";
	/** How many results each follow-up search suggested by an analysis returns. */
	static final int RELATED_SEARCH_RESULTS = 50;
	private static final Pattern ISSUE_URL = Pattern.compile("/issues/(\\d+)");
	private static final Pattern WIKI_URL = Pattern.compile("/projects/([^/]+)/wiki/([^/?#]+)");
	private final RedmineApiClientFactory clientFactory;

	public RedmineSearchService(RedmineApiClientFactory clientFactory,
			GAbstractRemoteVirtualFilesystemConsumingService<GRedmineSystem, GRedmineProjectEndpoint, RedmineNativePositionObject, RedmineNavigationCoordinates, RedmineResourceReference> consumingService,
			GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GRedmineSystem, GRedmineProjectEndpoint, RedmineResourceReference, IGRedmineVirtualFilesystemConsumingService> handler,
			RedmineBrowsingService browsingService) {
		super(consumingService, handler, browsingService);
		this.clientFactory = clientFactory;
	}

	@Override
	public String getId() {
		return REDMINE;
	}

	@Override
	public String getProductId() {
		return REDMINE;
	}

	@Override
	public String getDescription() {
		return "Redmine Search";
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.REDMINE_MODULE;
	}

	@Override
	protected RedmineBrowsingContext createBrowsingContext(GRedmineSystem system) {
		return RedmineBrowsingContext.of(system.getCode());
	}

	private RedmineRemoteTree tree(GRedmineSystem system, SearchCallParameters parameters)
			throws SearchServiceException {
		try {
			return new RedmineRemoteTree(clientFactory.getConnection(system, parameters));
		} catch (GeboCryptSecretException | IllegalArgumentException e) {
			throw new SearchServiceException("Cannot open the Redmine connection of " + system.getCode(), e);
		}
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		return search(query, system, nEntryLimit, null);
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit,
			SearchCallParameters parameters) throws IOException, SearchServiceException {
		requireSystem(system);
		query = CleanQueryUtil.cleanQuery(query);
		if (!(system.getSystemConfigurationReference() instanceof GRedmineSystem redmineSystem)
				|| query.getQueryText() == null || query.getQueryText().isBlank()) {
			return List.of();
		}
		RedmineRemoteTree tree = tree(redmineSystem, parameters);
		try {
			tree.requireIdentity();
			List<Search> hits = fullText(tree, null, query.getQueryText(), false, true, nEntryLimit);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine search \"{}\" on {}: {} hits", query.getQueryText(), redmineSystem.getCode(),
						hits.size());
			}
			return toResults(tree, redmineSystem, hits, nEntryLimit);
		} catch (RestClientException e) {
			throw new SearchServiceException("Redmine search failed on " + redmineSystem.getCode(), e);
		}
	}

	/**
	 * The Redmine full text search over issues (and wiki pages when asked). Redmine
	 * matches all the words when all_words is missing or not blank ("0" included):
	 * any word matching is asked by sending it empty.
	 */
	private List<Search> fullText(RedmineRemoteTree tree, String projectId, String text, boolean allWords,
			boolean wikiPages, int limit) {
		GetSearch200Response response = projectId == null
				? tree.getConnection().search().getSearch(RedmineConnection.JSON, limit, null, null, null, null, text,
						"all", allWords ? "1" : "", null, 1, null, wikiPages ? 1 : null, null, null, null, null,
						null, null, null)
				: tree.getConnection().search().getSearchByProject(RedmineConnection.JSON, projectId, limit, null,
						null, null, null, text, "subprojects", allWords ? "1" : "", null, 1, null,
						wikiPages ? 1 : null, null, null, null, null, null, null, null);
		return response != null && response.getResults() != null ? response.getResults() : List.of();
	}

	private List<SearchResult> toResults(RedmineRemoteTree tree, GRedmineSystem system, List<Search> hits, int limit) {
		Map<String, SearchResult> results = new LinkedHashMap<String, SearchResult>();
		for (Search hit : hits) {
			if (results.size() >= limit) {
				break;
			}
			String type = hit.getType() != null ? hit.getType() : "";
			String url = hit.getUrl() != null ? hit.getUrl() : "";
			try {
				if (type.startsWith("issue")) {
					Matcher m = ISSUE_URL.matcher(url);
					Integer issueId = m.find() ? Integer.valueOf(m.group(1)) : hit.getId();
					String key = "issue-" + issueId;
					if (!results.containsKey(key)) {
						Issue issue = tree.issue(issueId, RedmineRemoteTree.ISSUE_CHILDREN_INCLUDES);
						results.put(key, issueResult(tree, system, issue));
					}
				} else if (type.startsWith("wiki")) {
					Matcher m = WIKI_URL.matcher(url);
					if (m.find()) {
						String projectId = URLDecoder.decode(m.group(1), StandardCharsets.UTF_8);
						String title = URLDecoder.decode(m.group(2), StandardCharsets.UTF_8);
						String key = "wiki-" + projectId + "-" + title;
						if (!results.containsKey(key)) {
							results.put(key, wikiResult(tree, system, projectId, title, hit));
						}
					}
				}
			} catch (RestClientException e) {
				if (!RedmineRemoteTree.isNotFound(e)) {
					LOGGER.warn("Cannot read the Redmine search hit " + url, e);
				}
			}
		}
		return new ArrayList<SearchResult>(results.values());
	}

	private SearchResult result(GRedmineSystem system, String id, String text, String uri, String name,
			String contentType, RedmineNavigationCoordinatesHolder reference, int nesting) {
		SearchResult result = new SearchResult();
		result.setId(id);
		result.setSystemConfigurationCode(system.getCode());
		result.setDescriptiveText(text);
		result.setNestingLevel(nesting);
		result.setNavigationReference(RedmineNavigationUtil.toReference(reference.projectId, reference.projectName,
				reference.steps, name));
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(uri);
		result.getResultReference().setName(name);
		result.getResultReference().setTitle(name);
		result.getResultReference().setContentType(contentType);
		result.setChilds(new ArrayList<SearchResult>());
		setOriginOn(result);
		return result;
	}

	private record RedmineNavigationCoordinatesHolder(String projectId, String projectName,
			List<RedminePathComponent> steps) {
	}

	private SearchResult issueResult(RedmineRemoteTree tree, GRedmineSystem system, Issue issue) {
		String projectId = issue.getProject() != null ? String.valueOf(issue.getProject().getId()) : null;
		String projectName = issue.getProject() != null ? issue.getProject().getName() : null;
		String base = tree.getBaseUri();
		String issueId = String.valueOf(issue.getId());
		List<RedminePathComponent> folder = List.of(
				new RedminePathComponent(RedminePathNodeType.SECTION, RedmineSection.ISSUES.name()),
				new RedminePathComponent(RedminePathNodeType.ISSUE, issueId));
		String label = RedmineNodes.issueLabel(issue.getId(), issue.getSubject());
		String text = label + (issue.getStatus() != null ? " [" + issue.getStatus().getName() + "]" : "")
				+ (issue.getDescription() != null ? "\n" + issue.getDescription() : "");
		SearchResult result = result(system, "issue-" + issueId, text, base + "/issues/" + issueId, label,
				"text/html", new RedmineNavigationCoordinatesHolder(projectId, projectName, folder), 0);
		List<RedminePathComponent> content = new ArrayList<RedminePathComponent>(folder);
		content.add(new RedminePathComponent(RedminePathNodeType.ISSUE_CONTENT, issueId));
		result.getChilds().add(result(system, "issue-content-" + issueId, text, base + "/issues/" + issueId,
				label + ".md", RedmineNodes.MARKDOWN,
				new RedmineNavigationCoordinatesHolder(projectId, projectName, content), 1));
		if (issue.getAttachments() != null) {
			for (Attachment attachment : issue.getAttachments()) {
				List<RedminePathComponent> steps = new ArrayList<RedminePathComponent>(folder);
				steps.add(new RedminePathComponent(RedminePathNodeType.ATTACHMENT, String.valueOf(attachment.getId())));
				SearchResult child = result(system, "attachment-" + attachment.getId(), attachment.getFilename(),
						base + "/attachments/" + attachment.getId(), attachment.getFilename(),
						attachment.getContentType(), new RedmineNavigationCoordinatesHolder(projectId, projectName, steps),
						1);
				child.getResultReference()
						.setSize(attachment.getFilesize() != null ? attachment.getFilesize().longValue() : null);
				result.getChilds().add(child);
			}
		}
		return result;
	}

	private SearchResult wikiResult(RedmineRemoteTree tree, GRedmineSystem system, String projectId, String title,
			Search hit) {
		String base = tree.getBaseUri();
		List<RedminePathComponent> folder = List.of(
				new RedminePathComponent(RedminePathNodeType.SECTION, RedmineSection.WIKI.name()),
				new RedminePathComponent(RedminePathNodeType.WIKI_PAGE, title));
		String uri = base + "/projects/" + RedmineNodes.encode(projectId) + "/wiki/" + RedmineNodes.encode(title);
		String text = title + (hit.getDescription() != null ? "\n" + hit.getDescription() : "");
		SearchResult result = result(system, "wiki-" + projectId + "-" + title, text, uri, title, "text/html",
				new RedmineNavigationCoordinatesHolder(projectId, projectId, folder), 0);
		List<RedminePathComponent> content = new ArrayList<RedminePathComponent>(folder);
		content.add(new RedminePathComponent(RedminePathNodeType.WIKI_CONTENT, title));
		result.getChilds().add(result(system, "wiki-content-" + projectId + "-" + title, text, uri, title + ".md",
				RedmineNodes.MARKDOWN, new RedmineNavigationCoordinatesHolder(projectId, projectId, content), 1));
		return result;
	}

	@Override
	public List<SearchResult> nativeSearch(RedmineIssuesSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		return nativeSearch(query, system, nEntryLimit, null);
	}

	private static Map<String, Integer> byName(List<?> items) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Object item : items) {
			if (item instanceof Tracker t && t.getName() != null) {
				map.put(t.getName().toLowerCase(Locale.ROOT), t.getId());
			} else if (item instanceof IssueStatus s && s.getName() != null) {
				map.put(s.getName().toLowerCase(Locale.ROOT), s.getId());
			} else if (item instanceof IssuePriority p && p.getName() != null) {
				map.put(p.getName().toLowerCase(Locale.ROOT), p.getId());
			}
		}
		return map;
	}

	@Override
	public List<SearchResult> nativeSearch(RedmineIssuesSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit, SearchCallParameters parameters) throws IOException, SearchServiceException {
		requireSystem(system);
		if (query == null || !(system.getSystemConfigurationReference() instanceof GRedmineSystem redmineSystem)) {
			return List.of();
		}
		boolean text = RedmineIssuesQueryTranslator.hasTextTerms(query);
		boolean issueAttributes = RedmineIssuesQueryTranslator.hasIssueAttributesFilter(query);
		// every condition must hold: with issue attributes the text terms become a
		// filter of the issues queries; without them the projects only scope the
		// full text search, and alone they list the issues of those projects
		boolean structured = issueAttributes
				|| (!text && RedmineIssuesQueryTranslator.hasStructuredFilter(query));
		boolean fullText = text && !issueAttributes;
		if (!structured && !fullText) {
			LOGGER.debug("Empty Redmine filter, nothing searched");
			return List.of();
		}
		RedmineRemoteTree tree = tree(redmineSystem, parameters);
		RedmineConnection connection = tree.getConnection();
		try {
			tree.requireIdentity();
			Map<Integer, Boolean> issueIds = new LinkedHashMap<Integer, Boolean>();
			if (structured) {
				RedmineIssuesQueryTranslator translator = new RedmineIssuesQueryTranslator(
						() -> byName(connection.trackers().getTrackers(RedmineConnection.JSON, null).getTrackers()),
						() -> byName(connection.issueStatuses().getIssueStatuses(RedmineConnection.JSON, null)
								.getIssueStatuses()),
						() -> byName(connection.enumerations().getIssuePriorities(RedmineConnection.JSON, null)
								.getIssuePriorities()));
				for (RedmineIssuesQuery issuesQuery : translator.translate(query)) {
					int offset = 0;
					try {
						while (issueIds.size() < nEntryLimit) {
							int pageSize = Math.min(RedmineRemoteTree.REDMINE_PAGE_SIZE, nEntryLimit - issueIds.size());
							GetIssues200Response page = issuesQuery.execute(connection, offset, pageSize);
							List<IssueSummary> items = page != null && page.getIssues() != null ? page.getIssues()
									: List.of();
							items.forEach(x -> issueIds.put(x.getId(), true));
							offset += items.size();
							if (items.isEmpty() || page.getTotalCount() == null || offset >= page.getTotalCount()) {
								break;
							}
						}
					} catch (HttpClientErrorException e) {
						if (!isProjectNotVisible(e)) {
							throw e;
						}
						LOGGER.debug("Redmine issues query {} skipped on {}: project not visible ({})", issuesQuery,
								redmineSystem.getCode(), e.getStatusCode());
					}
				}
			}
			List<Search> textHits = new ArrayList<Search>();
			if (fullText) {
				String terms = String.join(" ",
						query.getTextTerms().stream().filter(x -> x != null && !x.isBlank()).toList());
				boolean allWords = query.getTextTermsMatchMode() == TextMatchMode.ALL;
				List<String> projects = query.getProjects() != null
						? query.getProjects().stream().filter(x -> x != null && !x.isBlank())
								.map(RedmineIssuesQueryTranslator::projectIdOf).distinct().toList()
						: List.of();
				if (projects.isEmpty()) {
					textHits.addAll(fullText(tree, null, terms, allWords, false, nEntryLimit));
				} else {
					for (String project : projects) {
						try {
							textHits.addAll(fullText(tree, project, terms, allWords, false, nEntryLimit));
						} catch (HttpClientErrorException e) {
							if (!isProjectNotVisible(e)) {
								throw e;
							}
							LOGGER.debug("Redmine full text search skipped on project {} of {}: not visible ({})",
									project, redmineSystem.getCode(), e.getStatusCode());
						}
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine native search on {}: {} issues by filter, {} full text hits",
						redmineSystem.getCode(), issueIds.size(), textHits.size());
			}
			List<SearchResult> results = new ArrayList<SearchResult>();
			for (Integer issueId : issueIds.keySet()) {
				if (results.size() >= nEntryLimit) {
					break;
				}
				try {
					results.add(issueResult(tree, redmineSystem,
							tree.issue(issueId, RedmineRemoteTree.ISSUE_CHILDREN_INCLUDES)));
				} catch (RestClientException e) {
					LOGGER.warn("Cannot read the Redmine issue " + issueId, e);
				}
			}
			for (SearchResult hit : toResults(tree, redmineSystem, textHits, nEntryLimit)) {
				if (results.size() >= nEntryLimit) {
					break;
				}
				if (results.stream().noneMatch(r -> r.getId().equals(hit.getId()))) {
					results.add(hit);
				}
			}
			return results;
		} catch (RestClientException e) {
			throw new SearchServiceException("Redmine native search failed on " + redmineSystem.getCode(), e);
		}
	}

	/**
	 * A project a filter names that the account cannot see answers 403, one that
	 * does not exist 404: either way that project has nothing to give, and the
	 * other queries of the same search still run.
	 */
	static boolean isProjectNotVisible(HttpClientErrorException e) {
		int status = e.getStatusCode().value();
		return status == 403 || status == 404;
	}

	private static void requireSystem(SearchableSystemMetaData system) throws SearchServiceException {
		if (system == null) {
			throw new SearchServiceException("Unknown Redmine searchable system");
		}
	}

	@Override
	public Class<RedmineIssuesSearchFilter> getNativeSearchDataStructureType() {
		return RedmineIssuesSearchFilter.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return REDMINE_NATIVE_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return REDMINE_STANDARD_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		StringBuilder catalog = new StringBuilder();
		if (cataloguesSample != null) {
			for (CatalogueSample sample : cataloguesSample) {
				catalog.append("- code: ").append(sample.getCode()).append(", name: ").append(sample.getDescription())
						.append('\n');
			}
		}
		return Map.of(REDMINE_PROJECTS_PROMPT_PARAM, catalog.toString());
	}

	@Override
	public Class<RedmineResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {
		return RedmineResultsExtractionData.class;
	}

	@Override
	public RedmineResultsExtractionData aggregate(RedmineResultsExtractionData oldConsolidated,
			RedmineResultsExtractionData consolidated) {
		RedmineResultsExtractionData data = basicAggregate(oldConsolidated, consolidated,
				new RedmineResultsExtractionData());
		RedmineAdditionalSearchFilter joined = new RedmineAdditionalSearchFilter();
		for (RedmineResultsExtractionData item : new RedmineResultsExtractionData[] { oldConsolidated,
				consolidated }) {
			if (item != null && item.getAdditionalRedmineSearchIdeas() != null) {
				RedmineAdditionalSearchFilter ideas = item.getAdditionalRedmineSearchIdeas();
				DataStructureJoinUtils.join(ideas.getIssueIds(), joined::getIssueIds, joined::setIssueIds);
				DataStructureJoinUtils.join(ideas.getTextTerms(), joined::getTextTerms, joined::setTextTerms);
				if (ideas.getTextTermsMatchMode() != null) {
					joined.setTextTermsMatchMode(ideas.getTextTermsMatchMode());
				}
			}
		}
		data.setAdditionalRedmineSearchIdeas(joined);
		return data;
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			RedmineResultsExtractionData extractedData) throws IOException, SearchServiceException {
		if (extractedData == null || extractedData.getAdditionalRedmineSearchIdeas() == null) {
			return null;
		}
		RedmineAdditionalSearchFilter ideas = extractedData.getAdditionalRedmineSearchIdeas();
		RedmineIssuesSearchFilter filter = new RedmineIssuesSearchFilter();
		boolean any = DataStructureJoinUtils.doneCopy(ideas.getIssueIds(), filter::setIssueIds);
		any |= DataStructureJoinUtils.doneCopy(ideas.getTextTerms(), filter::setTextTerms);
		filter.setTextTermsMatchMode(ideas.getTextTermsMatchMode());
		if (!any) {
			return null;
		}
		List<SearchResult> results = new ArrayList<SearchResult>();
		for (SearchableSystemMetaData system : getSearchableSystems()) {
			results.addAll(nativeSearch(filter, system, RELATED_SEARCH_RESULTS));
		}
		return new SearchResultAnalisysOutcome(null, results);
	}
}
