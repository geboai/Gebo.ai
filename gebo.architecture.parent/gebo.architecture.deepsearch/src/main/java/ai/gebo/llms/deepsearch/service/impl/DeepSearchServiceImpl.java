package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.BeanResolver;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchDocumentAnalisysResultStepRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchResponseRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.FluxSink.OverflowStrategy;

@Service
@AllArgsConstructor
public class DeepSearchServiceImpl implements IGDeepSearchService {
	static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchServiceImpl.class);
	final DeepSearchDefaultConfig defaultDeepsearchConfig;
	final DeepSearchConfigRepository configRepository;
	final IGRuntimeBinder runtimeBinder;
	final IGSecurityService securityService;
	final DeepSearchRequestRepository requestsRepository;
	final DeepSearchDocumentAnalisysResultStepRepository stepsRepository;
	final DeepSearchResponseRepository responseRepository;
	final GUserChatContextRepository userChatContextRepository;
	private KnowledgeBaseRepository knowledgeBaseRepository;
	final IGRagChatService ragChatService;
	final IGChatService chatService;
	final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao;
	final ChatProfilesRepository chatProfilesRepository;
	final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	final ExecutorService deepSearchExecutor = Executors
			.newSingleThreadExecutor(r -> new Thread(r, "deep-search-thread"));

	@Override
	public Flux<AbstractDeepSearchEvent> searchAsync(DeepSearchRequest request) throws LLMConfigException {
		final UserInfos userInfos = securityService.getCurrentUser();
		request.setUsername(userInfos.getUsername());
		requestsRepository.save(request);
		final List<DeepSearchConfig> data = configRepository.findAll();
		final DeepSearchConfig configuration = data != null && data.size() > 0 ? data.get(0) : defaultDeepsearchConfig;
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		IGConfigurableEmbeddingModel defaultEmbeddingModel = embeddingModelsRuntimeDao.defaultHandler();
		if (defaultEmbeddingModel != null) {
			embeddingModels.add(defaultEmbeddingModel);
		}
		List<GKnowledgeBase> knowledgeBases = knowledgeBaseRepository.findAllById(request.getKnowledgeBases());
		knowledgeBases.stream().map(x -> x.getEmbeddingModelReferences()).filter(y -> y != null && !y.isEmpty())
				.forEach(modelsList -> {
					modelsList.forEach(modelReference -> {
						IGConfigurableEmbeddingModel model = embeddingModelsRuntimeDao
								.findByModelReference(modelReference);
						if (model != null && model != defaultEmbeddingModel) {
							if (!embeddingModels.stream()
									.anyMatch(x -> (x == model || model.getCode().equals(x.getCode())))) {
								embeddingModels.add(model);
							}
						}
					});
				});
		GObjectRef<GBaseChatModelConfig> chatModelReference = configuration.getChatModelConfiguration();
		IGConfigurableChatModel chatModel = null;
		if (chatModelReference != null) {
			chatModel = chatModelsConfigDao.findByModelReference(chatModelReference);
		}
		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		IGConfigurableChatModel finalChatModel = chatModel;
		if (finalChatModel == null) {
			DeepSearchErrorEvent errorEvent = new DeepSearchErrorEvent();
			errorEvent.setInputData(request);
			errorEvent.setOutputData(GUserMessage.errorMessage("No chat model defined",
					"No chat model configured for deep search nor default chat model is configured"));
			return Flux.just(errorEvent);
		}
		Flux<AbstractDeepSearchEvent> flux = Flux.create(sink -> {

			// AtomicBoolean cancelled = new AtomicBoolean(false);
			// sink.onCancel(() -> cancelled.set(true));
			// sink.onDispose(() -> cancelled.set(true));

			Future<?> future = deepSearchExecutor.submit(() -> {
				try {

					final DeepsearchWorker deepSearchWorker = runtimeBinder.getImplementationOf(DeepsearchWorker.class);
					final DeepSearchState state = new DeepSearchState();
					final List<AbstractDeepSearchEvent> history = new ArrayList<AbstractDeepSearchEvent>();

					AbstractDeepSearchEvent thisStepResult = null;
					do {
						thisStepResult = deepSearchWorker.nextStep(request, history, state, configuration, userInfos,
								embeddingModels, finalChatModel);

						if (thisStepResult != null) {
							sink.next(thisStepResult);
							if (thisStepResult instanceof DeepSearchDocumentEvent) {
								history.add(thisStepResult);
							}
						} else {
							sink.complete();
						}
						if (thisStepResult instanceof DeepSearchProcessedEvent end) {
							sink.complete();
						}
					} while (thisStepResult != null && !(thisStepResult instanceof DeepSearchProcessedEvent
							|| thisStepResult instanceof DeepSearchErrorEvent));

				} catch (Throwable t) {
					LOGGER.error("Error in searchAsync(...)", t);
					// DeepSearchErrorEvent event = new DeepSearchErrorEvent();
					// event.setOutputData(GUserMessage.errorMessage("Error while deep searching",
					// t));
					// event.setInputData(request);
					// sink.next(event);
					sink.error(t);
				}
			});

			// if the downstream disposes, cancel the worker
			sink.onDispose(() -> future.cancel(true));

		}, OverflowStrategy.BUFFER);

		return flux.map(x -> {
			if (x instanceof DeepSearchDocumentEvent docEvent) {
				stepsRepository.save(docEvent.getOutputData());
			}
			if (x instanceof DeepSearchProcessedEvent processed) {
				responseRepository.save(processed.getOutputData());
			}
			return x;
		});
	}

	@Override
	public Page<DeepSearchRequest> myDeepsearchPaged(Pageable pageable) {

		return requestsRepository.findByUsername(securityService.getCurrentUser().getUsername(), pageable);
	}

	@Override
	public List<DeepSearchRequest> allMyDeepsearches() {

		return requestsRepository.findByUsername(securityService.getCurrentUser().getUsername());
	}

	@Override
	public Page<DeepSearchDocumentAnalisysResultStep> analisysDetailsPaged(String deepSearchCode, Pageable pageable) {
		findDeepSearchRequest(deepSearchCode);
		return stepsRepository.findByDeepsearchCode(deepSearchCode, pageable);
	}

	@Override
	public List<DeepSearchDocumentAnalisysResultStep> analisysDetails(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		return stepsRepository.findByDeepsearchCode(deepSearchCode);
	}

	@Override
	public DeepSearchResponse findDeepSearchResponse(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		Optional<DeepSearchResponse> result = responseRepository.findByDeepsearchCode(deepSearchCode);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public DeepSearchRequest findDeepSearchRequest(String deepSearchCode) {
		Optional<DeepSearchRequest> data = requestsRepository.findById(deepSearchCode);
		if (data.isPresent()) {
			securityService.checkBeingCreator(data.get());
		}
		return data.isPresent() ? data.get() : null;
	}

	@Override
	@Transactional
	public void deleteDeepSearch(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		requestsRepository.deleteById(deepSearchCode);
		responseRepository.deleteByDeepsearchCode(deepSearchCode);
		stepsRepository.deleteByDeepsearchCode(deepSearchCode);
	}

	@Override
	public Flux<AbstractDeepSearchEvent> searchAsync(GeboChatRequest request) throws LLMConfigException {
		if (request.getId() == null) {
			request.setId(UUID.randomUUID().toString());
		}
		String userChatContextCode = request.getUserChatContextCode();
		if (userChatContextCode == null || userChatContextCode.trim().length() == 0) {
			throw new IllegalStateException("Cannot handle request without referred user context code");
		}
		Optional<GUserChatContext> chatContextData = userChatContextRepository.findById(userChatContextCode);
		if (chatContextData.isEmpty())
			throw new IllegalStateException("Not existent user chat context");
		final GUserChatContext chatContext = chatContextData.get();
		GeboChatResponse cleanResponse = null;
		final boolean isRag = chatContext.getRagChat() != null && chatContext.getRagChat();
		List<GKnowledgeBase> visibleKnowledgeBases = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		List<String> knowledgeBasesCodesList = visibleKnowledgeBases.stream().map(x -> x.getCode()).toList();
		if (chatContext.getChatProfileCode() != null && isRag) {
			Optional<GChatProfileConfiguration> chatProfileOptional = chatProfilesRepository
					.findById(chatContext.getChatProfileCode());
			if (chatProfileOptional.isPresent()) {
				List<String> knowledgebaseCodes = chatProfileOptional.get().getKnowledgeBaseCodes();
				boolean allAccessible = chatProfileOptional.get().getUserChoosesKnowledgeBases() != null
						&& chatProfileOptional.get().getUserChoosesKnowledgeBases();
				if (!allAccessible && knowledgebaseCodes.size() > 0) {
					var filteredvisibles = knowledgeBaseVisibilityService
							.visiblesAndChildKnowledgebases(knowledgebaseCodes);
					if (filteredvisibles.size() > 0) {
						knowledgeBasesCodesList = filteredvisibles.stream().map(x -> x.getCode()).toList();
					}
				}
			}
		}
		if (chatContext.getChoosedKnowledgeBases() != null && !chatContext.getChoosedKnowledgeBases().isEmpty()) {

		}
		if (isRag) {
			cleanResponse = this.ragChatService.createUnprocessedResponse(request);
			cleanResponse.setQueryResponse("");
			this.ragChatService.addChatInteractionToUserContext(request, cleanResponse, chatContext);
		} else {
			cleanResponse = this.chatService.createUnprocessedResponse(request);
			cleanResponse.setQueryResponse("");
			this.chatService.addChatInteractionToUserContext(request, cleanResponse, chatContext);
		}
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setChatRequestCode(request.getId());
		deepSearchRequest.setKnowledgeBases(knowledgeBasesCodesList);
		deepSearchRequest.setQuery(request.getQuery());
		deepSearchRequest.setUserChatContextCode(userChatContextCode);
		final GeboChatResponse response = cleanResponse;
		final List<GResponseDocumentRef> documents = new ArrayList<GResponseDocumentRef>();
		response.setDocumentsRef(documents);
		Mono<AbstractDeepSearchEvent> trailingFlux = Mono.fromSupplier(() -> {
			DeepSearchChatResponseEvent responseEvent = new DeepSearchChatResponseEvent();
			responseEvent.setInputData(request);
			responseEvent.setOutputData(response);
			return responseEvent;
		});
		return searchAsync(deepSearchRequest).map(x -> {
			if (x instanceof DeepSearchDocumentEvent documentEvent) {
				// for each document add a reference here
				GDocumentReference currentDocument = documentEvent.getInputData();
				if (!documents.stream().anyMatch(y -> y.getDocumentCode().equals(currentDocument.getCode()))) {
					GResponseDocumentRef reference = new GResponseDocumentRef(currentDocument);
					documents.add(reference);
				}
			}
			if (x instanceof DeepSearchProcessedEvent processedEvent) {
				// completing with chat response in the history
				String responseText = processedEvent.getOutputData() != null
						? processedEvent.getOutputData().getResponse()
						: "";
				response.setQueryResponse(responseText);
				if (isRag) {

					this.ragChatService.addChatInteractionToUserContext(request, response, chatContext);
				} else {
					this.chatService.addChatInteractionToUserContext(request, response, chatContext);

				}
			}
			return x;
		}).concatWith(trailingFlux);
	}

}
