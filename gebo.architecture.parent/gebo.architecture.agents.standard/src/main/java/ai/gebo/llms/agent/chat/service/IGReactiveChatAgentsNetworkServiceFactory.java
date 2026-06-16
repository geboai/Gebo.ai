package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

public interface IGReactiveChatAgentsNetworkServiceFactory extends
		IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> {

}
