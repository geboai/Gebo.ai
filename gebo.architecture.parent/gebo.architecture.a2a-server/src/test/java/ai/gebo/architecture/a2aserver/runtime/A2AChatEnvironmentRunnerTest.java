/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatRunAttachments;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;

/**
 * What the A2A side hands to the chat environment runner: the run with what the
 * server adds to its model calls when it adds something, the plain run otherwise.
 */
class A2AChatEnvironmentRunnerTest {
	private final GChatEnvironmentNetworkRunner runner = mock(GChatEnvironmentNetworkRunner.class);
	private final A2AChatEnvironmentRunner a2a = new A2AChatEnvironmentRunner(runner);
	private final GeboChatRequest request = new GeboChatRequest();

	@Test
	void aRunWithAToolCallingHandsItToTheRunner() throws Exception {
		GeboToolCalling toolCalling = GeboToolCalling.builder().caller("a2a:server/skill").build();

		a2a.run("the-network", request, null, toolCalling, null);

		ArgumentCaptor<ChatRunAttachments> attachments = ArgumentCaptor.forClass(ChatRunAttachments.class);
		verify(runner).run(eq("the-network"), same(request), attachments.capture(), same(toolCalling), isNull());
		assertTrue(attachments.getValue().isEmpty(), "nothing was sent along, nothing is attached");
	}

	@Test
	void aRunWithoutOneIsHandedToTheRunnerWhichRefusesIt() throws Exception {
		a2a.run("the-network", request, null, null, null);

		// the runner is the one refusing a run without a container; the bridge always builds one
		verify(runner).run(eq("the-network"), same(request), any(ChatRunAttachments.class), isNull(), isNull());
	}
}
