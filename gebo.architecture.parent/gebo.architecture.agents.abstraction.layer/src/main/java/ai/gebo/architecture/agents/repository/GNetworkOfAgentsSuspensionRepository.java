/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

/** The suspended runs of the networks of agents, each the cut it was suspended at. */
public interface GNetworkOfAgentsSuspensionRepository extends IGBaseMongoDBRepository<GNetworkOfAgentsSuspension> {
	@Override
	default Class<GNetworkOfAgentsSuspension> getManagedType() {
		return GNetworkOfAgentsSuspension.class;
	}

	List<GNetworkOfAgentsSuspension> findByNetworkCode(String networkCode);

	List<GNetworkOfAgentsSuspension> findByCollaborationSessionId(String collaborationSessionId);

	/** The blocks of a conversation still to be answered: neither respawned nor dropped. */
	List<GNetworkOfAgentsSuspension> findByConversationCodeAndRespawnedAtIsNullAndDroppedAtIsNull(
			String conversationCode);

	/** Every block still to be answered, of every conversation: what the waiting conditions panel lists. */
	List<GNetworkOfAgentsSuspension> findByRespawnedAtIsNullAndDroppedAtIsNull();

	/** The open blocks with a condition past its expiry at the moment given. */
	List<GNetworkOfAgentsSuspension> findByEarliestExpiryAtLessThanEqualAndRespawnedAtIsNullAndDroppedAtIsNull(
			Date dueAt);

	/** The blocks that suspended unwatched, whose owner's message was not taken back yet: the open and the closed. */
	List<GNetworkOfAgentsSuspension> findByUnwatchedTrueAndMessagesRetractedAtIsNull();

	/** The blocks with an expiry, whose owner's message was not taken back yet: the open and the closed. */
	List<GNetworkOfAgentsSuspension> findByEarliestExpiryAtIsNotNullAndMessagesRetractedAtIsNull();

	/** The open block of a conversation, which holds one at most; the newest when it holds more. */
	default Optional<GNetworkOfAgentsSuspension> findOpenByConversationCode(String conversationCode) {
		if (conversationCode == null) {
			return Optional.empty();
		}
		return findByConversationCodeAndRespawnedAtIsNullAndDroppedAtIsNull(conversationCode).stream()
				.filter(block -> block != null).max((a, b) -> {
					final long ta = a.getSuspendedAt() != null ? a.getSuspendedAt().getTime() : 0L;
					final long tb = b.getSuspendedAt() != null ? b.getSuspendedAt().getTime() : 0L;
					return Long.compare(ta, tb);
				});
	}
}
