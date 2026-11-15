/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ClarificationReactionAttributes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DefaultReactionAttributes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl;

/**
 * Pins that a condition is read by the translator of the type kept with it,
 * whatever translators are registered and in whatever order; by its kind, as
 * before, when it has none or one no translator has any more. Pins too that the
 * DTO shows the typed attributes of its type and that an answer given typed is
 * read as its attributes, wherever it comes in.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ConditionTypePinnedTest {
	private final ClarificationConditionTransportTranslator clarification = new ClarificationConditionTransportTranslator();
	private final DefaultConditionTransportTranslator fallback = new DefaultConditionTransportTranslator();
	/** A translator registered later that takes the clarifications too. */
	private final GAbstractConditionTransportTranslator later = new GAbstractConditionTransportTranslator() {
		@Override
		public String getId() {
			return "later";
		}

		@Override
		public boolean canTranslate(AgentSuspendedWaitingCondition condition) {
			return condition != null && condition.getLlmChosenOptions() instanceof ClarificationOptions;
		}

		@Override
		protected void describe(AgentSuspendedWaitingCondition condition, LinkedHashMap<String, Object> attributes) {
		}

		@Override
		protected void resolve(AgentSuspendedWaitingConditionReactionDto reaction,
				AgentSuspendedWaitingCondition savedCondition) {
		}
	};
	private final NetworkOfAgentsSuspensionTransport transport = new NetworkOfAgentsSuspensionTransport(
			new AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl(
					List.of(fallback, clarification, later)));

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarificationCondition(
			String type) {
		final ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of(ClarificationOption.of("2023"), ClarificationOption.of("2024")));
		final AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> condition = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, "Which year?", "the report differs by year", null, true,
				ClarificationOptions.class, options);
		condition.setConditionType(type);
		return condition;
	}

	private static AgentSuspendedWaitingCondition<String, Object> plainCondition(String type) {
		final AgentSuspendedWaitingCondition<String, Object> condition = new AgentSuspendedWaitingCondition<String, Object>(
				String.class, "the invoice number", "needed to look it up", null, false);
		condition.setConditionType(type);
		return condition;
	}

	@Test
	void theTranslatorOfTheTypeKeptReadsTheConditionWhateverIsRegisteredAfterIt() {
		assertSame(later, transport.translatorOf(clarificationCondition(null)),
				"by kind the last one registered that takes it wins");
		assertSame(clarification, transport.translatorOf(clarificationCondition("clarification")),
				"the type kept wins over the order");
		assertSame(fallback, transport.translatorOf(clarificationCondition("default")));
		assertSame(fallback, transport.translatorOf(plainCondition(null)));
	}

	@Test
	void aTypeNoTranslatorHasAnyMoreIsReadByItsKind() {
		assertSame(later, transport.translatorOf(clarificationCondition("gone")));
		assertSame(fallback, transport.translatorOf(plainCondition("gone")));
	}

	@Test
	void withoutTranslatorsTheDefaultOneReadsEveryCondition() {
		final NetworkOfAgentsSuspensionTransport plain = NetworkOfAgentsSuspensionTransport.plain();
		assertEquals(DefaultConditionTransportTranslator.ID,
				plain.translatorOf(clarificationCondition("clarification")).getId());
		assertEquals(DefaultConditionTransportTranslator.ID, plain.translatorOf(plainCondition("default")).getId());
	}

	@Test
	void theDtoShowsTheTypedAttributesOfItsType() {
		final AgentSuspendedWaitingConditionDto asked = clarification.translate(clarificationCondition("clarification"));
		assertNotNull(asked.getClarificationAttributes());
		assertNull(asked.getDefaultAttributes());
		assertEquals("Which year?", asked.getClarificationAttributes().getQuestion());
		assertEquals("Which year?", asked.getAttributes().get(ClarificationConditionTransportTranslator.QUESTION),
				"the map stays for the clients that read it");

		final AgentSuspendedWaitingConditionDto plain = fallback.translate(plainCondition("default"));
		assertNotNull(plain.getDefaultAttributes());
		assertNull(plain.getClarificationAttributes());
		assertEquals(String.class.getName(), plain.getDefaultAttributes().getValueType());

		assertNull(later.translate(clarificationCondition("later")).getClarificationAttributes(),
				"a custom type shows its map only");
	}

	@Test
	void anAnswerGivenTypedOnlyIsReadAndMergingItAgainChangesNothing() {
		final AgentSuspendedWaitingConditionReactionDto answer = new AgentSuspendedWaitingConditionReactionDto();
		answer.setConditionType(ClarificationConditionTransportTranslator.ID);
		final ClarificationReactionAttributes typed = new ClarificationReactionAttributes();
		typed.setChosenOption("2023");
		answer.setClarificationAttributes(typed);
		final AgentSuspendedWaitingCondition saved = clarificationCondition("clarification");

		clarification.translateReaction(answer, saved);

		assertEquals(List.of("2023"), ((ClarificationAnswer) saved.getInstanceValue()).getChosenOptions());
		final LinkedHashMap<String, Object> once = new LinkedHashMap<String, Object>(answer.getAttributes());
		assertEquals(once, answer.mergeTypedAttributes().getAttributes());
	}

	@Test
	void theChatMergesTheAnswersGivenTypedBeforeAnythingReadsThem() {
		final AgentSuspendedWaitingConditionReactionDto typed = new AgentSuspendedWaitingConditionReactionDto();
		typed.setConditionType(DefaultConditionTransportTranslator.ID);
		final DefaultReactionAttributes value = new DefaultReactionAttributes();
		value.setValue("INV-7");
		typed.setDefaultAttributes(value);
		final AgentSuspendedWaitingConditionReactionDto ofTheMap = new AgentSuspendedWaitingConditionReactionDto();
		ofTheMap.setConditionType(DefaultConditionTransportTranslator.ID);
		ofTheMap.setAttributes(new LinkedHashMap<String, Object>());
		ofTheMap.getAttributes().put(DefaultConditionTransportTranslator.VALUE, "INV-8");
		final NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setReactions(new java.util.ArrayList<AgentSuspendedWaitingConditionReactionDto>(
				java.util.Arrays.asList(typed, null, ofTheMap)));
		final GeboChatRequest request = new GeboChatRequest();
		request.setNetworkOfAgentsSuspendedStateReaction(reaction);

		ChatPipelineServiceImpl.mergeTypedAnswers(request);
		ChatPipelineServiceImpl.mergeTypedAnswers(new GeboChatRequest());
		ChatPipelineServiceImpl.mergeTypedAnswers(null);

		assertEquals("INV-7", typed.getAttributes().get(DefaultConditionTransportTranslator.VALUE));
		assertEquals("INV-8", ofTheMap.getAttributes().get(DefaultConditionTransportTranslator.VALUE));
	}
}
