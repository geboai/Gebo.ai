/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.mistralai.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.mistralai.config.MistralModelsLibrary;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/** Pins the Mistral segment of the models library this module ships. */
class MistralModelsLibrarySegmentTest {

	@Test
	void theMistralSegmentCarriesTheDocumentedEfforts() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(),
				List.of(GProviderModelsLibrary.load(MistralModelsLibrary.YML, MistralModelsLibrary.PREFIX, MistralModelsLibrary.class)));

		assertTrue(library.findParameterRules("mistralai", "mistral-small-latest").acceptsEffort("high"));
		assertFalse(library.findParameterRules("mistralai", "zai-glm-5-3").acceptsEffort("none"));
		assertNull(library.findParameterRules("mistralai", "ministral-8b-latest"), "undocumented: no rule");
	}
}
