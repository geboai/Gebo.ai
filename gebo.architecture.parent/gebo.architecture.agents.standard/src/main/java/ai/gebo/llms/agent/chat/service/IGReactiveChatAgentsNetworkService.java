package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.IGReactiveOutputAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;

public interface IGReactiveChatAgentsNetworkService
		extends IGReactiveOutputAgentsNetworkService<String, GeboChatMessageEnvelope> {

}
