package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportServiceRepositoryPattern;

@Component
@Scope("singleton")
public class GImageModelConfigurationSupportServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGImageModelConfigurationSupportService>
		implements IGImageModelConfigurationSupportServiceRepositoryPattern {

	public GImageModelConfigurationSupportServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IGImageModelConfigurationSupportService> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGImageModelConfigurationSupportService x) {

		return x.getId();
	}

}
