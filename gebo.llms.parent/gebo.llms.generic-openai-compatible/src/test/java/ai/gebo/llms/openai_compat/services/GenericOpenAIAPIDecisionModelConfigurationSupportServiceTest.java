/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIDecisionModelTypeConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins the decision models of a provider declared in providers.yml: where they are
 * called (the configured url, else the provider's), when they may be called without a
 * key, and where they are listed from (the provider's models list provider, else its
 * models endpoint), the provider's default model always being offered.
 */
class GenericOpenAIAPIDecisionModelConfigurationSupportServiceTest {

	IGeboSecretsAccessService secrets;
	IGOpenAIApiUtil api;
	ModelsListProviderProxyService modelsLists;
	GenericOpenAIDecisionModelTypeConfig type;
	GenericOpenAIAPIDecisionModelConfigurationSupportService service;

	@BeforeEach
	void setUp() throws Exception {
		secrets = mock(IGeboSecretsAccessService.class);
		api = mock(IGOpenAIApiUtil.class);
		modelsLists = mock(ModelsListProviderProxyService.class);
		type = new GenericOpenAIDecisionModelTypeConfig();
		type.setCode("decision-generic-openai");
		type.setDescription("Decisions API server");
		type.setProviderId("generic-openai");
		type.setBaseUrl("https://decisions.example.test/v1");
		service = new GenericOpenAIAPIDecisionModelConfigurationSupportService(type, secrets, api, modelsLists,
				mock(IGLlmsServiceClientsProviderFactory.class), mock(ModelRuntimeConfigureHandler.class),
				ObservationRegistry.NOOP);
		GeboTokenContent token = new GeboTokenContent();
		token.setToken("provider-token");
		when(secrets.getSecretContentById("provider-key")).thenReturn(token);
	}

	static GenericOpenAIAPIDecisionModelConfig config(String secretCode, String baseUrl) {
		GenericOpenAIAPIDecisionModelConfig config = new GenericOpenAIAPIDecisionModelConfig();
		config.setApiSecretCode(secretCode);
		config.setBaseUrl(baseUrl);
		return config;
	}

	static OpenAIModel model(String id) {
		OpenAIModel model = new OpenAIModel();
		model.setId(id);
		return model;
	}

	static List<String> codes(OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> status) {
		return status.getResult().stream().map(GenericOpenAIAPIDecisionModelChoice::getCode).toList();
	}

	@Test
	void theConfiguredUrlWinsOverTheProvidersOwn() {
		assertEquals("http://localhost:8080/v1", service.baseUrlOf(config(null, "http://localhost:8080/v1")));
		assertEquals("https://decisions.example.test/v1", service.baseUrlOf(config(null, null)));
		assertEquals("https://decisions.example.test/v1", service.baseUrlOf(config(null, "  ")),
				"a blank url is no url");
		type.setBaseUrl(null);
		assertNull(service.baseUrlOf(config(null, null)));
	}

	@Test
	void anonymousCallsOnlyWhenTheProviderDeclaresOptionalAuthentication() throws Exception {
		assertThrows(LLMConfigException.class, () -> service.apiKeyOf(config(null, null)));
		type.setOptionalAuthentication(true);
		assertNull(service.apiKeyOf(config(null, null)));
		assertEquals("provider-token", service.apiKeyOf(config("provider-key", null)),
				"a configured key is used even when the provider accepts none");
	}

	@Test
	void withoutAModelsListProviderTheModelsEndpointIsAskedAndFiltered() throws Exception {
		when(api.getModels(any())).thenReturn(List.of(model("d1-omni-600m"), model("llama-3.3-70b"),
				model("decision-small")));

		OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> status = service
				.getModelChoices(config("provider-key", null));

		assertFalse(status.isHasErrorMessages());
		assertEquals(List.of("d1-omni-600m", "decision-small"), codes(status));
		ArgumentCaptor<OpenAIApiConfig> asked = ArgumentCaptor.forClass(OpenAIApiConfig.class);
		verify(api).getModels(asked.capture());
		assertEquals("https://decisions.example.test/v1", asked.getValue().getBasePath());
		assertEquals("provider-token", asked.getValue().getApiKey());
		verify(modelsLists, never()).geModels(any(), any(), any(), any());
	}

	@Test
	void theDeclaredDefaultModelIsAlwaysOfferedOnce() throws Exception {
		type.setDefaultModel("decision-small");
		when(api.getModels(any())).thenReturn(List.of(model("decision-small")));
		assertEquals(List.of("decision-small"), codes(service.getModelChoices(config("provider-key", null))));

		when(api.getModels(any())).thenReturn(List.of());
		assertEquals(List.of("decision-small"), codes(service.getModelChoices(config("provider-key", null))),
				"offered even when the endpoint lists none");
	}

	@Test
	void aModelsListProviderReplacesTheModelsEndpoint() throws Exception {
		type.setModelsListProvider("vendor-catalog");
		type.setDefaultModel("decision-small");
		GenericOpenAIAPIDecisionModelChoice listed = new GenericOpenAIAPIDecisionModelChoice();
		listed.setCode("catalog-decision-1");
		List<GenericOpenAIAPIDecisionModelChoice> catalog = new ArrayList<>(List.of(listed));
		when(modelsLists.geModels(eq("vendor-catalog"), any(), eq(GenericOpenAIAPIDecisionModelChoice.class),
				eq(type))).thenReturn(OperationStatus.of(catalog));

		OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> status = service
				.getModelChoices(config("provider-key", null));

		assertEquals(List.of("catalog-decision-1", "decision-small"), codes(status));
		verify(api, never()).getModels(any());
	}

	@Test
	void aMissingKeyOfAProviderRequiringOneIsReported() throws Exception {
		OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> status = service.getModelChoices(config(null, null));

		assertTrue(status.isHasErrorMessages());
		assertEquals("Decisions API server api key",
				status.getMessages().stream().filter(m -> m.getSeverity() == GUserMessage.MsgServerity.error)
						.findFirst().orElseThrow().getSummary());
		verify(api, never()).getModels(any());
	}

	@Test
	void theNamingRuleOfTheModelsEndpoint() {
		assertTrue(GenericOpenAIAPIDecisionModelConfigurationSupportService.isDecisionModel("D1-Omni:600m"));
		assertTrue(GenericOpenAIAPIDecisionModelConfigurationSupportService.isDecisionModel("gpt-6-luna"));
		assertFalse(GenericOpenAIAPIDecisionModelConfigurationSupportService.isDecisionModel("qwen3-32b"));
		assertFalse(GenericOpenAIAPIDecisionModelConfigurationSupportService.isDecisionModel(null));
	}
}
