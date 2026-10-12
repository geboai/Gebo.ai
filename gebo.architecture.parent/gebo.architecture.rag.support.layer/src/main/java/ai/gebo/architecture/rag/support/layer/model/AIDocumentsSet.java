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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	/**
	 * The documents of the given sets, each once with all their fragments: documents
	 * are grouped by their code, fragments are told apart by their identity (their
	 * chunk id, the same in every store, see {@link AIDocumentFragment#identity()}),
	 * so a fragment two searches found is kept once and every other fragment of a
	 * document is kept. The sets joined are left as they are.
	 */
	public static AIDocumentsSet join(AIDocumentsSet... result) {
		Map<String, AIDocumentReferenceItem> docsMap = new HashMap<String, AIDocumentReferenceItem>();
		int fragmentsIn = 0;
		int sets = 0;
		for (AIDocumentsSet ragDocumentsCachedDaoResult : result) {
			if (ragDocumentsCachedDaoResult != null) {
				sets++;
				fragmentsIn += ragDocumentsCachedDaoResult.countFragments();
				joinMap(ragDocumentsCachedDaoResult, docsMap);
			}
		}
		final AIDocumentsSet joined = fromMap(docsMap);
		if (JOIN_LOGGER.isDebugEnabled()) {
			final int fragmentsOut = joined.countFragments();
			JOIN_LOGGER.debug("join(...) " + sets + " set(s), " + fragmentsIn + " fragment(s) in, " + fragmentsOut
					+ " fragment(s) of " + joined.getDocumentItems().size() + " document(s) out, "
					+ (fragmentsIn - fragmentsOut) + " found more than once");
		}
		return joined;
	}

	/** The logger of the joins, by this class. */
	private static final Logger JOIN_LOGGER = LoggerFactory.getLogger(AIDocumentsSet.class);

	public static AIDocumentsSet fromMap(Map<String, AIDocumentReferenceItem> docsMap) {
		AIDocumentsSet results = new AIDocumentsSet();
		docsMap.values().forEach(x -> results.getDocumentItems().add(x));
		results.recalculateSize();
		return results;
	}

	/**
	 * Adds the documents of the set to the map, by their code: a document already in
	 * the map gets the fragments it does not hold yet (see
	 * {@link AIDocumentReferenceItem#containsFragment(AIDocumentFragment)}). The map
	 * holds copies: the set is left as it is.
	 */
	public static void joinMap(AIDocumentsSet result, Map<String, AIDocumentReferenceItem> docsMap) {
		result.documentItems.forEach(doc -> {
			final AIDocumentReferenceItem inMap = docsMap.get(doc.getCode());
			if (inMap == null) {
				docsMap.put(doc.getCode(), doc.copy());
			} else {
				for (AIDocumentFragment fragment : doc.getFragments()) {
					if (!inMap.containsFragment(fragment)) {
						inMap.getFragments().add(fragment.copy());
					}
				}
				inMap.recalculateSize();
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
		// each fragment once, by its identity (see AIDocumentFragment#identity())
		final Map<String, Boolean> alreadyInserted = new HashMap<String, Boolean>();
		documentItems.forEach(x -> {
			x.getFragments().forEach(y -> {
				if (!alreadyInserted.containsKey(y.identity())) {
					fragments.add(y);
					alreadyInserted.put(y.identity(), true);
				}
			});
		});
		fragments.forEach(y -> {
			if (y.toAIDocument() != null)
				documents.add(y.toAIDocument());
		});
		return documents;
	}

	/**
	 * The set of the given chunks: grouped by their document's code, each chunk once
	 * (see {@link AIDocumentFragment#identity()}).
	 */
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
			data.get(fragment.getCode()).addFragmentIfAbsent(fragment);
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