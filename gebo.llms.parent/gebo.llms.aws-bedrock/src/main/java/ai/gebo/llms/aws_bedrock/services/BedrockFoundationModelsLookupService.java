/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.OperationStatus;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsRequest;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsResponse;
import software.amazon.awssdk.services.bedrock.model.ModelModality;

/**
 * Coherent, live lookup of the models AWS Bedrock exposes to the account, based
 * on the control-plane {@code ListFoundationModels} operation. Results are
 * filtered by output modality so each model category (chat/text, embedding,
 * image) only offers the relevant foundation models.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
public class BedrockFoundationModelsLookupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BedrockFoundationModelsLookupService.class);

	@Autowired
	private BedrockCredentialsResolver credentialsResolver;

	/**
	 * Lists the foundation models whose output modality matches
	 * {@code requiredOutputModality}, mapping each to a platform model choice.
	 *
	 * @param apiSecretCode          secret code holding the AWS credentials and
	 *                               region (may be {@code null} to use the default
	 *                               provider chain and region)
	 * @param requiredOutputModality output modality to filter on
	 * @param choiceFactory          creates a fresh, empty choice instance
	 * @param <C>                    the concrete model choice type
	 * @return an {@link OperationStatus} carrying the choices, or an error message
	 *         if the AWS call fails
	 */
	public <C extends GBaseModelChoice> OperationStatus<List<C>> listModels(String apiSecretCode,
			ModelModality requiredOutputModality, Supplier<C> choiceFactory) {
		try {
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(apiSecretCode);
			Region awsRegion = credentialsResolver.resolveRegion(apiSecretCode);
			try (BedrockClient client = BedrockClient.builder().region(awsRegion).credentialsProvider(credentials)
					.build()) {
				ListFoundationModelsRequest request = ListFoundationModelsRequest.builder()
						.byOutputModality(requiredOutputModality).build();
				ListFoundationModelsResponse response = client.listFoundationModels(request);
				List<C> choices = new ArrayList<>();
				for (FoundationModelSummary summary : response.modelSummaries()) {
					C choice = choiceFactory.get();
					choice.setCode(summary.modelId());
					String provider = summary.providerName() != null ? summary.providerName() : "AWS";
					String name = summary.modelName() != null ? summary.modelName() : summary.modelId();
					choice.setDescription(name + " (" + provider + ")");
					choices.add(choice);
				}
				return OperationStatus.of(choices);
			}
		} catch (LLMConfigException e) {
			LOGGER.error("AWS Bedrock credentials/region configuration error during model lookup", e);
			return OperationStatus.ofError("AWS Bedrock configuration error", e.getMessage());
		} catch (Throwable t) {
			LOGGER.error("Unable to list AWS Bedrock foundation models", t);
			return OperationStatus.ofError("Unable to list AWS Bedrock foundation models", t.getMessage());
		}
	}
}
