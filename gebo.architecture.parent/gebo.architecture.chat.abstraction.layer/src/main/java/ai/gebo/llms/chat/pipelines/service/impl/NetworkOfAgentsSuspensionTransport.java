/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IConditionReactionRecorder;
import ai.gebo.llms.chat.pipelines.service.SolvedCondition;

/**
 * Turns a suspended run into what the chat carries to the client (see
 * {@link NetworkOfAgentsSuspendedState}): the block id and one DTO per
 * condition, each made by the translator for its kind, the default one when no
 * other knows the condition; and renders the state as text for a client that
 * only shows the answer. Built from the result of a run that just suspended, or
 * from a block loaded later, when the conversation is opened again. Where the
 * conditions are answered one at a time (see {@link IConditionReactionRecorder})
 * the state says so, and a loaded block marks the conditions already solved
 * ({@value #SOLVED}, {@value #SOLVED_BY}, {@value #SOLVED_AT}).
 */
@Service
public class NetworkOfAgentsSuspensionTransport {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkOfAgentsSuspensionTransport.class);
	private final IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern translators;
	/** The attribute of a condition an answer already solves. */
	public static final String SOLVED = "solved";
	/** The system the answer of a solved condition came from. */
	public static final String SOLVED_BY = "solvedBy";
	/** When the answer of a solved condition was taken. */
	public static final String SOLVED_AT = "solvedAt";
	private final IAgentSuspendedWaitingConditionTransportTranslator fallback = new DefaultConditionTransportTranslator();
	/** Looked up on use: the recorder reaches this transport through the dispatcher. */
	private final ObjectProvider<IConditionReactionRecorder> recorders;

	public NetworkOfAgentsSuspensionTransport(
			IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern translators) {
		this(translators, null);
	}

	@Autowired
	public NetworkOfAgentsSuspensionTransport(
			@Autowired(required = false) IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern translators,
			ObjectProvider<IConditionReactionRecorder> recorders) {
		this.translators = translators;
		this.recorders = recorders;
	}

	private IConditionReactionRecorder recorder() {
		return recorders != null ? recorders.getIfAvailable() : null;
	}

	/** A transport with the default translator alone: what a plain run outside Spring uses. */
	public static NetworkOfAgentsSuspensionTransport plain() {
		return new NetworkOfAgentsSuspensionTransport(null);
	}

	/** The state of a run that just suspended; null when the result is not a suspension. */
	public NetworkOfAgentsSuspendedState stateOf(NetworkOfAgentsExecutionResult<?> result) {
		if (result == null || result.getExecutedStatus() != NetworkOfAgentsExecutionResult.NStatus.SUSPENDED) {
			return null;
		}
		final List<AgentSuspendedWaitingCondition<?, ?>> conditions = new ArrayList<AgentSuspendedWaitingCondition<?, ?>>();
		if (result.getWaitingConditions() != null) {
			for (AgentSuspendedWaitingCondition<?, ?> condition : result.getWaitingConditions()) {
				conditions.add(condition);
			}
		}
		return stateOf(result.getSuspensionId(), conditions);
	}

	/** The state of a block loaded later: its conditions read back from it. */
	public NetworkOfAgentsSuspendedState stateOf(GNetworkOfAgentsSuspension block) {
		if (block == null) {
			return null;
		}
		final List<AgentSuspendedWaitingCondition<?, ?>> conditions = new ArrayList<AgentSuspendedWaitingCondition<?, ?>>();
		for (WaitingCondition waiting : block.conditions()) {
			conditions.add(waiting.toCondition());
		}
		final NetworkOfAgentsSuspendedState state = stateOf(block.getCode(), conditions);
		markSolved(state, block);
		return state;
	}

	/** Marks the conditions of the state an answer of some source already solves. */
	private void markSolved(NetworkOfAgentsSuspendedState state, GNetworkOfAgentsSuspension block) {
		final IConditionReactionRecorder recorder = recorder();
		if (recorder == null || state.getConditions() == null) {
			return;
		}
		final List<SolvedCondition> solved = recorder.solvedConditionsOf(block);
		if (solved == null || solved.isEmpty()) {
			return;
		}
		for (AgentSuspendedWaitingConditionDto dto : state.getConditions()) {
			for (SolvedCondition answer : solved) {
				if (answer.conditionId() != null && answer.conditionId().equals(dto.getConditionId())) {
					final LinkedHashMap<String, Object> attributes = dto.getAttributes() != null ? dto.getAttributes()
							: new LinkedHashMap<String, Object>();
					attributes.put(SOLVED, Boolean.TRUE);
					attributes.put(SOLVED_BY, answer.sourceSystem());
					attributes.put(SOLVED_AT, answer.solvedAt());
					dto.setAttributes(attributes);
				}
			}
		}
	}

	public NetworkOfAgentsSuspendedState stateOf(String suspensionId,
			List<AgentSuspendedWaitingCondition<?, ?>> conditions) {
		final NetworkOfAgentsSuspendedState state = new NetworkOfAgentsSuspendedState();
		state.setNetworkOfAgentsBlockId(suspensionId);
		final List<AgentSuspendedWaitingConditionDto> dtos = new ArrayList<AgentSuspendedWaitingConditionDto>();
		if (conditions != null) {
			for (AgentSuspendedWaitingCondition<?, ?> condition : conditions) {
				if (condition != null) {
					dtos.add(translatorOf(condition).translate(condition));
				}
			}
		}
		state.setConditions(dtos);
		state.setAnswersOneByOne(recorder() != null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Suspension:" + suspensionId + " carried to the client with " + dtos.size() + " condition(s)");
		}
		return state;
	}

	/** The translator for the condition: the one made for its kind, else the default. */
	public IAgentSuspendedWaitingConditionTransportTranslator translatorOf(AgentSuspendedWaitingCondition<?, ?> condition) {
		final IAgentSuspendedWaitingConditionTransportTranslator found = translators != null
				? translators.findTranslator(condition)
				: null;
		return found != null ? found : fallback;
	}

	/** Whether a condition of the state is mandatory: the conversation waits for the block. */
	public static boolean isMandatory(NetworkOfAgentsSuspendedState state) {
		if (state == null || state.getConditions() == null) {
			return false;
		}
		for (AgentSuspendedWaitingConditionDto dto : state.getConditions()) {
			if (dto.getAttributes() != null
					&& Boolean.TRUE.equals(dto.getAttributes().get(GAbstractConditionTransportTranslator.MANDATORY))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The state as text, for a client that only shows the answer: each condition's
	 * statement, the options when it has them, and whether it may be left
	 * unanswered.
	 */
	public static String render(NetworkOfAgentsSuspendedState state) {
		if (state == null || state.getConditions() == null || state.getConditions().isEmpty()) {
			return "";
		}
		final StringBuilder text = new StringBuilder();
		for (AgentSuspendedWaitingConditionDto dto : state.getConditions()) {
			if (text.length() > 0) {
				text.append("\n\n");
			}
			final Object statement = dto.getAttributes() != null
					? dto.getAttributes().get(GAbstractConditionTransportTranslator.STATEMENT)
					: null;
			text.append(statement != null ? String.valueOf(statement) : "The work waits for something from you.");
			final Object options = dto.getAttributes() != null
					? dto.getAttributes().get(ClarificationConditionTransportTranslator.OPTIONS)
					: null;
			if (options instanceof List<?> list && !list.isEmpty()) {
				for (Object option : list) {
					text.append("\n- ").append(optionText(option));
				}
				if (Boolean.TRUE.equals(dto.getAttributes().get(ClarificationConditionTransportTranslator.MULTIPLE_CHOICE))) {
					text.append("\n(You may choose more than one.)");
				}
				if (Boolean.TRUE.equals(
						dto.getAttributes().get(ClarificationConditionTransportTranslator.CUSTOM_ANSWER_ALLOWED))) {
					text.append("\n- or explain in your own words");
				}
			}
			if (dto.getAttributes() != null
					&& Boolean.FALSE.equals(dto.getAttributes().get(GAbstractConditionTransportTranslator.MANDATORY))) {
				text.append("\n(You may leave this unanswered and go on.)");
			}
		}
		return text.toString();
	}

	/** An option as a line: its label and, when it has one, its description. */
	private static String optionText(Object option) {
		if (!(option instanceof Map<?, ?> described)) {
			return String.valueOf(option);
		}
		final Object label = described.get(ClarificationConditionTransportTranslator.OPTION_LABEL);
		final Object description = described.get(ClarificationConditionTransportTranslator.OPTION_DESCRIPTION);
		return description != null ? label + ": " + description : String.valueOf(label);
	}
}
