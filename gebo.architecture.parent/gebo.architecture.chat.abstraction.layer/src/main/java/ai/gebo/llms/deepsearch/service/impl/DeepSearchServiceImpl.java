package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchDocumentResultError;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGHugeFilesDeepSearch;
import ai.gebo.llms.deepsearch.service.ReactiveMonitor;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.system.ingestion.GeboIngestionException;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@Scope("singleton")
@AllArgsConstructor
public class DeepSearchServiceImpl extends BaseLLMSInvokingService
		implements IGDeepSearchService, IGHugeFilesDeepSearch {

	private static final String ERROR_DOING_DEEP_SEARCH = "Error doing deep search";
	static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchServiceImpl.class);
	protected final DeepSearchDefaultConfig defaultDeepsearchConfig;
	protected final DeepSearchConfigRepository configRepository;
	protected final IGRuntimeBinder runtimeBinder;
	protected final IGSecurityService securityService;
	protected final GUserChatSessionRepository userChatContextRepository;
	protected final KnowledgeBaseRepository knowledgeBaseRepository;
	protected final IGChatSessionLifeCycleService sessionLifecycleService;
	protected final IGDeepSearchConfigProvider configProvider;
	protected final IGeboThreadManager threadManager;
	private static final String ERROR_WHILE_RUNNING_DEEP_SEARCH = "Error while running deep search";
	

	@PreDestroy
	public void shutdown() {
		
	}

	@Override
	public Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(LLMChatRequestResources request,
			MinimalChatContext minimalChatContext, GeboChatRequest geboChatRequest, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, List<String> searchDataSources,
			int perDataSourceK, int globalK, int sampleTextTokensSize)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		return Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				final IDocumentsChunkService chunkService = runtimeBinder
						.getImplementationOf(IDocumentsChunkService.class);
				final String chunkSessionId = chunkService
						.createChunkingSession("pureSearch:" + request.getCurrentRequest().getId());
				final FullReactiveDeepsearchWorker worker = runtimeBinder
						.getImplementationOf(FullReactiveDeepsearchWorker.class);

				Flux<AbstractPureSearchDocumentResultEntry> flow = null;
				try {
					flow = worker.streamPureSearch(request, minimalChatContext, geboChatRequest, emitter, chatModel,
							serviceModel, searchDataSources, perDataSourceK, globalK, sampleTextTokensSize,
							chunkSessionId);
					if (flow != null) {
						flow = flow.transform(ReactiveMonitor.monitor("pure-search"));
					}
					flow.filter(x -> x != null).onErrorResume(exc -> {
						final String msg = "Error while streaming chat respose";
						LOGGER.error(msg, exc);

						GUserMessage userMessage = GUserMessage.errorMessage(msg, exc);
						userMessage.setSeverity(MsgServerity.warn);
						return Flux.just(new PureSearchDocumentResultError(null, null, userMessage));
					});
				} catch (Throwable e) {
					LOGGER.error("Error doing pure search", e);
					PureSearchDocumentResultError error = new PureSearchDocumentResultError(null, null,
							GUserMessage.errorMessage("Error searching", e));
					flow = Flux.just(error);
				}
				if (chunkSessionId != null && flow != null) {
					Runnable deleteChunkingSessionRunnable = new Runnable() {
						@Override
						public void run() {
							try {
								runAs.doAsWithException(() -> {
									chunkService.disposeChunkingSession(chunkSessionId);
								});
							} catch (Throwable th) {
								LOGGER.error("Exception disposing", th);
							}
						}
					};
					flow.doAfterTerminate(deleteChunkingSessionRunnable);
				}
				return flow;
			});
		}).subscribeOn(threadManager.getBoundedElastic());

	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamDeepSearch(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			List<String> searchDataSources, int perDataSourceK, int globalK)
			throws LLMConfigException, GeboChatSessionLifecycleException {

		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		return Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				final FullReactiveDeepsearchWorker worker = runtimeBinder
						.getImplementationOf(FullReactiveDeepsearchWorker.class);
				return worker.streamNewDeepSearch(runtimeData, sinkUIEmitter, chatModel, serviceModel,
						searchDataSources, perDataSourceK, globalK);
			});
		}).subscribeOn(threadManager.getBoundedElastic());
	}

	@Override
	public List<GBaseObject> getDeepSearchActiveHandlers() {
		final FullReactiveDeepsearchWorker worker = runtimeBinder
				.getImplementationOf(FullReactiveDeepsearchWorker.class);
		return worker.getDeepSearchActiveHandlers(this.configProvider.get());
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamChatWithHugeFiles(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws GeboChatSessionLifecycleException, LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException {
		final FullReactiveDeepsearchWorker worker = runtimeBinder
				.getImplementationOf(FullReactiveDeepsearchWorker.class);
		return worker.streamChatWithHugeFiles(runtimeData, sinkUIEmitter, chatModel, serviceModel);
	}

}
