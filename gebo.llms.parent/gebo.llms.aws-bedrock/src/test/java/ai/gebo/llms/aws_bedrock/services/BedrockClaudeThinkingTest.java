/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.aws_bedrock.config.BedrockModelsLibrary;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;
import software.amazon.awssdk.core.SdkRequest;
import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.core.interceptor.Context;
import software.amazon.awssdk.core.interceptor.ExecutionAttributes;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseStreamRequest;

/**
 * Claude's thinking reaches the Converse requests in the shape Bedrock documents, and
 * each model is asked only for what it accepts.
 */
class BedrockClaudeThinkingTest {

	static SdkRequest modified(Document thinking, SdkRequest request) {
		return BedrockClaudeThinking.interceptor(thinking).modifyRequest(new Context.ModifyRequest() {
			@Override
			public SdkRequest request() {
				return request;
			}
		}, new ExecutionAttributes());
	}

	@Test
	void theThinkingGoesInTheAdditionalFields() {
		Document low = BedrockClaudeThinking.fields("low");
		assertEquals("adaptive", low.asMap().get("thinking").asMap().get("type").asString());
		assertEquals("low", low.asMap().get("output_config").asMap().get("effort").asString());
		Document off = BedrockClaudeThinking.fields(ModelParameterRules.NO_REASONING_EFFORT);
		assertEquals("disabled", off.asMap().get("thinking").asMap().get("type").asString());
		assertFalse(off.asMap().containsKey("output_config"), "no effort with the thinking off");

		ConverseRequest converse = ConverseRequest.builder().modelId("global.anthropic.claude-haiku-5-5")
				.additionalModelRequestFields(Document.mapBuilder().putString("top_k", "5").build()).build();
		ConverseRequest sent = (ConverseRequest) modified(low, converse);
		assertEquals("5", sent.additionalModelRequestFields().asMap().get("top_k").asString(), "its own fields kept");
		assertEquals("low", sent.additionalModelRequestFields().asMap().get("output_config").asMap().get("effort")
				.asString());

		ConverseStreamRequest stream = (ConverseStreamRequest) modified(off,
				ConverseStreamRequest.builder().modelId("global.anthropic.claude-haiku-5-5").build());
		assertEquals("disabled",
				stream.additionalModelRequestFields().asMap().get("thinking").asMap().get("type").asString());
	}

	@Test
	void otherRequestsAreLeftAlone() {
		SdkRequest other = software.amazon.awssdk.services.bedrockruntime.model.ApplyGuardrailRequest.builder()
				.guardrailIdentifier("g").build();
		assertSame(other, modified(BedrockClaudeThinking.fields("high"), other));
	}

	@Test
	void eachModelIsAskedForWhatItAccepts() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(
				GProviderModelsLibrary.load(BedrockModelsLibrary.YML, BedrockModelsLibrary.PREFIX, BedrockModelsLibrary.class)));
		assertFalse(library.findParameterRules("aws-bedrock", "global.anthropic.claude-opus-5-5").acceptsEffort("none"),
				"Opus 5.5 thinks always");
		assertFalse(library.findParameterRules("aws-bedrock", "global.anthropic.claude-sonnet-5-5").acceptsEffort("none"));
		assertTrue(library.findParameterRules("aws-bedrock", "global.anthropic.claude-haiku-5-5").acceptsEffort("none"));
		assertTrue(library.findParameterRules("aws-bedrock", "us.anthropic.claude-opus-5").acceptsEffort("xhigh"));
		assertFalse(library.findParameterRules("aws-bedrock", "anthropic.claude-sonnet-5").acceptsEffort("xhigh"));
		assertTrue(library.findParameterRules("aws-bedrock", "anthropic.claude-sonnet-5").acceptsEffort("none"));
		assertFalse(library.findParameterRules("aws-bedrock", "global.anthropic.claude-fable-5-1").acceptsEffort("none"));
		assertFalse(library.findParameterRules("aws-bedrock", "anthropic.claude-mythos-preview").acceptsEffort("xhigh"));
		assertFalse(library.findParameterRules("aws-bedrock", "us.anthropic.claude-opus-4-7").knowsEfforts(),
				"no efforts known: no thinking sent");
	}
}
