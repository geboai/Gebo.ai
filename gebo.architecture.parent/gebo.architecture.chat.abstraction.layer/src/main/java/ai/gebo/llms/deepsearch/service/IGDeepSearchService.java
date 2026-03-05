package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchUISettings;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.model.base.GBaseObject;
import reactor.core.publisher.Flux;

public interface IGDeepSearchService {
	public Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException;

	public Flux<AbstractDeepSearchEvent> streamDeepSearch(GeboChatRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException, GeboPersistenceException, IOException;

	public default DeepSearchResponse search(DeepSearchRequest request) throws LLMConfigException, GeboChatSessionLifecycleException {
		AbstractDeepSearchEvent last = this.streamDeepSearch(request).blockLast();
		if (last instanceof DeepSearchProcessedEvent result) {
			return result.getOutputData();
		} else
			return null;
	}

	public Page<DeepSearchRequest> myDeepsearchPaged(Pageable pageable);

	public List<DeepSearchRequest> allMyDeepsearches();

	public Page<DeepSearchDocumentAnalisysResultStep> analisysDetailsPaged(String deepSearchCode, Pageable pageable);

	public List<DeepSearchDocumentAnalisysResultStep> analisysDetails(String deepSearchCode);

	public List<DeepSearchDataSourceDocumentResult> findDataSourceDocumentResults(String deepSearchCode);

	public List<DeepSearchDataSourceResponse> findDataSourceResponses(String deepSearchCode);

	public DeepSearchResponse findDeepSearchResponse(String deepSearchCode);

	public DeepSearchRequest findDeepSearchRequest(String deepSearchCode);

	public List<GBaseObject> getDeepSearchActiveHandlers();

	public void deleteDeepSearch(String deepSearchCode);

	public void deleteDeepSearchByUserContextCode(String userContextCode);

	public long getDeepSearchDocumentsCount(String deepSearchCode);

	void stopDeepSearch(String deepSearchRequestId);

	public DeepSearchUISettings getDeepSearchUISettings();

	public Flux<AbstractDeepSearchEvent> streamDeepSearch(LLMChatRequestResources request,
			MinimalChatContext minimalChatContext, GeboChatResponse chatResponse, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, List<String> deepSearchDataSources)
			throws LLMConfigException, GeboChatSessionLifecycleException;

	public Flux<GeboChatMessageEnvelope> mapToChatFlux(Flux<AbstractDeepSearchEvent> flux,
			Class<? extends AbstractDeepSearchEvent> trailingType);
}
