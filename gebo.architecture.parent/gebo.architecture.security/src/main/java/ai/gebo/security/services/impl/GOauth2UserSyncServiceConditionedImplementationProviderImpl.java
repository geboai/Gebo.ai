package ai.gebo.security.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractConditionedImplementationProvider;
import ai.gebo.security.model.Oauth2SyncUsersData;
import ai.gebo.security.services.IGOauth2UserSyncService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGUsersAdminService;

@Service
public class GOauth2UserSyncServiceConditionedImplementationProviderImpl
		extends GAbstractConditionedImplementationProvider<Oauth2SyncUsersData, IGOauth2UserSyncService>
		implements IGOauth2UserSyncServiceConditionedImplementationProvider {
	private final IGUsersAdminService userService;

	public GOauth2UserSyncServiceConditionedImplementationProviderImpl(
			@Autowired(required = false) List<IGOauth2UserSyncService> implementations,
			IGUsersAdminService userService) {

		super(implementations, new IGOauth2UserSyncService() {
			@Override
			public void createOrSyncUser(Oauth2SyncUsersData data) {

				userService.createUserIfNotExists((String) data.getOauth2User().getAttributes().get("email"),
						data.getOauth2User().getAttributes(), data.getConfig().getRuntimeConfiguration().getProvider());

			}

			@Override
			public boolean isHandlerFor(Oauth2SyncUsersData param) {

				return true;
			}

			@Override
			public void syncUser(Oauth2SyncUsersData data) {

			}
		});
		this.userService = userService;
	}

	@Override
	public String getCodeValue(IGOauth2UserSyncService x) {

		return x.getClass().getName();
	}

}
