/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.heimdall;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.acl.mongo.AclAliasesDaoImpl;
import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.microservices.acl.controller.AclAliasesClusterController;
import ai.gebo.microservices.secrets.controller.SecretsClusterController;
import ai.gebo.microservices.security.controller.SecurityDirectoryClusterController;
import ai.gebo.security.controller.AuthController;
import ai.gebo.security.directory.mongo.MongoSecurityDirectory;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGeboSystemUserService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * heimdall actually starts, and the surfaces it exists to serve are actually
 * published.
 *
 * <h2>Why this test exists</h2>
 * <p>
 * Until now the cluster server-proxies had <b>never been executed</b>. The monolith's
 * context tests do not cover them - the monolith is not a cluster participant and
 * carries none of the SP modules - and heimdall had no tests at all. So a whole class
 * of defect was invisible to a green build: an interface whose only implementation
 * lives in an owner-only module, a controller whose constructor no longer matches its
 * auto-configuration, an autoconfiguration whose conditions never fire. Two bugs of
 * exactly that shape already shipped and were caught by reading rather than by
 * building ({@code IGeboSecretsAccessService} and {@code IGOauth2AccessTokenService},
 * each left with no implementation on three services).
 * </p>
 *
 * <h2>The DiscoveryClient is not incidental</h2>
 * <p>
 * The cluster guard admits only callers registered in service discovery, and it
 * <b>fails closed</b>: with no {@code DiscoveryClient} there is no
 * {@code GeboClusterParticipants} bean, and the SP auto-configurations - which are
 * {@code @ConditionalOnBean} on it - publish nothing at all. So a test that omitted
 * discovery would start heimdall happily and assert nothing about the endpoints,
 * which is the failure mode this test is here to prevent. The stub below supplies one.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Testcontainers
@SpringBootTest(classes = { HeimdallApplication.class, HeimdallContextTest.DiscoveryStubConfig.class })
class HeimdallContextTest {

	@Container
	static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

	static {
		try {
			Path home = Files.createTempDirectory("heimdall-home");
			Path work = Files.createTempDirectory("heimdall-work");
			System.setProperty(EnvironmentHolder.GEBO_HOME, home.toAbsolutePath().toString());
			System.setProperty(EnvironmentHolder.GEBO_WORK_DIRECTORY, work.toAbsolutePath().toString());
		} catch (Exception e) {
			throw new IllegalStateException("Cannot prepare the heimdall test home", e);
		}
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.host", mongo::getHost);
		registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
		registry.add("ai.gebo.mongodb.enabled", () -> true);
		registry.add("ai.gebo.mongodb.connectionString", mongo::getConnectionString);
		// Do not try to reach a registry that is not there; the stub below is the
		// DiscoveryClient.
		registry.add("eureka.client.enabled", () -> false);
		registry.add("eureka.client.register-with-eureka", () -> false);
		registry.add("eureka.client.fetch-registry", () -> false);
		// A signing key long enough for HS512 (the secret is base64-decoded).
		registry.add("ai.gebo.security.auth.tokenSecret",
				() -> "04ca023b39512e46d0c2cf4b48d5aac61d34302994c87ed4eff225dcf3b0a218"
						+ "739f3897051a057f9b846a69ea2927a587044164b7bae5e1306219d50b588cb1");
		registry.add("ai.gebo.security.auth.tokenExpirationMsec", () -> 120000);
	}

	@Autowired
	ApplicationContext context;

	/**
	 * The three cluster surfaces heimdall exists to serve. Each is published by an
	 * auto-configuration whose conditions - owner beans present, participants bean
	 * present - had never been evaluated in a running context before this test.
	 */
	@Test
	void publishesTheThreeClusterSurfaces() {
		assertThat(context.getBean(SecretsClusterController.class)).isNotNull();
		assertThat(context.getBean(SecurityDirectoryClusterController.class)).isNotNull();
		assertThat(context.getBean(AclAliasesClusterController.class)).isNotNull();
	}

	/**
	 * heimdall OWNS the three stores: it must resolve the local implementations, never
	 * a REST client onto itself.
	 */
	@Test
	void ownsTheThreeStores() {
		assertThat(context.getBean(IGeboSecretsAccessService.class).getClass().getName())
				.isEqualTo("ai.gebo.secrets.services.impl.GeboSecretsAccessServiceImpl");
		assertThat(context.getBean(IGSecurityDirectory.class)).isInstanceOf(MongoSecurityDirectory.class);
		assertThat(context.getBean(IAclAliasesDao.class)).isInstanceOf(AclAliasesDaoImpl.class);
	}

	/**
	 * What makes heimdall the security service rather than merely its keeper: it can
	 * issue a human token.
	 */
	@Test
	void canIssueTokens() {
		assertThat(context.getBean(AuthController.class)).isNotNull();
	}

	/**
	 * The system identity resolves without a Mongo document, and mints a token. This is
	 * the path that took the whole monolith context down once, by touching the crypting
	 * service during bean construction.
	 */
	@Test
	void thePlatformCanAuthenticateAsItself() {
		IGeboSystemUserService systemUser = context.getBean(IGeboSystemUserService.class);

		assertThat(systemUser.getUserInfos().getRoles()).contains("ADMIN");
		assertThat(systemUser.createToken()).isNotBlank();
	}

	/**
	 * A {@link DiscoveryClient} standing in for Eureka. Without one the participants
	 * guard has no membership, fails closed, and the cluster surfaces are never
	 * published - so this is what makes the assertions above meaningful.
	 */
	@TestConfiguration
	static class DiscoveryStubConfig {

		@Bean
		DiscoveryClient discoveryClient() {
			return new DiscoveryClient() {

				@Override
				public String description() {
					return "test";
				}

				@Override
				public List<String> getServices() {
					return List.of();
				}

				@Override
				public List<ServiceInstance> getInstances(String serviceId) {
					return List.of();
				}
			};
		}
	}
}
