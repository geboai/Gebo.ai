package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.service.IGHugeFilesDeepSearch;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultChatWithFilesStreamingOutputPipelineServiceImpl implements IStreamingOutputChatPipelineService {
	static final String DEFAULT_CHAT_WITH_DOCS_STREAMING = "default-chat-with-docs-service";
	private final IGPromptConfigDao promptsDao;
	private final IGChatService chatService;
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService;
	private final IGHugeFilesDeepSearch hugeFilesDeepSearch;

	@Override
	public StepExecutorType getExecutorType() {
		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_CHAT_WITH_DOCS_STREAMING;
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, LLMConfigException, GeboChatException, IOException {
		// if the size of the actual chatModel context window minus the prompt minus the
		// actual context is less than the chatModel context window
		// then go straight to a standard chat output with all the data, otherwise
		// running a deep search in the internal knowledge base with no
		// additional searches
		double contextWindow = chatModel.getContextLength();

		GPromptConfig prompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_CHAT_WITH_DOCUMENTS_PROMPT);
		double fullRequestSize = runtimeData.getRequestResources().getTokensSize() + prompt.getTokensSize();
		if (contextWindow >= 0.8 * fullRequestSize) {
			return chatService.streamChat(prompt.getPrompt(), runtimeData.getRequestResources(),
					runtimeData.getChatResponse(), chatModel);
		} else {
			LLMChatRequestResources resources = new LLMChatRequestResources(
					runtimeData.getRequestResources().getChatWithDocuments(), new AIDocumentsSet(),
					runtimeData.getRequestResources().getUploadedDocuments(), new AIDocumentsSet(),
					runtimeData.getRequestResources().getChathistory(),
					runtimeData.getRequestResources().getCurrentRequest(),
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
			double minimizedContextRequestSize = ITokensCountable.tokensSize(prompt, resources);
			if (contextWindow > 0.8 * minimizedContextRequestSize) {
				return chatService.streamChat(prompt.getPrompt(), resources, runtimeData.getChatResponse(), chatModel);
			} else {
				try {
					return hugeFilesDeepSearch.streamChatWithHugeFiles(runtimeData.getRequestResources(),
							runtimeData.getMinimalChatContext(), runtimeData.getChatResponse(), serviceModel,
							chatModel);
				} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException | GeboIngestionException
						| GeboContentHandlerSystemException | SearchServiceException e) {
					throw new ChatPipelineException("Exception streaming a huge file internal deep search", e);
				}
			}
		}

	}

}
