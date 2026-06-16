package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.IGReactiveOutputAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

public interface IGReactiveChatAgentsNetworkService
		extends IGReactiveOutputAgentsNetworkService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope> {

}
