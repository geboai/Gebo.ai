/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.ragsystem.content.vectorizator.IGTokenizer;

/**
 * Implementation of the IGTokenizer interface that handles the tokenization
 * of documents for the RAG system.
 * AI generated comments
 */
@Service
public class GTokenizerImpl implements IGTokenizer {
	static Logger LOGGER = LoggerFactory.getLogger(GTokenizerImpl.class);

	/**
	 * Default constructor for GTokenizerImpl.
	 */
	public GTokenizerImpl() {

	}

	/**
	 * Sanitizes a map by removing any entries with null keys or values.
	 * 
	 * @param map The map to sanitize
	 * @return A new map containing only non-null entries
	 */
	private Map<String, Object> sanitizeMap(Map<String, Object> map) {
		Map<String, Object> table = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			if (key != null && val != null) {
				table.put(key, val);
			}
		}
		return table;
	}

	/**
	 * Creates new sanitized documents by removing any metadata with null keys or values.
	 * 
	 * @param documents The list of documents to sanitize
	 * @return A new list containing documents with sanitized metadata
	 */
	private List<Document> sanitize(List<Document> documents) {
		List<Document> fixedDocs = new ArrayList<Document>();
		for (Document document : documents) {
			Document newDoc = new Document(document.getId(), document.getText(), sanitizeMap(document.getMetadata()));

			fixedDocs.add(newDoc);
		}
		return fixedDocs;
	}

	/**
	 * Tokenizes a list of documents using the TokenTextSplitter.
	 * Adds metadata about bytes size and token count to each document.
	 * 
	 * @param docs The list of documents to tokenize
	 * @param maxThreshold The maximum threshold for token size
	 * @return A list of tokenized documents
	 */
	@Override
	public List<Document> tokenize(List<Document> docs, int maxThreshold) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin Tokenizing");
		}
		List<Document> outContents = new ArrayList<Document>();
		List<Document> fixedIdsDocs = sanitize(docs);
		int defaultChunkSize = maxThreshold;
		// The minimum size of each text chunk in characters
		int minChunkSizeChars = 350;
		// Discard chunks shorter than this
		int minChunkLengthToEmbed = 5;
		// The maximum number of chunks to generate from a text
		int maxNumChunks = 10000;
		boolean keepSeparator = true;
		TokenTextSplitter tokensplitter = new TokenTextSplitter(defaultChunkSize, minChunkSizeChars,
				minChunkLengthToEmbed, maxNumChunks, keepSeparator);
		outContents = tokensplitter.apply(fixedIdsDocs);
		JTokkitTokenCountEstimator estimator = new JTokkitTokenCountEstimator();
		for (Document document : outContents) {
			int bytesSize = document.getText() != null ? document.getText().length() * 2 : 0;
			int tokensSize = document.getText() != null && document.isText() ? estimator.estimate(document.getText())
					: 0;
			document.getMetadata().put(DocumentMetaInfos.GEBO_BYTES_LENGTH, bytesSize);
			document.getMetadata().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, tokensSize);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End Tokenizing");
		}
		return outContents;
	}

}