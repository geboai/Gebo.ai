/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.GeboSecretContentEnvelope;
import ai.gebo.secrets.model.GeboSecretStoreRequest;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.repository.GeboSecretRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * The secrets store exposed to the other microservices of the cluster.
 *
 * <h2>It is a store, not a decryption service</h2>
 * <p>
 * Every method here moves the secret content <b>in its encrypted form</b>, byte for
 * byte as the repository holds it. This service never decrypts on a caller's behalf,
 * and a write arrives already encrypted, so it never sees a plaintext secret either.
 * The caller decrypts and encrypts locally with its own {@code IGeboCryptingService}
 * - every Gebo service has one, and the key material is shared (a bundled keystore,
 * or one pointed at by configuration).
 * </p>
 *
 * <p>
 * The result: <b>no secret is ever in the clear on the network</b>, not even between
 * two services inside the cluster, and not even to an attacker who defeats the
 * participants guard or terminates TLS. The guard bounds <i>who may fetch a
 * ciphertext</i>; the crypting keys bound <i>who can read one</i>. Two independent
 * controls rather than one.
 * </p>
 *
 * <p>
 * This is also why it goes through the repository rather than
 * {@link IGeboSecretsAccessService}: that interface's contract is to hand back
 * <i>decrypted</i> content, which is precisely what must not happen here.
 * </p>
 *
 * <p>
 * The counterpart of - not a replacement for - {@code api/admin/SecretsController},
 * which stays the ADMIN/UI surface. Reachable only from a microservice currently
 * registered in the cluster registry
 * ({@link ai.gebo.microservices.cluster.ClusterParticipantsOnlyInterceptor}).
 * </p>
 *
 * <p>
 * <b>Deliberately not a {@code @RestController}</b>: every Gebo app component-scans
 * {@code ai.gebo}, and a {@code @Component} would be published by that scan without
 * the participants guard, which only the auto-configuration installs alongside it.
 * </p>
 *
 * Gebo.ai comment agent
 */
@ResponseBody
@RequestMapping("${ai.gebo.secrets.cluster.base-path:api/cluster/SecretsController}")
public class SecretsClusterController {

	private final GeboSecretRepository repository;
	private final IGeboSecretsAccessService secretsService;

	public SecretsClusterController(GeboSecretRepository repository, IGeboSecretsAccessService secretsService) {
		this.repository = repository;
		this.secretsService = secretsService;
	}

	/**
	 * The secret's content <b>as stored</b> - still encrypted - plus its type.
	 *
	 * @param id the secret's unique id
	 * @return the envelope carrying the ciphertext
	 * @throws GeboCryptSecretException if there is no such secret
	 */
	@GetMapping(value = "getSecretContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboSecretContentEnvelope getSecretContentById(@RequestParam("id") String id)
			throws GeboCryptSecretException {
		GeboSecret secret = repository.findById(id).orElse(null);
		if (secret == null) {
			// Same contract as the local implementation: an unknown id is an error here.
			throw new GeboCryptSecretException("Unkown secret with code=>" + id);
		}
		return new GeboSecretContentEnvelope(secret.getSecretType(), secret.getSecretContent());
	}

	/**
	 * @param code the secret's unique id
	 * @return the secret's metadata, or {@code null} if there is no such secret
	 */
	@GetMapping(value = "getSecretInfoById", produces = MediaType.APPLICATION_JSON_VALUE)
	public SecretInfo getSecretInfoById(@RequestParam("code") String code) throws GeboCryptSecretException {
		return secretsService.getSecretInfoById(code);
	}

	@GetMapping(value = "getSecretInfoByContextCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SecretInfo> getSecretInfoByContextCode(@RequestParam("contextCode") String contextCode)
			throws GeboCryptSecretException {
		return secretsService.getSecretInfoByContextCode(contextCode);
	}

	/**
	 * Stores an already-encrypted secret, serving both store overloads: when the
	 * request carries a {@code secretId} the secret is stored under it, otherwise one
	 * is generated.
	 *
	 * <p>
	 * The ciphertext is written exactly as received. Re-encrypting it here would be
	 * both pointless and wrong - the content is already sealed with the shared key, and
	 * this service has no business unsealing it.
	 * </p>
	 *
	 * @param request the encrypted content and its metadata
	 * @return the id the secret is stored under
	 */
	@PostMapping(value = "storeSecret", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String storeSecret(@RequestBody @Valid @NotNull GeboSecretStoreRequest request) {
		String secretId = request.getSecretId();
		if (secretId == null || secretId.isBlank()) {
			secretId = UUID.randomUUID().toString();
		}
		GeboSecret secret = new GeboSecret();
		secret.setCode(secretId);
		secret.setContextCode(request.getContextCode());
		secret.setDescription(request.getDescription());
		secret.setSecretType(request.getSecretType());
		secret.setSecretContent(request.getCryptedContent());
		secret.setCreationDate(new Date());
		repository.insert(secret);
		return secretId;
	}

	/**
	 * Updates an existing secret with already-encrypted content; the request's
	 * {@code secretId} is the code of the secret to update.
	 */
	@PostMapping(value = "updateSecret", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateSecret(@RequestBody @Valid @NotNull GeboSecretStoreRequest request)
			throws GeboCryptSecretException {
		Optional<GeboSecret> found = repository.findById(request.getSecretId());
		if (found.isEmpty()) {
			throw new GeboCryptSecretException("Secret with code=>" + request.getSecretId() + " not found");
		}
		GeboSecret secret = found.get();
		secret.setSecretContent(request.getCryptedContent());
		secret.setDescription(request.getDescription());
		secret.setContextCode(request.getContextCode());
		secret.setSecretType(request.getSecretType());
		repository.save(secret);
	}

	@DeleteMapping("deleteSecret")
	public void deleteSecret(@RequestParam("code") String code) throws GeboCryptSecretException {
		secretsService.deleteSecret(code);
	}

	@GetMapping(value = "getAllSecretsId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllSecretsId() {
		return repository.findAll().stream().map(GeboSecret::getCode).toList();
	}
}
