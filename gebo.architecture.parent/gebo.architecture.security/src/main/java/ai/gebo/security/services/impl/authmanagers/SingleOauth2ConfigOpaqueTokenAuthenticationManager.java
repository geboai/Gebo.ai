package ai.gebo.security.services.impl.authmanagers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		// Provision strictly on the "validated token, unknown user" signal: the
		// UsernameNotFoundException can only surface after the provider has introspected
		// and validated the token (an inactive token throws earlier, in introspection)
		// and the converter then found no local user. So an unauthenticated token can
		// never reach provisioning, and this does not depend on Spring's
		// converter-invocation ordering.
		return (introspectedToken, principal) -> {
			try {
				return delegate.convert(introspectedToken, principal);
			} catch (UsernameNotFoundException notFound) {
				if (provisioner.provisionOnValidatedUnknownUser(oauth2Configuration, introspectedToken,
						principal.getAttributes())) {
					return delegate.convert(introspectedToken, principal);
				}
				throw notFound;
			}
		};
	}

}
