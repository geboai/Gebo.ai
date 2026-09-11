/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.webdavcms.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.config.WebdavDataSourcesConfig;
import ai.gebo.webdavcms.handler.config.WebdavSystemsConfig;

/**
 * That a declared WebDAV path becomes exactly the reference the module's own
 * navigation reads back - the href encoding of {@link WebdavNavigationUtil},
 * decoded here rather than compared against a literal, so the test says what the
 * module means rather than what the string happens to look like.
 *
 * Gebo.ai comment agent
 */
class WebdavDeclaredDataSourcesSeederTest {

	private static final String SYSTEM_CODE = "corporate-dav";

	/** A systems DAO holding one declared system, to resolve relative paths. */
	private static WebdavSystemsConfigurationDao systemsDao(String baseUri) {
		GWebdavContentManagementSystem system = new GWebdavContentManagementSystem();
		system.setCode(SYSTEM_CODE);
		system.setBaseUri(baseUri);
		WebdavSystemsConfig config = new WebdavSystemsConfig();
		config.setSystems(java.util.List.of(system));
		return new WebdavSystemsConfigurationDao(config, null);
	}

	private final WebdavDeclaredDataSourcesSeeder seeder = new WebdavDeclaredDataSourcesSeeder(
			new WebdavDataSourcesConfig(), null, systemsDao("https://dav.example.com/remote.php/dav"), null);

	private static GDeclaredDataSource source() {
		GDeclaredDataSource declared = new GDeclaredDataSource();
		declared.setCode("corporate-policies");
		declared.setSystemCode(SYSTEM_CODE);
		return declared;
	}

	private static GDeclaredDataSourcePath path(String path, boolean folder) {
		GDeclaredDataSourcePath declared = new GDeclaredDataSourcePath();
		declared.setPath(path);
		declared.setFolder(folder);
		return declared;
	}

	@Test
	void aFolderBecomesItsParentAsRootAndItselfAsTheStep() {
		VFilesystemReference reference = seeder
				.toReference(source(), path("https://dav.example.com/remote.php/dav/files/admin/Policies", true));

		assertThat(WebdavNavigationUtil.decodeRoot(reference.root))
				.isEqualTo("https://dav.example.com/remote.php/dav/files/admin");
		assertThat(reference.root.getUri()).isEqualTo("https://dav.example.com/remote.php/dav/files/admin");
		assertThat(reference.path.folder).isTrue();
		assertThat(reference.path.name).isEqualTo("Policies");
		assertThat(WebdavNavigationUtil.decodeFolders(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/admin/Policies");
	}

	@Test
	void aFileBecomesAFileStep() {
		VFilesystemReference reference = seeder
				.toReference(source(), path("https://dav.example.com/remote.php/dav/files/admin/handbook.pdf", false));

		assertThat(reference.path.folder).isFalse();
		assertThat(reference.path.name).isEqualTo("handbook.pdf");
		assertThat(WebdavNavigationUtil.decodeFiles(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/admin/handbook.pdf");
	}

	@Test
	void aTrailingSlashIsNotPartOfTheHref() {
		VFilesystemReference reference = seeder
				.toReference(source(), path("https://dav.example.com/remote.php/dav/files/admin/Policies/", true));

		assertThat(WebdavNavigationUtil.decodeFolders(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/admin/Policies");
	}

	@Test
	void theServerOriginIsTheWholeShareAndHasNoStep() {
		VFilesystemReference reference = seeder.toReference(source(), path("https://dav.example.com", true));

		assertThat(WebdavNavigationUtil.decodeRoot(reference.root)).isEqualTo("https://dav.example.com");
		assertThat(reference.path).isNull();
	}

	@Test
	void theServerOriginDeclaredAsAFileIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("https://dav.example.com", false)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("folder: true");
	}
	@Test
	void aRelativePathIsResolvedAgainstTheSystemBaseUri() {
		VFilesystemReference reference = seeder.toReference(source(), path("/files/webdav/Policies", true));

		assertThat(WebdavNavigationUtil.decodeRoot(reference.root))
				.isEqualTo("https://dav.example.com/remote.php/dav/files/webdav");
		assertThat(WebdavNavigationUtil.decodeFolders(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/webdav/Policies");
	}

	@Test
	void aRelativePathWithoutALeadingSlashWorksToo() {
		VFilesystemReference reference = seeder.toReference(source(), path("files/webdav/Policies", true));

		assertThat(WebdavNavigationUtil.decodeFolders(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/webdav/Policies");
	}

	@Test
	void aRelativePathAgainstAnUnknownSystemIsRefused() {
		GDeclaredDataSource orphan = source();
		orphan.setSystemCode("not-declared");

		assertThatThrownBy(() -> seeder.toReference(orphan, path("/files/webdav", true)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("not-declared");
	}
}
