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
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
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
 * {@link GeboSecretType#USERNAME_PASSWORD} entry, where the username holds the
 * AWS access key id and the password holds the AWS secret access key. When no
 * secret code is configured, the AWS default credentials provider chain is used
 * (environment, system properties, EC2/ECS instance role ...), which allows the
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
	 * Builds an {@link AwsCredentialsProvider} from the configured secret, falling
	 * back to the AWS default provider chain when no secret is set.
	 *
	 * @param apiSecretCode the secret code, may be {@code null}
	 * @return a usable credentials provider
	 * @throws LLMConfigException when the referenced secret cannot be read or is of
	 *                            the wrong type
	 */
	public AwsCredentialsProvider resolveCredentials(String apiSecretCode) throws LLMConfigException {
		if (apiSecretCode == null || apiSecretCode.trim().length() == 0) {
			return DefaultCredentialsProvider.create();
		}
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(apiSecretCode);
			if (secret.type() == GeboSecretType.USERNAME_PASSWORD) {
				GeboUsernamePasswordContent creds = (GeboUsernamePasswordContent) secret;
				AwsBasicCredentials basic = AwsBasicCredentials.create(creds.getUsername(), creds.getPassword());
				return StaticCredentialsProvider.create(basic);
			}
			throw new LLMConfigException(
					"AWS Bedrock credentials must be stored as a USERNAME_PASSWORD secret (username=access key id, password=secret access key)");
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("AWS Bedrock credentials configuration gone wrong", e);
		}
	}

	/**
	 * Resolves the AWS {@link Region} to use, falling back to {@link #DEFAULT_REGION}.
	 *
	 * @param region a region id such as {@code us-east-1}, may be {@code null}
	 * @return the resolved region
	 */
	public Region resolveRegion(String region) {
		if (region == null || region.trim().length() == 0) {
			return Region.of(DEFAULT_REGION);
		}
		return Region.of(region.trim());
	}
}
