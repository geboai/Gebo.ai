/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.complexPhrase.ComplexPhraseQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.KnnFloatVectorQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermInSetQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.Filter.ExpressionType;
import org.springframework.ai.vectorstore.filter.Filter.Operand;
import org.springframework.ai.vectorstore.filter.Filter.Value;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.lucene.model.InternalLuceneServerConfig;
import ai.gebo.architecture.lucene.model.LuceneVectorStoreEntry;
import ai.gebo.architecture.lucene.model.SearchKeywordRequest;
import ai.gebo.architecture.lucene.model.SearchKeywordResults;
import ai.gebo.architecture.lucene.model.SearchKeywordResults.ResultItem;
import ai.gebo.architecture.lucene.services.IGInternalLuceneServer;
import ai.gebo.architecture.lucene.utils.LuceneUtils;
import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.model.DocumentMetaInfos;

/**
 * AI generated comments
 * Implementation of the IGInternalLuceneServer and IGRunnable interfaces, 
 * providing Lucene indexing and search capabilities.
 */
class GInternalLuceneServerImpl implements IGInternalLuceneServer, IGRunnable {
    // Logger for logging system events
    final private static Logger LOGGER = LoggerFactory.getLogger(GInternalLuceneServerImpl.class);
    // Flag to indicate if the server is running
    private boolean running = true;
    // Path for data storage
    private final Path dataPath;
    // Directory for file system-based index storage
    private final NIOFSDirectory directory;
    // Standard analyzer for processing text input
    private final StandardAnalyzer analyzer = new StandardAnalyzer();
    // Container to store document batches
    private final List<List<LuceneVectorStoreEntry>> documentsBatchContainer = new ArrayList();
    // Lists to track deleted reference codes and IDs
    private final List<List<String>> deletedReferenceCodes = new ArrayList<List<String>>();
    private final List<List<String>> deletedIds = new ArrayList<List<String>>();
    // Configuration for the Lucene server
    private final InternalLuceneServerConfig serverConfig;
    // Mutex for synchronizing block caller operations
    private final Object blockCallerMutex = new Object();
    // Counter for blocked callers
    private int blockedCallers = 0;

    /**
     * Constructor to initialize the GInternalLuceneServerImpl with the provided data path
     * and server configuration.
     * 
     * @param dataPath - Path to the data directory
     * @param serverConfig - Configuration settings for the server
     * @throws IOException - Exception thrown if there's an issue with the directory
     */
    GInternalLuceneServerImpl(Path dataPath, InternalLuceneServerConfig serverConfig) throws IOException {
        this.dataPath = dataPath;
        this.directory = new NIOFSDirectory(dataPath);
        this.serverConfig = serverConfig;
    }

    /**
     * Calculates the total number of documents enqueued for processing.
     * 
     * @return - The total size of enqueued documents
     */
    private int enqueuedDocuments() {
        int size = 0;
        for (List v : documentsBatchContainer) {
            size += v.size();
        }
        return size;
    }

    /**
     * Continuously processes documents in the batch container while the server is running.
     * Handles saving, deleting, and notifying blocked callers as needed.
     */
    @Override
    public void run() {
        while (running) {
            final List<LuceneVectorStoreEntry> monovector = new ArrayList<LuceneVectorStoreEntry>();
            final List<String> ids2delete = new ArrayList<String>();
            final List<String> documentRefCodes2Delete = new ArrayList<String>();
            synchronized (documentsBatchContainer) {
                if (documentsBatchContainer.isEmpty()) {
                    try {
                        documentsBatchContainer.wait();
                    } catch (Throwable th) {
                        // running = false;
                        continue;
                    }
                }
                if (!running)
                    return;
                deletedReferenceCodes.forEach(x -> {
                    documentRefCodes2Delete.addAll(x);
                });
                deletedIds.forEach(x -> {
                    ids2delete.addAll(x);
                });
                for (List<LuceneVectorStoreEntry> docs : documentsBatchContainer) {
                    monovector.addAll(docs);
                }
                documentsBatchContainer.clear();
                deletedReferenceCodes.clear();
                deletedIds.clear();
            }

            if (!monovector.isEmpty() || !documentRefCodes2Delete.isEmpty() || !ids2delete.isEmpty()) {
                batchSave(documentRefCodes2Delete, ids2delete, monovector);
            }
            boolean unlockCallers = false;
            synchronized (documentsBatchContainer) {
                int enqueued = enqueuedDocuments();
                if (enqueued < serverConfig.saveQueueMaxLength && blockedCallers > 0) {
                    unlockCallers = true;
                }
            }
            if (unlockCallers) {
                synchronized (blockCallerMutex) {
                    blockCallerMutex.notifyAll();
                }
            }
        }
        try {
            directory.close();
        } catch (Throwable th) {
        }
    }

    /**
     * Executes a batch save operation, handling document addition and deletion
     * based on document reference codes and IDs.
     * 
     * @param documentRefCodes2Delete - List of document reference codes to delete
     * @param ids2delete - List of document IDs to delete
     * @param documents - Documents to be saved
     */
    private void batchSave(List<String> documentRefCodes2Delete, List<String> ids2delete,
            List<LuceneVectorStoreEntry> documents) {
        LOGGER.info("Begin lucene indexing=>" + documents.size() + " documents");
        try {
            List<String> ids = new ArrayList<String>(documents.stream().map(x -> {
                return x.getDocument().getId();
            }).toList());
            ids.addAll(ids2delete);
            List<org.apache.lucene.document.Document> luceneDocs = documents.stream()
                    .map(LuceneUtils::aiToLuceneDocument).toList();

            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            Term terms[] = new Term[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                terms[i] = new Term("id", ids.get(i));
            }
            Term codeTerms[] = new Term[documentRefCodes2Delete.size()];
            for (int i = 0; i < documentRefCodes2Delete.size(); i++) {
                codeTerms[i] = new Term(DocumentMetaInfos.CONTENT_CODE, documentRefCodes2Delete.get(i));
            }
            try (IndexWriter writter = new IndexWriter(directory, indexWriterConfig)) {
                if (codeTerms.length > 0) {
                    long deleted = writter.deleteDocuments(codeTerms);
                    if (deleted > 0) {
                        LOGGER.info("Replacing =>" + deleted + " documents by code");
                    }
                }
                if (terms.length > 0) {
                    long deleted = writter.deleteDocuments(terms);
                    if (deleted > 0) {
                        LOGGER.info("Replacing =>" + deleted + " documents by id");
                    }
                }

                if (!luceneDocs.isEmpty()) {
                    LOGGER.info("Adding =>" + luceneDocs.size() + " documents");
                    writter.addDocuments(luceneDocs);
                }
                writter.commit();
            } finally {
            }
        } catch (Throwable e) {
            final String msg = "Error writing lucene";
            LOGGER.error(msg, e);
        }
        LOGGER.info("End lucene indexing");
    }

    /**
     * Retrieves the data path for the Lucene server.
     * 
     * @return - The path where data is stored
     */
    @Override
    public Path getDataPath() {
        return dataPath;
    }

    /**
     * Saves a list of documents by deferring the save operation to handle
     * batching.
     * 
     * @param documents - List of documents to save
     */
    @Override
    public void save(List<LuceneVectorStoreEntry> documents) {
        deferSave(documents);
    }

    /**
     * Defers the saving of documents to manage batching and notify waiting
     * processes when the threshold is exceeded.
     * 
     * @param documents - List of documents to defer save
     */
    private void deferSave(List<LuceneVectorStoreEntry> documents) {
        int sampledCardinality = 0;
        synchronized (documentsBatchContainer) {
            documentsBatchContainer.add(documents);
            sampledCardinality = enqueuedDocuments();
            documentsBatchContainer.notifyAll();
        }
        if (sampledCardinality >= serverConfig.getSaveQueueMaxLength()) {
            synchronized (blockCallerMutex) {
                try {
                    blockedCallers++;
                    blockCallerMutex.wait();
                    blockedCallers--;
                } catch (Throwable th) {
                }
            }
        }
    }

    /**
     * Deletes documents based on a list of IDs and notifies waiting processes.
     * 
     * @param ids - List of IDs to delete
     * @return - Optional indicating success
     */
    @Override
    public Optional<Boolean> delete(List<String> ids) {
        synchronized (documentsBatchContainer) {
            deletedIds.add(ids);
            documentsBatchContainer.notify();
        }
        return Optional.of(true);
    }

    /**
     * Checks if the server is currently running.
     * 
     * @return - True if running, otherwise false
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets the running status of the server.
     * 
     * @param running - Boolean to set the running status
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Converts a filter expression into a Lucene Query object.
     * 
     * @param filter - The Expression to convert
     * @return - Corresponding Lucene Query
     */
    private Query convert(Expression filter) {
        Query q = null;
        Operand right = filter.right();
        Operand left = filter.left();
        ExpressionType type = filter.type();
        List<String> keys = new ArrayList<String>();
        switch (type) {
        case IN: {
            Value value = (Value) left;

            Object list = value.value();
            if (list != null) {
                if (list instanceof Collection) {
                    Collection coll = (Collection) list;
                    keys.addAll(coll);
                } else if (list.getClass().isArray()) {

                }
            }
        }
            break;
        }
        return q;
    }

    /**
     * Executes a similarity query based on a search request and a vector, returning
     * a list of matching documents.
     * 
     * @param request - Search request containing search parameters
     * @param vector  - Feature vector for similarity comparison
     * @return - List of documents matching the query
     * @throws IOException - Exception if there's an issue with index reading
     */
    public List<Document> runSimilarityQuery(SearchRequest request, float[] vector) throws IOException {
        List<Document> collect = new ArrayList<Document>();
        KnnFloatVectorQuery vectorQuery = new KnnFloatVectorQuery(LuceneUtils.VECTOR_KNN_FIELD_NAME, vector,
                request.getTopK());
        Expression filter = request.getFilterExpression();
        // The way to implement the filtering is quite unclear, taking the threadlocal
        // filtering values directly
        // and trying to reduce the scope coherently here
        KBContext context = LLMtInteractionContextThreadLocal.Context.get();
        Query filtering = null;
        if (context != null) {
            List<String> kbasesCodes = context.getKnowledgeBasesCodes();
            if (kbasesCodes != null && !kbasesCodes.isEmpty()) {
                Builder booleanQueryBuilder = new BooleanQuery.Builder();
                List<BytesRef> bytesRefsList = kbasesCodes.stream().map(x -> new BytesRef(x)).toList();
                TermInSetQuery termInSet = new TermInSetQuery(DocumentMetaInfos.KNOWLEDGEBASE_CODE, bytesRefsList);
                filtering = termInSet;
            }
        }
        // Query filtering = convert(filter);
        Query runQuery = vectorQuery;
        if (filtering != null) {
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(filtering, Occur.MUST);
            builder.add(vectorQuery, Occur.MUST);
            runQuery = builder.build();
        }
        try (IndexReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs topDocs = searcher.search(vectorQuery, request.getTopK());
            ScoreDoc[] score = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : score) {
                org.apache.lucene.document.Document document = searcher.storedFields().document(scoreDoc.doc);
                collect.add(LuceneUtils.luceneToAiDocument(document).getDocument());
            }
        }

        return collect;
    }

    /**
     * Executes a keyword search query across specified knowledge bases and keywords, returning
     * search results.
     * 
     * @param kbases - List of knowledge base identifiers
     * @param x - SearchKeywordRequest containing keywords and their logical operation
     * @return - SearchKeywordResults containing the search result items
     * @throws IOException - Exception if there's an issue with index reading
     * @throws ParseException - Exception if query parsing fails
     */
    public SearchKeywordResults runSearch(List<String> kbases, SearchKeywordRequest x)
            throws IOException, ParseException {
        final SearchKeywordResults results = new SearchKeywordResults();
        StringBuffer inKnowledgeBase = new StringBuffer();
        inKnowledgeBase.append("(");
        for (int j = 0; j < kbases.size(); j++) {
            inKnowledgeBase.append("\"");
            inKnowledgeBase.append(kbases.get(j));
            inKnowledgeBase.append("\"");
            if (j < kbases.size() - 1) {
                inKnowledgeBase.append(" OR ");
            }
        }
        inKnowledgeBase.append(")");
        StringBuffer keywordSearch = new StringBuffer("(");
        int i = 0;
        for (String keyword : x.getKeywords()) {
            String k = keyword.replace('\n', ' ').replace("\"", "").trim();
            keywordSearch.append("\"" + k + "\" ");
            if (i < x.getKeywords().size() - 1) {
                String OPERATOR = " AND ";
                if (x.getOperator() != null) {
                    OPERATOR = " " + x.getOperator().name() + " ";
                }
                keywordSearch.append(OPERATOR);
            }
            i++;
        }
        keywordSearch.append(")");
        ComplexPhraseQueryParser filterKnowledgeBaseQueryParser = new ComplexPhraseQueryParser(
                DocumentMetaInfos.KNOWLEDGEBASE_CODE, analyzer);
        ComplexPhraseQueryParser filterByKeywordsQueryParser = new ComplexPhraseQueryParser(LuceneUtils.CONTENT_FIELD,
                analyzer);
        Query filterKnowledgeBaseQuery = filterKnowledgeBaseQueryParser.parse(inKnowledgeBase.toString());
        Query byKeyword = filterByKeywordsQueryParser.parse(keywordSearch.toString());
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        BooleanQuery resultingQuery = builder.build();
        builder.add(filterKnowledgeBaseQuery, Occur.MUST);
        builder.add(byKeyword, Occur.MUST);
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(resultingQuery, 3);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            org.apache.lucene.document.Document doc = searcher.storedFields().document(scoreDoc.doc);
            ResultItem item = new ResultItem();
            item.setContent(doc.get(LuceneUtils.CONTENT_FIELD));
            item.setId(doc.get(LuceneUtils.ID_FIELD));
            item.setContentCode(doc.get(DocumentMetaInfos.CONTENT_CODE));
            results.getResults().add(item);
        }
        return results;
    }

    /**
     * Initiates server shutdown, stopping any ongoing operations.
     */
    @Override
    public void shutdown() {
        // Implementation left empty as it might depend on external factors
    }

    /**
     * Deletes documents based on document reference codes provided.
     * 
     * @param codes - List of document reference codes to delete
     */
    @Override
    public void deleteByDocumentReferenceCodes(List<String> codes) {
        synchronized (documentsBatchContainer) {
            deletedReferenceCodes.add(codes);
            documentsBatchContainer.notify();
        }
    }

    /**
     * Begins the shutdown process by setting the running flag to false
     * and notifying all waiting threads.
     */
    @Override
    public void startShutdown() {
        running = false;
        synchronized (documentsBatchContainer) {
            documentsBatchContainer.notifyAll();
        }
        synchronized (blockCallerMutex) {
            blockCallerMutex.notifyAll();
        }
    }

    /**
     * Deletes documents based on a filter expression.
     * 
     * @param filterExpression - Expression defining the criteria for deletion
     */
    @Override
    public void delete(Expression filterExpression) {
        // TODO Auto-generated method stub
    }
}