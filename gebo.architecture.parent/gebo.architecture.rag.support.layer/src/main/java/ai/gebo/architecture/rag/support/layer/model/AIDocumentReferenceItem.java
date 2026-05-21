/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.rag.support.layer.model;

import static org.mockito.Mockito.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.model.ExtractedDocumentMetaData;
import lombok.Data;

/**
 * AI generated comments Represents a reference item for a document in a
 * Retrieval-Augmented Generation (RAG) model, holding metadata and a list of
 * document fragments.
 */
@Data
public class AIDocumentReferenceItem implements IAIContent, Cloneable {

	// Number of tokens in the document.
	private int tokensSize;

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
	private List<AIDocumentFragment> fragments = new ArrayList<AIDocumentFragment>();

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
	public AIDocumentReferenceItem() {
		// Default constructor
	}

	/**
	 * Constructs a RagDocumentReferenceItem using extracted document metadata.
	 *
	 * @param metadata The metadata extracted from the document.
	 */
	public AIDocumentReferenceItem(ExtractedDocumentMetaData metadata) {
		this.code = metadata.getCode();
		this.name = metadata.getName();
		this.contentType = metadata.getContentType();
		this.extension = metadata.getExtension();
		this.parentProjectCode = metadata.getParentProjectCode();
		this.rootKnowledgebaseCode = metadata.getRootKnowledgebaseCode();
		this.originalUrl = metadata.getOriginalUrl();
		this.tokensSize = 0;
		this.NBytes = 0l;
	}

	/**
	 * Streams the child elements (fragments) of the document reference.
	 *
	 * @return A stream of IRagContent elements.
	 */
	@Override
	public Stream<IAIContent> streamChilds() {
		return fragments != null ? fragments.stream().map(x -> x) : Stream.of();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void reorderFragmentsByPosition() {
		TreeMap<Long, List<AIDocumentFragment>> order = new TreeMap<Long, List<AIDocumentFragment>>();
		if (this.fragments != null) {
			List<AIDocumentFragment> unpositioned = new ArrayList<AIDocumentFragment>();
			this.fragments.forEach(x -> {
				if (x.getChunkPosition() == null) {
					unpositioned.add(x);
				} else {
					if (!order.containsKey(x.getChunkPosition())) {
						order.put(x.getChunkPosition(), new ArrayList<AIDocumentFragment>());
					}
					order.get(x.getChunkPosition()).add(x);
				}
			});
			List<AIDocumentFragment> newSegmentsList = new ArrayList<AIDocumentFragment>();
			order.values().forEach(vector -> {
				newSegmentsList.addAll(vector);
			});
			newSegmentsList.addAll(unpositioned);
			this.fragments = newSegmentsList;
		}
	}

	public int countFragments() {
		int i = fragments.size();
		return i;
	}

	public List<Document> aiDocumentsList() {
		final List<Document> documents = new ArrayList<Document>();

		fragments.forEach(y -> {
			if (y.toAIDocument() != null)
				documents.add(y.toAIDocument());
		});
		return documents;
	}

	public static AIDocumentReferenceItem join(AIDocumentReferenceItem... docs) {
		AIDocumentReferenceItem outDoc = new AIDocumentReferenceItem();
		if (docs != null && docs.length > 0) {
			try {
				outDoc = (AIDocumentReferenceItem) docs[0].clone();

				Map<String, AIDocumentFragment> fragmentsMap = new HashMap<String, AIDocumentFragment>();
				for (AIDocumentReferenceItem doc : docs) {
					doc.fragments.forEach(x -> {
						fragmentsMap.put(x.getCode(), x);
					});
				}
				outDoc.fragments = new ArrayList<AIDocumentFragment>(fragmentsMap.values());
				outDoc.reorderFragmentsByPosition();
				outDoc.recalculateSize();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("Clone not supported...", e);
			}
		}
		return outDoc;
	}

	public String extractTokens(int sampleTextTokensSize) {
		StringBuffer buffer = new StringBuffer();
		int tokensSize = 0;
		for (AIDocumentFragment fragment : fragments) {
			if (tokensSize + fragment.getTokensSize() <= sampleTextTokensSize) {
				tokensSize += fragment.getTokensSize();
				buffer.append(fragment.getDocumentContent());
				buffer.append("...");
			} else {
				double delta = fragment.getTokensSize() - tokensSize;
				if (delta > 0.0) {
					final int stringLength = fragment.getDocumentContent() != null
							? fragment.getDocumentContent().length() - 1
							: 0;
					int nChars = Math.min((int) (delta * 4.2), stringLength);
					if (nChars > 0) {
						buffer.append(fragment.getDocumentContent().substring(0, nChars));
					}
				}
			}
		}
		return buffer.toString();
	}

}