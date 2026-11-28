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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.openproject.handler.OpenProjectResourceReference;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;

class GOpenProjectRemoteVirtualFilesystemConsumingServiceImplTest {
	static final String BASE = "http://openproject";

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

	/** An activity of the work package 12, shaped as the API v3 answers it. */
	static Map<String, Object> activity(int id, String comment, boolean internal) {
		return Map.of("_type", "Activity::Comment", "id", id, "comment", Map.of("format", "markdown", "raw", comment),
				"details", List.of(), "internal", internal, "createdAt", "2026-11-27T19:08:00Z", "_links",
				Map.of("workPackage", Map.of("href", "/api/v3/work_packages/12", "title", "Crash"), "user",
						Map.of("href", "/api/v3/users/6")));
	}

	@Test
	void anInternalCommentIsADocumentFlaggedInternalWithItsAuthor() {
		OpenProjectNativePositionObject node = OpenProjectNodes.internalComment(BASE, "gebo-core", "12",
				activity(57, "The customer is on the old contract.", true));
		assertEquals(OpenProjectPathNodeType.INTERNAL_COMMENT, node.getType());
		assertTrue(node.isResource());
		assertEquals("57", node.getId());
		assertEquals("internal-comment-57.md", node.getCode());
		assertEquals("#12 internal comment 57.md", node.getName());
		assertEquals(BASE + "/work_packages/12", node.getUrl());
		assertEquals(OpenProjectNodes.MARKDOWN, node.getResourceContentType());
		Map<String, Object> metas = node.getResourceReferenceMetaInfos();
		assertEquals("INTERNAL_COMMENT", metas.get(OpenProjectNativePositionObject.OPENPROJECT_OBJECT_TYPE));
		assertEquals("gebo-core", metas.get(OpenProjectNativePositionObject.OPENPROJECT_PROJECT_ID));
		assertEquals("12", metas.get(OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID));
		assertEquals("57", metas.get(OpenProjectNativePositionObject.OPENPROJECT_ACTIVITY_ID));
		assertEquals("true", metas.get(OpenProjectNativePositionObject.OPENPROJECT_INTERNAL_COMMENT));
		assertEquals("6", metas.get(OpenProjectNativePositionObject.OPENPROJECT_COMMENT_AUTHOR_ID));

		OpenProjectResourceReference reference = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(metas);
		assertEquals(OpenProjectPathNodeType.INTERNAL_COMMENT, reference.type);
		assertEquals("57", reference.itemId);
		assertEquals("12", reference.workPackageId);
		assertEquals("gebo-core", reference.projectId);
	}

	@Test
	void onlyInternalActivitiesWithATextAreInternalComments() {
		assertTrue(OpenProjectNodes.isInternalComment(activity(57, "Internal note", true)));
		assertFalse(OpenProjectNodes.isInternalComment(activity(58, "Public note", false)));
		assertFalse(OpenProjectNodes.isInternalComment(activity(59, " ", true)));
		assertFalse(OpenProjectNodes.isInternalComment(
				Map.of("id", 60, "internal", true, "details", List.of(Map.of("raw", "Status changed")))));
	}

	@Test
	void aWorkPackageListsItsInternalCommentsLikeItsAttachments() {
		AnsweringConnection connection = new AnsweringConnection();
		connection.answers.put("/work_packages/12", Map.of("id", 12, "subject", "Crash"));
		connection.answers.put("/work_packages/12/attachments",
				collection(List.of(Map.of("id", 34, "fileName", "relay.log", "contentType", "text/plain"))));
		connection.answers.put("/work_packages/12/activities", collection(List.of(activity(56, "Public note", false),
				activity(57, "Internal note", true), activity(59, " ", true))));

		List<OpenProjectNativePositionObject> children = new OpenProjectRemoteTree(connection).children("gebo-core",
				OpenProjectPathNodeType.WORK_PACKAGE, "12");

		assertEquals(List.of(OpenProjectPathNodeType.WORK_PACKAGE_CONTENT, OpenProjectPathNodeType.ATTACHMENT,
				OpenProjectPathNodeType.INTERNAL_COMMENT), children.stream().map(c -> c.getType()).toList());
		assertEquals("57", children.get(2).getId());
		assertEquals("SECTION:WORK_PACKAGES|WORK_PACKAGE:12|INTERNAL_COMMENT:57", OpenProjectNavigationUtil
				.toPathInfo("SECTION:WORK_PACKAGES|WORK_PACKAGE:12", children.get(2)).absolutePath);
	}

	@Test
	void anInternalCommentIsStreamedAgainFromItsMetas() throws Exception {
		AnsweringConnection connection = new AnsweringConnection();
		connection.answers.put("/activities/57", activity(57, "The customer is on the old contract.", true));
		connection.answers.put("/users/6", Map.of("id", 6, "name", "Support Lead"));
		OpenProjectResourceReference reference = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(OpenProjectNodes.internalComment(BASE, "gebo-core", "12",
						activity(57, "The customer is on the old contract.", true)).getResourceReferenceMetaInfos());

		String text;
		try (InputStream is = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl.open(reference,
				new OpenProjectRemoteTree(connection))) {
			text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
		}

		assertTrue(text.startsWith("# Internal comment 57 on work package #12"), text);
		assertTrue(text.contains("- Author: Support Lead"), text);
		assertTrue(text.contains("The customer is on the old contract."), text);
		assertTrue(connection.calls.contains("GET /activities/57"), connection.calls.toString());
	}

	@Test
	void aPathStepOfAnInternalCommentIsReadByItsActivity() {
		AnsweringConnection connection = new AnsweringConnection();
		connection.answers.put("/activities/57", activity(57, "Internal note", true));
		OpenProjectNativePositionObject node = new OpenProjectRemoteTree(connection).node("gebo-core",
				OpenProjectNavigationUtil.parseSegment("INTERNAL_COMMENT:57"));
		assertEquals(OpenProjectPathNodeType.INTERNAL_COMMENT, node.getType());
		assertEquals("12",
				node.getResourceReferenceMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID));
	}

	@Test
	void anInternalCommentGoneOrMadePublicIsDeleted() throws Exception {
		GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl service = new GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl(
				null, null);
		AnsweringConnection connection = new AnsweringConnection();
		connection.answers.put("/activities/57", activity(57, "Still internal", true));
		connection.answers.put("/activities/58", activity(58, "Made public", false));
		Map<String, Object> environment = new HashMap<String, Object>();
		environment.put(GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl.OPENPROJECT_TREE,
				new OpenProjectRemoteTree(connection));

		for (String id : List.of("57", "58", "59")) {
			GDocumentReference doc = new GDocumentReference();
			doc.setCode("internal-comment-" + id);
			OpenProjectResourceReference reference = GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl
					.toReference(Map.of(OpenProjectNativePositionObject.OPENPROJECT_OBJECT_TYPE, "INTERNAL_COMMENT",
							OpenProjectNativePositionObject.OPENPROJECT_ACTIVITY_ID, id,
							OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID, "12"));
			Object verified = service.verifyRemoteObjectExistence(null, null, doc, reference, environment);
			if (id.equals("57")) {
				assertNull(verified, "an internal comment still there is left as it is");
			} else {
				// 58 is no longer internal, 59 is not found
				assertSame(doc, verified, "activity " + id);
				assertTrue(doc.getDeleted(), "activity " + id);
			}
		}
	}

	static Map<String, Object> collection(List<Map<String, Object>> elements) {
		return Map.of("_type", "Collection", "total", elements.size(), "_embedded", Map.of("elements", elements));
	}

	/**
	 * A connection answering from memory, recording the calls: a resource it has
	 * no answer for is not found, a collection it has no answer for is empty; the
	 * account is always accepted.
	 */
	static class AnsweringConnection extends OpenProjectConnection {
		final Map<String, Map<String, Object>> answers = new HashMap<String, Map<String, Object>>();
		final List<String> calls = new ArrayList<String>();

		AnsweringConnection() {
			super(null, BASE);
			answers.put("/users/me", Map.of("id", 1, "login", "admin"));
		}

		@Override
		public Map<String, Object> get(String apiPath, Map<String, String> query) {
			calls.add("GET " + apiPath);
			Map<String, Object> answer = answers.get(apiPath);
			if (answer == null) {
				throw HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null);
			}
			return answer;
		}

		@Override
		public List<Map<String, Object>> all(String apiPath, Map<String, String> query) {
			calls.add("ALL " + apiPath);
			return List.of();
		}
	}
}
