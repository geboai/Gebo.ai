/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.agents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Office variant of the report/answer writer. It reuses the entire standard writer
 * (prompt handling, token-budget windowing, evidence and tool-call rendering) and
 * only reshapes the produced stream: the office writer prompt is instructed to
 * wrap document content meant for the editor in a {@code <GEBO-DOCUMENT>} escape.
 * {@link OfficeDocumentStreamSplitter} isolates that content so only the
 * user-facing chat text is streamed to the chat, while the document part is
 * attached to {@link GeboChatResponse#setAdditionalContent} for the office plugin
 * to insert into the document the user is working on.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Service
public class OfficeReportWriterReactiveAgentService extends ReportWriterReactiveAgentServiceImpl {

	public static final String OFFICE_REPORT_WRITER_NETWORK_AGENT_SERVICE = "OfficeReportWriterNetworkAgentService";
	private static final String DESCRIPTION = "Office report/answer writer that isolates a document part (for the editor) from the chat answer";
	private static final Logger LOGGER = LoggerFactory.getLogger(OfficeReportWriterReactiveAgentService.class);

	public OfficeReportWriterReactiveAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);
	}

	@Override
	public String getId() {
		return OFFICE_REPORT_WRITER_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Write the chat answer and, separately, the document content to insert into the user's office document");
		return capabilities;
	}

	@Override
	protected Flux<IGPartialOperation<GeboChatMessageEnvelope>> renderOutputStream(Flux<String> textStream,
			GeboChatResponse response, AgentsCollaborationSessionContext session,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			ToolCallsListener callBacksListener) {
		final OfficeDocumentStreamSplitter splitter = new OfficeDocumentStreamSplitter();
		final StringBuffer chatContent = new StringBuffer();

		Flux<IGPartialOperation<GeboChatMessageEnvelope>> bodyStream = textStream.map(chunk -> {
			String chatText = splitter.accept(chunk);
			chatContent.append(chatText);
			return IGPartialOperation.of(new GeboChatMessageEnvelope(chatText), false);
		});

		Flux<IGPartialOperation<GeboChatMessageEnvelope>> lastItem = Flux.defer(() -> {
			String tail = splitter.complete();
			chatContent.append(tail);
			boolean lastMessage = false;
			notificationSink.next("Agent: " + contextAgentPersona.getNetworkAgentName() + " has finished",
					ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType.INFO);
			response.setQueryResponse(chatContent.toString());
			response.setDocumentsRef(documentsList(session));
			response.setCalledFunctions(calledFunctions(callBacksListener));
			if (splitter.hasDocuments()) {
				response.setAdditionalContent(splitter.getDocuments());
			}
			GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
			envelope.setLastMessage(lastMessage);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Office writer finished: chat length {}, document part(s) {}", chatContent.length(),
						splitter.getDocuments().size());
			}
			GeboChatMessageEnvelope tailEnvelope = new GeboChatMessageEnvelope(tail);
			return Flux.just(IGPartialOperation.of(tailEnvelope, false),
					IGPartialOperation.of(envelope, lastMessage));
		});
		return Flux.concat(bodyStream, lastItem);
	}
}
