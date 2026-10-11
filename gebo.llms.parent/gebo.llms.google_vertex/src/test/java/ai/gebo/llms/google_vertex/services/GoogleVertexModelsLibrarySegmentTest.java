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
		assertEquals("2026-11-19",
				library.findByProviderIdAndModelId("google-vertex", "gemini-3.6-flash").getRetirementDate());
		assertNull(library.findByProviderIdAndModelId("google-vertex", "gemini-3.8-flash"), "no retirement announced");
		assertEquals(ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling.PROVIDER_DEFAULT,
				library.findParameterRules("google-vertex", "gemini-3.8-flash").getSampling(),
				"Gemini 3 keeps Google's temperature 1.0");
		assertNull(library.findParameterRules("google-vertex", "gemini-2.5-flash"));
	}
}
