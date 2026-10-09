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
    // The date (yyyy-MM-dd) OpenAI shuts the model down, null while none is announced
    private String shutdown_date=null;
    // Fields some OpenAI compatible providers add to the model object (Groq, ...)
    private Integer context_window=null, max_completion_tokens=null;
    private Boolean active=null;
    
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
	public String getShutdown_date() {
		return shutdown_date;
	}
	public void setShutdown_date(String shutdown_date) {
		this.shutdown_date = shutdown_date;
	}
	public Integer getContext_window() {
		return context_window;
	}
	public void setContext_window(Integer context_window) {
		this.context_window = context_window;
	}
	public Integer getMax_completion_tokens() {
		return max_completion_tokens;
	}
	public void setMax_completion_tokens(Integer max_completion_tokens) {
		this.max_completion_tokens = max_completion_tokens;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

}
