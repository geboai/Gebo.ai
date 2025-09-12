/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.model.ExtractedDocumentMetaData;

/**
 * Represents a fragment of a document within the RAG (Retrieval-Augmented Generation) system.
 * This class holds information about the document, including size in bytes and tokens,
 * and metadata related to its hierarchy in a knowledge base or project.
 * 
 * @author AI generated comments
 */
public class RagDocumentFragment implements IRagContent {
	
	// Number of tokens in the document fragment
	private long NTokens;
	
	// Number of bytes in the document fragment
	private long NBytes;
	
	// Associated document object
	private Document document = null;
	
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

	/**
	 * Constructs a new RagDocumentFragment using the provided document and metadata.
	 * 
	 * @param x the Document associated with this fragment.
	 * @param metaData the meta-data extracted related to this document.
	 */
	public RagDocumentFragment(Document x, ExtractedDocumentMetaData metaData) {
		this.document = x;
		this.code = metaData.getCode();
		this.parentProjectCode = metaData.getParentProjectCode();
		this.rootKnowledgebaseCode = metaData.getRootKnowledgebaseCode();
		this.NBytes = metaData.getBytesLength() != null ? metaData.getBytesLength().longValue() : 0l;
		this.NTokens = metaData.getTokenLength() != null ? metaData.getTokenLength().longValue() : 0l;
	}

	/**
	 * Retrieves the number of tokens in the document fragment.
	 * 
	 * @return the number of tokens.
	 */
	public long getNTokens() {
		return NTokens;
	}

	/**
	 * Sets the number of tokens in the document fragment.
	 * 
	 * @param nTokens the number of tokens.
	 */
	public void setNTokens(long nTokens) {
		this.NTokens = nTokens;
	}

	/**
	 * Retrieves the number of bytes in the document fragment.
	 * 
	 * @return the number of bytes.
	 */
	public long getNBytes() {
		return NBytes;
	}

	/**
	 * Sets the number of bytes in the document fragment.
	 * 
	 * @param nBytes the number of bytes.
	 */
	public void setNBytes(long nBytes) {
		this.NBytes = nBytes;
	}

	/**
	 * Retrieves the associated Document object.
	 * 
	 * @return the Document object.
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Sets the associated Document object.
	 * 
	 * @param document the Document to set.
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * Retrieves the unique code of the document fragment.
	 * 
	 * @return the fragment code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code of the document fragment.
	 * 
	 * @param code the fragment code.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Retrieves the root knowledge base code.
	 * 
	 * @return the root knowledge base code.
	 */
	public String getRootKnowledgebaseCode() {
		return rootKnowledgebaseCode;
	}

	/**
	 * Sets the root knowledge base code.
	 * 
	 * @param rootKnowledgebaseCode the root knowledge base code.
	 */
	public void setRootKnowledgebaseCode(String rootKnowledgebaseCode) {
		this.rootKnowledgebaseCode = rootKnowledgebaseCode;
	}

	/**
	 * Retrieves the parent project code.
	 * 
	 * @return the parent project code.
	 */
	public String getParentProjectCode() {
		return parentProjectCode;
	}

	/**
	 * Sets the parent project code.
	 * 
	 * @param parentProjectCode the parent project code.
	 */
	public void setParentProjectCode(String parentProjectCode) {
		this.parentProjectCode = parentProjectCode;
	}

	/**
	 * Recalculate the size of the document fragment.
	 * Currently not implemented.
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

	/**
	 * Retrieves the rank index used in ranking algorithms.
	 * 
	 * @return the rank index.
	 */
	public Integer getRankIndex() {
		return rankIndex;
	}

	/**
	 * Sets the rank index used in ranking algorithms.
	 * 
	 * @param rankIndex the rank index.
	 */
	public void setRankIndex(Integer rankIndex) {
		this.rankIndex = rankIndex;
	}

	/**
	 * Retrieves the weighted results ranking score.
	 * 
	 * @return the weighted results ranking score.
	 */
	public double getWeightedResultsRanking() {
		return weightedResultsRanking;
	}

	/**
	 * Sets the weighted results ranking score.
	 * 
	 * @param weightedResultsRanking the weighted results ranking score.
	 */
	public void setWeightedResultsRanking(double weightedResultsRanking) {
		this.weightedResultsRanking = weightedResultsRanking;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}