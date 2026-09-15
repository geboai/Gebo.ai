package ai.gebo.llms.agent.chat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.llms.agent.chat.service.IReportWriterReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public final class ReportWriterReactiveAgentServiceNetworkAdapterFactory
		implements IGReactiveToNetworkAgentAdapterFactory<String, GeboChatMessageEnvelope> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReportWriterReactiveAgentServiceNetworkAdapterFactory.class);

	@Override
	public boolean canBeAdapted(IGReactiveAgentService<String, GeboChatMessageEnvelope> service) {
		final boolean adaptable = service instanceof IReportWriterReactiveAgentService;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("canBeAdapted(" + (service != null ? service.getId() : null) + ") -> " + adaptable);
		}
		return adaptable;
	}

	@Override
	public AdapterWithFlux<String, GeboChatMessageEnvelope> create(
			IGReactiveAgentService<String, GeboChatMessageEnvelope> service) {
		Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink = Sinks.many().unicast().onBackpressureBuffer();
		ReportWriterReactiveAgentServiceNetworkAdapter adapter = new ReportWriterReactiveAgentServiceNetworkAdapter(
				service, sink);
		Flux<GeboChatMessageEnvelope> flux = sink.asFlux().map(IGPartialOperation::getData);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created a report writer network adapter over reactive service id:"
					+ (service != null ? service.getId() : null) + " with a unicast back pressure buffered sink");
		}
		return new AdapterWithFlux<>(adapter, flux, sink);
	}
}
