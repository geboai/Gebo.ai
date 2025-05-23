/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

public class OpenAIVectorStoreExpiresAfter {
	private OpenAIVectorStoreExpiringAnchor anchor = OpenAIVectorStoreExpiringAnchor.last_active_at;
	private int days = 10;
	public OpenAIVectorStoreExpiringAnchor getAnchor() {
		return anchor;
	}
	public void setAnchor(OpenAIVectorStoreExpiringAnchor anchor) {
		this.anchor = anchor;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
}