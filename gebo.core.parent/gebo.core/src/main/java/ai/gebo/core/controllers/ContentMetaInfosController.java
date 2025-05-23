/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.utils.DataPage;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Controller for managing content meta-information related to documents.
 * Provides various endpoints to retrieve document metadata, content objects, and perform searches.
 * AI generated comments
 */
@RestController
@RequestMapping("api/users/ContentMetaInfosController")
public class ContentMetaInfosController {
    // Injects the repository to handle document references
	@Autowired
	DocumentReferenceRepository repository;

	/**
	 * Class to hold metadata information about content.
	 */
	public static class ContentMetaInfo {
        // Indicates if the content exists
		public boolean exists = false;
        // Code representing the document
		public String code = null;
        // File extension of the document
		public String extension = null;
        // Content type of the document
		public String contentType = null;
        // Name of the file
		public String fileName = null;
        // Code of the parent project associated with the document
		public String parentProjectCode = null;
        // Root knowledge base associated with the document
		public String rootKnowledgeBase = null;
        // Identifier for the module
		public String moduleId = null;
        // URL to access the document
		public String url = null;

	}

	// Default constructor for the content meta info controller
	public ContentMetaInfosController() {

	}

	/**
	 * Gets metadata information for a document based on its code.
	 *
	 * @param code The code of the document.
	 * @return ContentMetaInfo The metadata information for the document.
	 */
	@GetMapping(value = "getContentMetaInfos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ContentMetaInfo getContentMetaInfos(@RequestParam("code") String code) {
		ContentMetaInfo meta = new ContentMetaInfo();
		Optional<GDocumentReference> content = repository.findById(code);
		meta.exists = content.isPresent(); // Check if content exists
		if (meta.exists) {
			GDocumentReference document = content.get();
			meta.code = document.getCode();
			meta.contentType = document.getContentType();
			meta.extension = document.getExtension();
			meta.fileName = document.getName();
			meta.parentProjectCode = document.getParentProjectCode();
			meta.rootKnowledgeBase = document.getRootKnowledgebaseCode();
			meta.url = document.getUri();
			meta.moduleId = document.getMessagingModuleId();
		}
		return meta;
	}

	/**
	 * Class to represent a content object with its content.
	 */
	public static class ContentObject {
		public String content = null;

        // Default constructor
		public ContentObject() {
		}

		// Constructor to initialize the content
		public ContentObject(String c) {
			this.content = c;
		}
	}

	/**
	 * Gets the content object for a document based on its code.
	 *
	 * @param code The code of the document.
	 * @return ContentObject The content object of the document.
	 * @throws FileNotFoundException if the file cannot be found.
	 * @throws IOException           if an error occurs while reading the file.
	 */
	@GetMapping(value = "getContentObject", produces = MediaType.APPLICATION_JSON_VALUE)
	public ContentObject getContentObject(@RequestParam("code") String code) throws FileNotFoundException, IOException {

		Optional<GDocumentReference> content = repository.findById(code);

		if (content.isPresent()) {
			GDocumentReference document = content.get();

			// Check if the document is nested in an archive
			if (document.getNestedInArchive() != null && document.getNestedInArchive()) {
				String absolutearchiveFile = document.getAbsoluteArchivePath();
				File file = new File(absolutearchiveFile);
				if (!file.exists()) {
					return null;
				}
				// Read content from the archive
				try (ZipFile zipFile = new ZipFile(file)) {
					ZipEntry entry = zipFile.getEntry(document.getArchiveInternalPath());
					if (entry == null) {
						return null;
					}

					try (InputStream is = zipFile.getInputStream(entry)) {
						return copyOutput(is);
					}
				}
			} else if (document.getAbsolutePath() != null) {
				// Check if the document has an absolute path
				File file = new File(document.getAbsolutePath());
				if (!file.exists()) {
					return null;
				}
				try (FileInputStream fis = new FileInputStream(file)) {
					return copyOutput(fis);
				}
			} else if (document.getArtificiallyGeneratedContent() != null) {
				// Check if the document has artificially generated content
				return new ContentObject(document.getArtificiallyGeneratedContent());
			}

		}
		return null;
	}

	/**
	 * Copies content from an InputStream and returns it wrapped in a ContentObject.
	 *
	 * @param is InputStream to read the content from.
	 * @return ContentObject The content object containing the read content.
	 * @throws IOException if an error occurs during reading.
	 */
	private ContentObject copyOutput(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte buffer[] = new byte[8192];
		int nRead = 0;
		while ((nRead = is.read(buffer)) > 0) {
			bos.write(buffer, 0, nRead);
		}
		return new ContentObject(bos.toString("UTF-8"));
	}

	/**
	 * Finds and returns views of document references by their codes.
	 * 
	 * @param codes List of document reference codes to search for.
	 * @return List of DocumentReferenceView for the specified codes.
	 */
	@PostMapping(value = "findDocumentReferenceViewByCode", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<DocumentReferenceView> findDocumentReferenceViewByCode(@RequestBody List<String> codes) {
		List<DocumentReferenceView> view = this.repository.findDocumentReferenceViewByCodeIn(codes);
		return view;
	}

	/**
	 * Class representing parameters for searching document by name.
	 */
	public static class SearchDocumentByNameParam {
		@NotNull
		public String name = null; // Document name to search for
		@NotNull
		public List<String> knowledgeBaseCodes = null; // List of knowledge base codes

	}

	/**
	 * Class extending SearchDocumentByNameParam to include pagination parameters.
	 */
	public static class SearchDocumentByNamePagedParam extends SearchDocumentByNameParam {
		@NotNull
		public DataPage page = null; // Pagination data

	}

	/**
	 * Paged search for document references by document name.
	 * 
	 * @param param Parameters including name, knowledge base codes, and pagination.
	 * @return Page of DocumentReferenceView that match the search criteria.
	 */
	@PostMapping(value = "searchByDocumentNamePaged", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<DocumentReferenceView> searchByDocumentNamePaged(
			@NotNull @Valid @RequestBody SearchDocumentByNamePagedParam param) {
		Pageable pageable = param.page.toPageable();
		return repository.findDocumentReferenceViewByRootKnowledgebaseCodeInAndNameContains(param.knowledgeBaseCodes,
				param.name, pageable);
	}

	/**
	 * Search for document references by document name.
	 * 
	 * @param param Parameters including name and knowledge base codes.
	 * @return List of DocumentReferenceView matching the search criteria.
	 */
	@PostMapping(value = "searchByDocumentName", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<DocumentReferenceView> searchByDocumentName(
			@NotNull @Valid @RequestBody SearchDocumentByNameParam param) {

		return repository.findDocumentReferenceViewByRootKnowledgebaseCodeInAndNameContains(param.knowledgeBaseCodes,
				param.name);
	}
}