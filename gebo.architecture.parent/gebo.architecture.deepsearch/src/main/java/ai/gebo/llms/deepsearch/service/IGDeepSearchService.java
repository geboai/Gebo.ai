package ai.gebo.llms.deepsearch.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import reactor.core.publisher.Flux;

public interface IGDeepSearchService {
	public Flux<AbstractDeepSearchEvent> searchAsync(DeepSearchRequest request) throws LLMConfigException;

	public Flux<AbstractDeepSearchEvent> searchAsync(GeboChatRequest request) throws LLMConfigException;

	public default DeepSearchResponse search(DeepSearchRequest request) throws LLMConfigException {
		AbstractDeepSearchEvent last = this.searchAsync(request).blockLast();
		if (last instanceof DeepSearchProcessedEvent result) {
			return result.getOutputData();
		} else
			return null;
	}

	public Page<DeepSearchRequest> myDeepsearchPaged(Pageable pageable);

	public List<DeepSearchRequest> allMyDeepsearches();

	public Page<DeepSearchDocumentAnalisysResultStep> analisysDetailsPaged(String deepSearchCode, Pageable pageable);

	public List<DeepSearchDocumentAnalisysResultStep> analisysDetails(String deepSearchCode);

	public DeepSearchResponse findDeepSearchResponse(String deepSearchCode);

	public DeepSearchRequest findDeepSearchRequest(String deepSearchCode);

	public void deleteDeepSearch(String deepSearchCode);
}
