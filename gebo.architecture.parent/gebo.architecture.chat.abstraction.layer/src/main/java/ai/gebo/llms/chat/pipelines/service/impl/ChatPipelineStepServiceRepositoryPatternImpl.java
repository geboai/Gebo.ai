package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;

@Component
@Scope("singleton")
public class ChatPipelineStepServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IChatPipelineStepService>
		implements IChatPipelineStepServiceRepositoryPattern {

	public ChatPipelineStepServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IChatPipelineStepService> implementations) {
		super(implementations);

	}
	@Override
	public String getCodeValue(IChatPipelineStepService x) {

		return x.getStepId();
	}

}
