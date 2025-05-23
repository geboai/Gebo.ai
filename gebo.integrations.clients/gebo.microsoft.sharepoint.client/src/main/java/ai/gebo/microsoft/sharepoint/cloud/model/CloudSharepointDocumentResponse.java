/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.model;

import java.util.List;

public class CloudSharepointDocumentResponse {
	private List<CloudSharepointDocument> documents;

	public List<CloudSharepointDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<CloudSharepointDocument> documents) {
		this.documents = documents;
	}
}