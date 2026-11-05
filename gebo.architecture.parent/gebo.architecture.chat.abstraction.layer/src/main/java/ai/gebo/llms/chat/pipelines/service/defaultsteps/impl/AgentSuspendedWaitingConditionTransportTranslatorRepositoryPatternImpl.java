package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern;

@Service
public class AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IAgentSuspendedWaitingConditionTransportTranslator>
		implements IAgentSuspendedWaitingConditionTransportTranslatorRepositoryPattern {

	public AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl(
			@Autowired(required = false) List<IAgentSuspendedWaitingConditionTransportTranslator> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IAgentSuspendedWaitingConditionTransportTranslator x) {

		return x.getId();
	}

	@Override
	public IAgentSuspendedWaitingConditionTransportTranslator findTranslator(AgentSuspendedWaitingCondition condition) {
		return findImplementation(x -> x.canTranslate(condition));
	}

	@Override
	public IAgentSuspendedWaitingConditionTransportTranslator findReactionTranslator(
			AgentSuspendedWaitingConditionReactionDto reaction) {

		return findImplementation(x -> x.canTranslateReaction(reaction));
	}

}
