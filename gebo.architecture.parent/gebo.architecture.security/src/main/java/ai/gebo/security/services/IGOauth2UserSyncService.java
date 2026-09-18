package ai.gebo.security.services;

import ai.gebo.architecture.patterns.IGConditionedImplementation;
import ai.gebo.security.model.Oauth2SyncUsersData;

public interface IGOauth2UserSyncService extends IGConditionedImplementation<Oauth2SyncUsersData> {
	/********************************************************
	 * Create the user if does not exists and syncs eventually user/group/additional infos
	 * @param data
	 */
	public void createOrSyncUser(Oauth2SyncUsersData data);
	/************************************************************************
	 * syncs eventually user/group/additional infos on the corresponding oauth2 user
	 * @param data
	 */
	public void syncUser(Oauth2SyncUsersData data);
}
