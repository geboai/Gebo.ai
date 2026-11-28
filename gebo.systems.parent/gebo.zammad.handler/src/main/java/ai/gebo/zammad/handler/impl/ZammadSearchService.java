/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.IGZammadVirtualFilesystemConsumingService;
import ai.gebo.zammad.handler.ZammadBrowsingContext;
import ai.gebo.zammad.handler.ZammadResourceReference;
import ai.gebo.zammad.client.model.Attachment;
import ai.gebo.zammad.client.model.Ticket;
import ai.gebo.zammad.client.model.TicketArticle;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadNavigationCoordinates;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;
import ai.gebo.zammad.search.api.ZammadAdditionalSearchFilter;
import ai.gebo.zammad.search.api.ZammadResultsExtractionData;
import ai.gebo.zammad.search.api.ZammadTicketsSearchFilter;
import ai.gebo.zammad.search.api.TextMatchMode;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemSearchService;
import ai.gebo.systems.abstraction.layer.impl.DataStructureJoinUtils;

/**
 * Live search of the Zammad systems: tickets and knowledge base answers. The
 * standard search sends the query text to the ticket search of Zammad (served
 * by Elasticsearch when Zammad has it, by its database otherwise) and to its
 * knowledge base search; the native search turns a structured filter (groups,
 * numbers, text terms, states, priorities, owner, dates) into a ticket search
 * condition where every criterion holds, and searches its text terms in the
 * knowledge base too. Tickets and answers are interleaved up to the limit, each
 * with its rendered content and attachments as children and the reference the
 * ingestion encoding reads back.
 */
@Service
public class ZammadSearchService extends
		GAbstractRemoteVirtualFilesystemSearchService<ZammadResultsExtractionData, GZammadSystem, GZammadProjectEndpoint, ZammadNativePositionObject, ZammadNavigationCoordinates, ZammadResourceReference, IGZammadVirtualFilesystemConsumingService, ZammadBrowsingContext>
		implements INativeSearchService<ZammadResultsExtractionData, ZammadTicketsSearchFilter> {
	public static final String ZAMMAD = "zammad";
	public static final String ZAMMAD_STANDARD_QUERY_EXTRACTION_PROMPT = "zammad-standard-query-extraction-prompt";
	public static final String ZAMMAD_NATIVE_QUERY_EXTRACTION_PROMPT = "zammad-native-query-extraction-prompt";
	static final String ZAMMAD_GROUPS_PROMPT_PARAM = "zammadGroups";
	/** How many results each follow-up search suggested by an analysis returns. */
	static final int RELATED_SEARCH_RESULTS = 50;
	private final ZammadApiClientFactory clientFactory;

	public ZammadSearchService(ZammadApiClientFactory clientFactory,
			GAbstractRemoteVirtualFilesystemConsumingService<GZammadSystem, GZammadProjectEndpoint, ZammadNativePositionObject, ZammadNavigationCoordinates, ZammadResourceReference> consumingService,
			GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GZammadSystem, GZammadProjectEndpoint, ZammadResourceReference, IGZammadVirtualFilesystemConsumingService> handler,
			ZammadBrowsingService browsingService) {
		super(consumingService, handler, browsingService);
		this.clientFactory = clientFactory;
	}

	@Override
	public String getId() {
		return ZAMMAD;
	}

	@Override
	public String getProductId() {
		return ZAMMAD;
	}

	@Override
	public String getDescription() {
		return "Zammad Search";
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ZAMMAD_MODULE;
	}

	@Override
	protected ZammadBrowsingContext createBrowsingContext(GZammadSystem system) {
		return ZammadBrowsingContext.of(system.getCode());
	}

	private ZammadRemoteTree tree(GZammadSystem system, SearchCallParameters parameters)
			throws SearchServiceException {
		try {
			return new ZammadRemoteTree(clientFactory.getConnection(system, parameters));
		} catch (GeboCryptSecretException | IllegalArgumentException e) {
			throw new SearchServiceException("Cannot open the Zammad connection of " + system.getCode(), e);
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
		if (!(system.getSystemConfigurationReference() instanceof GZammadSystem zammadSystem)
				|| query.getQueryText() == null || query.getQueryText().isBlank()) {
			return List.of();
		}
		String text = query.getQueryText().trim();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad search \"{}\" on {}", text, zammadSystem.getCode());
		}
		ZammadRemoteTree tree = tree(zammadSystem, parameters);
		try {
			tree.requireIdentity();
			List<Ticket> tickets = tree.getConnection().searchTickets(null, text, "updated_at", "desc", 1,
					nEntryLimit);
			List<Long> answers = answersOrNone(tree, List.of(text), nEntryLimit);
			return results(tree, zammadSystem, tickets, answers, nEntryLimit);
		} catch (RestClientException e) {
			throw new SearchServiceException("Zammad search failed on " + zammadSystem.getCode() + ": "
					+ ZammadErrors.of(e, zammadSystem.getBaseUri()), e);
		}
	}

	@Override
	public List<SearchResult> nativeSearch(ZammadTicketsSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		return nativeSearch(query, system, nEntryLimit, null);
	}

	@Override
	public List<SearchResult> nativeSearch(ZammadTicketsSearchFilter query, SearchableSystemMetaData system,
			int nEntryLimit, SearchCallParameters parameters) throws IOException, SearchServiceException {
		requireSystem(system);
		if (query == null || !(system.getSystemConfigurationReference() instanceof GZammadSystem zammadSystem)) {
			return List.of();
		}
		if (!ZammadTicketsQueryTranslator.hasFilter(query)) {
			LOGGER.debug("Empty Zammad filter, nothing searched");
			return List.of();
		}
		ZammadRemoteTree tree = tree(zammadSystem, parameters);
		try {
			tree.requireIdentity();
			ZammadTicketsQueryTranslator translator = new ZammadTicketsQueryTranslator(tree::namedGroups,
					tree::namedStates, tree::namedPriorities);
			Map<String, Object> condition;
			try {
				condition = translator.translate(query);
			} catch (IllegalArgumentException e) {
				throw new SearchServiceException("Wrong Zammad search filter: " + e.getMessage(), e);
			}
			List<Ticket> tickets = condition != null
					? tree.getConnection().searchTickets(condition, null, "updated_at", "desc", 1, nEntryLimit)
					: List.of();
			List<String> terms = ZammadTicketsQueryTranslator.textTerms(query);
			List<Long> answers = Boolean.FALSE.equals(query.getSearchKnowledgeBase()) || terms.isEmpty() ? List.of()
					: answersOrNone(tree, query.getTextTermsMatchMode() == TextMatchMode.ALL
							? List.of(String.join(" ", terms))
							: terms, nEntryLimit);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad native search on {}: {} tickets, {} answers", zammadSystem.getCode(),
						tickets.size(), answers.size());
			}
			return results(tree, zammadSystem, tickets, answers, nEntryLimit);
		} catch (RestClientException e) {
			throw new SearchServiceException("Zammad search failed on " + zammadSystem.getCode() + ": "
					+ ZammadErrors.of(e, zammadSystem.getBaseUri()), e);
		}
	}

	/**
	 * The answers matching any of the texts; none when the account cannot read
	 * the knowledge base, which leaves the tickets found.
	 */
	private List<Long> answersOrNone(ZammadRemoteTree tree, List<String> texts, int limit) {
		java.util.Set<Long> ids = new java.util.LinkedHashSet<Long>();
		try {
			for (String text : texts) {
				ids.addAll(tree.searchAnswers(text, limit));
			}
		} catch (RestClientException e) {
			if (!ZammadRemoteTree.isNotAvailable(e)) {
				throw e;
			}
			LOGGER.debug("The Zammad knowledge base is not searchable by the account: {}", e.getMessage());
		}
		return new ArrayList<Long>(ids);
	}

	/** Tickets and answers interleaved, the best of each first, up to the limit. */
	private List<SearchResult> results(ZammadRemoteTree tree, GZammadSystem system, List<Ticket> tickets,
			List<Long> answers, int nEntryLimit) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		int t = 0;
		int a = 0;
		while (results.size() < nEntryLimit && (t < tickets.size() || a < answers.size())) {
			if (t < tickets.size()) {
				Ticket ticket = tickets.get(t++);
				try {
					results.add(ticketResult(tree, system, ticket));
				} catch (RestClientException e) {
					LOGGER.warn("Cannot read the Zammad ticket " + ticket.getId(), e);
				}
			}
			if (a < answers.size() && results.size() < nEntryLimit) {
				Long answerId = answers.get(a++);
				try {
					results.add(answerResult(tree, system, answerId));
				} catch (RestClientException | IllegalArgumentException e) {
					LOGGER.warn("Cannot read the Zammad answer " + answerId, e);
				}
			}
		}
		return results;
	}

	private SearchResult result(GZammadSystem system, String id, String text, String uri, String name,
			String contentType, ZammadPathNodeType rootType, String rootId, String rootName,
			List<ZammadPathComponent> steps, int nesting) {
		SearchResult result = new SearchResult();
		result.setId(id);
		result.setSystemConfigurationCode(system.getCode());
		result.setDescriptiveText(text);
		result.setNestingLevel(nesting);
		result.setNavigationReference(ZammadNavigationUtil.toReference(rootType, rootId, rootName, steps, name));
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(uri);
		result.getResultReference().setName(name);
		result.getResultReference().setTitle(name);
		result.getResultReference().setContentType(contentType);
		result.setChilds(new ArrayList<SearchResult>());
		setOriginOn(result);
		return result;
	}

	private static String extra(Ticket ticket, String key) {
		Object value = ticket.getAdditionalProperties() != null ? ticket.getAdditionalProperty(key) : null;
		return value != null ? value.toString() : null;
	}

	/**
	 * A ticket as a result under its group, its rendered conversation and its
	 * attachments as children.
	 */
	private SearchResult ticketResult(ZammadRemoteTree tree, GZammadSystem system, Ticket ticket) {
		String id = String.valueOf(ticket.getId());
		String groupId = String.valueOf(ticket.getGroupId());
		String groupName = extra(ticket, "group");
		String base = tree.getBaseUri();
		String uri = ZammadNodes.ticketUrl(base, id);
		String label = ZammadNodes.ticketLabel(ticket);
		// the internal notes are not shown: the ticket's content leaves them out, and a
		// live search has no ACL of its own to keep them from the customer's side
		List<TicketArticle> articles = tree.articles(ticket.getId()).stream()
				.filter(article -> !Boolean.TRUE.equals(article.getInternal())).toList();
		String first = articles.isEmpty() ? null : articles.get(0).getBody();
		String state = extra(ticket, "state");
		String text = label + (state != null ? " [" + state + "]" : "")
				+ (first != null ? "\n" + org.jsoup.Jsoup.parse(first).text() : "");
		List<ZammadPathComponent> folder = List.of(new ZammadPathComponent(ZammadPathNodeType.TICKET, id));
		SearchResult result = result(system, "ticket-" + id, text, uri, label, ZammadContentRenderer.HTML,
				ZammadPathNodeType.GROUP, groupId, groupName, folder, 0);
		List<ZammadPathComponent> content = new ArrayList<ZammadPathComponent>(folder);
		content.add(new ZammadPathComponent(ZammadPathNodeType.TICKET_CONTENT, id));
		result.getChilds().add(result(system, "ticket-content-" + id, text, uri, label + ".html",
				ZammadContentRenderer.HTML, ZammadPathNodeType.GROUP, groupId, groupName, content, 1));
		for (TicketArticle article : articles) {
			if (article.getAttachments() == null) {
				continue;
			}
			for (Attachment attachment : article.getAttachments()) {
				String attachmentId = article.getId() + ZammadNavigationUtil.ARTICLE_ATTACHMENT_SEPARATOR
						+ attachment.getId();
				List<ZammadPathComponent> steps = new ArrayList<ZammadPathComponent>(folder);
				steps.add(new ZammadPathComponent(ZammadPathNodeType.ATTACHMENT, attachmentId));
				SearchResult child = result(system, "attachment-" + attachmentId, attachment.getFilename(), uri,
						attachment.getFilename(), ZammadNodes.contentTypeOf(attachment.getPreferences()),
						ZammadPathNodeType.GROUP, groupId, groupName, steps, 1);
				child.getResultReference().setSize(ZammadNodes.parseSize(attachment.getSize()));
				result.getChilds().add(child);
			}
		}
		return result;
	}

	/**
	 * An answer as a result under its knowledge base, its rendered body and its
	 * attachments as children.
	 */
	private SearchResult answerResult(ZammadRemoteTree tree, GZammadSystem system, Long answerId) {
		ZammadKnowledgeBaseIndex index = tree.knowledgeBases();
		Long categoryId = index.answerOf(answerId).getCategoryId();
		Long kbId = index.category(categoryId) != null ? index.category(categoryId).getKnowledgeBaseId() : null;
		if (kbId == null) {
			throw new IllegalArgumentException("No knowledge base for the Zammad answer " + answerId);
		}
		String base = tree.getBaseUri();
		String locale = tree.localeCode(kbId);
		String uri = ZammadNodes.knowledgeBaseItemUrl(base, kbId, locale, "answer", answerId);
		String title = index.answerTitle(answerId);
		String label = ZammadNavigationUtil.sanitizeName(title != null ? title : "Answer " + answerId);
		String kbTitle = index.knowledgeBaseTitle(kbId);
		String text = label + " [" + index.categoryPath(categoryId) + "]";
		String id = String.valueOf(answerId);
		List<ZammadPathComponent> folder = List.of(new ZammadPathComponent(ZammadPathNodeType.ANSWER, id));
		SearchResult result = result(system, "answer-" + id, text, uri, label, ZammadContentRenderer.HTML,
				ZammadPathNodeType.KNOWLEDGE_BASE, String.valueOf(kbId), kbTitle, folder, 0);
		List<ZammadPathComponent> content = new ArrayList<ZammadPathComponent>(folder);
		content.add(new ZammadPathComponent(ZammadPathNodeType.ANSWER_CONTENT, id));
		result.getChilds().add(result(system, "answer-content-" + id, text, uri, label + ".html",
				ZammadContentRenderer.HTML, ZammadPathNodeType.KNOWLEDGE_BASE, String.valueOf(kbId), kbTitle, content,
				1));
		for (Map<String, Object> attachment : index.answerAttachments(answerId)) {
			String attachmentId = String.valueOf(attachment.get("id"));
			String fileName = attachment.get("filename") != null ? attachment.get("filename").toString() : attachmentId;
			List<ZammadPathComponent> steps = new ArrayList<ZammadPathComponent>(folder);
			steps.add(new ZammadPathComponent(ZammadPathNodeType.ATTACHMENT, attachmentId));
			Object preferences = attachment.get("preferences");
			SearchResult child = result(system, "attachment-" + attachmentId, fileName, uri, fileName,
					preferences instanceof Map<?, ?> map ? ZammadNodes.contentTypeOf(map) : null,
					ZammadPathNodeType.KNOWLEDGE_BASE, String.valueOf(kbId), kbTitle, steps, 1);
			child.getResultReference().setSize(
					ZammadNodes.parseSize(attachment.get("size") != null ? attachment.get("size").toString() : null));
			result.getChilds().add(child);
		}
		return result;
	}

	private static void requireSystem(SearchableSystemMetaData system) throws SearchServiceException {
		if (system == null) {
			throw new SearchServiceException("Unknown Zammad searchable system");
		}
	}

	@Override
	public Class<ZammadTicketsSearchFilter> getNativeSearchDataStructureType() {
		return ZammadTicketsSearchFilter.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return ZAMMAD_NATIVE_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return ZAMMAD_STANDARD_QUERY_EXTRACTION_PROMPT;
	}

	/**
	 * The groups of the catalog, by code and name, for the native prompt; the
	 * knowledge bases of the catalog are not named in a filter.
	 */
	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		StringBuilder catalog = new StringBuilder();
		if (cataloguesSample != null) {
			for (CatalogueSample sample : cataloguesSample) {
				if (sample.getCode() != null && sample.getCode().startsWith(ZammadNavigationUtil.GROUP_PREFIX)) {
					String name = sample.getDescription();
					if (name != null && name.regionMatches(true, 0, ZammadTicketsQueryTranslator.GROUP_LABEL_PREFIX, 0,
							ZammadTicketsQueryTranslator.GROUP_LABEL_PREFIX.length())) {
						name = name.substring(ZammadTicketsQueryTranslator.GROUP_LABEL_PREFIX.length());
					}
					catalog.append("- code: ").append(sample.getCode()).append(", name: ").append(name).append('\n');
				}
			}
		}
		return Map.of(ZAMMAD_GROUPS_PROMPT_PARAM, catalog.toString());
	}

	@Override
	public Class<ZammadResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {
		return ZammadResultsExtractionData.class;
	}

	@Override
	public ZammadResultsExtractionData aggregate(ZammadResultsExtractionData oldConsolidated,
			ZammadResultsExtractionData consolidated) {
		ZammadResultsExtractionData data = basicAggregate(oldConsolidated, consolidated,
				new ZammadResultsExtractionData());
		ZammadAdditionalSearchFilter joined = new ZammadAdditionalSearchFilter();
		for (ZammadResultsExtractionData item : new ZammadResultsExtractionData[] { oldConsolidated,
				consolidated }) {
			if (item != null && item.getAdditionalZammadSearchIdeas() != null) {
				ZammadAdditionalSearchFilter ideas = item.getAdditionalZammadSearchIdeas();
				DataStructureJoinUtils.join(ideas.getTicketNumbers(), joined::getTicketNumbers,
						joined::setTicketNumbers);
				DataStructureJoinUtils.join(ideas.getTextTerms(), joined::getTextTerms, joined::setTextTerms);
				if (ideas.getTextTermsMatchMode() != null) {
					joined.setTextTermsMatchMode(ideas.getTextTermsMatchMode());
				}
			}
		}
		data.setAdditionalZammadSearchIdeas(joined);
		return data;
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			ZammadResultsExtractionData extractedData) throws IOException, SearchServiceException {
		if (extractedData == null || extractedData.getAdditionalZammadSearchIdeas() == null) {
			return null;
		}
		ZammadAdditionalSearchFilter ideas = extractedData.getAdditionalZammadSearchIdeas();
		ZammadTicketsSearchFilter filter = new ZammadTicketsSearchFilter();
		boolean any = DataStructureJoinUtils.doneCopy(ideas.getTicketNumbers(), filter::setTicketNumbers);
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
