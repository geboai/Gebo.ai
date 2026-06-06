package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IGAgentService<RequestType, ResponseType, NotificationObject> extends IGGenericAgentService {
	

	public ResponseType execute(RequestType request, GAgentConfig agentConfig,
			INotificationSink<NotificationObject> notificationSink) throws AgentException, LLMConfigException;

	

	
}
