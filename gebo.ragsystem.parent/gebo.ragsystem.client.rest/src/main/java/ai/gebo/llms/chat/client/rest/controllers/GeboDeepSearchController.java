package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceExecutor;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGInternalKnowledgeBaseDeepSearchExecutor;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.system.ingestion.GeboIngestionException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "api/users/GeboDeepSearchController")
@AllArgsConstructor
public class GeboDeepSearchController {
	final IGDeepSearchService deepSearchService;
	final IGDeepSearchDataSourceExecutor dataSourceExecutor;
	final IGInternalKnowledgeBaseDeepSearchExecutor internalKnowledgeBaseExecutor;
	final IGChatSessionLifeCycleService chatSessionLifecycleService;
	final IGChatModelRuntimeConfigurationDao chatModelsConfigurationDao;
	final IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider;

	@GetMapping(value = "getMyDeepSearchesPaged", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<DeepSearchRequest> getMyDeepSearchesPaged(@RequestParam("page") Integer page,
			@RequestParam("pageSize") Integer pageSize) {
		DataPage _page = new DataPage();
		_page.setPage(page);
		_page.setPageSize(pageSize);

		return deepSearchService.myDeepsearchPaged(_page.toPageable());
	}

	@GetMapping(value = "getMyDeepSearches", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeepSearchRequest> getMyDeepSearches() {
		return deepSearchService.allMyDeepsearches();
	}

	@GetMapping(value = "getMyDeepSearchesStepsPaged", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<DeepSearchDocumentAnalisysResultStep> getMyDeepSearchesStepsPaged(
			@RequestParam("deepSearchCode") String deepSearchCode, @RequestParam("page") Integer page,
			@RequestParam("pageSize") Integer pageSize) {
		DataPage _page = new DataPage();
		_page.setPage(page);
		_page.setPageSize(pageSize);
		return deepSearchService.analisysDetailsPaged(deepSearchCode, _page.toPageable());
	}

	@GetMapping(value = "getMyDeepSearchesSteps", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeepSearchDocumentAnalisysResultStep> getMyDeepSearchesSteps(
			@RequestParam("deepSearchCode") String deepSearchCode) {

		return deepSearchService.analisysDetails(deepSearchCode);
	}

	@GetMapping(value = "getMyDeepSearchById", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchRequest getMyDeepSearchById(@RequestParam("deepSearchCode") String deepSearchCode) {
		return deepSearchService.findDeepSearchRequest(deepSearchCode);
	}

	@GetMapping(value = "getMyDeepSearchResponseByRequestCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchResponse getMyDeepSearchResponseByRequestCode(
			@RequestParam("deepSearchCode") String deepSearchCode) {
		return deepSearchService.findDeepSearchResponse(deepSearchCode);
	}

	@GetMapping(value = "getMyDeepSearchDataSourceDocumentResultsByRequestCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeepSearchDataSourceDocumentResult> getMyDeepSearchDataSourceDocumentResultsByRequestCode(
			@RequestParam("deepSearchCode") String deepSearchCode) {
		return deepSearchService.findDataSourceDocumentResults(deepSearchCode);
	}

	@GetMapping(value = "getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeepSearchDataSourceResponse> getMyDeepSearchDeepSearchDataSourceResponsesByRequestCode(
			@RequestParam("deepSearchCode") String deepSearchCode) {
		return deepSearchService.findDataSourceResponses(deepSearchCode);
	}

	@DeleteMapping(value = "deleteDeepSearch", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteDeepSearch(@RequestBody @Valid @NotNull DeepSearchRequest request) {
		this.deepSearchService.deleteDeepSearch(request.getCode());
	}

	@PostMapping(value = "doDeepSearch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchResponse doDeepSearch(@RequestBody @Valid @NotNull DeepSearchRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		return this.deepSearchService.search(request);
	}

	@GetMapping(value = "getDeepSearchDataSources", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getDeepSearchDataSources() {

		return this.deepSearchService.getDeepSearchActiveHandlers();
	}

	@PostMapping(value = "streamDeepSearch", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamDeepSearch(@Valid @NotNull @RequestBody DeepSearchRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		Flux<AbstractDeepSearchEvent> flux = deepSearchService.streamDeepSearch(request);
		return stream(flux, DeepSearchProcessedEvent.class);
	}

	

	private Flux<ServerSentEvent<String>> stream(Flux<AbstractDeepSearchEvent> flux,
			Class<? extends AbstractDeepSearchEvent> trailingType) {
		return deepSearchService.mapToChatFlux(flux, trailingType).map(StreamUtil.mappingFunction)
				.map(sequence -> ServerSentEvent.<String>builder().data(sequence).build());
	}

	private Flux<ServerSentEvent<String>> streamEnvelopes(Flux<GeboChatMessageEnvelope> flux) {
		return flux.map(StreamUtil.mappingFunction)
				.map(sequence -> ServerSentEvent.<String>builder().data(sequence).build());
	}

	@PostMapping(value = "streamDeepSearchWithChatContext", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamDeepSearchWithChatContext(
			@Valid @NotNull @RequestBody GeboChatRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException, GeboPersistenceException, IOException {
		Flux<AbstractDeepSearchEvent> flux = deepSearchService.streamDeepSearch(request);
		return stream(flux, DeepSearchChatResponseEvent.class);
	}

	@GetMapping(value = "getDeepSearchDocumentsCount", produces = MediaType.APPLICATION_JSON_VALUE)
	public long getDeepSearchDocumentsCount(@RequestParam("deepSearchCode") String deepSearchCode) {
		return this.deepSearchService.getDeepSearchDocumentsCount(deepSearchCode);
	}

	@PostMapping(value = "stopDeepSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	public void stopDeepSearch(@RequestParam("deepSearchCode") String deepSearchCode) {
		this.deepSearchService.stopDeepSearch(deepSearchCode);
	}

	@PostMapping(value = "internalKnowledgeBaseDeepSearch", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> internalKnowledgeBaseDeepSearch(
			@Valid @NotNull @RequestBody GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException, LLMConfigException, IOException,
			GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException {
		this.chatSessionLifecycleService.ensureChatSessionExists(request);
		IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
		IGConfigurableChatModel serviceModel = this.chatModelsConfigurationDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		LLMChatRequestResources requestData = this.chatSessionLifecycleService.startRequest(request, chatModel,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
		GeboChatResponse response = this.chatSessionLifecycleService.createEmptyResponse(request);
		MinimalChatContext minimalChatContext = this.chatSessionLifecycleService.getMinimalChatContext(request,
				serviceModel.getContextLength() / 3);
		Flux<GeboChatMessageEnvelope> flux = this.internalKnowledgeBaseExecutor.execute(requestData, minimalChatContext,
				response, chatModel, serviceModel);
		return streamEnvelopes(flux);
	}

	@PostMapping(value = "dataSourceDeepSearch/{dataSourceCode}", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> dataSourceDeepSearch(@PathVariable("dataSourceCode") String dataSourceCode,
			@Valid @NotNull @RequestBody GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException, LLMConfigException, IOException,
			GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException {
		this.chatSessionLifecycleService.ensureChatSessionExists(request);
		IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
		IGConfigurableChatModel serviceModel = this.chatModelsConfigurationDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		LLMChatRequestResources requestData = this.chatSessionLifecycleService.startRequest(request, chatModel,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
		GeboChatResponse response = this.chatSessionLifecycleService.createEmptyResponse(request);
		MinimalChatContext minimalChatContext = this.chatSessionLifecycleService.getMinimalChatContext(request,
				serviceModel.getContextLength() / 3);
		List<IGReactiveDeepSearchDataSourceService> dataSources = dataSourcesProvider.getDynamicDeepSearchServices();
		IGReactiveDeepSearchDataSourceService dataSourceHandler = dataSources.stream()
				.filter(x -> x.getHandlerId().equals(dataSourceCode)).findFirst().orElseThrow(() -> {
					return new GeboChatSessionLifecycleException("No data source found:" + dataSourceCode);
				});
		Flux<GeboChatMessageEnvelope> flux = this.dataSourceExecutor.execute(dataSourceHandler, request,
				minimalChatContext, response, chatModel, serviceModel);
		return streamEnvelopes(flux);
	}
}
