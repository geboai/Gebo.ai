/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.services.impl;

import java.time.Clock;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.model.GSystemMessageDismissal;
import ai.gebo.system.messages.model.SystemMessageAudience;
import ai.gebo.system.messages.repositories.SystemMessageDismissalRepository;
import ai.gebo.system.messages.repositories.SystemMessageRepository;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import ai.gebo.system.messages.services.SystemMessagePublication;

/**
 * Default {@link IGSystemMessagesService}, over the Mongo repositories.
 */
@Service
public class GSystemMessagesServiceImpl implements IGSystemMessagesService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final List<SystemMessageAudience> ADMINISTRATOR_AUDIENCES = List.of(SystemMessageAudience.ADMINS,
			SystemMessageAudience.ALL);
	private static final List<SystemMessageAudience> USER_AUDIENCES = List.of(SystemMessageAudience.USERS,
			SystemMessageAudience.ALL);
	/** error first, then warn, info, success. */
	private static final List<MsgServerity> SEVERITY_ORDER = List.of(MsgServerity.error, MsgServerity.warn,
			MsgServerity.info, MsgServerity.success);

	private final SystemMessageRepository messageRepository;
	private final SystemMessageDismissalRepository dismissalRepository;
	private final Clock clock;

	@Autowired
	public GSystemMessagesServiceImpl(SystemMessageRepository messageRepository,
			SystemMessageDismissalRepository dismissalRepository) {
		this(messageRepository, dismissalRepository, Clock.systemUTC());
	}

	GSystemMessagesServiceImpl(SystemMessageRepository messageRepository,
			SystemMessageDismissalRepository dismissalRepository, Clock clock) {
		this.messageRepository = messageRepository;
		this.dismissalRepository = dismissalRepository;
		this.clock = clock;
	}

	@Override
	public GSystemMessage publish(SystemMessagePublication publication) {
		validate(publication);
		String id = GSystemMessage.idOf(publication.source(), publication.key());
		Date now = Date.from(clock.instant());
		Optional<GSystemMessage> existing = messageRepository.findById(id);
		if (existing.isPresent() && sameContent(existing.get(), publication)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("publish(" + id + ") unchanged, revision " + existing.get().getRevision() + " kept");
			}
			return existing.get();
		}
		GSystemMessage message = existing.orElseGet(GSystemMessage::new);
		message.setId(id);
		message.setSource(publication.source());
		message.setKey(publication.key());
		message.setSeverity(publication.severity());
		message.setSummary(publication.summary());
		message.setDetail(publication.detail());
		message.setAudience(publication.audience());
		message.setDismissible(publication.dismissible());
		message.setExpiresAt(publication.expiresAt());
		message.setRevision(existing.map(m -> m.getRevision() + 1).orElse(1L));
		if (message.getCreatedAt() == null) {
			message.setCreatedAt(now);
		}
		message.setUpdatedAt(now);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("publish(" + id + ") audience=" + message.getAudience() + " severity="
					+ message.getSeverity() + " revision=" + message.getRevision());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("publish(" + id + ") content: " + message);
		}
		return messageRepository.save(message);
	}

	private static void validate(SystemMessagePublication publication) {
		Objects.requireNonNull(publication, "publication");
		if (isBlank(publication.source()) || isBlank(publication.key()) || isBlank(publication.summary())
				|| publication.severity() == null || publication.audience() == null) {
			throw new IllegalArgumentException(
					"A system message needs a source, a key, a severity, a summary and an audience");
		}
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}

	private static boolean sameContent(GSystemMessage message, SystemMessagePublication publication) {
		return message.getSeverity() == publication.severity()
				&& Objects.equals(message.getSummary(), publication.summary())
				&& Objects.equals(message.getDetail(), publication.detail())
				&& message.getAudience() == publication.audience()
				&& message.isDismissible() == publication.dismissible()
				&& Objects.equals(message.getExpiresAt(), publication.expiresAt());
	}

	@Override
	public void retract(String source, String key) {
		String id = GSystemMessage.idOf(source, key);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("retract(" + id + ")");
		}
		messageRepository.deleteById(id);
		dismissalRepository.deleteByMessageId(id);
	}

	@Override
	public List<GSystemMessage> findVisibleFor(String username, boolean administrator) {
		Date now = Date.from(clock.instant());
		Map<String, Long> dismissedRevisions = dismissalRepository.findByUsername(username).stream().collect(
				Collectors.toMap(GSystemMessageDismissal::getMessageId, GSystemMessageDismissal::getRevision,
						Math::max));
		List<GSystemMessage> visible = messageRepository.findByAudienceIn(audiencesOf(administrator)).stream()
				.filter(m -> m.getExpiresAt() == null || m.getExpiresAt().after(now))
				.filter(m -> !m.isDismissible() || dismissedRevisions.getOrDefault(m.getId(), 0L) < m.getRevision())
				.sorted(Comparator.comparing((Function<GSystemMessage, Integer>) m -> severityRank(m.getSeverity()))
						.thenComparing(GSystemMessage::getUpdatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
				.toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findVisibleFor(" + username + ", administrator=" + administrator + ") -> " + visible.size()
					+ " messages");
		}
		return visible;
	}

	private static int severityRank(MsgServerity severity) {
		int rank = SEVERITY_ORDER.indexOf(severity);
		return rank < 0 ? SEVERITY_ORDER.size() : rank;
	}

	private static List<SystemMessageAudience> audiencesOf(boolean administrator) {
		return administrator ? ADMINISTRATOR_AUDIENCES : USER_AUDIENCES;
	}

	@Override
	public boolean dismiss(String username, boolean administrator, String messageId) {
		Optional<GSystemMessage> message = messageRepository.findById(messageId);
		if (message.isEmpty() || !message.get().isDismissible()
				|| !audiencesOf(administrator).contains(message.get().getAudience())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("dismiss(" + username + ", " + messageId + ") refused: "
						+ (message.isEmpty() ? "no such message"
								: !message.get().isDismissible() ? "not dismissible" : "not routed to the user"));
			}
			return false;
		}
		GSystemMessageDismissal dismissal = new GSystemMessageDismissal();
		dismissal.setId(GSystemMessageDismissal.idOf(username, messageId));
		dismissal.setUsername(username);
		dismissal.setMessageId(messageId);
		dismissal.setRevision(message.get().getRevision());
		dismissal.setDismissedAt(Date.from(clock.instant()));
		dismissalRepository.save(dismissal);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dismiss(" + username + ", " + messageId + ") at revision " + dismissal.getRevision());
		}
		return true;
	}

	@Override
	public List<GSystemMessage> findAll() {
		return messageRepository.findAll();
	}
}
