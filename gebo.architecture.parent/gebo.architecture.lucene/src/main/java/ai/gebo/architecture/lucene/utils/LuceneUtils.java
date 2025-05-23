/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.KnnFloatVectorField;
import org.apache.lucene.document.TextField;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.lucene.model.LuceneVectorStoreEntry;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Utility class for handling conversion between Lucene documents
 * and application-specific document formats.
 * 
 *  AI generated comments
 */
public class LuceneUtils {
	// Constant for the field name used to store the semantic vector in Lucene
	public static final String VECTOR_KNN_FIELD_NAME = "semantic";
	// Constant for the field name used to store the document ID in Lucene
	public static final String ID_FIELD = "id";
	// Constant for the field name used to store the document content in Lucene
	public static final String CONTENT_FIELD = "content";

	/**
	 * Converts a Lucene document to an AI-specific LuceneVectorStoreEntry.
	 *
	 * @param ldoc The Lucene document to convert.
	 * @return A LuceneVectorStoreEntry containing the converted document.
	 */
	public static LuceneVectorStoreEntry luceneToAiDocument(org.apache.lucene.document.Document ldoc) {
		// Map to hold document metadata information
		Map<String, Object> metaInfos = new HashMap<String, Object>();
		// Iterate over all attributes and populate metadata
		for (String attribute : DocumentMetaInfos.ALL_ATTRIBUTES) {
			String value = ldoc.get(attribute);
			if (value != null) {
				metaInfos.put(attribute, value);
			}
		}
		// Create a new Document with ID, content, and extracted metadata
		Document document = new Document(ldoc.get("id"), ldoc.get("content"), metaInfos);
		// Retrieve the semantic vector field
		KnnFloatVectorField vectorField = (KnnFloatVectorField) ldoc.getField(VECTOR_KNN_FIELD_NAME);
		// Create and return a new LuceneVectorStoreEntry
		LuceneVectorStoreEntry entry = new LuceneVectorStoreEntry(document,
				vectorField != null ? vectorField.vectorValue() : null);

		return entry;
	}

	/**
	 * Converts an AI-specific LuceneVectorStoreEntry to a Lucene document.
	 *
	 * @param doc The LuceneVectorStoreEntry to convert.
	 * @return An org.apache.lucene.document.Document containing the converted document.
	 */
	public static org.apache.lucene.document.Document aiToLuceneDocument(LuceneVectorStoreEntry doc) {
		// Retrieve the underlying Document and embedding from the entry
		Document document = doc.getDocument();
		float[] embedding = doc.getEmbedding();
		// New Lucene document to populate
		org.apache.lucene.document.Document ldoc = new org.apache.lucene.document.Document();
		// Add the semantic vector field if the embedding is valid
		if (embedding != null && embedding.length <= 1024) {
			ldoc.add(new KnnFloatVectorField(VECTOR_KNN_FIELD_NAME, embedding));
		}
		// Add the document ID field
		ldoc.add(new TextField(ID_FIELD, document.getId(), Field.Store.YES));
		// Add all metadata as individual fields
		Map<String, Object> metainfos = document.getMetadata();
		for (String attribute : DocumentMetaInfos.ALL_ATTRIBUTES) {
			Object value = metainfos.get(attribute);
			if (value != null && value instanceof String) {
				// Store the value as a TextField in Lucene
				ldoc.add(new TextField(attribute, value.toString().trim(), Field.Store.YES));
			}
		}
		// Add the document content field
		ldoc.add(new TextField(CONTENT_FIELD, document.getText(), Field.Store.YES));
		return ldoc;
	}

}