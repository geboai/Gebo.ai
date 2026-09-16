package ai.gebo.llms.agent.standard.services;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GAbstractDocumentsSearchNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import java.util.ArrayList;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;

/**
 * Base class for the standard document-search agents. It centralizes the
 * concerns shared by <em>every</em> standard searcher, whatever its backing
 * store: optionally re-ranking the result documents with the
 * {@link IGRankerService}, deriving the retrieval {@code topK}, and exporting the
 * searchable-systems capability descriptors.
 * <p>
 * Turning raw {@link ai.gebo.architecture.search.model.SearchResult}s into Spring
 * AI {@link Document}s (chunking, chunking policies/settings, per-document chunk
 * caps) is an external-source concern and therefore lives in
 * {@link GAbstractExternalDocumentsSearchAgentService}, not here: searchers whose
 * backing service already returns ready document chunks (such as the internal
 * knowledge base searcher) must not inherit those settings.
 */
public abstract class GAbstractStandardDocumentsSearchAgentService extends GAbstractDocumentsSearchNetworkAgentService {

	protected final IGRankerService rankerService;

	public GAbstractStandardDocumentsSearchAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IGRankerService rankerService) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.rankerService = rankerService;
	}

	/**
	 * Ranks the documents with the ranker service when the command requests it and a
	 * ranker is configured; otherwise returns them unchanged.
	 */
	protected List<Document> maybeRank(List<Document> documents, SearchAgentCommand command) throws AgentException {
		return maybeRank(documents, command, null);
	}

	/**
	 * Ranks the retrieved documents when asked to, and tells the user which documents
	 * the search ended up on.
	 * <p>
	 * The names are announced after ranking rather than after retrieval on purpose:
	 * retrieval deliberately over fetches (see {@link #retrievalTopK}) and the ranker
	 * then discards most of it, so the retrieved set would name documents that never
	 * reach the answer. What is announced here is what the writer will actually work
	 * from.
	 */
	protected List<Document> maybeRank(List<Document> documents, SearchAgentCommand command,
			INotificationSink notificationSink) throws AgentException {
		final List<Document> outcome = rankDocuments(documents, command);
		notifyFoundDocuments(outcome, notificationSink);
		return outcome;
	}

	/**
	 * The distinct source documents behind a set of retrieved fragments, in the order
	 * the fragments came back, so the best ranked document is named first.
	 */
	protected List<String> foundDocumentNames(List<Document> documents) {
		final List<String> names = new ArrayList<String>();
		if (documents == null) {
			return names;
		}
		for (Document document : documents) {
			final Object fileName = document.getMetadata().get(DocumentMetaInfos.GEBO_FILE_NAME);
			final Object contentCode = document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
			final Object chosen = fileName != null ? fileName : contentCode;
			if (chosen == null) {
				continue;
			}
			final String name = String.valueOf(chosen);
			if (!name.isBlank() && !names.contains(name)) {
				names.add(name);
			}
		}
		return names;
	}

	/**
	 * Names the documents the search settled on, in the conversation.
	 * <p>
	 * A search agent runs for tens of seconds and, until now, said nothing at all: the
	 * notification sink was handed to it and never used. Naming the documents is the
	 * one piece of progress that is both cheap to produce and meaningful to read - it
	 * is the evidence the answer will be built on, and it lets the reader see a wrong
	 * source being picked up long before the report is written.
	 */
	protected void notifyFoundDocuments(List<Document> documents, INotificationSink notificationSink) {
		if (notificationSink == null) {
			return;
		}
		final List<String> names = foundDocumentNames(documents);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("notifyFoundDocuments(...) agent id:" + getId() + " fragments:"
					+ (documents != null ? documents.size() : 0) + " distinct document(s):" + names.size());
		}
		if (names.isEmpty()) {
			// Worth saying out loud: an empty result is exactly the case where the user is
			// otherwise left guessing why the answer is thin.
			notificationSink.next("Agent: " + getId() + " found no matching document",
					INotificationSink.NotificationObject.NotificationType.INFO);
			return;
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<FOUND_DOCUMENTS agent=" + getId() + ">");
			LOGGER.trace(String.valueOf(names));
			LOGGER.trace("</FOUND_DOCUMENTS>");
		}
		notificationSink.next("Agent: " + getId() + " found " + names.size() + " document(s): "
				+ String.join(", ", names), INotificationSink.NotificationObject.NotificationType.INFO);
	}

	private List<Document> rankDocuments(List<Document> documents, SearchAgentCommand command) throws AgentException {
		if (documents == null || documents.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Ranking skipped for agent id:" + getId() + " : nothing was retrieved");
			}
			return documents;
		}
		if (rankingRequested(command) && rankerService.isRankerConfigured()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Ranking " + documents.size() + " document(s) for agent id:" + getId() + " topK:"
						+ command.getTopK());
			}
			try {
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("<RANKING_QUERY agent=" + getId() + ">");
					LOGGER.trace(command.getCommand());
					LOGGER.trace("</RANKING_QUERY>");
				}
				List<Document> ranked = rankerService.call(documents, command.getCommand(), command.getTopK());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Ranking produced " + (ranked != null ? ranked.size() : 0)
							+ " document(s) for agent id:" + getId());
				}
				if (LOGGER.isTraceEnabled() && ranked != null) {
					int position = 1;
					for (Document document : ranked) {
						LOGGER.trace("<RANKED_DOCUMENT position=" + position + " id=" + document.getId() + ">");
						LOGGER.trace(document.getText());
						LOGGER.trace("</RANKED_DOCUMENT>");
						position++;
					}
				}
				return ranked;
			} catch (LLMConfigException e) {
				throw new AgentException("Error ranking search documents", e);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Ranking skipped for agent id:" + getId() + " (rankingRequested:" + rankingRequested(command)
					+ ")");
		}
		return documents;
	}

	/**
	 * Number of documents to retrieve before ranking. When ranking is going to run,
	 * a wider candidate set is fetched (topK*2) so the ranker has material to reorder.
	 */
	protected int retrievalTopK(SearchAgentCommand command) {
		int topK = command != null ? command.getTopK() : 20;
		final boolean widened = rankingRequested(command) && rankerService.isRankerConfigured();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("retrievalTopK(...) agent id:" + getId() + " requestedTopK:" + topK + " widenedForRanking:"
					+ widened + " retrieving:" + (widened ? topK * 2 : topK));
		}
		return widened ? topK * 2 : topK;
	}

	protected boolean rankingRequested(SearchAgentCommand command) {
		return command != null && Boolean.TRUE.equals(command.getExecuteRanking());
	}

	/**
	 * Exports, on the capabilities descriptor, the systems this search agent can
	 * reach (as accessible resources) together with the catalogs/sections each of
	 * those systems exposes. The catalogues are read through the search service's own
	 * {@link ISearchService#getCachedCatalogues(String) cached} accessor, so the
	 * content/virtual-filesystem services serve a persisted snapshot (no live/remote
	 * sampling while building the network description) and the others answer live.
	 * Failures are swallowed (the descriptor is best-effort).
	 */
	protected void appendSearchableSystems(AgentCapabilities capabilities, ISearchService<?> searchService) {
		if (capabilities == null || searchService == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("appendSearchableSystems(...) skipped for agent id:" + getId() + " capabilities:"
						+ (capabilities != null) + " searchService:" + (searchService != null));
			}
			return;
		}
		try {
			List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("appendSearchableSystems(...) agent id:" + getId() + " advertises "
						+ (systems != null ? systems.size() : 0) + " searchable system(s)");
			}
			if (systems != null) {
				for (SearchableSystemMetaData system : systems) {
					if (system == null) {
						continue;
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("Searchable system: " + system.getCode() + " - " + system.getDescription());
					}
					capabilities.addResource(
							AgentCapabilityResource.of(system.getCode(), system.getDescription(), null));
					appendSampledCatalogues(capabilities, searchService, system);
				}
			}
		} catch (Throwable th) {
			LOGGER.warn("Cannot enumerate searchable systems for agent capabilities of {}", getId(), th);
		}
	}

	private void appendSampledCatalogues(AgentCapabilities capabilities, ISearchService<?> searchService,
			SearchableSystemMetaData system) {
		try {
			List<CatalogueSample> catalogues = searchService.getCachedCatalogues(system.getCode());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("System " + system.getCode() + " exposes " + (catalogues != null ? catalogues.size() : 0)
						+ " cached catalogue(s) for agent id:" + getId());
			}
			if (catalogues != null) {
				for (CatalogueSample catalogue : catalogues) {
					if (catalogue != null) {
						if (LOGGER.isTraceEnabled()) {
							LOGGER.trace("Catalogue: " + catalogue.getCode() + " - " + catalogue.getDescription());
						}
						capabilities.addCatalog(AgentCapabilityResource.of(catalogue.getCode(), catalogue.getCode(),
								catalogue.getDescription()));
					}
				}
			}
		} catch (Throwable th) {
			LOGGER.warn("Cannot read cached catalogues for system {} of agent {}", system.getCode(), getId(), th);
		}
	}
}
