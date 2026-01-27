package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultToolUsingStreamingOutputChatPipelineServiceImpl implements IStreamingOutputChatPipelineService {
	private final IGChatService chatService;
	private final ChatPipelinesConfiguration configuration;
	public static final String DEFAULT_TOOL_USING_STREAMING = "default-tool-using-streaming";

	@Override
	public StepExecutorType getExecutorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStepId() {

		return DEFAULT_TOOL_USING_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		List<String> toolCallsList = DefaultPipelineSharedEnvironmentUtil.getAISuggestedToolsCallList(runtimeData);
		PromptTemplate promptTemplate = new PromptTemplate(
				configuration.getDefaultPipelineToolCallOutputPrompt().getPrompt());
		promptTemplate.add("toolsList", toolCallsList != null ? toolCallsList : List.of());
		try {
			return chatService.streamChat(promptTemplate.render(), runtimeData.getRequestResources(),
					runtimeData.getUserChatContext(), runtimeData.getChatResponse(), chatModel);
		} catch (GeboChatException | LLMConfigException e) {
			throw new ChatPipelineException("Exception in tools execution output", e);
		}
	}

}
