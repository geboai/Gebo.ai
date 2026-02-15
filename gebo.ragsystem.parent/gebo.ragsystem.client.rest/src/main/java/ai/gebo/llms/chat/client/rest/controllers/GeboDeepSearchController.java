package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchUISettings;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "api/users/GeboDeepSearchController")
@AllArgsConstructor
public class GeboDeepSearchController {
	private static final String ERROR_WHILE_RUNNING_DEEP_SEARCH = "Error while running deep search";
	final IGDeepSearchService deepSearchService;

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
			throws LLMConfigException {
		return this.deepSearchService.search(request);
	}

	@GetMapping(value = "getDeepSearchDataSources", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getDeepSearchDataSources() {

		return this.deepSearchService.getDeepSearchActiveHandlers();
	}

	@PostMapping(value = "streamDeepSearch", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamDeepSearch(@Valid @NotNull @RequestBody DeepSearchRequest request)
			throws LLMConfigException {
		Flux<AbstractDeepSearchEvent> flux = deepSearchService.streamDeepSearch(request);
		return stream(flux, DeepSearchProcessedEvent.class);
	}

	@GetMapping(value = "getDeepSearchUISettings", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchUISettings getDeepSearchUISettings() {
		return deepSearchService.getDeepSearchUISettings();
	}

	private Flux<ServerSentEvent<String>> stream(Flux<AbstractDeepSearchEvent> flux,
			Class<? extends AbstractDeepSearchEvent> trailingType) {
		return deepSearchService.mapToChatFlux(flux, trailingType).map(StreamUtil.mappingFunction)
				.map(sequence -> ServerSentEvent.<String>builder().data(sequence).build());
	}

	@PostMapping(value = "streamDeepSearchWithChatContext", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamDeepSearchWithChatContext(
			@Valid @NotNull @RequestBody GeboChatRequest request) throws LLMConfigException, GeboChatSessionLifecycleException, GeboPersistenceException {
		Flux<AbstractDeepSearchEvent> flux = deepSearchService.streamDeepSearch(request);
		return stream(flux, DeepSearchChatResponseEvent.class);
	}

	@GetMapping(value = "getDeepSearchDocumentsCount", produces = MediaType.APPLICATION_JSON_VALUE)
	public long getDeepSearchDocumentsCount(@RequestParam("deepSearchCode") String deepSearchCode) {
		return this.deepSearchService.getDeepSearchDocumentsCount(deepSearchCode);
	}

}
