package ai.gebo.llms.aws_bedrock.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import software.amazon.awssdk.services.bedrock.model.FoundationModelLifecycle;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.InferenceProfileModel;
import software.amazon.awssdk.services.bedrock.model.InferenceProfileSummary;

class BedrockFoundationModelsLookupServiceTest {

	static FoundationModelSummary model(String id, String name, String lifecycle, String... inferenceTypes) {
		return FoundationModelSummary.builder().modelId(id).modelName(name).providerName("Anthropic")
				.modelArn("arn:aws:bedrock:us-east-1::foundation-model/" + id)
				.inputModalitiesWithStrings("TEXT", "IMAGE").outputModalitiesWithStrings("TEXT")
				.inferenceTypesSupportedWithStrings(inferenceTypes)
				.modelLifecycle(FoundationModelLifecycle.builder().status(lifecycle)
						.endOfLifeTime("LEGACY".equals(lifecycle) ? Instant.parse("2026-12-01T00:00:00Z") : null)
						.build())
				.build();
	}

	static InferenceProfileSummary profile(String id, String modelId) {
		return InferenceProfileSummary.builder().inferenceProfileId(id).status("ACTIVE")
				.models(InferenceProfileModel.builder()
						.modelArn("arn:aws:bedrock:us-east-1::foundation-model/" + modelId).build(),
						InferenceProfileModel.builder()
								.modelArn("arn:aws:bedrock:us-west-2::foundation-model/" + modelId).build())
				.build();
	}

	@Test
	void profileOnlyModelsAreOfferedByTheirProfilesAndProvisionedOnlyOnesNotAtAll() {
		List<GBaseChatModelChoice> choices = BedrockFoundationModelsLookupService.toChoices(
				List.of(model("anthropic.claude-sonnet-5-5", "Claude Sonnet 5.5", "ACTIVE", "INFERENCE_PROFILE"),
						model("amazon.titan-text-express-v1", "Titan Text Express", "ACTIVE", "ON_DEMAND"),
						model("amazon.titan-text-express-v1:0:8k", "Titan Text Express 8k", "ACTIVE", "PROVISIONED")),
				List.of(profile("us.anthropic.claude-sonnet-5-5", "anthropic.claude-sonnet-5-5"),
						profile("global.anthropic.claude-sonnet-5-5", "anthropic.claude-sonnet-5-5")),
				GBaseChatModelChoice::new);

		assertEquals(List.of("amazon.titan-text-express-v1", "us.anthropic.claude-sonnet-5-5",
				"global.anthropic.claude-sonnet-5-5").stream().sorted().toList(),
				choices.stream().map(GBaseChatModelChoice::getCode).sorted().toList());
		GBaseChatModelChoice us = choices.stream().filter(c -> c.getCode().startsWith("us.")).findFirst().get();
		assertEquals("Claude Sonnet 5.5 (Anthropic, us inference profile)", us.getDescription());
		assertTrue(us.getMetaInfos().getSupportsVision());
	}

	@Test
	void aLegacyModelSaysWhenItsLifeEnds() {
		List<GBaseChatModelChoice> choices = BedrockFoundationModelsLookupService.toChoices(
				List.of(model("anthropic.claude-v2", "Claude 2", "LEGACY", "ON_DEMAND")), List.of(),
				GBaseChatModelChoice::new);

		assertEquals("Claude 2 (Anthropic) (legacy, end of life 2026-12-01)", choices.get(0).getDescription());
		assertTrue(choices.get(0).getMetaInfos().getDeprecated());
	}
}
