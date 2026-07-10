/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;

@Document
public class GAwsS3ProjectEndpoint extends GVirtualFilesystemProjectEndpoint {

	private String s3SystemCode;

	public String getS3SystemCode() {
		return s3SystemCode;
	}

	public void setS3SystemCode(String s3SystemCode) {
		this.s3SystemCode = s3SystemCode;
	}
}
