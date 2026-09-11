/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.awss3.content.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import ai.gebo.awss3.content.handler.GAwsS3System;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * The AWS S3 systems a deployment declares in its own configuration, under
 * {@code ai.gebo.awss3.systems}:
 *
 * <pre>
 * ai.gebo.awss3:
 *   systems:
 *     - code: corporate-buckets
 *       description: Corporate S3 buckets
 *       awsEndpoint: https://s3.eu-south-1.amazonaws.com
 *       s3SecretCode: aws-ingestion-account
 * </pre>
 *
 * <p>
 * {@code s3SecretCode} references an AWS connection credential held by the
 * secrets management layer - one declared under
 * {@code ai.gebo.secrets.config.aws-connection} or created in the admin UI - so
 * no credential is ever spelled out here.
 * </p>
 *
 * <p>
 * A separate bean rather than a field on {@link AwsS3HandlerConfig}, which binds
 * the same prefix for the query-extraction prompt: the two are configured by
 * different people at different times, and only this one is {@code @Validated},
 * so the cascade reaches the constraints {@link GAwsS3System} carries.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.awss3")
@Validated
@Data
public class AwsS3SystemsConfig {

	/** {@code ai.gebo.awss3.systems} */
	private List<@Valid GAwsS3System> systems = new ArrayList<GAwsS3System>();
}
