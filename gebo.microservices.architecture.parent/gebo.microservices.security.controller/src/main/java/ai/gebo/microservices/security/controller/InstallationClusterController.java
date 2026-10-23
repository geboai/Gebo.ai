/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.microservices.cluster.ClusterParticipantsGuard;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.auth.GeboInstallationBootstrapToken;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.IGInstallationIdProvider;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The installation id, for the other microservices, which need it before they
 * can mint any platform token. Heimdall owns the installation record; this
 * module is only ever a dependency of heimdall.
 * <p>
 * It lives under {@code /public/}, which the platform's security chain already
 * lets through, so the chain needs no rule of its own; the restriction is done
 * here, in the application:
 * <ul>
 * <li>only registered cluster participants, like every cluster surface
 * ({@link ClusterParticipantsGuard}): a request forwarded by the gateway comes
 * from the gateway's address, which is not a participant, and is refused;</li>
 * <li>authenticated by its own channel: a plain JWT signed with the shared token
 * secret, valid five minutes, in the
 * {@value GeboInstallationBootstrapToken#HEADER} header (see
 * {@link GeboInstallationBootstrapToken}).</li>
 * </ul>
 */
@RestController
@ConditionalOnProperty(prefix = "ai.gebo.security.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@RequestMapping(InstallationClusterController.BASE_PATH)
public class InstallationClusterController {
	private static final Logger LOGGER = LoggerFactory.getLogger(InstallationClusterController.class);
	/** Under /public/: the platform chain permits it, this controller restricts it. */
	static final String BASE_PATH = "public/InstallationController";

	private final IGInstallationIdProvider installationIdProvider;
	private final GeboClusterParticipants participants;
	private final GeboSecurityConfig securityConfig;

	public InstallationClusterController(IGInstallationIdProvider installationIdProvider,
			GeboClusterParticipants participants, GeboSecurityConfig securityConfig) {
		this.installationIdProvider = installationIdProvider;
		this.participants = participants;
		this.securityConfig = securityConfig;
	}

	@GetMapping(value = "getInstallationId", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> getInstallationId(
			@RequestHeader(value = GeboInstallationBootstrapToken.HEADER, required = false) String bootstrapToken,
			HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		String caller;
		try {
			caller = GeboInstallationBootstrapToken.verify(securityConfig.getAuth().getTokenSecret(), bootstrapToken);
		} catch (IllegalArgumentException refused) {
			LOGGER.warn("Installation id refused to {}: {}", request.getRemoteAddr(), refused.getMessage());
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid " + GeboInstallationBootstrapToken.HEADER);
		}
		LOGGER.info("Installation id handed to {} ({})", caller, request.getRemoteAddr());
		return Map.of("installationId", installationIdProvider.getInstallationId());
	}
}
