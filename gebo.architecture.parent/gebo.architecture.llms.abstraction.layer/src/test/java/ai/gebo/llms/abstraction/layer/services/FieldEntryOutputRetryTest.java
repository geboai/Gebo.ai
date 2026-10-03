/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Pins the second call of a field-parsed output that gave none of its fields: every
 * caller (routing, request rewriting, search planning...) otherwise falls back on a
 * default whatever the request asked.
 */
class FieldEntryOutputRetryTest {

	/** A service whose model writes the scripted outputs, one per call. */
	static class ScriptedService extends BaseLLMSInvokingService {
		final List<String> outputs;
		int calls = 0;

		ScriptedService(String... outputs) {
			this.outputs = new ArrayList<>(List.of(outputs));
		}

		@Override
		protected String callLLM(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
				IChatRequestContext context, Map<String, Object> params) {
			return outputs.get(calls++);
		}

		Map<String, List<String>> parse(String... fields) throws Exception {
			return callLLMRepeatableFieldEntryOutput(null, new GPromptTemplateConfig(), null, Map.of(), List.of(fields));
		}
	}

	@Test
	void anOutputWithoutAnyFieldIsAskedOnceMore() throws Exception {
		ScriptedService service = new ScriptedService("I think it is a report.",
				"deliverable=ANALISYS\nrewrittenQuery=a report on the contracts");

		Map<String, List<String>> fields = service.parse("deliverable", "rewrittenQuery");

		assertEquals(2, service.calls);
		assertEquals(List.of("ANALISYS"), fields.get("deliverable"));
		assertEquals(List.of("a report on the contracts"), fields.get("rewrittenQuery"));
	}

	@Test
	void anOutputWithSomeOfTheFieldsIsKept() throws Exception {
		ScriptedService service = new ScriptedService("routingDecision=DELEGATED_AGENT", "never asked");

		Map<String, List<String>> fields = service.parse("routingDecision", "deepSearchedSystems");

		assertEquals(1, service.calls, "an optional field missing is no failure");
		assertEquals(List.of("DELEGATED_AGENT"), fields.get("routingDecision"));
	}

	@Test
	void theRetryIsMadeOnceOnly() throws Exception {
		ScriptedService service = new ScriptedService("nothing", "still nothing", "never asked");

		assertTrue(service.parse("deliverable").isEmpty(), "the caller falls back");
		assertEquals(2, service.calls);
	}
}
