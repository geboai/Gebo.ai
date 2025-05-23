/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.architecture.lucene.config.GeboLuceneConfig;
import ai.gebo.architecture.lucene.model.LuceneVectorStoreEntry;
import ai.gebo.architecture.lucene.model.SearchKeywordRequest;
import ai.gebo.architecture.lucene.model.SearchKeywordResults;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.model.DocumentMetaInfos;

/**
 * AI generated comments
 * The LuceneService class is responsible for handling Lucene search indexing and querying using batch message processing.
 * It listens for context close events to initialize the Lucene server instance.
 */
@Component
@Scope("singleton")
@ConditionalOnProperty(prefix = "ai.gebo.lucene.config", name = "enabled", havingValue = "true")
public class LuceneService implements IGBatchMessagesReceiver, ApplicationListener<ContextClosedEvent> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LuceneService.class);
    @Autowired
    IGGeboConfigService geboConfig;
    @Autowired
    GeboLuceneConfig luceneConfig;
    @Autowired
    IGInternalLuceneServerFactory luceneServerFactory;
    IGInternalLuceneServer luceneServer = null;
    Object monitor = new Object();

    /**
     * Processes a batch of messages and persists them into the Lucene index.
     * @param messages a batch of GMessageEnvelope containing messages to be indexed
     */
    @Override
    public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {

        List<GDocumentMessageFragmentPayload> payloads = new ArrayList<GDocumentMessageFragmentPayload>();
        for (int i = 0; i < messages.getPayload().size(); i++) {
            GMessageEnvelope entry = (GMessageEnvelope) messages.getPayload().get(i);
            if (entry.getPayload() instanceof GDocumentMessageFragmentPayload) {
                GDocumentMessageFragmentPayload payload = (GDocumentMessageFragmentPayload) entry.getPayload();
                payloads.add(payload);
            }
        }
        try {
            indexWrite(payloads);
        } catch (IOException e) {
            // Handle exception by printing the stack trace
            e.printStackTrace();
        }
    }

    /**
     * Converts a document message fragment payload into a Lucene document.
     * @param pl the document message fragment payload
     * @return a Lucene Document representing the payload
     */
    private Document toLuceneDocument(GDocumentMessageFragmentPayload pl) {
        Document ldoc = new Document();
        Map<String, Object> metainfos = pl.getDocuments().get(0).getMetadata();
        for (String attribute : DocumentMetaInfos.ALL_ATTRIBUTES) {
            Object value = metainfos.get(attribute);
            if (value != null && value instanceof String) {
                ldoc.add(new TextField(attribute, value.toString().trim(), Field.Store.YES));
            }
        }
        StringBuffer sb = new StringBuffer();
        pl.getDocuments().stream().forEach(x -> {
            sb.append(x.getText() + "\n");
        });
        ldoc.add(new TextField("content", sb.toString().trim(), Field.Store.YES));
        return ldoc;
    }

    /**
     * Converts a list of document message fragment payloads into Lucene documents.
     * @param payload the list of document message fragment payloads
     * @return a list of Lucene Documents
     */
    private List<Document> toLuceneDocuments(List<GDocumentMessageFragmentPayload> payload) {

        return payload.stream().filter(x -> {
            return !x.getDocuments().isEmpty();
        }).map(x -> {
            return toLuceneDocument(x);
        }).toList();
    }

    /**
     * Writes the provided payloads to the Lucene index.
     * @param val a list of document message fragment payloads to index
     * @throws IOException if an I/O error occurs during indexing
     */
    private void indexWrite(List<GDocumentMessageFragmentPayload> val) throws IOException {
        if (val.isEmpty())
            return;

        List<org.springframework.ai.document.Document> aidocs = new ArrayList<org.springframework.ai.document.Document>();
        List<String> codes = new ArrayList<String>();
        val.stream().forEach(x -> {
            aidocs.addAll(x.getDocuments());
            codes.add(x.getDocumentReference().getCode());
        });
        // Delete existing entries by document reference codes before saving new ones
        luceneServer.deleteByDocumentReferenceCodes(codes);
        // Save new documents into the Lucene index
        luceneServer.save(aidocs.stream().map(x -> {
            LuceneVectorStoreEntry entry = new LuceneVectorStoreEntry(x);
            return entry;
        }).toList());

    }

    /**
     * Executes a search query on the Lucene index.
     * @param kbases the knowledge bases to search
     * @param x the search keyword request
     * @return the results of the search query
     * @throws IOException if an I/O error occurs during the search
     * @throws ParseException if the search query cannot be parsed
     */
    public SearchKeywordResults runSearch(List<String> kbases, SearchKeywordRequest x)
            throws IOException, ParseException {

        return this.luceneServer.runSearch(kbases, x);
    }

    /**
     * Initializes the Lucene server when the application context is closed.
     * @param event the context closed event that triggers initialization
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            Path path = Path.of(geboConfig.getGeboWorkDirectory(), "LuceneIndexing", "data-indexes");
            luceneServer = luceneServerFactory.create(path, luceneConfig.getIndexerConfig());
        } catch (Throwable th) {
            LOGGER.error("Exception in onApplicationEvent", th);
        }
    }

}