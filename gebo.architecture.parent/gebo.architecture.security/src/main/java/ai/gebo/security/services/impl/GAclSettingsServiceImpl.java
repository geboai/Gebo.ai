/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.acl.IAclGrantedAccess;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AclGrantAssignment;
import ai.gebo.security.model.AclOwnerRef;
import ai.gebo.security.model.AclOwnerType;
import ai.gebo.security.model.AclSelectableOwners;
import ai.gebo.security.model.AclSystemMode;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGAclSettingsService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.IGUsersAdminService;
import lombok.AllArgsConstructor;

/**
 * Default {@link IGAclSettingsService} implementation. Owner unique-ids follow
 * the same convention used across the security layer: {@code everyone:...} (see
 * {@link IAclGrantedAccess#EVERYONE_ACL_UNIQUE_ID}), {@code group:<code>} and
 * {@code user:<username>}.
 */
@Service
@AllArgsConstructor
public class GAclSettingsServiceImpl implements IGAclSettingsService {

	private static final String GROUP_PREFIX = "group:";
	private static final String USER_PREFIX = "user:";

	private final IGSecurityService securityService;
	private final GeboSecurityConfig securityConfig;
	private final IGUsersAdminService usersAdminService;
	private final IAclAliasesDao aclAliasesDao;

	@Override
	public AclSystemMode getSystemAclMode() {
		return new AclSystemMode(securityService.getPlatformContentAccessPolicy(), securityConfig.isUseAcl());
	}

	@Override
	public AclSelectableOwners getSelectableOwners() {
		AclOwnerRef everyone = new AclOwnerRef(AclOwnerType.EVERYONE, IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID,
				"Everyone", "Every authenticated user", null);

		List<AclOwnerRef> groups = new ArrayList<>();
		List<UsersGroup> allGroups = usersAdminService.getAllGroups();
		if (allGroups != null) {
			for (UsersGroup group : allGroups) {
				String label = group.getDescription() != null && !group.getDescription().isBlank()
						? group.getDescription()
						: group.getCode();
				groups.add(new AclOwnerRef(AclOwnerType.GROUP, group.getCode(), label, group.getDescription(),
						group.getUserIds() != null ? new ArrayList<>(group.getUserIds()) : new ArrayList<>()));
			}
		}

		List<AclOwnerRef> users = new ArrayList<>();
		List<UserInfos> allUsers = usersAdminService.getAllUsers();
		if (allUsers != null) {
			for (UserInfos user : allUsers) {
				if (user.getDisabled() != null && user.getDisabled())
					continue;
				users.add(new AclOwnerRef(AclOwnerType.USER, user.getUsername(), userLabel(user), null, null));
			}
		}

		return new AclSelectableOwners(everyone, groups, users);
	}

	private String userLabel(UserInfos user) {
		String name = user.getName() != null ? user.getName().trim() : "";
		String surname = user.getSourname() != null ? user.getSourname().trim() : "";
		String full = (name + " " + surname).trim();
		return full.isEmpty() ? user.getUsername() : full + " (" + user.getUsername() + ")";
	}

	@Override
	public List<AclGrantAssignment> resolveAliases(List<Integer> aliases) {
		if (aliases == null || aliases.isEmpty())
			return new ArrayList<>();

		// Accumulate the grants held by each distinct owner.
		Map<OwnerKey, EnumSet<AclGrantType>> ownerGrants = new LinkedHashMap<>();
		for (Integer alias : aliases) {
			if (alias == null)
				continue;
			GAclEntry entry = aclAliasesDao.findAcl(alias.intValue());
			if (entry == null || entry.getGrant() == null || entry.getAclGrantedUniqueId() == null)
				continue;
			OwnerKey key = parseOwner(entry.getAclGrantedUniqueId());
			if (key == null)
				continue;
			ownerGrants.computeIfAbsent(key, k -> EnumSet.noneOf(AclGrantType.class)).add(entry.getGrant());
		}
		if (ownerGrants.isEmpty())
			return new ArrayList<>();

		collapse(ownerGrants);

		List<AclGrantAssignment> out = new ArrayList<>();
		for (Map.Entry<OwnerKey, EnumSet<AclGrantType>> e : ownerGrants.entrySet()) {
			if (e.getValue().isEmpty())
				continue;
			out.add(new AclGrantAssignment(e.getKey().type(), e.getKey().code(), new ArrayList<>(e.getValue())));
		}
		return out;
	}

	/**
	 * Applies the display collapse rules in place, per grant type: everyone hides
	 * groups/users, then a granted group hides its member users.
	 */
	private void collapse(Map<OwnerKey, EnumSet<AclGrantType>> ownerGrants) {
		// everyone wins: strip everyone's grants from every group/user.
		EnumSet<AclGrantType> everyoneGrants = null;
		for (Map.Entry<OwnerKey, EnumSet<AclGrantType>> e : ownerGrants.entrySet()) {
			if (e.getKey().type() == AclOwnerType.EVERYONE)
				everyoneGrants = e.getValue();
		}
		if (everyoneGrants != null && !everyoneGrants.isEmpty()) {
			for (Map.Entry<OwnerKey, EnumSet<AclGrantType>> e : ownerGrants.entrySet()) {
				if (e.getKey().type() != AclOwnerType.EVERYONE)
					e.getValue().removeAll(everyoneGrants);
			}
		}

		// group covers members: for each granted group, strip the grant from its
		// member users.
		Map<String, List<String>> groupMembers = groupMembership();
		for (Map.Entry<OwnerKey, EnumSet<AclGrantType>> user : ownerGrants.entrySet()) {
			if (user.getKey().type() != AclOwnerType.USER || user.getValue().isEmpty())
				continue;
			String username = user.getKey().code();
			for (Map.Entry<OwnerKey, EnumSet<AclGrantType>> group : ownerGrants.entrySet()) {
				if (group.getKey().type() != AclOwnerType.GROUP || group.getValue().isEmpty())
					continue;
				List<String> members = groupMembers.get(group.getKey().code());
				if (members != null && members.contains(username))
					user.getValue().removeAll(group.getValue());
			}
		}
	}

	private Map<String, List<String>> groupMembership() {
		Map<String, List<String>> membership = new LinkedHashMap<>();
		List<UsersGroup> allGroups = usersAdminService.getAllGroups();
		if (allGroups != null) {
			for (UsersGroup group : allGroups) {
				membership.put(group.getCode(), group.getUserIds() != null ? group.getUserIds() : List.of());
			}
		}
		return membership;
	}

	@Override
	public List<Integer> encodeAssignments(List<AclGrantAssignment> assignments) {
		Set<Integer> out = new LinkedHashSet<>();
		if (assignments == null || assignments.isEmpty())
			return new ArrayList<>();
		for (AclGrantAssignment assignment : assignments) {
			if (assignment == null || assignment.getOwnerType() == null || assignment.getGrants() == null)
				continue;
			String uniqueId = ownerUniqueId(assignment.getOwnerType(), assignment.getOwnerCode());
			if (uniqueId == null)
				continue;
			for (AclGrantType grant : assignment.getGrants()) {
				if (grant == null)
					continue;
				GAclEntry entry = new GAclEntry(uniqueId, grant);
				Integer alias = aclAliasesDao.findAlias(entry);
				if (alias == null)
					alias = aclAliasesDao.addAcl(entry);
				out.add(alias);
			}
		}
		return new ArrayList<>(out);
	}

	private String ownerUniqueId(AclOwnerType type, String code) {
		switch (type) {
		case EVERYONE:
			return IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID;
		case GROUP:
			return code != null ? GROUP_PREFIX + code : null;
		case USER:
			return code != null ? USER_PREFIX + code : null;
		default:
			return null;
		}
	}

	private OwnerKey parseOwner(String aclGrantedUniqueId) {
		if (IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID.equals(aclGrantedUniqueId))
			return new OwnerKey(AclOwnerType.EVERYONE, IAclGrantedAccess.EVERYONE_ACL_UNIQUE_ID);
		if (aclGrantedUniqueId.startsWith(GROUP_PREFIX))
			return new OwnerKey(AclOwnerType.GROUP, aclGrantedUniqueId.substring(GROUP_PREFIX.length()));
		if (aclGrantedUniqueId.startsWith(USER_PREFIX))
			return new OwnerKey(AclOwnerType.USER, aclGrantedUniqueId.substring(USER_PREFIX.length()));
		return null;
	}

	/**
	 * Identity of an ACL owner used for accumulation while resolving aliases.
	 */
	private static record OwnerKey(AclOwnerType type, String code) {
	}
}
