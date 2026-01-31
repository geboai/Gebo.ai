package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;

public interface IGDeepSearchDataSourceServiceRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGDeepSearchDataSourceService> {
	public List<IGDeepSearchDataSourceService> findByExecutionTime(DataSourceExecutionTime executionTime);
}
