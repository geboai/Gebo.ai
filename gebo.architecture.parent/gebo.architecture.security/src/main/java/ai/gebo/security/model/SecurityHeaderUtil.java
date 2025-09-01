package ai.gebo.security.model;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityHeaderUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHeaderUtil.class);
	public static final String AUTHORIZATION_TYPE = "X-AuthType";
	public static final String AUTHORIZATION_PROVIDER_ID = "X-Authprovider-id";
	public static final String AUTHORIZATION_TENANT_ID = "X-tenant-id";
	public static final String AUTHORIZATION = "Authorization";

	public static enum XAuthType {
		OAUTH2, LOCAL_JWT
	};

	public static final String DEFAULT_TENANT = "default-tenant";
	public static final String DEFAULT_PROVIDER_ID = "default-internal-jwt-provider";

	public static SecurityHeaderData getSecurityHeaderData(HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Logging received header");
			Enumeration<String> names = request.getHeaderNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				LOGGER.debug("Header[" + name + "]=" + request.getHeader(name));
			}
			LOGGER.debug("Logged received header");
		}
		String token = getTokenFromRequest(request);
		String authType = getHeaderOrDefault(request, AUTHORIZATION_TYPE, XAuthType.LOCAL_JWT.name());
		String authProviderId = getHeaderOrDefault(request, AUTHORIZATION_PROVIDER_ID, DEFAULT_PROVIDER_ID);
		String authTenantId = getHeaderOrDefault(request, AUTHORIZATION_TENANT_ID, DEFAULT_TENANT);
		return new SecurityHeaderData(token, XAuthType.valueOf(authType), authProviderId, authTenantId,
				token == null || token.trim().length() == 0);
	}

	private static String getHeaderOrDefault(HttpServletRequest request, String headerEntry, String defaultValue) {
		String value = request.getHeader(headerEntry);
		if (value == null || value.trim().length() == 0)
			value = defaultValue;
		return value;
	}

	/**
	 * Extracts the JWT token from the request's Authorization header.
	 *
	 * @param request the request made to the server
	 * @return the JWT token as a string or null if not found
	 */
	private static String getTokenFromRequest(HttpServletRequest request) {
		// Retrieve Authorization header
		String bearerToken = request.getHeader(SecurityHeaderUtil.AUTHORIZATION);
		// Validate and parse the JWT token from header
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public static SecurityHeaderData createSelfsignedJwtSecurityHeader(String token) {
		return new SecurityHeaderData(token, XAuthType.LOCAL_JWT, DEFAULT_PROVIDER_ID, DEFAULT_TENANT, false);
	}

	public static SecurityHeaderData createSelfsignedJwtSecurityHeaderOauth2(String token) {
		return new SecurityHeaderData(token, XAuthType.LOCAL_JWT, DEFAULT_PROVIDER_ID, DEFAULT_TENANT, false);
	}

}
