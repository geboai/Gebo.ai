package ai.gebo.security.services;

import java.util.List;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;

public interface IAclGrantedAccessorService {
	public IAclGrantedAccessor fromGroup(UsersGroup group);

	public List<Integer> aliasesfromGroups(List<UsersGroup> group);

	public IAclGrantedAccessor fromGroup(UsersGroup group, AclGrantType grantType);

	public List<Integer> aliasesfromGroups(List<UsersGroup> group, AclGrantType grantType);

	public Integer aliasForEveryone(AclGrantType grantType);

	public IAclGrantedAccessor fromUser(User user);

	public IAclGrantedAccessor fromUser(User user, AclGrantType grantType);

	public IAclGrantedAccessor fromUser(UserInfos user);

	public IAclGrantedAccessor fromUser(UserInfos user, AclGrantType grantType);
}
