package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
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
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultChatWithFilesStreamingOutputPipelineServiceImpl implements IStreamingOutputChatPipelineService {
	static final String DEFAULT_CHAT_WITH_DOCS_STREAMING = "default-chat-with-docs-service";
	private final IGPromptConfigDao promptsDao;
	private final IGChatService chatService;
	private final IGHugeFilesDeepSearch hugeFilesDeepSearch;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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

		GPromptTemplateConfig prompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_CHAT_WITH_DOCUMENTS_PROMPT);
		double fullRequestSize = runtimeData.getRequestResources().getTokensSize() + prompt.getTokensSize();
		// the request fits when it takes the tokens budget's share of the context window at most
		// (ai.gebo.llms.tokens-budget.factor)
		if (fullRequestSize <= BaseLLMSInvokingService.ERRONEUS_TOKEN_LENGTH_ERROR_COEFF * contextWindow) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("execute(...) the request of " + (long) fullRequestSize + " (tok) fits the context of "
						+ (long) contextWindow + " (tok): answered with all its documents");
			}
			return chatService.streamChat(prompt, Map.of(), runtimeData.getRequestResources(),
					runtimeData.getChatResponse(), chatModel);
		} else {
			LLMChatRequestResources resources = new LLMChatRequestResources(
					runtimeData.getRequestResources().getChatWithDocuments(), new AIDocumentsSet(),
					runtimeData.getRequestResources().getUploadedDocuments(), new AIDocumentsSet(),
					runtimeData.getRequestResources().getChathistory(),
					runtimeData.getRequestResources().getCurrentRequest(),
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
			// the same request: its rules, feedback notes, tool calls recorder and knowledge bases
			resources.copyRequestValuesFrom(runtimeData.getRequestResources());
			double minimizedContextRequestSize = ITokensCountable.tokensSize(prompt, resources);
			if (minimizedContextRequestSize <= BaseLLMSInvokingService.ERRONEUS_TOKEN_LENGTH_ERROR_COEFF * contextWindow) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("execute(...) the request of " + (long) fullRequestSize + " (tok) does not fit the context of "
							+ (long) contextWindow + " (tok), with the selected documents only, "
							+ (long) minimizedContextRequestSize + " (tok), it does");
				}
				return chatService.streamChat(prompt, Map.of(), resources, runtimeData.getChatResponse(), chatModel);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("execute(...) the selected documents, " + (long) minimizedContextRequestSize
							+ " (tok), do not fit the context of " + (long) contextWindow
							+ " (tok): they are analysed in pieces");
				}
				try {
					return hugeFilesDeepSearch.streamChatWithHugeFiles(runtimeData, sinkUIEmitter, chatModel,
							serviceModel);
				} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException | GeboIngestionException
						| GeboContentHandlerSystemException | SearchServiceException e) {
					throw new ChatPipelineException("Exception streaming a huge file internal deep search", e);
				}
			}
		}

	}

}
