/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.directory.mongo.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.security.directory.mongo.GeboSystemUserAclInitializer;
import ai.gebo.security.directory.mongo.MongoSecurityDirectory;
import ai.gebo.security.services.IAclGrantedAccessorService;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * Publishes the local, Mongo-backed security directory on a service that owns the
 * user store.
 *
 * <p>
 * Ordered first so that, should a misconfigured deployment ever put both directory
 * modules on one classpath, <b>the owner wins</b>: a service that can read the store
 * itself has no business proxying the question to another service. The remote
 * directory then backs off on {@code @ConditionalOnMissingBean}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GeboMongoSecurityDirectoryAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(IGSecurityDirectory.class)
	public IGSecurityDirectory mongoSecurityDirectory(UserRepository usersRepo, UsersGroupRepository groupsRepo,
			PasswordEncoder passwordEncoder, IGeboSystemUserService systemUserService) {
		return new MongoSecurityDirectory(usersRepo, groupsRepo, passwordEncoder, systemUserService);
	}

	/**
	 * Allocates the system identity's ACL aliases at startup.
	 *
	 * <p>
	 * Published here, with the owner's directory, because allocation must happen on the
	 * service that owns the store - the alias integers come from a Mongo sequence. A
	 * consumer never carries this module, so it never runs the initializer.
	 * </p>
	 */
	@Bean
	@ConditionalOnMissingBean(GeboSystemUserAclInitializer.class)
	public GeboSystemUserAclInitializer geboSystemUserAclInitializer(IGeboSystemUserService systemUserService,
			IAclGrantedAccessorService accessorService, IAclAliasesDao aliasesDao) {
		return new GeboSystemUserAclInitializer(systemUserService, accessorService, aliasesDao);
	}
}
