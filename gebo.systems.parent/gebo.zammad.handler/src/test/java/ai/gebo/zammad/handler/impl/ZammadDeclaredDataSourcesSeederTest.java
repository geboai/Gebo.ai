/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ai.gebo.model.virtualfs.VFilesystemReference;

class ZammadDeclaredDataSourcesSeederTest {

	@Test
	void wholeGroupsAndKnowledgeBases() {
		VFilesystemReference reference = ZammadDeclaredDataSourcesSeeder.toReference("/groups/2/", true);
		assertEquals("GROUP:2", reference.root.getCode());
		assertNull(reference.path);
		reference = ZammadDeclaredDataSourcesSeeder.toReference("knowledge_bases/1", true);
		assertEquals("KNOWLEDGE_BASE:1", reference.root.getCode());
		assertNull(reference.path);
	}

	@Test
	void ticketsCategoriesAndAnswers() {
		VFilesystemReference ticket = ZammadDeclaredDataSourcesSeeder.toReference("groups/2/tickets/#42", true);
		assertEquals("GROUP:2", ticket.root.getCode());
		assertEquals("TICKET:42", ticket.path.absolutePath);
		assertEquals("CATEGORY:3",
				ZammadDeclaredDataSourcesSeeder.toReference("knowledge_bases/1/categories/3", true).path.absolutePath);
		assertEquals("ANSWER:7",
				ZammadDeclaredDataSourcesSeeder.toReference("knowledge_bases/1/answers/7", true).path.absolutePath);
	}

	@Test
	void wrongDeclarationsAreRefused() {
		assertThrows(IllegalStateException.class, () -> ZammadDeclaredDataSourcesSeeder.toReference("", true));
		assertThrows(IllegalStateException.class, () -> ZammadDeclaredDataSourcesSeeder.toReference("projects/2", true));
		assertThrows(IllegalStateException.class, () -> ZammadDeclaredDataSourcesSeeder.toReference("groups/abc", true));
		assertThrows(IllegalStateException.class,
				() -> ZammadDeclaredDataSourcesSeeder.toReference("groups/2/answers/7", true));
		assertThrows(IllegalStateException.class,
				() -> ZammadDeclaredDataSourcesSeeder.toReference("knowledge_bases/1/tickets/7", true));
		assertThrows(IllegalStateException.class,
				() -> ZammadDeclaredDataSourcesSeeder.toReference("groups/2/tickets", true));
		// every declared Zammad path is a folder
		assertThrows(IllegalStateException.class, () -> ZammadDeclaredDataSourcesSeeder.toReference("groups/2", false));
	}
}
