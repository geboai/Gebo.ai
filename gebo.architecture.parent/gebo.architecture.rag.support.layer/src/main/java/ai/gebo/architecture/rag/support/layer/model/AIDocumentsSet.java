/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.rag.support.layer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.model.IJsonClonable;
import lombok.Data;

/**
 * AI generated comments This class represents a cached DAO (Data Access Object)
 * result for RAG (Retrieval-Augmented Generation) documents. It implements the
 * IRagContent interface to provide methods for accessing and manipulating
 * document references, and recalculating their size and weight.
 */
@Data
public class AIDocumentsSet implements IAIContent, IJsonClonable<AIDocumentsSet>, Cloneable {
	// Number of tokens in the document
	private int tokensSize;
	// Number of bytes in the document
	private long NBytes;
	// List of document reference items
	private List<AIDocumentReferenceItem> documentItems = new ArrayList<AIDocumentReferenceItem>();

	/**
	 * Streams the child elements of this RAG content.
	 * 
	 * @return a stream of child RAG contents
	 */
	@Override
	public Stream<IAIContent> streamChilds() {
		return documentItems != null ? documentItems.stream().map(x -> x) : Stream.of();
	}

	/**
	 * Recalculates the size of the document items based on their token count.
	 * Adjusts the weighted results ranking of each document and its fragments.
	 */
	@Override
	public void recalculateSize() {
		IAIContent.super.recalculateSize();
		if (tokensSize > 0) {
			double globalWeight = tokensSize;
			if (documentItems != null) {
				for (AIDocumentReferenceItem item : documentItems) {
					if (item.getTokensSize() > 0l) {
						double documentSize = item.getTokensSize();
						item.setWeightedResultsRanking(100.0 * documentSize / globalWeight);
						if (item.getFragments() != null) {
							for (AIDocumentFragment f : item.getFragments()) {
								if (f.getTokensSize() > 0l) {
									f.setWeightedResultsRanking(100.0 * ((double) f.getTokensSize()) / globalWeight);
								}
							}
						}
					}
				}
			}
		}
	}

	public void reorderFragmentsByPosition() {
		if (documentItems != null) {
			documentItems.stream().forEach(x -> x.reorderFragmentsByPosition());
		}
	}

	/**
	 * Orders the document items by their weight, using their weighted results
	 * ranking. Ensures the document items list is updated with the ordered items.
	 */
	public void orderByDocumentWeight() {
		this.recalculateSize();
		final TreeMap<Double, List<AIDocumentReferenceItem>> ordered = new TreeMap<Double, List<AIDocumentReferenceItem>>();
		if (documentItems != null) {
			boolean allAreWeighted = true;
			for (AIDocumentReferenceItem x : documentItems) {
				allAreWeighted = allAreWeighted && x.getWeightedResultsRanking() > 0.0;
				Double key = x.getWeightedResultsRanking();
				if (!ordered.containsKey(key)) {
					ordered.put(key, new ArrayList<AIDocumentReferenceItem>());
				}
				ordered.get(key).add(x);
			}
			if (allAreWeighted) {
				List<AIDocumentReferenceItem> newList = new ArrayList<AIDocumentReferenceItem>();
				for (Entry<Double, List<AIDocumentReferenceItem>> entry : ordered.entrySet()) {
					Double key = entry.getKey();
					List<AIDocumentReferenceItem> val = entry.getValue();
					newList.addAll(val);
				}
				documentItems = newList;
			}
		}
	}

	public static AIDocumentsSet join(AIDocumentsSet... result) {
		Map<String, AIDocumentReferenceItem> docsMap = new HashMap<String, AIDocumentReferenceItem>();
		for (AIDocumentsSet ragDocumentsCachedDaoResult : result) {
			if (ragDocumentsCachedDaoResult != null)
				joinMap(ragDocumentsCachedDaoResult, docsMap);
		}
		return fromMap(docsMap);
	}

	public static AIDocumentsSet fromMap(Map<String, AIDocumentReferenceItem> docsMap) {
		AIDocumentsSet results = new AIDocumentsSet();
		docsMap.values().forEach(x -> results.getDocumentItems().add(x));
		results.recalculateSize();
		return results;
	}

	public static void joinMap(AIDocumentsSet result, Map<String, AIDocumentReferenceItem> docsMap) {
		result.documentItems.forEach(doc -> {
			if (!docsMap.containsKey(doc.getCode())) {
				try {
					docsMap.put(doc.getCode(), (AIDocumentReferenceItem) doc.clone());
				} catch (CloneNotSupportedException e) {
					LOGGER.error("Error cloning", e);
				}
			} else {
				AIDocumentReferenceItem docCopy = docsMap.get(doc.getCode());
				for (AIDocumentFragment fragment : doc.getFragments()) {
					if (!docCopy.getFragments().stream().anyMatch(x -> x.getCode().equals(fragment.getCode()))) {
						try {
							docCopy.getFragments().add((AIDocumentFragment) fragment.clone());
						} catch (CloneNotSupportedException e) {
							LOGGER.error("Error cloning", e);
						}
					}
				}

			}
		});
	}

	public int countFragments() {
		int i = 0;
		for (AIDocumentReferenceItem ragDocumentReferenceItem : documentItems) {
			i += ragDocumentReferenceItem.countFragments();
		}
		return i;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Compiles a list of AI documents from the document fragments. Only fragments
	 * with an associated document are included.
	 * 
	 * @return a list of AI documents
	 */
	public List<Document> aiDocumentsList() {
		final List<Document> documents = new ArrayList<Document>();
		final List<AIDocumentFragment> fragments = new ArrayList<AIDocumentFragment>();
		final Map<String, Boolean> alreadyInserted = new HashMap<String, Boolean>();
		documentItems.forEach(x -> {
			x.getFragments().forEach(y -> {
				if (!alreadyInserted.containsKey(y.getDocumentId())) {
					fragments.add(y);
					alreadyInserted.put(y.getDocumentId(), true);
				}
			});
		});
		fragments.forEach(y -> {
			if (y.toAIDocument() != null)
				documents.add(y.toAIDocument());
		});
		return documents;
	}

	public static AIDocumentsSet from(List<Document> documents) {

		Map<String, AIDocumentReferenceItem> data = new HashMap<String, AIDocumentReferenceItem>();
		for (Document document : documents) {
			AIDocumentFragment fragment = new AIDocumentFragment(document,
					ExtractedDocumentMetaData.of(document.getMetadata()));
			data.computeIfAbsent(fragment.getCode(), (code) -> {
				AIDocumentReferenceItem item = new AIDocumentReferenceItem(
						ExtractedDocumentMetaData.of(document.getMetadata()));
				item.setCode(code);
				return item;
			});
			data.get(fragment.getCode()).getFragments().add(fragment);
			data.get(fragment.getCode()).recalculateSize();
		}
		return fromMap(data);
	}

	public void removeAIDocumentReferenceByCode(String code) {
		int index = 0;
		boolean found = false;
		for (AIDocumentReferenceItem aiDocumentReferenceItem : documentItems) {
			if (found = (aiDocumentReferenceItem.getCode().equals(code))) {
				break;
			}
			index++;
		}
		if (found) {
			documentItems.remove(index);
		}
	}

	public static void removeAIDocumentReferenceByCode(String code, AIDocumentsSet... set) {
		if (set != null) {

			for (AIDocumentsSet aiDocumentsSet : set) {
				if (aiDocumentsSet != null) {
					aiDocumentsSet.removeAIDocumentReferenceByCode(code);
				}
			}
		}
	}
}