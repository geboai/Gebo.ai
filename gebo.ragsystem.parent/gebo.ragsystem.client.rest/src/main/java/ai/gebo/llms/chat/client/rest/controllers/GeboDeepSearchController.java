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

import ai.gebo.architecture.utils.DataPage;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.client.rest.model.DeepSearchStep;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.GUserMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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

	@DeleteMapping(value = "deleteDeepSearch", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteDeepSearch(@RequestBody @Valid @NotNull DeepSearchRequest request) {
		this.deepSearchService.deleteDeepSearch(request.getCode());
	}

	@PostMapping(value = "doDeepSearch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DeepSearchResponse doDeepSearch(@RequestBody @Valid @NotNull DeepSearchRequest request)
			throws LLMConfigException {
		return this.deepSearchService.search(request);
	}

	@PostMapping(value = "streamDeepSearch", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamDeepSearch(@Valid @NotNull @RequestBody DeepSearchRequest request)
			throws LLMConfigException {
		Flux<AbstractDeepSearchEvent> flux = deepSearchService.searchAsync(request);
		return stream(flux, DeepSearchProcessedEvent.class);
	}

	private Flux<ServerSentEvent<String>> stream(Flux<AbstractDeepSearchEvent> flux,
			Class<? extends AbstractDeepSearchEvent> trailingType) {
		return flux.map(entry -> {
			GeboChatMessageEnvelope _envelope = null;
			if (entry instanceof DeepSearchDocumentEvent documentEvent) {
				_envelope = new GeboChatMessageEnvelope(
						new DeepSearchStep(new GResponseDocumentRef(documentEvent.getInputData()),
								documentEvent.getOutputData().getFragment(), documentEvent.getProcessPercentage()));
			} else if (entry instanceof DeepSearchProcessedEvent processedEvent) {
				GeboChatMessageEnvelope envelop = new GeboChatMessageEnvelope(processedEvent.getOutputData());
				_envelope = envelop;

			} else if (entry instanceof DeepSearchErrorEvent errorEvent) {
				GeboChatMessageEnvelope exceptionEnvelope = new GeboChatMessageEnvelope();
				exceptionEnvelope.setContent(errorEvent.getOutputData());
				_envelope = exceptionEnvelope;
			} else if (entry instanceof DeepSearchChatResponseEvent chatResponseEvent) {
				GeboChatMessageEnvelope envelop = new GeboChatMessageEnvelope(chatResponseEvent.getOutputData());
				_envelope = envelop;
			} else {
				GeboChatMessageEnvelope envelop = new GeboChatMessageEnvelope(entry.getOutputData());
				_envelope = envelop;
			}
			_envelope.setLastMessage(trailingType.isAssignableFrom(entry.getClass()));
			return _envelope;
		}).onErrorResume(exc -> {
			GeboChatMessageEnvelope exceptionEnvelope = new GeboChatMessageEnvelope();
			GUserMessage userMessage = GUserMessage.errorMessage(ERROR_WHILE_RUNNING_DEEP_SEARCH, exc);
			exceptionEnvelope.setContent(userMessage);
			exceptionEnvelope.setLastMessage(true);
			return Flux.just(exceptionEnvelope);
		}).map(StreamUtil.mappingFunction).map(sequence -> ServerSentEvent.<String>builder().data(sequence).build());
	}

	@PostMapping(value = "streamDeepSearchWithChatContext", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamDeepSearchWithChatContext(
			@Valid @NotNull @RequestBody GeboChatRequest request) throws LLMConfigException {
		Flux<AbstractDeepSearchEvent> flux = deepSearchService.searchAsync(request);
		return stream(flux, DeepSearchChatResponseEvent.class);
	}
}
