/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.directory.mongo.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.security.directory.mongo.GUsersAdminServiceImpl;
import ai.gebo.security.directory.mongo.GeboSystemUserAclInitializer;
import ai.gebo.security.directory.mongo.MongoSecurityDirectory;
import ai.gebo.security.services.IAclGrantedAccessorService;
import ai.gebo.security.repository.UserRepository;
import ai.gebo.security.repository.UsersGroupRepository;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.security.services.impl.AclGrantedAccessorServiceImpl;

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

	// Declared to return the CONCRETE type, not IGSecurityDirectory: consumers that
	// @Autowire/@ConditionalOnMissingBean the interface still resolve it fine (Spring
	// matches subtypes to a requested supertype), but GeboSecurityClusterControllerAutoConfiguration's
	// @ConditionalOnBean(MongoSecurityDirectory.class) - "publish only if this service
	// OWNS the directory, not merely sees the interface" - can only ever match a bean
	// whose declared/factory-method type is that concrete class; a bean typed as the
	// interface is invisible to a @ConditionalOnBean asking for one of its subtypes,
	// regardless of @AutoConfigureAfter ordering between the two.
	@Bean
	@ConditionalOnMissingBean(IGSecurityDirectory.class)
	public MongoSecurityDirectory mongoSecurityDirectory(UserRepository usersRepo, UsersGroupRepository groupsRepo,
			PasswordEncoder passwordEncoder, IGeboSystemUserService systemUserService,
			// ObjectProvider, not IGUsersAdminService directly: that bean depends (through
			// AclGrantedAccessorServiceImpl) on IGSecurityDirectory itself, so a direct
			// dependency here is circular. See MongoSecurityDirectory's own field comment.
			ObjectProvider<IGUsersAdminService> userAdminService) {
		return new MongoSecurityDirectory(usersRepo, groupsRepo, passwordEncoder, systemUserService,
				userAdminService);
	}

	/**
	 * The local, Mongo-backed {@link IGUsersAdminService} of a service that owns the
	 * user store - see {@link GUsersAdminServiceImpl}'s own class comment for why it
	 * is published here (an explicit {@code @Bean}, not component-scanned) rather than
	 * living in {@code gebo.architecture.security} as a plain {@code @Service}: that
	 * module is a transitive dependency of virtually everything, so a bean shipped
	 * inside it would always win {@code restUsersAdminService}'s
	 * {@code @ConditionalOnMissingBean} race and the remote implementation could never
	 * activate on any other microservice.
	 */
	@Bean
	@ConditionalOnMissingBean(IGUsersAdminService.class)
	public GUsersAdminServiceImpl usersAdminService(UserRepository usersRepo, UsersGroupRepository groupsRepo,
			PasswordEncoder passwordEncoder, AclGrantedAccessorServiceImpl grantedAccessorService,
			IAclAliasesDao aclAliasesDao, IGeboCryptingService cryptService, IGeboSystemUserService systemUserService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		return new GUsersAdminServiceImpl(usersRepo, groupsRepo, passwordEncoder, grantedAccessorService,
				aclAliasesDao, cryptService, systemUserService, securityAuditLoggerService);
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
