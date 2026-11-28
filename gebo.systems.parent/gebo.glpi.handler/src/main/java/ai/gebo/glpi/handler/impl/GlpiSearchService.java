/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
import ai.gebo.glpi.client.model.Document;
import ai.gebo.glpi.client.model.Followup;
import ai.gebo.glpi.client.model.KBArticle;
import ai.gebo.glpi.client.model.Solution;
import ai.gebo.glpi.client.model.Ticket;
import ai.gebo.glpi.client.model.TicketTask;
import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.glpi.handler.GlpiBrowsingContext;
import ai.gebo.glpi.handler.GlpiResourceReference;
import ai.gebo.glpi.handler.IGGlpiVirtualFilesystemConsumingService;
import ai.gebo.glpi.handler.config.GlpiHandlerConfig;
import ai.gebo.glpi.handler.model.GlpiNativePositionObject;
import ai.gebo.glpi.handler.model.GlpiNavigationCoordinates;
import ai.gebo.glpi.handler.model.GlpiPathComponent;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.glpi.search.api.GlpiAdditionalSearchFilter;
import ai.gebo.glpi.search.api.GlpiResultsExtractionData;
import ai.gebo.glpi.search.api.GlpiSearchFilter;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.impl.DataStructureJoinUtils;

/**
 * Live search over GLPI tickets and knowledge base articles for the chat
 * agents. Every result is a streamable item: a ticket is its body, with its
 * follow-ups, tasks, solutions and documents as children; an article is
 * itself. Their navigation references use the ingestion encoding, so
 * loadSearchResult streams them as the ingestion does.
 */
@Service
public class GlpiSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<GlpiResultsExtractionData, GGlpiSystem, GGlpiProjectEndpoint, GlpiNativePositionObject, GlpiNavigationCoordinates, GlpiResourceReference, IGGlpiVirtualFilesystemConsumingService, GlpiBrowsingContext>
		implements INativeSearchService<GlpiResultsExtractionData, GlpiSearchFilter> {
	public static final String GLPI_NATIVE_QUERY_EXTRACTION_PROMPT = "glpi-native-query-extraction-prompt";
	public static final String GLPI_STANDARD_QUERY_EXTRACTION_PROMPT = "glpi-standard-query-extraction-prompt";
	static final String GLPI_ENTITIES_PROMPT_PARAM = "glpiEntities";
	static final String GLPI = "glpi";
	static final String SORT_BY_MODIFICATION = "date_mod:desc";
	/** Results asked per system when following the analysis suggestions */
	static final int RELATED_SEARCH_ENTRIES = 50;

	private final GlpiApiClientFactory apiClientFactory;

	public GlpiSearchService(GlpiApiClientFactory apiClientFactory,
			GAbstractRemoteVirtualFilesystemConsumingService<GGlpiSystem, GGlpiProjectEndpoint, GlpiNativePositionObject, GlpiNavigationCoordinates, GlpiResourceReference> virtualFileSystemConsumingService,
			GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GGlpiSystem, GGlpiProjectEndpoint, GlpiResourceReference, IGGlpiVirtualFilesystemConsumingService> contentManagementSystemHandler,
			GlpiHandlerConfig config, GlpiBrowsingService browsingService) {
		super(virtualFileSystemConsumingService, contentManagementSystemHandler, browsingService);
		this.apiClientFactory = apiClientFactory;
	}

	@Override
	public String getId() {
		return GLPI;
	}

	@Override
	public String getProductId() {
		return GLPI;
	}

	@Override
	public String getDescription() {
		return "GLPI Search";
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GLPI_MODULE;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return GLPI_STANDARD_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return GLPI_NATIVE_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Class<GlpiResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {
		return GlpiResultsExtractionData.class;
	}

	@Override
	public Class<GlpiSearchFilter> getNativeSearchDataStructureType() {
		return GlpiSearchFilter.class;
	}

	@Override
	protected GlpiBrowsingContext createBrowsingContext(GGlpiSystem system) {
		return GlpiBrowsingContext.of(system.getCode());
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		return search(query, system, nEntryLimit, null);
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit,
			SearchCallParameters parameters) throws IOException, SearchServiceException {
		query = CleanQueryUtil.cleanQuery(query);
		if (!(system.getSystemConfigurationReference() instanceof GGlpiSystem glpiSystem)
				|| GlpiRsqlUtil.words(query.getQueryText()).isEmpty()) {
			return List.of();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI text search '" + query.getQueryText() + "' on " + glpiSystem.getCode());
		}
		String kbFilter = GlpiRsqlUtil.kbArticlesTextFilter(query.getQueryText());
		return execute(glpiSystem, true, GlpiRsqlUtil.ticketsTextFilter(query.getQueryText()), kbFilter != null,
				kbFilter, nEntryLimit, parameters);
	}

	@Override
	public List<SearchResult> nativeSearch(GlpiSearchFilter query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		return nativeSearch(query, system, nEntryLimit, null);
	}

	@Override
	public List<SearchResult> nativeSearch(GlpiSearchFilter query, SearchableSystemMetaData system, int nEntryLimit,
			SearchCallParameters parameters) throws IOException, SearchServiceException {
		if (query == null || !(system.getSystemConfigurationReference() instanceof GGlpiSystem glpiSystem)) {
			return List.of();
		}
		boolean tickets = GlpiRsqlUtil.searchesTickets(query);
		boolean kbArticles = GlpiRsqlUtil.searchesKbArticles(query);
		String ticketsFilter = tickets ? GlpiRsqlUtil.ticketsFilter(query) : null;
		String kbFilter = kbArticles ? GlpiRsqlUtil.kbArticlesFilter(query) : null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI native search on " + glpiSystem.getCode() + " tickets=" + (tickets ? ticketsFilter : "no")
					+ " kb=" + (kbArticles ? kbFilter : "no"));
		}
		return execute(glpiSystem, tickets, ticketsFilter, kbArticles, kbFilter, nEntryLimit, parameters);
	}

	/**
	 * Runs the searches asked, a null filter reading every ticket or article
	 * visible to the GLPI user
	 */
	private List<SearchResult> execute(GGlpiSystem system, boolean tickets, String ticketsFilter,
			boolean kbArticles, String kbFilter, int nEntryLimit, SearchCallParameters parameters)
			throws SearchServiceException {
		List<SearchResult> results = new ArrayList<SearchResult>();
		try {
			GlpiRemoteAccess access = new GlpiRemoteAccess(apiClientFactory.getApiClient(system, parameters));
			String rootUrl = GlpiApiClientFactory.rootUrlOf(system.getBaseUri());
			if (tickets) {
				for (Ticket ticket : access.searchTickets(ticketsFilter, nEntryLimit, SORT_BY_MODIFICATION)) {
					SearchResult result = ticketResult(ticket, system, access, rootUrl);
					if (result != null) {
						results.add(result);
					}
				}
			}
			if (kbArticles) {
				for (KBArticle article : access.searchKbArticles(kbFilter, nEntryLimit, SORT_BY_MODIFICATION)) {
					SearchResult result = kbResult(article, system, rootUrl);
					if (result != null) {
						results.add(result);
					}
				}
			}
		} catch (GeboCryptSecretException e) {
			throw new SearchServiceException("Error in the GLPI credentials", e);
		}
		setOriginOn(results);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI search on " + system.getCode() + " found " + results.size() + " results");
		}
		return results;
	}

	private static SearchResult node(GGlpiSystem system, String id, long entityId, String entityName,
			List<GlpiPathComponent> steps, GlpiNativePositionObject nativeNode, String descriptiveText,
			int nestingLevel) {
		SearchResult result = new SearchResult();
		result.setId(id);
		result.setSystemConfigurationCode(system.getCode());
		result.setNestingLevel(nestingLevel);
		result.setNavigationReference(
				GlpiNavigationUtil.toVirtualFilesystemReference(entityId, entityName, steps, nativeNode.getName()));
		result.setDescriptiveText(descriptiveText != null ? descriptiveText : nativeNode.getName());
		result.setModificationDate(nativeNode.getResourceModificationTime());
		SearchResultReference reference = new SearchResultReference();
		reference.setUri(nativeNode.getUrl());
		reference.setName(nativeNode.getName());
		reference.setTitle(descriptiveText != null ? descriptiveText : nativeNode.getName());
		reference.setContentType(nativeNode.getResourceContentType());
		result.setResultReference(reference);
		return result;
	}

	private static List<GlpiPathComponent> ticketSteps(long ticketId, GlpiPathNodeType type, long itemId) {
		return List.of(new GlpiPathComponent(GlpiPathNodeType.TICKETS_SECTION, null),
				new GlpiPathComponent(GlpiPathNodeType.TICKET, ticketId), new GlpiPathComponent(type, itemId));
	}

	private SearchResult ticketResult(Ticket ticket, GGlpiSystem system, GlpiRemoteAccess access, String rootUrl) {
		if (ticket.getId() == null || ticket.getEntity() == null || ticket.getEntity().getId() == null) {
			return null;
		}
		final long ticketId = ticket.getId();
		final long entityId = ticket.getEntity().getId();
		final String entityName = ticket.getEntity().getCompletename() != null ? ticket.getEntity().getCompletename()
				: ticket.getEntity().getName();
		SearchResult result = node(system, "glpi-ticket-" + ticketId, entityId, entityName,
				ticketSteps(ticketId, GlpiPathNodeType.TICKET_BODY, ticketId),
				GlpiNativeObjects.ticketBody(ticket, rootUrl), "Ticket " + GlpiNativeObjects.ticketTitle(ticket), 0);
		try {
			for (Solution solution : access.solutions(ticketId)) {
				result.getChilds().add(node(system, "glpi-solution-" + solution.getId(), entityId, entityName,
						ticketSteps(ticketId, GlpiPathNodeType.SOLUTION, solution.getId()),
						GlpiNativeObjects.solution(ticketId, solution, rootUrl), null, 1));
			}
			for (Followup followup : access.followups(ticketId)) {
				result.getChilds().add(node(system, "glpi-followup-" + followup.getId(), entityId, entityName,
						ticketSteps(ticketId, GlpiPathNodeType.FOLLOWUP, followup.getId()),
						GlpiNativeObjects.followup(ticketId, followup, rootUrl), null, 1));
			}
			for (TicketTask task : access.tasks(ticketId)) {
				result.getChilds().add(node(system, "glpi-task-" + task.getId(), entityId, entityName,
						ticketSteps(ticketId, GlpiPathNodeType.TASK, task.getId()),
						GlpiNativeObjects.task(ticketId, task, rootUrl), null, 1));
			}
			for (Document document : access.ticketDocuments(ticketId)) {
				result.getChilds().add(node(system, "glpi-document-" + document.getId(), entityId, entityName,
						ticketSteps(ticketId, GlpiPathNodeType.DOCUMENT, document.getId()),
						GlpiNativeObjects.document(ticketId, document, rootUrl), null, 1));
			}
		} catch (RuntimeException e) {
			// the ticket itself is still a result
			LOGGER.error("Cannot read the timeline of GLPI ticket " + ticketId, e);
		}
		return result;
	}

	private SearchResult kbResult(KBArticle article, GGlpiSystem system, String rootUrl) {
		if (article.getId() == null || article.getEntity() == null || article.getEntity().getId() == null) {
			return null;
		}
		return node(system, "glpi-kbarticle-" + article.getId(), article.getEntity().getId(),
				article.getEntity().getName(),
				List.of(new GlpiPathComponent(GlpiPathNodeType.KB_SECTION, null),
						new GlpiPathComponent(GlpiPathNodeType.KB_ARTICLE, article.getId())),
				GlpiNativeObjects.kbArticle(article, rootUrl), GlpiNativeObjects.kbArticleTitle(article), 0);
	}

	@Override
	public GlpiResultsExtractionData aggregate(GlpiResultsExtractionData oldConsolidated,
			GlpiResultsExtractionData consolidated) {
		GlpiResultsExtractionData data = basicAggregate(oldConsolidated, consolidated, new GlpiResultsExtractionData());
		GlpiAdditionalSearchFilter ideas = new GlpiAdditionalSearchFilter();
		join(oldConsolidated, ideas);
		join(consolidated, ideas);
		data.setAdditionalGlpiSearchIdeas(ideas);
		return data;
	}

	private static void join(GlpiResultsExtractionData container, GlpiAdditionalSearchFilter data) {
		if (container == null || container.getAdditionalGlpiSearchIdeas() == null) {
			return;
		}
		GlpiAdditionalSearchFilter joined = container.getAdditionalGlpiSearchIdeas();
		DataStructureJoinUtils.join(joined.getTicketIds(), data::getTicketIds, data::setTicketIds);
		DataStructureJoinUtils.join(joined.getTitleTerms(), data::getTitleTerms, data::setTitleTerms);
		DataStructureJoinUtils.join(joined.getContentTerms(), data::getContentTerms, data::setContentTerms);
		if (joined.getTitleTermsMatchMode() != null) {
			data.setTitleTermsMatchMode(joined.getTitleTermsMatchMode());
		}
		if (joined.getContentTermsMatchMode() != null) {
			data.setContentTermsMatchMode(joined.getContentTermsMatchMode());
		}
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			GlpiResultsExtractionData extractedData) throws IOException, SearchServiceException {
		if (extractedData == null || extractedData.getAdditionalGlpiSearchIdeas() == null) {
			return null;
		}
		GlpiAdditionalSearchFilter ideas = extractedData.getAdditionalGlpiSearchIdeas();
		GlpiSearchFilter filter = new GlpiSearchFilter();
		boolean atLeastOne = DataStructureJoinUtils.doneCopy(ideas.getTicketIds(), filter::setTicketIds);
		atLeastOne |= DataStructureJoinUtils.doneCopy(ideas.getTitleTerms(), filter::setTitleTerms);
		atLeastOne |= DataStructureJoinUtils.doneCopy(ideas.getContentTerms(), filter::setContentTerms);
		filter.setTitleTermsMatchMode(ideas.getTitleTermsMatchMode());
		filter.setContentTermsMatchMode(ideas.getContentTermsMatchMode());
		if (!atLeastOne) {
			return null;
		}
		SearchableSystemMetaData asked = systemId != null ? findSystemById(systemId) : null;
		List<SearchableSystemMetaData> systems = asked != null ? List.of(asked) : getSearchableSystems();
		List<SearchResult> results = new ArrayList<SearchResult>();
		for (SearchableSystemMetaData system : systems) {
			List<SearchResult> found = nativeSearch(filter, system, RELATED_SEARCH_ENTRIES);
			if (found != null) {
				results.addAll(found);
			}
		}
		return new SearchResultAnalisysOutcome(null, results);
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		return Map.of(GLPI_ENTITIES_PROMPT_PARAM, renderEntities(cataloguesSample));
	}

	static String renderEntities(List<CatalogueSample> cataloguesSample) {
		StringBuilder buffer = new StringBuilder();
		if (cataloguesSample != null) {
			for (CatalogueSample sample : cataloguesSample) {
				String code = sample.getCode();
				String id = code != null && code.startsWith(GlpiNavigationUtil.ENTITY_PREFIX)
						? code.substring(GlpiNavigationUtil.ENTITY_PREFIX.length())
						: code;
				buffer.append("- entity id: ").append(id).append(", name: ").append(sample.getDescription())
						.append("\n");
			}
		}
		return buffer.toString();
	}
}
