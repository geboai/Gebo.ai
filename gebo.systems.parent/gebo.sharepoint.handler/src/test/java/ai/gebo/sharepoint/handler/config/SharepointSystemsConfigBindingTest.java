/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.sharepoint.handler.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ByteArrayResource;

import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.SharepointVersion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * That {@code ai.gebo.sharepoint.systems} really binds - through the same
 * {@link YamlPropertySourceLoader} + {@link Binder} pair Spring Boot uses at
 * startup, from YAML text rather than from a hand-built map.
 *
 * <p>
 * Two things about {@link SharepointSystemsConfig}'s shape are worth pinning
 * down. The prefix is shared with
 * {@code MicrosoftSharepointHandlerConfig}, which binds the prompt library off
 * the same {@code ai.gebo.sharepoint} root - a declaration under one must not
 * need the other's properties to be present. And the list binds into a concrete
 * {@code GContentManagementSystem} subclass, so the handler-specific fields
 * ({@code secretCode}, {@code sharepointVersion}) have to arrive alongside the
 * inherited ones ({@code code}, {@code baseUri}, ...) rather than being dropped.
 * </p>
 *
 * <p>
 * The last test covers the reason the bean is {@code @Validated}: SharePoint
 * marks both of its own fields {@code @NotNull}, and a half-declared system has
 * to fail the startup instead of the first connection.
 * </p>
 *
 * Gebo.ai comment agent
 */
class SharepointSystemsConfigBindingTest {

	private SharepointSystemsConfig bind(String yaml) {
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
		return Binder.get(environment).bind("ai.gebo.sharepoint", SharepointSystemsConfig.class)
				.orElseGet(SharepointSystemsConfig::new);
	}

	@Test
	void bindsTheDeclaredSystemsWithTheirHandlerSpecificFields() {
		SharepointSystemsConfig config = bind("""
				ai.gebo.sharepoint:
				  systems:
				    - code: corporate-sharepoint
				      description: Corporate SharePoint Online
				      baseUri: https://example.sharepoint.com
				      sharepointVersion: CLOUD_VERSION
				      secretCode: msgraph-application
				    - code: legacy-farm
				      baseUri: https://sp2019.example.com
				      sharepointVersion: ONPREMISE2019
				      secretCode: sp2019-service-account
				""");

		assertThat(config.getSystems()).hasSize(2);

		GSharepointContentManagementSystem online = config.getSystems().get(0);
		assertThat(online.getCode()).isEqualTo("corporate-sharepoint");
		assertThat(online.getDescription()).isEqualTo("Corporate SharePoint Online");
		assertThat(online.getBaseUri()).isEqualTo("https://example.sharepoint.com");
		assertThat(online.getSharepointVersion()).isEqualTo(SharepointVersion.CLOUD_VERSION);
		assertThat(online.getSecretCode()).isEqualTo("msgraph-application");

		assertThat(config.getSystems().get(1).getSharepointVersion()).isEqualTo(SharepointVersion.ONPREMISE2019);
	}

	@Test
	void theSharedPrefixDoesNotMakeTheTwoBeansNeedEachOther() {
		SharepointSystemsConfig config = bind("""
				ai.gebo.sharepoint:
				  library:
				    - code: some-prompt-library
				  systems:
				    - code: corporate-sharepoint
				      sharepointVersion: CLOUD_VERSION
				      secretCode: msgraph-application
				""");

		assertThat(config.getSystems()).hasSize(1);
		assertThat(config.getSystems().get(0).getCode()).isEqualTo("corporate-sharepoint");
	}

	@Test
	void declaringNoSystemsIsTheNormalCase() {
		SharepointSystemsConfig config = bind("""
				ai.gebo.sharepoint:
				  library:
				    - code: some-prompt-library
				""");

		assertThat(config.getSystems()).isEmpty();
	}

	@Test
	void aHalfDeclaredSystemViolatesItsOwnConstraints() {
		SharepointSystemsConfig config = bind("""
				ai.gebo.sharepoint:
				  systems:
				    - code: corporate-sharepoint
				      baseUri: https://example.sharepoint.com
				""");

		try (var factory = Validation.buildDefaultValidatorFactory()) {
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<SharepointSystemsConfig>> violations = validator.validate(config);

			assertThat(violations).extracting((v) -> v.getPropertyPath().toString())
					.contains("systems[0].secretCode", "systems[0].sharepointVersion");
		}
	}
}
