package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@AllArgsConstructor
public abstract class AbstractReactiveAgentServiceNetworkAdapter<RequestType, ResponseType, NotificationObject>
		implements IGNetworkAgentService<RequestType, ResponseType> {
	private final IGReactiveAgentService<RequestType, ResponseType, NotificationObject> service;
	private final Class<RequestType> inputType;
	private final Class<ResponseType> outputType;

	private final Sinks.Many<IGPartialOperation<ResponseType>> sink;

	@Override
	public String getId() {

		return service.getId();
	}

	@Override
	public String getDescription() {

		return service.getDescription();
	}

	@Override
	public List<GAgentConfig> getAccessibleConfigurations() {

		return service.getAccessibleConfigurations();
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {

		return service.getAgentCapabilities(agentConfig);
	}

	@Override
	public Class<RequestType> getInputType() {

		return inputType;
	}

	@Override
	public Class<ResponseType> getOutputType() {
		return outputType;
	}

	@Override
	public List<AgentsExchangeMessage<ResponseType>> onMessage(IChatRequestContext chatRequestContext,
			GAgentConfig config, AgentsExchangeMessage<RequestType> msg, int actualContributionNr,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona,
			INotificationSink notificationSink,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext<RequestType, ResponseType> mySessionContext, ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao)
			throws LLMConfigException, AgentException {
		Flux<IGPartialOperation<ResponseType>> flux = service.execute(chatRequestContext, config, msg.getPayload(),
				network, contextAgentPersona, notificationSink, session, mySessionContext, runAs);
		Flux<IGPartialOperation<ResponseType>> duplicatedFlux = flux.map(x -> {
			sink.emitNext(x, null);
			return x;
		});
		List<IGPartialOperation<ResponseType>> buffered = duplicatedFlux.collectList().block();
		ResponseType response = extractResponse(buffered);
		AgentsExchangeMessage<ResponseType> outMessage = AgentsExchangeMessage.of(session, msg.getFromAgent(), response,
				MessageSemantic.RESPONSE);
		return List.of(outMessage);
	}

	protected abstract ResponseType extractResponse(List<IGPartialOperation<ResponseType>> buffered);

}
