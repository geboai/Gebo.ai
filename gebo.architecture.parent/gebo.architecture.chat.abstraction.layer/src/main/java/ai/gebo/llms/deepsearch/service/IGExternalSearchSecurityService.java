package ai.gebo.llms.deepsearch.service;

import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.ISearchService;

public interface IGExternalSearchSecurityService {
	public boolean isEnabledForCurrentUser(ISearchService searchService) throws SearchServiceException;
}
