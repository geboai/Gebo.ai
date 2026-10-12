/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleScope;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRuleChange;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatRuleRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatRulesServiceImpl implements IGChatRulesService {
	static final int MAX_RULE_LENGTH = 500;
	private final ChatRuleRepository repository;
	private final GUserChatSessionRepository sessionRepository;
	private final IGSecurityService securityService;

	@Override
	public GChatRule createRule(GChatRule draft) throws GeboChatSessionLifecycleException {
		if (draft == null || draft.getScope() == null) {
			throw new GeboChatSessionLifecycleException("A rule needs a scope");
		}
		String me = currentUsername();
		GChatRule rule = new GChatRule();
		rule.setId(UUID.randomUUID().toString());
		rule.setScope(draft.getScope());
		rule.setText(validText(draft.getText()));
		rule.setOwnerUsername(me);
		switch (draft.getScope()) {
		case SESSION -> {
			ownedSession(draft.getUserChatContextCode());
			rule.setUserChatContextCode(draft.getUserChatContextCode());
		}
		case USER -> {
		}
		case SHARED -> {
			requireAdmin();
			setAudience(rule, draft);
		}
		}
		if (draft.getSourceUserChatContextCode() != null) {
			ownedSession(draft.getSourceUserChatContextCode());
			rule.setSourceUserChatContextCode(draft.getSourceUserChatContextCode());
			rule.setSourceRequestId(draft.getSourceRequestId());
		}
		rule.setChatProfileCode(blankToNull(draft.getChatProfileCode()));
		rule.setPipelineCode(blankToNull(draft.getPipelineCode()));
		rule.setEnabled(draft.isEnabled());
		Date now = new Date();
		rule.setCreatedAt(now);
		rule.setModifiedAt(now);
		rule.setModifiedBy(me);
		return repository.save(rule);
	}

	@Override
	public GChatRule updateRule(GChatRule changed) throws GeboChatSessionLifecycleException {
		if (changed == null || changed.getId() == null) {
			throw new GeboChatSessionLifecycleException("The rule to update has no id");
		}
		GChatRule rule = editableRule(changed.getId());
		String text = validText(changed.getText());
		if (!Objects.equals(text, rule.getText()) || changed.isEnabled() != rule.isEnabled()) {
			rule.getChanges().add(new GChatRuleChange(new Date(), currentUsername(), rule.getText(), rule.isEnabled()));
		}
		rule.setText(text);
		rule.setEnabled(changed.isEnabled());
		rule.setChatProfileCode(blankToNull(changed.getChatProfileCode()));
		rule.setPipelineCode(blankToNull(changed.getPipelineCode()));
		if (rule.getScope() == ChatRuleScope.SHARED) {
			setAudience(rule, changed);
		}
		rule.setModifiedAt(new Date());
		rule.setModifiedBy(currentUsername());
		return repository.save(rule);
	}

	@Override
	public void deleteRule(String id) throws GeboChatSessionLifecycleException {
		repository.delete(editableRule(id));
	}

	@Override
	public List<GChatRule> copyChatRules(String sourceUserChatContextCode, String targetUserChatContextCode)
			throws GeboChatSessionLifecycleException {
		ownedSession(sourceUserChatContextCode);
		ownedSession(targetUserChatContextCode);
		List<GChatRule> copies = new ArrayList<GChatRule>();
		Date now = new Date();
		for (GChatRule source : repository.findByScopeAndUserChatContextCode(ChatRuleScope.SESSION,
				sourceUserChatContextCode)) {
			GChatRule copy = new GChatRule();
			copy.setId(UUID.randomUUID().toString());
			copy.setScope(ChatRuleScope.SESSION);
			copy.setUserChatContextCode(targetUserChatContextCode);
			copy.setOwnerUsername(source.getOwnerUsername());
			copy.setText(source.getText());
			copy.setEnabled(source.isEnabled());
			copy.setChatProfileCode(source.getChatProfileCode());
			copy.setPipelineCode(source.getPipelineCode());
			copy.setSourceUserChatContextCode(source.getSourceUserChatContextCode());
			copy.setSourceRequestId(source.getSourceRequestId());
			copy.setCreatedAt(now);
			copy.setModifiedAt(now);
			copy.setModifiedBy(currentUsername());
			copies.add(copy);
		}
		return repository.saveAll(copies);
	}

	@Override
	public List<GChatRule> getMyRules() {
		return repository.findByOwnerUsernameAndScopeIn(currentUsername(),
				List.of(ChatRuleScope.SESSION, ChatRuleScope.USER));
	}

	@Override
	public List<GChatRule> getChatRules(String userChatContextCode) throws GeboChatSessionLifecycleException {
		ownedSession(userChatContextCode);
		return repository.findByScopeAndUserChatContextCode(ChatRuleScope.SESSION, userChatContextCode);
	}

	@Override
	public List<GChatRule> getSharedRulesAppliedToMe() {
		return repository.findByScope(ChatRuleScope.SHARED).stream()
				.filter(x -> securityService.isCanAccess(x, false)).toList();
	}

	@Override
	public List<GChatRule> getSharedRules() {
		requireAdmin();
		return repository.findByScope(ChatRuleScope.SHARED);
	}

	@Override
	public List<GChatRule> getApplicableRules(String userChatContextCode, String chatProfileCode,
			String pipelineCode) {
		List<GChatRule> candidates = new ArrayList<GChatRule>(getSharedRulesAppliedToMe());
		candidates.addAll(repository.findByScopeAndOwnerUsername(ChatRuleScope.USER, currentUsername()));
		if (userChatContextCode != null) {
			candidates.addAll(
					repository.findByScopeAndUserChatContextCode(ChatRuleScope.SESSION, userChatContextCode));
		}
		return candidates.stream().filter(GChatRule::isEnabled)
				.filter(x -> x.getChatProfileCode() == null || x.getChatProfileCode().equals(chatProfileCode))
				.filter(x -> x.getPipelineCode() == null || x.getPipelineCode().equals(pipelineCode)).toList();
	}

	private GChatRule editableRule(String id) throws GeboChatSessionLifecycleException {
		GChatRule rule = repository.findById(id)
				.orElseThrow(() -> new GeboChatSessionLifecycleException("Rule " + id + " not found"));
		if (rule.getScope() == ChatRuleScope.SHARED) {
			requireAdmin();
		} else if (!currentUsername().equals(rule.getOwnerUsername())) {
			throw new SecurityException("The actual user is not the owner of this rule");
		}
		return rule;
	}

	private void setAudience(GChatRule rule, GChatRule from) {
		rule.setAccessibleToAll(from.getAccessibleToAll());
		rule.setAccessibleUsers(from.getAccessibleUsers());
		rule.setAccessibleGroups(from.getAccessibleGroups());
	}

	private void requireAdmin() {
		if (!securityService.isCurrentUserAdmin()) {
			throw new SecurityException("Only administrators can manage shared chat rules");
		}
	}

	private GUserChatSession ownedSession(String userChatContextCode) throws GeboChatSessionLifecycleException {
		if (userChatContextCode == null) {
			throw new GeboChatSessionLifecycleException("A session rule needs its chat");
		}
		GUserChatSession session = sessionRepository.findById(userChatContextCode)
				.orElseThrow(() -> new GeboChatSessionLifecycleException("Chat " + userChatContextCode + " not found"));
		securityService.checkBeingCreator(session);
		return session;
	}

	private String currentUsername() {
		return securityService.getCurrentUser().getUsername();
	}

	private static String validText(String text) throws GeboChatSessionLifecycleException {
		if (text == null || text.isBlank()) {
			throw new GeboChatSessionLifecycleException("A rule needs a text");
		}
		String trimmed = text.trim();
		if (trimmed.length() > MAX_RULE_LENGTH) {
			throw new GeboChatSessionLifecycleException(
					"A rule can be at most " + MAX_RULE_LENGTH + " characters long");
		}
		return trimmed;
	}

	private static String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value;
	}
}
