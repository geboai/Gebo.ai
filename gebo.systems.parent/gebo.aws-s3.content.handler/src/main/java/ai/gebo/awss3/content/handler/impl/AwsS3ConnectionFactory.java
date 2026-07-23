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
import ai.gebo.secrets.model.GeboAwsConnectionCredentials;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.awss3.content.handler.GAwsS3System;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Service
public class AwsS3ConnectionFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3ConnectionFactory.class);

	private static final String DEFAULT_REGION = "us-east-1";

	@Autowired
	private IGeboSecretsAccessService secretAccessService;

	public S3Client createS3Client(GAwsS3System system, String secretCode)
			throws VirtualFilesystemBrowsingException {
		try {
			GeboAwsConnectionCredentials connection = loadConnection(secretCode);

			AwsCredentialsProvider credentialsProvider;
			Region region;

			if (connection != null) {
				credentialsProvider = StaticCredentialsProvider.create(
						AwsBasicCredentials.create(connection.getAccessKeyId(), connection.getSecretAccessKey()));
				region = connection.getRegion() != null
						? Region.of(connection.getRegion().getCode())
						: Region.of(DEFAULT_REGION);
			} else {
				credentialsProvider = DefaultCredentialsProvider.create();
				region = Region.of(DEFAULT_REGION);
			}

			S3ClientBuilder builder = S3Client.builder()
					.region(region)
					.credentialsProvider(credentialsProvider);

			if (system.getAwsEndpoint() != null && !system.getAwsEndpoint().isBlank()) {
				builder.endpointOverride(URI.create(system.getAwsEndpoint()));
				builder.forcePathStyle(true);
			}

			return builder.build();
		} catch (VirtualFilesystemBrowsingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Cannot create S3 client", e);
			throw new VirtualFilesystemBrowsingException("Cannot create S3 client: " + e.getMessage(), e);
		}
	}

	private GeboAwsConnectionCredentials loadConnection(String secretCode) throws VirtualFilesystemBrowsingException {
		if (secretCode == null || secretCode.trim().length() == 0) {
			return null;
		}
		try {
			AbstractGeboSecretContent secret = secretAccessService.getSecretContentById(secretCode);
			if (secret.type() == GeboSecretType.AWS_CONNECTION) {
				return (GeboAwsConnectionCredentials) secret;
			}
			throw new VirtualFilesystemBrowsingException(
					"AWS S3 credentials must be stored as an AWS_CONNECTION secret");
		} catch (GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cannot resolve AWS S3 credentials", e);
		}
	}
}