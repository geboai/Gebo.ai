package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.agent.IChatAgentService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class DefaultPipelineStreamingAgenticStepServiceImpl implements IStreamingOutputChatPipelineService {
	public static final String AGENTIC_CHAT_STEP_SERVICE = "AgenticChatStepService";
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPipelineStreamingAgenticStepServiceImpl.class);
	private final IChatAgentService chatAgentService;
	private final IGChatSessionLifeCycleService sessionLifecycleService;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return AGENTIC_CHAT_STEP_SERVICE;
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

		//try {
			final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
			final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			Optional<GAgentConfig> configuration = chatAgentService.getDefaultConfiguration();
			Flux<IGPartialOperation<GeboChatMessageEnvelope>> outFlux =null;
			/*chatAgentService.execute(chatRequestContext,
					configuration.get(), runtimeData, network, contextAgentPersona, new INotificationSink<GeboChatMessageEnvelope>() {
						@Override
						public void next(GeboChatMessageEnvelope state) {
							sinkUIEmitter.next(state);
						}
					}, session, privateMemory, runAs); */
			Vector<GeboChatResponse> finalResponse = new Vector<>();
			return outFlux.map(x -> {
				GeboChatMessageEnvelope envelope = x.getData();
				if (envelope.isLastMessage() && envelope.getContent() instanceof GeboChatResponse response) {
					finalResponse.add(response);
				}
				return envelope;
			}).publishOn(runAs.wrap(Schedulers.boundedElastic())).doOnComplete(() -> {
				runAs.doAs(() -> {
					try {
						sessionLifecycleService.endRequest(request, runtimeData.getChatResponse());
					} catch (Throwable e) {
					}
					try {
						sessionLifecycleService.chatRequestCompleted(request, chatModel);
					} catch (Throwable e) {

					}

				});

			});
		//} catch (AgentException e) {
		//	throw new GeboChatException("Exception in agentic chat", e);
		//}

	}

}
