package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.Map;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultToolUsingStreamingOutputChatPipelineServiceImpl implements IStreamingOutputChatPipelineService {

	private final IGChatService chatService;
	private final IGPromptConfigDao promptsDao;
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
		Map<String, Object> params = DefaultPipelineSharedPromptPlaceholders.extractSharedPromptParameters(
				runtimeData.getSharedEnvironment(), DefaultPipelineSharedPromptPlaceholders.TOOLS_LIST_TEMPLATE_PARAM);
		PromptTemplate promptTemplate = new PromptTemplate(
				promptsDao.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_TOOLS_CALL_OUTPUT_PROMPT).getPrompt());

		try {
			return chatService.streamChat(promptTemplate.create(params).getContents(),
					runtimeData.getRequestResources(), runtimeData.getChatResponse(), chatModel);
		} catch (GeboChatException | LLMConfigException e) {
			throw new ChatPipelineException("Exception in tools execution output", e);
		}
	}

}
