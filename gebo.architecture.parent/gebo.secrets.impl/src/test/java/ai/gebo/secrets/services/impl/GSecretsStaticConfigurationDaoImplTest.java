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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.secrets.config.GeboStaticSecretEntry;
import ai.gebo.secrets.config.GeboStaticSecretsConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.model.SecretInfo;

/**
 * What {@link GSecretsStaticConfigurationDaoImpl} has to get right about the
 * secrets declared in the configuration.
 *
 * Gebo.ai comment agent
 */
class GSecretsStaticConfigurationDaoImplTest {

	private GeboStaticSecretEntry<GeboUsernamePasswordContent> usernamePassword(String code, String contextCode,
			String username, String password) {
		GeboUsernamePasswordContent content = new GeboUsernamePasswordContent();
		content.setUsername(username);
		content.setPassword(password);
		GeboStaticSecretEntry<GeboUsernamePasswordContent> entry = new GeboStaticSecretEntry<>();
		entry.setCode(code);
		entry.setDescription("declared " + code);
		entry.setContextCode(contextCode);
		entry.setSecret(content);
		return entry;
	}

	private GeboStaticSecretEntry<GeboTokenContent> token(String code, String contextCode, String token) {
		GeboTokenContent content = new GeboTokenContent();
		content.setToken(token);
		content.setUser("gebo");
		GeboStaticSecretEntry<GeboTokenContent> entry = new GeboStaticSecretEntry<>();
		entry.setCode(code);
		entry.setContextCode(contextCode);
		entry.setSecret(content);
		return entry;
	}

	private GeboStaticSecretsConfig config() {
		GeboStaticSecretsConfig config = new GeboStaticSecretsConfig();
		config.setUsernamePassword(List.of(usernamePassword("ingestion-account", "SYSTEMS", "ingestion", "s3cr3t")));
		config.setToken(List.of(token("openai-key", "LLMS", "sk-configured")));
		return config;
	}

	private GSecretsStaticConfigurationDaoImpl dao(GeboStaticSecretsConfig config) {
		return new GSecretsStaticConfigurationDaoImpl(config);
	}

	@Test
	void resolvesADeclaredSecretByCodeAndMarksItReadOnly() {
		AbstractGeboSecretContent content = dao(config()).findByCode("ingestion-account");

		assertThat(content).isInstanceOf(GeboUsernamePasswordContent.class);
		assertThat(((GeboUsernamePasswordContent) content).getPassword()).isEqualTo("s3cr3t");
		assertThat(content.type()).isEqualTo(GeboSecretType.USERNAME_PASSWORD);
		assertThat(AbstractGeboSecretContent.isReadOnlySecret(content)).isTrue();
	}

	@Test
	void anUndeclaredCodeResolvesToNothing() {
		GSecretsStaticConfigurationDaoImpl dao = dao(config());

		assertThat(dao.findByCode("not-declared")).isNull();
		assertThat(dao.findInfoByCode("not-declared")).isNull();
		assertThat(dao.isConfiguredCode("not-declared")).isFalse();
		assertThat(dao.findByCode(null)).isNull();
		assertThat(dao.isConfiguredCode(null)).isFalse();
	}

	@Test
	void eachReadGetsItsOwnContent() {
		GSecretsStaticConfigurationDaoImpl dao = dao(config());

		GeboUsernamePasswordContent first = (GeboUsernamePasswordContent) dao.findByCode("ingestion-account");
		first.setPassword("mutated by a caller");

		GeboUsernamePasswordContent second = (GeboUsernamePasswordContent) dao.findByCode("ingestion-account");
		assertThat(second).isNotSameAs(first);
		assertThat(second.getPassword()).isEqualTo("s3cr3t");
	}

	@Test
	void exposesTheMetadataOfADeclaredSecret() {
		GSecretsStaticConfigurationDaoImpl dao = dao(config());

		SecretInfo info = dao.findInfoByCode("openai-key");
		assertThat(info.getCode()).isEqualTo("openai-key");
		assertThat(info.getContextCode()).isEqualTo("LLMS");
		assertThat(info.getSecretType()).isEqualTo(GeboSecretType.TOKEN);
		assertThat(info.getReadOnly()).isTrue();

		// The held metadata is shared, so a caller's edit must not reach the next read.
		info.setDescription("mutated by a caller");
		assertThat(dao.findInfoByCode("openai-key").getDescription()).isNotEqualTo("mutated by a caller");

		assertThat(dao.findInfoByContextCode("SYSTEMS")).extracting(SecretInfo::getCode)
				.containsExactly("ingestion-account");
		assertThat(dao.findInfoByContextCode("NOTHING-HERE")).isEmpty();
		assertThat(dao.getAllSecretsId()).containsExactly("ingestion-account", "openai-key");
		assertThat(dao.getConfigurations()).hasSize(2)
				.allMatch(AbstractGeboSecretContent::isReadOnlySecret);
	}

	@Test
	void anEmptyConfigurationIsNotAnError() {
		GSecretsStaticConfigurationDaoImpl dao = dao(new GeboStaticSecretsConfig());

		assertThat(dao.getAllSecretsId()).isEmpty();
		assertThat(dao.getConfigurations()).isEmpty();
		assertThat(dao.findByCode("anything")).isNull();
	}

	@Test
	void theSameCodeDeclaredTwiceRefusesToStart() {
		// Across two different type lists, which is the case a per-list check would
		// miss - and the one that would make findByCode's answer depend on field order.
		GeboStaticSecretsConfig config = new GeboStaticSecretsConfig();
		config.setUsernamePassword(List.of(usernamePassword("duplicated", "SYSTEMS", "u", "p")));
		config.setToken(List.of(token("duplicated", "LLMS", "sk-other")));

		assertThatThrownBy(() -> dao(config)).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("duplicated").hasMessageContaining("more than once");
	}

	@Test
	void aDeclarationWithoutACodeRefusesToStart() {
		GeboStaticSecretsConfig config = new GeboStaticSecretsConfig();
		config.setToken(List.of(token("   ", "LLMS", "sk-anonymous")));

		assertThatThrownBy(() -> dao(config)).isInstanceOf(IllegalStateException.class).hasMessageContaining("no code");
	}

	@Test
	void aDeclarationWithoutContentRefusesToStart() {
		GeboStaticSecretEntry<GeboTokenContent> entry = new GeboStaticSecretEntry<>();
		entry.setCode("contentless");
		GeboStaticSecretsConfig config = new GeboStaticSecretsConfig();
		config.setToken(List.of(entry));

		assertThatThrownBy(() -> dao(config)).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("contentless").hasMessageContaining("no secret content");
	}
}
