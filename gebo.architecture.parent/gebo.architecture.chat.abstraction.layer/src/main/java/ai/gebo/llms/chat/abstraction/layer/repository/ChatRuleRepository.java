/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleScope;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;

public interface ChatRuleRepository extends MongoRepository<GChatRule, String> {
	public List<GChatRule> findByScope(ChatRuleScope scope);

	public List<GChatRule> findByScopeAndOwnerUsername(ChatRuleScope scope, String ownerUsername);

	public List<GChatRule> findByScopeAndUserChatContextCode(ChatRuleScope scope, String userChatContextCode);

	public List<GChatRule> findByOwnerUsernameAndScopeIn(String ownerUsername, List<ChatRuleScope> scopes);
}
