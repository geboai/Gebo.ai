/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.FoundationModelLifecycle;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.InferenceProfileModel;
import software.amazon.awssdk.services.bedrock.model.InferenceProfileSummary;
import software.amazon.awssdk.services.bedrock.model.InferenceProfileType;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsRequest;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsResponse;
import software.amazon.awssdk.services.bedrock.model.ListInferenceProfilesRequest;
import software.amazon.awssdk.services.bedrock.model.ModelModality;

/**
 * Lists the Bedrock models a configuration can actually invoke.
 * <p>
 * A foundation model is invoked by its id only when it supports on demand
 * throughput. Most current models (Claude, Nova, Llama 3.2 and later, ...) support
 * only the INFERENCE_PROFILE type: they are invoked through a cross region
 * inference profile id ({@code us.}, {@code eu.}, {@code apac.}, {@code global.}),
 * and a request naming the bare model id is refused. Models offered only with
 * provisioned throughput cannot be invoked without a purchased provisioning and are
 * not listed.
 */
@Service
public class BedrockFoundationModelsLookupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BedrockFoundationModelsLookupService.class);

	static final String PROVIDER_ID = "aws-bedrock";
	static final String INFORMATIVE_URL = "https://docs.aws.amazon.com/bedrock/latest/userguide/models-supported.html";
	static final String ON_DEMAND = "ON_DEMAND", INFERENCE_PROFILE = "INFERENCE_PROFILE";

	@Autowired
	private BedrockCredentialsResolver credentialsResolver;

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
				List<InferenceProfileSummary> profiles = new ArrayList<>();
				List<GUserMessage> messages = new ArrayList<>();
				try {
					client.listInferenceProfilesPaginator(
							ListInferenceProfilesRequest.builder().typeEquals(InferenceProfileType.SYSTEM_DEFINED).build())
							.inferenceProfileSummaries().forEach(profiles::add);
				} catch (Throwable t) {
					LOGGER.warn("Unable to list the AWS Bedrock inference profiles: " + t.getMessage());
					messages.add(GUserMessage.warnMessage("AWS Bedrock inference profiles",
							"The inference profiles cannot be listed (" + t.getMessage()
									+ "): the models invoked only through a profile are not offered"));
				}
				return OperationStatus.of(toChoices(response.modelSummaries(), profiles, choiceFactory), messages);
			}
		} catch (LLMConfigException e) {
			LOGGER.error("AWS Bedrock credentials/region configuration error during model lookup", e);
			return OperationStatus.ofError("AWS Bedrock configuration error", e.getMessage());
		} catch (Throwable t) {
			LOGGER.error("Unable to list AWS Bedrock foundation models", t);
			return OperationStatus.ofError("Unable to list AWS Bedrock foundation models", t.getMessage());
		}
	}

	/**
	 * The invocable choices of the listed foundation models: the model id of the on
	 * demand ones, the id of every active inference profile routing to a model.
	 */
	static <C extends GBaseModelChoice> List<C> toChoices(List<FoundationModelSummary> models,
			List<InferenceProfileSummary> profiles, Supplier<C> choiceFactory) {
		List<C> choices = new ArrayList<>();
		for (FoundationModelSummary summary : models) {
			List<String> types = summary.inferenceTypesSupportedAsStrings();
			String provider = summary.providerName() != null ? summary.providerName() : "AWS";
			String name = summary.modelName() != null ? summary.modelName() : summary.modelId();
			if (types.contains(ON_DEMAND)) {
				choices.add(choice(choiceFactory, summary.modelId(), name + " (" + provider + ")", summary));
			}
			if (types.contains(INFERENCE_PROFILE)) {
				for (InferenceProfileSummary profile : profiles) {
					if (routesTo(profile, summary.modelId()) && !"INACTIVE".equals(profile.statusAsString())) {
						choices.add(choice(choiceFactory, profile.inferenceProfileId(),
								name + " (" + provider + ", " + profileScope(profile) + ")", summary));
					}
				}
			}
		}
		return choices;
	}

	/**
	 * Whether a profile routes to a foundation model: its models are the model's arns
	 * in the regions it spans, all ending with {@code foundation-model/<model id>}.
	 */
	static boolean routesTo(InferenceProfileSummary profile, String modelId) {
		if (modelId == null || !profile.hasModels())
			return false;
		String suffix = "foundation-model/" + modelId;
		for (InferenceProfileModel model : profile.models()) {
			if (model.modelArn() != null && model.modelArn().endsWith(suffix))
				return true;
		}
		return false;
	}

	/** The scope of a profile as its id prefix says it: us, eu, apac, global ... */
	static String profileScope(InferenceProfileSummary profile) {
		String id = profile.inferenceProfileId();
		int dot = id != null ? id.indexOf('.') : -1;
		String scope = dot > 0 ? id.substring(0, dot) : "cross region";
		return scope + " inference profile";
	}

	private static <C extends GBaseModelChoice> C choice(Supplier<C> choiceFactory, String code, String description,
			FoundationModelSummary summary) {
		C choice = choiceFactory.get();
		choice.setCode(code);
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(PROVIDER_ID);
		meta.setModelId(code);
		meta.setInformativeUrl(INFORMATIVE_URL);
		meta.setChatModel(summary.outputModalitiesAsStrings().contains("TEXT"));
		meta.setEmbeddingModel(summary.outputModalitiesAsStrings().contains("EMBEDDING"));
		if (summary.hasInputModalities())
			meta.setSupportsVision(summary.inputModalitiesAsStrings().contains("IMAGE"));
		FoundationModelLifecycle lifecycle = summary.modelLifecycle();
		boolean legacy = lifecycle != null && "LEGACY".equals(lifecycle.statusAsString());
		if (lifecycle != null && lifecycle.statusAsString() != null) {
			meta.setDeprecated(legacy);
			meta.setDeprecationDate(toDate(lifecycle.legacyTime()));
			meta.setRetirementDate(toDate(lifecycle.endOfLifeTime()));
		}
		if (legacy) {
			description += meta.getRetirementDate() != null
					? " (legacy, end of life " + meta.getRetirementDate().substring(0, 10) + ")"
					: " (legacy)";
		}
		meta.setDescription(description);
		choice.setDescription(description);
		choice.setInformativeUrl(INFORMATIVE_URL);
		choice.setMetaInfos(meta);
		return choice;
	}

	private static String toDate(Instant instant) {
		return instant != null ? instant.toString() : null;
	}
}
