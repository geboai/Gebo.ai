/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.model;

import java.util.Map;

import lombok.Data;

/**
 * AI generated comments
 *
 * Represents the metadata of an extracted document within the system. This
 * class holds various properties related to the document's metadata such as
 * code, extension, original URL, project codes, content type, and more.
 */
@Data
public class ExtractedDocumentMetaData {
	// Final fields representing the metadata attributes of a document
	final String code, extension, originalUrl, parentProjectCode, rootKnowledgebaseCode, contentType, name,
			metaInfoHeader, referenceType;
	final Integer tokenLength, bytesLength;

	/**
	 * Constructs an instance of {@code ExtractedDocumentMetaData} with the
	 * specified attributes.
	 *
	 * @param code                  the code identifier of the document
	 * @param extension             the file extension of the document
	 * @param originalUrl           the original URL of the document
	 * @param parentProjectCode     the code of the parent project associated with
	 *                              the document
	 * @param rootKnowledgebaseCode the code of the root knowledgebase
	 * @param contentType           the MIME type of the document content
	 * @param name                  the name of the document
	 * @param tokenLength           the length in tokens of the document content
	 * @param bytesLength           the size of the document in bytes
	 * @param metaInfoHeader        additional metadata header information
	 */
	private ExtractedDocumentMetaData(String code, String extension, String originalUrl, String parentProjectCode,
			String rootKnowledgebaseCode, String contentType, String name, Integer tokenLength, Integer bytesLength,
			String metaInfoHeader, String referenceType) {
		this.code = code;
		this.contentType = contentType;
		this.extension = extension;
		this.name = name;
		this.originalUrl = originalUrl;
		this.parentProjectCode = parentProjectCode;
		this.rootKnowledgebaseCode = rootKnowledgebaseCode;
		this.tokenLength = tokenLength;
		this.bytesLength = bytesLength;
		this.metaInfoHeader = metaInfoHeader;
		this.referenceType = referenceType;
	}

	/**
	 * Retrieves a string value associated with the specified constant from the
	 * metadata map.
	 *
	 * @param constant the key to look up in the metadata
	 * @param metadata the map containing metadata values
	 * @return the value associated with the constant, or null if not found or
	 *         metadata is null
	 */
	private static String getValue(String constant, Map<String, Object> metadata) {
		return metadata != null && metadata.containsKey(constant) ? metadata.get(constant).toString() : null;
	}

	/**
	 * Retrieves a numeric value associated with the specified constant from the
	 * metadata map.
	 *
	 * @param constant the key to look up in the metadata
	 * @param metadata the map containing metadata values
	 * @return the numeric value associated with the constant, or null if not found,
	 *         not a number, or metadata is null
	 */
	private static Integer getNumericValue(String constant, Map<String, Object> metadata) {
		Object value = metadata != null && metadata.containsKey(constant) ? metadata.get(constant) : null;
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		return null;
	}

	/**
	 * Creates an instance of {@code ExtractedDocumentMetaData} based on the
	 * provided metadata map.
	 *
	 * @param metadata a map containing metadata attributes for document creation
	 * @return an instance of {@code ExtractedDocumentMetaData} or null if the
	 *         metadata is null
	 */
	public static ExtractedDocumentMetaData of(Map<String, Object> metadata) {
		if (metadata == null)
			return null;
		return new ExtractedDocumentMetaData(getValue(DocumentMetaInfos.CONTENT_CODE, metadata),
				getValue(DocumentMetaInfos.CONTENT_EXTENSION, metadata),
				getValue(DocumentMetaInfos.CONTENT_ORIGINAL_URL, metadata),
				getValue(DocumentMetaInfos.PROJECT_CODE, metadata),
				getValue(DocumentMetaInfos.KNOWLEDGEBASE_CODE, metadata),
				getValue(DocumentMetaInfos.CONTENT_TYPE, metadata),
				getValue(DocumentMetaInfos.GEBO_FILE_NAME, metadata),
				getNumericValue(DocumentMetaInfos.GEBO_TOKEN_LENGTH, metadata),
				getNumericValue(DocumentMetaInfos.GEBO_BYTES_LENGTH, metadata),
				getValue(DocumentMetaInfos.GEBO_EMBEDDING_METADATA, metadata),
				getValue(DocumentMetaInfos.GEBO_REFERENCE_TYPE, metadata));
	}

	/**
	 * Determines if the document is enriched with additional metadata header
	 * information.
	 *
	 * @return true if the meta-info header is not null and contains characters,
	 *         false otherwise
	 */
	public boolean isEnrichedWithMetaInfo() {
		return metaInfoHeader != null && metaInfoHeader.trim().length() > 0;
	}
}