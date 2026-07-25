/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Denies a cluster-only endpoint call that does not come from a microservice
 * currently registered in the cluster (see {@link GeboClusterParticipants}).
 *
 * <p>
 * Replaces {@code ClusterParticipantsOnlyInterceptor}: an interceptor bean and the
 * hand-registered ({@code @Bean}, not {@code @Component}) controller it guarded had
 * to be published atomically, by the same conditional auto-configuration, or the
 * endpoint could exist unguarded - which in turn meant the controller could not be
 * a plain {@code @RestController}, because that would make it visible to the blanket
 * {@code ai.gebo} component scan every Gebo app performs, independently of whether
 * the auto-configuration's conditions held. That plumbing turned out to have a real
 * cost: {@code RequestMappingHandlerMapping} builds its handler map once, and a
 * hand-registered {@code @Bean} reached only through a deep
 * {@code @ConditionalOnBean} chain was consistently still missing when it did,
 * so every call 404'd regardless of authentication or the guard - confirmed live via
 * {@code /actuator/beans} (bean existed) + {@code /actuator/mappings} (zero routes).
 * </p>
 *
 * <p>
 * Calling this at the top of each handler method - explicit in the code a reviewer
 * is already reading, not implicit in separately-wired interceptor/bean conditions
 * elsewhere - gets the same guarantee (no endpoint without its guard) a different
 * way: there is no separate wiring to accidentally decouple, because the check
 * <i>is</i> the endpoint's own logic. It also lets the controller be an ordinary
 * {@code @RestController} (a normal, {@code @ComponentScan}-discovered bean, not a
 * hand-registered one), sidestepping the {@code RequestMappingHandlerMapping} timing
 * problem entirely rather than working around it.
 * </p>
 *
 * <p>
 * The address checked is {@link HttpServletRequest#getRemoteAddr()}, i.e. the peer
 * of the TCP connection. Forwarded headers ({@code X-Forwarded-For} and friends) are
 * deliberately <b>ignored</b>: they are set by the caller, so trusting them would let
 * anyone claim a participant address and defeat the whole check. When calls are
 * routed through the gateway the peer is the gateway, which is itself a registered
 * participant (it is included via {@code extra-service-ids}).
 * </p>
 *
 * <p>
 * This is a network-membership check, not an authentication one. It restricts
 * <i>which hosts</i> may reach the endpoint; <i>who</i> is asking still comes from
 * the propagated caller token that the client forwards and the hosting service's
 * normal security configuration validates. The two are complementary and both apply.
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class ClusterParticipantsGuard {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterParticipantsGuard.class);

	private ClusterParticipantsGuard() {
	}

	/**
	 * @param participants the live cluster membership
	 * @param request the incoming request
	 * @throws ResponseStatusException {@code 403 FORBIDDEN} if the caller's address is
	 *             not a registered cluster participant
	 */
	public static void check(GeboClusterParticipants participants, HttpServletRequest request) {
		String remoteAddress = request.getRemoteAddr();
		if (participants.isParticipantAddress(remoteAddress)) {
			return;
		}
		// Log the refused address, not the requested resource: the uri may carry an id
		// and this line can land in an aggregated log.
		LOGGER.warn("Denied {} {} from '{}': not a registered cluster participant", request.getMethod(),
				request.getRequestURI(), remoteAddress);
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not a registered cluster participant");
	}
}
