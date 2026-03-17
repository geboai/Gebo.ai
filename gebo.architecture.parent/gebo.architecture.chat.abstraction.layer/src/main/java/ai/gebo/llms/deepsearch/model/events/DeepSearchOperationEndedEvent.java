package ai.gebo.llms.deepsearch.model.events;

import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeepSearchOperationEndedEvent
		extends AbstractDeepSearchEvent<DeepSearchRequest, DeepSearchOperationEndedEvent.EndedSearchObject> {
	@Data
	public static final class EndedSearchObject {
		String content = null;
	}

	public DeepSearchOperationEndedEvent() {
		setOutputData(new EndedSearchObject());
	}

	public static DeepSearchOperationEndedEvent of(DeepSearchRequest request) {
		DeepSearchOperationEndedEvent e = new DeepSearchOperationEndedEvent();
		e.setInputData(request);
		return e;
	}

	public static Flux<AbstractDeepSearchEvent> justFlux(DeepSearchRequest request) {
		return Flux.just(of(request));
	}

	public static Mono<AbstractDeepSearchEvent> justMono(DeepSearchRequest request) {
		return Mono.just(of(request));
	}
}
