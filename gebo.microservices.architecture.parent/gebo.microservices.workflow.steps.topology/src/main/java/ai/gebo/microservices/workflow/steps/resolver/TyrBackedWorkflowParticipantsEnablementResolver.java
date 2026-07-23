/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.resolver;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.workflow.steps.IWorkflowParticipantsEnablementResolver;
import ai.gebo.microservices.workflow.steps.config.GeboWorkflowStepsProperties;

/**
 * The <b>off-tyr</b> resolver, used by every service that runs the workflow logic
 * without being the authority (the chunker, which performs the tokenization
 * fan-out). It asks tyr for the enabled-step set <b>once per run</b> and caches it
 * keyed by {@code (knowledgeBase, project, dataSource, workflowType, workflowId)},
 * so the per-message {@code isEnabled()} the router runs is a local map hit.
 *
 * <p>
 * The target base url is resolved from {@link GeboMicroserviceUrlResolver} (so it
 * follows the deployment's addressing strategy) and the caller's bearer token is
 * forwarded — the async pipeline thread falls back to the platform's system
 * identity when no user token is present, so the call is always authenticated.
 * </p>
 *
 * <p>
 * A failed resolution is <b>not</b> cached and is rethrown, so the workflow
 * message is retried rather than silently routed as if the downstream indexers
 * were disabled (which would drop indexing).
 * </p>
 */
public class TyrBackedWorkflowParticipantsEnablementResolver implements IWorkflowParticipantsEnablementResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TyrBackedWorkflowParticipantsEnablementResolver.class);

	private static final ParameterizedTypeReference<List<String>> STRING_LIST = new ParameterizedTypeReference<List<String>>() {
	};

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final GeboTtlCache cache;
	private final String tyrMicroserviceId;
	private final String basePath;

	public TyrBackedWorkflowParticipantsEnablementResolver(WebClient webClient,
			GeboMicroserviceUrlResolver urlResolver, IGeboCallerTokenPropagator tokenPropagator, GeboTtlCache cache,
			GeboWorkflowStepsProperties properties) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.cache = cache;
		this.tyrMicroserviceId = properties.getTyrMicroserviceId();
		this.basePath = trimSlashes(properties.getEnabledStepsBasePath());
	}

	@Override
	public Set<String> enabledSteps(WorkflowContext context, String workflowType, String workflowId) {
		String key = cacheKey(context, workflowType, workflowId);
		return cache.get(key, () -> fetchFromTyr(workflowType, workflowId));
	}

	private Set<String> fetchFromTyr(String workflowType, String workflowId) {
		String baseUrl = urlResolver.baseUrlForMicroserviceId(tyrMicroserviceId)
				.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the workflow authority "
						+ "microservice '" + tyrMicroserviceId + "': it is not a member of the topology and has no "
						+ "'direct' entry (gebo.microservices.topology.url.direct)."));
		try {
			List<String> steps = webClient.get()
					.uri(uri(baseUrl, workflowType, workflowId)).headers(this::applyCallerToken)
					.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(STRING_LIST).block();
			Set<String> enabled = new LinkedHashSet<>();
			if (steps != null) {
				for (String s : steps) {
					if (s != null) {
						enabled.add(s.toUpperCase());
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Resolved enabled steps for {}/{} from tyr: {}", workflowType, workflowId, enabled);
			}
			return enabled;
		} catch (RuntimeException ex) {
			// Do not cache and do not fail-open: rethrow so the workflow message is
			// retried instead of routing as if the downstream indexers were disabled.
			throw new IllegalStateException("Failed to resolve enabled workflow steps from tyr ('" + tyrMicroserviceId
					+ "') for " + workflowType + "/" + workflowId, ex);
		}
	}

	private URI uri(String baseUrl, String workflowType, String workflowId) {
		return UriComponentsBuilder.fromUriString(baseUrl + "/" + basePath + "/enabledSteps")
				.queryParam("workflowType", workflowType).queryParam("workflowId", workflowId).build().encode().toUri();
	}

	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		} else {
			LOGGER.debug("No caller token to forward to the workflow authority '{}'; call goes out unauthenticated",
					tyrMicroserviceId);
		}
	}

	private String cacheKey(WorkflowContext context, String workflowType, String workflowId) {
		String kb = "-";
		String project = "-";
		String dataSource = "-";
		if (context != null) {
			kb = orDash(context.getKnowledgeBaseCode());
			project = orDash(context.getProjectCode());
			if (context.getDataSource() != null) {
				dataSource = orDash(context.getDataSource().getCode());
			}
		}
		return kb + "|" + project + "|" + dataSource + "|" + workflowType + "|" + workflowId;
	}

	private static String orDash(String v) {
		return v == null ? "-" : v;
	}

	private static String trimSlashes(String path) {
		String trimmed = path == null ? "" : path.trim();
		while (trimmed.startsWith("/")) {
			trimmed = trimmed.substring(1);
		}
		while (trimmed.endsWith("/")) {
			trimmed = trimmed.substring(0, trimmed.length() - 1);
		}
		return trimmed;
	}
}
