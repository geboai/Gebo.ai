package ai.gebo.security.services;

import java.util.List;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;

public interface IAclGrantedAccessorService {
	/**
	 * The ACL identity of a user - the id its grants are recorded against (e.g.
	 * {@code user:paolo@gebo.ai}).
	 *
	 * <p>
	 * On the interface because it is the naming convention the ACL store is keyed by,
	 * and callers outside this package need it to allocate a principal's aliases.
	 * Re-deriving {@code "user:" + username} at the call site would duplicate that
	 * convention, and a second copy of it is how the two drift apart.
	 * </p>
	 *
	 * @param user the user
	 * @return its ACL unique id
	 */
	public String getUniqueId(UserInfos user);

	/**
	 * The ACL identity of a group (e.g. {@code group:editors}).
	 *
	 * @param group the group
	 * @return its ACL unique id
	 */
	public String getUniqueId(UsersGroup group);

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
