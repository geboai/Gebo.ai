/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

import java.util.HashMap;

public class OpenAIVectorStore extends OpenAIObject {
	private String name = null;
	private Long usage_bytes = null;
	private String status = null;

	private OpenAIVectorStoreFileCounts file_counts = null;
	private OpenAIVectorStoreExpiresAfter expires_after = null;
	private Long expires_at=null,last_active_at=null;
	private HashMap<String, Object> metadata=null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUsage_bytes() {
		return usage_bytes;
	}
	public void setUsage_bytes(Long usage_bytes) {
		this.usage_bytes = usage_bytes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OpenAIVectorStoreFileCounts getFile_counts() {
		return file_counts;
	}
	public void setFile_counts(OpenAIVectorStoreFileCounts file_counts) {
		this.file_counts = file_counts;
	}
	public OpenAIVectorStoreExpiresAfter getExpires_after() {
		return expires_after;
	}
	public void setExpires_after(OpenAIVectorStoreExpiresAfter expires_after) {
		this.expires_after = expires_after;
	}
	public Long getExpires_at() {
		return expires_at;
	}
	public void setExpires_at(Long expires_at) {
		this.expires_at = expires_at;
	}
	public Long getLast_active_at() {
		return last_active_at;
	}
	public void setLast_active_at(Long last_active_at) {
		this.last_active_at = last_active_at;
	}
	public HashMap<String, Object> getMetadata() {
		return metadata;
	}
	public void setMetadata(HashMap<String, Object> metadata) {
		this.metadata = metadata;
	}
}
