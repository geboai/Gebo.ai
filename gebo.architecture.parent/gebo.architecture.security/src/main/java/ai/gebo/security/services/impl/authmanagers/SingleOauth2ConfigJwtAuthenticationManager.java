package ai.gebo.security.services.impl.authmanagers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleOauth2ConfigJwtAuthenticationManager implements AuthenticationManager {
	final SecurityHeaderData header;
	final Oauth2RuntimeConfiguration oauth2Configuration;
	final Converter<Jwt, AbstractAuthenticationToken> converter;
	final JwtDecoderCache decoderCache;
	// Nullable: when set, decoded tokens auto-provision/sync the user (policy-gated)
	// before the principal is loaded, so a trusted identity is never rejected for
	// merely not existing locally yet.
	final GOauth2ResourceServerUserProvisioner provisioner;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String issuerUri = oauth2Configuration.getProviderConfig().getIssuerUri();
		// Reuse a cached decoder per issuer instead of performing OIDC discovery +
		// JWKS download on every request.
		JwtDecoder jwtDecoder = decoderCache.forIssuerLocation(issuerUri);
		JwtAuthenticationProvider jwtProvider = new JwtAuthenticationProvider(jwtDecoder);
		jwtProvider.setJwtAuthenticationConverter(wrapWithProvisioning(converter));
		return jwtProvider.authenticate(authentication);
	}

	private Converter<Jwt, AbstractAuthenticationToken> wrapWithProvisioning(
			Converter<Jwt, AbstractAuthenticationToken> delegate) {
		if (provisioner == null)
			return delegate;
		// The provider has already validated the JWT by the time it invokes the
		// converter, so provisioning here runs only on an authentic token.
		return jwt -> {
			provisioner.provisionIfNeeded(oauth2Configuration, jwt.getTokenValue(), jwt.getClaims());
			return delegate.convert(jwt);
		};
	}

}
