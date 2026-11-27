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

import java.util.Map;

import org.junit.jupiter.api.Test;

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
	void referenceNeverFails() {
		assertNotNull(GRedmineRemoteVirtualFilesystemConsumingServiceImpl.toReference(null));
		RedmineResourceReference reference = GRedmineRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(Map.of(RedmineNativePositionObject.REDMINE_OBJECT_TYPE, "SOMETHING_ELSE",
						RedmineNativePositionObject.REDMINE_ISSUE_ID, "x"));
		assertNull(reference.type);
		assertNull(reference.issueId);
	}
}
