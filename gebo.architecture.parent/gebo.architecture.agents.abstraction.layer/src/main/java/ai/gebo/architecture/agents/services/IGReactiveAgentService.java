package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import reactor.core.publisher.Flux;

public interface IGReactiveAgentService<RequestType, ResponseType, NotificationObject>
		extends IGAgentService<RequestType, Flux<IGPartialOperation<ResponseType>>, NotificationObject> {
	@Override
	Flux<IGPartialOperation<ResponseType>> execute(RequestType request, GAgentConfig agentConfig,
			INotificationSink<NotificationObject> notificationSink) throws AgentException, LLMConfigException;
}
