/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboAwsConnectionCredentials;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

/**
 * Resolves AWS credentials and region for every AWS Bedrock / Polly / Transcribe
 * model configuration.
 *
 * <p>
 * Credentials are stored in the platform secret vault as a
 * {@link GeboSecretType#AWS_CONNECTION} entry, which carries the AWS access key
 * id, the AWS secret access key and the AWS region as a single unit. The region
 * is therefore resolved from the very same secret, unifying credentials and
 * region management. When no secret code is configured, the AWS default
 * credentials provider chain is used (environment, system properties, EC2/ECS
 * instance role ...) together with the {@link #DEFAULT_REGION}, which allows the
 * platform to rely on an attached IAM role when it runs inside AWS.
 * </p>
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
public class BedrockCredentialsResolver {

	/** Region used when a configuration does not specify one. */
	public static final String DEFAULT_REGION = "us-east-1";

	@Autowired
	private IGeboSecretsAccessService secretService;

	/**
	 * Builds an {@link AwsCredentialsProvider} from the configured
	 * {@link GeboSecretType#AWS_CONNECTION} secret, falling back to the AWS default
	 * provider chain when no secret is set.
	 *
	 * @param apiSecretCode the secret code, may be {@code null}
	 * @return a usable credentials provider
	 * @throws LLMConfigException when the referenced secret cannot be read or is of
	 *                            the wrong type
	 */
	public AwsCredentialsProvider resolveCredentials(String apiSecretCode) throws LLMConfigException {
		GeboAwsConnectionCredentials connection = loadConnection(apiSecretCode);
		if (connection == null) {
			return DefaultCredentialsProvider.create();
		}
		AwsBasicCredentials basic = AwsBasicCredentials.create(connection.getAccessKeyId(),
				connection.getSecretAccessKey());
		return StaticCredentialsProvider.create(basic);
	}

	/**
	 * Resolves the AWS {@link Region} to use from the same
	 * {@link GeboSecretType#AWS_CONNECTION} secret that holds the credentials,
	 * falling back to {@link #DEFAULT_REGION} when no secret (or no region) is
	 * configured.
	 *
	 * @param apiSecretCode the secret code holding the AWS connection, may be
	 *                      {@code null}
	 * @return the resolved region
	 * @throws LLMConfigException when the referenced secret cannot be read or is of
	 *                            the wrong type
	 */
	public Region resolveRegion(String apiSecretCode) throws LLMConfigException {
		GeboAwsConnectionCredentials connection = loadConnection(apiSecretCode);
		if (connection == null || connection.getRegion() == null) {
			return Region.of(DEFAULT_REGION);
		}
		return Region.of(connection.getRegion().getCode());
	}

	/**
	 * Loads and validates the {@link GeboAwsConnectionCredentials} referenced by the
	 * given secret code.
	 *
	 * @param apiSecretCode the secret code, may be {@code null} or blank
	 * @return the AWS connection secret, or {@code null} when no secret code is set
	 *         (default provider chain / default region is to be used)
	 * @throws LLMConfigException when the referenced secret cannot be read or is not
	 *                            an {@link GeboSecretType#AWS_CONNECTION} secret
	 */
	private GeboAwsConnectionCredentials loadConnection(String apiSecretCode) throws LLMConfigException {
		if (apiSecretCode == null || apiSecretCode.trim().length() == 0) {
			return null;
		}
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(apiSecretCode);
			if (secret.type() == GeboSecretType.AWS_CONNECTION) {
				return (GeboAwsConnectionCredentials) secret;
			}
			throw new LLMConfigException(
					"AWS Bedrock credentials must be stored as an AWS_CONNECTION secret (access key id, secret access key and region)");
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("AWS Bedrock credentials configuration gone wrong", e);
		}
	}
}
