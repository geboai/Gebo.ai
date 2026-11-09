package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl;

/**
 * Pins what the chat carries to the client of a suspended run, from the result
 * of the run or from its block loaded later, and how it reads as text.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class NetworkOfAgentsSuspensionTransportTest {
	private final ClarificationConditionTransportTranslator clarification = new ClarificationConditionTransportTranslator();
	private final NetworkOfAgentsSuspensionTransport transport = new NetworkOfAgentsSuspensionTransport(
			new AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl(
					List.of(new DefaultConditionTransportTranslator(), clarification)));

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarificationCondition(
			boolean mandatory) {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of(ClarificationOption.of("2023"), ClarificationOption.of("2024")));
		options.setCustomAnswerAllowed(true);
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Which year?", "the report differs by year", null, mandatory, ClarificationOptions.class, options);
	}

	@Test
	void theResultOfARunThatSuspendedBecomesTheStateTheClientReactsTo() {
		AgentSuspendedWaitingCondition condition = clarificationCondition(true);
		NetworkOfAgentsExecutionResult<String> result = NetworkOfAgentsExecutionResult.suspended("block-1",
				List.of(condition));

		NetworkOfAgentsSuspendedState state = transport.stateOf(result);

		assertEquals("block-1", state.getNetworkOfAgentsBlockId());
		assertEquals(1, state.getConditions().size());
		assertEquals("clarification", state.getConditions().get(0).getConditionType(), "the translator for its kind");
		assertEquals(condition.getSuspendedConditionId(), state.getConditions().get(0).getConditionId());
		assertTrue(NetworkOfAgentsSuspensionTransport.isMandatory(state));
		assertSame(clarification, transport.translatorOf(condition));
	}

	@Test
	void aRunThatEndedHasNoState() {
		assertNull(transport.stateOf(NetworkOfAgentsExecutionResult.of("the answer")));
		assertNull(transport.stateOf((NetworkOfAgentsExecutionResult<?>) null));
	}

	@Test
	void anAgentWaitingForTwoConditionsShowsThemBothAndAMandatoryOneHoldsTheBlock() {
		AgentSuspendedWaitingCondition first = clarificationCondition(false);
		AgentSuspendedWaitingCondition second = clarificationCondition(true);
		GNetworkOfAgentsSuspension block = new GNetworkOfAgentsSuspension();
		block.setCode("block-3");
		SuspendedAgentState entry = SuspendedAgentState.of(null, null, null, first);
		entry.getWaitingConditions().add(GNetworkOfAgentsSuspension.WaitingCondition.of(second));
		block.getSuspendedAgents().add(entry);

		NetworkOfAgentsSuspendedState state = transport.stateOf(block);

		assertEquals(List.of(first.getSuspendedConditionId(), second.getSuspendedConditionId()),
				state.getConditions().stream().map(c -> c.getConditionId()).toList());
		assertTrue(block.isMandatory());
	}

	@Test
	void aBlockLoadedLaterGivesTheSameStateFromItsSavedConditions() {
		AgentSuspendedWaitingCondition condition = clarificationCondition(false);
		GNetworkOfAgentsSuspension block = new GNetworkOfAgentsSuspension();
		block.setCode("block-2");
		block.getSuspendedAgents().add(SuspendedAgentState.of(null, null, null, condition));

		NetworkOfAgentsSuspendedState state = transport.stateOf(block);

		assertEquals("block-2", state.getNetworkOfAgentsBlockId());
		assertEquals(condition.getSuspendedConditionId(), state.getConditions().get(0).getConditionId(),
				"the saved id, read back from the block");
		assertEquals(List.of(Map.of("label", "2023"), Map.of("label", "2024")),
				state.getConditions().get(0).getAttributes().get("options"));
		assertFalse(NetworkOfAgentsSuspensionTransport.isMandatory(state));
	}

	@Test
	void theStateRendersAsTextForAClientThatOnlyShowsTheAnswer() {
		NetworkOfAgentsSuspendedState state = transport.stateOf(NetworkOfAgentsExecutionResult.suspended("block-3",
				List.of(clarificationCondition(false), new AgentSuspendedWaitingCondition<String, Object>(
						String.class, "the invoice number", "needed to look it up", null, true))));

		String text = NetworkOfAgentsSuspensionTransport.render(state);

		assertEquals("Which year?\n- 2023\n- 2024\n- or explain in your own words\n"
				+ "(You may leave this unanswered and go on.)\n\nthe invoice number", text);
		assertEquals("", NetworkOfAgentsSuspensionTransport.render(null));
	}

	@Test
	void withoutTranslatorsDeployedTheDefaultOneCarriesEveryCondition() {
		NetworkOfAgentsSuspendedState state = NetworkOfAgentsSuspensionTransport.plain()
				.stateOf(NetworkOfAgentsExecutionResult.suspended("block-4", List.of(clarificationCondition(true))));

		assertEquals("default", state.getConditions().get(0).getConditionType());
		assertEquals("Which year?", state.getConditions().get(0).getAttributes().get("statement"));
	}

	@Test
	void theTextShowsEachOptionWithItsDescriptionAndSaysWhenSeveralMayBeChosen() {
		AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> condition = clarificationCondition(true);
		ClarificationOptions options = condition.getLlmChosenOptions();
		options.getOptions().set(0, ClarificationOption.of("2023", "the closed year"));
		options.setMultipleChoice(true);
		NetworkOfAgentsSuspendedState state = new NetworkOfAgentsSuspendedState();
		state.setConditions(List.of(clarification.translate(condition)));

		String text = NetworkOfAgentsSuspensionTransport.render(state);

		assertTrue(text.contains("\n- 2023: the closed year\n- 2024\n"), text);
		assertTrue(text.contains("(You may choose more than one.)"), text);
	}
}
