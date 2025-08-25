package ai.gebo.security.services.impl.authmanagers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocalJwtAuthenticationManager implements AuthenticationManager {
	final HttpServletRequest request;
	final LocalJwtTokenProvider tokenProvider;
	final UserDetailsService customUserDetailsService;

	@Override 
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication instanceof BearerTokenAuthenticationToken auth) {
			String token = auth.getToken();
			if (!tokenProvider.validateToken(token)) {
				throw new BadCredentialsException("invalid local jwt value");
			}
			// Retrieves user ID from validated token
			String userId = tokenProvider.getUserIdFromToken(token);

			// Loads UserDetails using the user ID
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
			// Creates an authentication token with user details
			UsernamePasswordAuthenticationToken _authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			_authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			authentication = _authentication;
		}
		return authentication;
	}
}