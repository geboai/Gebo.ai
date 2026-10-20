package ai.gebo.llms.agent.standard.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.agent.standardtools.DeepSearchReduction;
import ai.gebo.llms.agent.standardtools.DeepSearchToolsSupport;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.security.services.ReactiveIdentityUtil;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.GAgentConfig;
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

	/** Metadata key of the analysis document of a deep search: the codes of the documents it rests on. */
	public static final String DEEP_SEARCH_SOURCE_CODES = "GEBO_DEEP_SEARCH_SOURCE_CODES";
	/** Metadata key flagging the analysis document of a deep search. */
	public static final String DEEP_SEARCH_ANALYSIS = "GEBO_DEEP_SEARCH_ANALYSIS";
	/** The deep search capability every standard searcher advertises when the deep search is available. */
	static final String DEEP_SEARCH_CAPABILITY = "Deep search when the command asks it (deepSearch=true, deepSearchDepth "
			+ "FOCUSED/BROAD/EXHAUSTIVE): reads many documents found by its searches, analyses them batch by batch against "
			+ "the command and reduces the analyses into one, handed over with the documents it rests on instead of the fragments";
	protected final IGRankerService rankerService;
	/** The deep search tools' collaborators, resolved on first use (null when the deep search is not deployed). */
	private volatile DeepSearchToolsSupport deepSearchSupport = null;

	public GAbstractStandardDocumentsSearchAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IGRankerService rankerService) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.rankerService = rankerService;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		if (deepSearchSupport() != null) {
			capabilities.addCapability(DEEP_SEARCH_CAPABILITY);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Documents search agent id:" + getId() + " advertises the deep search capability");
			}
		}
		return capabilities;
	}

	/**
	 * The deep search tools' collaborators (the analysis and the models it runs with),
	 * resolved through the runtime binder on first use so the searchers, built while
	 * the agents are assembled, need no injection; null when the deep search is not
	 * deployed, the searchers then only search.
	 */
	protected DeepSearchToolsSupport deepSearchSupport() {
		DeepSearchToolsSupport support = deepSearchSupport;
		if (support == null) {
			try {
				support = runtimeBinder != null ? runtimeBinder.getImplementationOf(DeepSearchToolsSupport.class) : null;
			} catch (RuntimeException e) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search support not available to agent id:" + getId() + ": " + e.getMessage());
				}
				support = null;
			}
			if (support != null) {
				deepSearchSupport = support;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deepSearchSupport() agent id:" + getId() + " deep search "
						+ (support != null ? "available" : "not available"));
			}
		}
		return support;
	}

	/** Whether the command asks for a deep search this agent can run. */
	protected boolean deepSearchRequested(SearchAgentCommand command) {
		return command != null && command.isDeepSearchRequested() && deepSearchSupport() != null;
	}

	/** The depth of the deep search the command asks, BROAD when it gives none. */
	protected Depth deepSearchDepth(SearchAgentCommand command) {
		return DeepSearchReduction.depthOf(command != null ? command.getDeepSearchDepth() : null);
	}

	/**
	 * A deep search: the fragments found are analysed batch by batch against the
	 * command and the analyses reduced into one, handed over as a single document
	 * with the codes of the documents it rests on ({@link #DEEP_SEARCH_SOURCE_CODES}),
	 * whose refs are published for the answer's documents. When the analysis cannot
	 * run the fragments are handed over as they are, and the readers are told.
	 */
	@Override
	protected List<Document> deliverDocuments(List<Document> documents, SearchAgentCommand command,
			IChatRequestContext chatRequestContext, IGConfigurableChatModel agentModel,
			AgentsCollaborationSessionContext session, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink, ReactiveIdentityUtil runAs, List<String> statusNotices)
			throws AgentException {
		if (!deepSearchRequested(command)) {
			return super.deliverDocuments(documents, command, chatRequestContext, agentModel, session,
					contextAgentPersona, notificationSink, runAs, statusNotices);
		}
		final List<Document> fragments = documents != null ? documents : List.of();
		final Depth depth = deepSearchDepth(command);
		final List<String> foundNames = foundDocumentNames(fragments);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin deliverDocuments(...) deep search by agent id:" + getId() + " depth:" + depth + " on "
					+ fragments.size() + " fragment(s) of " + foundNames.size() + " document(s)");
		}
		if (fragments.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End deliverDocuments(...) deep search by agent id:" + getId() + ": nothing found to analyse");
			}
			return fragments;
		}
		if (notificationSink != null) {
			notificationSink.next("Agent: " + getId() + " deep search: analysing " + fragments.size() + " fragment(s) of "
					+ foundNames.size() + " document(s)", INotificationSink.NotificationObject.NotificationType.INFO);
		}
		DeepSearchReduction.Outcome outcome;
		try {
			outcome = DeepSearchReduction.reduce(deepSearchSupport(), getId(), command.getCommand(), depth, fragments,
					chatRequestContext, agentModel, runAs, progressNotifier(notificationSink));
		} catch (RuntimeException e) {
			LOGGER.error("Deep search analysis failed for agent id:" + getId() + ", the fragments found are handed over",
					e);
			outcome = DeepSearchReduction.Outcome.none(fragments.size());
		}
		if (!outcome.hasAnalysis()) {
			statusNotices.add("deep search asked but the analysis of the " + fragments.size()
					+ " fragments found could not be run: the fragments are given as they are");
			if (notificationSink != null) {
				notificationSink.next("Agent: " + getId() + " deep search: the analysis could not be run, the "
						+ fragments.size() + " fragment(s) found are given as they are",
						INotificationSink.NotificationObject.NotificationType.INFO);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End deliverDocuments(...) deep search by agent id:" + getId()
						+ " without analysis: the fragments are handed over");
			}
			return fragments;
		}
		// the documents the analysis rests on are the answer's: their refs are published
		// for the writer, which lists them from the analysis document
		publishDocumentRefs(fragments, outcome.sourceCodes(), session);
		final Map<String, Object> metadata = new LinkedHashMap<>();
		metadata.put(DocumentMetaInfos.TITLE, "Deep search analysis by " + getId() + ": " + outcome.fragmentsRead()
				+ " of " + outcome.fragmentsFound() + " fragments of " + outcome.sourceCodes().size()
				+ " document(s) read");
		metadata.put(DEEP_SEARCH_ANALYSIS, Boolean.TRUE);
		metadata.put(DEEP_SEARCH_SOURCE_CODES, outcome.sourceCodes());
		final Document analysis = new Document(outcome.analysis(), metadata);
		final StringBuilder notice = new StringBuilder("deep search: ").append(outcome.fragmentsRead()).append(" of ")
				.append(outcome.fragmentsFound()).append(" fragments of ").append(foundNames.size())
				.append(" documents found were analysed, the analysis rests on ").append(outcome.sourceCodes().size())
				.append(" document(s)");
		if (outcome.fragmentsRead() < outcome.fragmentsFound()) {
			notice.append("; ").append(outcome.fragmentsFound() - outcome.fragmentsRead())
					.append(" fragment(s) were left unread by the analysis");
		}
		if (outcome.notCovered() != null && !outcome.notCovered().isBlank()) {
			notice.append("; the analysis reports as missing: ").append(outcome.notCovered().trim());
		}
		statusNotices.add(notice.toString());
		if (notificationSink != null) {
			notificationSink.next("Agent: " + getId() + " deep search: analysed " + outcome.fragmentsRead()
					+ " fragment(s), the analysis rests on " + outcome.sourceCodes().size() + " document(s)",
					INotificationSink.NotificationObject.NotificationType.INFO);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End deliverDocuments(...) deep search by agent id:" + getId() + " hands over one analysis of "
					+ outcome.fragmentsRead() + " fragment(s) resting on " + outcome.sourceCodes().size()
					+ " document(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<DEEP_SEARCH_STATUS_NOTICE agent=" + getId() + ">");
			LOGGER.trace(notice.toString());
			LOGGER.trace("</DEEP_SEARCH_STATUS_NOTICE>");
		}
		return List.of(analysis);
	}

	/** The progress of the deep search analysis, told to the user through the notification sink. */
	protected IGProgressNotifier progressNotifier(INotificationSink notificationSink) {
		if (notificationSink == null) {
			return IGProgressNotifier.NONE;
		}
		return new IGProgressNotifier() {
			@Override
			public void notifyProgress(String code, String message) {
				notificationSink.next("Agent: " + getId() + " deep search: " + message,
						INotificationSink.NotificationObject.NotificationType.INFO);
			}

			@Override
			public void notifyLLMProblems() {
				notificationSink.next("Agent: " + getId() + " deep search: the AI provider failed",
						INotificationSink.NotificationObject.NotificationType.INFO);
			}
		};
	}

	/**
	 * Publishes in the session the refs of the given documents (by content code) the
	 * answer may list, keeping the ones already published (the external searchers
	 * publish richer refs, built from their search results): first write per code wins.
	 */
	protected void publishDocumentRefs(List<Document> fragments, List<String> codes,
			AgentsCollaborationSessionContext session) {
		if (fragments == null || codes == null || codes.isEmpty() || session == null) {
			return;
		}
		final Map<String, GResponseDocumentRef> byCode = chatWithDocumentRefs(session);
		int published = 0;
		for (Document fragment : fragments) {
			final Object code = fragment != null && fragment.getMetadata() != null
					? fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)
					: null;
			if (code == null || !codes.contains(code.toString()) || byCode.containsKey(code.toString())) {
				continue;
			}
			byCode.put(code.toString(), new GResponseDocumentRef(fragment));
			published++;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("publishDocumentRefs(...) agent id:" + getId() + " published " + published + " ref(s) of "
					+ codes.size() + " document(s), " + byCode.size() + " ref(s) now in the session");
		}
	}

	/** The refs of the documents the answer may list, by content code, shared by the searchers of the session. */
	@SuppressWarnings("unchecked")
	protected Map<String, GResponseDocumentRef> chatWithDocumentRefs(AgentsCollaborationSessionContext session) {
		final Map<String, Object> environment = session.getEnvironment();
		synchronized (environment) {
			Object existing = environment.get(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE);
			if (existing instanceof Map<?, ?>) {
				return (Map<String, GResponseDocumentRef>) existing;
			}
			Map<String, GResponseDocumentRef> byCode = new ConcurrentHashMap<>();
			environment.put(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE, byCode);
			return byCode;
		}
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
		if (notificationSink != null && documents != null && !documents.isEmpty() && rankingRequested(command)
				&& rankerService.isRankerConfigured()) {
			// Ranking is an LLM call over the whole candidate set, so it is a distinct wait
			// worth naming rather than folding into the single "is searching" beat.
			notificationSink.next("Agent: " + getId() + " is ranking " + documents.size() + " candidate(s)",
					INotificationSink.NotificationObject.NotificationType.INFO);
		}
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
				List<Document> ranked = rankerService.rankAndRemoveIrrelevant(documents, command.getCommand(), command.getTopK());
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
		if (deepSearchRequested(command)) {
			// a deep search reads as many documents as the deep search tools do, at least
			final int deepTopK = Math.max(topK, deepSearchSupport().searchTopK());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrievalTopK(...) agent id:" + getId() + " deep search requestedTopK:" + topK
						+ " retrieving:" + deepTopK);
			}
			return deepTopK;
		}
		final boolean widened = rankingRequested(command) && rankerService.isRankerConfigured();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("retrievalTopK(...) agent id:" + getId() + " requestedTopK:" + topK + " widenedForRanking:"
					+ widened + " retrieving:" + (widened ? topK * 2 : topK));
		}
		return widened ? topK * 2 : topK;
	}

	protected boolean rankingRequested(SearchAgentCommand command) {
		if (command == null || !Boolean.TRUE.equals(command.getExecuteRanking())) {
			return false;
		}
		if (deepSearchRequested(command)) {
			// the analysis reads and judges every fragment found: nothing is dropped before it
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("rankingRequested(...) agent id:" + getId()
						+ " ranking asked but not run: the deep search analysis judges the fragments");
			}
			return false;
		}
		return true;
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
