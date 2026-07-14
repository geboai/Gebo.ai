package ai.gebo.security.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.acl.IAclGrantedAccess;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IAclGrantedAccessorService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AclGrantedAccessorServiceImpl implements IAclGrantedAccessorService {
	private static final String GROUP = "group";
	private static final String USER = "user";
	private final IAclAliasesDao aliasesDao;
	// The directory, NOT the repository: on a service that does not own the user store
	// this is the REST-backed one, and this class then works unchanged. The ACL aliases
	// stay local (IAclAliasesDao is a per-service replica), so only the groups are remote.
	private final IGSecurityDirectory directory;

	@Override
	public IAclGrantedAccessor fromGroup(UsersGroup group) {
		final String uniqueId = getUniqueId(group);
		final List<Integer> aliases = aliasesDao.findAliasesByAclGrantedUniqueId(uniqueId);
		return new IAclGrantedAccessor() {
			@Override
			public String getMainAclUniqueId() {
				return uniqueId;
			}

			@Override
			public List<IAclGrantedAccess> getAccesses() {
				return List.of(new IAclGrantedAccess() {
					@Override
					public String getAclUniqueId() {

						return uniqueId;
					}

					@Override
					public String getObjectType() {

						return GROUP;
					}

					@Override
					public List<Integer> getOwnedAclAliases() {
						return aliases;
					}
				});
			}
		};
	}

	@Override
	public String getUniqueId(UsersGroup group) {
		String id = GROUP + ":" + group.getCode();
		return id;
	}

	@Override
	public String getUniqueId(UserInfos user) {
		String id = USER + ":" + user.getUsername();
		return id;
	}

	@Override
	public IAclGrantedAccessor fromUser(User user) {

		return fromUser(UserInfos.of(user));
	}

	@Override
	public IAclGrantedAccessor fromUser(UserInfos user) {
		final String uniqueId = getUniqueId(user);
		final List<Integer> aliases = aliasesDao
				.findAliasesByAclGrantedUniqueIdIn(List.of(uniqueId, IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID));
		List<UsersGroup> groups = directory.findGroupsOfUser(user.getUsername());
		List<IAclGrantedAccessor> groupsAccessors = groups.stream().map(this::fromGroup).toList();
		final List<IAclGrantedAccess> accesses = new ArrayList<>();
		final IAclGrantedAccess thisAccess = new IAclGrantedAccess() {
			@Override
			public String getAclUniqueId() {

				return uniqueId;
			}

			@Override
			public String getObjectType() {

				return GROUP;
			}

			@Override
			public List<Integer> getOwnedAclAliases() {
				return aliases;
			}
		};
		accesses.add(thisAccess);
		for (IAclGrantedAccessor groupLevel : groupsAccessors) {
			accesses.addAll(groupLevel.getAccesses());
		}
		return new IAclGrantedAccessor() {
			@Override
			public String getMainAclUniqueId() {
				return uniqueId;
			}

			@Override
			public List<IAclGrantedAccess> getAccesses() {
				return accesses;
			}
		};
	}

	@Override
	public IAclGrantedAccessor fromGroup(UsersGroup group, AclGrantType grantType) {
		final String uniqueId = getUniqueId(group);
		final List<Integer> aliases = aliasesDao.findAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType);
		return new IAclGrantedAccessor() {
			@Override
			public String getMainAclUniqueId() {
				return uniqueId;
			}

			@Override
			public List<IAclGrantedAccess> getAccesses() {
				return List.of(new IAclGrantedAccess() {
					@Override
					public String getAclUniqueId() {

						return uniqueId;
					}

					@Override
					public String getObjectType() {

						return GROUP;
					}

					@Override
					public List<Integer> getOwnedAclAliases() {
						return aliases;
					}
				});
			}
		};

	}

	@Override
	public IAclGrantedAccessor fromUser(User user, AclGrantType grantType) {

		return fromUser(new UserInfosImpl(user), grantType);
	}

	@Override
	public IAclGrantedAccessor fromUser(UserInfos user, AclGrantType grantType) {
		final String uniqueId = getUniqueId(user);
		final List<Integer> aliases = aliasesDao.findAliasesByAclGrantedUniqueIdInAndAclGrantType(
				List.of(uniqueId, IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID), grantType);
		List<UsersGroup> groups = directory.findGroupsOfUser(user.getUsername());

		List<IAclGrantedAccessor> groupsAccessors = groups.stream().map(x -> this.fromGroup(x, grantType)).toList();
		final List<IAclGrantedAccess> accesses = new ArrayList<>();
		final IAclGrantedAccess everyOneAccess = new IAclGrantedAccess() {

			@Override
			public List<Integer> getOwnedAclAliases() {

				return List.of(aliasForEveryone(grantType));
			}

			@Override
			public String getObjectType() {

				return "everyone";
			}

			@Override
			public String getAclUniqueId() {

				return EVERYONE_ACL_UNIQUE_ID;
			}
		};
		accesses.add(everyOneAccess);
		final IAclGrantedAccess thisAccess = new IAclGrantedAccess() {
			@Override
			public String getAclUniqueId() {

				return uniqueId;
			}

			@Override
			public String getObjectType() {

				return GROUP;
			}

			@Override
			public List<Integer> getOwnedAclAliases() {
				return aliases;
			}
		};
		accesses.add(thisAccess);
		for (IAclGrantedAccessor groupLevel : groupsAccessors) {
			accesses.addAll(groupLevel.getAccesses());
		}
		return new IAclGrantedAccessor() {
			@Override
			public String getMainAclUniqueId() {

				return uniqueId;
			}

			@Override
			public List<IAclGrantedAccess> getAccesses() {
				return accesses;
			}
		};
	}

	@Override
	public List<Integer> aliasesfromGroups(List<UsersGroup> group) {
		List<String> groupIds = group.stream().map(x -> getUniqueId(x)).toList();
		List<Integer> data = this.aliasesDao.findAliasesByAclGrantedUniqueIdIn(groupIds);
		return data;
	}

	@Override
	public List<Integer> aliasesfromGroups(List<UsersGroup> group, AclGrantType grantType) {
		List<String> groupIds = group.stream().map(x -> getUniqueId(x)).toList();
		List<Integer> data = this.aliasesDao.findAliasesByAclGrantedUniqueIdInAndAclGrantType(groupIds, grantType);
		return data;
	}

	final Map<AclGrantType, Integer> everyoneAliases = new HashMap<>();

	@Override
	public Integer aliasForEveryone(AclGrantType grantType) {
		if (everyoneAliases.isEmpty()) {
			synchronized (everyoneAliases) {
				List<GAclEntry> acls = List.of(GAclEntry.EVERYONE_READ_ACCESS, GAclEntry.EVERYONE_WRITE_ACCESS,
						GAclEntry.EVERYONE_EXECUTE_ACCESS);
				for (GAclEntry gAclEntry : acls) {
					Integer alias = this.aliasesDao.findAlias(gAclEntry);
					if (alias == null) {
						alias = this.aliasesDao.addAcl(gAclEntry);
					}
					this.everyoneAliases.put(gAclEntry.getGrant(), alias);
				}
			}
		}
		return this.everyoneAliases.get(grantType);
	}

}
