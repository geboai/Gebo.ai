/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.system.ingestion;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.system.ingestion.model.MetaDataHeaderInfos;

/**
 * AI generated comments Interface for enriching documents with metadata during
 * the ingestion process. This interface provides methods to add metadata and
 * cataloging information to documents as they are ingested into the knowledge
 * base.
 */
public interface IGAIDocumentMetaDataEnricher {
	/**
	 * Creates a metadata header for a document based on its reference and context.
	 * 
	 * @param documents     TODO
	 * @param reference     the document reference
	 * @param knowledgeBase the knowledge base containing the document
	 * @param project       the project associated with the document
	 * @param endpoint      the project endpoint where the document is accessed
	 * 
	 * @return metadata header information for the document
	 */
	public MetaDataHeaderInfos createMetaDataHeader(List<Document> documents, GDocumentReference reference,
			GKnowledgeBase knowledgeBase, GProject project, GProjectEndpoint endpoint);

	/**
	 * Enriches a list of documents with cataloging information.
	 * 
	 * @param documents the documents to be enriched
	 * @return a list of documents with added cataloging information
	 */
	public List<Document> enrichCatalogingInformations(List<Document> documents);

	/**
	 * Enriches a list of documents with cataloging information using specific
	 * context parameters.
	 * 
	 * @param documents     the documents to be enriched
	 * @param reference     the document reference
	 * @param knowledgeBase the knowledge base containing the document
	 * @param project       the project associated with the document
	 * @param endpoint      the project endpoint where the document is accessed
	 * @return a list of documents with added cataloging information
	 */
	public List<Document> enrichCatalogingInformations(List<Document> documents, GDocumentReference reference,
			GKnowledgeBase knowledgeBase, GProject project, GProjectEndpoint endpoint);
	/***********************************************************************
	 * Applies the metadata header to a document
	 * @param document
	 * @param infos
	 * @return
	 */
	public Document enrich(Document document, MetaDataHeaderInfos infos);
}