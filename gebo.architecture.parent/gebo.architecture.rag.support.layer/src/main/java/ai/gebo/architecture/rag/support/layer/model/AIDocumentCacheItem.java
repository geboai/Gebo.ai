/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.rag.support.layer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

/**
 * RagDocumentCacheItem class represents a cache item for a document in a retrieval-augmented generation system.
 * It extends GBaseObject and is annotated for MongoDB document storage.
 * It stores the document's text, token size, byte size, and last modification time.
 * AI generated comments
 */
@org.springframework.data.mongodb.core.mapping.Document
@Data
public class AIDocumentCacheItem extends GBaseObject {
    // Stores the actual textual content of the document
    private String text = null;
	
    // Stores the estimated count of tokens in the text
    private Long tokensSize = null;
    
    // Stores the size of the document in bytes
    private Long bytesSize = null;
    
    // Stores the timestamp of when the document was last modified
    private Long lastModified = null;

    // Metadata associated with the document
    private Map<String, Object> metaData = new HashMap<String, Object>();

   
    /**
     * Factory method to create a RagDocumentCacheItem from a stream of Document objects.
     * Aggregates the text from each Document and calculates token and byte sizes.
     *
     * @param documents a stream of Document objects
     * @return a populated RagDocumentCacheItem instance
     */
    public static AIDocumentCacheItem of(Stream<Document> documents) {
        final AIDocumentCacheItem item = new AIDocumentCacheItem();
        final StringBuffer buffer = new StringBuffer();
        documents.forEach(x -> {
            if (item.metaData.isEmpty()) {
                item.metaData.putAll(x.getMetadata()); // Populates metadata if not already populated
            }
            String text = x.getText();
            if (text != null) {
                buffer.append(text); // Appends text to the buffer
                buffer.append("\n");
            }
        });
        item.text = buffer.toString();
        TokenCountEstimator estimator = new JTokkitTokenCountEstimator();
        int tokenCount = estimator.estimate(item.text); // Estimates the token count
        item.tokensSize = Long.valueOf(tokenCount);
        item.bytesSize = Long.valueOf(item.text.length() * 2); // Estimates the byte size assuming 2 bytes per character
        return item;
    }
}