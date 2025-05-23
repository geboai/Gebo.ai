/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

public class OpenAIPagedResultList<T extends OpenAIObject> extends OpenAIResultList<T> {

	public OpenAIPagedResultList() {

	}

	String first_id = null, last_id = null;
	Boolean has_more = null;
	public String getFirst_id() {
		return first_id;
	}
	public void setFirst_id(String first_id) {
		this.first_id = first_id;
	}
	public String getLast_id() {
		return last_id;
	}
	public void setLast_id(String last_id) {
		this.last_id = last_id;
	}
	public Boolean getHas_more() {
		return has_more;
	}
	public void setHas_more(Boolean has_more) {
		this.has_more = has_more;
	}
}
