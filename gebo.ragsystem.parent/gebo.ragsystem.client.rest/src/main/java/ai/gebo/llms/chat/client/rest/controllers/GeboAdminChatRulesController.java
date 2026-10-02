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
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import lombok.AllArgsConstructor;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(path = "api/admin/GeboAdminChatRulesController")
@AllArgsConstructor
public class GeboAdminChatRulesController {
	final IGChatRulesService rulesService;

	@PostMapping(value = "createSharedRule", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GChatRule createSharedRule(@RequestBody GChatRule rule) throws GeboChatSessionLifecycleException {
		rule.setScope(ChatRuleScope.SHARED);
		return rulesService.createRule(rule);
	}

	@PostMapping(value = "updateSharedRule", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GChatRule updateSharedRule(@RequestBody GChatRule rule) throws GeboChatSessionLifecycleException {
		return rulesService.updateRule(rule);
	}

	@DeleteMapping("deleteSharedRule")
	public void deleteSharedRule(@RequestParam("id") String id) throws GeboChatSessionLifecycleException {
		rulesService.deleteRule(id);
	}

	@GetMapping(value = "getSharedRules", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GChatRule> getSharedRules() {
		return rulesService.getSharedRules();
	}
}
