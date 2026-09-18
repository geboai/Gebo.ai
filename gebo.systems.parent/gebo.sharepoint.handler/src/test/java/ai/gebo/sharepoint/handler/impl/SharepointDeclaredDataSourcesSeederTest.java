/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.sharepoint.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.sharepoint.handler.config.SharepointDataSourcesConfig;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathComponent;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathNodeType;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * That a declared {@code driveId/itemId} becomes the OneDrive position the
 * module's own {@code GMicrosoftGraphNavigationUtils} reads back - and that a
 * SharePoint site is refused rather than half-translated.
 *
 * Gebo.ai comment agent
 */
class SharepointDeclaredDataSourcesSeederTest {

	private final SharepointDeclaredDataSourcesSeeder seeder = new SharepointDeclaredDataSourcesSeeder(
			new SharepointDataSourcesConfig(), null, null);

	private static GDeclaredDataSourcePath path(String path, boolean folder) {
		GDeclaredDataSourcePath declared = new GDeclaredDataSourcePath();
		declared.setPath(path);
		declared.setFolder(folder);
		return declared;
	}

	/** The declaration a path belongs to; these handlers do not read it. */
	private static GDeclaredDataSource source() {
		GDeclaredDataSource declared = new GDeclaredDataSource();
		declared.setCode("declared-source");
		declared.setSystemCode("declared-system");
		return declared;
	}

	@Test
	void aFolderBecomesADriveRootAndAFolderStep() {
		VFilesystemReference reference = seeder.toReference(source(), path("b!xQ3zDrive/01ABCDEFITEM", true));

		assertThat(GMicrosoftGraphNavigationUtils.isDrive(reference.root)).isTrue();
		assertThat(GMicrosoftGraphNavigationUtils.getDriveId(reference.root)).isEqualTo("b!xQ3zDrive");
		List<MicrosoftGraphPathComponent> components = GMicrosoftGraphNavigationUtils.pathComponents(reference.path);
		assertThat(components).hasSize(1);
		assertThat(components.get(0).type).isEqualTo(MicrosoftGraphPathNodeType.DRIVE_ITEM_FOLDER);
		assertThat(components.get(0).id).isEqualTo("01ABCDEFITEM");
		assertThat(reference.path.folder).isTrue();
	}

	@Test
	void aFileBecomesADriveItemStep() {
		VFilesystemReference reference = seeder.toReference(source(), path("b!xQ3zDrive/01ABCDEFITEM", false));

		List<MicrosoftGraphPathComponent> components = GMicrosoftGraphNavigationUtils.pathComponents(reference.path);
		assertThat(components.get(0).type).isEqualTo(MicrosoftGraphPathNodeType.DRIVE_ITEM);
		assertThat(reference.path.folder).isFalse();
	}

	@Test
	void aBareDriveIdIsTheWholeDriveAndHasNoStep() {
		VFilesystemReference reference = seeder.toReference(source(), path("b!xQ3zDrive", true));

		assertThat(GMicrosoftGraphNavigationUtils.getDriveId(reference.root)).isEqualTo("b!xQ3zDrive");
		assertThat(reference.path).isNull();
	}

	@Test
	void aSharepointSiteIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("SHAREPOINT-SITE:contoso.sharepoint.com,abc,def", true)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("admin UI");
	}

	@Test
	void aFolderNameStyledPathIsRefusedBecauseGraphAddressesItemsById() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("b!xQ3zDrive/Shared/Policies", true)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("by id");
	}
}
