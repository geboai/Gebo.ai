/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.ToolCallConfirmationOptions;
import ai.gebo.architecture.agents.services.GWaitExternalConditionSink;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.agents.services.ResolvedConditionText;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * Pins the tools an agent runs only once a person allows the call: put to a person as a
 * question with the tool and its arguments, not run; on the respawn an allowed call with
 * the same arguments (whatever the order of their keys) runs, a refused one returns the
 * person's message, other arguments are asked again; where nobody can be asked nothing
 * runs; the model is told the outcome.
 */
class ConfirmingToolCallbackTest {

	final AtomicInteger runs = new AtomicInteger();

	final ToolCallback deleteDocument = new ToolCallback() {
		@Override
		public ToolDefinition getToolDefinition() {
			return ToolDefinition.builder().name("deleteDocument").description("Deletes a document.")
					.inputSchema("{}").build();
		}

		@Override
		public String call(String toolInput) {
			runs.incrementAndGet();
			return "deleted";
		}
	};

	static ToolContext contextWaitingIn(GWaitExternalConditionSink wait) {
		return new ToolContext(GeboToolCalling.builder().provide(IGWaitExternalCondition.class, wait).build()
				.toolsContext(Map.of()));
	}

	static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> answered(String arguments,
			ClarificationAnswer answer) {
		ToolCallConfirmationOptions options = new ToolCallConfirmationOptions();
		options.setToolName("deleteDocument");
		options.setArguments(arguments);
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Allow?", null, answer, true, ClarificationOptions.class, options);
	}

	@Test
	void aCallNobodyDecidedIsPutToAPersonAndDoesNotRun() {
		GWaitExternalConditionSink wait = new GWaitExternalConditionSink();
		ConfirmingToolCallback gated = new ConfirmingToolCallback(deleteDocument, ToolCallConfirmations.NONE);

		String result = gated.call("{\"id\":\"doc-1\"}", contextWaitingIn(wait));

		assertEquals(ConfirmingToolCallback.WAITING, result);
		assertEquals(0, runs.get(), "nothing runs before a person allows it");
		assertTrue(wait.endsTheCall(), "the call ends: the network suspends");
		AgentSuspendedWaitingCondition<?, ?> condition = wait.waited().get(0);
		ToolCallConfirmationOptions options = assertInstanceOf(ToolCallConfirmationOptions.class,
				condition.getLlmChosenOptions(), "asked as any question of the agents");
		assertEquals("deleteDocument", options.getToolName());
		assertEquals("{\"id\":\"doc-1\"}", options.getArguments());
		assertEquals(List.of(ToolCallConfirmationOptions.ALLOW, ToolCallConfirmationOptions.DENY), options.labels());
		assertTrue(options.isCustomAnswerAllowed(), "room for a message to the model");
		assertTrue(condition.getWaitingConditionStatement().contains("{\"id\":\"doc-1\"}"),
				"the person sees the arguments");
		assertTrue(condition.isMandatory());
		assertTrue(condition.isHumanInTheLoopProcessTreatment());
		assertSame(ClarificationAnswer.class, condition.getType());
		assertTrue(gated.getToolDefinition().description().endsWith(ConfirmingToolCallback.NEEDS_CONFIRMATION),
				"the model knows before calling");
	}

	@Test
	void anAllowedCallRunsWithTheSameArgumentsInAnyKeyOrder() {
		ToolCallConfirmations decided = ToolCallConfirmations.of(List
				.of(answered("{\"id\":\"doc-1\",\"force\":true}", ClarificationAnswer.option(ToolCallConfirmationOptions.ALLOW))));
		GWaitExternalConditionSink wait = new GWaitExternalConditionSink();

		String result = new ConfirmingToolCallback(deleteDocument, decided).call("{\"force\":true, \"id\":\"doc-1\"}",
				contextWaitingIn(wait));

		assertEquals("deleted", result);
		assertEquals(1, runs.get());
		assertTrue(wait.waited().isEmpty(), "nothing asked again");
	}

	@Test
	void aRefusedCallReturnsThePersonsMessageAndDoesNotRun() {
		ClarificationAnswer deny = ClarificationAnswer.option(ToolCallConfirmationOptions.DENY);
		deny.setCustomExplanation("Archive it instead");
		ToolCallConfirmations decided = ToolCallConfirmations.of(List.of(answered("{\"id\":\"doc-1\"}", deny)));

		String result = new ConfirmingToolCallback(deleteDocument, decided).call("{\"id\":\"doc-1\"}",
				contextWaitingIn(new GWaitExternalConditionSink()));

		assertEquals(0, runs.get());
		assertTrue(result.contains("refused"), result);
		assertTrue(result.contains("Archive it instead"), result);
	}

	@Test
	void aCallWithOtherArgumentsIsAskedAgain() {
		ToolCallConfirmations decided = ToolCallConfirmations.of(
				List.of(answered("{\"id\":\"doc-1\"}", ClarificationAnswer.option(ToolCallConfirmationOptions.ALLOW))));
		GWaitExternalConditionSink wait = new GWaitExternalConditionSink();

		assertEquals(ConfirmingToolCallback.WAITING,
				new ConfirmingToolCallback(deleteDocument, decided).call("{\"id\":\"doc-2\"}", contextWaitingIn(wait)));
		assertEquals(0, runs.get());
		assertEquals(1, wait.waited().size());
	}

	@Test
	void aConfirmationLeftUnansweredRefusesTheCall() {
		ToolCallConfirmations decided = ToolCallConfirmations.of(List.of(answered("{\"id\":\"doc-1\"}", null)));

		new ConfirmingToolCallback(deleteDocument, decided).call("{\"id\":\"doc-1\"}",
				contextWaitingIn(new GWaitExternalConditionSink()));

		assertEquals(0, runs.get());
	}

	@Test
	void whereNobodyCanBeAskedNothingRuns() {
		ConfirmingToolCallback gated = new ConfirmingToolCallback(deleteDocument, ToolCallConfirmations.NONE);

		assertEquals(ConfirmingToolCallback.NOT_AVAILABLE, gated.call("{\"id\":\"doc-1\"}", null));
		assertEquals(ConfirmingToolCallback.NOT_AVAILABLE,
				gated.call("{\"id\":\"doc-1\"}", new ToolContext(Map.of())));
		assertEquals(0, runs.get());
	}

	@Test
	void theDecoratorWrapsOnlyTheListedTools() {
		ToolCallback other = new ToolCallback() {
			@Override
			public ToolDefinition getToolDefinition() {
				return ToolDefinition.builder().name("readDocument").description("Reads.").inputSchema("{}").build();
			}

			@Override
			public String call(String toolInput) {
				return "text";
			}
		};

		List<ToolCallback> decorated = ConfirmingToolCallback
				.decorator(List.of("deleteDocument"), ToolCallConfirmations.NONE).decorate(List.of(deleteDocument, other));

		assertInstanceOf(ConfirmingToolCallback.class, decorated.get(0));
		assertSame(other, decorated.get(1));
	}

	@Test
	void theRespawnedModelIsToldTheOutcome() {
		String allowed = ResolvedConditionText.render(
				answered("{\"id\":\"doc-1\"}", ClarificationAnswer.option(ToolCallConfirmationOptions.ALLOW)));
		assertTrue(allowed.startsWith("A person allowed the call to the tool deleteDocument"), allowed);
		assertTrue(allowed.contains("Call the tool again with exactly these arguments"), allowed);

		ClarificationAnswer deny = ClarificationAnswer.option(ToolCallConfirmationOptions.DENY);
		deny.setCustomExplanation("Archive it instead");
		String refused = ResolvedConditionText.render(answered("{\"id\":\"doc-1\"}", deny));
		assertTrue(refused.startsWith("A person refused the call to the tool deleteDocument"), refused);
		assertTrue(refused.endsWith("Their message: Archive it instead"), refused);

		String unanswered = ResolvedConditionText.render(answered("{\"id\":\"doc-1\"}", null));
		assertTrue(unanswered.startsWith("Nobody confirmed the call to the tool deleteDocument"), unanswered);

		assertNull(ConfirmingToolCallback.answered(null));
	}
}
