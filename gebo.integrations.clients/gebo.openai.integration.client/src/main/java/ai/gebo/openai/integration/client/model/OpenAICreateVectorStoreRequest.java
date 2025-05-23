/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;

public class OpenAICreateVectorStoreRequest {
	@NotNull
	private String name = null;
	private List<String> file_ids = null;
	private Map<String, Object> metadata = null;

	private OpenAIVectorStoreExpiresAfter expires_after = null;
	
	private OpenAIVectorStoreChunkingStrategy chunking_strategy=null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFile_ids() {
		return file_ids;
	}
	public void setFile_ids(List<String> file_ids) {
		this.file_ids = file_ids;
	}
	public Map<String, Object> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
	public OpenAIVectorStoreExpiresAfter getExpires_after() {
		return expires_after;
	}
	public void setExpires_after(OpenAIVectorStoreExpiresAfter expires_after) {
		this.expires_after = expires_after;
	}
	public OpenAIVectorStoreChunkingStrategy getChunking_strategy() {
		return chunking_strategy;
	}
	public void setChunking_strategy(OpenAIVectorStoreChunkingStrategy chunking_strategy) {
		this.chunking_strategy = chunking_strategy;
	}
}
