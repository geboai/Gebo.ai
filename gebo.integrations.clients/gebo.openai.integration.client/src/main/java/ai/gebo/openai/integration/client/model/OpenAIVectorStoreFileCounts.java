/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

public class OpenAIVectorStoreFileCounts {
	private Integer in_progress;

	// The number of files that are currently being processed.

	private Integer completed;

	// The number of files that have been successfully processed.

	private Integer failed;

	// The number of files that have failed to process.

	private Integer cancelled;

	// The number of files that were cancelled.

	private Integer total;

	public Integer getIn_progress() {
		return in_progress;
	}

	public void setIn_progress(Integer in_progress) {
		this.in_progress = in_progress;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	public Integer getFailed() {
		return failed;
	}

	public void setFailed(Integer failed) {
		this.failed = failed;
	}

	public Integer getCancelled() {
		return cancelled;
	}

	public void setCancelled(Integer cancelled) {
		this.cancelled = cancelled;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	// The total number of files.
}