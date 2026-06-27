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
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
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
		if (documents == null || documents.isEmpty()) {
			return documents;
		}
		if (rankingRequested(command) && rankerService.isRankerConfigured()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Ranking " + documents.size() + " document(s) for agent id:" + getId() + " topK:"
						+ command.getTopK());
			}
			try {
				List<Document> ranked = rankerService.call(documents, command.getCommand(), command.getTopK());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Ranking produced " + (ranked != null ? ranked.size() : 0)
							+ " document(s) for agent id:" + getId());
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
		return (rankingRequested(command) && rankerService.isRankerConfigured()) ? topK * 2 : topK;
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
			return;
		}
		try {
			List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
			if (systems != null) {
				for (SearchableSystemMetaData system : systems) {
					if (system == null) {
						continue;
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
			if (catalogues != null) {
				for (CatalogueSample catalogue : catalogues) {
					if (catalogue != null) {
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
