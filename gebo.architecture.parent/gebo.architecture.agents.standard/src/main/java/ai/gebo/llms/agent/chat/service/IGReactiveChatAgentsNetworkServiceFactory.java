package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;

public interface IGReactiveChatAgentsNetworkServiceFactory extends
		IGAgentsNetworkServiceFactory<String, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> {

}
