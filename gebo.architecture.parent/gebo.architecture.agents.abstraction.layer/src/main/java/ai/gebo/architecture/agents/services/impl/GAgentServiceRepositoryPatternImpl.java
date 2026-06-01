package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGAgentService;
import ai.gebo.architecture.agents.services.IGAgentServiceRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
@Service
public class GAgentServiceRepositoryPatternImpl extends GAbstractImplementationsRepositoryPattern<IGAgentService> implements IGAgentServiceRepositoryPattern{

	public GAgentServiceRepositoryPatternImpl(@Autowired(required = false) List<IGAgentService> implementations) {
		super(implementations);
	}

	@Override
	public String getCodeValue(IGAgentService x) {

		return x.getId();
	}

}
