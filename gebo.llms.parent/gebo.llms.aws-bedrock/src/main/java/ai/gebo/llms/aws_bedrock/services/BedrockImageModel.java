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
import java.util.stream.Collectors;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

/**
 * Spring AI {@link ImageModel} implementation for AWS Bedrock image generation
 * models. Spring AI 2.0 does not ship a Bedrock image model, so this adapter
 * drives the AWS SDK {@code InvokeModel} operation directly and understands the
 * two dominant request/response shapes on Bedrock:
 * <ul>
 * <li>Amazon (Titan Image / Nova Canvas): {@code textToImageParams} /
 * {@code imageGenerationConfig} request, {@code images[]} base64 response;</li>
 * <li>Stability AI: {@code text_prompts} request, {@code artifacts[].base64}
 * response.</li>
 * </ul>
 */
public class BedrockImageModel implements ImageModel {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private final BedrockRuntimeClient client;
	private final String modelId;
	private final Integer height;
	private final Integer width;
	private final Double cfgScale;
	private final Long seed;

	public BedrockImageModel(BedrockRuntimeClient client, String modelId, Integer height, Integer width,
			Double cfgScale, Long seed) {
		this.client = client;
		this.modelId = modelId;
		this.height = height;
		this.width = width;
		this.cfgScale = cfgScale;
		this.seed = seed;
	}

	@Override
	public ImageResponse call(ImagePrompt request) {
		String prompt = request.getInstructions().stream().map(ImageMessage::getText).collect(Collectors.joining(" "));
		try {
			String body = modelId.startsWith("stability.") ? buildStabilityBody(prompt) : buildAmazonBody(prompt);
			InvokeModelResponse response = client.invokeModel(InvokeModelRequest.builder()
					.modelId(modelId)
					.contentType("application/json")
					.accept("application/json")
					.body(SdkBytes.fromUtf8String(body))
					.build());
			JsonNode root = MAPPER.readTree(response.body().asUtf8String());
			List<String> base64Images = extractImages(root);
			List<ImageGeneration> generations = new ArrayList<>();
			for (String b64 : base64Images) {
				generations.add(new ImageGeneration(new Image(null, b64)));
			}
			return new ImageResponse(generations);
		} catch (Exception e) {
			throw new RuntimeException("AWS Bedrock image generation failed for model " + modelId, e);
		}
	}

	private String buildAmazonBody(String prompt) throws Exception {
		ObjectNode root = MAPPER.createObjectNode();
		root.put("taskType", "TEXT_IMAGE");
		ObjectNode params = root.putObject("textToImageParams");
		params.put("text", prompt);
		ObjectNode genConfig = root.putObject("imageGenerationConfig");
		genConfig.put("numberOfImages", 1);
		if (height != null) {
			genConfig.put("height", height);
		}
		if (width != null) {
			genConfig.put("width", width);
		}
		if (cfgScale != null) {
			genConfig.put("cfgScale", cfgScale);
		}
		if (seed != null) {
			genConfig.put("seed", seed);
		}
		return MAPPER.writeValueAsString(root);
	}

	private String buildStabilityBody(String prompt) throws Exception {
		ObjectNode root = MAPPER.createObjectNode();
		ArrayNode textPrompts = root.putArray("text_prompts");
		ObjectNode textPrompt = textPrompts.addObject();
		textPrompt.put("text", prompt);
		if (cfgScale != null) {
			root.put("cfg_scale", cfgScale);
		}
		if (seed != null) {
			root.put("seed", seed);
		}
		if (height != null) {
			root.put("height", height);
		}
		if (width != null) {
			root.put("width", width);
		}
		return MAPPER.writeValueAsString(root);
	}

	private List<String> extractImages(JsonNode root) {
		List<String> images = new ArrayList<>();
		JsonNode amazonImages = root.get("images");
		if (amazonImages != null && amazonImages.isArray()) {
			amazonImages.forEach(node -> images.add(node.asText()));
			return images;
		}
		JsonNode artifacts = root.get("artifacts");
		if (artifacts != null && artifacts.isArray()) {
			artifacts.forEach(node -> {
				JsonNode base64 = node.get("base64");
				if (base64 != null) {
					images.add(base64.asText());
				}
			});
		}
		return images;
	}
}
