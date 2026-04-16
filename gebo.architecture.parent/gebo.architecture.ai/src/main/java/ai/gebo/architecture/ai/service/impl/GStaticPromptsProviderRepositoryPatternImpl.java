package ai.gebo.architecture.ai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.IStaticPromptsProviderRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Component
@Scope("singleton")
public class GStaticPromptsProviderRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGStaticPromptsProvider>
		implements IStaticPromptsProviderRepositoryPattern {

	public GStaticPromptsProviderRepositoryPatternImpl(
			@Autowired(required = false) List<IGStaticPromptsProvider> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGStaticPromptsProvider x) {
		return x.getId();
	}
}
