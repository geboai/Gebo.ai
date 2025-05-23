/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;

public class OpenAIModel extends OpenAIObject{
	
    private String owned_by=null;
    private ModelMetaInfo metaInfos=null;
    
	public OpenAIModel() {
		
	}
	
	public String getOwned_by() {
		return owned_by;
	}
	public void setOwned_by(String owned_by) {
		this.owned_by = owned_by;
	}
	public ModelMetaInfo getMetaInfos() {
		return metaInfos;
	}
	public void setMetaInfos(ModelMetaInfo metaInfos) {
		this.metaInfos = metaInfos;
	}

}
