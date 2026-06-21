package ai.gebo.llms.agent.chat.service;

import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;

public interface IChatAgentNotificationSink extends INotificationSink<GeboChatMessageEnvelope> {

}
