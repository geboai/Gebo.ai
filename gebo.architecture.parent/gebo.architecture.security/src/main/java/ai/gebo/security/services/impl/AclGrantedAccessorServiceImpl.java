package ai.gebo.security.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.acl.IAclGrantedAccess;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IAclGrantedAccessorService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AclGrantedAccessorServiceImpl implements IAclGrantedAccessorService {
	private static final String GROUP = "group";
	private static final String USER = "user";
	private final IAclAliasesDao aliasesDao;
	private final UsersGroupRepository groupsRepo;

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

	String getUniqueId(UsersGroup group) {
		String id = GROUP + ":" + group.getCode();
		return id;
	}

	String getUniqueId(UserInfos user) {
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
		final List<Integer> aliases = aliasesDao.findAliasesByAclGrantedUniqueId(uniqueId);
		List<UsersGroup> groups = groupsRepo.findByUserIdsIn(user.getUsername());
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
		final List<Integer> aliases = aliasesDao.findAliasesByAclGrantedUniqueIdAndAclGrantType(uniqueId, grantType);
		List<UsersGroup> groups = groupsRepo.findByUserIdsIn(user.getUsername());
		List<IAclGrantedAccessor> groupsAccessors = groups.stream().map(x -> this.fromGroup(x, grantType)).toList();
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

}
