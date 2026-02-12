package ai.gebo.llms.deepsearch.service;

import java.util.List;
import java.util.Optional;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;

public interface IGReactiveEnabledDeepSearchDataSourceLookupService {

	List<IGReactiveDeepSearchDataSourceService> enabledDataSources(IGConfigurableChatModel model,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request);

	default IGReactiveDeepSearchDataSourceService enabledDataSourceByCode(String id, IGConfigurableChatModel model,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request) {
		List<IGReactiveDeepSearchDataSourceService> dss = enabledDataSources(model, deepSearchConfig, request);
		return dss.stream().filter(x -> x.getHandlerId() != null && id != null && x.getHandlerId().equals(id))
				.findFirst().orElse(null);
	}
}