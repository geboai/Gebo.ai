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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.aws_bedrock.config.BedrockModelsLibrary;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the Bedrock segment of the models library: the Claude models refusing any
 * sampling parameter are covered under their base ids and their cross-region profile
 * ids, the older ones are not.
 */
class BedrockModelsLibrarySegmentTest {

	@Test
	void claudeModelsRefusingSamplingAreCoveredUnderEveryId() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(
				GProviderModelsLibrary.load(BedrockModelsLibrary.YML, BedrockModelsLibrary.PREFIX, BedrockModelsLibrary.class)));

		for (String id : List.of("anthropic.claude-sonnet-5-5", "us.anthropic.claude-sonnet-5-5",
				"global.anthropic.claude-haiku-5-5", "jp.anthropic.claude-haiku-5-5", "eu.anthropic.claude-opus-5-5",
				"anthropic.claude-opus-4-7", "us.anthropic.claude-fable-5-1")) {
			assertEquals(Sampling.REJECTED, library.findParameterRules("aws-bedrock", id).getSampling(), id);
		}
		assertNull(library.findParameterRules("aws-bedrock", "us.anthropic.claude-sonnet-4-5-20250929-v1:0"),
				"Sonnet 4.5 refuses sampling only with thinking on");
		assertNull(library.findParameterRules("aws-bedrock", "us.amazon.nova-pro-v1:0"));
		assertNull(library.findParameterRules("anthropic", "claude-sonnet-5-5"), "a rule is its provider's only");
	}
}
