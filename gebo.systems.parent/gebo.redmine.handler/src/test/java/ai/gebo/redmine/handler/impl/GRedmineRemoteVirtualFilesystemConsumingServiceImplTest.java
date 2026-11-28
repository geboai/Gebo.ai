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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.redmine.client.model.IdName;
import ai.gebo.redmine.client.model.IssueJournalsInner;
import ai.gebo.redmine.handler.RedmineResourceReference;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedminePathNodeType;

class GRedmineRemoteVirtualFilesystemConsumingServiceImplTest {

	@Test
	void referenceFromNodeMetas() {
		RedmineNativePositionObject node = RedmineNodes.issueContent("http://redmine", "gebo-core", 12, "Crash", null);
		RedmineResourceReference reference = GRedmineRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(node.getResourceReferenceMetaInfos());
		assertEquals(RedminePathNodeType.ISSUE_CONTENT, reference.type);
		assertEquals("gebo-core", reference.projectId);
		assertEquals(12, reference.issueId);
	}

	@Test
	void referenceFromPrivateNoteMetas() {
		IssueJournalsInner journal = new IssueJournalsInner();
		journal.setId(56);
		IdName author = new IdName();
		author.setId(7);
		author.setName("Bea");
		journal.setUser(author);
		journal.setNotes("The customer is Acme.");
		journal.setPrivateNotes(true);
		RedmineNativePositionObject node = RedmineNodes.privateNote("http://redmine", "gebo-core", 12, journal);
		assertEquals(RedminePathNodeType.PRIVATE_NOTE, node.getType());
		assertEquals("56", node.getId());
		assertEquals("private-note-56", node.getCode());
		assertEquals("#12 private note 56.md", node.getName());
		assertEquals(RedmineNodes.MARKDOWN, node.getResourceContentType());
		assertTrue(node.isResource());
		assertEquals("true", node.getResourceReferenceMetaInfos().get(RedmineNativePositionObject.REDMINE_PRIVATE_NOTE));
		assertEquals("7", node.getResourceReferenceMetaInfos().get(RedmineNativePositionObject.REDMINE_NOTE_AUTHOR_ID));
		RedmineResourceReference reference = GRedmineRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(node.getResourceReferenceMetaInfos());
		assertEquals(RedminePathNodeType.PRIVATE_NOTE, reference.type);
		assertEquals("gebo-core", reference.projectId);
		assertEquals(12, reference.issueId);
		assertEquals(56, reference.journalId);
	}

	@Test
	void referenceNeverFails() {
		assertNotNull(GRedmineRemoteVirtualFilesystemConsumingServiceImpl.toReference(null));
		RedmineResourceReference reference = GRedmineRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(Map.of(RedmineNativePositionObject.REDMINE_OBJECT_TYPE, "SOMETHING_ELSE",
						RedmineNativePositionObject.REDMINE_ISSUE_ID, "x"));
		assertNull(reference.type);
		assertNull(reference.issueId);
	}
}
