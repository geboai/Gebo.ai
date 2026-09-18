package ai.gebo.llms.agent.chat.service.impl;

import java.util.List;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AbstractReactiveAgentServiceNetworkAdapter;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import reactor.core.publisher.Sinks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportWriterReactiveAgentServiceNetworkAdapter
		extends AbstractReactiveAgentServiceNetworkAdapter<String, GeboChatMessageEnvelope> {
	private static final Logger ADAPTER_LOGGER = LoggerFactory
			.getLogger(ReportWriterReactiveAgentServiceNetworkAdapter.class);

	public ReportWriterReactiveAgentServiceNetworkAdapter(
			IGReactiveAgentService<String, GeboChatMessageEnvelope> service,
			Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink) {
		super(service, String.class, GeboChatMessageEnvelope.class, sink);
	}

	@Override
	protected GeboChatMessageEnvelope extractResponse(List<IGPartialOperation<GeboChatMessageEnvelope>> buffered) {
		GeboChatMessageEnvelope lastValidResponse = null;
		int validResponses = 0;
		if (ADAPTER_LOGGER.isDebugEnabled()) {
			ADAPTER_LOGGER.debug("Begin extractResponse(...) scanning "
					+ (buffered != null ? buffered.size() : 0) + " buffered partial(s) for the final chat response");
		}
		for (IGPartialOperation<GeboChatMessageEnvelope> entry : buffered) {
			if (entry.getData() != null && entry.getData().getContent() instanceof GeboChatResponse) {
				lastValidResponse = entry.getData();
				validResponses++;
			}
		}
		if (ADAPTER_LOGGER.isDebugEnabled()) {
			ADAPTER_LOGGER.debug("End extractResponse(...) found " + validResponses
					+ " chat response partial(s), keeping the last one:" + (lastValidResponse != null));
		}
		if (ADAPTER_LOGGER.isTraceEnabled()) {
			ADAPTER_LOGGER.trace("<REPORT_WRITER_FINAL_RESPONSE>");
			ADAPTER_LOGGER.trace(String.valueOf(lastValidResponse != null ? lastValidResponse.getContent() : null));
			ADAPTER_LOGGER.trace("</REPORT_WRITER_FINAL_RESPONSE>");
		}
		return lastValidResponse;
	}

}
