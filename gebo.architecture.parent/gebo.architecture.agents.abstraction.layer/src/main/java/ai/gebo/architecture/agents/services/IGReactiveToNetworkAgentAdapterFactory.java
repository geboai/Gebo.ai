package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public interface IGReactiveToNetworkAgentAdapterFactory<RequestType, ResponseType> {
	@Getter
	@AllArgsConstructor
	public static class AdapterWithFlux<RequestType, ResponseType> {
		private final IGNetworkAgentService<RequestType, ResponseType> adapter;
		private final Flux<ResponseType> flux;
		private final Sinks.Many<IGPartialOperation<ResponseType>> sink;
	}

	public boolean canBeAdapted(IGReactiveAgentService<RequestType, ResponseType> service);

	public AdapterWithFlux<RequestType, ResponseType> create(IGReactiveAgentService<RequestType, ResponseType> service)
			throws NetworkOfAgentsException;
}
