/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knlowledgebase.model.contents;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments Represents a document reference within the virtual
 * filesystem. This includes properties such as extension, content type, file
 * size, etc. The document is stored and indexed in a MongoDB database.
 */
@Document
@Data
public class GDocumentReference extends GAbstractVirtualFilesystemObject implements IGComponentOriginatedDocument {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 3645646023522604382L;

	/**
	 * Unique identifier for synchronization purposes.
	 */
	private String synchronizationUUID = null;

	/**
	 * File extension (e.g., 'pdf', 'docx'). Indexed with a hash for fast lookup.
	 */
	@HashIndexed
	private String extension = null;

	/**
	 * Content type of the file (e.g., 'text/plain', 'application/pdf'). Indexed
	 * with a hash for fast lookup.
	 */
	@HashIndexed
	private String contentType = null;

	/**
	 * Identifier for the archetype of the file. Indexed with a hash for fast
	 * lookup.
	 */
	@HashIndexed
	private String geboFileArchetypeId = null;

	/**
	 * Size of the file in bytes.
	 */
	private Long fileSize = null;

	/**
	 * Flag indicating whether the content type is unmanaged.
	 */
	private Boolean unmanagedContentType = null;

	/**
	 * Type of the reference (FILE or WEB).
	 */
	private ReferenceType referenceType = null;

	/**
	 * Flag indicating whether vectorization of the content was skipped.
	 */
	private Boolean skippedVectorizationContent = null;

	/**
	 * Artificially generated content of the document.
	 */
	private String artificiallyGeneratedContent = null;
	@NotNull
	private GeboComponentInfo originComponent = null;
	@HashIndexed
	private List<GDocumentAttributeValue> attributesValues = null;
	@HashIndexed
	private String langCode = null;
	@HashIndexed
	private String translationOfDocumentCode = null;
	@HashIndexed
	private List<String> categoryCodes = null;
	@HashIndexed
	private LocalDate publishedDate = null;
	@HashIndexed
	private String author = null;

}