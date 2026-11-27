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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.openproject.handler.OpenProjectResourceReference;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;

class GOpenProjectRemoteVirtualFilesystemConsumingServiceImplTest {

	@Test
	void referenceFromNodeMetas() {
		OpenProjectNativePositionObject node = OpenProjectNodes.workPackageContent("http://openproject", "gebo-core",
				Map.of("id", 12, "subject", "Crash"));
		OpenProjectResourceReference reference = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(node.getResourceReferenceMetaInfos());
		assertEquals(OpenProjectPathNodeType.WORK_PACKAGE_CONTENT, reference.type);
		assertEquals("gebo-core", reference.projectId);
		assertEquals("12", reference.itemId);
		OpenProjectNativePositionObject attachment = OpenProjectNodes.attachment("http://openproject", "gebo-core",
				Map.of("id", 34, "fileName", "relay.log", "fileSize", 58, "contentType", "text/plain"));
		reference = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(attachment.getResourceReferenceMetaInfos());
		assertEquals(OpenProjectPathNodeType.ATTACHMENT, reference.type);
		assertEquals("34", reference.itemId);
		assertEquals("text/plain", reference.contentType);
		assertEquals(58L, attachment.getResourceFileSize());
	}

	@Test
	void referenceNeverFails() {
		assertNotNull(GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl.toReference(null));
		OpenProjectResourceReference reference = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(Map.of(OpenProjectNativePositionObject.OPENPROJECT_OBJECT_TYPE, "SOMETHING_ELSE",
						OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID, "x"));
		assertNull(reference.type);
		assertNull(reference.itemId);
	}
}
