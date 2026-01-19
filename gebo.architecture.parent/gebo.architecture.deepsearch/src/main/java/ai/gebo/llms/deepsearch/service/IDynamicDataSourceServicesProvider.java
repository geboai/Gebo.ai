package ai.gebo.llms.deepsearch.service;

import java.util.List;

public interface IDynamicDataSourceServicesProvider {
	public List<IGDeepSearchDataSourceService> getDynamicDeepSearchServices();
}
