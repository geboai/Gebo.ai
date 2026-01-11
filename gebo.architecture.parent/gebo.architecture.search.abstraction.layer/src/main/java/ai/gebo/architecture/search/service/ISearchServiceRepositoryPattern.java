package ai.gebo.architecture.search.service;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.model.base.GeboComponentInfo;

public interface ISearchServiceRepositoryPattern extends IGImplementationsRepositoryPattern<ISearchService>{

	ISearchService findByOriginComponent(GeboComponentInfo originComponent);

}
