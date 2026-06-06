package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGAgentServiceRepositoryPattern;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
@Service
public class GAgentServiceRepositoryPatternImpl extends GAbstractImplementationsRepositoryPattern<IGGenericAgentService> implements IGAgentServiceRepositoryPattern{

	public GAgentServiceRepositoryPatternImpl(@Autowired(required = false) List<IGGenericAgentService> implementations) {
		super(implementations);
	}

	@Override
	public String getCodeValue(IGGenericAgentService x) {

		return x.getId();
	}

}
