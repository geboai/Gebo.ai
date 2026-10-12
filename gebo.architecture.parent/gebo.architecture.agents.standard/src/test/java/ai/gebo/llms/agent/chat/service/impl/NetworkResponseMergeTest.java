/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.model.GUserMessage;

/**
 * Pins the merge of a network answer onto the pipeline response the user receives:
 * the messages the agents give the user with their answer reach it (they were left
 * out), each once.
 */
class NetworkResponseMergeTest {

	@Test
	void theAgentsMessagesReachTheUserOnce() {
		GeboChatResponse network = new GeboChatResponse();
		GUserMessage warning = GUserMessage.warnMessage("Sources not read for this answer", "b.pdf");
		network.getBackendMessages().add(warning);
		GeboChatResponse pipeline = new GeboChatResponse();
		GUserMessage existing = GUserMessage.infoMessage("Already there", "");
		pipeline.getBackendMessages().add(existing);

		ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.mergeBackendMessages(network, pipeline);
		ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.mergeBackendMessages(network, pipeline);

		assertEquals(2, pipeline.getBackendMessages().size());
		assertSame(existing, pipeline.getBackendMessages().get(0));
		assertSame(warning, pipeline.getBackendMessages().get(1));
	}
}
