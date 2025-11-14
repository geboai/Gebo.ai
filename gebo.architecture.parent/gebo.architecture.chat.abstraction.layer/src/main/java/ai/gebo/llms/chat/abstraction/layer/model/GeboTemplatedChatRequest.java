/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import lombok.Data;

/**
 * GeboTemplatedChatRequest is a generic class for managing chat requests with
 * type parameter RequestType representing the type of query. It provides
 * various configurations required for processing chat requests.
 * 
 * AI generated comments
 * 
 * type param RequestType The type of the query used in the chat request.
 */
@Data
public class GeboTemplatedChatRequest<RequestType> implements Serializable {

	/** Unique identifier for each chat request instance */
	protected String id = UUID.randomUUID().toString();

	/** Code representing the user's chat context */
	protected String userChatContextCode = null;

	/** Code representing the chat profile used for the request */
	protected String chatProfileCode = null;

	/** Code representing the chat model being used */
	protected String chatModelCode = null;

	/** Flag to determine whether responses should be streamed */
	protected boolean streamResponse = false;

	/** The chat query, parameterized by RequestType */
	protected RequestType query = null;

	/** Custom configuration for RAG (Retrieval-Augmented Generation) requests */
	protected GeboRagRequestCustomConfig customRagConfig = null;

	/** List of knowledge bases selected for the request */
	private List<String> choosedKnowledgeBases = null;

	/** List of documents that are forcibly requested */
	protected List<String> forcedRequestDocuments = new ArrayList<String>();
	/** documents retrieved on rag or "chat with document" **/
	protected RagDocumentsCachedDaoResult documents = null;

}