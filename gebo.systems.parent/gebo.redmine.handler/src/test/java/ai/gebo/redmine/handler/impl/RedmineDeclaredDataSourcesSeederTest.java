/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;

class RedmineDeclaredDataSourcesSeederTest {

	@Test
	void wholeProject() {
		VFilesystemReference reference = RedmineDeclaredDataSourcesSeeder.toReference("/gebo-core/", true);
		assertEquals("PROJECT:gebo-core", reference.root.getCode());
		assertNull(reference.path);
	}

	@Test
	void sectionsIssuesPagesAndFiles() {
		assertEquals("SECTION:ISSUES",
				RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/issues", true).path.absolutePath);
		assertEquals("SECTION:ISSUES|ISSUE:42",
				RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/issues/#42", true).path.absolutePath);
		assertEquals("SECTION:WIKI|WIKI_PAGE:Release_notes",
				RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/wiki/Release_notes", true).path.absolutePath);
		assertEquals("SECTION:FILES|FILE:7",
				RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/files/7", false).path.absolutePath);
	}

	@Test
	void wrongDeclarationsAreRefused() {
		assertThrows(IllegalStateException.class, () -> RedmineDeclaredDataSourcesSeeder.toReference("", true));
		assertThrows(IllegalStateException.class,
				() -> RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/news", true));
		assertThrows(IllegalStateException.class,
				() -> RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/issues/abc", true));
		// a single file is not a folder and the other way round
		assertThrows(IllegalStateException.class,
				() -> RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/files/7", true));
		assertThrows(IllegalStateException.class,
				() -> RedmineDeclaredDataSourcesSeeder.toReference("gebo-core/issues", false));
	}
}
