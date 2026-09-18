/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.config.GeboStaticSecretEntry;
import ai.gebo.secrets.config.GeboStaticSecretsConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.model.GeboSecret;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.repository.GeboSecretRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.secrets.services.IGSecretsStaticConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.SecurityAuditTaxonomy;

/**
 * How {@link GeboSecretsAccessServiceImpl} integrates the secrets declared in
 * the configuration: where they win a read, and what they make the write paths
 * refuse.
 *
 * <p>
 * The crypting service is deliberately left {@code null}: not one of these paths
 * may reach it. A configured secret is served without touching it, and a refused
 * write must be refused <b>before</b> any content is encrypted - so a
 * {@code NullPointerException} here would itself be the finding.
 * </p>
 *
 * Gebo.ai comment agent
 */
class GeboSecretsAccessServiceImplStaticSecretsTest {

	/** The store, as a {@link GeboSecretRepository} over a map. */
	private final Map<String, GeboSecret> stored = new LinkedHashMap<>();

	private final List<String> auditedActions = new ArrayList<>();

	private GeboSecretRepository repository;

	private IGSecurityAuditLoggerService auditLogger;

	@BeforeEach
	void setUp() {
		stored.clear();
		auditedActions.clear();
		this.repository = inMemoryRepository();
		this.auditLogger = new IGSecurityAuditLoggerService() {

			@Override
			public SecurityEvent newSecurityEvent() {
				return new SecurityEvent(ArchitectureType.MONOLITHIC, "test-user", "203.0.113.7", "test-app",
						"test-correlation-id", Instant.now().toString(), "test", "POST", "/test");
			}

			@Override
			public void log(SecurityEvent event) {
				auditedActions.add(event.getAction() + ":" + event.getOutcome());
			}
		};
	}

	/**
	 * {@link GeboSecretRepository} is a Spring Data interface with a hundred
	 * inherited methods and no implementation to extend; a proxy over the handful
	 * this service actually calls is the honest way to stand it up without a Mongo.
	 */
	private GeboSecretRepository inMemoryRepository() {
		InvocationHandler handler = (proxy, method, args) -> {
			switch (method.getName()) {
			case "findById":
				return Optional.ofNullable(stored.get((String) args[0]));
			case "findAll":
				return new ArrayList<>(stored.values());
			case "findByContextCode": {
				List<GeboSecret> found = new ArrayList<>();
				for (GeboSecret secret : stored.values()) {
					if (secret.getContextCode() != null && secret.getContextCode().equals(args[0]))
						found.add(secret);
				}
				return found;
			}
			case "insert":
			case "save": {
				GeboSecret secret = (GeboSecret) args[0];
				stored.put(secret.getCode(), secret);
				return secret;
			}
			case "deleteById":
				stored.remove((String) args[0]);
				return null;
			case "toString":
				return "inMemoryGeboSecretRepository";
			case "hashCode":
				return System.identityHashCode(proxy);
			case "equals":
				return proxy == args[0];
			default:
				throw new UnsupportedOperationException("The service should not call " + method.getName());
			}
		};
		return (GeboSecretRepository) Proxy.newProxyInstance(GeboSecretRepository.class.getClassLoader(),
				new Class<?>[] { GeboSecretRepository.class }, handler);
	}

	private GeboSecretsAccessServiceImpl service(IGSecretsStaticConfigurationDao staticDao) {
		return new GeboSecretsAccessServiceImpl(repository, null, Optional.empty(), auditLogger, staticDao);
	}

	private IGSecretsStaticConfigurationDao staticDao() {
		GeboUsernamePasswordContent declared = new GeboUsernamePasswordContent();
		declared.setUsername("ingestion");
		declared.setPassword("s3cr3t");
		GeboStaticSecretEntry<GeboUsernamePasswordContent> entry = new GeboStaticSecretEntry<>();
		entry.setCode("declared-account");
		entry.setDescription("declared in application.yml");
		entry.setContextCode("SYSTEMS");
		entry.setSecret(declared);

		GeboCustomSecretContent custom = new GeboCustomSecretContent();
		custom.setContent("{}");
		custom.setContentType("application/json");
		custom.setCustomContentDescription("a declared custom secret");
		GeboStaticSecretEntry<GeboCustomSecretContent> customEntry = new GeboStaticSecretEntry<>();
		customEntry.setCode("declared-custom");
		customEntry.setContextCode("SYSTEMS");
		customEntry.setSecret(custom);

		GeboStaticSecretsConfig config = new GeboStaticSecretsConfig();
		config.setUsernamePassword(List.of(entry));
		config.setCustomSecret(List.of(customEntry));
		return new GSecretsStaticConfigurationDaoImpl(config);
	}

	private void store(String code, String contextCode) {
		GeboSecret secret = new GeboSecret();
		secret.setCode(code);
		secret.setContextCode(contextCode);
		secret.setDescription("stored " + code);
		secret.setSecretType(GeboSecretType.TOKEN);
		secret.setSecretContent("whatever, never decrypted here");
		secret.setCreationDate(new Date());
		stored.put(code, secret);
	}

	@Test
	void aDeclaredSecretIsServedWithoutTouchingTheStore() throws Exception {
		AbstractGeboSecretContent content = service(staticDao()).getSecretContentById("declared-account");

		assertThat(content).isInstanceOf(GeboUsernamePasswordContent.class);
		assertThat(((GeboUsernamePasswordContent) content).getPassword()).isEqualTo("s3cr3t");
		assertThat(AbstractGeboSecretContent.isReadOnlySecret(content)).isTrue();
	}

	@Test
	void aDeclaredSecretShadowsAStoredOneOfTheSameCode() throws Exception {
		// Not a hypothetical: the code may already have been in the store before it
		// was declared. The configuration is the deployment's answer, so it wins.
		store("declared-account", "SYSTEMS");
		GeboSecretsAccessServiceImpl service = service(staticDao());

		assertThat(service.getSecretContentById("declared-account")).isInstanceOf(GeboUsernamePasswordContent.class);
		assertThat(service.getSecretInfoById("declared-account").getReadOnly()).isTrue();
		// ...and the shadowed record is not offered anywhere, because no read of that
		// code could ever reach it.
		assertThat(service.getSecretInfoByContextCode("SYSTEMS")).extracting(SecretInfo::getCode)
				.containsExactly("declared-account", "declared-custom");
		assertThat(service.getAllSecretsId()).containsExactly("declared-account", "declared-custom");
	}

	@Test
	void anUndeclaredCodeStillFallsThroughToTheStore() throws Exception {
		store("stored-token", "LLMS");
		GeboSecretsAccessServiceImpl service = service(staticDao());

		assertThat(service.getSecretInfoById("stored-token").getCode()).isEqualTo("stored-token");
		assertThat(service.getSecretInfoById("stored-token").getReadOnly()).isNull();
		assertThat(service.getSecretInfoByContextCode("LLMS")).extracting(SecretInfo::getCode)
				.containsExactly("stored-token");
		assertThat(service.getAllSecretsId()).contains("stored-token");
		assertThat(service.getSecretInfoById("neither-declared-nor-stored")).isNull();
		assertThatThrownBy(() -> service.getSecretContentById("neither-declared-nor-stored"))
				.isInstanceOf(GeboCryptSecretException.class).hasMessageContaining("Unkown secret");
	}

	@Test
	void aDeclaredCustomSecretIsReadIntoTheCallerSubclass() throws Exception {
		GeboCustomSecretContent content = service(staticDao()).getCustomSecretContentById("declared-custom",
				GeboCustomSecretContent.class);

		assertThat(content.getContentType()).isEqualTo("application/json");
		assertThat(AbstractGeboSecretContent.isReadOnlySecret(content)).isTrue();
	}

	@Test
	void aDeclaredSecretOfTheWrongTypeIsNotReadAsACustomOne() {
		assertThatThrownBy(
				() -> service(staticDao()).getCustomSecretContentById("declared-account", GeboCustomSecretContent.class))
				.isInstanceOf(GeboCryptSecretException.class).hasMessageContaining("must be CUSTOM_SECRET");
	}

	@Test
	void writingBackAReadOnlyContentIsRefused() throws Exception {
		GeboSecretsAccessServiceImpl service = service(staticDao());
		AbstractGeboSecretContent readOnly = service.getSecretContentById("declared-account");

		assertThatThrownBy(() -> service.storeSecret(readOnly, "d", "SYSTEMS"))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThatThrownBy(() -> service.storeSecret(readOnly, "d", "SYSTEMS", "some-other-code"))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThatThrownBy(() -> service.updateSecret(readOnly, "d", "SYSTEMS", "some-other-code"))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThat(stored).isEmpty();
	}

	@Test
	void writingOverADeclaredCodeIsRefusedEvenWithAWritableContent() throws Exception {
		GeboSecretsAccessServiceImpl service = service(staticDao());
		GeboTokenContent writable = new GeboTokenContent();
		writable.setToken("sk-mine");
		writable.setUser("attacker");

		assertThatThrownBy(() -> service.storeSecret(writable, "d", "SYSTEMS", "declared-account"))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThatThrownBy(() -> service.updateSecret(writable, "d", "SYSTEMS", "declared-account"))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
		assertThat(stored).isEmpty();
		assertThat(auditedActions).containsOnly(SecurityAuditTaxonomy.Action.SECRET_CREATE + ":"
				+ SecurityAuditTaxonomy.Outcome.FAILURE,
				SecurityAuditTaxonomy.Action.SECRET_UPDATE + ":" + SecurityAuditTaxonomy.Outcome.FAILURE);
	}

	@Test
	void deletingADeclaredSecretIsRefused() {
		// A delete is given nothing but a code, so the code check is the only guard
		// that can apply - and the reason it exists.
		assertThatThrownBy(() -> service(staticDao()).deleteSecret("declared-account"))
				.isInstanceOf(GeboCryptSecretException.class)
				.hasMessage(IGeboSecretsAccessService.READ_ONLY_SECRET_MESSAGE);
	}

	@Test
	void anOrdinarySecretIsStillWritableAndDeletable() {
		GeboSecretsAccessServiceImpl service = service(staticDao());
		GeboTokenContent writable = new GeboTokenContent();
		writable.setToken("sk-mine");
		writable.setUser("gebo");

		store("stored-token", "LLMS");
		assertThatCode(() -> service.deleteSecret("stored-token")).doesNotThrowAnyException();
		assertThat(stored).isEmpty();
		// The guards must not stand in the way of a normal write; encryption is what
		// this one goes on to need, and the null crypting service is what proves it got
		// past them.
		assertThatThrownBy(() -> service.storeSecret(writable, "d", "LLMS", "a-new-code"))
				.isInstanceOf(NullPointerException.class);
	}
}
