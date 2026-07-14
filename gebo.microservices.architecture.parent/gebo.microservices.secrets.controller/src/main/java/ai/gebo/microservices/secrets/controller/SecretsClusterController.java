/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretContentEnvelope;
import ai.gebo.secrets.model.GeboSecretContentTypes;
import ai.gebo.secrets.model.GeboSecretStoreRequest;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * The full {@link IGeboSecretsAccessService} exposed over REST for the other
 * microservices of the cluster, so that
 * {@code GeboSecretsAccessServiceRestClient} can implement the interface against
 * a remote secrets service (heimdall.gebo.ai).
 *
 * <p>
 * This is the counterpart of - not a replacement for -
 * {@code api/admin/SecretsController}, which stays the ADMIN/UI surface and
 * deliberately exposes only secret <i>metadata</i> plus the create/delete
 * operations. The endpoints here additionally return decrypted secret
 * <b>content</b>, which the admin controller never does, and are therefore not
 * reachable by users at all: every request must originate from a microservice
 * currently registered in the cluster registry
 * ({@link ai.gebo.microservices.secrets.cluster.ClusterParticipantsOnlyInterceptor}),
 * and the caller's own token - forwarded by the client - is still subject to the
 * hosting service's security configuration.
 * </p>
 *
 * <p>
 * Secret content crosses the wire as JSON carried inside
 * {@link GeboSecretContentEnvelope} / {@link GeboSecretStoreRequest} rather than
 * as a typed {@link AbstractGeboSecretContent} field, because that base class has
 * no Jackson type information and must not gain any (see
 * {@link GeboSecretContentTypes}).
 * </p>
 *
 * <p>
 * <b>Deliberately not a {@code @RestController}.</b> Every Gebo application
 * component-scans {@code ai.gebo}, and a {@code @RestController} (being a
 * {@code @Component}) would be picked up by that scan on its own - publishing
 * these endpoints without the participants guard, which is contributed by
 * {@link ai.gebo.microservices.secrets.config.GeboSecretsClusterControllerAutoConfiguration}
 * and not by the scan. Type-level {@code @RequestMapping} + {@code @ResponseBody}
 * gives identical behaviour (Spring detects a handler by either
 * {@code @Controller} or {@code @RequestMapping} on the bean type) while leaving
 * the class invisible to component scanning, so the only way it can reach the
 * container is through that auto-configuration - which registers the guard with
 * it. Endpoints and guard therefore cannot come apart.
 * </p>
 *
 * Gebo.ai comment agent
 */
@ResponseBody
@RequestMapping("${ai.gebo.secrets.cluster.base-path:api/cluster/SecretsController}")
public class SecretsClusterController {

	private final IGeboSecretsAccessService secretsService;
	private final ObjectMapper mapper;

	public SecretsClusterController(IGeboSecretsAccessService secretsService, ObjectMapper mapper) {
		this.secretsService = secretsService;
		this.mapper = mapper;
	}

	/**
	 * The decrypted content of a secret, as JSON plus its type.
	 *
	 * <p>
	 * A custom secret is read through
	 * {@link IGeboSecretsAccessService#getCustomSecretContentById(String, Class)}
	 * into {@link GeboRawCustomSecretContent} so the stored JSON is reproduced
	 * verbatim, subclass fields included - the caller, not this service, knows which
	 * {@code GeboCustomSecretContent} subclass the content really is.
	 * </p>
	 *
	 * @param id the secret's unique id
	 * @return the content envelope
	 * @throws GeboCryptSecretException if the secret does not exist or cannot be
	 *             decrypted
	 */
	@GetMapping(value = "getSecretContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboSecretContentEnvelope getSecretContentById(@RequestParam("id") String id)
			throws GeboCryptSecretException {
		SecretInfo info = secretsService.getSecretInfoById(id);
		if (info == null) {
			// Same contract as the local implementation: an unknown id is an error here,
			// while getSecretInfoById below simply yields nothing.
			throw new GeboCryptSecretException("Unkown secret with code=>" + id);
		}
		if (info.getSecretType() == GeboSecretType.CUSTOM_SECRET) {
			GeboRawCustomSecretContent raw = secretsService.getCustomSecretContentById(id,
					GeboRawCustomSecretContent.class);
			return new GeboSecretContentEnvelope(GeboSecretType.CUSTOM_SECRET, toJson(raw));
		}
		AbstractGeboSecretContent content = secretsService.getSecretContentById(id);
		return new GeboSecretContentEnvelope(content.type(), toJson(content));
	}

	/**
	 * Metadata of a secret.
	 *
	 * @param code the secret's unique id
	 * @return the secret info, or {@code null} (HTTP 200 with an empty body) if no
	 *         such secret exists - mirroring the local implementation
	 * @throws GeboCryptSecretException if the lookup fails
	 */
	@GetMapping(value = "getSecretInfoById", produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo getSecretInfoById(@RequestParam("code") String code) throws GeboCryptSecretException {
		return secretsService.getSecretInfoById(code);
	}

	/**
	 * Metadata of every secret stored under a context.
	 *
	 * @param contextCode the context to list
	 * @return the secrets' info
	 * @throws GeboCryptSecretException if the lookup fails
	 */
	@GetMapping(value = "getSecretInfoByContextCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SecretInfo> getSecretInfoByContextCode(@RequestParam("contextCode") String contextCode)
			throws GeboCryptSecretException {
		return secretsService.getSecretInfoByContextCode(contextCode);
	}

	/**
	 * Stores a secret, serving both store overloads: when the request carries a
	 * {@code secretId} the secret is stored under it, otherwise the service
	 * generates one.
	 *
	 * @param request the content and its metadata
	 * @return the id the secret is stored under
	 * @throws GeboCryptSecretException if the content cannot be read or stored
	 */
	@PostMapping(value = "storeSecret", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String storeSecret(@RequestBody @Valid @NotNull GeboSecretStoreRequest request)
			throws GeboCryptSecretException {
		AbstractGeboSecretContent content = readContent(request);
		String secretId = request.getSecretId();
		if (secretId == null || secretId.isBlank()) {
			return secretsService.storeSecret(content, request.getDescription(), request.getContextCode());
		}
		secretsService.storeSecret(content, request.getDescription(), request.getContextCode(), secretId);
		return secretId;
	}

	/**
	 * Updates an existing secret; the request's {@code secretId} is the code of the
	 * secret to update.
	 *
	 * @param request the new content and its metadata
	 * @throws GeboCryptSecretException if the content cannot be read, or the secret
	 *             does not exist
	 */
	@PostMapping(value = "updateSecret", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateSecret(@RequestBody @Valid @NotNull GeboSecretStoreRequest request)
			throws GeboCryptSecretException {
		AbstractGeboSecretContent content = readContent(request);
		secretsService.updateSecret(content, request.getDescription(), request.getContextCode(),
				request.getSecretId());
	}

	/**
	 * Deletes a secret.
	 *
	 * @param code the secret's unique id
	 * @throws GeboCryptSecretException if the deletion fails
	 */
	@DeleteMapping("deleteSecret")
	public void deleteSecret(@RequestParam("code") String code) throws GeboCryptSecretException {
		secretsService.deleteSecret(code);
	}

	/** Reads the request's JSON content into the concrete class its type maps to. */
	private AbstractGeboSecretContent readContent(GeboSecretStoreRequest request) throws GeboCryptSecretException {
		Class<? extends AbstractGeboSecretContent> contentClass = GeboSecretContentTypes
				.contentClassFor(request.getSecretType());
		if (contentClass == null) {
			throw new GeboCryptSecretException("Unkown value of SecretType " + request.getSecretType());
		}
		try {
			return mapper.readValue(request.getContentJson(), contentClass);
		} catch (JacksonException e) {
			throw new GeboCryptSecretException("Cannot read secret content of type " + request.getSecretType(), e);
		}
	}

	private String toJson(AbstractGeboSecretContent content) throws GeboCryptSecretException {
		try {
			return mapper.writeValueAsString(content);
		} catch (JacksonException e) {
			throw new GeboCryptSecretException("Cannot write secret content of type " + content.type(), e);
		}
	}
}
