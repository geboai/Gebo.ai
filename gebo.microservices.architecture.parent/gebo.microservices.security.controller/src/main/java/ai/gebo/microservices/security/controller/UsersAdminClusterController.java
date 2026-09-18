/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.controller;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.microservices.cluster.ClusterParticipantsGuard;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

/**
 * {@link IGUsersAdminService} exposed to the other microservices of the cluster:
 * user/group administration for a service that does not own the user store.
 *
 * <p>
 * Mirrors {@link SecurityDirectoryClusterController}'s shape exactly: reachable only
 * from a microservice currently registered in service discovery
 * ({@link ClusterParticipantsGuard#check}, first line of every method, never from the
 * edge), plain {@code @RestController} (this module is only ever a dependency of the
 * service that owns the directory), delegates to the local {@link IGUsersAdminService}
 * (heimdall's {@code GUsersAdminServiceImpl}, from {@code gebo.security.directory.mongo}
 * - already a dependency of this module).
 * </p>
 *
 * <p>
 * <b>Auditing</b>: {@code createUserIfNotExists} - the OAuth2 resource-server
 * auto-provisioning/sync entrypoint - is audited here (a peer microservice asked
 * heimdall to provision/sync a user), in addition to the local
 * {@code GUsersAdminServiceImpl.createUserIfNotExists} which audits the actual write.
 * The other, admin-UI-shaped CRUD endpoints are not separately audited here: this
 * cluster surface is not used by the admin UI (only {@code UsersAdminController} is),
 * so there is nothing to log yet without a real caller.
 * </p>
 *
 * Gebo.ai comment agent
 */
@RestController
@ConditionalOnProperty(prefix = "ai.gebo.security.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@RequestMapping("${ai.gebo.security.cluster.base-path:api/cluster/SecurityController}/UsersAdmin")
public class UsersAdminClusterController {

	private final IGUsersAdminService userAdminService;
	private final GeboClusterParticipants participants;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	public UsersAdminClusterController(IGUsersAdminService userAdminService, GeboClusterParticipants participants,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.userAdminService = userAdminService;
		this.participants = participants;
		this.securityAuditLoggerService = securityAuditLoggerService;
	}

	@PostMapping(value = "insertUser", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser insertUser(@RequestBody @NotNull InsertUserRequest req, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.insertUser(req.getUser(), req.getPassword());
	}

	@PostMapping(value = "updateUser", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser updateUser(@RequestBody @NotNull EditableUser user, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.updateUser(user);
	}

	@GetMapping(value = "findUserByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public EditableUser findUserByUsername(@RequestParam("email") String email, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.findUserByUsername(email);
	}

	@PostMapping(value = "deleteUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUser(@RequestBody @NotNull EditableUser user, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		userAdminService.deleteUser(user);
	}

	@PostMapping(value = "findUserByQbe", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PageResult<UserInfos> findUserByQbe(@RequestBody @NotNull UserQbeRequest req, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		Page<UserInfos> page = userAdminService.findUserByQbe(req.getQbe(), req.toPageable());
		return PageResult.of(page);
	}

	@PostMapping(value = "findEditableUserByQbe", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PageResult<UserInfos> findUserByQbe(@RequestBody @NotNull EditableUserQbeRequest req,
			HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		Page<UserInfos> page = userAdminService.findUserByQbe(req.getQbe(), req.toPageable());
		return PageResult.of(page);
	}

	@PostMapping(value = "insertGroup", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup insertGroup(@RequestBody @NotNull UsersGroup group, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.insertGroup(group);
	}

	@GetMapping(value = "findGroupByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup findGroupByCode(@RequestParam("code") String code, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.findGroupByCode(code);
	}

	@PostMapping(value = "updateGroup", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UsersGroup updateGroup(@RequestBody @NotNull UsersGroup group, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.updateGroup(group);
	}

	@PostMapping(value = "deleteGroup", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGroup(@RequestBody @NotNull UsersGroup group, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		userAdminService.deleteGroup(group);
	}

	@PostMapping(value = "findUsersGroupByQbe", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PageResult<UsersGroup> findUsersGroupByQbe(@RequestBody @NotNull GroupQbeRequest req,
			HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		Page<UsersGroup> page = userAdminService.findUsersGroupByQbe(req.getQbe(), req.toPageable());
		return PageResult.of(page);
	}

	@GetMapping(value = "getAllGroups", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UsersGroup> getAllGroups(HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.getAllGroups();
	}

	@GetMapping(value = "getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserInfos> getAllUsers(HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return userAdminService.getAllUsers();
	}

	/**
	 * Auto-provisioning/sync for the OAuth2 bearer-token authentication path (see
	 * {@code GJwtAuthenticationConverter} and {@code GOauth2ResourceServerUserProvisioner}
	 * in {@code gebo.architecture.security}), which - unlike the interactive
	 * {@code oauth2Login} redirect flow - runs on every microservice, not only
	 * heimdall/monolith. The decision to call this at all (governed by
	 * {@code ai.gebo.security.loginPolicy}) is made by the caller; this endpoint
	 * performs the write unconditionally, exactly like the local
	 * {@link IGUsersAdminService#createUserIfNotExists} it delegates to (which does its
	 * own audit logging of the actual write). This method additionally logs that a
	 * cluster peer requested it, giving the network hop its own audit trail entry.
	 */
	@PostMapping(value = "createUserIfNotExists", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void createUserIfNotExists(@RequestBody @NotNull CreateUserIfNotExistsRequest req,
			HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.USER_AUTO_PROVISION);
		event.setResourceId(req.getUsername());
		try {
			userAdminService.createUserIfNotExists(req.getUsername(), req.getAttributes(), req.getAuthProvider());
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}

	@PostMapping(value = "changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void changePassword(@RequestBody @NotNull ChangePasswordRequest req, HttpServletRequest request)
			throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
		userAdminService.changePassword(req.getUsername(), req.getPassword());
	}

	// --- Wire DTOs ------------------------------------------------------------

	/** A stable, explicit page shape - not Spring Data's own JSON, which can drift. */
	public static class PageResult<T> {
		private List<T> content;
		private long totalElements;
		private int number;
		private int size;

		public static <T> PageResult<T> of(Page<T> page) {
			PageResult<T> out = new PageResult<>();
			out.content = page.getContent();
			out.totalElements = page.getTotalElements();
			out.number = page.getNumber();
			out.size = page.getSize();
			return out;
		}

		public List<T> getContent() {
			return content;
		}

		public void setContent(List<T> content) {
			this.content = content;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
	}

	public static class InsertUserRequest {
		private EditableUser user;
		private String password;

		public EditableUser getUser() {
			return user;
		}

		public void setUser(EditableUser user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	/** Shared shape for a query-by-example request: the example plus page/size. */
	public abstract static class AbstractQbeRequest {
		private int page;
		private int size = 20;

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public PageRequest toPageable() {
			return PageRequest.of(page, size);
		}
	}

	public static class UserQbeRequest extends AbstractQbeRequest {
		private User qbe;

		public User getQbe() {
			return qbe;
		}

		public void setQbe(User qbe) {
			this.qbe = qbe;
		}
	}

	public static class EditableUserQbeRequest extends AbstractQbeRequest {
		private EditableUser qbe;

		public EditableUser getQbe() {
			return qbe;
		}

		public void setQbe(EditableUser qbe) {
			this.qbe = qbe;
		}
	}

	public static class GroupQbeRequest extends AbstractQbeRequest {
		private UsersGroup qbe;

		public UsersGroup getQbe() {
			return qbe;
		}

		public void setQbe(UsersGroup qbe) {
			this.qbe = qbe;
		}
	}

	public static class CreateUserIfNotExistsRequest {
		private String username;
		private Map<String, Object> attributes;
		private AuthProvider authProvider;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		public AuthProvider getAuthProvider() {
			return authProvider;
		}

		public void setAuthProvider(AuthProvider authProvider) {
			this.authProvider = authProvider;
		}
	}

	public static class ChangePasswordRequest {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
