/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.pipelines.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.services.UserLanguageDetection;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IGRequestUnderstandingService;
import ai.gebo.llms.chat.pipelines.service.IGUserRequestIntentClassifier;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.system.ingestion.IGLanguageDetector;

/**
 * The request understanding of the chat pipelines, offered to the runs from
 * outside them: the language detection the pipelines executor runs on every
 * request, and the rewrite and intent classification of the default routing
 * step ({@link IGUserRequestIntentClassifier}), found where it is deployed.
 */
@Service
public class GRequestUnderstandingServiceImpl implements IGRequestUnderstandingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GRequestUnderstandingServiceImpl.class);

	/**
	 * The platform's language detector (the one the ingestion tags the documents
	 * with); nothing where it is not deployed, the prompts then ask the model to
	 * deduce the language.
	 */
	private final ObjectProvider<IGLanguageDetector> languageDetector;
	private final IGRuntimeBinder runtimeBinder;

	public GRequestUnderstandingServiceImpl(ObjectProvider<IGLanguageDetector> languageDetector,
			IGRuntimeBinder runtimeBinder) {
		this.languageDetector = languageDetector;
		this.runtimeBinder = runtimeBinder;
	}

	@Override
	public void detectLanguage(GeboChatRequest request) {
		if (request == null || request.getUserLanguage() != null) {
			return;
		}
		final IGLanguageDetector detector = languageDetector != null ? languageDetector.getIfAvailable() : null;
		request.setUserLanguage(UserLanguageDetection.of(detector, request.getQuery()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Request " + request.getId() + " user's language:" + request.getUserLanguage()
					+ (detector == null ? " (no language detector deployed)" : ""));
		}
	}

	@Override
	public DeliverableIntent understand(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, INotificationSink sink)
			throws ChatPipelineException {
		final IGUserRequestIntentClassifier classifier = runtimeBinder
				.getImplementationOf(IGUserRequestIntentClassifier.class);
		if (classifier == null) {
			LOGGER.debug("No user request intent classifier is deployed: the request is taken as it is");
			return null;
		}
		final DeliverableIntent intent = classifier.classifyUserRequest(runtimeData, emitterOf(sink), chatModel,
				serviceModel);
		if (LOGGER.isDebugEnabled()) {
			final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
			LOGGER.debug("Request " + request.getId() + " understood: intent:" + intent + " rewritten:"
					+ (request.getRewrittenQuery() != null) + " searchRequested:" + request.getSearchRequested()
					+ " searchForbidden:" + request.getSearchForbidden() + " language:" + request.getUserLanguage());
		}
		return intent;
	}

	/**
	 * The UI emitter the classifier tells its progress to, over the sink of a run
	 * that has no chat UI: the notifications reach the sink, the chat frames go
	 * nowhere, since no chat shows them.
	 */
	static ISinkUIEmitter emitterOf(INotificationSink sink) {
		return new ISinkUIEmitter() {
			@Override
			public void notifyUser(String code, String message, String icon, Long duration,
					ChatNotificationContent.NotificationType notificationType) {
				if (sink == null) {
					return;
				}
				NotificationType type = NotificationType.INFO;
				try {
					type = NotificationType.valueOf(notificationType != null ? notificationType.name() : "INFO");
				} catch (IllegalArgumentException unknown) {
					// an info, then
				}
				sink.next(new NotificationObject(code, message, icon, type));
			}

			@Override
			public void next(GeboChatMessageEnvelope event) {
				// no chat shows the frames of this run
			}

			@Override
			public void error(Throwable error) {
				LOGGER.debug("The request understanding of a run from outside a chat reported an error", error);
			}

			@Override
			public void complete() {
			}
		};
	}
}
