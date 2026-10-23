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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.security.services.IGAuthenticatedTokenObserver;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocalJwtAuthenticationManager implements AuthenticationManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(LocalJwtAuthenticationManager.class);
	final HttpServletRequest request;
	final LocalJwtTokenProvider tokenProvider;
	final UserDetailsService customUserDetailsService;
	final List<IGAuthenticatedTokenObserver> tokenObservers;

	public LocalJwtAuthenticationManager(HttpServletRequest request, LocalJwtTokenProvider tokenProvider,
			UserDetailsService customUserDetailsService) {
		this(request, tokenProvider, customUserDetailsService, List.of());
	}

	@Override 
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication instanceof BearerTokenAuthenticationToken auth) {
			String token = auth.getToken();
			if (!tokenProvider.validateToken(token)) {
				throw new BadCredentialsException("invalid local jwt value");
			}
			// Retrieves user ID from validated token
			String userId = tokenProvider.getUserIdFromToken(token);
			notifyObservers(token);

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

	/** The sessions store learns of the token and where the request came from. */
	private void notifyObservers(String token) {
		if (tokenObservers == null || tokenObservers.isEmpty()) {
			return;
		}
		Claims claims = tokenProvider.parseClaims(token);
		if (claims == null || claims.getId() == null) {
			return;
		}
		Map<String, Object> readOnly = Collections.unmodifiableMap(claims);
		for (IGAuthenticatedTokenObserver observer : tokenObservers) {
			try {
				observer.onAuthenticated(claims.getId(), readOnly, clientIp(request),
						request.getHeader("User-Agent"));
			} catch (RuntimeException e) {
				LOGGER.warn("Token observer failed (ignored): {}", e.getMessage());
			}
		}
	}

	/** The caller's address, behind a reverse proxy the first forwarded one. */
	public static String clientIp(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String forwarded = request.getHeader("X-Forwarded-For");
		if (forwarded != null && !forwarded.isBlank()) {
			return forwarded.split(",")[0].trim();
		}
		return request.getRemoteAddr();
	}
}