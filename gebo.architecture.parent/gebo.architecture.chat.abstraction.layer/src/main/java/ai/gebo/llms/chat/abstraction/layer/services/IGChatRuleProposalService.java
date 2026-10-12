/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleConflict;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;

public interface IGChatRuleProposalService {

	// Candidate rule texts drawn from that answer, its feedback and the recent history; nothing is saved.
	public List<String> proposeRules(String userChatContextCode, String requestId)
			throws GeboChatSessionLifecycleException, LLMConfigException;

	// Advice before saving: the enabled rules the given new or edited rule would contradict.
	public List<ChatRuleConflict> checkRuleConflicts(GChatRule candidate)
			throws GeboChatSessionLifecycleException, LLMConfigException;
}
