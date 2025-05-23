/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

import java.util.ArrayList;
import java.util.List;

public class OpenAIResultList<T extends OpenAIObject> {
	private OpenAIObjectType object = null;
	private List<T> data = new ArrayList<T>();

	public OpenAIResultList() {

	}

	

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}



	public OpenAIObjectType getObject() {
		return object;
	}



	public void setObject(OpenAIObjectType object) {
		this.object = object;
	}

}
