/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleScope;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRuleProposalService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import lombok.AllArgsConstructor;

@PreAuthorize("hasAnyRole('USER','ADMIN','APPLICATION')")
@RestController
@RequestMapping(path = "api/users/GeboChatRulesController")
@AllArgsConstructor
public class GeboChatRulesController {
	final IGChatRulesService rulesService;
	final IGChatRuleProposalService proposalService;

	@GetMapping(value = "proposeRules", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> proposeRules(@RequestParam("userChatContextCode") String userChatContextCode,
			@RequestParam("requestId") String requestId) throws GeboChatSessionLifecycleException, LLMConfigException {
		return proposalService.proposeRules(userChatContextCode, requestId);
	}

	@PostMapping(value = "createRule", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GChatRule createRule(@RequestBody GChatRule rule) throws GeboChatSessionLifecycleException {
		requireOwnScope(rule);
		return rulesService.createRule(rule);
	}

	@PostMapping(value = "updateRule", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GChatRule updateRule(@RequestBody GChatRule rule) throws GeboChatSessionLifecycleException {
		requireOwnScope(rule);
		return rulesService.updateRule(rule);
	}

	@DeleteMapping("deleteRule")
	public void deleteRule(@RequestParam("id") String id) throws GeboChatSessionLifecycleException {
		rulesService.deleteRule(id);
	}

	@GetMapping(value = "getMyRules", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GChatRule> getMyRules() {
		return rulesService.getMyRules();
	}

	@GetMapping(value = "getChatRules", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GChatRule> getChatRules(@RequestParam("userChatContextCode") String userChatContextCode)
			throws GeboChatSessionLifecycleException {
		return rulesService.getChatRules(userChatContextCode);
	}

	@GetMapping(value = "getSharedRulesAppliedToMe", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GChatRule> getSharedRulesAppliedToMe() {
		return rulesService.getSharedRulesAppliedToMe();
	}

	private static void requireOwnScope(GChatRule rule) {
		if (rule != null && rule.getScope() == ChatRuleScope.SHARED) {
			throw new SecurityException("Shared rules are managed from the administration");
		}
	}
}
