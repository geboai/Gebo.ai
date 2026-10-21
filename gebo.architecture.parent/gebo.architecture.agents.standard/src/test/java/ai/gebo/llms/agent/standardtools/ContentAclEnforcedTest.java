package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.security.services.IGSecurityService;

/**
 * The one rule of the content filters: the ACL aliases apply when the policy is
 * ACL based and the user is neither an administrator nor bypassed by a product's
 * rule (see IGContentAclBypass).
 */
class ContentAclEnforcedTest {

	private static IGSecurityService security(ContentAccessPolicy policy, boolean admin, boolean bypassed) {
		IGSecurityService security = mock(IGSecurityService.class, CALLS_REAL_METHODS);
		when(security.getPlatformContentAccessPolicy()).thenReturn(policy);
		when(security.isCurrentUserAdmin()).thenReturn(admin);
		when(security.isContentAclBypassed()).thenReturn(bypassed);
		return security;
	}

	@Test
	void theAclIsEnforcedOnAPlainUserUnderTheAclPolicy() {
		assertTrue(security(ContentAccessPolicy.ACL_BASED, false, false).isContentAclEnforced());
	}

	@Test
	void administratorsAndBypassedUsersReadEverything() {
		assertFalse(security(ContentAccessPolicy.ACL_BASED, true, false).isContentAclEnforced());
		assertFalse(security(ContentAccessPolicy.ACL_BASED, false, true).isContentAclEnforced());
	}

	@Test
	void nothingIsEnforcedUnderTheGroupPolicy() {
		assertFalse(security(ContentAccessPolicy.GROUP_BASED, false, false).isContentAclEnforced());
	}

	@Test
	void noProductRuleMeansNoBypass() {
		IGSecurityService security = mock(IGSecurityService.class, CALLS_REAL_METHODS);
		assertFalse(security.isContentAclBypassed());
	}

	/** The ACL framework's own gate: a bypassed user passes the read checks as an administrator, the writes never. */
	@Test
	void theAclFrameworkLetsABypassedUserReadAsAnAdministratorButNotWrite() {
		IGSecurityService impl = mock(IGSecurityService.class, CALLS_REAL_METHODS);
		when(impl.isCurrentUserAdmin()).thenReturn(false);
		when(impl.isContentAclBypassed()).thenReturn(true);

		assertTrue(impl.readsAsAdministrator(true, AclGrantType.READ));
		assertTrue(impl.readsAsAdministrator(false, AclGrantType.READ));
		assertFalse(impl.readsAsAdministrator(true, AclGrantType.WRITE));
		assertFalse(impl.readsAsAdministrator(true, AclGrantType.READ, AclGrantType.WRITE));
		assertFalse(impl.readsAsAdministrator(true));

		when(impl.isContentAclBypassed()).thenReturn(false);
		assertFalse(impl.readsAsAdministrator(true, AclGrantType.READ));
		when(impl.isCurrentUserAdmin()).thenReturn(true);
		assertTrue(impl.readsAsAdministrator(true, AclGrantType.WRITE));
		assertFalse(impl.readsAsAdministrator(false, AclGrantType.WRITE));
	}
}
