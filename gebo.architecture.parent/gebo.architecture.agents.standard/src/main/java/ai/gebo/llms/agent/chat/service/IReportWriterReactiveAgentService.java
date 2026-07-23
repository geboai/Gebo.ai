package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;

public interface IReportWriterReactiveAgentService
		extends IGReactiveAgentService<String, GeboChatMessageEnvelope> {

}
