package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ReactiveEnabledDeepSearchDataSourceLookupServiceImpl implements IGReactiveEnabledDeepSearchDataSourceLookupService {
	private final static Logger LOGGER = LoggerFactory.getLogger(ReactiveEnabledDeepSearchDataSourceLookupServiceImpl.class);
	private final IGReactiveDynamicDataSourceServicesProvider dynamicProvider;
	private final IGReactiveDeepSearchDataSourceServiceRepositoryPattern staticProvider;

	@Override
	public List<IGReactiveDeepSearchDataSourceService> enabledDataSources(IGConfigurableChatModel model,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request) {
		List<IGReactiveDeepSearchDataSourceService> dynamicServices = dynamicProvider.getDynamicDeepSearchServices();
		List<IGReactiveDeepSearchDataSourceService> staticServices = staticProvider.getImplementations();
		List<IGReactiveDeepSearchDataSourceService> allServices = new ArrayList<IGReactiveDeepSearchDataSourceService>();
		allServices.addAll(dynamicServices);
		allServices.addAll(staticServices);

		List<IGReactiveDeepSearchDataSourceService> out = new ArrayList<IGReactiveDeepSearchDataSourceService>();
		for (IGReactiveDeepSearchDataSourceService service : allServices) {
			try {
				if (service.isEnabled(model, deepSearchConfig, request)) {
					out.add(service);
				}
			} catch (SearchServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return out;
	}

}
