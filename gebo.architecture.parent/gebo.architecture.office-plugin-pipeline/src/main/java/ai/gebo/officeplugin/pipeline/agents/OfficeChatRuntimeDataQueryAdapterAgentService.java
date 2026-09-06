/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.agents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.agent.standard.services.ChatRuntimeDataQueryAdapterAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AdditionalContent;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Office variant of the network input adapter. It reuses the standard adapter's
 * behaviour (extract the user query and forward it to the controller) and, in
 * addition, seeds the network shared context with the document fragments the user
 * is editing: it emits one {@link MessageSemantic#RESPONSE} message per fragment,
 * which the network runtime turns into a shared-context contribution visible to
 * every agent (searchers, coordinator and writer). This is why the office network
 * is aware both of the user's question and of the piece of document being worked
 * on.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Service
public class OfficeChatRuntimeDataQueryAdapterAgentService extends ChatRuntimeDataQueryAdapterAgentService {

	private static final String DESCRIPTION = "Office input adapter: forwards the user query to the controller and seeds the shared context with the document fragments the user is editing";

	public OfficeChatRuntimeDataQueryAdapterAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
	}

	@Override
	public String getId() {
		return OfficeAssistantConstants.OFFICE_INPUT_ADAPTER_AGENT;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Seed the shared context with the office document fragments the user is currently editing");
		return capabilities;
	}

	@Override
	public List<AgentsExchangeMessage<String>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<ChatPipelineExecutionRuntimeData> msg, int actualContributionNr,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<ChatPipelineExecutionRuntimeData, String> mySessionContext,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		// Reuse the standard forwarding of the user query to the controller.
		List<AgentsExchangeMessage<String>> out = new ArrayList<>(super.onMessage(chatRequestContext, config, msg,
				actualContributionNr, network, contextAgentPersona, notificationSink, session, mySessionContext, runAs,
				agentsDao));
		// Then seed the shared context with the document fragments as RESPONSE
		// contributions (they are recorded as shared context and not routed onward).
		List<AdditionalContent> fragments = OfficeFragments.fromEnvironment(session.getEnvironment());
		final String self = contextAgentPersona.getNetworkAgentName();
		final String target = !out.isEmpty() ? out.get(0).getToAgent() : self;
		for (AdditionalContent fragment : fragments) {
			out.add(new AgentsExchangeMessage<String>(session.getId(), MessageSemantic.RESPONSE, self, null, target,
					renderFragment(fragment), 1));
		}
		return out;
	}

	private static String renderFragment(AdditionalContent fragment) {
		StringBuilder sb = new StringBuilder();
		sb.append("USER IS EDITING THIS DOCUMENT FRAGMENT").append("\r\n");
		sb.append("fragment name: ").append(fragment.getName()).append("\r\n");
		sb.append("contentType: ").append(fragment.getContentType()).append("\r\n");
		sb.append("---- fragment content begin ----").append("\r\n");
		sb.append(fragment.getContent()).append("\r\n");
		sb.append("---- fragment content end ----");
		return sb.toString();
	}
}
