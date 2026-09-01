package ai.gebo.security.services.impl.authmanagers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleOauth2ConfigOpaqueTokenAuthenticationManager implements AuthenticationManager {
	final SecurityHeaderData header;
	final Oauth2RuntimeConfiguration oauth2Configuration;
	final OpaqueTokenAuthenticationConverter converter;
	// Nullable: when set, introspected tokens auto-provision/sync the user
	// (policy-gated) before the principal is loaded.
	final GOauth2ResourceServerUserProvisioner provisioner;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SpringOpaqueTokenIntrospector opaqueTokenIntrospector = new SpringOpaqueTokenIntrospector(
				oauth2Configuration.getProviderConfig().getIntrospectionUri(),
				oauth2Configuration.getClient().getClientId(), oauth2Configuration.getClient().getSecret());

		OpaqueTokenAuthenticationProvider opaqueProvider = new OpaqueTokenAuthenticationProvider(
				opaqueTokenIntrospector);

		opaqueProvider.setAuthenticationConverter(wrapWithProvisioning(converter));
		return opaqueProvider.authenticate(authentication);

	}

	private OpaqueTokenAuthenticationConverter wrapWithProvisioning(OpaqueTokenAuthenticationConverter delegate) {
		if (provisioner == null)
			return delegate;
		// The provider has already introspected/validated the token before invoking the
		// converter, so provisioning here runs only on an authentic token.
		return (introspectedToken, principal) -> {
			provisioner.provisionIfNeeded(oauth2Configuration, introspectedToken, principal.getAttributes());
			return delegate.convert(introspectedToken, principal);
		};
	}

}
