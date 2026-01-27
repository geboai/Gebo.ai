package ai.gebo.llms.chat.pipelines.service.defaultsteps;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.model.SearchRewritings;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultRagStreamingOutputChatPipelineStepServiceImpl extends BaseOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private final IGRagChatService ragChatService;
	private final ChatPipelinesConfiguration configuration;
	public static final String DEFAULT_RAG_STEP = "default-rag-step";

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_RAG_STEP;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		LLMChatRequestResources request = super.integrateWithAISuggestedDocuments(runtimeData);
		SearchRewritings searchRewritings = DefaultPipelineSharedEnvironmentUtil
				.getAISuggestedSearchRewritings(runtimeData);
		request = integrateWithSearches(searchRewritings, request);
		try {
			return ragChatService.streamChat(configuration.getDefaultPipelineRagOutputPrompt().getPrompt(), request,
					runtimeData.getUserChatContext(), runtimeData.getChatResponse(), chatModel);
		} catch (GeboChatException | LLMConfigException e) {
			throw new ChatPipelineException("Exception in finalizing rag chat", e);
		}

	}

	private LLMChatRequestResources integrateWithSearches(SearchRewritings searchRewritings,
			LLMChatRequestResources request) {
		// TODO Auto-generated method stub
		return null;
	}

}
