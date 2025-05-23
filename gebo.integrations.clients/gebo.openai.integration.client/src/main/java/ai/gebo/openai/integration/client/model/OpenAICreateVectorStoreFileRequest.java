/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

import jakarta.validation.constraints.NotNull;

public class OpenAICreateVectorStoreFileRequest {
	
	@NotNull
	private String file_id = null;
	private OpenAIVectorStoreChunkingStrategy chunking_strategy = null;

	public OpenAICreateVectorStoreFileRequest() {

	}

	

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public OpenAIVectorStoreChunkingStrategy getChunking_strategy() {
		return chunking_strategy;
	}

	public void setChunking_strategy(OpenAIVectorStoreChunkingStrategy chunking_strategy) {
		this.chunking_strategy = chunking_strategy;
	}

}
