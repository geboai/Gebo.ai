package ai.gebo.llms.agent.chat.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.NetworkOfAgentsException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
public class ReactiveChatAgentsNetworkStreamingOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private static final String SERVICE_ID = "ReactiveChatAgentsNetworkStreamingOutputChatPipelineService";
	private static final String EXCEPTION_CREATING_NETWORK_OF_AGENTS = "Exception creating network of agents";
	private static final String EXCEPTION_RUNNING_NETWORK_OF_AGENTS = "Exception running network of agents";
	private final IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory;
	private final IDynamicAgentsNetworkDataSource agentsNetworkDataSource;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.class);

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return SERVICE_ID;
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...) reactive chat agents network streaming pipeline step:" + getStepId());
		}
		try {
			ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			INotificationSink notificationSink = sinkUIEmitter;
			
			List<GAgentsNetwork> ds = this.agentsNetworkDataSource.getConfigurations();
			if (ds.isEmpty())
				throw new ChatPipelineException("No agentic chat network set");
			GAgentsNetwork network = ds.get(0);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating runtime network of agents from config code:" + network.getCode());
			}
			IGReactiveChatAgentsNetworkService runtimeNetwork = factory.create(network, notificationSink,
					ChatPipelineExecutionRuntimeData.class, GeboChatMessageEnvelope.class, runAs);
			Flux<GeboChatMessageEnvelope> flux = runtimeNetwork.getFlux();
			flux = flux.doOnSubscribe((subscription) -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Subscription received, launching executeNetwork(...) for network code:"
							+ network.getCode());
				}
				runAs.doAs(() -> {
					try {
						runtimeNetwork.executeNetwork(runtimeData.getRequestResources().createChatRequestContext(),
								runtimeData);
					} catch (AgentException | LLMConfigException e) {
						LOGGER.error(EXCEPTION_RUNNING_NETWORK_OF_AGENTS, e);
					}
				});
			}).doOnCancel(() -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Reactive chat agents network flux cancelled, disposing runtime network");
				}
				runtimeNetwork.dispose();
			}).doFinally(signalType -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Reactive chat agents network flux terminated with signal:" + signalType
							+ ", disposing runtime network");
				}
				runtimeNetwork.dispose();
			});
			;
			return flux.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
		}catch(

	NetworkOfAgentsException e)
	{
		LOGGER.error(EXCEPTION_CREATING_NETWORK_OF_AGENTS, e);
		throw new ChatPipelineException(EXCEPTION_CREATING_NETWORK_OF_AGENTS, e);
	}

}

}
