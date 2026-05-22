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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGUsersAdminService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments Implementation of the IGUsersAdminService interface to
 * handle user and group management.
 */
@Service
@AllArgsConstructor
public class GUsersAdminServiceImpl implements IGUsersAdminService {

	final UserRepository userRepo; // Repository for user-related database operations

	final UsersGroupRepository groupsRepo; // Repository for groups-related database operations

	final PasswordEncoder passwordEncoder; // Encoder for hashing user passwords
	final AclGrantedAccessorServiceImpl grantedAccessorService;
	final IAclAliasesDao aclAliasesDao;
	final IGeboCryptingService cryptService;

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
		Optional<User> alreadyCheck = userRepo.findById(user.getUsername());
		if (alreadyCheck.isPresent())
			throw new IllegalStateException("Already existing user");
		User u = new User();
		u.assignValues(user);
		if (password == null || password.trim().length() == 0)
			throw new IllegalStateException("Empty password forbidden");
		u.setPassword(passwordEncoder.encode(password));
		EditableUser out = new EditableUser(u = userRepo.insert(u));
		String id = grantedAccessorService.getUniqueId(new UserInfosImpl(u));
		AclGrantType[] allPossibleGrants = AclGrantType.values();
		for (int i = 0; i < allPossibleGrants.length; i++) {
			AclGrantType aclGrantType = allPossibleGrants[i];
			GAclEntry entry = new GAclEntry(id, aclGrantType);
			aclAliasesDao.addAcl(entry);
		}
		return out;
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
		Optional<User> u = userRepo.findById(user.getUsername());
		if (u.isEmpty())
			throw new RuntimeException("Non existent user");
		User usr = u.get();
		usr.assignValues(user);
		return new EditableUser(userRepo.save(usr));
	}

	/**
	 * Deletes a user from the database.
	 * 
	 * @param user The user to delete.
	 */
	@Override
	public void deleteUser(EditableUser user) {
		userRepo.deleteById(user.getUsername());

	}

	/**
	 * Inserts a new user group into the database.
	 * 
	 * @param group The group details to insert.
	 * @return The newly created UsersGroup object.
	 */
	@Override
	public UsersGroup insertGroup(UsersGroup group) {
		group = groupsRepo.insert(group);
		String id = grantedAccessorService.getUniqueId(group);
		AclGrantType[] allPossibleGrants = AclGrantType.values();
		for (int i = 0; i < allPossibleGrants.length; i++) {
			AclGrantType aclGrantType = allPossibleGrants[i];
			GAclEntry entry = new GAclEntry(id, aclGrantType);
			aclAliasesDao.addAcl(entry);
		}
		return group;
	}

	/**
	 * Updates the information of an existing user group.
	 * 
	 * @param group The group details to update.
	 * @return The updated UsersGroup object.
	 */
	@Override
	public UsersGroup updateGroup(UsersGroup group) {
		return groupsRepo.save(group);
	}

	/**
	 * Deletes a user group from the database.
	 * 
	 * @param group The group to delete.
	 */
	@Override
	public void deleteGroup(UsersGroup group) {
		groupsRepo.delete(group);
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

	@Override
	public void createUserIfNotExists(String email, Map<String, Object> attributes, AuthProvider authProvider) {
		EditableUser user = this.findUserByUsername(email);
		if (user == null) {
			user = new EditableUser();
			user.setUsername(email);
			user.setRoles(List.of("USER"));
			user.setDisabled(false);
			user.setAuthProvider(authProvider);
			this.insertUser(user, UUID.randomUUID().toString());
		}
	}

	@Override
	public void changePassword(String username, String password) throws GeboCryptSecretException {

		Optional<User> user = this.userRepo.findById(username);
		if (user.isPresent()) {
			User usr = user.get();
			usr.setPassword(cryptService.crypt(password));
			userRepo.save(usr);
		}

	}

	@Override
	public Page<UserInfos> findUserByQbe(EditableUser qbe, Pageable pageable) {
		return this.userRepo.findByQbe(qbe, pageable);
	}

}