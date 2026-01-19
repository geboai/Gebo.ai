package ai.gebo.architecture.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.model.base.GeboComponentInfo;

@Component
@Scope("singleton")
public class SearchServiceRepositoryPatternImpl extends GAbstractImplementationsRepositoryPattern<ISearchService>
		implements ISearchServiceRepositoryPattern {

	public SearchServiceRepositoryPatternImpl(@Autowired(required = false) List<ISearchService> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(ISearchService x) {

		return x.getId();
	}

	@Override
	public ISearchService findByOriginComponent(GeboComponentInfo originComponent) {
		if (originComponent == null)
			return null;
		final String msgModuleId = originComponent.getMessagingModuleId();
		final String msgComponentId = originComponent.getMessagingComponentId();
		return findImplementation(x -> {
			boolean found = x.getMessagingModuleId() != null && x.getMessagingSystemId() != null && msgModuleId != null
					&& msgComponentId != null && msgModuleId.equals(x.getMessagingModuleId())
					&& msgComponentId.equals(x.getMessagingSystemId());
			return found;

		});
	}

}
