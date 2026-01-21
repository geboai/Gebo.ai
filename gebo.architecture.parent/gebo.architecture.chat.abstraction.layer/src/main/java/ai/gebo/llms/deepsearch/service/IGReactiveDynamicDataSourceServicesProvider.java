package ai.gebo.llms.deepsearch.service;

import java.util.List;

public interface IGReactiveDynamicDataSourceServicesProvider {
	public List<IGReactiveDeepSearchDataSourceService> getDynamicDeepSearchServices();
}
