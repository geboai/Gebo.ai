/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.redmine.client.invoker.ApiClient;
import ai.gebo.redmine.client.model.IssueJournalsInner;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.RedmineResourceReference;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;

/**
 * A private note of an issue is a document of its own: the issue folder lists
 * it, a stored one streams again from its meta infos alone, the issue content
 * no longer holds its text, and it is deleted once the issue holds it no more as
 * a private note. Redmine is played by a mock server answering the generated
 * client's calls; the client, its mapper, the tree and the service are the real
 * ones.
 */
class RedminePrivateNotesTest {
	static final String BASE = "http://redmine.test";
	static final String PUBLIC_NOTE = "Reproduced on version 6.0.";
	static final String PRIVATE_NOTE = "The customer is Acme, keep it internal.";

	/**
	 * Issue 42 with a public note (101), a private note (102), a private journal
	 * with no text (103), an attachment and a sub-issue.
	 */
	static String issue(boolean secondNoteStillPrivate) {
		return """
				{"issue":{"id":42,"project":{"id":1,"name":"Acme"},"tracker":{"id":1,"name":"Bug"},
				 "status":{"id":1,"name":"New","is_closed":false},"priority":{"id":2,"name":"Normal"},
				 "author":{"id":10,"name":"Ann Admin"},"subject":"Crash","description":"boom",
				 "is_private":false,"done_ratio":0,
				 "created_on":"2026-01-02T10:00:00Z","updated_on":"2026-01-03T10:00:00Z",
				 "attachments":[{"id":77,"filename":"crash.log","filesize":10,"content_type":"text/plain",
				  "description":"","content_url":"http://redmine.test/attachments/download/77/crash.log",
				  "author":{"id":10,"name":"Ann Admin"},"created_on":"2026-01-02T10:00:00Z"}],
				 "children":[{"id":43,"tracker":{"id":1,"name":"Bug"},"subject":"Sub"}],
				 "journals":[
				  {"id":101,"user":{"id":10,"name":"Ann Admin"},"notes":"%s",
				   "created_on":"2026-01-02T11:00:00Z","updated_on":"2026-01-02T11:00:00Z",
				   "private_notes":false,"details":[]},
				  {"id":102,"user":{"id":11,"name":"Bob Builder"},"notes":"%s",
				   "created_on":"2026-01-03T10:00:00Z","updated_on":"2026-01-03T10:00:00Z",
				   "private_notes":%s,"details":[]},
				  {"id":103,"user":{"id":11,"name":"Bob Builder"},"notes":"",
				   "created_on":"2026-01-03T10:00:00Z","updated_on":"2026-01-03T10:00:00Z",
				   "private_notes":true,"details":[{"property":"attr","name":"status_id","old_value":"1","new_value":"2"}]}]}}"""
				.formatted(PUBLIC_NOTE, PRIVATE_NOTE, secondNoteStillPrivate);
	}

	static final String ACCOUNT = """
			{"user":{"id":1,"login":"admin","admin":true,"firstname":"Redmine","lastname":"Admin",
			 "mail":"admin@redmine.test","created_on":"2026-01-01T10:00:00Z","status":1}}""";

	MockRestServiceServer server;
	RedmineConnection connection;
	GRedmineSystem system;

	@BeforeEach
	void setUp() {
		RestClient.Builder builder = ApiClient.buildRestClientBuilder(RedmineApiClientFactory.MAPPER);
		server = MockRestServiceServer.bindTo(builder).ignoreExpectOrder(true).build();
		RestClient restClient = builder.build();
		ApiClient apiClient = new ApiClient(restClient);
		apiClient.setBasePath(BASE);
		connection = new RedmineConnection(apiClient, restClient, BASE);
		system = new GRedmineSystem();
		system.setCode("redmine-system");
		system.setBaseUri(BASE);
	}

	void expectIssue(boolean secondNoteStillPrivate, String include) {
		server.expect(manyTimes(), requestTo(allOf(startsWith(BASE + "/issues/42.json"), containsString(include))))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(issue(secondNoteStillPrivate), MediaType.APPLICATION_JSON));
	}

	GRedmineRemoteVirtualFilesystemConsumingServiceImpl service() {
		return new GRedmineRemoteVirtualFilesystemConsumingServiceImpl(mock(IGDocumentReferenceFactory.class),
				mock(RedmineApiClientFactory.class));
	}

	Map<String, Object> environment() {
		Map<String, Object> environment = new HashMap<String, Object>();
		environment.put(GRedmineRemoteVirtualFilesystemConsumingServiceImpl.REDMINE_TREE,
				new RedmineRemoteTree(connection));
		return environment;
	}

	static String read(InputStream stream) throws Exception {
		try (stream) {
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	@Test
	void theIssueFolderListsEachPrivateNoteWithTextAsADocumentOfItsOwn() {
		server.expect(requestTo(startsWith(BASE + "/my/account.json")))
				.andRespond(withSuccess(ACCOUNT, MediaType.APPLICATION_JSON));
		expectIssue(true, "journals");
		RedmineRemoteTree tree = new RedmineRemoteTree(connection);

		List<RedmineNativePositionObject> children = tree.children("acme", RedminePathNodeType.ISSUE, "42");

		server.verify();
		assertEquals(List.of(RedminePathNodeType.ISSUE_CONTENT, RedminePathNodeType.ATTACHMENT,
				RedminePathNodeType.PRIVATE_NOTE, RedminePathNodeType.ISSUE),
				children.stream().map(RedmineNativePositionObject::getType).toList());
		RedmineNativePositionObject note = children.get(2);
		assertEquals("102", note.getId());
		assertEquals("#42 private note 102.md", note.getName());
		Map<String, Object> metas = note.getResourceReferenceMetaInfos();
		assertEquals("PRIVATE_NOTE", metas.get(RedmineNativePositionObject.REDMINE_OBJECT_TYPE));
		assertEquals("acme", metas.get(RedmineNativePositionObject.REDMINE_PROJECT_ID));
		assertEquals("42", metas.get(RedmineNativePositionObject.REDMINE_ISSUE_ID));
		assertEquals("102", metas.get(RedmineNativePositionObject.REDMINE_JOURNAL_ID));
		assertEquals("true", metas.get(RedmineNativePositionObject.REDMINE_PRIVATE_NOTE));
		assertEquals("11", metas.get(RedmineNativePositionObject.REDMINE_NOTE_AUTHOR_ID));
		assertNotNull(note.getResourceModificationTime());
		// browsing shows it as the ingestion decodes it
		PathInfo path = RedmineNavigationUtil.toPathInfo("SECTION:ISSUES|ISSUE:42", note);
		assertEquals("SECTION:ISSUES|ISSUE:42|PRIVATE_NOTE:102", path.absolutePath);
		assertFalse(path.folder);
		assertEquals(PathInfoMetaType.WEB_PAGE, path.metaType);
	}

	@Test
	void aPrivateNoteStepResolvesUnderItsIssueOnly() {
		expectIssue(true, "journals");
		RedmineRemoteTree tree = new RedmineRemoteTree(connection);
		RedminePathComponent issue = new RedminePathComponent(RedminePathNodeType.ISSUE, "42");

		RedmineNativePositionObject note = tree.node("acme", issue,
				new RedminePathComponent(RedminePathNodeType.PRIVATE_NOTE, "102"));

		assertNotNull(note);
		assertEquals(RedminePathNodeType.PRIVATE_NOTE, note.getType());
		assertEquals("private-note-102", note.getCode());
		// a public note or an empty private journal is no private note document
		assertNull(tree.node("acme", issue, new RedminePathComponent(RedminePathNodeType.PRIVATE_NOTE, "101")));
		assertNull(tree.node("acme", issue, new RedminePathComponent(RedminePathNodeType.PRIVATE_NOTE, "103")));
		assertThrows(IllegalArgumentException.class,
				() -> tree.node("acme", new RedminePathComponent(RedminePathNodeType.PRIVATE_NOTE, "102")));
	}

	@Test
	void aStoredPrivateNoteStreamsAloneAndTheIssueContentNoLongerHoldsIt() throws Exception {
		expectIssue(true, "journals");
		GRedmineRemoteVirtualFilesystemConsumingServiceImpl service = service();
		RedmineNativePositionObject note = RedmineNodes.privateNote(BASE, "acme", 42,
				new IssueJournalsInner().id(102).notes(PRIVATE_NOTE).privateNotes(true));
		RedmineResourceReference noteReference = GRedmineRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(note.getResourceReferenceMetaInfos());
		RedmineResourceReference contentReference = GRedmineRemoteVirtualFilesystemConsumingServiceImpl
				.toReference(RedmineNodes.issueContent(BASE, "acme", 42, "Crash", null).getResourceReferenceMetaInfos());

		String noteText = read(service.streamResource(system, null, noteReference, environment()));
		String contentText = read(service.streamResource(system, null, contentReference, environment()));

		assertTrue(noteText.startsWith("# Private note on Bug #42: Crash"), noteText);
		assertTrue(noteText.contains("- Author: Bob Builder"), noteText);
		assertTrue(noteText.contains("- Created on: 2026-01-03T10:00:00Z"), noteText);
		assertTrue(noteText.contains(PRIVATE_NOTE), noteText);
		assertFalse(noteText.contains(PUBLIC_NOTE), noteText);
		assertTrue(contentText.contains(PUBLIC_NOTE), contentText);
		assertFalse(contentText.contains(PRIVATE_NOTE), contentText);
		assertFalse(contentText.contains("Bob Builder"), contentText);
	}

	@Test
	void aPrivateNoteMadePublicIsNoLongerStreamedAndItsDocumentIsDeleted() throws Exception {
		expectIssue(false, "journals");
		GRedmineRemoteVirtualFilesystemConsumingServiceImpl service = service();
		GDocumentReference doc = new GDocumentReference();
		doc.setCode("kb/redmine/private-note-102");
		Map<String, Object> metas = new HashMap<String, Object>();
		metas.put(RedmineNativePositionObject.REDMINE_OBJECT_TYPE, "PRIVATE_NOTE");
		metas.put(RedmineNativePositionObject.REDMINE_PROJECT_ID, "acme");
		metas.put(RedmineNativePositionObject.REDMINE_ISSUE_ID, "42");
		metas.put(RedmineNativePositionObject.REDMINE_JOURNAL_ID, "102");
		doc.setCustomMetaInfos(metas);
		RedmineResourceReference reference = service.getResourceHandle(system, null, doc, environment());

		assertThrows(GeboContentHandlerSystemException.class,
				() -> service.streamResource(system, null, reference, environment()));
		assertSame(doc, service.verifyRemoteObjectExistence(system, null, doc, reference, environment()));
		assertTrue(doc.getDeleted());
	}

	@Test
	void aPrivateNoteStillPrivateIsKept() throws Exception {
		expectIssue(true, "journals");
		GRedmineRemoteVirtualFilesystemConsumingServiceImpl service = service();
		GDocumentReference doc = new GDocumentReference();
		doc.setCode("kb/redmine/private-note-102");
		doc.setCustomMetaInfos(new HashMap<String, Object>(Map.of(RedmineNativePositionObject.REDMINE_OBJECT_TYPE,
				"PRIVATE_NOTE", RedmineNativePositionObject.REDMINE_ISSUE_ID, "42",
				RedmineNativePositionObject.REDMINE_JOURNAL_ID, "102")));
		RedmineResourceReference reference = service.getResourceHandle(system, null, doc, environment());

		assertNull(service.verifyRemoteObjectExistence(system, null, doc, reference, environment()));
		assertFalse(doc.getDeleted());
		// without its journal id nothing is known: left as it is, Redmine not asked
		reference.journalId = null;
		assertNull(service.verifyRemoteObjectExistence(system, null, doc, reference, environment()));
		assertFalse(doc.getDeleted());
	}
}
