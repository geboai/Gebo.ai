/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Denies any request to the secrets cluster endpoints that does not come from a
 * microservice currently registered in the cluster (see
 * {@link GeboClusterParticipants}).
 *
 * <p>
 * The address checked is {@link HttpServletRequest#getRemoteAddr()}, i.e. the
 * peer of the TCP connection. Forwarded headers ({@code X-Forwarded-For} and
 * friends) are deliberately <b>ignored</b>: they are set by the caller, so
 * trusting them would let anyone claim a participant address and defeat the whole
 * check. When calls are routed through the gateway the peer is the gateway, which
 * is itself a registered participant (it is included via
 * {@code extra-service-ids}).
 * </p>
 *
 * <p>
 * This is a network-membership check, not an authentication one. It restricts
 * <i>which hosts</i> may reach the endpoints; <i>who</i> is asking still comes
 * from the propagated caller token that the client forwards and the hosting
 * service's normal security configuration validates. The two are complementary
 * and both apply.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class ClusterParticipantsOnlyInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterParticipantsOnlyInterceptor.class);

	private final GeboClusterParticipants participants;

	public ClusterParticipantsOnlyInterceptor(GeboClusterParticipants participants) {
		this.participants = participants;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String remoteAddress = request.getRemoteAddr();
		if (participants.isParticipantAddress(remoteAddress)) {
			return true;
		}
		// Log the refused address, not the requested secret: the uri carries the secret
		// id and this line may land in an aggregated log.
		LOGGER.warn("Denied {} {} from '{}': not a registered cluster participant", request.getMethod(),
				request.getRequestURI(), remoteAddress);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		return false;
	}
}
