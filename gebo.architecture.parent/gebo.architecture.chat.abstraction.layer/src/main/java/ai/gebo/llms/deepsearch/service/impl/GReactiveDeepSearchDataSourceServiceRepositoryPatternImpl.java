package ai.gebo.llms.deepsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;

@Component
@Scope("singleton")
public class GReactiveDeepSearchDataSourceServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGReactiveDeepSearchDataSourceService>
		implements IGReactiveDeepSearchDataSourceServiceRepositoryPattern {

	public GReactiveDeepSearchDataSourceServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IGReactiveDeepSearchDataSourceService> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGReactiveDeepSearchDataSourceService x) {

		return x.getHandlerId();
	}

	@Override
	public List<IGReactiveDeepSearchDataSourceService> findByExecutionTime(DataSourceExecutionTime executionTime) {

		return findImplementations(x -> x.getExecutionTime() == executionTime);
	}

}
