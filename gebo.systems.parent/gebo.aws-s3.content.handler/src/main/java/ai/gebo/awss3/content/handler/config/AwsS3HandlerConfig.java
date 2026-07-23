/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai.gebo.awss3")
public class AwsS3HandlerConfig {

	private String queryExtractionPrompt = "Extract search filters from the following query for AWS S3 object search.";

	public String getQueryExtractionPrompt() {
		return queryExtractionPrompt;
	}

	public void setQueryExtractionPrompt(String queryExtractionPrompt) {
		this.queryExtractionPrompt = queryExtractionPrompt;
	}
}