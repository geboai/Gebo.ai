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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import tools.jackson.core.JacksonException;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint.TestEndpointType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.vectorstores.GAccountingExtendedVectorStoreAdapter;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreConfigurationProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.ragsystem.vectorstores.local.GeboLocalVectorStore;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * Full architecture test of the single-dependency installation: the monolith
 * boots with MongoDB as its ONLY companion service and does real retrieval
 * augmented generation through the embedded vector store.
 *
 * <h2>What this proves, and why it is the test that was needed</h2>
 *
 * The original plan for a minimal Windows installation was to let MongoDB hold
 * the vectors as well, since MongoDB is the one dependency with a Windows
 * installer. That is not possible: the {@code $vectorSearch} stage Spring AI's
 * MongoDB store issues is served by the separate {@code mongot} binary, which
 * MongoDB publishes for Linux only - "Native Windows mongot binaries are not
 * available" - and which additionally requires a replica set, while every
 * Gebo.ai compose file runs a standalone {@code mongod}. Pointing
 * {@code ai.gebo.vectorstore.use} at MONGO on such a deployment produces a store
 * that accepts writes and cannot answer a single query.
 *
 * So the dependency is removed rather than moved: MongoDB keeps the application
 * state it already keeps, and {@link VectorStoreProduct#LOCAL} - Spring AI's own
 * SimpleVectorStore, persisted as JSON under the work directory - keeps the
 * vectors in process. The result is an OSS installation whose only external
 * service is MongoDB.
 *
 * Every assertion below is about that claim:
 * <ol>
 * <li>the vendor really is selected through the ordinary configuration switch,
 * so the architecture stayed vendor-neutral;</li>
 * <li>the data really is a local file under the work directory, i.e. no port and
 * no service, and it survives being written out;</li>
 * <li>the graph and full text stacks are genuinely ABSENT, not merely idle;</li>
 * <li>a real ingestion through the real workflow lands real vectors, and
 * retrieval - plain, filtered by knowledge base, and filtered by document -
 * really answers. That last point is what a write-only probe would have
 * missed.</li>
 * </ol>
 */
public class MongoOnlyEmbeddedVectorStoreIntegrationTest extends AbstractMongoOnlyBaseIntegrationTest {

	/** Used to read back the vendor the platform resolved at runtime. */
	@Autowired
	IGVectorStoreConfigurationProvider vectorStoreConfigurationProvider;

	/** Used to assert that the excluded stacks registered no bean at all. */
	@Autowired
	ApplicationContext applicationContext;

	/** How long the ingestion is given before the test gives up. */
	private static final int MAX_WORKFLOW_CYCLES = 30;

	/** Pause between two polls of the ingestion job. */
	private static final long WORKFLOW_POLL_MILLIS = 10000L;

	/**
	 * Consecutive identical counts that mean the vector store stopped growing.
	 *
	 * The workflow reporting {@code isFinished()} is not the moment the store is
	 * complete: documents reach it from the batch aggregator threads. The same
	 * caveat the shared helper documents for the in-memory test store applies here.
	 */
	private static final int SETTLE_STABLE_OBSERVATIONS = 3;

	/** Pause between two polls of the vector store size. */
	private static final long SETTLE_POLL_MILLIS = 2000L;

	/** Polls allowed while waiting for the store to settle. */
	private static final int SETTLE_MAX_OBSERVATIONS = 60;

	/**
	 * Resolves the embedded store behind the default test embedding model.
	 *
	 * Going through the runtime DAO rather than building a store directly is the
	 * point: it is the instance the ingestion pipeline itself writes into.
	 *
	 * @return the live embedded vector store
	 */
	private GeboLocalVectorStore getEmbeddedVectorStore() {
		IGConfigurableEmbeddingModel embeddingModel = embeddingModelRuntimeDao
				.findByCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		assertNotNull(embeddingModel, "The default test embedding model must be configured");
		VectorStore vectorStore = embeddingModel.getVectorStore();
		assertTrue(vectorStore instanceof GAccountingExtendedVectorStoreAdapter,
				"The platform always wraps a store into the accounting adapter, found: "
						+ (vectorStore == null ? "null" : vectorStore.getClass().getName()));
		Object wrapped = ((GAccountingExtendedVectorStoreAdapter) vectorStore).getWrapped();
		assertTrue(wrapped instanceof GeboLocalVectorStore,
				"ai.gebo.vectorstore.use=LOCAL must resolve to the embedded store, found: "
						+ (wrapped == null ? "null" : wrapped.getClass().getName()));
		return (GeboLocalVectorStore) wrapped;
	}

	/**
	 * The vendor switch, the resolved product and the on-disk placement.
	 *
	 * @throws Exception if the configuration cannot be read
	 */
	@Test
	public void testEmbeddedVectorStoreIsSelectedAndLocal() throws Exception {
		VectorStoreRuntimeConfiguration runtimeConfiguration = vectorStoreConfigurationProvider.get();
		assertEquals(VectorStoreProduct.LOCAL, runtimeConfiguration.getProduct(),
				"The runtime configuration must report the product named by ai.gebo.vectorstore.use");

		GeboLocalVectorStore store = getEmbeddedVectorStore();
		Path storeFile = store.getStoreFile();
		assertNotNull(storeFile, "The embedded store must expose its data file");
		assertTrue(Files.isDirectory(storeFile.getParent()),
				"The directory holding the data file must exist on disk: " + storeFile.getParent());
		// The store was given no explicit directory, so it must have placed itself
		// under GEBO_WORK_DIRECTORY - the volume a deployment already backs up.
		assertTrue(storeFile.toAbsolutePath().normalize().startsWith(WORK.toAbsolutePath().normalize()),
				"With no configured directory the data file must live under GEBO_WORK_DIRECTORY (" + WORK
						+ "), found: " + storeFile);
		LOGGER.info("Embedded vector store file: " + storeFile.toAbsolutePath());
	}

	/**
	 * The excluded services must have contributed no bean.
	 *
	 * Asserting on beans rather than on connectivity is deliberate: a disabled
	 * flag that still registered the beans would fail at the first query instead
	 * of at startup, which is exactly the sort of half-installed state this
	 * perimeter is meant to rule out.
	 */
	@Test
	public void testGraphAndFullTextStacksAreAbsent() {
		assertNoBeanOfType("ai.gebo.architecture.fulltext.service.IGFullTextSearchService",
				"OpenSearch is not part of this installation");
		assertNoBeanOfType("ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService",
				"Neo4j / GraphRAG is not part of this installation");
		// The graph REPOSITORIES follow the same switch, and their absence is what
		// actually decides whether an ingestion can run: IGPersistentObjectManager
		// resolves a repository per entity type, so a graph bean surviving without
		// its repository is the failure mode this perimeter has to exclude.
		assertNoBeanOfType(
				"ai.gebo.architecture.graphrag.extraction.repositories.GraphRagExtractionConfigRepository",
				"No GraphRAG repository may be registered with the graph stack switched off");

		// Deliberately NOT asserted: org.springframework.data.neo4j.core.Neo4jTemplate.
		// Spring Boot's own Neo4jDataAutoConfiguration registers a template and a
		// Driver from the mere presence of the driver on the classpath, and
		// ai.gebo.neo4j.enabled never claimed to suppress it. That bean needs no
		// reachable server - the driver connects lazily - and the proof is this very
		// context: it started, and no Neo4j is running anywhere in this test.
	}

	/**
	 * Fails when the context carries a bean of the named type.
	 *
	 * The type is looked up by name because these classes are only on the
	 * classpath, never meant to be referenced by a test of the reduced perimeter -
	 * and a type that cannot even be loaded trivially satisfies the assertion.
	 *
	 * @param className fully qualified type that must have no bean
	 * @param why       message explaining the expectation
	 */
	private void assertNoBeanOfType(String className, String why) {
		Class<?> type = null;
		try {
			type = Class.forName(className);
		} catch (ClassNotFoundException notOnClasspath) {
			LOGGER.info("Type " + className + " is not even on the classpath - " + why);
			return;
		}
		String[] beans = applicationContext.getBeanNamesForType(type);
		assertEquals(0, beans.length,
				why + ", but the context registered: " + String.join(", ", beans) + " for " + className);
	}

	/**
	 * The end-to-end run: ingest the bundled corpus through the real workflow, then
	 * retrieve from the embedded store.
	 *
	 * @throws Exception if the ingestion or the retrieval fails
	 */
	@Test
	public void testFullIngestionAndRetrievalOnMongoOnlyInstallation() throws Exception {
		GeboLocalVectorStore store = getEmbeddedVectorStore();
		assertEquals(0, store.fragmentCount(), "The embedded store must start empty for this test");

		TestProjectEndpoint endpoint = createAndPersist("mongo only embedded vector store",
				TestProjectEndpoint.class);
		endpoint.setOpenZips(true);
		endpoint.setTestType(TestEndpointType.PATH_CONTENTS);
		endpoint = persistentObjectManager.update(endpoint);
		GProject project = getProject(endpoint);

		GVirtualFolder folder = new GVirtualFolder();
		folder.setParentProjectCode(endpoint.getParentProjectCode());
		folder.setRootKnowledgebaseCode(project.getRootKnowledgeBaseCode());
		folder.setCode("mongoOnlyEmbeddedVectorStore");
		folder.setDescription("Corpus of the single-dependency installation test");
		for (String bundled : ALL_DATA_FILES) {
			String extension = bundled.substring(bundled.lastIndexOf("."));
			Path extracted = extractResource(bundled, extension);
			endpoint.getTestFilesystemPaths().add(extracted.toString());
		}
		endpoint.getTestVirtualFolders().add(folder);
		endpoint = persistentObjectManager.update(endpoint);

		runIngestionAndWait(endpoint);

		int vectorised = awaitSettledIndex(store);
		assertTrue(vectorised > 0,
				"The ingestion must have written fragments into the embedded store, found " + vectorised);
		LOGGER.info("The embedded store holds " + vectorised + " fragments after the ingestion");

		// Every bundled file is DISCOVERED, so Mongo holds a reference for all of
		// them - including the malformed one, which is referenced but yields no
		// chunk. That asymmetry is the point of the next assertion.
		assertEquals(ALL_DATA_FILES.size(), getNRDocuments(endpoint),
				"Mongo must hold one document reference per bundled file");

		assertRetrievalWorks(store, project.getRootKnowledgeBaseCode());
		assertCorpusIsPersisted(store, vectorised);

		cleanPersistent(endpoint);
	}

	/**
	 * Asserts the corpus reaches the disk, which is what makes a restart of a
	 * single-dependency installation survivable.
	 *
	 * Saving is debounced by default - the store serialises its whole content, so
	 * writing after every ingestion batch would be quadratic - therefore the flush
	 * is requested explicitly here instead of being waited for. The same flush runs
	 * on close and from the registry's shutdown hook.
	 *
	 * @param store      the embedded store
	 * @param fragments  how many fragments the store reported holding
	 * @throws IOException if the data file cannot be inspected
	 */
	private void assertCorpusIsPersisted(GeboLocalVectorStore store, int fragments) throws IOException {
		store.flush();
		Path storeFile = store.getStoreFile();
		assertTrue(Files.exists(storeFile), "The embedded store must have written its data file: " + storeFile);
		long size = Files.size(storeFile);
		assertTrue(size > 0, "The persisted data file must not be empty: " + storeFile);
		LOGGER.info("Persisted " + fragments + " fragments as " + size + " bytes at " + storeFile);
	}

	/**
	 * Runs the standard ingestion workflow on the endpoint and waits for it.
	 *
	 * @param endpoint the endpoint to ingest
	 * @throws GeboJobServiceException  if the job cannot be started
	 * @throws GeboPersistenceException on persistence errors
	 * @throws JacksonException         if the summary cannot be rendered
	 * @throws InterruptedException     if the wait is interrupted
	 */
	private void runIngestionAndWait(TestProjectEndpoint endpoint)
			throws GeboJobServiceException, GeboPersistenceException, JacksonException, InterruptedException {
		GJobStatus jobStatus = ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
		JobSummary summary = workflowStatsService.getJobSummary(jobStatus.getCode());
		int cycles = 0;
		do {
			Thread.sleep(WORKFLOW_POLL_MILLIS);
			summary = workflowStatsService.getJobSummary(jobStatus.getCode());
			cycles++;
			LOGGER.info("Ingestion cycle " + cycles);
			printSummary(summary);
		} while (!(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished())
				&& cycles < MAX_WORKFLOW_CYCLES);
		summary = workflowStatsService.getJobSummary(jobStatus.getCode());
		LOGGER.info("Summary=" + mapper.writeValueAsString(summary));
		assertTrue(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished(),
				"The ingestion workflow must have finished");
		// isHasErrors() is deliberately NOT asserted to be false: the shared corpus
		// carries wrong-file-format.pdf on purpose, so a healthy run DOES report the
		// reader error for it ("PDF header signature not found"). What matters is
		// that the workflow finishes and that the readable files land, which the
		// caller asserts on the index and on the document references.
	}

	/**
	 * Waits until the embedded store stops growing.
	 *
	 * @param store the store to observe
	 * @return the fragment count once it settled
	 * @throws InterruptedException if the wait is interrupted
	 */
	private int awaitSettledIndex(GeboLocalVectorStore store) throws InterruptedException {
		int previous = -1;
		int stable = 0;
		for (int observation = 0; observation < SETTLE_MAX_OBSERVATIONS; observation++) {
			int current = store.fragmentCount();
			if (current == previous && current > 0) {
				stable++;
				if (stable >= SETTLE_STABLE_OBSERVATIONS) {
					return current;
				}
			} else {
				stable = 0;
			}
			previous = current;
			Thread.sleep(SETTLE_POLL_MILLIS);
		}
		LOGGER.warn("The embedded index never settled; asserting on the last count of " + previous);
		return previous;
	}

	/**
	 * Asserts that retrieval really answers out of the embedded store.
	 *
	 * The document codes are taken from the Mongo document references rather than
	 * from a search result, so the check covers the WHOLE corpus instead of
	 * whatever a single top-k happened to sample.
	 *
	 * @param store             the embedded store
	 * @param knowledgeBaseCode the knowledge base the corpus was ingested into
	 * @throws GeboPersistenceException if the document references cannot be read
	 */
	private void assertRetrievalWorks(GeboLocalVectorStore store, String knowledgeBaseCode)
			throws GeboPersistenceException {
		// 1. a plain semantic search must return fragments carrying the metadata the
		// pipeline is expected to have enriched them with.
		List<Document> hits = store
				.similaritySearch(SearchRequest.builder().query("document content").topK(10).build());
		assertFalse(hits.isEmpty(), "A semantic search over the embedded store must return fragments");
		for (Document hit : hits) {
			assertNotNull(hit.getScore(), "Every hit must carry a similarity score");
			assertNotNull(hit.getText(), "Every hit must carry back its text");
			assertNotNull(hit.getMetadata().get(DocumentMetaInfos.CONTENT_CODE),
					"Every stored fragment must keep its CONTENT_CODE metadata");
			assertEquals(knowledgeBaseCode, hit.getMetadata().get(DocumentMetaInfos.KNOWLEDGEBASE_CODE),
					"Every stored fragment must keep the knowledge base it belongs to");
		}

		// 2. the filter the RAG layer actually issues - IN over the knowledge base -
		// must select the corpus, and must exclude anything else.
		FilterExpressionBuilder matching = new FilterExpressionBuilder();
		List<Document> inKnowledgeBase = store.similaritySearch(SearchRequest.builder().query("document content")
				.topK(50)
				.filterExpression(matching.in(DocumentMetaInfos.KNOWLEDGEBASE_CODE, knowledgeBaseCode).build())
				.build());
		assertFalse(inKnowledgeBase.isEmpty(),
				"Filtering on the knowledge base of the ingested corpus must still return fragments");

		FilterExpressionBuilder notMatching = new FilterExpressionBuilder();
		List<Document> inOtherKnowledgeBase = store.similaritySearch(SearchRequest.builder()
				.query("document content").topK(50)
				.filterExpression(notMatching.in(DocumentMetaInfos.KNOWLEDGEBASE_CODE, "NO-SUCH-KB").build())
				.build());
		assertTrue(inOtherKnowledgeBase.isEmpty(),
				"Filtering on a knowledge base that was never ingested must return nothing, got "
						+ inOtherKnowledgeBase.size());

		// 3. every document Mongo knows about must be reachable through a
		// CONTENT_CODE filter - that is how the platform narrows a search down to a
		// single document - except the malformed file, which produced no chunk at all.
		List<GDocumentReference> references = persistentObjectManager.findAll(GDocumentReference.class);
		assertEquals(ALL_DATA_FILES.size(), references.size(),
				"The corpus must be fully referenced before retrieval is checked");
		List<String> retrievable = new ArrayList<>();
		List<String> empty = new ArrayList<>();
		for (GDocumentReference reference : references) {
			FilterExpressionBuilder perDocument = new FilterExpressionBuilder();
			List<Document> ofDocument = store.similaritySearch(SearchRequest.builder().query("document content")
					.topK(20)
					.filterExpression(
							perDocument.eq(DocumentMetaInfos.CONTENT_CODE, reference.getCode()).build())
					.build());
			if (ofDocument.isEmpty()) {
				empty.add(reference.getName());
				continue;
			}
			retrievable.add(reference.getName());
			for (Document fragment : ofDocument) {
				assertEquals(reference.getCode(), fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE),
						"A CONTENT_CODE filter must never return a fragment of another document");
			}
		}
		LOGGER.info("Retrievable documents: " + retrievable);
		LOGGER.info("Documents without fragments: " + empty);
		assertEquals(ALL_DATA_FILES.size() - 1, retrievable.size(),
				"Every readable bundled file must be retrievable by CONTENT_CODE from the embedded store; "
						+ "retrievable=" + retrievable + " without fragments=" + empty);
		assertEquals(1, empty.size(),
				"Only the deliberately malformed bundled file may end up without fragments, found: " + empty);
	}
}
