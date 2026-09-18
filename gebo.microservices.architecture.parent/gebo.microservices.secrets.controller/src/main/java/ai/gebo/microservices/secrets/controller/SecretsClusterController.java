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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.microservices.cluster.ClusterParticipantsGuard;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.GeboSecretContentEnvelope;
import ai.gebo.secrets.model.GeboSecretStoreRequest;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.repository.GeboSecretRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGeboSecretsExternalStorageService;
import ai.gebo.secrets.services.IGSecretsStaticConfigurationDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.ObjectMapper;

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
 * <h2>Not every secret HAS a repository record - and the wire stays ciphertext
 * regardless</h2>
 * <p>
 * Passing the stored ciphertext through works only for the secrets that are in
 * the Mongo repository. Two sources are not:
 * </p>
 * <ul>
 * <li>a secret declared under {@code ai.gebo.secrets.config.*}
 * ({@link IGSecretsStaticConfigurationDao}) - it lives in this service's own
 * configuration and is never written to the store;</li>
 * <li>every secret, once an {@link IGeboSecretsExternalStorageService} is the
 * active storage - they live in the vault, and the SPI hands them back
 * <b>decrypted</b>, which is all its contract offers.</li>
 * </ul>
 *
 * <p>
 * Both are reached through {@link IGeboSecretsAccessService}, which owns that
 * resolution order, and what comes back is plaintext - so it is sealed here, with
 * the shared key, before it is put in the envelope. <b>The policy is not
 * weakened: no secret leaves this service in the clear, whatever its source.</b>
 * What is different is only where the plaintext momentarily exists - in this
 * service, which is the one that holds the crypting keys, holds the declarations
 * in its own configuration file, and is the only participant trusted with either.
 * The caller cannot tell, and decrypts exactly as it would for a stored secret.
 * </p>
 *
 * <p>
 * Without this, a declared secret would work in the monolith and silently not
 * exist in the cluster, and an externalised store would answer "unknown secret"
 * for every id - which is the worst shape either could take.
 * </p>
 *
 * <p>
 * The writes need the mirror of the declared case: they bypass
 * {@code GeboSecretsAccessServiceImpl}, and with it the guard that refuses a
 * write to a configuration-declared secret, so they re-apply the code check
 * themselves, with {@link IGeboSecretsAccessService#READ_ONLY_SECRET_MESSAGE}.
 * The {@code readOnly} content check cannot be applied here - the content arrives
 * encrypted, by design - and belongs to the caller, where the client
 * implementation applies it before the request is ever made.
 * </p>
 *
 * <p>
 * <b>Known limitation, unchanged here:</b> the writes and
 * {@code getSecretContentById}'s repository branch address the Mongo repository
 * directly, so an externalised store is served on the read path only. Routing the
 * cluster writes through the external SPI is a separate change - it cannot be done
 * without decrypting the incoming ciphertext, which is the one thing this surface
 * refuses to do.
 * </p>
 *
 * <p>
 * The counterpart of - not a replacement for - {@code api/admin/SecretsController},
 * which stays the ADMIN/UI surface. Reachable only from a microservice currently
 * registered in the cluster registry - every method checks
 * {@link ClusterParticipantsGuard#check}, explicitly, first line.
 * </p>
 *
 * <p>
 * A plain {@code @RestController}: this module is only ever a dependency of the
 * service that owns the secrets store (heimdall), so component-scan discovery is
 * already scoped correctly by Maven, and there is no separate guard bean whose
 * presence needs to stay atomic with this one's.
 * </p>
 *
 * Gebo.ai comment agent
 */
@RestController
@ConditionalOnProperty(prefix = "ai.gebo.secrets.cluster", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@RequestMapping("${ai.gebo.secrets.cluster.base-path:api/cluster/SecretsController}")
public class SecretsClusterController {

	private static final ObjectMapper mapper = new ObjectMapper();

	private final GeboSecretRepository repository;
	private final IGeboSecretsAccessService secretsService;
	private final GeboClusterParticipants participants;
	private final IGSecretsStaticConfigurationDao staticConfigurationDao;
	private final Optional<IGeboSecretsExternalStorageService> externalStorage;
	/**
	 * Used for one thing only: sealing a content that did NOT come out of the
	 * repository - a declared one, or one the external vault returned decrypted -
	 * so that it leaves this service the same way a stored one does. A stored
	 * secret's ciphertext is never touched.
	 */
	private final IGeboCryptingService cryptService;

	public SecretsClusterController(GeboSecretRepository repository, IGeboSecretsAccessService secretsService,
			GeboClusterParticipants participants, IGSecretsStaticConfigurationDao staticConfigurationDao,
			Optional<IGeboSecretsExternalStorageService> externalStorage, IGeboCryptingService cryptService) {
		this.repository = repository;
		this.secretsService = secretsService;
		this.participants = participants;
		this.staticConfigurationDao = staticConfigurationDao;
		this.externalStorage = externalStorage;
		this.cryptService = cryptService;
	}

	/**
	 * Whether the repository is NOT where this id's content lives - the two cases
	 * {@link IGeboSecretsAccessService} answers from somewhere else, tested in its
	 * own order so that the two never disagree about which source owns an id.
	 */
	private boolean isServedOutsideTheRepository(String id) {
		return staticConfigurationDao.isConfiguredCode(id) || (externalStorage.isPresent()
				&& externalStorage.get().isConfigured() && externalStorage.get().isActiveStorage());
	}

	/**
	 * Refuses a write or a delete aimed at a code the configuration declares, with
	 * the interface's own refusal message - these endpoints write through the
	 * repository and so never reach {@code GeboSecretsAccessServiceImpl}'s guard.
	 */
	private void checkCodeNotConfigured(String code) throws GeboCryptSecretException {
		if (staticConfigurationDao.isConfiguredCode(code))
			throw new GeboCryptSecretException(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
	}

	/**
	 * The secret's content <b>as stored</b> - still encrypted - plus its type.
	 *
	 * <p>
	 * A secret that has no repository record - declared in the configuration, or
	 * held by an active external vault - is sealed on the spot with the shared key,
	 * so the caller cannot tell, and does not need to, where the ciphertext came
	 * from.
	 * </p>
	 *
	 * @param id the secret's unique id
	 * @return the envelope carrying the ciphertext
	 * @throws GeboCryptSecretException if there is no such secret
	 */
	@GetMapping(value = "getSecretContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboSecretContentEnvelope getSecretContentById(@RequestParam("id") String id, HttpServletRequest request)
			throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
		if (isServedOutsideTheRepository(id)) {
			// Plaintext in, ciphertext out: the access service is the only thing that
			// knows how to resolve these, and it resolves them decrypted.
			AbstractGeboSecretContent content = secretsService.getSecretContentById(id);
			return new GeboSecretContentEnvelope(content.type(),
					cryptService.crypt(mapper.writeValueAsString(content)));
		}
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
	public SecretInfo getSecretInfoById(@RequestParam("code") String code, HttpServletRequest request)
			throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
		return secretsService.getSecretInfoById(code);
	}

	@GetMapping(value = "getSecretInfoByContextCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SecretInfo> getSecretInfoByContextCode(@RequestParam("contextCode") String contextCode,
			HttpServletRequest request) throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
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
	 * @param storeRequest the encrypted content and its metadata
	 * @return the id the secret is stored under
	 */
	@PostMapping(value = "storeSecret", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String storeSecret(@RequestBody @Valid @NotNull GeboSecretStoreRequest storeRequest,
			HttpServletRequest request) throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
		String secretId = storeRequest.getSecretId();
		if (secretId == null || secretId.isBlank()) {
			secretId = UUID.randomUUID().toString();
		}
		checkCodeNotConfigured(secretId);
		GeboSecret secret = new GeboSecret();
		secret.setCode(secretId);
		secret.setContextCode(storeRequest.getContextCode());
		secret.setDescription(storeRequest.getDescription());
		secret.setSecretType(storeRequest.getSecretType());
		secret.setSecretContent(storeRequest.getCryptedContent());
		secret.setCreationDate(new Date());
		repository.insert(secret);
		return secretId;
	}

	/**
	 * Updates an existing secret with already-encrypted content; the request's
	 * {@code secretId} is the code of the secret to update.
	 */
	@PostMapping(value = "updateSecret", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateSecret(@RequestBody @Valid @NotNull GeboSecretStoreRequest storeRequest,
			HttpServletRequest request) throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
		checkCodeNotConfigured(storeRequest.getSecretId());
		Optional<GeboSecret> found = repository.findById(storeRequest.getSecretId());
		if (found.isEmpty()) {
			throw new GeboCryptSecretException("Secret with code=>" + storeRequest.getSecretId() + " not found");
		}
		GeboSecret secret = found.get();
		secret.setSecretContent(storeRequest.getCryptedContent());
		secret.setDescription(storeRequest.getDescription());
		secret.setContextCode(storeRequest.getContextCode());
		secret.setSecretType(storeRequest.getSecretType());
		repository.save(secret);
	}

	@DeleteMapping("deleteSecret")
	public void deleteSecret(@RequestParam("code") String code, HttpServletRequest request)
			throws GeboCryptSecretException {
		ClusterParticipantsGuard.check(participants, request);
		secretsService.deleteSecret(code);
	}

	/**
	 * The ids of every secret, declared or stored.
	 *
	 * <p>
	 * Delegated to {@link IGeboSecretsAccessService} rather than read off the
	 * repository: ids are not secret material, so the "never decrypt on a caller's
	 * behalf" rule does not apply, and the service is where declared and stored ids
	 * are merged - and de-duplicated, when a declaration shadows a record.
	 * </p>
	 */
	@GetMapping(value = "getAllSecretsId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllSecretsId(HttpServletRequest request) {
		ClusterParticipantsGuard.check(participants, request);
		return secretsService.getAllSecretsId();
	}
}
