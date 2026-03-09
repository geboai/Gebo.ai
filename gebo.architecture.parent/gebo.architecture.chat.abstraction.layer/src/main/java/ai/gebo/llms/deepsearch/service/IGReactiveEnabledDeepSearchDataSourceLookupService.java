package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.llms.deepsearch.model.DeepSearchConfig;

public interface IGReactiveEnabledDeepSearchDataSourceLookupService {

	List<IGReactiveDeepSearchDataSourceService> enabledDataSources(DeepSearchConfig deepSearchConfig);

	default IGReactiveDeepSearchDataSourceService enabledDataSourceByCode(String id, DeepSearchConfig deepSearchConfig) {
		List<IGReactiveDeepSearchDataSourceService> dss = enabledDataSources(deepSearchConfig);
		return dss.stream().filter(x -> x.getHandlerId() != null && id != null && x.getHandlerId().equals(id))
				.findFirst().orElse(null);
	}
}