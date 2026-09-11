/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.secrets.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ByteArrayResource;

import ai.gebo.secrets.model.GeboAwsConnectionCredentials;
import ai.gebo.secrets.model.GeboAwsConnectionCredentials.AwsRegion;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;

/**
 * That {@code ai.gebo.secrets.config.*} really binds - through the same
 * {@link YamlPropertySourceLoader} + {@link Binder} pair Spring Boot uses at
 * startup, from YAML text rather than from a hand-built map.
 *
 * <p>
 * The point being tested is the one thing about
 * {@link GeboStaticSecretsConfig}'s shape that is not obvious: each list's
 * element type is {@code GeboStaticSecretEntry<SomeConcreteContent>}, so the
 * binder has to resolve the entry's {@code secret} property - declared as the
 * type variable - against the list's own generic argument. If it did not, every
 * declared secret would bind with an empty content, and a deployment would find
 * out only when something asked for one.
 * </p>
 *
 * Gebo.ai comment agent
 */
class GeboStaticSecretsConfigBindingTest {

	private GeboStaticSecretsConfig bind(String yaml) {
		YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
		StandardEnvironment environment = new StandardEnvironment();
		List<PropertySource<?>> sources;
		try {
			sources = loader.load("test", new ByteArrayResource(yaml.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		for (PropertySource<?> source : sources) {
			environment.getPropertySources().addFirst(source);
		}
		return Binder.get(environment).bind("ai.gebo.secrets.config", GeboStaticSecretsConfig.class)
				.orElseGet(GeboStaticSecretsConfig::new);
	}

	@Test
	void bindsEachTypeIntoItsConcreteContentClass() {
		GeboStaticSecretsConfig config = bind("""
				ai.gebo.secrets.config:
				  username-password:
				    - code: ingestion-account
				      description: Service account of the nightly ingestion
				      context-code: SYSTEMS
				      secret:
				        username: ingestion
				        password: s3cr3t
				  token:
				    - code: openai-key
				      context-code: LLMS
				      secret:
				        token: sk-configured
				        user: gebo
				  aws-connection:
				    - code: aws-main
				      context-code: SYSTEMS
				      secret:
				        access-key-id: AKIA
				        secret-access-key: shhh
				        region: eu-central-1
				""");

		assertThat(config.getUsernamePassword()).hasSize(1);
		GeboStaticSecretEntry<GeboUsernamePasswordContent> up = config.getUsernamePassword().get(0);
		assertThat(up.getCode()).isEqualTo("ingestion-account");
		assertThat(up.getDescription()).isEqualTo("Service account of the nightly ingestion");
		assertThat(up.getContextCode()).isEqualTo("SYSTEMS");
		assertThat(up.getSecret()).isNotNull();
		assertThat(up.getSecret().getUsername()).isEqualTo("ingestion");
		assertThat(up.getSecret().getPassword()).isEqualTo("s3cr3t");
		assertThat(up.getSecret().type()).isEqualTo(GeboSecretType.USERNAME_PASSWORD);

		GeboStaticSecretEntry<GeboTokenContent> token = config.getToken().get(0);
		assertThat(token.getSecret().getToken()).isEqualTo("sk-configured");
		assertThat(token.getSecret().getUser()).isEqualTo("gebo");

		// The AWS region is an enum whose JSON form is the dashed AWS code; relaxed
		// binding has to accept that same spelling in the configuration.
		GeboStaticSecretEntry<GeboAwsConnectionCredentials> aws = config.getAwsConnection().get(0);
		assertThat(aws.getSecret().getRegion()).isEqualTo(AwsRegion.EU_CENTRAL_1);
		assertThat(aws.getSecret().getAccessKeyId()).isEqualTo("AKIA");

		assertThat(config.allEntries()).hasSize(3);
		// Nothing is declared read-only by the configuration itself; the DAO is what
		// marks it, so that the flag can never be forged from application.yml either.
		assertThat(up.getSecret().getReadOnly()).isNull();
	}

	@Test
	void anEmptyConfigurationBindsToNoEntries() {
		assertThat(bind("ai.gebo.other: 1").allEntries()).isEmpty();
	}

	/**
	 * The properties have to reach a property source the unbound-elements check
	 * actually looks at: it treats {@code systemProperties} and the system
	 * environment as benign, and {@code withPropertyValues(...)} publishes through
	 * the former.
	 */
	private static ApplicationContextInitializer<ConfigurableApplicationContext> properties(Map<String, Object> props) {
		return context -> context.getEnvironment().getPropertySources()
				.addFirst(new MapPropertySource("declared-secrets-test", props));
	}

	@Test
	void aContentViolatingItsOwnConstraintsFailsTheStartup() {
		// The @Valid cascade has to reach INTO the list elements and on into their
		// content: a username/password secret with no password is a misconfiguration
		// the deployment must not survive. Only the real @ConfigurationProperties +
		// @Validated machinery does that, hence a context run rather than the bare
		// Binder the other tests use.
		new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
				.withUserConfiguration(GeboStaticSecretsConfig.class)
				.withInitializer(properties(
						Map.of("ai.gebo.secrets.config.username-password[0].code", "half-a-credential",
								"ai.gebo.secrets.config.username-password[0].secret.username", "ingestion")))
				.run(context -> assertThat(context).hasFailed().getFailure()
						.hasStackTraceContaining("secret.password"));
	}

	@Test
	void anEntryWithoutACodeFailsTheStartup() {
		new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
				.withUserConfiguration(GeboStaticSecretsConfig.class)
				.withInitializer(properties(Map.of("ai.gebo.secrets.config.token[0].secret.token", "sk-anonymous")))
				.run(context -> assertThat(context).hasFailed().getFailure()
						.hasStackTraceContaining("token[0].code"));
	}

	@Test
	void theRealConfigurationPropertiesBeanBindsTheSameWay() {
		new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(ConfigurationPropertiesAutoConfiguration.class))
				.withUserConfiguration(GeboStaticSecretsConfig.class)
				.withInitializer(properties(
						Map.of("ai.gebo.secrets.config.username-password[0].code", "ingestion-account",
								"ai.gebo.secrets.config.username-password[0].context-code", "SYSTEMS",
								"ai.gebo.secrets.config.username-password[0].secret.username", "ingestion",
								"ai.gebo.secrets.config.username-password[0].secret.password", "s3cr3t")))
				.run(context -> {
					assertThat(context).hasSingleBean(GeboStaticSecretsConfig.class);
					GeboStaticSecretsConfig config = context.getBean(GeboStaticSecretsConfig.class);
					assertThat(config.allEntries()).hasSize(1);
					assertThat(config.getUsernamePassword().get(0).getSecret().getPassword()).isEqualTo("s3cr3t");
				});
	}
}
