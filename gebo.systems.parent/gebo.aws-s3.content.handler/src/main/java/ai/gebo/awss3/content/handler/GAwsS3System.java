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

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

@Document
public class GAwsS3System extends GContentManagementSystem {

	private String awsEndpoint;
	private String s3SecretCode;

	public String getAwsEndpoint() {
		return awsEndpoint;
	}

	public void setAwsEndpoint(String awsEndpoint) {
		this.awsEndpoint = awsEndpoint;
	}

	public String getS3SecretCode() {
		return s3SecretCode;
	}

	public void setS3SecretCode(String s3SecretCode) {
		this.s3SecretCode = s3SecretCode;
	}
}