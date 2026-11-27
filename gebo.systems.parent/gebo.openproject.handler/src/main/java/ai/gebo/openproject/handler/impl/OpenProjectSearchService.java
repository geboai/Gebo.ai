/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
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
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.openproject.handler.IGOpenProjectVirtualFilesystemConsumingService;
import ai.gebo.openproject.handler.OpenProjectBrowsingContext;
import ai.gebo.openproject.handler.OpenProjectResourceReference;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectNavigationCoordinates;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
import ai.gebo.openproject.handler.model.OpenProjectSection;
import ai.gebo.openproject.search.api.OpenProjectAdditionalSearchFilter;
import ai.gebo.openproject.search.api.OpenProjectResultsExtractionData;
import ai.gebo.openproject.search.api.OpenProjectWorkPackagesSearchFilter;
import ai.gebo.openproject.search.api.TextMatchMode;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.impl.DataStructureJoinUtils;

/**
 * Live search of work packages on the OpenProject systems. The standard search
 * finds the work packages whose subject, description or comments contain any
 * word of the query; the native search turns a structured filter (projects,
 * numbers, types, statuses, priorities, assignee, dates, text terms) into the
 * filters of a work packages query of the API v3, where every condition holds.
 * Results are work packages, with their rendered content and attachments as
 * children, each with the reference the ingestion encoding reads back.
 * Documents, meetings and news are found through the knowledge bases they are
 * ingested in.
 */
@Service
public class OpenProjectSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<OpenProjectResultsExtractionData, GOpenProjectSystem, GOpenProjectProjectEndpoint, OpenProjectNativePositionObject, OpenProjectNavigationCoordinates, OpenProjectResourceReference, IGOpenProjectVirtualFilesystemConsumingService, OpenProjectBrowsingContext>
		implements INativeSearchService<OpenProjectResultsExtractionData, OpenProjectWorkPackagesSearchFilter> {
	public static final String OPENPROJECT = "openproject";
	public static final String OPENPROJECT_STANDARD_QUERY_EXTRACTION_PROMPT = "openproject-standard-query-extraction-prompt";
	public static final String OPENPROJECT_NATIVE_QUERY_EXTRACTION_PROMPT = "openproject-native-query-extraction-prompt";
	static final String OPENPROJECT_PROJECTS_PROMPT_PARAM = "openprojectProjects";
	/** How many results each follow-up search suggested by an analysis returns. */
	static final int RELATED_SEARCH_RESULTS = 50;
	private final OpenProjectApiClientFactory clientFactory;

	public OpenProjectSearchService(OpenProjectApiClientFactory clientFactory,
			GAbstractRemoteVirtualFilesystemConsumingService<GOpenProjectSystem, GOpenProjectProjectEndpoint, OpenProjectNativePositionObject, OpenProjectNavigationCoordinates, OpenProjectResourceReference> consumingService,
			GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GOpenProjectSystem, GOpenProjectProjectEndpoint, OpenProjectResourceReference, IGOpenProjectVirtualFilesystemConsumingService> handler,
			OpenProjectBrowsingService browsingService) {
		super(consumingService, handler, browsingService);
		this.clientFactory = clientFactory;
	}

	@Override
	public String getId() {
		return OPENPROJECT;
	}

	@Override
	public String getProductId() {
		return OPENPROJECT;
	}

	@Override
	public String getDescription() {
		return "OpenProject Search";
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.OPENPROJECT_MODULE;
	}

	@Override
	protected OpenProjectBrowsingContext createBrowsingContext(GOpenProjectSystem system) {
		return OpenProjectBrowsingContext.of(system.getCode());
	}

	private OpenProjectRemoteTree tree(GOpenProjectSystem system, SearchCallParameters parameters)
			throws SearchServiceException {
		try {
			return new OpenProjectRemoteTree(clientFactory.getConnection(system, parameters));
		} catch (GeboCryptSecretException | IllegalArgumentException e) {
			throw new SearchServiceException("Cannot open the OpenProject connection of " + system.getCode(), e);
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
		if (!(system.getSystemConfigurationReference() instanceof GOpenProjectSystem openprojectSystem)
				|| query.getQueryText() == null || query.getQueryText().isBlank()) {
			return List.of();
		}
		OpenProjectWorkPackagesSearchFilter filter = new OpenProjectWorkPackagesSearchFilter();
		filter.setTextTerms(Arrays.stream(query.getQueryText().split(" ")).filter(x -> !x.isBlank()).toList());
		filter.setTextTermsMatchMode(TextMatchMode.ANY);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject search \"{}\" on {} as any of {}", query.getQueryText(),
					openprojectSystem.getCode(), filter.getTextTerms());
		}
		return searchWorkPackages(filter, openprojectSystem, nEntryLimit, parameters);
	}

	private SearchResult result(GOpenProjectSystem system, String id, String text, String uri, String name,
			String contentType, String projectId, String projectName, List<OpenProjectPathComponent> steps,
			int nesting) {
		SearchResult result = new SearchResult();
		result.setId(id);
		result.setSystemConfigurationCode(system.getCode());
		result.setDescriptiveText(text);
		result.setNestingLevel(nesting);
		result.setNavigationReference(OpenProjectNavigationUtil.toReference(projectId, projectName, steps, name));
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(uri);
		result.getResultReference().setName(name);
		result.getResultReference().setTitle(name);
		result.getResultReference().setContentType(contentType);
		result.setChilds(new ArrayList<SearchResult>());
		setOriginOn(result);
		return result;
	}

	/**
	 * A work package as a result, its rendered content and its attachments as
	 * children; the path is the flat one the ingestion encoding resolves step by
	 * step, also for child work packages.
	 */
	private SearchResult workPackageResult(OpenProjectRemoteTree tree, GOpenProjectSystem system,
			Map<String, Object> wp) {
		String id = OpenProjectHal.string(wp, "id");
		String projectId = tree.projectIdentifierOf(wp);
		String projectName = OpenProjectHal.linkTitle(wp, "project");
		String base = tree.getBaseUri();
		String uri = OpenProjectNodes.workPackageUrl(base, id);
		List<OpenProjectPathComponent> folder = List.of(
				new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, OpenProjectSection.WORK_PACKAGES.name()),
				new OpenProjectPathComponent(OpenProjectPathNodeType.WORK_PACKAGE, id));
		String label = OpenProjectNodes.workPackageLabel(id, OpenProjectHal.text(wp, "subject"));
		String type = OpenProjectHal.linkTitle(wp, "type");
		String status = OpenProjectHal.linkTitle(wp, "status");
		String description = OpenProjectHal.text(wp, "description.raw");
		String text = label + (type != null || status != null
				? " [" + (type != null ? type : "") + (type != null && status != null ? ", " : "")
						+ (status != null ? status : "") + "]"
				: "") + (description != null ? "\n" + description : "");
		SearchResult result = result(system, "work-package-" + id, text, uri, label, "text/html", projectId,
				projectName, folder, 0);
		List<OpenProjectPathComponent> content = new ArrayList<OpenProjectPathComponent>(folder);
		content.add(new OpenProjectPathComponent(OpenProjectPathNodeType.WORK_PACKAGE_CONTENT, id));
		result.getChilds().add(result(system, "work-package-content-" + id, text, uri, label + ".md",
				OpenProjectNodes.MARKDOWN, projectId, projectName, content, 1));
		for (Map<String, Object> attachment : tree.attachments("/work_packages/" + id)) {
			String attachmentId = OpenProjectHal.string(attachment, "id");
			String fileName = OpenProjectHal.text(attachment, "fileName");
			List<OpenProjectPathComponent> steps = new ArrayList<OpenProjectPathComponent>(folder);
			steps.add(new OpenProjectPathComponent(OpenProjectPathNodeType.ATTACHMENT, attachmentId));
			SearchResult child = result(system, "attachment-" + attachmentId, fileName, base + "/attachments/" + attachmentId,
					fileName, OpenProjectHal.text(attachment, "contentType"), projectId, projectName, steps, 1);
			child.getResultReference().setSize(OpenProjectHal.longValue(attachment, "fileSize"));
			result.getChilds().add(child);
		}
		return result;
	}

	@Override
	public List<SearchResult> nativeSearch(OpenProjectWorkPackagesSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		return nativeSearch(query, system, nEntryLimit, null);
	}

	@Override
	public List<SearchResult> nativeSearch(OpenProjectWorkPackagesSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit, SearchCallParameters parameters) throws IOException, SearchServiceException {
		requireSystem(system);
		if (query == null || !(system.getSystemConfigurationReference() instanceof GOpenProjectSystem openprojectSystem)) {
			return List.of();
		}
		if (!OpenProjectWorkPackagesQueryTranslator.hasFilter(query)) {
			LOGGER.debug("Empty OpenProject filter, nothing searched");
			return List.of();
		}
		return searchWorkPackages(query, openprojectSystem, nEntryLimit, parameters);
	}

	/**
	 * Runs the queries of a filter and merges their work packages, the first
	 * query's first, up to the limit.
	 */
	private List<SearchResult> searchWorkPackages(OpenProjectWorkPackagesSearchFilter filter,
			GOpenProjectSystem system, int nEntryLimit, SearchCallParameters parameters) throws SearchServiceException {
		OpenProjectRemoteTree tree = tree(system, parameters);
		try {
			tree.requireIdentity();
			OpenProjectWorkPackagesQueryTranslator translator = new OpenProjectWorkPackagesQueryTranslator(
					() -> tree.idsByName("/types"), () -> tree.idsByName("/statuses"),
					() -> tree.idsByName("/priorities"), tree::visibleProjectNumericId);
			Map<String, Map<String, Object>> found = new LinkedHashMap<String, Map<String, Object>>();
			for (String filters : translator.translate(filter)) {
				if (found.size() >= nEntryLimit) {
					break;
				}
				for (Map<String, Object> wp : tree.searchWorkPackages(filters, nEntryLimit)) {
					found.putIfAbsent(OpenProjectHal.string(wp, "id"), wp);
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenProject search on {}: {} work packages", system.getCode(), found.size());
			}
			List<SearchResult> results = new ArrayList<SearchResult>();
			for (Map<String, Object> wp : found.values()) {
				if (results.size() >= nEntryLimit) {
					break;
				}
				try {
					results.add(workPackageResult(tree, system, wp));
				} catch (RestClientException e) {
					LOGGER.warn("Cannot read the OpenProject work package " + OpenProjectHal.string(wp, "id"), e);
				}
			}
			return results;
		} catch (RestClientException e) {
			throw new SearchServiceException("OpenProject search failed on " + system.getCode() + ": "
					+ OpenProjectErrors.of(e, system.getBaseUri()), e);
		}
	}

	private static void requireSystem(SearchableSystemMetaData system) throws SearchServiceException {
		if (system == null) {
			throw new SearchServiceException("Unknown OpenProject searchable system");
		}
	}

	@Override
	public Class<OpenProjectWorkPackagesSearchFilter> getNativeSearchDataStructureType() {
		return OpenProjectWorkPackagesSearchFilter.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return OPENPROJECT_NATIVE_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return OPENPROJECT_STANDARD_QUERY_EXTRACTION_PROMPT;
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
		return Map.of(OPENPROJECT_PROJECTS_PROMPT_PARAM, catalog.toString());
	}

	@Override
	public Class<OpenProjectResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {
		return OpenProjectResultsExtractionData.class;
	}

	@Override
	public OpenProjectResultsExtractionData aggregate(OpenProjectResultsExtractionData oldConsolidated,
			OpenProjectResultsExtractionData consolidated) {
		OpenProjectResultsExtractionData data = basicAggregate(oldConsolidated, consolidated,
				new OpenProjectResultsExtractionData());
		OpenProjectAdditionalSearchFilter joined = new OpenProjectAdditionalSearchFilter();
		for (OpenProjectResultsExtractionData item : new OpenProjectResultsExtractionData[] { oldConsolidated,
				consolidated }) {
			if (item != null && item.getAdditionalOpenProjectSearchIdeas() != null) {
				OpenProjectAdditionalSearchFilter ideas = item.getAdditionalOpenProjectSearchIdeas();
				DataStructureJoinUtils.join(ideas.getWorkPackageIds(), joined::getWorkPackageIds,
						joined::setWorkPackageIds);
				DataStructureJoinUtils.join(ideas.getTextTerms(), joined::getTextTerms, joined::setTextTerms);
				if (ideas.getTextTermsMatchMode() != null) {
					joined.setTextTermsMatchMode(ideas.getTextTermsMatchMode());
				}
			}
		}
		data.setAdditionalOpenProjectSearchIdeas(joined);
		return data;
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			OpenProjectResultsExtractionData extractedData) throws IOException, SearchServiceException {
		if (extractedData == null || extractedData.getAdditionalOpenProjectSearchIdeas() == null) {
			return null;
		}
		OpenProjectAdditionalSearchFilter ideas = extractedData.getAdditionalOpenProjectSearchIdeas();
		OpenProjectWorkPackagesSearchFilter filter = new OpenProjectWorkPackagesSearchFilter();
		boolean any = DataStructureJoinUtils.doneCopy(ideas.getWorkPackageIds(), filter::setWorkPackageIds);
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
