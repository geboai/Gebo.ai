package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

public interface IChatAgentService
		extends IGReactiveAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> {

}
