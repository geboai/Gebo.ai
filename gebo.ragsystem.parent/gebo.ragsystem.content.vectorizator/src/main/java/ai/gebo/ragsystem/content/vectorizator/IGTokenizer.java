/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator;

import java.util.List;

import org.springframework.ai.document.Document;

/**
 * AI generated comments
 * 
 * Interface that defines functionality for tokenizing documents.
 * This interface is responsible for breaking down documents into manageable tokens
 * based on a specified threshold.
 */
public interface IGTokenizer {
    /**
     * Tokenizes a list of documents according to a maximum threshold.
     *
     * @param docs The list of documents to tokenize
     * @param maxThreshold The maximum token threshold per document
     * @return A list of tokenized documents
     */
    public List<Document> tokenize(List<Document> docs, int maxThreshold); 
}