/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.directory.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGeboSystemUserService;
import lombok.AllArgsConstructor;

/**
 * The {@link IGSecurityDirectory} of a service that <b>owns</b> the user store:
 * it reads Mongo directly. This is heimdall and the monolith.
 *
 * <p>
 * <b>It lives in its own module, and that is the whole mechanism.</b> A service
 * that does not own the user store depends on
 * {@code gebo.microservices.security.client} and gets {@code RestSecurityDirectory}
 * instead; a service that owns it depends on this module. The two are never on the
 * same classpath, so which directory a service uses is decided by what it
 * <i>packages</i> - visible in its pom, impossible to get wrong at runtime.
 * </p>
 *
 * <p>
 * This class deliberately does <b>not</b> live in {@code gebo.architecture.security}:
 * that module is a transitive dependency of virtually everything, so a directory
 * shipped inside it would be present on every service, would always win the
 * {@code @ConditionalOnMissingBean} race, and no service could ever have used the
 * remote one - the same trap {@code gebo.secrets.impl} fell into by riding in
 * transitively.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AllArgsConstructor
public class MongoSecurityDirectory implements IGSecurityDirectory {

	private final UserRepository usersRepo;
	private final UsersGroupRepository groupsRepo;
	private final PasswordEncoder passwordEncoder;
	private final IGeboSystemUserService systemUserService;

	@Override
	public UserInfos findUserByUsername(String username) {
		// The platform's own identity is virtual - configured, never stored - so it must
		// be answered before Mongo is consulted, or every lookup of it would come back
		// empty. See IGeboSystemUserService.
		if (systemUserService.isSystemUser(username)) {
			return systemUserService.getUserInfos();
		}
		Optional<User> user = usersRepo.findById(username);
		return user.map(UserInfosImpl::new).orElse(null);
	}

	@Override
	public List<UsersGroup> findGroupsOfUser(String username) {
		return groupsRepo.findByUserIdsIn(username);
	}

	@Override
	public List<UsersGroup> findAllGroups() {
		return groupsRepo.findAll();
	}

	@Override
	public boolean checkPassword(String username, String rawPassword) {
		// The system identity holds an unusable password by construction: no presented
		// value can match it, and it must not be able to re-authenticate either.
		if (systemUserService.isSystemUser(username)) {
			return false;
		}
		Optional<User> user = usersRepo.findById(username);
		if (user.isEmpty() || user.get().getPassword() == null) {
			return false;
		}
		return passwordEncoder.matches(rawPassword, user.get().getPassword());
	}
}
