package ai.gebo.llms.agent.standard.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentCapabilities;
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
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.security.services.IGSecurityService;

public class NativeDocumentsSearchNetworkAgentService<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType, NativeSearchDataStructure extends INativeQueryObject>
		extends GAbstractStandardDocumentsSearchAgentService {

	public static final String NATIVE_SEARCH_AGENT_FOR = "Native search Agent for ";
	public static final String NATIVE_SEARCHER_AGENT = "NativeSearcherAgent";
	final INativeSearchService<CustomSearchResultExtractionDataType, NativeSearchDataStructure> nativeSearchWrapper;

	public NativeDocumentsSearchNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IDocumentsChunkService chunkingService,
			IGRankerService rankerService,
			INativeSearchService<CustomSearchResultExtractionDataType, NativeSearchDataStructure> nativeSearchWrapper) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory, chunkingService, rankerService);
		this.nativeSearchWrapper = nativeSearchWrapper;
	}

	@Override
	public String getId() {

		return nativeSearchWrapper.getProductId() + NATIVE_SEARCHER_AGENT;
	}

	@Override
	public String getDescription() {

		return NATIVE_SEARCH_AGENT_FOR + nativeSearchWrapper.getProductId();
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability("Generate provider-native queries and search the '"
				+ nativeSearchWrapper.getProductId() + "' system, returning the most relevant retrieved documents");
		appendSearchableSystems(capabilities, nativeSearchWrapper);
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
		final int topK = retrievalTopK(command);
		final List<SearchResult> results = new ArrayList<>();
		try {
			// The native query template (runtime-patched with the agent network placeholders
			// at config time) is passed in as `prompt`, fed with the agent placeholder params
			// merged with the per-system native template params.
			final Class<NativeSearchDataStructure> queryType = nativeSearchWrapper.getNativeSearchDataStructureType();
			for (SearchableSystemMetaData system : nativeSearchWrapper.getSearchableSystems()) {
				List<CatalogueSample> catalogues = nativeSearchWrapper.getCataloguesListSample(system.getCode());
				Map<String, Object> callParams = new HashMap<>(params);
				callParams.putAll(nativeSearchWrapper.createCustomTemplateParamsMap(system,
						catalogues != null ? catalogues : List.of()));
				NativeSearchDataStructure queryObject = callLLMStructuredReturn(agentModel, prompt,
						chatRequestContext, callParams, queryType);
				results.addAll(nativeSearchWrapper.nativeSearch(queryObject, system, topK));
			}
		} catch (LLMConfigException | IOException | SearchServiceException e) {
			throw new AgentException("Error executing native search agent " + getId(), e);
		}
		// The native query object is provider-specific, so derive relevance keywords from
		// the command text.
		final List<String> keywords = keywordsFromCommand(command);
		return maybeRank(chunkToDocuments(results, agentModel, command, keywords), command);
	}

}
