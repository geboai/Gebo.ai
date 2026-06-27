package ai.gebo.llms.agent.chat.service.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;

public class ChatNetworkDocumentRenderers {
	@Service
	public static class GeboChatMessageEnvelopeRenderer implements IGDocumentContentRenderer<GeboChatMessageEnvelope> {

		private static final String SERVICE_ID = "GeboChatMessageEnvelopeRenderer";

		@Override
		public String getId() {

			return SERVICE_ID;
		}

		@Override
		public Class<GeboChatMessageEnvelope> getRenderedType() {

			return GeboChatMessageEnvelope.class;
		}

		@Override
		public boolean isCanRender(Object document) {
			if (document instanceof GeboChatMessageEnvelope)
				return true;
			return false;
		}

		@Override
		public String render(GeboChatMessageEnvelope document) {
			String content = null;
			if (document.getContent() instanceof GeboChatResponse response) {
				content = response.getQueryResponse();
			} else if (document.getContent() instanceof String response) {
				content = response;
			}
			return content != null ? content : "";
		}

	}

	public static class GeboChatResponseRenderer implements IGDocumentContentRenderer<GeboChatResponse> {

		private static final String SERVICE_ID = "GeboChatResponseRenderer";

		@Override
		public String getId() {

			return SERVICE_ID;
		}

		@Override
		public Class<GeboChatResponse> getRenderedType() {

			return GeboChatResponse.class;
		}

		@Override
		public boolean isCanRender(Object document) {
			if (document instanceof GeboChatResponse)
				return true;
			return false;
		}

		@Override
		public String render(GeboChatResponse response) {
			String content = null;

			content = response.getQueryResponse();

			return content != null ? content : "";
		}

	}
}
