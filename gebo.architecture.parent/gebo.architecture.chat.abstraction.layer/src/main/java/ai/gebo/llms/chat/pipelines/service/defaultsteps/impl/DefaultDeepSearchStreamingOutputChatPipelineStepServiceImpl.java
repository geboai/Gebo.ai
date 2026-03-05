package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl
		implements IStreamingOutputChatPipelineService {
	private final IGDeepSearchService deepSearchService;
	private final IGeboThreadManager threadManager;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	public static final String DEFAULT_DEEPSEARCH_STREAMING = "default-deepsearch-streaming";
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl.class);

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPSEARCH_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		Object deepSearchedSystems = runtimeData.getSharedEnvironment()
				.get(DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS);
		List<String> deepSearchDataSources = deepSearchedSystems instanceof List list ? list : List.of();
		try {

			Flux<AbstractDeepSearchEvent> flux = deepSearchService.streamDeepSearch(runtimeData.getRequestResources(),
					runtimeData.getMinimalChatContext(), runtimeData.getChatResponse(), chatModel, serviceModel,
					deepSearchDataSources);
			/*
			 * Flux<DocumentsEnrichDecision> enrichDecision =
			 * super.doDocumentsRetrieve(runtimeData, serviceModel,
			 * LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
			 * 
			 * enrichDecision.concatMap(ed -> { List<String> aiChoosedDataSources =
			 * ed.getSearchesDecisions().getDeepSearchDataSources(); try { return
			 * deepSearchService.streamDeepSearch(ed.getRequestResources(),
			 * runtimeData.getMinimalChatContext(), runtimeData.getChatResponse(),
			 * chatModel, serviceModel, aiChoosedDataSources); } catch (LLMConfigException |
			 * GeboChatSessionLifecycleException e) { String msg =
			 * "Nested exception in deferred stream creation"; throw new
			 * RuntimeException(msg, e); } });
			 */
			Flux<GeboChatMessageEnvelope> mapped = deepSearchService.mapToChatFlux(flux,
					DeepSearchChatResponseEvent.class);
			flux.doOnComplete(() -> {
				try {
					this.chatSessionLifecycleService
							.chatRequestCompleted(runtimeData.getRequestResources().getCurrentRequest(), chatModel);
				} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
					LOGGER.error("Exceptinin deep search streaming pipeline handler", e);
				}
			});
			mapped.subscribeOn(threadManager.getScheduler());
			return mapped;
		} catch (LLMConfigException e) {
			throw new ChatPipelineException("Error executing deep search", e);
		}

	}

}
