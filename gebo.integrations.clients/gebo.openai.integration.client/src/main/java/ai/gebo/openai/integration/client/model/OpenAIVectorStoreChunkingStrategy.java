/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

public class OpenAIVectorStoreChunkingStrategy {
	private String type="auto";
	private Integer max_chunk_size_tokens=null;
	private Integer chunk_overlap_tokens=null;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMax_chunk_size_tokens() {
		return max_chunk_size_tokens;
	}
	public void setMax_chunk_size_tokens(Integer max_chunk_size_tokens) {
		this.max_chunk_size_tokens = max_chunk_size_tokens;
	}
	public Integer getChunk_overlap_tokens() {
		return chunk_overlap_tokens;
	}
	public void setChunk_overlap_tokens(Integer chunk_overlap_tokens) {
		this.chunk_overlap_tokens = chunk_overlap_tokens;
	}
}