/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;

/**
 * Reactive counterpart of {@link SecurityHeaderUtil}: reads the same Gebo auth
 * headers ({@code X-AuthType}, {@code X-Authprovider-id}, {@code X-tenant-id},
 * {@code Authorization}) from a {@link ServerWebExchange} and builds the same
 * {@link SecurityHeaderData}, so the reactive resource-server resolver selects
 * managers by exactly the same rules as the servlet one.
 */
public final class ReactiveSecurityHeaderUtil {

	private ReactiveSecurityHeaderUtil() {
	}

	public static SecurityHeaderData getSecurityHeaderData(ServerWebExchange exchange) {
		HttpHeaders headers = exchange.getRequest().getHeaders();
		String token = getTokenFromHeaders(headers);
		String authType = getHeaderOrDefault(headers, SecurityHeaderUtil.AUTHORIZATION_TYPE, XAuthType.LOCAL_JWT.name());
		String authProviderId = getHeaderOrDefault(headers, SecurityHeaderUtil.AUTHORIZATION_PROVIDER_ID,
				SecurityHeaderUtil.DEFAULT_PROVIDER_ID);
		String authTenantId = getHeaderOrDefault(headers, SecurityHeaderUtil.AUTHORIZATION_TENANT_ID,
				SecurityHeaderUtil.DEFAULT_TENANT);
		return new SecurityHeaderData(token, XAuthType.valueOf(authType), authProviderId, authTenantId,
				token == null || token.trim().length() == 0);
	}

	private static String getHeaderOrDefault(HttpHeaders headers, String headerEntry, String defaultValue) {
		String value = headers.getFirst(headerEntry);
		if (value == null || value.trim().length() == 0)
			value = defaultValue;
		return value;
	}

	private static String getTokenFromHeaders(HttpHeaders headers) {
		String bearerToken = headers.getFirst(SecurityHeaderUtil.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
