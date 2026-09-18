/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.awss3.content.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import ai.gebo.awss3.content.handler.config.AwsS3DataSourcesConfig;
import ai.gebo.awss3.content.handler.impl.model.AwsS3NavigationCoordinates;
import ai.gebo.awss3.content.handler.impl.model.AwsS3PathNodeType;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSource;
import ai.gebo.systems.abstraction.layer.config.GDeclaredDataSourcePath;

/**
 * That a declared {@code bucket/key} becomes the position
 * {@code AwsS3NavigationUtil} reads back: the translation is checked by running
 * the module's own decoder over it, not by comparing encoded strings.
 *
 * Gebo.ai comment agent
 */
class AwsS3DeclaredDataSourcesSeederTest {

	private final AwsS3DeclaredDataSourcesSeeder seeder = new AwsS3DeclaredDataSourcesSeeder(
			new AwsS3DataSourcesConfig(), null, null);

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

	private AwsS3NavigationCoordinates coordinatesOf(String path, boolean folder) throws Exception {
		VFilesystemReference reference = seeder.toReference(source(), path(path, folder));
		return AwsS3NavigationUtil.toCoordinates(reference);
	}

	@Test
	void aPrefixBecomesTheBucketRootAndAFolderStep() throws Exception {
		AwsS3NavigationCoordinates coordinates = coordinatesOf("corporate-docs/reports/2026/", true);

		assertThat(coordinates.getRoot().getCode()).isEqualTo("corporate-docs");
		assertThat(coordinates.getBrowsingStepsCustom()).hasSize(2);
		assertThat(coordinates.getBrowsingStepsCustom().get(0).type).isEqualTo(AwsS3PathNodeType.BUCKET);
		assertThat(coordinates.getBrowsingStepsCustom().get(1).type).isEqualTo(AwsS3PathNodeType.FOLDER);
		assertThat(coordinates.getBrowsingStepsCustom().get(1).id).isEqualTo("reports/2026/");
	}

	@Test
	void aPrefixIsGivenTheTrailingSlashSThreeUsesToMeanEverythingUnderIt() throws Exception {
		AwsS3NavigationCoordinates coordinates = coordinatesOf("corporate-docs/reports/2026", true);

		assertThat(coordinates.getBrowsingStepsCustom().get(1).id).isEqualTo("reports/2026/");
	}

	@Test
	void anObjectBecomesAResourceStep() throws Exception {
		AwsS3NavigationCoordinates coordinates = coordinatesOf("corporate-docs/reports/summary.pdf", false);

		assertThat(coordinates.getBrowsingStepsCustom().get(1).type).isEqualTo(AwsS3PathNodeType.RESOURCE);
		assertThat(coordinates.getBrowsingStepsCustom().get(1).id).isEqualTo("reports/summary.pdf");
	}

	@Test
	void aBareBucketIsTheWholeBucketAndHasNoStep() throws Exception {
		VFilesystemReference reference = seeder.toReference(source(), path("corporate-docs", true));

		assertThat(reference.root.getCode()).isEqualTo("corporate-docs");
		assertThat(reference.path).isNull();
	}

	@Test
	void aBucketDeclaredAsAFileIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("corporate-docs", false)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("folder: true");
	}

	@Test
	void aPrefixDeclaredAsAFileIsRefused() {
		assertThatThrownBy(() -> seeder.toReference(source(), path("corporate-docs/reports/", false)))
				.isInstanceOf(IllegalStateException.class).hasMessageContaining("trailing slash");
	}
}
