/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.system.ingestion.IGAIDocumentMetaDataEnricher;
import ai.gebo.system.ingestion.config.GeboContentReadingConfig;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.MetaDataHeaderInfos;

/**
 * AI generated comments
 * 
 * GAIDocumentCatalogingEnricherImpl is responsible for enriching documents with metadata
 * such as knowledge base, project, and category information. This class implements 
 * the IGAIDocumentMetaDataEnricher interface to provide document enrichment capabilities.
 */
@Component
@Scope("singleton")
public class GAIDocumentCatalogingEnricherImpl implements IGAIDocumentMetaDataEnricher {
	static final Logger LOGGER = LoggerFactory.getLogger(GAIDocumentCatalogingEnricherImpl.class);
	static final String METADATA_BLOCK_START = "[METADATA]";
	static final String CONTENT_BLOCK_START = "[CONTENT]";
	static final String METADATA_BLOCK_END = "[/METADATA]";
	static final String CONTENT_BLOCK_END = "[/CONTENT]";
	@Autowired
	protected GeboContentReadingConfig contentsReadingConfig;
	@Autowired
	protected IGPersistentObjectManager persistentObjectManager;

	/** Maps file extensions to their corresponding IngestionFileType */
	final Map<String, IngestionFileType> fileTypeRetrieve = new HashMap<String, IngestionFileType>();
	/** Maps content types to their corresponding IngestionFileType */
	final Map<String, IngestionFileType> contentTypeRetrieve = new HashMap<String, IngestionFileType>();

	/**
	 * Initializes the file type maps based on the content reading configuration.
	 * Maps file extensions and content types to their respective IngestionFileType.
	 */
	protected void fileTypesFillMap() {
		if (contentsReadingConfig != null) {
			contentsReadingConfig.getHandlers().forEach(x -> {
				if (x.getFileTypes() != null) {
					x.getFileTypes().forEach(ft -> {
						if (ft.getExtensions() != null) {
							ft.getExtensions().forEach(ext -> {
								fileTypeRetrieve.put(ext, ft);
							});
						}
						if (ft.getContentTypes() != null) {
							ft.getContentTypes().forEach(ct -> {
								contentTypeRetrieve.put(ct, ft);
							});
						}
					});
				}
			});
		}
	}

	/**
	 * Retrieves the IngestionFileType for a given document reference based on its extension.
	 * 
	 * @param reference The document reference to find the file type for
	 * @return The corresponding IngestionFileType for the document
	 */
	protected IngestionFileType getFileType(GDocumentReference reference) {
		if (fileTypeRetrieve.isEmpty()) {
			this.fileTypesFillMap();
		}
		return fileTypeRetrieve.get(reference.getExtension());
	}

	/**
	 * Inner class that holds the document and its related objects like knowledge base,
	 * project, and endpoint.
	 */
	static class DocumentEnvironment {
		GDocumentReference document = null;
		GKnowledgeBase knowledgeBase = null;
		GProject project = null;
		GProjectEndpoint endpoint = null;
	}

	/**
	 * Inner class that represents a document in process of being enriched with its
	 * metadata and environment information.
	 */
	static class EnrichedProcessAIDocument {
		ExtractedDocumentMetaData metaData = null;
		Document document = null;
		DocumentEnvironment environment = null;

	}

	/**
	 * Inner class that holds the results of checking which documents need to be enriched.
	 * Categorizes documents as uncatalogued, already enriched, or to be enriched.
	 */
	static class EnrichedCheckResult {
		List<Document> uncataloghed = new ArrayList<Document>();
		List<Document> alreadyEnriched = new ArrayList<Document>();
		Map<String, List<EnrichedProcessAIDocument>> toBeEnriched = new HashMap<String, List<EnrichedProcessAIDocument>>();
	}

	/**
	 * Checks the enriching status of a list of documents and categorizes them based on
	 * whether they need enrichment or are already processed.
	 * 
	 * @param documents The list of documents to check
	 * @return An EnrichedCheckResult containing the categorized documents
	 * @throws GeboPersistenceException If there is an error retrieving data from persistence
	 */
	private EnrichedCheckResult checkEnrichingStatus(List<Document> documents) throws GeboPersistenceException {
		final EnrichedCheckResult data = new EnrichedCheckResult();
		if (documents != null) {
			documents.forEach(x -> {
				ExtractedDocumentMetaData extracted = ExtractedDocumentMetaData.of(x.getMetadata());

				if (extracted == null || extracted.getCode() == null) {
					data.uncataloghed.add(x);
				} else if (extracted.isEnrichedWithMetaInfo()) {
					data.alreadyEnriched.add(x);
				} else {
					EnrichedProcessAIDocument enriched = new EnrichedProcessAIDocument();
					enriched.document = x;
					enriched.metaData = extracted;
					if (!data.toBeEnriched.containsKey(extracted.getCode())) {
						data.toBeEnriched.put(extracted.getCode(),
								new ArrayList<GAIDocumentCatalogingEnricherImpl.EnrichedProcessAIDocument>());
					}
					data.toBeEnriched.get(extracted.getCode()).add(enriched);

				}
			});
			if (!data.toBeEnriched.isEmpty()) {
				List<GDocumentReference> bycodes = persistentObjectManager.findAllByIds(GDocumentReference.class,
						data.toBeEnriched.keySet());
				for (GDocumentReference x : bycodes) {
					List<EnrichedProcessAIDocument> list = data.toBeEnriched.get(x.getCode());
					GKnowledgeBase kb = null;
					GProject pj = null;
					GProjectEndpoint ep = null;
					if (x.getRootKnowledgebaseCode() != null) {
						kb = persistentObjectManager.findById(GKnowledgeBase.class, x.getRootKnowledgebaseCode());
					}
					if (x.getParentProjectCode() != null) {
						pj = persistentObjectManager.findById(GProject.class, x.getParentProjectCode());
					}
					if (x.getProjectEndpointReference() != null) {
						ep = persistentObjectManager.findByReference(x.getProjectEndpointReference(),
								GProjectEndpoint.class);
					}

					if (list != null) {
						for (EnrichedProcessAIDocument enriched : list) {
							enriched.environment = new DocumentEnvironment();
							enriched.environment.document = x;
							enriched.environment.knowledgeBase = kb;
							enriched.environment.project = pj;
							enriched.environment.endpoint = ep;
						}
					}
				}

			}
		}
		return data;
	}

	/**
	 * Enriches a list of documents with cataloging information by fetching related metadata
	 * from the persistence layer and applying it to the documents.
	 * 
	 * @param documents The list of documents to enrich
	 * @return A list of enriched documents
	 */
	@Override
	public List<Document> enrichCatalogingInformations(List<Document> documents) {
		List<Document> out = new ArrayList<Document>();
		try {
			EnrichedCheckResult data = checkEnrichingStatus(documents);
			out.addAll(data.uncataloghed);
			out.addAll(data.alreadyEnriched);
			Collection<List<EnrichedProcessAIDocument>> toenrich = data.toBeEnriched.values();
			if (!toenrich.isEmpty()) {
				for (List<EnrichedProcessAIDocument> toBeEnriched : toenrich) {
					EnrichedProcessAIDocument firstDocument = toBeEnriched.get(0);
					if (firstDocument.document != null && firstDocument.environment != null
							&& firstDocument.environment.endpoint != null
							&& firstDocument.environment.knowledgeBase != null
							&& firstDocument.environment.project != null) {
						MetaDataHeaderInfos header = createMetaDataHeader(firstDocument.environment.document,
								firstDocument.environment.knowledgeBase, firstDocument.environment.project,
								firstDocument.environment.endpoint);
						for (EnrichedProcessAIDocument toEnrich : toBeEnriched) {
							Document enrichedDocument = enrich(toEnrich.document, header);
							out.add(enrichedDocument);
						}
					} else {
						LOGGER.warn("For document:" + firstDocument.document.getId()
								+ " full references and metainfo are not reachable");
						out.addAll(toBeEnriched.stream().map(x -> x.document).toList());
					}

				}
			}
			if (out.size() != documents.size()) {
				LOGGER.error("The enriching phase is losing data, call assistence\n document codes:"
						+ data.toBeEnriched.keySet());
				return documents;
			}
		} catch (GeboPersistenceException e) {
			LOGGER.error("Persistence management exception", e);
			throw new RuntimeException("Persistence management exception", e);
		} finally {
		}

		return out;
	}

	/**
	 * Enriches a document with metadata information by incorporating the provided metadata
	 * header information into the document's content and metadata.
	 * 
	 * @param document The document to enrich
	 * @param infos The metadata header information to add
	 * @return The enriched document
	 */
	public Document enrich(Document document, MetaDataHeaderInfos infos) {
		StringBuffer newContent = new StringBuffer();
		if (document.getMetadata() != null && document.getMetadata().containsKey(DocumentMetaInfos.TITLE)) {
			newContent.append("TITLE:" + document.getMetadata().get(DocumentMetaInfos.TITLE).toString() + "\n");
		}
		if (document.getMetadata() != null && document.getMetadata().containsKey(DocumentMetaInfos.SUBTITLE)) {
			newContent.append("SUBTITLE:" + document.getMetadata().get(DocumentMetaInfos.SUBTITLE).toString() + "\n");
		}
		newContent.append(infos.metaDataHeader);
		newContent.append("\n");
		// newContent.append(infos.startLine);

		// newContent.append(infos.startBlock);
		// newContent.append(CONTENT_BLOCK_START);
		// newContent.append(infos.endBlock);
		newContent.append("\n");
		newContent.append(document.getText());
		newContent.append("\n");
		// newContent.append(infos.startLine);
		// newContent.append(infos.startBlock);
		// newContent.append("\n");
		// newContent.append(CONTENT_BLOCK_END);
		// newContent.append("\n");
		// newContent.append(infos.endBlock);
		// newContent.append("\n");
		HashMap<String, Object> newMetadata = new HashMap<String, Object>();
		if (document.getMetadata() != null) {
			newMetadata.putAll(document.getMetadata());
		}
		if (document.getMetadata() != null && infos.metaDataHeader != null
				&& infos.metaDataHeader.trim().length() > 0) {
			newMetadata.put(DocumentMetaInfos.GEBO_EMBEDDING_METADATA, infos.metaDataHeader);
		}

		Document newDocument = new Document(document.getId(), newContent.toString(), newMetadata);
		return newDocument;
	}

	/**
	 * Creates a metadata header for enriching documents based on the provided document reference,
	 * knowledge base, project, and endpoint information.
	 * 
	 * @param reference The document reference
	 * @param knowledgeBase The knowledge base the document belongs to
	 * @param project The project the document belongs to
	 * @param endpoint The project endpoint
	 * @return A MetaDataHeaderInfos object containing the formatted metadata header
	 */
	@Override
	public MetaDataHeaderInfos createMetaDataHeader(GDocumentReference reference, GKnowledgeBase knowledgeBase,
			GProject project, GProjectEndpoint endpoint) {
		MetaDataHeaderInfos infos = new MetaDataHeaderInfos();
		infos.metaDataHeader = "";
		infos.fileType = getFileType(reference);
		infos.startBlock = infos.fileType != null && infos.fileType.getCommentEscapeBegin() != null
				? infos.fileType.getCommentEscapeBegin()
				: "";
		infos.endBlock = infos.fileType != null && infos.fileType.getCommentEscapeEnd() != null
				? infos.fileType.getCommentEscapeEnd()
				: "";
		infos.startLine = infos.fileType != null && infos.fileType.getCommentRowEscape() != null
				? infos.fileType.getCommentRowEscape()
				: "";
		if (infos.fileType == null || infos.fileType.getEnrichWithCatalogInfos() == null
				|| !infos.fileType.getEnrichWithCatalogInfos()) {
			return infos;
		}

		StringBuffer catalogInfos = new StringBuffer(infos.startBlock + "\n");

		if (endpoint != null && endpoint.getCatalogingCriteria() != null
				&& endpoint.getCatalogingCriteria().trim().length() > 0) {
			catalogInfos.append(infos.startLine + "Category organization criteria: "
					+ endpoint.getCatalogingCriteria().replace("\n", " ") + "\n");
		}
		if (reference.getRelativePath() != null) {
			catalogInfos.append(infos.startLine + "Category: " + reference.getRelativePath() + "\n");
		}
		if (knowledgeBase != null) {
			catalogInfos.append(infos.startLine + "knowledge base: " + knowledgeBase.getDescription() + "\n");
		}
		if (project != null) {
			catalogInfos.append(infos.startLine + "project/item: " + project.getDescription() + "\n");
		}
		catalogInfos.append(infos.endBlock);
		StringBuffer metaData = new StringBuffer();
		metaData.append(infos.startLine);
		metaData.append(infos.startBlock);
		metaData.append(METADATA_BLOCK_START);
		metaData.append("\n");
		metaData.append(catalogInfos.toString());
		metaData.append("\n");
		metaData.append(METADATA_BLOCK_END);
		metaData.append("\n");
		metaData.append(infos.endBlock);
		metaData.append("\n");
		infos.metaDataHeader = metaData.toString();
		return infos;
	}

	/**
	 * Enriches a list of documents with cataloging information using the explicitly provided
	 * reference, knowledge base, project, and endpoint information.
	 * 
	 * @param documents The list of documents to enrich
	 * @param reference The document reference
	 * @param knowledgeBase The knowledge base
	 * @param project The project
	 * @param endpoint The project endpoint
	 * @return A list of enriched documents
	 */
	@Override
	public List<Document> enrichCatalogingInformations(List<Document> documents, GDocumentReference reference,
			GKnowledgeBase knowledgeBase, GProject project, GProjectEndpoint endpoint) {
		MetaDataHeaderInfos metaHeader = createMetaDataHeader(reference, knowledgeBase, project, endpoint);
		List<Document> enriched = new ArrayList<Document>();
		for (Document document : documents) {

			if (document.isText() && (document.getMetadata() == null
					|| !document.getMetadata().containsKey(DocumentMetaInfos.GEBO_EMBEDDING_METADATA))) {
				Document newDocument = enrich(document, metaHeader);
				enriched.add(newDocument);
			} else {
				enriched.add(document);
			}

		}
		return enriched;
	}

}