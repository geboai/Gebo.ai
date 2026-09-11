/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.googledrive.handlers.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import ai.gebo.googledrive.handlers.config.GoogleDriveDataSourcesConfig;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * That a declared {@code driveId/fileId} becomes the position the module's own
 * {@code GoogleDriveNavigationUtil} reads back.
 *
 * Gebo.ai comment agent
 */
class GoogleDriveDeclaredDataSourcesSeederTest {

	private final GoogleDriveDeclaredDataSourcesSeeder seeder = new GoogleDriveDeclaredDataSourcesSeeder(
			new GoogleDriveDataSourcesConfig(), null, null);

	private static GDeclaredDataSourcePath path(String path, boolean folder) {
		GDeclaredDataSourcePath declared = new GDeclaredDataSourcePath();
		declared.setPath(path);
		declared.setFolder(folder);
		return declared;
	}

	@Test
	void aFolderBecomesTheDriveRootAndAFolderStep() {
		VFilesystemReference reference = seeder.toReference(path("0AJv7q2Xk9mLkUk9PVA/1BxY8sQ2fN7pLmRt3KcWv", true));

		assertThat(reference.root.getCode()).isEqualTo("0AJv7q2Xk9mLkUk9PVA");
		assertThat(reference.path.folder).isTrue();
		assertThat(GoogleDriveNavigationUtil.getDriveFolderId(reference.path)).isEqualTo("1BxY8sQ2fN7pLmRt3KcWv");
	}

	@Test
	void aFileBecomesAResourceStep() {
		VFilesystemReference reference = seeder.toReference(path("0AJv7q2Xk9mLkUk9PVA/1BxY8sQ2fN7pLmRt3KcWv", false));

		assertThat(reference.path.folder).isFalse();
		assertThat(reference.path.absolutePath)
				.isEqualTo(GoogleDriveNavigationUtil.GOOGLE_DRIVE_RESOURCE_PREFIX + "1BxY8sQ2fN7pLmRt3KcWv");
		assertThat(GoogleDriveNavigationUtil.getDriveFolderId(reference.path)).isNull();
	}

	@Test
	void aBareDriveIdIsTheWholeDriveAndHasNoStep() {
		VFilesystemReference reference = seeder.toReference(path("0AJv7q2Xk9mLkUk9PVA", true));

		assertThat(reference.root.getCode()).isEqualTo("0AJv7q2Xk9mLkUk9PVA");
		assertThat(reference.path).isNull();
	}

	@Test
	void aDriveDeclaredAsAFileIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(path("0AJv7q2Xk9mLkUk9PVA", false)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("folder: true");
	}

	@Test
	void aFolderNameStyledPathIsRefusedBecauseDriveAddressesByIdOnly() {
		assertThatThrownBy(() -> seeder.toReference(path("0AJv7q2Xk9mLkUk9PVA/Shared/Handbook", true)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("by id");
	}
}
