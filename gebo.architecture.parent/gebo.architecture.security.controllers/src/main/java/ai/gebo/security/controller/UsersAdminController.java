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
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.CurrentUser;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserPrincipal;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AI generated comments Rest controller for managing users and user groups. All
 * operations require the user to have 'ADMIN' role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/UsersAdminController")
@AllArgsConstructor
public class UsersAdminController {

	private final IGUsersAdminService userAdminService;
	private final IGSecurityService securityService;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	// Takes an already-created SecurityEvent (never calls newSecurityEvent()
	// itself) so newSecurityEvent()'s caller-stack capture points at the real
	// endpoint method, not at this shared helper.
	private void logUserAdminEvent(SecurityEvent event, String action, String resourceId, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(action);
		event.setResourceId(resourceId);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
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
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			EditableUser inserted = userAdminService.insertUser(p.user, p.password);
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.USER_INSERT,
					p.user != null ? p.user.getUsername() : null, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return inserted;
		} catch (RuntimeException e) {
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.USER_INSERT,
					p.user != null ? p.user.getUsername() : null, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Updates an existing user.
	 *
	 * @param user the user details to update
	 * @return the updated EditableUser object
	 */
	@PostMapping(value = "updateUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser updateUser(@Valid @RequestBody EditableUser user) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			EditableUser updated = userAdminService.updateUser(user);
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.USER_UPDATE,
					user != null ? user.getUsername() : null, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return updated;
		} catch (RuntimeException e) {
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.USER_UPDATE,
					user != null ? user.getUsername() : null, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Deletes a specified user.
	 * 
	 * @param user the user to delete
	 */
	@PostMapping(value = "deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUser(@Valid @RequestBody EditableUser user) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			userAdminService.deleteUser(user);
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.USER_DELETE,
					user != null ? user.getUsername() : null, SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.USER_DELETE,
					user != null ? user.getUsername() : null, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Inserts a new user group into the system.
	 * 
	 * @param group the group details
	 * @return the inserted UsersGroup object
	 */
	@PostMapping(value = "insertGroup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup insertGroup(@Valid @RequestBody UsersGroup group) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			UsersGroup inserted = userAdminService.insertGroup(group);
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_INSERT,
					group != null ? group.getCode() : null, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return inserted;
		} catch (RuntimeException e) {
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_INSERT,
					group != null ? group.getCode() : null, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Updates an existing user group.
	 * 
	 * @param group the group details to update
	 * @return the updated UsersGroup object
	 */
	@PostMapping(value = "updateGroup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup updateGroup(@Valid @RequestBody UsersGroup group) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			UsersGroup updated = userAdminService.updateGroup(group);
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_UPDATE,
					group != null ? group.getCode() : null, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return updated;
		} catch (RuntimeException e) {
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_UPDATE,
					group != null ? group.getCode() : null, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * Deletes a specified user group.
	 * 
	 * @param group the group to delete
	 */
	@PostMapping(value = "deleteGroup", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGroup(@Valid @RequestBody UsersGroup group) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			userAdminService.deleteGroup(group);
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_DELETE,
					group != null ? group.getCode() : null, SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			logUserAdminEvent(event, SecurityAuditTaxonomy.Action.GROUP_DELETE,
					group != null ? group.getCode() : null, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	/**
	 * DTO for querying users by example (QBE) with pagination.
	 */
	public static class FindUserByQbeParam {
		@NotNull
		public EditableUser qbe = null;
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

	@Data
	public static class ChangeUsernamePasswordData {
		@NotNull
		private String username = null;
		@NotNull
		private String password = null;
		@NotNull
		private String confirmpassword = null;
		@NotNull
		private String currentUserPassword = null;
	}

	@PostMapping(value = "changeUserPassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GUserMessage changeUserPassword(@Valid @RequestBody ChangeUsernamePasswordData changePwdData)
			throws GeboCryptSecretException {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.PASSWORD_CHANGE_ADMIN);
		event.setResourceId(changePwdData != null ? changePwdData.getUsername() : null);
		try {
			boolean pwdOk = this.securityService.checkActualUserPassword(changePwdData.getCurrentUserPassword());
			GUserMessage result;
			if (pwdOk) {
				if (changePwdData.getPassword().equals(changePwdData.getConfirmpassword())) {
					userAdminService.changePassword(changePwdData.getUsername(), changePwdData.getPassword());
					result = GUserMessage.successMessage("Password changed with success", "New password set");
					event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
				} else {
					result = GUserMessage.errorMessage("Password and confirm password problem",
							"New password and confirm password for the user are not equal");
					event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
				}
			} else {
				result = GUserMessage.errorMessage("Your password is incorrect",
						"Let's try again digitating your password");
				event.setOutcome(SecurityAuditTaxonomy.Outcome.DENIED);
			}
			securityAuditLoggerService.log(event);
			return result;
		} catch (RuntimeException | GeboCryptSecretException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			securityAuditLoggerService.log(event);
			throw e;
		}
	}
}