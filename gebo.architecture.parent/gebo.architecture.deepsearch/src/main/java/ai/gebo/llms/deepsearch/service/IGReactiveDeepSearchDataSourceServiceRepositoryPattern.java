package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;

public interface IGReactiveDeepSearchDataSourceServiceRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGReactiveDeepSearchDataSourceService> {
	public List<IGReactiveDeepSearchDataSourceService> findByExecutionTime(DataSourceExecutionTime executionTime);
}
