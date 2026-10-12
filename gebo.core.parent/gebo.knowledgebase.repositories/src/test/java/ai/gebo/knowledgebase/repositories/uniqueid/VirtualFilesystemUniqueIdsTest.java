/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.repositories.uniqueid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import ai.gebo.architecture.persistence.IGMongoSequenceService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;

/**
 * Pins the uniqueIds of the document references and virtual folders: kept across
 * synchronizations, new only for objects never saved, one sequence for both kinds,
 * the objects saved before numbered once at startup.
 */
class VirtualFilesystemUniqueIdsTest {

	private MongoOperations mongo;
	private IGMongoSequenceService sequence;
	private VirtualFilesystemUniqueIds uniqueIds;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	@BeforeEach
	void setUp() {
		mongo = mock(MongoOperations.class);
		sequence = mock(IGMongoSequenceService.class);
		when(sequence.nextSequence(VirtualFilesystemUniqueIds.UNIQUE_ID_SEQUENCE)).thenReturn(41L, 42L, 43L);
		uniqueIds = new VirtualFilesystemUniqueIds(provider(mongo), provider(sequence));
	}

	private static GDocumentReference document(String code) {
		GDocumentReference document = new GDocumentReference();
		document.setCode(code);
		return document;
	}

	@Test
	void aSynchronizedDocumentKeepsItsStoredUniqueId() {
		GDocumentReference stored = document("doc-1");
		stored.setUniqueId(7L);
		when(mongo.findOne(any(Query.class), eq(GDocumentReference.class))).thenReturn(stored);
		GDocumentReference rebuilt = document("doc-1");

		assertEquals(7L, uniqueIds.ensureUniqueId(rebuilt));
		assertEquals(7L, rebuilt.getUniqueId());
		verify(sequence, never()).nextSequence(any());
	}

	@Test
	void aNewObjectIsNumberedFromTheSharedSequence() {
		GDocumentReference document = document("new-doc");
		GVirtualFolder folder = new GVirtualFolder();
		folder.setCode("new-folder");

		assertEquals(41L, uniqueIds.ensureUniqueId(document));
		assertEquals(42L, uniqueIds.ensureUniqueId(folder));
		assertEquals(42L, folder.getUniqueId());
	}

	@Test
	void aKnownUniqueIdAndOtherKindsAreLeftAsTheyAre() {
		GDocumentReference document = document("doc");
		document.setUniqueId(5L);
		GSoftwareArtifact artifact = new GSoftwareArtifact();
		artifact.setCode("artifact");

		assertEquals(5L, uniqueIds.ensureUniqueId(document));
		assertNull(uniqueIds.ensureUniqueId(artifact));
		assertNull(artifact.getUniqueId());
		verify(mongo, never()).findOne(any(Query.class), any());
		verify(sequence, never()).nextSequence(any());
	}

	@Test
	void theSaveCallbackNumbersOnlyWhatHasNoUniqueId() {
		VirtualFilesystemUniqueIdCallback callback = new VirtualFilesystemUniqueIdCallback(provider(uniqueIds));
		GDocumentReference document = document("doc");

		assertSame(document, callback.onBeforeConvert(document, "gDocumentReference"));
		assertEquals(41L, document.getUniqueId());
		callback.onBeforeConvert(document, "gDocumentReference");
		verify(sequence, times(1)).nextSequence(any());
	}

	@Test
	void theDocumentUniqueIdIsReadByItsCode() {
		GDocumentReference stored = document("doc-9");
		stored.setUniqueId(9L);
		when(mongo.findOne(any(Query.class), eq(GDocumentReference.class))).thenReturn(stored);

		assertEquals(9L, uniqueIds.documentUniqueId("doc-9"));
		assertNull(uniqueIds.documentUniqueId(" "));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void theBackfillNumbersOnlyObjectsStillWithoutUniqueId() {
		VirtualFilesystemUniqueIdBackfill backfill = new VirtualFilesystemUniqueIdBackfill(mongo, sequence);
		when(mongo.stream(any(Query.class), eq(GDocumentReference.class)))
				.thenReturn((java.util.stream.Stream) List.of(document("a"), document("b")).stream());
		UpdateResult numbered = mock(UpdateResult.class);
		when(numbered.getModifiedCount()).thenReturn(1L);
		UpdateResult alreadyNumbered = mock(UpdateResult.class);
		when(alreadyNumbered.getModifiedCount()).thenReturn(0L);
		when(mongo.updateFirst(any(Query.class), any(Update.class), eq(GDocumentReference.class)))
				.thenReturn(numbered, alreadyNumbered);

		assertEquals(1L, backfill.backfill(GDocumentReference.class));

		ArgumentCaptor<Query> queries = ArgumentCaptor.forClass(Query.class);
		verify(mongo, times(2)).updateFirst(queries.capture(), any(Update.class), eq(GDocumentReference.class));
		// numbered only while still without a uniqueId: another node may have done it
		String criteria = queries.getValue().getQueryObject().toJson();
		assertEquals(true, criteria.contains("\"uniqueId\": null"), criteria);
	}
}
