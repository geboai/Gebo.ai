/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.openai.OpenAiDecisionOptions;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.model.GOpenAIDecisionModelChoice;
import ai.gebo.llms.openai.model.GOpenAIDecisionModelConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins how OpenAI's decision models are looked up: the models endpoint's decision
 * models are kept, the other models dropped, Spring AI's default decision model is
 * always offered once, and a missing key or a failing lookup becomes a message for
 * the user instead of an exception.
 */
class OpenAIDecisionModelConfigurationSupportServiceTest {

	IGeboSecretsAccessService secrets;
	IGOpenAIApiUtil api;
	OpenAIDecisionModelConfigurationSupportService service;

	@BeforeEach
	void setUp() throws Exception {
		secrets = mock(IGeboSecretsAccessService.class);
		api = mock(IGOpenAIApiUtil.class);
		service = new OpenAIDecisionModelConfigurationSupportService(secrets, api,
				mock(IGLlmsServiceClientsProviderFactory.class), mock(ModelRuntimeConfigureHandler.class),
				ObservationRegistry.NOOP);
		GeboTokenContent token = new GeboTokenContent();
		token.setToken("sk-test");
		when(secrets.getSecretContentById("openai-key")).thenReturn(token);
	}

	static OpenAIModel model(String id, Boolean active) {
		OpenAIModel model = new OpenAIModel();
		model.setId(id);
		model.setActive(active);
		return model;
	}

	static GOpenAIDecisionModelConfig configWithKey(String secretCode) {
		GOpenAIDecisionModelConfig config = new GOpenAIDecisionModelConfig();
		config.setApiSecretCode(secretCode);
		return config;
	}

	static List<String> codes(OperationStatus<List<GOpenAIDecisionModelChoice>> status) {
		return status.getResult().stream().map(GOpenAIDecisionModelChoice::getCode).toList();
	}

	static GUserMessage firstError(OperationStatus<?> status) {
		return status.getMessages().stream().filter(m -> m.getSeverity() == GUserMessage.MsgServerity.error)
				.findFirst().orElseThrow();
	}

	@Test
	void theDecisionModelsAreTheOnesNamedAfterTheDecisionsApi() {
		assertTrue(OpenAIDecisionModelConfigurationSupportService.isDecisionModel("gpt-6-luna"));
		assertTrue(OpenAIDecisionModelConfigurationSupportService.isDecisionModel("GPT-6-Luna-mini"));
		assertTrue(OpenAIDecisionModelConfigurationSupportService.isDecisionModel("decision-1"));
		assertFalse(OpenAIDecisionModelConfigurationSupportService.isDecisionModel("gpt-6.1-sol"));
		assertFalse(OpenAIDecisionModelConfigurationSupportService.isDecisionModel("text-embedding-3-large"));
		assertFalse(OpenAIDecisionModelConfigurationSupportService.isDecisionModel(null));
	}

	@Test
	void aListedDecisionModelIsKeptAndAChatModelDropped() throws Exception {
		when(api.getModels(any())).thenReturn(List.of(model("gpt-6.1-sol", true), model("gpt-6-luna-mini", true),
				model("gpt-6-luna-old", false), model("whisper-1", null)));

		OperationStatus<List<GOpenAIDecisionModelChoice>> status = service.getModelChoices(configWithKey("openai-key"));

		assertFalse(status.isHasErrorMessages());
		assertEquals(List.of("gpt-6-luna-mini", OpenAiDecisionOptions.DEFAULT_DECISION_MODEL), codes(status),
				"the inactive model is dropped, Spring AI's default is appended");
		GOpenAIDecisionModelChoice first = status.getResult().get(0);
		assertEquals("openai", first.getMetaInfos().getProviderId());
		assertEquals(OpenAIDecisionModelConfigurationSupportService.INFORMATIVE_URL, first.getInformativeUrl());
	}

	@Test
	void theDefaultDecisionModelIsOfferedOnlyOnce() throws Exception {
		when(api.getModels(any())).thenReturn(List.of(model(OpenAiDecisionOptions.DEFAULT_DECISION_MODEL, true)));

		assertEquals(List.of(OpenAiDecisionOptions.DEFAULT_DECISION_MODEL),
				codes(service.getModelChoices(configWithKey("openai-key"))));
	}

	@Test
	void theLookupAsksTheConfiguredUrlWithTheConfiguredKey() throws Exception {
		when(api.getModels(any())).thenReturn(List.of());
		GOpenAIDecisionModelConfig config = configWithKey("openai-key");
		config.setBaseUrl("https://proxy.example.test/v1");

		service.getModelChoices(config);

		ArgumentCaptor<OpenAIApiConfig> asked = ArgumentCaptor.forClass(OpenAIApiConfig.class);
		verify(api).getModels(asked.capture());
		assertEquals("sk-test", asked.getValue().getApiKey());
		assertEquals("https://proxy.example.test/v1", asked.getValue().getBasePath());
		assertEquals("openai", asked.getValue().getProviderId());
	}

	@Test
	void aLookupErrorBecomesAUserMessage() throws Exception {
		when(api.getModels(any())).thenThrow(new GeboRestIntegrationException("401 Unauthorized"));

		OperationStatus<List<GOpenAIDecisionModelChoice>> status = service.getModelChoices(configWithKey("openai-key"));

		assertTrue(status.isHasErrorMessages());
		assertEquals("Cannot access the OpenAI models list", firstError(status).getSummary());
		assertTrue(status.getResult().isEmpty(), "no model is offered when the account cannot be read");
	}

	@Test
	void aMissingSecretBecomesAnApiKeyMessage() throws Exception {
		OperationStatus<List<GOpenAIDecisionModelChoice>> status = service.getModelChoices(configWithKey(null));

		assertTrue(status.isHasErrorMessages());
		assertEquals("OpenAI api key", firstError(status).getSummary());
		verify(api, never()).getModels(any());
		verify(secrets, never()).getSecretContentById(anyString());
	}
}
