/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;

/**
 * Access to an object its creator owns: the creator passes, and so does whoever
 * the platform's usual checks let through - users, groups, everyone, or the ACLs
 * under that policy; nobody else, administrators on their role alone included.
 */
class IGSecurityServiceCreatorOrGrantedTest {
	/** An object with the security fields a conversation has. */
	static class ASecuredObject extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource {
		private List<String> accessibleUsers;
		private List<String> accessibleGroups;
		private Boolean accessibleToAll;
		private List<Integer> aclAliases;

		@Override
		public String owner() {
			return getUserCreated();
		}

		@Override
		public List<String> getAccessibleUsers() {
			return accessibleUsers;
		}

		@Override
		public List<String> getAccessibleGroups() {
			return accessibleGroups;
		}

		@Override
		public Boolean getAccessibleToAll() {
			return accessibleToAll;
		}

		@Override
		public List<Integer> getAclAliases() {
			return aclAliases;
		}

		@Override
		public void setAclAliases(List<Integer> aclAliases) {
			this.aclAliases = aclAliases;
		}
	}

	private IGSecurityService service;
	private ASecuredObject object;

	@BeforeEach
	void setUp() {
		service = mock(IGSecurityService.class, CALLS_REAL_METHODS);
		object = new ASecuredObject();
		object.setCode("a-conversation");
		object.setUserCreated("the-owner");
		doCallRealMethod().when(service).checkBeingCreatorOrGranted(any(), any(AclGrantType[].class));
	}

	@Test
	void theCreatorPassesWithoutTheUsualChecks() {
		doNothing().when(service).checkBeingCreator(object);

		assertDoesNotThrow(() -> service.checkBeingCreatorOrGranted(object, AclGrantType.WRITE));

		verify(service, never()).isCanDo(any(), anyBoolean(), any(AclGrantType[].class));
	}

	@Test
	void whoeverTheUsualChecksLetThroughPasses() {
		doThrow(new SecurityException("The actual user is not the owner of this object")).when(service)
				.checkBeingCreator(object);
		doReturn(true).when(service).isCanDo(eq(object), eq(false), any(AclGrantType[].class));

		assertDoesNotThrow(() -> service.checkBeingCreatorOrGranted(object, AclGrantType.WRITE));
	}

	@Test
	void nobodyElsePasses() {
		doThrow(new SecurityException("The actual user is not the owner of this object")).when(service)
				.checkBeingCreator(object);
		doReturn(false).when(service).isCanDo(eq(object), eq(false), any(AclGrantType[].class));

		assertThrows(SecurityException.class, () -> service.checkBeingCreatorOrGranted(object, AclGrantType.READ));
	}

	@Test
	void administratorsAreNotLetThroughOnTheirRoleAlone() {
		doThrow(new SecurityException("The actual user is not the owner of this object")).when(service)
				.checkBeingCreator(object);
		doReturn(false).when(service).isCanDo(eq(object), eq(false), any(AclGrantType[].class));
		doReturn(true).when(service).isCanDo(eq(object), eq(true), any(AclGrantType[].class));

		assertThrows(SecurityException.class, () -> service.checkBeingCreatorOrGranted(object, AclGrantType.READ));
	}
}
