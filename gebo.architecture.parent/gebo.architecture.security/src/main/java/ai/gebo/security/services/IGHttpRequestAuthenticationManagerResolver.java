package ai.gebo.security.services;

import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface IGHttpRequestAuthenticationManagerResolver extends AuthenticationManagerResolver< HttpServletRequest> {

	public Authentication authenticateByLocalJWT(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

}
