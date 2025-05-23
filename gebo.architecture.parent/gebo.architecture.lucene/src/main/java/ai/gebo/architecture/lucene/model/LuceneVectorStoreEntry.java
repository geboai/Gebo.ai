/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.model;

import java.io.Serializable;

import org.springframework.ai.document.Document;

/**
 * Gebo.ai comment agent
 * Represents an immutable entry in a Lucene vector store, consisting of a document and its corresponding embedding vector.
 * This class implements {@link Serializable} to allow serialization of its instances.
 */
public final class LuceneVectorStoreEntry implements Serializable {

    /** The embedding vector associated with the Document. */
    private final float[] embedding;

    /** The document object associated with this vector store entry. */
    private final Document document;

    /**
     * Constructs a LuceneVectorStoreEntry with the specified document and no embedding vector.
     * 
     * @param document The document associated with this entry.
     */
    public LuceneVectorStoreEntry(Document document) {
        this.document = document;
        this.embedding = null; // No embedding provided, set to null
    }

    /**
     * Constructs a LuceneVectorStoreEntry with the specified document and embedding vector.
     * 
     * @param document The document associated with this entry.
     * @param embedding The embedding vector associated with the document.
     */
    public LuceneVectorStoreEntry(Document document, float[] embedding) {
        this.document = document;
        this.embedding = embedding;
    }

    /**
     * Returns the embedding vector associated with this entry.
     * 
     * @return A float array representing the embedding vector.
     */
    public float[] getEmbedding() {
        return embedding;
    }

    /**
     * Returns the document associated with this entry.
     * 
     * @return The document associated with this entry.
     */
    public Document getDocument() {
        return document;
    }
}