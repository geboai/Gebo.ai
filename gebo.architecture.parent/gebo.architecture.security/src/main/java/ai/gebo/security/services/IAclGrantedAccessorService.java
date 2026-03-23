package ai.gebo.security.services;

import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;

public interface IAclGrantedAccessorService {
	public IAclGrantedAccessor fromGroup(UsersGroup group);

	public IAclGrantedAccessor fromUser(User group);

	public IAclGrantedAccessor fromUser(UserInfos group);
}
