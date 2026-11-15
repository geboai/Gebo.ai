/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.pipelines.model.ModelUseInfos;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;

class DefaultPipelineStreamingDelegatedStepServiceImplTest {

	@Test
	void theDelegatedStepUsesItsModelsAsItsTargetDoes() {
		final IStreamingOutputChatPipelineService target = mock(IStreamingOutputChatPipelineService.class);
		final ModelUseInfos chat = ModelUseInfos.usedFor("writes");
		final ModelUseInfos service = ModelUseInfos.notUsed();
		when(target.getChatModelUseInfos()).thenReturn(chat);
		when(target.getServiceModelUseInfos()).thenReturn(service);

		final DefaultPipelineStreamingDelegatedStepServiceImpl step = new DefaultPipelineStreamingDelegatedStepServiceImpl(
				target);

		assertSame(chat, step.getChatModelUseInfos());
		assertSame(service, step.getServiceModelUseInfos());
	}

	@Test
	void withNoTargetItUsesNoModel() {
		final DefaultPipelineStreamingDelegatedStepServiceImpl step = new DefaultPipelineStreamingDelegatedStepServiceImpl(
				null);

		assertFalse(step.getChatModelUseInfos().isModelUsed());
		assertFalse(step.getServiceModelUseInfos().isModelUsed());
	}
}
