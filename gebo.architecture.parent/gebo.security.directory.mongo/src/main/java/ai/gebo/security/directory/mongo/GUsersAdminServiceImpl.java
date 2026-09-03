/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.directory.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.security.services.impl.AclGrantedAccessorServiceImpl;
import lombok.AllArgsConstructor;

/**
 * The {@link IGUsersAdminService} of a service that <b>owns</b> the user store: it
 * reads/writes Mongo directly. This is heimdall and the monolith.
 *
 * <p>
 * <b>It lives in its own module, and that is the whole mechanism</b> - the same
 * reasoning as {@link MongoSecurityDirectory}. A service that does not own the user
 * store depends on {@code gebo.microservices.security.client} and gets
 * {@code RestUsersAdminService} instead (proxying to heimdall's
 * {@code UsersAdminClusterController}); a service that owns it depends on this
 * module. This class deliberately does NOT live in {@code gebo.architecture.security}
 * (a transitive dependency of virtually everything): shipped there, it would always
 * be present, would always win the {@code @ConditionalOnMissingBean} race, and the
 * remote implementation could never activate anywhere.
 * </p>
 *
 * <h2>Auditing</h2>
 * <p>
 * Every write here emits a {@code userAdministration} security event through
 * {@link IGSecurityAuditLoggerService}, on the dedicated Wazuh-ingested
 * "security-log" appender. This is the <b>store-level</b> tier: it fires no matter
 * which door the write came through - the admin UI ({@code UsersAdminController}),
 * heimdall's cluster surface ({@code UsersAdminClusterController}), the installation
 * bootstrap ({@code GeboFastInstallationSetupService}), the password-reset workflow,
 * or OAuth2 auto-provisioning - several of which have no controller instrumentation
 * of their own. The controller-level events that do exist stay: they record the
 * caller's <i>intent</i> (and outcomes the service never sees, such as an admin who
 * failed to confirm their own password), and correlate to these by
 * {@code correlationId}. That two-tier shape is the one already established for
 * {@code userAutoProvision}.
 * </p>
 *
 * <p>
 * Events carry the identity acted upon in {@code resourceId} and the security-relevant
 * shape of the change in {@code details} - roles, disabled flag, auth provider, group
 * membership size, and for updates the previous values, so a privilege escalation is
 * visible in the log itself rather than only by diffing the database. <b>No password
 * or secret material is ever put in an event.</b>
 * </p>
 *
 * Gebo.ai comment agent
 */
@AllArgsConstructor
public class GUsersAdminServiceImpl implements IGUsersAdminService {

	final UserRepository userRepo; // Repository for user-related database operations

	final UsersGroupRepository groupsRepo; // Repository for groups-related database operations

	// Passwords do not live in the user document any more: they are USERNAME_PASSWORD
	// secrets under "user:<username>". See IGUserPasswordService.
	final IGUserPasswordService userPasswordService;
	final AclGrantedAccessorServiceImpl grantedAccessorService;
	final IAclAliasesDao aclAliasesDao;
	final IGeboSystemUserService systemUserService;
	final IGSecurityAuditLoggerService securityAuditLoggerService;

	/** {@code resourceType} of the events raised for a user identity. */
	private static final String RESOURCE_TYPE_USER = "user";

	/** {@code resourceType} of the events raised for a users group. */
	private static final String RESOURCE_TYPE_GROUP = "usersGroup";

	/**
	 * Fills in and emits an audit event.
	 *
	 * <p>
	 * Takes an already-created {@link SecurityEvent} and never calls
	 * {@code newSecurityEvent()} itself, so the caller-stack that
	 * {@code newSecurityEvent()} captures points at the real service method rather
	 * than at this shared helper - the same rule {@code UsersAdminController} follows.
	 * </p>
	 */
	private void logAdminEvent(SecurityEvent event, String action, String resourceType, String resourceId,
			String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(action);
		event.setResourceType(resourceType);
		event.setResourceId(resourceId);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	/**
	 * The security-relevant shape of a user, for an event's {@code details}: what it
	 * can do (roles), whether it can be used at all (disabled), and how it
	 * authenticates. Never the password.
	 */
	private static void describeUser(SecurityEvent event, String prefix, List<String> roles, Boolean disabled,
			Object authProvider) {
		event.getDetails().put(prefix + "Roles", roles == null ? List.of() : new ArrayList<>(roles));
		event.getDetails().put(prefix + "Disabled", disabled);
		event.getDetails().put(prefix + "AuthProvider", authProvider == null ? null : authProvider.toString());
	}

	/**
	 * The security-relevant shape of a group: its membership, which is what grants
	 * whatever the group is granted.
	 */
	private static void describeGroup(SecurityEvent event, String prefix, UsersGroup group) {
		List<String> members = group == null || group.getUserIds() == null ? List.of() : group.getUserIds();
		event.getDetails().put(prefix + "MembersCount", members.size());
		event.getDetails().put(prefix + "Members", new ArrayList<>(members));
	}

	/**
	 * Inserts a new user into the database after validating uniqueness and
	 * password.
	 *
	 * @param user     The user details to insert.
	 * @param password The plaintext password of the user.
	 * @return An EditableUser object representing the newly created user.
	 * @throws IllegalStateException if the user already exists or the password is
	 *                               invalid.
	 */
	@Override
	public EditableUser insertUser(EditableUser user, String password) {
		// Opened before the reserved-identity guard below, so that an attempt to create
		// the system user is itself audited (as DENIED) rather than only rejected.
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String username = user != null ? user.getUsername() : null;
		if (user != null)
			describeUser(event, "new", user.getRoles(), user.getDisabled(), user.getAuthProvider());
		// The system identity is virtual: it is defined by configuration, has no Mongo
		// document, and must never acquire one - a persisted row would shadow the
		// configured identity (and could be given a real, loginable password, turning
		// the platform's own ADMIN account into one a human can sign in as).
		//
		// This is the single chokepoint for user creation: createUserIfNotExists()
		// delegates here, and that is the hook OAuth2 login auto-provisions through. So
		// guarding here closes BOTH doors - admin creation and federation - at once.
		if (systemUserService.isSystemUser(username)) {
			event.getDetails().put("reason", "reservedSystemIdentity");
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_INSERT, RESOURCE_TYPE_USER, username,
					SecurityAuditTaxonomy.Outcome.DENIED);
			throw new IllegalStateException(
					"The user " + systemUserService.getUsername() + " is the reserved Gebo.ai system identity "
							+ "and cannot be created; it is defined by ai.gebo.security.system-user");
		}
		try {
			Optional<User> alreadyCheck = userRepo.findById(username);
			if (alreadyCheck.isPresent())
				throw new IllegalStateException("Already existing user");
			User u = new User();
			u.assignValues(user);
			if (password == null || password.trim().length() == 0)
				throw new IllegalStateException("Empty password forbidden");
			// Written BEFORE the user row, and keyed on the username assignValues just
			// normalised. The two stores cannot be updated atomically, so the order is chosen
			// for which half is safe to have on its own: a password secret with no user is
			// inert (nothing can log in as a user that does not exist, and a retry overwrites
			// it), whereas a user row with no password would be an account nobody - including
			// its owner - could ever authenticate as.
			try {
				userPasswordService.storePassword(u.getUsername(), password);
			} catch (GeboCryptSecretException e) {
				throw new IllegalStateException("Cannot store the password of user " + u.getUsername(), e);
			}
			EditableUser out = new EditableUser(u = userRepo.insert(u));
			String id = grantedAccessorService.getUniqueId(new UserInfosImpl(u));
			AclGrantType[] allPossibleGrants = AclGrantType.values();
			for (int i = 0; i < allPossibleGrants.length; i++) {
				AclGrantType aclGrantType = allPossibleGrants[i];
				GAclEntry entry = new GAclEntry(id, aclGrantType);
				aclAliasesDao.addAcl(entry);
			}
			// The persisted username, not the one asked for: assignValues lower-cases it, and
			// the persisted form is what every later event about this identity will carry.
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_INSERT, RESOURCE_TYPE_USER, u.getUsername(),
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return out;
		} catch (RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_INSERT, RESOURCE_TYPE_USER, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Updates the information of an existing user.
	 *
	 * @param user The user details to update.
	 * @return An EditableUser object representing the updated user.
	 * @throws RuntimeException if the user does not exist.
	 */
	@Override
	public EditableUser updateUser(EditableUser user) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String username = user != null ? user.getUsername() : null;
		try {
			Optional<User> u = userRepo.findById(username);
			if (u.isEmpty())
				throw new RuntimeException("Non existent user");
			User usr = u.get();
			// Captured before assignValues overwrites them: a grant of ADMIN, or the
			// re-enabling of a disabled account, is the most security-relevant thing this
			// method can do, and it is only visible as a *difference*.
			List<String> previousRoles = usr.getRoles() == null ? List.of() : new ArrayList<>(usr.getRoles());
			Boolean previousDisabled = usr.getDisabled();
			AuthProvider previousProvider = usr.getProvider();
			usr.assignValues(user);
			EditableUser out = new EditableUser(userRepo.save(usr));
			describeUser(event, "previous", previousRoles, previousDisabled, previousProvider);
			describeUser(event, "new", usr.getRoles(), usr.getDisabled(), usr.getProvider());
			event.getDetails().put("rolesChanged",
					!Objects.equals(previousRoles, usr.getRoles() == null ? List.of() : usr.getRoles()));
			event.getDetails().put("disabledChanged", !Objects.equals(previousDisabled, usr.getDisabled()));
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_UPDATE, RESOURCE_TYPE_USER, usr.getUsername(),
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return out;
		} catch (RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_UPDATE, RESOURCE_TYPE_USER, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Deletes a user from the database.
	 *
	 * @param user The user to delete.
	 */
	@Override
	public void deleteUser(EditableUser user) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String username = user != null ? user.getUsername() : null;
		if (user != null)
			describeUser(event, "deleted", user.getRoles(), user.getDisabled(), user.getAuthProvider());
		try {
			userRepo.deleteById(username);
			// The password outlives the user document unless it is deleted too - it is in a
			// different store. Done after the row so a failure here leaves an orphan secret
			// (inert: no user, no login) rather than a live account with no password.
			try {
				userPasswordService.deletePassword(username);
			} catch (GeboCryptSecretException e) {
				throw new IllegalStateException("Cannot delete the password of user " + username, e);
			}
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_DELETE, RESOURCE_TYPE_USER, username,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logAdminEvent(event, SecurityAuditTaxonomy.Action.USER_DELETE, RESOURCE_TYPE_USER, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Inserts a new user group into the database.
	 *
	 * @param group The group details to insert.
	 * @return The newly created UsersGroup object.
	 */
	@Override
	public UsersGroup insertGroup(UsersGroup group) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String code = group != null ? group.getCode() : null;
		describeGroup(event, "new", group);
		try {
			group = groupsRepo.insert(group);
			String id = grantedAccessorService.getUniqueId(group);
			AclGrantType[] allPossibleGrants = AclGrantType.values();
			for (int i = 0; i < allPossibleGrants.length; i++) {
				AclGrantType aclGrantType = allPossibleGrants[i];
				GAclEntry entry = new GAclEntry(id, aclGrantType);
				aclAliasesDao.addAcl(entry);
			}
			logAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_INSERT, RESOURCE_TYPE_GROUP, group.getCode(),
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return group;
		} catch (RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_INSERT, RESOURCE_TYPE_GROUP, code,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Updates the information of an existing user group.
	 *
	 * @param group The group details to update.
	 * @return The updated UsersGroup object.
	 */
	@Override
	public UsersGroup updateGroup(UsersGroup group) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String code = group != null ? group.getCode() : null;
		try {
			// An extra read on a cold admin path, in exchange for the membership *delta*
			// being in the log: a group is an ACL principal, so who was added to (or
			// dropped from) it is the change that matters, and save() overwrites it.
			Optional<UsersGroup> previous = code == null ? Optional.empty() : groupsRepo.findById(code);
			describeGroup(event, "previous", previous.orElse(null));
			describeGroup(event, "new", group);
			event.getDetails().put("created", previous.isEmpty());
			UsersGroup saved = groupsRepo.save(group);
			logAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_UPDATE, RESOURCE_TYPE_GROUP, code,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
			return saved;
		} catch (RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_UPDATE, RESOURCE_TYPE_GROUP, code,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Deletes a user group from the database.
	 *
	 * @param group The group to delete.
	 */
	@Override
	public void deleteGroup(UsersGroup group) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		String code = group != null ? group.getCode() : null;
		describeGroup(event, "deleted", group);
		try {
			groupsRepo.delete(group);
			logAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_DELETE, RESOURCE_TYPE_GROUP, code,
					SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			event.getDetails().put("error", e.getMessage());
			logAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_DELETE, RESOURCE_TYPE_GROUP, code,
					SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Finds users by query-by-example, supporting pagination.
	 *
	 * @param qbe      The example user to search for.
	 * @param pageable The pagination information.
	 * @return A pageable list of users matching the example.
	 */
	@Override
	public Page<UserInfos> findUserByQbe(User qbe, Pageable pageable) {
		return userRepo.findBy(Example.of(qbe), pageable);
	}

	/**
	 * Finds user groups by query-by-example, supporting pagination.
	 *
	 * @param qbe      The example group to search for.
	 * @param pageable The pagination information.
	 * @return A pageable list of groups matching the example.
	 */
	@Override
	public Page<UsersGroup> findUsersGroupByQbe(UsersGroup qbe, Pageable pageable) {
		return groupsRepo.findBy(Example.of(qbe), pageable);
	}

	/**
	 * Finds a user by their username.
	 *
	 * @param email The username of the user to find.
	 * @return An EditableUser if found, otherwise null.
	 */
	@Override
	public EditableUser findUserByUsername(String email) {
		Optional<User> u = userRepo.findById(email);
		if (u.isPresent())
			return new EditableUser(u.get());
		return null;
	}

	/**
	 * Finds a group by its code.
	 *
	 * @param code The code of the group to find.
	 * @return The UsersGroup if found, otherwise null.
	 */
	@Override
	public UsersGroup findGroupByCode(String code) {
		Optional<UsersGroup> grOptional = groupsRepo.findById(code);
		if (grOptional.isPresent())
			return grOptional.get();
		return null;
	}

	/**
	 * Retrieves all the user groups from the database.
	 *
	 * @return A list of all UsersGroup objects.
	 */
	@Override
	public List<UsersGroup> getAllGroups() {
		return groupsRepo.findAll();
	}

	/**
	 * Retrieves all users from the database and converts them into UserInfos.
	 *
	 * @return A list of UserInfos representing all users.
	 */
	@Override
	public List<UserInfos> getAllUsers() {
		return new ArrayList<UserInfos>(userRepo.findAll().stream().map(x -> {
			return new UserInfosImpl(x);
		}).toList());
	}

	/**
	 * Auto-provisioning/sync chokepoint for the OAuth2 bearer-token (resource-server)
	 * authentication path - reached locally when this service owns the user store, or
	 * remotely via {@code UsersAdminClusterController} when a peer microservice's
	 * {@code RestUsersAdminService} calls it. Audited with its own
	 * {@code userAutoProvision} action - distinct from the {@code userInsert} that
	 * {@link #insertUser} raises when this decides to create - so that the log
	 * distinguishes "an unknown federated identity was let in" from "an administrator
	 * created an account", even though the first produces the second.
	 */
	@Override
	public void createUserIfNotExists(String email, Map<String, Object> attributes, AuthProvider authProvider) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.USER_AUTO_PROVISION);
		event.setResourceId(email);
		try {
			EditableUser user = this.findUserByUsername(email);
			if (user == null) {
				user = new EditableUser();
				user.setUsername(email);
				user.setRoles(List.of("USER"));
				user.setDisabled(false);
				user.setAuthProvider(authProvider);
				this.insertUser(user, UUID.randomUUID().toString());
				event.getDetails().put("created", true);
			} else {
				event.getDetails().put("created", false);
			}
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}

	/**
	 * Sets another account's password.
	 *
	 * <p>
	 * The actual write to the secret store is audited one tier down, by
	 * {@code IGUserPasswordService} ({@code passwordSecretStore}). What is audited
	 * <b>here</b> is the case that tier never sees: a change asked for on a username
	 * that does not exist, which this method deliberately swallows. Silently doing
	 * nothing is fine for the caller; silently leaving no trace is not, because a run
	 * of such calls is what username enumeration through this endpoint looks like.
	 * </p>
	 */
	@Override
	public void changePassword(String username, String password) throws GeboCryptSecretException {

		Optional<User> user = this.userRepo.findById(username);
		if (user.isPresent()) {
			// The persisted username, not the one asked for: it is what the secret's context
			// code was built from.
			userPasswordService.storePassword(user.get().getUsername(), password);
		} else {
			SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
			event.getDetails().put("reason", "unknownUser");
			logAdminEvent(event, SecurityAuditTaxonomy.Action.PASSWORD_CHANGE_ADMIN, RESOURCE_TYPE_USER, username,
					SecurityAuditTaxonomy.Outcome.FAILURE);
		}

	}

	@Override
	public Page<UserInfos> findUserByQbe(EditableUser qbe, Pageable pageable) {
		return this.userRepo.findByQbe(qbe, pageable);
	}

}
