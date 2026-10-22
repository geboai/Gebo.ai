package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.acl.IContentAclResource;
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

	/** A content of the knowledge bases, as the framework gate sees it. */
	private static IContentAclResource content() {
		return new IContentAclResource() {
			private List<Integer> aliases;

			@Override
			public List<Integer> getAclAliases() {
				return aliases;
			}

			@Override
			public void setAclAliases(List<Integer> acl) {
				this.aliases = acl;
			}
		};
	}

	/** A configuration: ACL-granted, not a content. */
	private static IAclGrantedResource configuration() {
		return new IAclGrantedResource() {
			private List<Integer> aliases;

			@Override
			public List<Integer> getAclAliases() {
				return aliases;
			}

			@Override
			public void setAclAliases(List<Integer> acl) {
				this.aliases = acl;
			}
		};
	}

	/**
	 * The ACL framework's own gate: a bypassed user reads the contents of the
	 * knowledge bases as an administrator, never writes them, never reaches a
	 * configuration.
	 */
	@Test
	void theAclFrameworkLetsABypassedUserReadContentsAsAnAdministratorOnly() {
		IGSecurityService impl = mock(IGSecurityService.class, CALLS_REAL_METHODS);
		when(impl.isCurrentUserAdmin()).thenReturn(false);
		when(impl.isContentAclBypassed()).thenReturn(true);

		assertTrue(impl.readsAsAdministrator(true, content(), AclGrantType.READ));
		assertTrue(impl.readsAsAdministrator(false, content(), AclGrantType.READ));
		assertFalse(impl.readsAsAdministrator(true, content(), AclGrantType.WRITE));
		assertFalse(impl.readsAsAdministrator(true, content(), AclGrantType.READ, AclGrantType.WRITE));
		assertFalse(impl.readsAsAdministrator(true, content()));
		assertFalse(impl.readsAsAdministrator(true, configuration(), AclGrantType.READ), "a configuration is never opened");
		assertFalse(impl.readsAsAdministrator(true, null, AclGrantType.READ));

		when(impl.isContentAclBypassed()).thenReturn(false);
		assertFalse(impl.readsAsAdministrator(true, content(), AclGrantType.READ));
		when(impl.isCurrentUserAdmin()).thenReturn(true);
		assertTrue(impl.readsAsAdministrator(true, configuration(), AclGrantType.WRITE));
		assertFalse(impl.readsAsAdministrator(false, configuration(), AclGrantType.WRITE));
	}

	@Test
	void readOnlyMeansEveryGrantAskedIsARead() {
		assertTrue(IGSecurityService.readOnly(AclGrantType.READ));
		assertFalse(IGSecurityService.readOnly(AclGrantType.READ, AclGrantType.EXECUTE));
		assertFalse(IGSecurityService.readOnly());
		assertFalse(IGSecurityService.readOnly((AclGrantType[]) null));
	}
}
