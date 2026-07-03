package ai.gebo.llms.deepsearch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.DeepSearchDataSourceAccess;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.acl.AclGrantType;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GExternalSearchSecurityServiceImpl implements IGExternalSearchSecurityService {
	private final IGSecurityService securityService;
	private final IGDeepSearchConfigProvider configProvider;

	@Override
	public boolean isEnabledForCurrentUser(ISearchService searchService) throws SearchServiceException {
		DeepSearchConfig deepSearchConfig = configProvider.get();
		boolean adminConfigured = !(deepSearchConfig instanceof DeepSearchDefaultConfig);
		if (!adminConfigured && !securityService.isCurrentUserAdmin()) {
			return false;
		}
		boolean userCanAccess = securityService.isCanDo(deepSearchConfig, true, AclGrantType.EXECUTE);
		if (userCanAccess) {
			List<DeepSearchDataSourceAccess> accesses = deepSearchConfig.getDataSourcesAccesses();
			boolean perDataSourceConfigured = deepSearchConfig.getPerDataSourceConfigured() != null
					&& deepSearchConfig.getPerDataSourceConfigured();
			if (accesses != null && !accesses.isEmpty() && !securityService.isCurrentUserAdmin()
					&& perDataSourceConfigured) {
				String thisSystemId = searchService.getId();
				DeepSearchDataSourceAccess gridCell = accesses.stream()
						.filter(x -> x.getDataSourceId() != null && x.getDataSourceId().equals(thisSystemId))
						.findFirst().orElse(null);
				userCanAccess = gridCell != null && securityService.isCanDo(gridCell, true, AclGrantType.EXECUTE);
			}
		}
		if (searchService.isEnabled() && userCanAccess) {
			List<SearchableSystemMetaData> systems = searchService.getSearchableSystems();
			return systems != null && !systems.isEmpty();
		}
		return false;
	}

}
