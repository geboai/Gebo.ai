/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.model.ExtractedDocumentMetaData;
import lombok.Data;

/**
 * Represents a fragment of a document within the RAG (Retrieval-Augmented
 * Generation) system. This class holds information about the document,
 * including size in bytes and tokens, and metadata related to its hierarchy in
 * a knowledge base or project.
 * 
 * @author AI generated comments
 */
@Data
public class RagDocumentFragment implements IRagContent, Cloneable {

	// Number of tokens in the document fragment
	private long NTokens;

	// Number of bytes in the document fragment
	private long NBytes;

	// Associated document object
	private String documentId = null;
	private String documentContent = null;
	private Map<String, Object> metaData = new HashMap<>();

	// Conversion constant from bytes to tokens
	private static final double bytes2token = 4.2;

	// Unique code identifying the document fragment
	private String code = null;

	// Code identifying the root knowledge base this fragment belongs to
	private String rootKnowledgebaseCode = null;

	// Code identifying the parent project of this document fragment
	private String parentProjectCode = null;

	// Rank index used in some ranking algorithm
	private Integer rankIndex = null;

	// Weighted ranking score for results
	private double weightedResultsRanking = 0.0;
	private String origin = null;

	public RagDocumentFragment() {

	}

	/**
	 * Constructs a new RagDocumentFragment using the provided document and
	 * metadata.
	 * 
	 * @param x        the Document associated with this fragment.
	 * @param metaData the meta-data extracted related to this document.
	 */
	public RagDocumentFragment(Document x, ExtractedDocumentMetaData metaData) {
		this.documentId = x.getId();
		this.documentContent = x.getText();
		this.metaData = x.getMetadata();
		this.code = metaData.getCode();
		this.parentProjectCode = metaData.getParentProjectCode();
		this.rootKnowledgebaseCode = metaData.getRootKnowledgebaseCode();
		this.NBytes = metaData.getBytesLength() != null ? metaData.getBytesLength().longValue() : 0l;
		this.NTokens = metaData.getTokenLength() != null ? metaData.getTokenLength().longValue() : 0l;
	}

	
	/**
	 * Retrieves the associated Document object.
	 * 
	 * @return the Document object.
	 */
	public Document toAIDocument() {
		return new Document(documentId, documentContent, metaData);
	}

	

	/**
	 * Recalculate the size of the document fragment. Currently not implemented.
	 */
	@Override
	public void recalculateSize() {
		// Implementation can be added here for recalculating size
	}

	/**
	 * Streams child IRagContent elements.
	 * 
	 * @return an empty stream of IRagContent elements.
	 */
	@Override
	public Stream<IRagContent> streamChilds() {
		return Stream.of();
	}

	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}