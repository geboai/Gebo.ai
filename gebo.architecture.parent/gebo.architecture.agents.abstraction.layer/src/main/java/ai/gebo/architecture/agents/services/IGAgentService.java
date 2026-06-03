package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import reactor.core.publisher.Flux;

public interface IGAgentService<RequestType, ResponseType, NotificationObject> {
	public String getId();

	public String getDescription();

	public String getDefaultLoopPromptUseCode();

	public String getDefaultCompleteEvaluationPromptUseCode();

	public Flux<IGPartialOperation<ResponseType>> execute(RequestType request, GAgentConfig agentConfig,
			INotificationSink<NotificationObject> notificationSink) throws AgentException, LLMConfigException;

	public List<GAgentConfig> getAccessibleConfigurations();

	public default Optional<GAgentConfig> getDefaultConfiguration() {
		List<GAgentConfig> configs = getAccessibleConfigurations();
		return configs.stream().filter(x -> x.getDefaultConfiguration() != null && x.getDefaultConfiguration())
				.findFirst();
	}

}
