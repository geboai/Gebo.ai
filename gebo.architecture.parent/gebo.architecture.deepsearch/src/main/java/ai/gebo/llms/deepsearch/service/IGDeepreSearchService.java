package ai.gebo.llms.deepsearch.service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import reactor.core.publisher.Flux;

public interface IGDeepreSearchService {
	public Flux<AbstractDeepSearchEvent> searchAsync(DeepSearchRequest request) throws LLMConfigException;
	
	public default DeepSearchResponse search(DeepSearchRequest request) throws LLMConfigException {
		AbstractDeepSearchEvent last = this.searchAsync(request).blockLast();
		if (last instanceof DeepSearchProcessedEvent result) {
			return result.getOutputData();
		} else
			return null;
	}
}
