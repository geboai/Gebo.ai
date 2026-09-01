package ai.gebo.security.services;

import ai.gebo.architecture.patterns.IGConditionedImplementation;
import ai.gebo.security.model.Oauth2SyncUsersData;

public interface IGOauth2UserSyncService extends IGConditionedImplementation<Oauth2SyncUsersData> {
	public void createOrSyncUser(Oauth2SyncUsersData data);

	public void syncUser(Oauth2SyncUsersData data);
}
