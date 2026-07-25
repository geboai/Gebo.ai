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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.microservices.cluster.ClusterParticipantsGuard;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityDirectory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

/**
 * The security <b>directory</b> exposed to the other microservices of the
 * cluster: who the users are, which groups they are in, and whether a password is
 * theirs.
 *
 * <p>
 * This is the entire server-proxy for security, and that is the point. The
 * authorization <i>algorithm</i> never crosses the network - every service runs it
 * locally over its own objects, against a locally replicated
 * {@code IAclAliasesDao} - so the only thing heimdall has to answer is the
 * directory. A REST mirror of {@code IGSecurityService} would have been impossible
 * anyway: 9 of its 16 methods take the caller's own entity graphs.
 * </p>
 *
 * <p>
 * Reachable only from a microservice currently registered in service discovery -
 * every method checks {@link ClusterParticipantsGuard#check}, explicitly, first
 * line - never from the edge.
 * </p>
 *
 * <p>
 * A plain {@code @RestController}: this module is only ever a dependency of the
 * service that owns the directory (heimdall), so component-scan discovery is
 * already scoped correctly by Maven, and there is no separate guard bean whose
 * presence needs to stay atomic with this one's - the guard is inline, in every
 * method body, below.
 * </p>
 *
 * Gebo.ai comment agent
 */
@RestController
@ConditionalOnProperty(prefix = "ai.gebo.security.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@RequestMapping("${ai.gebo.security.cluster.base-path:api/cluster/SecurityController}")
public class SecurityDirectoryClusterController {

	private final IGSecurityDirectory directory;
	private final GeboClusterParticipants participants;

	public SecurityDirectoryClusterController(IGSecurityDirectory directory, GeboClusterParticipants participants) {
		this.directory = directory;
		this.participants = participants;
	}

	/**
	 * @param username the username
	 * @return the user, or {@code null} (HTTP 200, empty body) if unknown - the same
	 *         contract the local directory has
	 */
	@GetMapping(value = "findUserByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfosImpl findUserByUsername(@RequestParam("username") String username, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		UserInfos user = directory.findUserByUsername(username);
		// UserInfos is an interface; the wire needs the concrete carrier.
		return user == null ? null : toImpl(user);
	}

	@GetMapping(value = "findGroupsOfUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UsersGroup> findGroupsOfUser(@RequestParam("username") String username, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return directory.findGroupsOfUser(username);
	}

	@GetMapping(value = "findAllGroups", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UsersGroup> findAllGroups(HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return directory.findAllGroups();
	}

	/**
	 * Verifies a password <b>here</b>, where the hash lives.
	 *
	 * <p>
	 * The presented password is sent to be checked; the stored hash is never returned,
	 * so no password hash ever leaves this service. It is a POST because the secret
	 * belongs in a body, not in a query string that lands in access logs.
	 * </p>
	 *
	 * @param checkRequest the username and the presented plaintext password
	 * @return whether it matches
	 */
	@PostMapping(value = "checkPassword", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean checkPassword(@RequestBody @NotNull CheckPasswordRequest checkRequest, HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return directory.checkPassword(checkRequest.getUsername(), checkRequest.getPassword());
	}

	private static UserInfosImpl toImpl(UserInfos user) {
		if (user instanceof UserInfosImpl impl) {
			return impl;
		}
		UserInfosImpl impl = new UserInfosImpl();
		impl.setUsername(user.getUsername());
		impl.setName(user.getName());
		impl.setSourname(user.getSourname());
		impl.setRoles(user.getRoles());
		impl.setDisabled(user.getDisabled());
		return impl;
	}

	/** Body of {@code checkPassword} - keeps the password out of the url. */
	public static class CheckPasswordRequest {
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
