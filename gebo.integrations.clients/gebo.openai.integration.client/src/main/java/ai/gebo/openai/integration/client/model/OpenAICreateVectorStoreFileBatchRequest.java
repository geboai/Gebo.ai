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

import jakarta.validation.constraints.NotNull;

public class OpenAICreateVectorStoreFileBatchRequest {
	@NotNull
	private List<String> file_ids = null;
	private OpenAIVectorStoreChunkingStrategy chunking_strategy = null;

	public OpenAICreateVectorStoreFileBatchRequest() {

	}

	public List<String> getFile_ids() {
		return file_ids;
	}

	public void setFile_ids(List<String> file_ids) {
		this.file_ids = file_ids;
	}

	public OpenAIVectorStoreChunkingStrategy getChunking_strategy() {
		return chunking_strategy;
	}

	public void setChunking_strategy(OpenAIVectorStoreChunkingStrategy chunking_strategy) {
		this.chunking_strategy = chunking_strategy;
	}

}
