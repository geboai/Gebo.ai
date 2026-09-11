/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.mock.web.MockHttpServletRequest;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.secrets.config.GeboStaticSecretEntry;
import ai.gebo.secrets.config.GeboStaticSecretsConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.GeboSecretContentEnvelope;
import ai.gebo.secrets.model.GeboSecretStoreRequest;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.repository.GeboSecretRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGSecretsStaticConfigurationDao;
import ai.gebo.secrets.services.impl.GSecretsStaticConfigurationDaoImpl;

/**
 * The one invariant of this surface: <b>nothing leaves it in the clear</b>.
 *
 * <h2>Why this needs its own test</h2>
 * <p>
 * For a stored secret the invariant holds trivially - the ciphertext is read off
 * the repository and passed through. For a secret with no repository record it
 * does not: those are resolved through {@link IGeboSecretsAccessService}, whose
 * contract is to hand back <i>decrypted</i> content, so the controller has to seal
 * it before it goes on the wire. Forgetting that step would not fail any other
 * test - the caller would simply receive a plaintext JSON it would then try to
 * decrypt - so the guarantee is asserted here directly, on the response body.
 * </p>
 *
 * <p>
 * The crypting service is a reversible stand-in rather than the real keystore:
 * what is under test is <i>that the content was sealed at all</i>, not the cipher.
 * </p>
 *
 * Gebo.ai comment agent
 */
class SecretsClusterControllerCiphertextTest {

	/** Marks what went through {@link #cryptService}, so plaintext cannot pass unnoticed. */
	private static final String SEAL = "sealed:";

	private final Map<String, GeboSecret> stored = new LinkedHashMap<>();

	private MockHttpServletRequest request;

	private final IGeboCryptingService cryptService = cryptingStandIn();

	@BeforeEach
	void setUp() {
		stored.clear();
		this.request = new MockHttpServletRequest();
		this.request.setRemoteAddr("127.0.0.1");
	}

	private SecretsClusterController controller(IGSecretsStaticConfigurationDao staticDao,
			IGeboSecretsAccessService secretsService) {
		// Discovery reports nothing; the loopback caller is pinned instead, which is the
		// documented single-host arrangement.
		GeboClusterParticipants participants = new GeboClusterParticipants(emptyDiscovery(),
				GeboMicroservicesTopology.of(List.of()), List.of(), List.of("127.0.0.1"), Duration.ZERO);
		return new SecretsClusterController(repository(), secretsService, participants, staticDao, Optional.empty(),
				cryptService);
	}

	private DiscoveryClient emptyDiscovery() {
		return new DiscoveryClient() {

			@Override
			public String description() {
				return "no discovery";
			}

			@Override
			public List<ServiceInstance> getInstances(String serviceId) {
				return List.of();
			}

			@Override
			public List<String> getServices() {
				return List.of();
			}
		};
	}

	/** A {@link GeboSecretRepository} over a map - see the note in the secrets impl tests. */
	private GeboSecretRepository repository() {
		InvocationHandler handler = (proxy, method, args) -> {
			switch (method.getName()) {
			case "findById":
				return Optional.ofNullable(stored.get((String) args[0]));
			case "findAll":
				return new ArrayList<>(stored.values());
			case "insert":
			case "save": {
				GeboSecret secret = (GeboSecret) args[0];
				stored.put(secret.getCode(), secret);
				return secret;
			}
			case "toString":
				return "inMemoryGeboSecretRepository";
			case "hashCode":
				return System.identityHashCode(proxy);
			case "equals":
				return proxy == args[0];
			default:
				throw new UnsupportedOperationException("The controller should not call " + method.getName());
			}
		};
		return (GeboSecretRepository) Proxy.newProxyInstance(GeboSecretRepository.class.getClassLoader(),
				new Class<?>[] { GeboSecretRepository.class }, handler);
	}

	/**
	 * A reversible stand-in for the keystore-backed service: only
	 * {@code crypt}/{@code decrypt} are ever called here.
	 *
	 * <p>
	 * Base64 rather than a plain marker prefix on purpose - a transform that leaves
	 * the plaintext legible would let the test's own "the secret is not on the wire"
	 * assertion pass for the wrong reason, or fail for one.
	 * </p>
	 */
	private IGeboCryptingService cryptingStandIn() {
		InvocationHandler handler = (proxy, method, args) -> {
			switch (method.getName()) {
			case "crypt":
				if (args[0] instanceof String plain)
					return SEAL + Base64.getEncoder().encodeToString(plain.getBytes(StandardCharsets.UTF_8));
				throw new UnsupportedOperationException("unexpected crypt overload");
			case "decrypt":
				if (args[0] instanceof String sealed)
					return new String(Base64.getDecoder().decode(sealed.substring(SEAL.length())),
							StandardCharsets.UTF_8);
				throw new UnsupportedOperationException("unexpected decrypt overload");
			case "toString":
				return "reversibleCryptingStandIn";
			case "hashCode":
				return System.identityHashCode(proxy);
			case "equals":
				return proxy == args[0];
			default:
				throw new UnsupportedOperationException("The controller should not call " + method.getName());
			}
		};
		return (IGeboCryptingService) Proxy.newProxyInstance(IGeboCryptingService.class.getClassLoader(),
				new Class<?>[] { IGeboCryptingService.class }, handler);
	}

	private IGSecretsStaticConfigurationDao staticDao() {
		GeboUsernamePasswordContent declared = new GeboUsernamePasswordContent();
		declared.setUsername("ingestion");
		declared.setPassword("s3cr3t");
		GeboStaticSecretEntry<GeboUsernamePasswordContent> entry = new GeboStaticSecretEntry<>();
		entry.setCode("declared-account");
		entry.setContextCode("SYSTEMS");
		entry.setSecret(declared);
		GeboStaticSecretsConfig config = new GeboStaticSecretsConfig();
		config.setUsernamePassword(List.of(entry));
		return new GSecretsStaticConfigurationDaoImpl(config);
	}

	/** Only the methods the controller delegates to; the rest must not be called. */
	private IGeboSecretsAccessService accessService(IGSecretsStaticConfigurationDao staticDao) {
		InvocationHandler handler = (proxy, method, args) -> {
			switch (method.getName()) {
			case "getSecretContentById": {
				AbstractGeboSecretContent content = staticDao.findByCode((String) args[0]);
				if (content == null)
					throw new GeboCryptSecretException("Unkown secret with code=>" + args[0]);
				return content;
			}
			case "getAllSecretsId":
				return staticDao.getAllSecretsId();
			case "getSecretInfoById":
				return staticDao.findInfoByCode((String) args[0]);
			case "toString":
				return "staticOnlyAccessService";
			case "hashCode":
				return System.identityHashCode(proxy);
			case "equals":
				return proxy == args[0];
			default:
				throw new UnsupportedOperationException("The controller should not call " + method.getName());
			}
		};
		return (IGeboSecretsAccessService) Proxy.newProxyInstance(IGeboSecretsAccessService.class.getClassLoader(),
				new Class<?>[] { IGeboSecretsAccessService.class }, handler);
	}

	@Test
	void aDeclaredSecretLeavesEncrypted() throws Exception {
		IGSecretsStaticConfigurationDao staticDao = staticDao();
		SecretsClusterController controller = controller(staticDao, accessService(staticDao));

		GeboSecretContentEnvelope envelope = controller.getSecretContentById("declared-account", request);

		assertThat(envelope.getSecretType()).isEqualTo(GeboSecretType.USERNAME_PASSWORD);
		// THE assertion: the password is not on the wire.
		assertThat(envelope.getCryptedContent()).startsWith(SEAL).doesNotContain("s3cr3t");
		// ...and what the caller decrypts is the content, readOnly marker included, so
		// its own write guard fires exactly as it would in the monolith.
		String json = cryptService.decrypt(envelope.getCryptedContent());
		assertThat(json).contains("s3cr3t").contains("\"readOnly\":true");
	}

	@Test
	void aStoredSecretIsPassedThroughUntouched() throws Exception {
		IGSecretsStaticConfigurationDao staticDao = staticDao();
		GeboSecret secret = new GeboSecret();
		secret.setCode("stored-token");
		secret.setSecretType(GeboSecretType.TOKEN);
		secret.setSecretContent("the-stored-ciphertext");
		secret.setCreationDate(new Date());
		stored.put(secret.getCode(), secret);

		GeboSecretContentEnvelope envelope = controller(staticDao, accessService(staticDao))
				.getSecretContentById("stored-token", request);

		// Byte for byte as stored: not re-sealed, not decrypted, not touched.
		assertThat(envelope.getCryptedContent()).isEqualTo("the-stored-ciphertext");
		assertThat(envelope.getSecretType()).isEqualTo(GeboSecretType.TOKEN);
	}

	@Test
	void anUnknownSecretIsStillAnError() {
		IGSecretsStaticConfigurationDao staticDao = staticDao();

		assertThatThrownBy(
				() -> controller(staticDao, accessService(staticDao)).getSecretContentById("no-such-secret", request))
				.isInstanceOf(GeboCryptSecretException.class).hasMessageContaining("Unkown secret");
	}

	@Test
	void aClusterWriteCannotTakeADeclaredCode() {
		IGSecretsStaticConfigurationDao staticDao = staticDao();
		SecretsClusterController controller = controller(staticDao, accessService(staticDao));
		GeboSecretStoreRequest write = new GeboSecretStoreRequest();
		write.setSecretId("declared-account");
		write.setContextCode("SYSTEMS");
		write.setSecretType(GeboSecretType.TOKEN);
		write.setCryptedContent("whatever");

		// These endpoints write straight to the repository, so they never reach
		// GeboSecretsAccessServiceImpl's guard: without their own check a cluster
		// caller could shadow a declared secret with a record nobody can ever read.
		assertThatThrownBy(() -> controller.storeSecret(write, request))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThatThrownBy(() -> controller.updateSecret(write, request))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThat(stored).isEmpty();
	}

	@Test
	void declaredIdsAreListedToTheCluster() {
		IGSecretsStaticConfigurationDao staticDao = staticDao();

		assertThat(controller(staticDao, accessService(staticDao)).getAllSecretsId(request))
				.containsExactly("declared-account");
	}

	@Test
	void theMetadataOfADeclaredSecretSaysItIsReadOnly() throws Exception {
		IGSecretsStaticConfigurationDao staticDao = staticDao();

		SecretInfo info = controller(staticDao, accessService(staticDao)).getSecretInfoById("declared-account",
				request);

		assertThat(info.getReadOnly()).isTrue();
		assertThat(info.getSecretType()).isEqualTo(GeboSecretType.USERNAME_PASSWORD);
	}
}
