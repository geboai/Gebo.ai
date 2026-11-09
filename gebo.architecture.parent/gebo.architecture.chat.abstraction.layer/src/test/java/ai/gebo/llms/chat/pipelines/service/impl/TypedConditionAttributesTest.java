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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ClarificationConditionAttributes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ClarificationReactionAttributes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DefaultConditionAttributes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DefaultReactionAttributes;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl;

/**
 * Pins the typed attributes of the two built-in condition types: they show what
 * the attributes map shows, and an answer given typed stands for the attributes
 * it covers while the others are kept for the custom extensions. Pins too the
 * type a condition is given: the id of the translator that shows it.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class TypedConditionAttributesTest {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final ClarificationConditionTransportTranslator clarification = new ClarificationConditionTransportTranslator();
	private final DefaultConditionTransportTranslator fallback = new DefaultConditionTransportTranslator();

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarificationCondition(
			boolean mandatory) {
		final ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of(ClarificationOption.of("2023", "the closed one"), ClarificationOption.of("2024")));
		options.setCustomAnswerAllowed(true);
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Which year?", "the report differs by year", null, mandatory, ClarificationOptions.class, options);
	}

	private static AgentSuspendedWaitingCondition<Integer, Object> numberCondition() {
		return new AgentSuspendedWaitingCondition<Integer, Object>(Integer.class, "the invoice number",
				"needed to look it up", null, false);
	}

	private static AgentSuspendedWaitingConditionReactionDto reaction(String type,
			LinkedHashMap<String, Object> attributes) {
		final AgentSuspendedWaitingConditionReactionDto reaction = new AgentSuspendedWaitingConditionReactionDto();
		reaction.setConditionType(type);
		reaction.setAttributes(attributes);
		return reaction;
	}

	private static LinkedHashMap<String, Object> throughJson(Map<String, Object> attributes) throws Exception {
		return MAPPER.readValue(MAPPER.writeValueAsString(attributes),
				new TypeReference<LinkedHashMap<String, Object>>() {
				});
	}

	@Test
	void aClarificationShowsTypedWhatItsMapShows() throws Exception {
		final LinkedHashMap<String, Object> attributes = clarification.translate(clarificationCondition(true))
				.getAttributes();

		for (Map<String, Object> shown : List.of(attributes, throughJson(attributes))) {
			final ClarificationConditionAttributes typed = ClarificationConditionAttributes.of(shown);
			assertEquals("Which year?", typed.getStatement());
			assertEquals("the report differs by year", typed.getExplanation());
			assertTrue(typed.isMandatory());
			assertEquals("Which year?", typed.getQuestion());
			assertEquals(List.of(ClarificationOption.of("2023", "the closed one"), ClarificationOption.of("2024")),
					typed.getOptions());
			assertFalse(typed.isMultipleChoice());
			assertTrue(typed.isCustomAnswerAllowed());
		}
	}

	@Test
	void aDefaultConditionShowsTypedWhatItsMapShows() throws Exception {
		final LinkedHashMap<String, Object> attributes = fallback.translate(numberCondition()).getAttributes();

		for (Map<String, Object> shown : List.of(attributes, throughJson(attributes))) {
			final DefaultConditionAttributes typed = DefaultConditionAttributes.of(shown);
			assertEquals("the invoice number", typed.getStatement());
			assertEquals("needed to look it up", typed.getExplanation());
			assertFalse(typed.isMandatory());
			assertEquals(Integer.class.getName(), typed.getValueType());
			assertNull(typed.getOptionsType());
			assertNull(typed.getLlmChosenOptions());
		}
	}

	@Test
	void withoutAttributesThereIsNoTypedView() {
		assertNull(ClarificationConditionAttributes.of(null));
		assertNull(DefaultConditionAttributes.of(null));
		assertNull(ClarificationReactionAttributes.of(null));
		assertNull(DefaultReactionAttributes.of(null));
		assertTrue(ClarificationConditionAttributes.of(new LinkedHashMap<String, Object>()).isMandatory(),
				"a condition is mandatory unless it says otherwise");
	}

	@Test
	void aTypedChoiceStandsForTheChoicesOfTheMapAndKeepsTheOtherAttributes() {
		final LinkedHashMap<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put(ClarificationConditionTransportTranslator.CHOSEN_OPTIONS, List.of("2023"));
		attributes.put("ticket", "T-12");
		final ClarificationReactionAttributes typed = new ClarificationReactionAttributes();
		typed.setChosenOption("2024");

		final LinkedHashMap<String, Object> merged = typed.mergeInto(attributes);

		assertEquals("2024", merged.get(ClarificationConditionTransportTranslator.CHOSEN_OPTION));
		assertFalse(merged.containsKey(ClarificationConditionTransportTranslator.CHOSEN_OPTIONS),
				"the map's choice is not added to the typed one");
		assertEquals("T-12", merged.get("ticket"), "kept for the custom extensions");

		final AgentSuspendedWaitingCondition saved = clarificationCondition(true);
		clarification.translateReaction(reaction(ClarificationConditionTransportTranslator.ID, merged), saved);
		assertEquals(List.of("2024"), ((ClarificationAnswer) saved.getInstanceValue()).getChosenOptions());
	}

	@Test
	void aTypedDeclineIsReadAsTheMapsOne() {
		final ClarificationReactionAttributes typed = new ClarificationReactionAttributes();
		typed.setDeclined(true);
		final AgentSuspendedWaitingCondition saved = clarificationCondition(false);

		clarification.translateReaction(reaction(ClarificationConditionTransportTranslator.ID, typed.mergeInto(null)),
				saved);

		assertNull(saved.getInstanceValue(), "declined: nothing was filled");
	}

	@Test
	void aTypedValueIsConvertedToTheConditionsType() {
		final LinkedHashMap<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put(DefaultConditionTransportTranslator.VALUE, "1");
		final DefaultReactionAttributes typed = new DefaultReactionAttributes();
		typed.setValue("42");
		final AgentSuspendedWaitingCondition saved = numberCondition();

		fallback.translateReaction(reaction(DefaultConditionTransportTranslator.ID, typed.mergeInto(attributes)), saved);

		assertEquals(42, saved.getInstanceValue());
	}

	@Test
	void anAnswerOfTheMapReadsTyped() throws Exception {
		final LinkedHashMap<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put(ClarificationConditionTransportTranslator.CHOSEN_OPTIONS, List.of("2023", "2024"));
		attributes.put(ClarificationConditionTransportTranslator.CUSTOM_EXPLANATION, "both");
		attributes.put(GAbstractConditionTransportTranslator.DECLINED, "false");

		final ClarificationReactionAttributes typed = ClarificationReactionAttributes.of(throughJson(attributes));

		assertEquals(List.of("2023", "2024"), typed.getChosenOptions());
		assertEquals("both", typed.getCustomExplanation());
		assertNull(typed.getChosenOption());
		assertEquals(Boolean.FALSE, typed.getDeclined());
		assertEquals(Boolean.TRUE, DefaultReactionAttributes
				.of(new LinkedHashMap<String, Object>(Map.of(GAbstractConditionTransportTranslator.DECLINED, true)))
				.getDeclined());
	}

	@Test
	void theTypeOfAConditionIsTheIdOfTheTranslatorThatShowsIt() {
		final NetworkOfAgentsSuspensionTransport transport = new NetworkOfAgentsSuspensionTransport(
				new AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl(
						List.of(fallback, clarification)));
		final WaitingConditionTypeResolverImpl resolver = new WaitingConditionTypeResolverImpl(transport);

		assertEquals(ClarificationConditionTransportTranslator.ID, resolver.typeOf(clarificationCondition(true)));
		assertEquals(DefaultConditionTransportTranslator.ID, resolver.typeOf(numberCondition()));
		assertNull(resolver.typeOf(null));
		assertEquals(DefaultConditionTransportTranslator.ID,
				new WaitingConditionTypeResolverImpl(NetworkOfAgentsSuspensionTransport.plain())
						.typeOf(clarificationCondition(true)),
				"without translators the default one shows every condition");
	}
}
