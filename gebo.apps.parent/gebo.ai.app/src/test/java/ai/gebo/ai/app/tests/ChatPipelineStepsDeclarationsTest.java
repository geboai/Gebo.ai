/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.llms.chat.pipelines.model.ModelUseInfos;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;

/** What the registered steps declare, which the admin configures their tools from. */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true" })
public class ChatPipelineStepsDeclarationsTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired
	private List<IChatPipelineStepService> steps;
	@Autowired
	private List<IRoutingChatPipelineStepService> routers;

	private static void assertDescribed(String role, IChatPipelineStepService step, ModelUseInfos infos) {
		assertNotNull(infos, step.getStepId() + " declares its " + role + " model");
		if (infos.isModelUsed()) {
			assertNotNull(infos.getModelUseDescription(), step.getStepId() + " says what its " + role + " model does");
			assertFalse(infos.getModelUseDescription().isBlank());
		}
	}

	@Test
	void everyStepDeclaresItsModels() {
		assertFalse(steps.isEmpty());
		for (IChatPipelineStepService step : steps) {
			assertDescribed("chat", step, step.getChatModelUseInfos());
			assertDescribed("service", step, step.getServiceModelUseInfos());
		}
	}

	@Test
	void everyRouterDeclaresRegisteredSteps() {
		assertFalse(routers.isEmpty());
		final Set<String> registered = steps.stream().map(IChatPipelineStepService::getStepId)
				.collect(Collectors.toSet());
		for (IRoutingChatPipelineStepService router : routers) {
			assertFalse(router.getReachableStepIds().isEmpty(), router.getStepId() + " reaches some step");
			for (String stepId : router.getReachableStepIds()) {
				assertTrue(registered.contains(stepId), router.getStepId() + " reaches the unknown step " + stepId);
			}
		}
	}
}
