/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * Gebo.ai comment agent
 * Interface for translating GeboDocuments to AI-compatible formats and vice versa.
 * It provides methods to convert {@link GeboDocument} to AI {@link Document} format, 
 * extract text from a {@link GeboDocument}, and create a {@link GeboDocument} from chat responses.
 */
package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;

import ai.gebo.document.model.GeboDocument;

public interface IGDocumentTranslator {

    /**
     * Converts a {@link GeboDocument} to a list of AI {@link Document} objects.
     *
     * @param document The GeboDocument to convert.
     * @return A list of Documents in AI format.
     */
    public List<Document> toAi(GeboDocument document);

    /**
     * Extracts and returns the text content of a {@link GeboDocument}.
     *
     * @param document The GeboDocument to extract text from.
     * @return The textual representation of the document.
     */
    public String toText(GeboDocument document);

    /**
     * Creates a {@link GeboDocument} from a {@link ChatResponse}.
     *
     * @param res The ChatResponse object containing the response data.
     * @param contentType The type of content in the response.
     * @param extension The file extension for the resulting GeboDocument.
     * @return A new GeboDocument created from the chat response.
     */
    public GeboDocument fromChatResponse(ChatResponse res, String contentType, String extension);
}