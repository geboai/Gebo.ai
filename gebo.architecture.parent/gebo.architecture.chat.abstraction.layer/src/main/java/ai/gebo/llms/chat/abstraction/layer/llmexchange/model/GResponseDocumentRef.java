/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import lombok.Data;

/**
 * Represents a reference to a document along with its metadata and internal
 * references. Implements Serializable interface for object serialization. AI
 * generated comments
 */
@Data
public class GResponseDocumentRef implements Serializable {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Represents an internal reference to a document, including its ID and page.
	 * Implements Serializable interface.
	 */
	@Data
	public static class DocInternalRef implements Serializable {
		private String id = null; // ID of the document reference
		private String page = null; // Page of the document reference

	}

	private ReferenceRefType referenceType = null;
	private String uuid = null; // Unique identifier for the document
	private String documentCode = null; // Code that identifies the document
	private String description = null; // Description of the document
	private String contentType = null; // Type of the document content
	private String extension = null; // File extension of the document
	private String knowledgeBaseCode = null; // Code associated with the knowledge base
	private String projectCode = null; // Code associated with the project
	private String geboTreatAs = null; // Treatment type of the document in the system
	private String geboFileTypeDescription = null; // Description of the file type
	private String geboFileTypeId = null; // File type identifier
	private String name = null; // Name of the document
	private boolean knowledgeBaseDocument;
	private SearchResult nestedSearchResult = null;
	private long NTokensRelevant = 0l; // Number of relevant tokens in the document
	private long NTotalContentTokens = 0l; // Total number of content tokens in the document
	private double loadPercentage = 0.0; // Load percentage of tokens relevant to total content
	private long NBytesRelevant = 0l; // Number of relevant bytes in the document
	private List<DocInternalRef> references = new ArrayList<GResponseDocumentRef.DocInternalRef>(); // List of document
																									// internal
																									// references

	/**
	 * Retrieves a metadata value from a map using a constant key.
	 * 
	 * @param metadata the metadata map
	 * @param constant the key to retrieve the metadata
	 * @return the metadata value as a string, or null if not available
	 */
	private String get(Map<String, Object> metadata, String constant) {
		return metadata != null && metadata.containsKey(constant) ? metadata.get(constant).toString() : null;
	}

	public GResponseDocumentRef(GDocumentReference document) {
		Map<String, Object> metadata = document.getCustomMetaInfos();
		this.documentCode = get(metadata, DocumentMetaInfos.CONTENT_CODE);
		this.description = get(metadata, DocumentMetaInfos.CONTENT_DESCRIPTION);
		this.contentType = get(metadata, DocumentMetaInfos.CONTENT_TYPE);
		this.extension = get(metadata, DocumentMetaInfos.CONTENT_EXTENSION);
		this.knowledgeBaseCode = get(metadata, DocumentMetaInfos.KNOWLEDGEBASE_CODE);
		this.projectCode = get(metadata, DocumentMetaInfos.PROJECT_CODE);
		this.geboTreatAs = get(metadata, DocumentMetaInfos.GEBO_FILE_TREAT_AS);
		this.geboFileTypeDescription = get(metadata, DocumentMetaInfos.GEBO_FILE_TYPE_DESCRIPTION);
		this.geboFileTypeId = get(metadata, DocumentMetaInfos.GEBO_FILE_TYPE_ID);
		this.name = get(metadata, DocumentMetaInfos.GEBO_FILE_NAME);
		this.documentCode = document.getCode();
		this.name = document.getName();
		this.description = document.getDescription();
		this.extension = document.getExtension();
		this.contentType = document.getContentType();
		String referenceType = get(metadata, DocumentMetaInfos.GEBO_REFERENCE_TYPE);
		this.knowledgeBaseDocument = true;
		if (referenceType != null) {
			try {
				this.referenceType = ReferenceRefType.valueOf(referenceType);
			} catch (Throwable t) {
			}
		}
	}

	public static GResponseDocumentRef of(IGComponentOriginatedDocument document) {
		if (document instanceof GDocumentReference reference) {
			return new GResponseDocumentRef(reference);
		}
		if (document instanceof SearchResult reference) {
			return new GResponseDocumentRef(reference);
		}
		return null;
	}

	public GResponseDocumentRef(SearchResult nestedSearchResult) {
		this.nestedSearchResult = nestedSearchResult;
		this.description = nestedSearchResult.getDescriptiveText();
		this.documentCode = nestedSearchResult.getCode();
		if (nestedSearchResult.getNavigationReference() != null
				&& nestedSearchResult.getNavigationReference().path != null
				&& nestedSearchResult.getNavigationReference().path.name != null) {
			this.name = nestedSearchResult.getNavigationReference().path.name;
		} else if (nestedSearchResult.getResultReference() != null
				&& nestedSearchResult.getResultReference().getName() != null) {
			this.name = nestedSearchResult.getResultReference().getName();
		}
	}

	/**
	 * Constructs a GResponseDocumentRef from a Document object. Initializes
	 * document metadata fields.
	 * 
	 * @param document the Document object to reference
	 */
	public GResponseDocumentRef(Document document) {
		Map<String, Object> metadata = document.getMetadata();
		this.documentCode = get(metadata, DocumentMetaInfos.CONTENT_CODE);
		this.description = get(metadata, DocumentMetaInfos.CONTENT_DESCRIPTION);
		this.contentType = get(metadata, DocumentMetaInfos.CONTENT_TYPE);
		this.extension = get(metadata, DocumentMetaInfos.CONTENT_EXTENSION);
		this.knowledgeBaseCode = get(metadata, DocumentMetaInfos.KNOWLEDGEBASE_CODE);
		this.projectCode = get(metadata, DocumentMetaInfos.PROJECT_CODE);
		this.geboTreatAs = get(metadata, DocumentMetaInfos.GEBO_FILE_TREAT_AS);
		this.geboFileTypeDescription = get(metadata, DocumentMetaInfos.GEBO_FILE_TYPE_DESCRIPTION);
		this.geboFileTypeId = get(metadata, DocumentMetaInfos.GEBO_FILE_TYPE_ID);
		this.name = get(metadata, DocumentMetaInfos.GEBO_FILE_NAME);
		String searchResultJSON = get(metadata, DocumentMetaInfos.GEBO_EXTERNAL_SEARCH_RESULT_JSON);
		if (searchResultJSON != null && searchResultJSON.trim().length() > 0) {
			try {
				SearchResult sr = objectMapper.readValue(searchResultJSON, SearchResult.class);
				if (sr != null) {
					this.nestedSearchResult = sr;
					if (this.name == null) {
						this.name = sr.getResultReference() != null ? sr.getResultReference().getName()
								: sr.getNavigationReference() != null && sr.getNavigationReference().path != null
										? sr.getNavigationReference().path.name
										: null;
					}
				}
			} catch (Throwable th) {
			}
		}
		this.uuid = document.getId();
		String referenceType = get(metadata, DocumentMetaInfos.GEBO_REFERENCE_TYPE);
		if (referenceType != null) {
			try {
				this.referenceType = ReferenceRefType.valueOf(referenceType);
			} catch (Throwable t) {
			}
		}
	}

	/**
	 * Default constructor for GResponseDocumentRef.
	 */
	public GResponseDocumentRef() {

	}

	/**
	 * Gets the short code derived from the document code. Extracts the substring
	 * after the last '/' character.
	 * 
	 * @return the short code or null if not available
	 */
	public String getShortCode() {
		String shortCode = null;
		if (documentCode != null) {
			int idx = documentCode.lastIndexOf("/");
			if (idx < documentCode.length() - 1) {
				shortCode = documentCode.substring(idx + 1);
			} else
				shortCode = documentCode.substring(documentCode.lastIndexOf("/"));
		}
		return shortCode;
	}

	/**
	 * Creates a list of GResponseDocumentRef objects from extracted documents.
	 * Populates each document reference with token and internal reference
	 * information.
	 * 
	 * @param extractedDocuments the result containing extracted document data
	 * @return a list of GResponseDocumentRef objects
	 */
	public static List<GResponseDocumentRef> from(AIDocumentsSet extractedDocuments) {
		final List<GResponseDocumentRef> out = new ArrayList<GResponseDocumentRef>();
		extractedDocuments.getDocumentItems().forEach(x -> {
			if (!x.getFragments().isEmpty()) {
				GResponseDocumentRef documentRef = new GResponseDocumentRef(x.getFragments().get(0).toAIDocument());
				out.add(documentRef);
				documentRef.setNTokensRelevant(x.getTokensSize());
				documentRef.setNTotalContentTokens(x.getTotalFileNTokens());
				if (documentRef.getNTotalContentTokens() > 0) {
					double percent = 100.0 * ((double) x.getTokensSize()) / ((double) x.getTotalFileNTokens());
					if (percent > 100.0)
						percent = 100.0;
					documentRef.loadPercentage = percent;
				}
				x.getFragments().forEach(y -> {
					DocInternalRef internalRef = new DocInternalRef();
					internalRef.setId(y.toAIDocument().getId());
					Object page = y.toAIDocument().getMetadata() != null
							? y.toAIDocument().getMetadata().get(DocumentMetaInfos.CONTENT_PAGE)
							: null;
					if (page != null) {
						internalRef.setPage(page.toString());
					}
					documentRef.getReferences().add(internalRef);
				});
			}
		});
		return out;
	}

}