/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

public class OpenAIVectorStoreFile extends OpenAIObject {
	private Long usage_bytes = null, created_at = null;
	private String status = null, vector_store_id = null;
	private OpenAIVectorStoreChunkingStrategy chunking_strategy = null;
	private OpenAIErrorMessage last_error = null;

	public OpenAIVectorStoreFile() {

	}

	public Long getUsage_bytes() {
		return usage_bytes;
	}

	public void setUsage_bytes(Long usage_bytes) {
		this.usage_bytes = usage_bytes;
	}

	public Long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVector_store_id() {
		return vector_store_id;
	}

	public void setVector_store_id(String vector_store_id) {
		this.vector_store_id = vector_store_id;
	}

	public OpenAIVectorStoreChunkingStrategy getChunking_strategy() {
		return chunking_strategy;
	}

	public void setChunking_strategy(OpenAIVectorStoreChunkingStrategy chunking_strategy) {
		this.chunking_strategy = chunking_strategy;
	}

	public OpenAIErrorMessage getLast_error() {
		return last_error;
	}

	public void setLast_error(OpenAIErrorMessage last_error) {
		this.last_error = last_error;
	}

}
