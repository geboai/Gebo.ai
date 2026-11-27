/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.google_vertex.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.google_vertex.config.GoogleVertexModelsLibrary;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the Google Vertex segment of the models library: the retirement dates the
 * Vertex models api does not give, for the chat models with an announced one.
 */
class GoogleVertexModelsLibrarySegmentTest {

	@Test
	void announcedRetirementsAreKnown() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(GProviderModelsLibrary
				.load(GoogleVertexModelsLibrary.YML, GoogleVertexModelsLibrary.PREFIX, GoogleVertexModelsLibrary.class)));

		ModelMetaInfo flash = library.findByProviderIdAndModelId("google-vertex", "gemini-2.5-flash");
		assertTrue(flash.getDeprecated());
		assertEquals("2026-10-20", flash.getRetirementDate());
		assertEquals("gemini-3.8-flash", flash.getReplacementModel());
		// moved by Google to January 28, 2027 (its Vertex model page, read 2026-11-26)
		assertEquals("2027-01-28",
				library.findByProviderIdAndModelId("google-vertex", "gemini-3.6-flash").getRetirementDate());
		assertEquals("gemini-4-hydrogen",
				library.findByProviderIdAndModelId("google-vertex", "gemini-3.6-flash").getReplacementModel());
		assertEquals(1048576,
				library.findByProviderIdAndModelId("google-vertex", "gemini-3.8-flash").getContextLength());
		assertNull(library.findByProviderIdAndModelId("google-vertex", "gemini-3.8-flash").getRetirementDate(),
				"no retirement announced");
		assertEquals(ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling.IGNORED,
				library.findParameterRules("google-vertex", "gemini-3.8-flash").getSampling(),
				"Gemini 3.6 Flash and later ignore the sampling parameters");
		assertEquals(ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling.IGNORED,
				library.findParameterRules("google-vertex", "gemini-4-hydrogen").getSampling());
		assertEquals(ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling.PROVIDER_DEFAULT,
				library.findParameterRules("google-vertex", "gemini-3.5-flash").getSampling(),
				"Gemini 3 up to 3.5 keeps Google's temperature 1.0");
		assertEquals(2.0, library.findTemperatureRange("google-vertex", "gemini-3.5-flash").getMax());
		assertEquals(1.0, library.findTopPRange("google-vertex", "gemini-3.1-pro-preview").getMax());
		assertNull(library.findTemperatureRange("google-vertex", "gemini-2.5-flash"), "no range given for 2.5");
		assertNull(library.findParameterRules("google-vertex", "gemini-2.5-flash"));
	}

	@Test
	void theGeminiModelsTakeEveryInput() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(GProviderModelsLibrary
				.load(GoogleVertexModelsLibrary.YML, GoogleVertexModelsLibrary.PREFIX, GoogleVertexModelsLibrary.class)));
		for (String id : List.of("gemini-3.8-flash", "gemini-3.5-flash", "gemini-3.1-flash-lite", "gemini-2.5-flash")) {
			assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.AUDIO,
					GModelInputModality.VIDEO, GModelInputModality.DOCUMENT),
					library.findByProviderIdAndModelId("google-vertex", id).getInputModalities(), id);
		}
	}
}
