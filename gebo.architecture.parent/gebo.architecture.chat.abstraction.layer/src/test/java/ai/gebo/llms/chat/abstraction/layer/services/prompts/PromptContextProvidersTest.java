/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.prompts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GChatRunInfo;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;

class PromptContextProvidersTest {

	@Test
	void theRunProviderSaysWhereTheRunComesFromAndWhetherSomebodyWaits() {
		IChatRequestContext context = IChatRequestContext.builder().actualUserRequest("q")
				.runInfo(GChatRunInfo.of("HARNESS", Boolean.FALSE))
				.pipelineInfos(Map.of("user-intent", DeliverableIntent.ANALISYS.name())).build();

		Map<String, Object> values = new GRunPromptContextProvider().valuesFor(
				Set.of("runOrigin", "attended", "agentDeliverableCompleteness"), context);

		assertEquals("HARNESS", values.get("runOrigin"));
		assertEquals("no", values.get("attended"));
		assertEquals(DeliverableIntent.ANALISYS.getAgentDeliverableCompleteness(),
				values.get("agentDeliverableCompleteness"));
	}

	@Test
	void aRunNothingIsKnownOfIsAnAttendedOneOfUnknownOrigin() {
		Map<String, Object> values = new GRunPromptContextProvider().valuesFor(Set.of("runOrigin", "attended"),
				IChatRequestContext.of("q"));

		assertEquals("UNKNOWN", values.get("runOrigin"));
		assertEquals("yes", values.get("attended"));
	}

	@Test
	void theTimeProviderFillsWhatIsAskedOnly() {
		Map<String, Object> values = new GTimePromptContextProvider().valuesFor(Set.of("currentDate"), null);

		assertEquals(Set.of("currentDate"), values.keySet());
		assertTrue(String.valueOf(values.get("currentDate")).matches("\\d{4}-\\d{2}-\\d{2}"));
	}

	@Test
	void theUserProviderNamesNobodyUnlessTheInstallationTurnsItOn() {
		IGSecurityService security = mock(IGSecurityService.class);

		Map<String, Object> off = new GCurrentUserPromptContextProvider(security, false)
				.valuesFor(Set.of("currentUserName", "currentUserGroups"), null);

		assertEquals(GCurrentUserPromptContextProvider.NOBODY, off.get("currentUserName"));
		assertEquals("", off.get("currentUserGroups"));
		verify(security, never()).getCurrentUser();

		UserInfos user = mock(UserInfos.class);
		when(user.getName()).thenReturn("Ada");
		when(user.getSourname()).thenReturn("Lovelace");
		when(security.getCurrentUser()).thenReturn(user);
		Map<String, Object> on = new GCurrentUserPromptContextProvider(security, true)
				.valuesFor(Set.of("currentUserName"), null);

		assertEquals("Ada Lovelace", on.get("currentUserName"));
	}
}
