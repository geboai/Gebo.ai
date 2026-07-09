/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboCustomSecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.awss3.content.handler.GAwsS3System;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Service
public class AwsS3ConnectionFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3ConnectionFactory.class);

	@Autowired
	private IGeboSecretsAccessService secretAccessService;

	public S3Client createS3Client(GAwsS3System system, String secretCode)
			throws VirtualFilesystemBrowsingException {
		try {
			AbstractGeboSecretContent secret = secretAccessService.getSecretContentById(secretCode);
			if (!(secret instanceof GeboCustomSecretContent)) {
				throw new VirtualFilesystemBrowsingException(
						"Invalid secret type for S3, required GeboCustomSecretContent");
			}
			GeboCustomSecretContent customSecret = (GeboCustomSecretContent) secret;
			String decryptedSecretKey = customSecret.getContent();

			S3ClientBuilder builder = S3Client.builder()
					.region(Region.of(system.getAwsRegion()))
					.credentialsProvider(StaticCredentialsProvider.create(
							AwsBasicCredentials.create(system.getAwsAccessKeyId(), decryptedSecretKey)));

			if (system.getAwsEndpoint() != null && !system.getAwsEndpoint().isBlank()) {
				builder.endpointOverride(URI.create(system.getAwsEndpoint()));
				builder.forcePathStyle(true);
			}

			return builder.build();
		} catch (GeboCryptSecretException e) {
			LOGGER.error("Cannot resolve S3 secret", e);
			throw new VirtualFilesystemBrowsingException("Cannot resolve S3 secret: " + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("Cannot create S3 client", e);
			throw new VirtualFilesystemBrowsingException("Cannot create S3 client: " + e.getMessage(), e);
		}
	}
}