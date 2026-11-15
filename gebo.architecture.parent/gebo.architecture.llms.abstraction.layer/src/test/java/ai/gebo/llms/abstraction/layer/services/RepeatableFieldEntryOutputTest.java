/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * An output with none of the fields is asked for once more, unless a tool ended
 * the call: asked again, the model would call that tool again.
 */
class RepeatableFieldEntryOutputTest {
	private final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
	private final GeboToolCalling toolCalling = mock(GeboToolCalling.class);
	private final BaseLLMSInvokingService service = new BaseLLMSInvokingService();

	private Map<String, List<String>> fields() throws Exception {
		when(model.textResponse(any(), anyMap(), any(), any(GeboToolCalling.class))).thenReturn("no field here");
		return service.callLLMRepeatableFieldEntryOutput(model, null, null, Map.of(), List.of("QUERY"), toolCalling);
	}

	@Test
	void anOutputWithNoFieldIsAskedForOnceMore() throws Exception {
		assertTrue(fields().isEmpty());

		verify(model, times(2)).textResponse(any(), anyMap(), any(), any(GeboToolCalling.class));
	}

	@Test
	void aCallEndedByAToolIsNotMadeAgain() throws Exception {
		when(toolCalling.isStoppedByTool()).thenReturn(true);

		assertTrue(fields().isEmpty());

		verify(model, times(1)).textResponse(any(), anyMap(), any(), any(GeboToolCalling.class));
	}
}
