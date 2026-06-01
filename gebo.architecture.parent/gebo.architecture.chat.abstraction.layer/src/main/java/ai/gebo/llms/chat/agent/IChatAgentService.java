package ai.gebo.llms.chat.agent;

import ai.gebo.architecture.agents.services.IGAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

public interface IChatAgentService
		extends IGAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> {

}
