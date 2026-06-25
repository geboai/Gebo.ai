package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.security.services.IGSecurityService;

public class InternalKnowledgeBaseSearchNetworkAgentService extends GAbstractStandardDocumentsSearchAgentService
		implements IGInternalKnowledgeBaseDocumentsSearchNetworkAgentService {

	public static final String AGENT_THAT_SEARCHES_THE_INTERNAL_KNOWLEDGE_BASE = "Agent that searches the internal knowledge base";
	public static final String INTERNAL_KNOWLEDGE_BASE_SEARCHER = "internalKnowledgeBaseSearcher";
	/**
	 * Use-code of the standard RAG search-planner prompt this agent reuses. Exposed
	 * so the agent network configuration can fetch and runtime-patch it without
	 * depending on the chat pipelines' prompt library directly.
	 */
	public static final String SEARCH_PLANNER_PROMPT_USE_CODE = GeboPromptsLibrary.DEFAULT_PIPELINE_RAG_SEARCH_PLANNER_PROMPT;
	private static final String SEMANTIC_QUERIES_FIELD = "semanticQueries";
	private static final String FULL_TEXT_QUERIES_FIELD = "fullTextQueries";

	private final IGDocumentsSearchService documentsSearchService;
	private final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;

	public InternalKnowledgeBaseSearchNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IDocumentsChunkService chunkingService,
			IGRankerService rankerService, IGDocumentsSearchService documentsSearchService,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory, chunkingService, rankerService);
		this.documentsSearchService = documentsSearchService;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
	}

	@Override
	public String getId() {

		return INTERNAL_KNOWLEDGE_BASE_SEARCHER;
	}

	@Override
	public String getDescription() {

		return AGENT_THAT_SEARCHES_THE_INTERNAL_KNOWLEDGE_BASE;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Plan semantic and full-text queries and search the internal knowledge base, returning the most relevant document chunks (optionally re-ranked)");
		try {
			List<GKnowledgeBase> visibles = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
			if (visibles != null) {
				for (GKnowledgeBase kb : visibles) {
					if (kb != null) {
						capabilities.addCatalog(AgentCapabilityResource.of(kb.getCode(), kb.getDescription(), null));
					}
				}
			}
		} catch (Throwable th) {
			LOGGER.warn("Cannot enumerate visible knowledge bases for internal-KB agent capabilities", th);
		}
		return capabilities;
	}

	@Override
	protected List<Document> retrieveDocuments(GPromptTemplateConfig prompt, IChatRequestContext chatRequestContext,
			IGConfigurableChatModel agentModel, Map<String, Object> params, GAgentsNetwork network,
			GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
			AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
			INotificationSink notificationSink) throws AgentException {
		final SearchAgentCommand command = msg.getPayload();
		try {
			// LLM planner: rewrite the command into semantic and full-text search queries.
			// `prompt` is the runtime-patched planner prompt (agent network placeholders
			// injected at config time), so it is used directly instead of re-fetching the
			// raw planner template.
			Map<String, List<String>> fields = callLLMRepeatableFieldEntryOutput(agentModel, prompt,
					chatRequestContext, params, List.of(SEMANTIC_QUERIES_FIELD, FULL_TEXT_QUERIES_FIELD));
			List<String> semanticQueries = fields.getOrDefault(SEMANTIC_QUERIES_FIELD, List.of());
			List<String> fullTextQueries = fields.getOrDefault(FULL_TEXT_QUERIES_FIELD, List.of());

			final List<String> kbCodes = visibleKnowledgeBaseCodes();
			SemanticSearchMetaDataFilter semanticFilter = new SemanticSearchMetaDataFilter();
			semanticFilter.setKnowledgeBasesCodes(kbCodes);
			FullTextSearchMetaDataFilter fullTextFilter = new FullTextSearchMetaDataFilter();
			fullTextFilter.setKnowledgebaseCodes(kbCodes);
			if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
					&& !securityService.isCurrentUserAdmin()) {
				List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
						.getAllOwnedAclAliases();
				semanticFilter.setAclAliases(aclAliases);
				fullTextFilter.setAclAliases(aclAliases);
			}

			final int topK = retrievalTopK(command);
			final int tokensBudget = (int) (agentModel.getContextLength() * 0.75);
			AIDocumentsSet set = documentsSearchService.search(command.getCommand(), semanticQueries, semanticFilter,
					fullTextQueries, fullTextFilter, command.getCommand(), topK, tokensBudget);
			List<Document> documents = set != null ? set.aiDocumentsList() : new ArrayList<>();
			return maybeRank(documents, command);
		} catch (LLMConfigException | FullTextException e) {
			throw new AgentException("Error executing internal knowledge base search agent", e);
		}
	}

	private List<String> visibleKnowledgeBaseCodes() {
		List<GKnowledgeBase> visibles = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		return visibles != null ? visibles.stream().map(GKnowledgeBase::getCode).toList() : List.of();
	}

}
