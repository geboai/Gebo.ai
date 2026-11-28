/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;

class OpenProjectDeclaredDataSourcesSeederTest {

	@Test
	void wholeProject() {
		VFilesystemReference reference = OpenProjectDeclaredDataSourcesSeeder.toReference("/gebo-core/", true);
		assertEquals("PROJECT:gebo-core", reference.root.getCode());
		assertNull(reference.path);
	}

	@Test
	void sectionsAndItems() {
		assertEquals("SECTION:WORK_PACKAGES",
				OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/work_packages", true).path.absolutePath);
		assertEquals("SECTION:WORK_PACKAGES", OpenProjectDeclaredDataSourcesSeeder
				.toReference("gebo-core/work-packages", true).path.absolutePath);
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:42", OpenProjectDeclaredDataSourcesSeeder
				.toReference("gebo-core/work_packages/#42", true).path.absolutePath);
		assertEquals("SECTION:DOCUMENTS|DOCUMENT:3",
				OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/documents/3", true).path.absolutePath);
		assertEquals("SECTION:MEETINGS|MEETING:7",
				OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/meetings/7", true).path.absolutePath);
		assertEquals("SECTION:NEWS|NEWS:5",
				OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/news/5", false).path.absolutePath);
	}

	@Test
	void wrongDeclarationsAreRefused() {
		assertThrows(IllegalStateException.class, () -> OpenProjectDeclaredDataSourcesSeeder.toReference("", true));
		assertThrows(IllegalStateException.class,
				() -> OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/wiki", true));
		assertThrows(IllegalStateException.class,
				() -> OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/work_packages/abc", true));
		// a single news is not a folder and the other way round
		assertThrows(IllegalStateException.class,
				() -> OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/news/5", true));
		assertThrows(IllegalStateException.class,
				() -> OpenProjectDeclaredDataSourcesSeeder.toReference("gebo-core/work_packages", false));
	}
}
