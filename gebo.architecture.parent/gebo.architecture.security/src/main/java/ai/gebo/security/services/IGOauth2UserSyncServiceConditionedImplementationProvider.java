package ai.gebo.security.services;

import ai.gebo.architecture.patterns.IGConditionedImplementationProvider;
import ai.gebo.security.model.Oauth2SyncUsersData;

public interface IGOauth2UserSyncServiceConditionedImplementationProvider
		extends IGConditionedImplementationProvider<Oauth2SyncUsersData, IGOauth2UserSyncService> {

}
