/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.document.Document;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.document.model.GeboTextDocumentFragment;
import ai.gebo.llms.abstraction.layer.services.IGDocumentTranslator;

/**
 * AI generated comments
 * Implementation of the IGDocumentTranslator interface, providing methods
 * to convert GeboDocument objects to AI Documents, plain text, and from chat
 * responses.
 */
@Service
public class GDocumentTranslatorImpl implements IGDocumentTranslator {

	/**
	 * Constructs a new instance of GDocumentTranslatorImpl.
	 */
	public GDocumentTranslatorImpl() {

	}

	/**
	 * Converts a GeboDocument to a list of AI Document objects.
	 *
	 * @param document the GeboDocument to be converted
	 * @return a list containing the converted AI Document
	 */
	@Override
	public List<Document> toAi(GeboDocument document) {
		StringBuffer text = new StringBuffer();
		for (GeboTextDocumentFragment t : document.getTexts()) {
			text.append(t.getContent()); // Append content of each text fragment
		}
		Document doc = new Document(text.toString(), document.getCustomMetaData());
		return List.of(doc); // Return the created Document in a list
	}

	/**
	 * Converts a GeboDocument to a plain text string.
	 *
	 * @param document the GeboDocument to be converted
	 * @return a string representation of the document's content
	 */
	@Override
	public String toText(GeboDocument document) {
		StringBuffer buffer = new StringBuffer();
		for (GeboTextDocumentFragment t : document.getTexts()) {
			buffer.append(t.getContent()); // Append content of each text fragment
		}
		return buffer.toString(); // Return the combined text content
	}

	/**
	 * Creates a GeboDocument from a ChatResponse, setting content type and extension.
	 *
	 * @param res the chat response containing generated results
	 * @param contentType the type of content to set for the document
	 * @param extension the file extension to set for the document
	 * @return a GeboDocument constructed from the chat response data
	 */
	@Override
	public GeboDocument fromChatResponse(ChatResponse res, String contentType, String extension) {
		GeboDocument document = new GeboDocument();
		document.setContentType(contentType);
		document.setExtension(extension);
		if (res.getResults() != null && !res.getResults().isEmpty()) {
			for (Generation r : res.getResults()) {
				String text = r.getOutput().getText();
				GeboTextDocumentFragment fragment = new GeboTextDocumentFragment();
				fragment.setContent(text); // Set content for each text fragment
				document.getTexts().add(fragment);
				List<Media> media = r.getOutput().getMedia();
				for (Media _media : media) {
					// Try to add media to the document
					document.tryAddMedia(_media.getName(),_media.getMimeType().getType(),_media.getMimeType().getSubtype(),_media.getMimeType().getSubtypeSuffix(),_media.getDataAsByteArray());
				}
			}
		}
		return document; // Return the constructed GeboDocument
	}

}