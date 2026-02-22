package ai.gebo.llms.deepsearch.model.events;

import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeepSearchOperationEndedEvent extends AbstractDeepSearchEvent<DeepSearchRequest, Object> {
	public DeepSearchOperationEndedEvent() {
		setOutputData(new Object());
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
