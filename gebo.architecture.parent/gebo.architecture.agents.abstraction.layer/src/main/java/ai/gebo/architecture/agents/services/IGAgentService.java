package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import reactor.core.publisher.Flux;

public interface IGAgentService<RequestType, ResponseType,NotificationObject> {
	public String getId();

	public String getDescription();

	public Flux<IGPartialOperation<ResponseType>> execute(RequestType request, GAgentConfig agentConfig, INotificationSink<NotificationObject> notificationSink) throws AgentException, LLMConfigException;
}
