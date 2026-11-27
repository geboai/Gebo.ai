/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import ai.gebo.glpi.handler.config.GlpiDataSourcesConfig;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

class GlpiDeclaredDataSourcesSeederTest {

	private final GlpiDeclaredDataSourcesSeeder seeder = new GlpiDeclaredDataSourcesSeeder(new GlpiDataSourcesConfig(),
			null, null);

	private static GDeclaredDataSource source() {
		GDeclaredDataSource declared = new GDeclaredDataSource();
		declared.setCode("helpdesk-knowledge");
		declared.setSystemCode("company-glpi");
		return declared;
	}

	private static GDeclaredDataSourcePath path(String path, boolean folder) {
		GDeclaredDataSourcePath declared = new GDeclaredDataSourcePath();
		declared.setPath(path);
		declared.setFolder(folder);
		return declared;
	}

	@Test
	void anEntityIsARootWithNoStep() {
		VFilesystemReference reference = seeder.toReference(source(), path("ENTITY:0", true));
		assertThat(reference.root.getCode()).isEqualTo("ENTITY:0");
		assertThat(reference.path).isNull();
	}

	@Test
	void aSectionOrATicketIsAFolderStep() {
		VFilesystemReference section = seeder.toReference(source(), path("ENTITY:3|TICKETS", true));
		assertThat(section.root.getCode()).isEqualTo("ENTITY:3");
		assertThat(section.path.absolutePath).isEqualTo("TICKETS");
		assertThat(section.path.folder).isTrue();
		VFilesystemReference ticket = seeder.toReference(source(), path("ENTITY:3|TICKETS|TICKET:1042", true));
		assertThat(ticket.path.absolutePath).isEqualTo("TICKETS|TICKET:1042");
		assertThat(ticket.path.name).isEqualTo("TICKET:1042");
	}

	@Test
	void anArticleIsAResourceStep() {
		VFilesystemReference article = seeder.toReference(source(), path("ENTITY:3|KB|KBARTICLE:15", false));
		assertThat(article.path.absolutePath).isEqualTo("KB|KBARTICLE:15");
		assertThat(article.path.folder).isFalse();
	}

	@Test
	void aWrongFolderFlagIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("ENTITY:3|KB|KBARTICLE:15", true)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("folder: false");
		assertThatThrownBy(() -> seeder.toReference(source(), path("ENTITY:3", false)))
				.isInstanceOf(IllegalStateException.class);
	}

	@Test
	void aPathNotInTheGlpiEncodingIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("/Documents/Policies", true)))
				.isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> seeder.toReference(source(), path("ENTITY:x|TICKETS", true)))
				.isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> seeder.toReference(source(), path("ENTITY:3|TICKETS|ISSUE-KEY:9", true)))
				.isInstanceOf(IllegalStateException.class);
	}
}
