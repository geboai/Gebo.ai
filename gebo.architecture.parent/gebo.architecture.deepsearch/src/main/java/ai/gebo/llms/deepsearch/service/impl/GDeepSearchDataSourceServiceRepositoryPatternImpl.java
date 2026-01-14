package ai.gebo.llms.deepsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceServiceRepositoryPattern;

@Component
@Scope("singleton")
public class GDeepSearchDataSourceServiceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGDeepSearchDataSourceService>
		implements IGDeepSearchDataSourceServiceRepositoryPattern {

	public GDeepSearchDataSourceServiceRepositoryPatternImpl(
			@Autowired(required = false) List<IGDeepSearchDataSourceService> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IGDeepSearchDataSourceService x) {

		return x.getHandlerId();
	}

	@Override
	public List<IGDeepSearchDataSourceService> findByExecutionTime(DataSourceExecutionTime executionTime) {

		return findImplementations(x -> x.getExecutionTime() == executionTime);
	}

}
