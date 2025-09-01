package ai.gebo.security.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import ai.gebo.security.model.SecurityHeaderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A filter implementation to handle CORS (Cross-Origin Resource Sharing)
 * settings.
 */
public class GeboAICorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		// Set CORS headers
		httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
		httpServletResponse.addHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, Origin , Accept , X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,"
						+ SecurityHeaderUtil.AUTHORIZATION + "," + SecurityHeaderUtil.AUTHORIZATION_PROVIDER_ID
						+ "," + SecurityHeaderUtil.AUTHORIZATION_TENANT_ID + ","
						+ SecurityHeaderUtil.AUTHORIZATION_TYPE);
		if (httpServletRequest.getMethod().equals("OPTIONS")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}