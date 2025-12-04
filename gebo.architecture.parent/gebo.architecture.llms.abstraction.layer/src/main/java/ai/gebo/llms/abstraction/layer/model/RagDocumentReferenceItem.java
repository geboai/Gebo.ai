/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import ai.gebo.model.ExtractedDocumentMetaData;

/**
 * AI generated comments Represents a reference item for a document in a
 * Retrieval-Augmented Generation (RAG) model, holding metadata and a list of
 * document fragments.
 */
public class RagDocumentReferenceItem implements IRagContent,Cloneable {

	// Number of tokens in the document.
	private long NTokens;

	// Total number of tokens for the entire file.
	private long totalFileNTokens = 0l;

	// Number of bytes in the document.
	private long NBytes;

	// Flag indicating if the document reference is complete.
	private boolean complete = false;

	// Unique code for the document reference.
	private String code = null;

	// Code representing the root knowledge base.
	private String rootKnowledgebaseCode = null;

	// Code of the parent project associated with the document.
	private String parentProjectCode = null;

	// List of document fragments associated with this document reference.
	private List<RagDocumentFragment> fragments = new ArrayList<RagDocumentFragment>();

	// MIME type of the document content.
	private String contentType;

	// File extension of the document.
	private String extension;

	// Name of the document.
	private String name;

	// Original URL of the document.
	private String originalUrl;

	// Weighted ranking result, used for sorting or relevance evaluation.
	private double weightedResultsRanking = 0.0;
	

	/**
	 * Default constructor for creating an empty RagDocumentReferenceItem.
	 */
	public RagDocumentReferenceItem() {
		// Default constructor
	}

	/**
	 * Constructs a RagDocumentReferenceItem using extracted document metadata.
	 *
	 * @param metadata The metadata extracted from the document.
	 */
	public RagDocumentReferenceItem(ExtractedDocumentMetaData metadata) {
		this.code = metadata.getCode();
		this.name = metadata.getName();
		this.contentType = metadata.getContentType();
		this.extension = metadata.getExtension();
		this.parentProjectCode = metadata.getParentProjectCode();
		this.rootKnowledgebaseCode = metadata.getRootKnowledgebaseCode();
		this.originalUrl = metadata.getOriginalUrl();
		this.NTokens = 0l;
		this.NBytes = 0l;
	}

	/**
	 * Gets the code of the document reference.
	 *
	 * @return The document code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code for the document reference.
	 *
	 * @param code The document code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the root knowledgebase code.
	 *
	 * @return The root knowledgebase code.
	 */
	public String getRootKnowledgebaseCode() {
		return rootKnowledgebaseCode;
	}

	/**
	 * Sets the root knowledgebase code.
	 *
	 * @param rootKnowledgebaseCode The root knowledgebase code to set.
	 */
	public void setRootKnowledgebaseCode(String rootKnowledgebaseCode) {
		this.rootKnowledgebaseCode = rootKnowledgebaseCode;
	}

	/**
	 * Gets the parent project code.
	 *
	 * @return The parent project code.
	 */
	public String getParentProjectCode() {
		return parentProjectCode;
	}

	/**
	 * Sets the parent project code.
	 *
	 * @param parentProjectCode The parent project code to set.
	 */
	public void setParentProjectCode(String parentProjectCode) {
		this.parentProjectCode = parentProjectCode;
	}

	/**
	 * Gets the number of tokens in the document.
	 *
	 * @return The number of tokens.
	 */
	public long getNTokens() {
		return NTokens;
	}

	/**
	 * Sets the number of tokens in the document.
	 *
	 * @param nTokens The number of tokens to set.
	 */
	public void setNTokens(long nTokens) {
		NTokens = nTokens;
	}

	/**
	 * Gets the number of bytes in the document.
	 *
	 * @return The number of bytes.
	 */
	public long getNBytes() {
		return NBytes;
	}

	/**
	 * Sets the number of bytes in the document.
	 *
	 * @param nBytes The number of bytes to set.
	 */
	public void setNBytes(long nBytes) {
		NBytes = nBytes;
	}

	/**
	 * Gets the list of document fragments.
	 *
	 * @return A list of RagDocumentFragments.
	 */
	public List<RagDocumentFragment> getFragments() {
		return fragments;
	}

	/**
	 * Sets the list of document fragments.
	 *
	 * @param documents A list of fragments to set.
	 */
	public void setFragments(List<RagDocumentFragment> documents) {
		this.fragments = documents;
	}

	/**
	 * Checks if the document reference is complete.
	 *
	 * @return true if complete, false otherwise.
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Sets whether the document reference is complete.
	 *
	 * @param complete The completion status to set.
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	/**
	 * Streams the child elements (fragments) of the document reference.
	 *
	 * @return A stream of IRagContent elements.
	 */
	@Override
	public Stream<IRagContent> streamChilds() {
		return fragments != null ? fragments.stream().map(x -> x) : Stream.of();
	}

	/**
	 * Gets the content type of the document.
	 *
	 * @return The content type.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type of the document.
	 *
	 * @param contentType The content type to set.
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the file extension of the document.
	 *
	 * @return The file extension.
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the file extension of the document.
	 *
	 * @param extension The extension to set.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Gets the name of the document.
	 *
	 * @return The document name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the document.
	 *
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the original URL of the document.
	 *
	 * @return The original URL.
	 */
	public String getOriginalUrl() {
		return originalUrl;
	}

	/**
	 * Sets the original URL of the document.
	 *
	 * @param originalUrl The original URL to set.
	 */
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	/**
	 * Gets the total number of tokens for the entire file.
	 *
	 * @return The total file tokens.
	 */
	public long getTotalFileNTokens() {
		return totalFileNTokens;
	}

	/**
	 * Sets the total number of tokens for the entire file.
	 *
	 * @param totalFileNTokens The total file tokens to set.
	 */
	public void setTotalFileNTokens(long totalFileNTokens) {
		this.totalFileNTokens = totalFileNTokens;
	}

	/**
	 * Gets the weighted results ranking.
	 *
	 * @return The weighted results ranking.
	 */
	public double getWeightedResultsRanking() {
		return weightedResultsRanking;
	}

	/**
	 * Sets the weighted results ranking.
	 *
	 * @param weightedResultsRanking The ranking to set.
	 */
	public void setWeightedResultsRanking(double weightedResultsRanking) {
		this.weightedResultsRanking = weightedResultsRanking;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}