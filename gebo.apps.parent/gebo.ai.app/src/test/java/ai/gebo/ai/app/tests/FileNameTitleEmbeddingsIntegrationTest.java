/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint.TestEndpointType;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.core.impl.GCoreMessagesEmitterImpl;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorizedFragmentMetadata;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentChunksReader;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentIdentitySearch;
import ai.gebo.llms.agent.standardtools.KnowledgeBaseDocumentIdentitySearch.FoundDocument;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.EmbedType;
import ai.gebo.ragsystem.content.vectorizator.impl.DocumentIdentityVectors;
import ai.gebo.ragsystem.content.vectorizator.impl.FileNameTitleVectorsBackfill;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * Certifies the file name and title vectors of the documents: written beside the
 * vectors of the contents with their very metadata but for what they embed, never
 * found by the searches of contents, finding their document by its file name or
 * title, given to the documents vectorized before they existed by the backfill, and
 * deleted with their document.
 */
public class FileNameTitleEmbeddingsIntegrationTest extends AbstractMongoOnlyBaseIntegrationTest {

	private static final int MAX_WORKFLOW_CYCLES = 30;
	private static final long WORKFLOW_POLL_MILLIS = 10000L;
	private static final long SETTLE_POLL_MILLIS = 2000L;
	private static final int SETTLE_MAX_OBSERVATIONS = 60;
	/** The metadata a file name or title vector does not copy from the contents': what tells a part of them. */
	private static final Set<String> NOT_COPIED = Set.of(DocumentMetaInfos.EMBED_TYPE,
			DocumentMetaInfos.GEBO_CHUNK_POSITION, DocumentMetaInfos.CONTENT_PAGE, DocumentMetaInfos.GEBO_TOKEN_LENGTH,
			DocumentMetaInfos.GEBO_BYTES_LENGTH);

	@Autowired
	KnowledgeBaseDocumentIdentitySearch identitySearch;
	@Autowired
	FileNameTitleVectorsBackfill backfill;
	@Autowired
	KnowledgeBaseDocumentChunksReader chunksReader;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	GCoreMessagesEmitterImpl coreEmitter;

	private IGExtendedVectorStore store() {
		final IGConfigurableEmbeddingModel model = embeddingModelRuntimeDao.findByCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		assertNotNull(model, "The default test embedding model must be configured");
		return model.getVectorStore();
	}

	private Map<String, Object> metadataOf(IGExtendedVectorStore store, String id) throws Exception {
		final List<VectorizedFragmentMetadata> read = store.readMetadataByIds(List.of(id));
		return read.isEmpty() ? null : read.get(0).getMetadata();
	}

	@Test
	public void theFileNameAndTitleVectorsOfTheDocuments() throws Exception {
		final IGExtendedVectorStore store = store();
		TestProjectEndpoint endpoint = createAndPersist("file name and title vectors", TestProjectEndpoint.class);
		endpoint.setOpenZips(true);
		endpoint.setTestType(TestEndpointType.PATH_CONTENTS);
		endpoint = persistentObjectManager.update(endpoint);
		final GProject project = getProject(endpoint);
		final String knowledgeBase = project.getRootKnowledgeBaseCode();
		final GVirtualFolder folder = new GVirtualFolder();
		folder.setParentProjectCode(endpoint.getParentProjectCode());
		folder.setRootKnowledgebaseCode(knowledgeBase);
		folder.setCode("fileNameTitleVectors");
		folder.setDescription("Corpus of the file name and title vectors test");
		for (String bundled : ALL_DATA_FILES) {
			final Path extracted = extractResource(bundled, bundled.substring(bundled.lastIndexOf(".")));
			endpoint.getTestFilesystemPaths().add(extracted.toString());
		}
		endpoint.getTestVirtualFolders().add(folder);
		endpoint = persistentObjectManager.update(endpoint);

		runIngestionAndWait(endpoint);
		final List<GVectorizedContent> vectorized = awaitVectorized(endpoint);
		assertFalse(vectorized.isEmpty(), "The corpus must have been vectorized");
		final Map<String, GDocumentReference> references = new HashMap<>();
		for (GDocumentReference reference : persistentObjectManager.findAll(GDocumentReference.class)) {
			references.put(reference.getCode(), reference);
		}

		// 1. every document vectorized has one file name vector, a title one when it has
		// a title, with the metadata of its contents' vectors but for what they embed
		int titled = 0;
		final Map<GDocumentReference, String> titles = new HashMap<>();
		final Map<GDocumentReference, String> authors = new HashMap<>();
		for (GVectorizedContent record : vectorized) {
			final String code = record.getId().getDocReferenceCode();
			assertEquals(1, record.getFileNameVectorsId() != null ? record.getFileNameVectorsId().size() : -1,
					"One file name vector for " + code);
			assertNotNull(record.getTitleVectorsId(), "The title vectors are recorded for " + code);
			assertNotNull(record.getAuthorVectorsId(), "The author vectors are recorded for " + code);
			final Map<String, Object> content = metadataOf(store, record.getVectorsId().get(0));
			assertEquals(EmbedType.DOCUMENT.name(), content.get(DocumentMetaInfos.EMBED_TYPE),
					"The contents' vectors are marked as such for " + code);
			final Map<String, Object> fileName = metadataOf(store, record.getFileNameVectorsId().get(0));
			assertEquals(EmbedType.FILE_NAME.name(), fileName.get(DocumentMetaInfos.EMBED_TYPE));
			assertSameMetadata(content, fileName, code);
			if (!record.getTitleVectorsId().isEmpty()) {
				titled++;
				final Map<String, Object> title = metadataOf(store, record.getTitleVectorsId().get(0));
				assertEquals(EmbedType.TITLE.name(), title.get(DocumentMetaInfos.EMBED_TYPE));
				assertSameMetadata(content, title, code);
				titles.put(references.get(code), content.get(DocumentMetaInfos.TITLE).toString());
				// the title's text is kept with the vectors, for the listings
				assertEquals(content.get(DocumentMetaInfos.TITLE).toString(), record.getTitle(),
						"The title of " + code + " is kept");
			} else {
				assertNull(record.getTitle(), "No title kept for " + code);
			}
			// an author vector exactly when the document has an author
			assertEquals(content.containsKey(DocumentMetaInfos.AUTHOR) ? 1 : 0, record.getAuthorVectorsId().size(),
					"The author vectors of " + code);
			if (!record.getAuthorVectorsId().isEmpty()) {
				final Map<String, Object> author = metadataOf(store, record.getAuthorVectorsId().get(0));
				assertEquals(EmbedType.AUTHOR.name(), author.get(DocumentMetaInfos.EMBED_TYPE));
				assertSameMetadata(content, author, code);
				authors.put(references.get(code), content.get(DocumentMetaInfos.AUTHOR).toString());
				assertEquals(content.get(DocumentMetaInfos.AUTHOR).toString(), record.getAuthor(),
						"The author of " + code + " is kept");
			} else {
				assertNull(record.getAuthor(), "No author kept for " + code);
			}
		}
		assertTrue(titled > 0, "Some documents of the corpus have a title");
		// the file name, title and author vectors embed their text alone, not their metadata
		final Map<String, Object> someMetadata = metadataOf(store, vectorized.get(0).getVectorsId().get(0));
		final Document titleVector = DocumentIdentityVectors.vector(someMetadata, EmbedType.TITLE, "The Secret Doctrine");
		assertEquals("The Secret Doctrine", titleVector.getFormattedContent(MetadataMode.EMBED));
		assertEquals("The Secret Doctrine", titleVector.getFormattedContent(MetadataMode.ALL));
		LOGGER.info("Documents with an author: " + authors.values());

		// 2. the searches of contents never find a file name or title vector
		final SemanticSearchMetaDataFilter contents = new SemanticSearchMetaDataFilter();
		contents.setKnowledgeBasesCodes(List.of(knowledgeBase));
		for (GDocumentReference reference : references.values()) {
			final List<Document> found = store.similaritySearch(SearchRequest.builder().query(reference.getName())
					.topK(100).similarityThresholdAll().filterExpression(contents.build()).build());
			assertFalse(found.isEmpty(), "A search of contents finds contents");
			for (Document hit : found) {
				assertEquals(EmbedType.DOCUMENT.name(), hit.getMetadata().get(DocumentMetaInfos.EMBED_TYPE),
						"A search of contents found a " + hit.getMetadata().get(DocumentMetaInfos.EMBED_TYPE)
								+ " vector searching " + reference.getName());
			}
		}

		// 3. a document is found by its file name and by its title
		for (GVectorizedContent record : vectorized) {
			final GDocumentReference reference = references.get(record.getId().getDocReferenceCode());
			final List<FoundDocument> byName = identitySearch.search(reference.getName(), EmbedType.FILE_NAME,
					List.of(knowledgeBase), 3, 0.0);
			assertFalse(byName.isEmpty(), "Found by its file name: " + reference.getName());
			assertEquals(reference.getUniqueId(), byName.get(0).uniqueId(),
					"The best found by its file name is " + reference.getName() + ": " + byName);
			assertEquals(reference.getName(), byName.get(0).matched());
		}
		for (Map.Entry<GDocumentReference, String> titledDocument : titles.entrySet()) {
			final List<FoundDocument> byTitle = identitySearch.search(titledDocument.getValue(), EmbedType.TITLE,
					List.of(knowledgeBase), 10, 0.0);
			assertTrue(byTitle.stream().anyMatch(x -> x.uniqueId().equals(titledDocument.getKey().getUniqueId())),
					"Found by its title " + titledDocument.getValue() + ": " + byTitle);
			assertTrue(byTitle.stream().allMatch(x -> x.matched() != null));
		}
		for (Map.Entry<GDocumentReference, String> authored : authors.entrySet()) {
			final List<FoundDocument> byAuthor = identitySearch.search(authored.getValue(), EmbedType.AUTHOR,
					List.of(knowledgeBase), 10, 0.0);
			assertTrue(byAuthor.stream().anyMatch(x -> x.uniqueId().equals(authored.getKey().getUniqueId())),
					"Found by its author " + authored.getValue() + ": " + byAuthor);
		}

		// 4. a document vectorized before the file name and title vectors existed is
		// given them by the backfill, with the same metadata
		final GVectorizedContent legacy = vectorized.get(0);
		final List<String> legacyIds = new ArrayList<>(legacy.getFileNameVectorsId());
		legacyIds.addAll(legacy.getTitleVectorsId());
		legacyIds.addAll(legacy.getAuthorVectorsId());
		store.delete(legacyIds);
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(legacy.getId())),
				new Update().unset("fileNameVectorsId").unset("titleVectorsId").unset("authorVectorsId"),
				GVectorizedContent.class);
		backfill.backfillAll();
		final GVectorizedContent backfilled = vectorizedContentRepository.findById(legacy.getId()).orElseThrow();
		assertEquals(1, backfilled.getFileNameVectorsId().size(), "The backfill gave the file name vector back");
		assertNotEquals(legacy.getFileNameVectorsId().get(0), backfilled.getFileNameVectorsId().get(0));
		assertEquals(legacy.getTitleVectorsId().size(), backfilled.getTitleVectorsId().size(),
				"The backfill gave the title vector back when there is a title");
		assertEquals(legacy.getAuthorVectorsId().size(), backfilled.getAuthorVectorsId().size(),
				"The backfill gave the author vector back when there is an author");
		assertEquals(legacy.getTitle(), backfilled.getTitle(), "The backfill kept the title's text");
		assertEquals(legacy.getAuthor(), backfilled.getAuthor(), "The backfill kept the author's text");
		final Map<String, Object> legacyContent = metadataOf(store, backfilled.getVectorsId().get(0));
		final Map<String, Object> backfilledName = metadataOf(store, backfilled.getFileNameVectorsId().get(0));
		assertEquals(EmbedType.FILE_NAME.name(), backfilledName.get(DocumentMetaInfos.EMBED_TYPE));
		assertSameMetadata(legacyContent, backfilledName, legacy.getId().getDocReferenceCode());
		for (GVectorizedContent other : vectorized.subList(1, vectorized.size())) {
			assertEquals(other.getFileNameVectorsId(),
					vectorizedContentRepository.findById(other.getId()).orElseThrow().getFileNameVectorsId(),
					"The backfill leaves the documents done alone");
		}

		// 4b. a document vectorized when the title's text was not kept yet is given the
		// texts alone by the backfill, without new vectors; the listings read them
		final GVectorizedContent untexted = vectorized.stream()
				.filter(x -> !x.getId().equals(legacy.getId()) && !x.getTitleVectorsId().isEmpty()).findFirst()
				.orElseThrow(() -> new AssertionError("A titled document besides the legacy one"));
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(untexted.getId())),
				new Update().unset("title").unset("author"), GVectorizedContent.class);
		assertNull(vectorizedContentRepository.findById(untexted.getId()).orElseThrow().getTitle());
		backfill.backfillAll();
		final GVectorizedContent texted = vectorizedContentRepository.findById(untexted.getId()).orElseThrow();
		assertEquals(untexted.getTitle(), texted.getTitle(), "The backfill gave the title's text back");
		assertEquals(untexted.getAuthor(), texted.getAuthor(), "The backfill gave the author's text back");
		assertEquals(untexted.getVectorsId(), texted.getVectorsId(), "The contents' vectors are the same");
		assertEquals(untexted.getTitleVectorsId(), texted.getTitleVectorsId(), "No title vector added");
		assertEquals(untexted.getFileNameVectorsId(), texted.getFileNameVectorsId(), "No file name vector added");
		final String untextedCode = untexted.getId().getDocReferenceCode();
		assertEquals(untexted.getTitle(), chunksReader.identities(List.of(untextedCode)).get(untextedCode).title(),
				"The listings read the title the contents tell");

		// 5. deleting the documents deletes all their vectors
		final List<String> all = new ArrayList<>();
		for (GVectorizedContent record : vectorizedContentRepository.findAll()) {
			if (references.containsKey(record.getId().getDocReferenceCode())) {
				all.addAll(record.allVectorsId());
			}
		}
		// as the knowledge base deletion does: the records, then the message telling
		// the vectorizator to dispose of the knowledge base's vectors
		final GKnowledgeBase deleted = persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase);
		cleanPersistent(endpoint);
		final GDeletedKnowledgeBasePayload deletion = new GDeletedKnowledgeBasePayload();
		deletion.setKnowledgeBase(deleted);
		coreEmitter.sendDeletingPayload(deletion);
		int left = all.size();
		for (int observation = 0; observation < SETTLE_MAX_OBSERVATIONS && left > 0; observation++) {
			Thread.sleep(SETTLE_POLL_MILLIS);
			left = store.readMetadataByIds(all).size();
		}
		assertEquals(0, left, "Deleting the documents deleted their contents', file name and title vectors");
	}

	/** The metadata of a file name or title vector is that of its document's contents. */
	private static void assertSameMetadata(Map<String, Object> content, Map<String, Object> identity, String code) {
		for (Map.Entry<String, Object> entry : content.entrySet()) {
			if (!NOT_COPIED.contains(entry.getKey())) {
				assertEquals(entry.getValue(), identity.get(entry.getKey()),
						"The metadata " + entry.getKey() + " of " + code + " is the contents' one");
			}
		}
		for (String key : identity.keySet()) {
			assertTrue(NOT_COPIED.contains(key) || content.containsKey(key),
					"The metadata " + key + " of " + code + " does not come from the contents");
		}
		assertFalse(identity.containsKey(DocumentMetaInfos.GEBO_CHUNK_POSITION));
	}

	private void runIngestionAndWait(TestProjectEndpoint endpoint) throws Exception {
		final GJobStatus jobStatus = ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
		JobSummary summary = workflowStatsService.getJobSummary(jobStatus.getCode());
		int cycles = 0;
		do {
			Thread.sleep(WORKFLOW_POLL_MILLIS);
			summary = workflowStatsService.getJobSummary(jobStatus.getCode());
			cycles++;
		} while (!(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished())
				&& cycles < MAX_WORKFLOW_CYCLES);
		assertTrue(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished(),
				"The ingestion workflow must have finished");
	}

	/**
	 * The vectorizations of the endpoint's documents with contents, once their count
	 * stops growing: documents reach the vector store after the workflow is finished.
	 */
	private List<GVectorizedContent> awaitVectorized(TestProjectEndpoint endpoint) throws Exception {
		final String project = endpoint.getParentProjectCode();
		List<GVectorizedContent> found = List.of();
		int previous = -1, stable = 0;
		for (int observation = 0; observation < SETTLE_MAX_OBSERVATIONS && stable < 3; observation++) {
			found = vectorizedContentRepository.findByParentProjectCode(project)
					.filter(x -> !Boolean.TRUE.equals(x.getDeleted()) && x.getVectorsId() != null
							&& !x.getVectorsId().isEmpty())
					.toList();
			stable = found.size() == previous && !found.isEmpty() ? stable + 1 : 0;
			previous = found.size();
			Thread.sleep(SETTLE_POLL_MILLIS);
		}
		LOGGER.info("Vectorized documents: " + found.size());
		return found;
	}
}
