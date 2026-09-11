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
import ai.gebo.webdavcms.handler.config.WebdavDataSourcesConfig;

/**
 * That a declared WebDAV path becomes exactly the reference the module's own
 * navigation reads back - the href encoding of {@link WebdavNavigationUtil},
 * decoded here rather than compared against a literal, so the test says what the
 * module means rather than what the string happens to look like.
 *
 * Gebo.ai comment agent
 */
class WebdavDeclaredDataSourcesSeederTest {

	private final WebdavDeclaredDataSourcesSeeder seeder = new WebdavDeclaredDataSourcesSeeder(
			new WebdavDataSourcesConfig(), null, null);

	private static GDeclaredDataSourcePath path(String path, boolean folder) {
		GDeclaredDataSourcePath declared = new GDeclaredDataSourcePath();
		declared.setPath(path);
		declared.setFolder(folder);
		return declared;
	}

	@Test
	void aFolderBecomesItsParentAsRootAndItselfAsTheStep() {
		VFilesystemReference reference = seeder
				.toReference(path("https://dav.example.com/remote.php/dav/files/admin/Policies", true));

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
				.toReference(path("https://dav.example.com/remote.php/dav/files/admin/handbook.pdf", false));

		assertThat(reference.path.folder).isFalse();
		assertThat(reference.path.name).isEqualTo("handbook.pdf");
		assertThat(WebdavNavigationUtil.decodeFiles(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/admin/handbook.pdf");
	}

	@Test
	void aTrailingSlashIsNotPartOfTheHref() {
		VFilesystemReference reference = seeder
				.toReference(path("https://dav.example.com/remote.php/dav/files/admin/Policies/", true));

		assertThat(WebdavNavigationUtil.decodeFolders(java.util.List.of(reference)))
				.containsExactly("https://dav.example.com/remote.php/dav/files/admin/Policies");
	}

	@Test
	void theServerOriginIsTheWholeShareAndHasNoStep() {
		VFilesystemReference reference = seeder.toReference(path("https://dav.example.com", true));

		assertThat(WebdavNavigationUtil.decodeRoot(reference.root)).isEqualTo("https://dav.example.com");
		assertThat(reference.path).isNull();
	}

	@Test
	void aPathThatIsNotAnHrefIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(path("/remote.php/dav/files/admin/Policies", true)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("full href");
	}

	@Test
	void theServerOriginDeclaredAsAFileIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(path("https://dav.example.com", false)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("folder: true");
	}
}
