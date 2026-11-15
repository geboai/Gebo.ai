/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.ollama.model.GOllamaDecisionModelChoice;
import ai.gebo.llms.ollama.model.GOllamaDecisionModelConfig;
import ai.gebo.llms.ollama.services.OllamaModelsLookupService.ModelsList;
import ai.gebo.llms.ollama.services.OllamaModelsLookupService.ShowParam;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * Pins the decision models lookup on the /api/tags and /api/show shapes documented at
 * https://docs.ollama.com/api: only the models whose capabilities contain "decision"
 * are offered, and a server too old to report capabilities offers none, unlike the
 * chat and embedding lookups, which then fall back to the model names.
 */
class OllamaDecisionModelsLookupTest {

	static final String BASE_URL = "http://ollama.test:11434";

	RestTemplateWrapperService rest;
	OllamaModelsLookupService lookup;
	final Map<String, Map<String, Object>> shows = new HashMap<>();

	@BeforeEach
	void setUp() throws Exception {
		rest = mock(RestTemplateWrapperService.class);
		lookup = new OllamaModelsLookupService(mock(IGeboSecretsAccessService.class), rest,
				mock(ILLMTypeFiltrerRepositoryPattern.class));
		when(rest.postForEntity(eq(new URI(BASE_URL + "/api/show")), any(ShowParam.class), eq(HashMap.class)))
				.thenAnswer(call -> {
					ShowParam asked = call.getArgument(1);
					Map<String, Object> show = shows.get(asked.name);
					return ResponseEntity.ok(show != null ? new HashMap<>(show) : new HashMap<>());
				});
	}

	void serving(String... names) throws Exception {
		ModelsList list = new ModelsList();
		for (String name : names) {
			list.models.add(OllamaModelsLookupServiceTest.model(name));
		}
		when(rest.getForEntity(new URI(BASE_URL + "/api/tags"), ModelsList.class)).thenReturn(ResponseEntity.ok(list));
	}

	static GOllamaDecisionModelConfig config() {
		GOllamaDecisionModelConfig config = new GOllamaDecisionModelConfig();
		config.setBaseUrl(BASE_URL);
		return config;
	}

	@Test
	void onlyTheModelsReportingTheDecisionCapabilityAreOffered() throws Exception {
		shows.put("d1-omni:600m", Map.of("capabilities", List.of("decision"), "model_info",
				Map.of("general.architecture", "d1", "d1.context_length", 32768)));
		shows.put("qwen3:8b", OllamaModelsLookupServiceTest.QWEN);
		shows.put("bge-m3:latest", OllamaModelsLookupServiceTest.BGE_M3);
		serving("d1-omni:600m", "qwen3:8b", "bge-m3:latest");

		OperationStatus<List<GOllamaDecisionModelChoice>> status = lookup.getDecisionModels(config());

		assertFalse(status.isHasErrorMessages());
		assertEquals(List.of("d1-omni:600m"),
				status.getResult().stream().map(GOllamaDecisionModelChoice::getCode).toList());
		GOllamaDecisionModelChoice d1 = status.getResult().get(0);
		assertEquals(32768, d1.getContextLength());
		assertEquals("d1-omni:600m (567M, F16)", d1.getDescription());
		assertTrue(d1.getModelDetails().containsKey("model_info"));
	}

	@Test
	void aServerNotReportingCapabilitiesOffersNoDecisionModel() throws Exception {
		shows.put("d1-omni:600m", Map.of("model_info", Map.of("d1.context_length", 32768)));
		serving("d1-omni:600m", "llama3:8b");

		OperationStatus<List<GOllamaDecisionModelChoice>> status = lookup.getDecisionModels(config());

		assertFalse(status.isHasErrorMessages());
		assertTrue(status.getResult().isEmpty(), "the name alone does not make a decision model");
	}

	@Test
	void aBaseUrlWithATrailingSlashAsksTheSameEndpoints() throws Exception {
		shows.put("d1-omni:600m", Map.of("capabilities", List.of("decision")));
		serving("d1-omni:600m");
		GOllamaDecisionModelConfig config = config();
		config.setBaseUrl(BASE_URL + "/");

		assertEquals(1, lookup.getDecisionModels(config).getResult().size());
	}

	@Test
	void anUnreachableServerBecomesAUserMessage() throws Exception {
		when(rest.getForEntity(new URI(BASE_URL + "/api/tags"), ModelsList.class))
				.thenThrow(new ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException(
						"Connection refused"));

		OperationStatus<List<GOllamaDecisionModelChoice>> status = lookup.getDecisionModels(config());

		assertTrue(status.isHasErrorMessages());
	}
}
