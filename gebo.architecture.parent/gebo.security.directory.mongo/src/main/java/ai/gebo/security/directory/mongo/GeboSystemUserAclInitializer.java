/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.directory.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.security.services.IAclGrantedAccessorService;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * Allocates the ACL aliases of the platform's <b>system identity</b> at startup.
 *
 * <h2>Why this is needed at all</h2>
 * <p>
 * Every ordinary user gets an alias per {@link AclGrantType} the moment it is
 * created - {@code GUsersAdminServiceImpl.insertUser} does it. The system identity
 * is never created: it is virtual, and creating it is explicitly <i>forbidden</i>.
 * So nothing was ever allocating its aliases, and it would hold none.
 * </p>
 *
 * <p>
 * That gap is not obvious, because most checks hide it: the system identity is an
 * {@code ADMIN}, and every path taking {@code adminCanDoAll = true} short-circuits
 * before the ACL is consulted. But
 * {@code isCanDoAction(resource, false, grants)} and
 * {@code filterAclCanDoAction(..., false, ...)} do <b>not</b> short-circuit - they
 * ask {@code AclAccessCheck} for the accessor's aliases, find none, and deny. The
 * platform would then be unable to act on its own behalf on precisely the paths that
 * enforce ACLs strictly, and it would look like a permissions bug rather than a
 * missing bootstrap.
 * </p>
 *
 * <h2>Where it runs, and why only here</h2>
 * <p>
 * In {@code gebo.security.directory.mongo} - the module that <i>means</i> "this
 * service owns the identity store", and which only heimdall and the monolith carry.
 * Allocation must happen on the owner: {@code addAcl()} draws its integer from a
 * Mongo sequence, so an initializer running on every service would either race the
 * owner or (through the REST client) ask it to allocate the same entry repeatedly.
 * Here it runs exactly once per deployment that owns the store.
 * </p>
 *
 * <p>
 * It is <b>idempotent</b>: each entry is looked up before being allocated, so a
 * restart adds nothing - the same contract {@code AclAliasesDaoImpl} follows when it
 * bootstraps the {@code EVERYONE} presets.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboSystemUserAclInitializer implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboSystemUserAclInitializer.class);

	private final IGeboSystemUserService systemUserService;
	private final IAclGrantedAccessorService accessorService;
	private final IAclAliasesDao aliasesDao;

	public GeboSystemUserAclInitializer(IGeboSystemUserService systemUserService,
			IAclGrantedAccessorService accessorService, IAclAliasesDao aliasesDao) {
		this.systemUserService = systemUserService;
		this.accessorService = accessorService;
		this.aliasesDao = aliasesDao;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// The ACL id the system identity's grants are recorded against, derived through
		// the accessor service so the "user:<name>" convention lives in one place.
		String uniqueId = accessorService.getUniqueId(systemUserService.getUserInfos());

		int allocated = 0;
		for (AclGrantType grantType : AclGrantType.values()) {
			GAclEntry entry = new GAclEntry(uniqueId, grantType);
			try {
				if (aliasesDao.findAlias(entry) == null) {
					aliasesDao.addAcl(entry);
					allocated++;
				}
			} catch (RuntimeException ex) {
				// Do not take the service down over this: it is a bootstrap, and a failure here
				// costs the system identity its strict-ACL grants, not the platform its start.
				// Say so loudly instead - a silent skip would surface later as a baffling denial.
				LOGGER.error("Cannot allocate the {} ACL alias of the system identity '{}'. Strict ACL checks "
						+ "(adminCanDoAll=false) will DENY the platform's own background work until this is "
						+ "resolved.", grantType, uniqueId, ex);
			}
		}

		if (allocated > 0) {
			LOGGER.info("Allocated {} ACL alias(es) for the system identity '{}'", allocated, uniqueId);
		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The system identity '{}' already holds every ACL alias", uniqueId);
		}
	}
}
