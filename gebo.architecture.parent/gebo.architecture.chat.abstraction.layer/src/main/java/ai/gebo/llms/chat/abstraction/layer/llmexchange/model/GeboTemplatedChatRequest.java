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
import java.util.UUID;

import ai.gebo.llms.chat.abstraction.layer.model.GeboRagRequestCustomConfig;
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
	private String id = UUID.randomUUID().toString();

	/** Code representing the user's chat context */
	private String userChatContextCode = null;

	/** Code representing the chat profile used for the request */
	private String chatProfileCode = null;

	/** Code representing the chat model being used */
	private String chatModelCode = null;

	/** Flag to determine whether responses should be streamed */
	private boolean streamResponse = false;

	/** The chat query, parameterized by RequestType */
	private RequestType query = null;
	private RequestType rewrittenQuery = null;
	/** Custom configuration for RAG (Retrieval-Augmented Generation) requests */
	private GeboRagRequestCustomConfig customRagConfig = null;

	/** List of knowledge bases selected for the request */
	private List<String> choosedKnowledgeBases = null;
	private String chatPipelineProcessId = null;
	/** List of documents that are forcibly requested */
	private List<String> forcedRequestDocuments = new ArrayList<String>();
	
	private List<UserUploadedContent> userUploadedContents = new ArrayList<>();
	private List<String> deepSearchDataSources = null;

	public static <RequestType> RequestType actualQuery(GeboTemplatedChatRequest<RequestType> request) {
		return request.getRewrittenQuery() != null ? request.getRewrittenQuery() : request.getQuery();
	}

}