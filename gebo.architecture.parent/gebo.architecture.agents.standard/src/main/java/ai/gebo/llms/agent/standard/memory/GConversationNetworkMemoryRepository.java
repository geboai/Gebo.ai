/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.memory;

import java.util.Optional;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface GConversationNetworkMemoryRepository extends IGBaseMongoDBRepository<GConversationNetworkMemory> {

	@Override
	default Class<GConversationNetworkMemory> getManagedType() {
		return GConversationNetworkMemory.class;
	}

	public Optional<GConversationNetworkMemory> findByConversationCodeAndNetworkCode(String conversationCode,
			String networkCode);

	public long deleteByConversationCode(String conversationCode);
}
