package ai.gebo.llms.deepsearch.model.events;

import ai.gebo.llms.deepsearch.model.DeepSearchNotification;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeepSearchNotificationEvent extends AbstractDeepSearchEvent<DeepSearchRequest, DeepSearchNotification> {
	public static DeepSearchNotificationEvent of(DeepSearchRequest request, String message,String dataSourceDescription) {
		DeepSearchNotificationEvent out = new DeepSearchNotificationEvent();
		out.setInputData(request);
		out.setOutputData(new DeepSearchNotification(message,dataSourceDescription));
		out.getOutputData().setContent(message);
		return out;
	}

	public static Mono<AbstractDeepSearchEvent> mono(DeepSearchRequest request, String message,String dataSourceDescription) {
		return Mono.just(of(request, message, dataSourceDescription));
	}

	public static Flux<AbstractDeepSearchEvent> flux(DeepSearchRequest request, String message,String dataSourceDescription) {
		return Mono.just((AbstractDeepSearchEvent) of(request, message, dataSourceDescription)).flux();
	}
}
