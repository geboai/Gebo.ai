package ai.gebo.architecture.ai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Service
public class GIAIToolsAndResourcesLibrarySourceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGAIToolsAndResourcesLibrarySource>
		implements IGAIToolsAndResourcesLibrarySourceRepositoryPattern {

	public GIAIToolsAndResourcesLibrarySourceRepositoryPatternImpl(
			@Autowired(required = false) List<IGAIToolsAndResourcesLibrarySource> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGAIToolsAndResourcesLibrarySource x) {

		return x.getId();
	}

}
