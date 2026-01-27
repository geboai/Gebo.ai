package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultStreamingOutputChatPipelineServiceImpl extends BaseOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private final IGChatService chatService;
	private final ChatPipelinesConfiguration configuration;
	public static final String DEFAULT_STREAMING_OUTPUT = "default-streaming-output";

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_STREAMING_OUTPUT;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		LLMChatRequestResources request = super.integrateWithAISuggestedDocuments(runtimeData);
		try {
			return this.chatService.streamChat(configuration.getDefaultPipelineOutputPrompt().getPrompt(), request,
					runtimeData.getUserChatContext(), runtimeData.getChatResponse(), chatModel);
		} catch (GeboChatException | LLMConfigException e) {
			throw new ChatPipelineException("Exception handing standard chat output", e);
		}
	}

}
