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
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

/**
 * AI generated comments
 * This class represents a cached DAO (Data Access Object) result for RAG (Retrieval-Augmented Generation) documents.
 * It implements the IRagContent interface to provide methods for accessing and manipulating document references,
 * and recalculating their size and weight.
 */
public class RagDocumentsCachedDaoResult implements IRagContent {
	// Number of tokens in the document
	private long NTokens;
	// Number of bytes in the document
	private long NBytes;
	// List of document reference items
	private List<RagDocumentReferenceItem> documentItems = new ArrayList<RagDocumentReferenceItem>();

	/**
	 * Gets the number of tokens in the document.
	 * @return the number of tokens
	 */
	public long getNTokens() {
		return NTokens;
	}

	/**
	 * Sets the number of tokens in the document.
	 * @param nTokens the number of tokens
	 */
	public void setNTokens(long nTokens) {
		NTokens = nTokens;
	}

	/**
	 * Gets the number of bytes in the document.
	 * @return the number of bytes
	 */
	public long getNBytes() {
		return NBytes;
	}

	/**
	 * Sets the number of bytes in the document.
	 * @param nBytes the number of bytes
	 */
	public void setNBytes(long nBytes) {
		NBytes = nBytes;
	}

	/**
	 * Gets the list of document reference items.
	 * @return the list of document reference items
	 */
	public List<RagDocumentReferenceItem> getDocumentItems() {
		return documentItems;
	}

	/**
	 * Sets the list of document reference items.
	 * @param documentItems the list of document reference items
	 */
	public void setDocumentItems(List<RagDocumentReferenceItem> documentItems) {
		this.documentItems = documentItems;
	}

	/**
	 * Streams the child elements of this RAG content.
	 * @return a stream of child RAG contents
	 */
	@Override
	public Stream<IRagContent> streamChilds() {
		return documentItems != null ? documentItems.stream().map(x -> x) : Stream.of();
	}

	/**
	 * Recalculates the size of the document items based on their token count.
	 * Adjusts the weighted results ranking of each document and its fragments.
	 */
	@Override
	public void recalculateSize() {
		IRagContent.super.recalculateSize();
		if (NTokens > 0l) {
			double globalWeight = NTokens;
			if (documentItems != null) {
				for (RagDocumentReferenceItem item : documentItems) {
					if (item.getNTokens() > 0l) {
						double documentSize = item.getNTokens();
						item.setWeightedResultsRanking(100.0 * documentSize / globalWeight);
						if (item.getFragments() != null) {
							for (RagDocumentFragment f : item.getFragments()) {
								if (f.getNTokens() > 0l) {
									f.setWeightedResultsRanking(100.0 * ((double) f.getNTokens()) / globalWeight);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Orders the document items by their weight, using their weighted results ranking.
	 * Ensures the document items list is updated with the ordered items.
	 */
	public void orderByDocumentWeight() {
		this.recalculateSize();
		final TreeMap<Double, List<RagDocumentReferenceItem>> ordered = new TreeMap<Double, List<RagDocumentReferenceItem>>();
		if (documentItems != null) {
			boolean allAreWeighted = true;
			for (RagDocumentReferenceItem x : documentItems) {
				allAreWeighted = allAreWeighted && x.getWeightedResultsRanking() > 0.0;
				Double key = x.getWeightedResultsRanking();
				if (!ordered.containsKey(key)) {
					ordered.put(key, new ArrayList<RagDocumentReferenceItem>());
				}
				ordered.get(key).add(x);
			}
			if (allAreWeighted) {
				List<RagDocumentReferenceItem> newList = new ArrayList<RagDocumentReferenceItem>();
				for (Entry<Double, List<RagDocumentReferenceItem>> entry : ordered.entrySet()) {
					Double key = entry.getKey();
					List<RagDocumentReferenceItem> val = entry.getValue();
					newList.addAll(val);
				}
				documentItems = newList;
			}
		}
	}

	/**
	 * Compiles a list of AI documents from the document fragments.
	 * Only fragments with an associated document are included.
	 * @return a list of AI documents
	 */
	public List<Document> aiDocumentsList() {
		final List<Document> documents = new ArrayList<Document>();
		documentItems.forEach(x -> {
			x.getFragments().forEach(y -> {
				if (y.getDocument() != null)
					documents.add(y.getDocument());
			});
		});
		return documents;
	}

}