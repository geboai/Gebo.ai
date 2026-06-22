package ai.gebo.llms.agent.chat.service.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.llms.agent.chat.service.IReportWriterNetworkAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public final class ReportWriterNetworkAgentServiceNetworkAdapterFactory
		implements IGReactiveToNetworkAgentAdapterFactory<String, GeboChatMessageEnvelope, GeboChatMessageEnvelope> {

	@Override
	public boolean canBeAdapted(IGReactiveAgentService<String, GeboChatMessageEnvelope, GeboChatMessageEnvelope> service) {

		return service instanceof IReportWriterNetworkAgentService;
	}

	@Override
	public AdapterWithFlux<String, GeboChatMessageEnvelope> create(
			IGReactiveAgentService<String, GeboChatMessageEnvelope, GeboChatMessageEnvelope> service) {
		Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink = Sinks.many().unicast().onBackpressureBuffer();
		ReportWriterNetworkAgentServiceNetworkAdapter adapter = new ReportWriterNetworkAgentServiceNetworkAdapter(service,
				sink);
		Flux<GeboChatMessageEnvelope> flux = sink.asFlux().map(IGPartialOperation::getData);
		return new AdapterWithFlux<>(adapter, flux, sink);
	}
}
