package ai.gebo.llms.deepsearch.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceExecutor;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class GDeepSearchDataSourceExecutorImpl implements IGDeepSearchDataSourceExecutor {
	private final IGDeepSearchConfigProvider configProvider;
	private final DeepSearchServiceImpl deepSearchServiceImpl;
	private final IGeboThreadManager threadManager;
	private final DeepSearchRequestRepository deepSearchRequestRepository;
	private final IDocumentsChunkService chunkService;
	private final IGSecurityService securityService;

	@Override
	public Flux<GeboChatMessageEnvelope> execute(IGReactiveDeepSearchDataSourceService service, GeboChatRequest request,
			GeboChatResponse response, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			GUserChatSession context) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException {
		String query = GeboChatRequest.actualQuery(request);
		DeepSearchConfig config = this.configProvider.get();
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setChatRequestCode(request.getId());
		deepSearchRequest.setKnowledgeBases(List.of());
		deepSearchRequest.setQuery(query);
		deepSearchRequest.setUserChatContextCode(context.getCode());
		deepSearchRequest.setDeepSearchDataSources(List.of(service.getHandlerId()));
		deepSearchRequest.setUsername(securityService.getCurrentUser().getUsername());
		this.deepSearchRequestRepository.save(deepSearchRequest);
		final String chunkSessionId = chunkService.createChunkingSession("deepsearch:" + request.getId());
		Flux<AbstractDeepSearchEvent> flux = service.streamSearch(chatModel, serviceModel, config, deepSearchRequest,
				List.of(), chunkSessionId, null, null, null, null);
		flux = mapDataSourceProcessedToDeepSearchProcessed(flux, deepSearchRequest);
		flux = deepSearchServiceImpl.manageTrailingChatSessionEvents(flux, request, response, context);
		flux = flux.onErrorResume(Common.commonFallBack(deepSearchRequest));

		flux = flux.subscribeOn(threadManager.getScheduler());
		return deepSearchServiceImpl.mapToChatFlux(flux, DeepSearchProcessedEvent.class);
	}

	private Flux<AbstractDeepSearchEvent> mapDataSourceProcessedToDeepSearchProcessed(
			Flux<AbstractDeepSearchEvent> flux, DeepSearchRequest deepSearchRequest) {
		final Vector<DeepSearchDataSourceProcessedEvent> processed = new Vector<DeepSearchDataSourceProcessedEvent>();
		Flux<AbstractDeepSearchEvent> trailFlux = Flux.defer(() -> {
			DeepSearchResponse response = new DeepSearchResponse();
			response.setDeepsearchCode(deepSearchRequest.getCode());
			response.setProcessPercentage(100.0);
			response.setResponse(processed.isEmpty() ? "" : processed.get(0).getOutputData().getResponse());
			response.setSearchResultsEmpty(processed.isEmpty());
			DeepSearchProcessedEvent event = new DeepSearchProcessedEvent();
			event.setInputData(deepSearchRequest);
			event.setOutputData(response);
			return Flux.just((AbstractDeepSearchEvent) event);
		});
		flux = flux.map(x -> {
			if (x instanceof DeepSearchDataSourceProcessedEvent event) {
				processed.add(event);
			}

			return x;
		});
		return Flux.concat(flux, trailFlux);
	}

}
