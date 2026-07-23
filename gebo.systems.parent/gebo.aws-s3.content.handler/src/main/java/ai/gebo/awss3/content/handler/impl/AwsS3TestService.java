/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.awss3.content.handler.GAwsS3System;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@Service
public class AwsS3TestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3TestService.class);

	@Autowired
	private AwsS3ConnectionFactory connectionFactory;

	public OperationStatus<GAwsS3System> test(GAwsS3System system) {
		OperationStatus<GAwsS3System> status = OperationStatus.of(system);
		try {
			S3Client client = connectionFactory.createS3Client(system, system.getS3SecretCode());
			ListBucketsResponse response = client.listBuckets();
			status.getMessages().add(GUserMessage.infoMessage("S3 connection successful",
					"Found " + response.buckets().size() + " bucket(s)"));
		} catch (VirtualFilesystemBrowsingException e) {
			LOGGER.error("S3 connection test failed", e);
			status.getMessages().add(GUserMessage.errorMessage("S3 connection test failed", e));
		} catch (Exception e) {
			LOGGER.error("S3 connection test failed", e);
			status.getMessages().add(GUserMessage.errorMessage("S3 connection test failed: " + e.getMessage(), e));
		}
		return status;
	}
}