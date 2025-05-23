/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.utils.DataPage;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGUsersAdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Rest controller for managing users and user groups. 
 * All operations require the user to have 'ADMIN' role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/UsersAdminController")
public class UsersAdminController {
	
	@Autowired
	IGUsersAdminService userAdminService;

	/**
	 * Default constructor for UsersAdminController.
	 */
	public UsersAdminController() {

	}

	/**
	 * DTO for inserting a user with mandatory fields.
	 */
	public static class InsertUserParam {
		@NotNull
		public EditableUser user = null;
		@NotNull
		public String password = null;
	}

	/**
	 * Inserts a new user into the system.
	 *
	 * @param p the parameters containing user details and password
	 * @return the inserted EditableUser object
	 */
	@PostMapping(value = "insertUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser insertUser(@Valid @RequestBody InsertUserParam p) {

		return userAdminService.insertUser(p.user, p.password);
	}

	/**
	 * Updates an existing user.
	 *
	 * @param user the user details to update
	 * @return the updated EditableUser object
	 */
	@PostMapping(value = "updateUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser updateUser(@Valid @RequestBody EditableUser user) {

		return userAdminService.updateUser(user);
	}

	/**
	 * Deletes a specified user.
	 * 
	 * @param user the user to delete
	 */
	@PostMapping(value = "deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUser(@Valid @RequestBody EditableUser user) {
		userAdminService.deleteUser(user);

	}

	/**
	 * Inserts a new user group into the system.
	 * 
	 * @param group the group details
	 * @return the inserted UsersGroup object
	 */
	@PostMapping(value = "insertGroup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup insertGroup(@Valid @RequestBody UsersGroup group) {

		return userAdminService.insertGroup(group);
	}

	/**
	 * Updates an existing user group.
	 * 
	 * @param group the group details to update
	 * @return the updated UsersGroup object
	 */
	@PostMapping(value = "updateGroup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup updateGroup(@Valid @RequestBody UsersGroup group) {
		return userAdminService.updateGroup(group);
	}

	/**
	 * Deletes a specified user group.
	 * 
	 * @param group the group to delete
	 */
	@PostMapping(value = "deleteGroup", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGroup(@Valid @RequestBody UsersGroup group) {
		userAdminService.deleteGroup(group);

	}

	/**
	 * DTO for querying users by example (QBE) with pagination.
	 */
	public static class FindUserByQbeParam {
		@NotNull
		public User qbe = null;
		@NotNull
		public DataPage page = null;
	}

	/**
	 * Finds users matching the example (QBE) criteria.
	 * 
	 * @param param the parameters containing query example and pagination
	 * @return a page of UserInfos matching the criteria
	 */
	@PostMapping(value = "findUserByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<UserInfos> findUserByQbe(@Valid @RequestBody FindUserByQbeParam param) {
		return userAdminService.findUserByQbe(param.qbe, param.page.toPageable());
	}

	/**
	 * DTO for querying user groups by example (QBE) with pagination.
	 */
	public static class FindUsersGroupParam {
		@NotNull
		public UsersGroup qbe = null;
		@NotNull
		public DataPage page = null;
	}

	/**
	 * Finds user groups matching the example (QBE) criteria.
	 * 
	 * @param param the parameters containing query example and pagination
	 * @return a page of UsersGroup matching the criteria
	 */
	@PostMapping(value = "findUsersGroupByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<UsersGroup> findUsersGroupByQbe(@Valid @RequestBody FindUsersGroupParam param) {

		return userAdminService.findUsersGroupByQbe(param.qbe, param.page.toPageable());
	}

	/**
	 * Finds a user by their email/username.
	 * 
	 * @param email the email/username of the user
	 * @return the found EditableUser object
	 */
	@GetMapping(value = "findUserByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser findUserByUsername(@RequestParam("email") String email) {
		return userAdminService.findUserByUsername(email);
	}

	/**
	 * Finds a group by its unique code.
	 * 
	 * @param code the code of the group
	 * @return the found UsersGroup object
	 */
	@GetMapping(value = "findGroupByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup findGroupByCode(@RequestParam("code") String code) {
		return userAdminService.findGroupByCode(code);
	}

	/**
	 * Retrieves all user groups in the system.
	 * 
	 * @return a list of all UsersGroup objects
	 */
	@GetMapping(value = "getAllGroups", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UsersGroup> getAllGroups() {
		return userAdminService.getAllGroups();
	}

	/**
	 * Retrieves all users in the system.
	 * 
	 * @return a list of all UserInfos objects
	 */
	@GetMapping(value = "getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserInfos> getAllUsers() {
		return userAdminService.getAllUsers();
	}
}