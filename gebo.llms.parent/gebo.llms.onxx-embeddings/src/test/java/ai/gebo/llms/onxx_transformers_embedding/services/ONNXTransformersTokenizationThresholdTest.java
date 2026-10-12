/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.onxx_transformers_embedding.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ai.gebo.llms.abstraction.layer.services.impl.GModelChoiceMetaInfoEnricherServiceImpl;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;
import ai.gebo.llms.onxx_transformers_embedding.config.OnnxTransformersModelsLibrary;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelChoice;

/**
 * The bundled all-MiniLM-L6-v2 truncates its input at 256 word pieces (its model
 * card): the choice the lookup offers carries it from the models library, and a
 * choice saved without it gets it too.
 */
class ONNXTransformersTokenizationThresholdTest {

	@Test
	void theBundledModelTellsItsInputLimit() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(),
				List.of(GProviderModelsLibrary.load(OnnxTransformersModelsLibrary.YML, OnnxTransformersModelsLibrary.PREFIX,
						OnnxTransformersModelsLibrary.class)));
		GModelChoiceMetaInfoEnricherServiceImpl enricher = new GModelChoiceMetaInfoEnricherServiceImpl();
		ReflectionTestUtils.setField(enricher, "dao", library);

		GONNXTransformersEmbeddingModelChoice offered = new ONNXTransformersModelsLookupService(enricher)
				.getEmbeddingModels(null).getResult().get(0);
		assertEquals(256, offered.getOptimalTokenizationParam());
		assertEquals(256, offered.getContextLength());
		assertEquals(256, ONNXTransformersEmbeddingModelConfigurationSupportService.tokenizationThreshold(offered));

		GONNXTransformersEmbeddingModelChoice saved = new GONNXTransformersEmbeddingModelChoice();
		saved.setCode(ONNXTransformersModelsLookupService.BUNDLED_MODEL);
		assertEquals(256, ONNXTransformersEmbeddingModelConfigurationSupportService.tokenizationThreshold(saved));
		assertEquals(256, ONNXTransformersEmbeddingModelConfigurationSupportService.tokenizationThreshold(null));
	}
}
