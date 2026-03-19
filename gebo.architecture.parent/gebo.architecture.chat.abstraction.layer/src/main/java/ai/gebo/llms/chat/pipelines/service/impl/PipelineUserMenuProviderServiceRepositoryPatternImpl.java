package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderService;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderServiceRepositoryPattern;

@Component
@Scope("singleton")
public class PipelineUserMenuProviderServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IPipelineUserMenuProviderService>
		implements IPipelineUserMenuProviderServiceRepositoryPattern {

	public PipelineUserMenuProviderServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IPipelineUserMenuProviderService> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IPipelineUserMenuProviderService x) {

		return x.getPipelineId();
	}

}
