package ai.gebo.architecture.agents.services;

import reactor.core.publisher.Flux;

public interface IGReactiveOutputAgentsNetworkService<InputType, OutputType>
		extends IGAgentsNetworkService<InputType, OutputType> {
	public Flux<OutputType> getFlux();

}
