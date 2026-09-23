/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.text.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * JSON Ingestion Handler for processing JSON files into documents.
 * This service handles the ingestion of JSON content, parsing it into Document objects
 * that can be indexed and processed by the Gebo system.
 */
@Service
public class JsonIngestionHandler extends GAbstractConfiguredHandler {

	/**
	 * Constructor for JsonIngestionHandler.
	 * 
	 * @param dao the configuration data access object
	 */
	public JsonIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	/**
	 * Fallback method for reading a file when standard processing fails.
	 * Reads the entire file as a single document.
	 * 
	 * @param file the path to the file to read
	 * @param metadata metadata to associate with the document
	 * @return a list containing a single document with the file content
	 * @throws IOException if an I/O error occurs
	 */
	private List<Document> fallBackRead(Path file, Map<String, Object> metadata) throws IOException {
		if (!Files.exists(file, LinkOption.NOFOLLOW_LINKS)) {
			LOGGER.error("Non existent file:" + file.toAbsolutePath());
			return new ArrayList<Document>();
		}
		try (InputStream is = Files.newInputStream(file, StandardOpenOption.READ)) {
			ByteArrayOutputStream bos = readToBos(is);
			return List.of(new Document(bos.toString("UTF-8"), metadata));
		}
	}

	/**
	 * Utility method to read an InputStream into a ByteArrayOutputStream.
	 * 
	 * @param is the input stream to read
	 * @return a ByteArrayOutputStream containing the data
	 * @throws IOException if an I/O error occurs
	 */
	private ByteArrayOutputStream readToBos(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte buffer[] = new byte[4096];
		int nRead = -1;
		while ((nRead = is.read(buffer)) > 0) {
			bos.write(buffer, 0, nRead);
		}
		bos.flush();
		return bos;
	}

	/**
	 * Fallback method for reading an InputStream when standard processing fails.
	 * Reads the entire input stream as a single document.
	 * 
	 * @param is the input stream to read
	 * @param metadata metadata to associate with the document
	 * @return a list containing a single document with the stream content
	 * @throws IOException if an I/O error occurs
	 */
	private List<Document> fallBackRead(InputStream is, Map<String, Object> metadata) throws IOException {
		ByteArrayOutputStream bos = readToBos(is);
		return List.of(new Document(bos.toString("UTF-8"), metadata));
	}

	/**
	 * Enriches a list of documents with additional metadata.
	 * Creates new Document instances with merged metadata.
	 * 
	 * @param in the list of documents to enrich
	 * @param metadata the metadata to add to each document
	 * @return a new list of documents with enriched metadata
	 */
	private List<Document> enrichWithMeta(List<Document> in, Map<String, Object> metadata) {
		List<Document> docs = new ArrayList<Document>();
		if (in != null) {
			for (Document document : in) {
				Map<String, Object> meta = document.getMetadata();
				Map<String, Object> newMeta = new HashMap<String, Object>();
				if (meta != null) {
					newMeta.putAll(meta);
				}
				if (metadata != null) {
					newMeta.putAll(metadata);
				}
				docs.add(new Document(document.getId(), document.getText(), metadata));
			}
		}

		return docs;
	}

	/**
	 * Processes JSON content from an input stream into a stream of Document objects.
	 * Uses SpringAI's JsonReader for parsing, with fallback handling if parsing fails.
	 * 
	 * @param reference the document reference being processed
	 * @param is the input stream containing JSON data
	 * @param metadata metadata to associate with the documents
	 * @return a stream of Document objects
	 * @throws GeboIngestionException if ingestion fails
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		enrichMetaData(reference, metadata);
		ByteArrayOutputStream fallbackCopy = readToBos(is);
		ByteArrayInputStream bis = new ByteArrayInputStream(fallbackCopy.toByteArray());
		try {

			JsonReader reader = new JsonReader(new InputStreamResource(bis));
			List<Document> documents = reader.get();
			documents = enrichWithMeta(documents, metadata);
			if (documents == null) {
				LOGGER.error("No JSON documents generated for:" + reference.getCode());
				documents = new ArrayList<Document>();
			}
			return documents.stream();
		} catch (Throwable th) {
			LOGGER.error("Falling back to direct content return JSON", th);
			List<Document> documents = fallBackRead(bis, metadata);
			return documents.stream();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
		}

	}

	/**
	 * Returns the unique code identifier for this handler.
	 * 
	 * @return the handler code
	 */
	@Override
	public String getCode() {

		return "JsonIntestionHandler";
	}

	/**
	 * Handles JSON content as a text-only document.
	 * Uses the internal text file format method with JSON content type.
	 * 
	 * @param reference the document reference being processed
	 * @param is the input stream containing JSON data
	 * @param manageMetainfo metadata to associate with the document
	 * @return a GeboDocument with the processed content
	 * @throws GeboIngestionException if ingestion fails
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {

		return internalTextFileFormatTextOnlyContent(reference, "application/json", is, manageMetainfo);
	}
}