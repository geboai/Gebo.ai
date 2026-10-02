/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.model.ChatAnswerFeedbackRating;
import ai.gebo.llms.chat.abstraction.layer.model.GChatAnswerFeedback;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatAnswerFeedbackRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAnswerFeedbackService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatAnswerFeedbackServiceImpl implements IGChatAnswerFeedbackService {
	static final int MAX_COMMENT_LENGTH = 2000;
	private final ChatAnswerFeedbackRepository repository;
	private final GUserChatSessionRepository sessionRepository;
	private final IGSecurityService securityService;

	@Override
	public GChatAnswerFeedback setFeedback(String userChatContextCode, String requestId,
			ChatAnswerFeedbackRating rating, String comment) throws GeboChatSessionLifecycleException {
		if (rating == null) {
			throw new GeboChatSessionLifecycleException("A feedback needs a rating");
		}
		GUserChatSession session = ownedSession(userChatContextCode);
		boolean answered = session.getInteractions() != null && session.getInteractions().stream()
				.anyMatch(x -> x.getRequest() != null && requestId != null && requestId.equals(x.getRequest().getId())
						&& x.getResponse() != null);
		if (!answered) {
			throw new GeboChatSessionLifecycleException(
					"Request " + requestId + " is not an answered request of the chat " + userChatContextCode);
		}
		String id = GChatAnswerFeedback.idOf(userChatContextCode, requestId);
		GChatAnswerFeedback feedback = repository.findById(id).orElseGet(GChatAnswerFeedback::new);
		Date now = new Date();
		if (feedback.getId() == null) {
			feedback.setId(id);
			feedback.setCreatedAt(now);
		}
		feedback.setUserChatContextCode(userChatContextCode);
		feedback.setRequestId(requestId);
		feedback.setUsername(securityService.getCurrentUser().getUsername());
		feedback.setRating(rating);
		feedback.setComment(normalized(comment));
		feedback.setChatProfileCode(session.getChatProfileCode());
		feedback.setChatModelCode(session.getChatModelCode());
		feedback.setPipelineCode(session.getPipelineCode());
		feedback.setModifiedAt(now);
		return repository.save(feedback);
	}

	@Override
	public void removeFeedback(String userChatContextCode, String requestId) throws GeboChatSessionLifecycleException {
		ownedSession(userChatContextCode);
		repository.deleteById(GChatAnswerFeedback.idOf(userChatContextCode, requestId));
	}

	@Override
	public List<GChatAnswerFeedback> getChatFeedbacks(String userChatContextCode)
			throws GeboChatSessionLifecycleException {
		ownedSession(userChatContextCode);
		return repository.findByUserChatContextCode(userChatContextCode);
	}

	private GUserChatSession ownedSession(String userChatContextCode) throws GeboChatSessionLifecycleException {
		if (userChatContextCode == null) {
			throw new GeboChatSessionLifecycleException("session id is null");
		}
		GUserChatSession session = sessionRepository.findById(userChatContextCode)
				.orElseThrow(() -> new GeboChatSessionLifecycleException("Chat " + userChatContextCode + " not found"));
		securityService.checkBeingCreator(session);
		return session;
	}

	private static String normalized(String comment) {
		if (comment == null || comment.isBlank()) {
			return null;
		}
		String trimmed = comment.trim();
		return trimmed.length() > MAX_COMMENT_LENGTH ? trimmed.substring(0, MAX_COMMENT_LENGTH) : trimmed;
	}
}
