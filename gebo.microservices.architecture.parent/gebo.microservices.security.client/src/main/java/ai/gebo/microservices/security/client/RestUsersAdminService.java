/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditTaxonomy;

/**
 * The {@link IGUsersAdminService} of a service that does <b>not</b> own the user
 * store: it asks heimdall's {@code UsersAdminClusterController}.
 *
 * <p>
 * Mirrors {@link RestSecurityDirectory}'s mechanism and reasoning exactly - same
 * module (so every non-heimdall microservice that already gets the remote
 * {@code IGSecurityDirectory} gets this too, with no new dependency to add), same
 * {@code @ConditionalOnMissingBean(IGUsersAdminService.class)} back-off so a service
 * that packages {@code gebo.security.directory.mongo} instead keeps its local,
 * Mongo-backed implementation. Unlike {@code RestSecurityDirectory}, this is admin CRUD
 * (cold path - an admin UI, or the OAuth2 auto-provisioning/sync entrypoint), so there
 * is no per-request/TTL caching here.
 * </p>
 *
 * <p>
 * <b>Auditing</b>: {@code createUserIfNotExists} - the OAuth2 resource-server
 * auto-provisioning/sync entrypoint reached from every microservice - is audited here
 * as the caller's own "I asked heimdall to provision/sync this identity" event, giving
 * this service's own security-log a trail entry for the network hop, in addition to
 * heimdall's own (cluster controller) and the actual write's (local
 * {@code GUsersAdminServiceImpl}).
 * </p>
 *
 * Gebo.ai comment agent
 */
public class RestUsersAdminService implements IGUsersAdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestUsersAdminService.class);

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String microserviceId;
	private final String basePath;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	public RestUsersAdminService(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String microserviceId, String basePath,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.microserviceId = microserviceId;
		this.basePath = trimSlashes(basePath) + "/UsersAdmin";
		this.securityAuditLoggerService = securityAuditLoggerService;
	}

	@Override
	public EditableUser insertUser(EditableUser user, String password) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("user", user);
		body.put("password", password);
		return call("insertUser", () -> webClient.post().uri(uri("insertUser")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
				.retrieve().bodyToMono(EditableUser.class).block());
	}

	@Override
	public EditableUser updateUser(EditableUser user) {
		return call("updateUser", () -> webClient.post().uri(uri("updateUser")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(user)
				.retrieve().bodyToMono(EditableUser.class).block());
	}

	@Override
	public EditableUser findUserByUsername(String email) {
		return call("findUserByUsername", () -> webClient.get().uri(uri("findUserByUsername", "email", email))
				.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(EditableUser.class).block());
	}

	@Override
	public void deleteUser(EditableUser user) {
		call("deleteUser", () -> webClient.post().uri(uri("deleteUser")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).bodyValue(user).retrieve().toBodilessEntity().block());
	}

	@Override
	public Page<UserInfos> findUserByQbe(User qbe, Pageable pageable) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("qbe", qbe);
		body.put("page", pageable.getPageNumber());
		body.put("size", pageable.getPageSize());
		PageResult<UserInfos> result = call("findUserByQbe",
				() -> webClient.post().uri(uri("findUserByQbe")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
						.retrieve().bodyToMono(new org.springframework.core.ParameterizedTypeReference<PageResult<UserInfos>>() {
						}).block());
		return toPage(result, pageable);
	}

	@Override
	public Page<UserInfos> findUserByQbe(EditableUser qbe, Pageable pageable) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("qbe", qbe);
		body.put("page", pageable.getPageNumber());
		body.put("size", pageable.getPageSize());
		PageResult<UserInfos> result = call("findEditableUserByQbe",
				() -> webClient.post().uri(uri("findEditableUserByQbe")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
						.retrieve().bodyToMono(new org.springframework.core.ParameterizedTypeReference<PageResult<UserInfos>>() {
						}).block());
		return toPage(result, pageable);
	}

	@Override
	public UsersGroup insertGroup(UsersGroup group) {
		return call("insertGroup", () -> webClient.post().uri(uri("insertGroup")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(group)
				.retrieve().bodyToMono(UsersGroup.class).block());
	}

	@Override
	public UsersGroup findGroupByCode(String code) {
		return call("findGroupByCode", () -> webClient.get().uri(uri("findGroupByCode", "code", code))
				.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(UsersGroup.class).block());
	}

	@Override
	public UsersGroup updateGroup(UsersGroup group) {
		return call("updateGroup", () -> webClient.post().uri(uri("updateGroup")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(group)
				.retrieve().bodyToMono(UsersGroup.class).block());
	}

	@Override
	public void deleteGroup(UsersGroup group) {
		call("deleteGroup", () -> webClient.post().uri(uri("deleteGroup")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).bodyValue(group).retrieve().toBodilessEntity().block());
	}

	@Override
	public Page<UsersGroup> findUsersGroupByQbe(UsersGroup qbe, Pageable pageable) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("qbe", qbe);
		body.put("page", pageable.getPageNumber());
		body.put("size", pageable.getPageSize());
		PageResult<UsersGroup> result = call("findUsersGroupByQbe",
				() -> webClient.post().uri(uri("findUsersGroupByQbe")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
						.retrieve().bodyToMono(new org.springframework.core.ParameterizedTypeReference<PageResult<UsersGroup>>() {
						}).block());
		return toPage(result, pageable);
	}

	@Override
	public List<UsersGroup> getAllGroups() {
		UsersGroup[] groups = call("getAllGroups", () -> webClient.get().uri(uri("getAllGroups"))
				.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(UsersGroup[].class).block());
		return groups == null ? List.of() : List.of(groups);
	}

	@Override
	public List<UserInfos> getAllUsers() {
		ai.gebo.security.model.UserInfosImpl[] users = call("getAllUsers",
				() -> webClient.get().uri(uri("getAllUsers")).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve()
						.bodyToMono(ai.gebo.security.model.UserInfosImpl[].class).block());
		return users == null ? List.of() : List.of(users);
	}

	@Override
	public void createUserIfNotExists(String email, Map<String, Object> attributes, AuthProvider authProvider) {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION);
		event.setCategory(SecurityAuditTaxonomy.Category.USER_ADMINISTRATION);
		event.setAction(SecurityAuditTaxonomy.Action.USER_AUTO_PROVISION);
		event.setResourceId(email);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("username", email);
		body.put("attributes", attributes);
		body.put("authProvider", authProvider);
		try {
			call("createUserIfNotExists",
					() -> webClient.post().uri(uri("createUserIfNotExists")).headers(this::applyCallerToken)
							.contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve().toBodilessEntity()
							.block());
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}

	@Override
	public void changePassword(String username, String password) throws GeboCryptSecretException {
		Map<String, String> body = new LinkedHashMap<>();
		body.put("username", username);
		body.put("password", password);
		call("changePassword", () -> webClient.post().uri(uri("changePassword")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve().toBodilessEntity().block());
	}

	// --- Internals ----------------------------------------------------------

	private <T> Page<T> toPage(PageResult<T> result, Pageable pageable) {
		if (result == null) {
			return Page.empty(pageable);
		}
		List<T> content = result.getContent() == null ? List.of() : result.getContent();
		Pageable effective = PageRequest.of(result.getNumber(), Math.max(result.getSize(), 1));
		return new PageImpl<>(content, effective, result.getTotalElements());
	}

	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		}
	}

	private URI uri(String endpoint) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint).build().encode()
				.toUri();
	}

	private URI uri(String endpoint, String paramName, String paramValue) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint)
				.queryParam(paramName, paramValue).build().encode().toUri();
	}

	private String baseUrl() {
		Optional<String> baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId);
		return baseUrl.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the security "
				+ "microservice '" + microserviceId + "': it is not a member of the topology and has no 'direct' "
				+ "entry (gebo.microservices.topology.url.direct)."));
	}

	/**
	 * The interface methods do not declare checked exceptions (beyond
	 * {@code changePassword}'s {@code GeboCryptSecretException}, never actually thrown
	 * from here since encoding happens on heimdall's side), so a transport failure
	 * surfaces as an unchecked one rather than being swallowed into a misleading empty
	 * result.
	 */
	private <T> T call(String operation, Supplier<T> remoteCall) {
		try {
			return remoteCall.get();
		} catch (WebClientResponseException ex) {
			LOGGER.error("Remote {} failed: {} {}", operation, ex.getStatusCode(), ex.getResponseBodyAsString());
			throw new IllegalStateException("Users admin call '" + operation + "' failed: " + ex.getStatusCode(),
					ex);
		} catch (RuntimeException ex) {
			LOGGER.error("Remote {} failed", operation, ex);
			throw new IllegalStateException("Users admin call '" + operation + "' failed", ex);
		}
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

	/** Mirrors {@code UsersAdminClusterController.PageResult<T>}'s wire shape. */
	public static class PageResult<T> {
		private List<T> content;
		private long totalElements;
		private int number;
		private int size;

		public List<T> getContent() {
			return content;
		}

		public void setContent(List<T> content) {
			this.content = content;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
	}
}
