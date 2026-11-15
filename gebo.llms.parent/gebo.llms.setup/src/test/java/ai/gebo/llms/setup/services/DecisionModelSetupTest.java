/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.setup.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;
import ai.gebo.llms.setup.config.LLMModelPresetChoice;
import ai.gebo.llms.setup.config.LLMSModelsPresets;
import ai.gebo.llms.setup.config.LLMSVendor;
import ai.gebo.llms.setup.config.LLMSVendorInfo;
import ai.gebo.llms.setup.config.LLMSVendorsSetupConfig;
import ai.gebo.llms.setup.config.ModelType;
import ai.gebo.llms.setup.model.ComponentLLMSStatus;
import ai.gebo.llms.setup.model.LLMAutoconfigureCreationData;
import ai.gebo.llms.setup.model.LLMCreateModelData;
import ai.gebo.llms.setup.model.LLMSModelsCreationResult;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;

/**
 * Pins the decision models in the LLM setup: an optional kind, like the images. A
 * default decision model shows in the setup status with its model and provider; the
 * autoconfigure creates one only when it is asked for, and validates the preset's code
 * against the provider's lookup - present, created; absent, reported unresolved with
 * the choices the provider offers.
 */
class DecisionModelSetupTest {

	static final String HANDLER = "decision-ollama";

	final Map<Class<?>, Object> mocks = new HashMap<>();
	LLMSVendorsSetupConfig library;
	GeboLLMSSetupService service;
	IGDecisionModelRuntimeConfigurationDao decisionDao;
	IGDecisionModelConfigurationSupportService<GBaseDecisionModelChoice, GBaseDecisionModelConfig<GBaseDecisionModelChoice>> support;
	GDecisionModelType type;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		library = new LLMSVendorsSetupConfig();
		library.setVendors(List.of(ollamaWithDecisionPreset()));
		Constructor<?> constructor = GeboLLMSSetupService.class.getConstructors()[0];
		Object[] arguments = new Object[constructor.getParameterCount()];
		for (int i = 0; i < arguments.length; i++) {
			Class<?> parameter = constructor.getParameterTypes()[i];
			arguments[i] = parameter == LLMSVendorsSetupConfig.class ? library
					: mocks.computeIfAbsent(parameter, org.mockito.Mockito::mock);
		}
		service = (GeboLLMSSetupService) constructor.newInstance(arguments);
		when(((IGSecurityAuditLoggerService) mocks.get(IGSecurityAuditLoggerService.class)).newSecurityEvent())
				.thenReturn(mock(SecurityEvent.class));
		decisionDao = (IGDecisionModelRuntimeConfigurationDao) mocks.get(IGDecisionModelRuntimeConfigurationDao.class);
		type = new GDecisionModelType();
		type.setCode(HANDLER);
		support = mock(IGDecisionModelConfigurationSupportService.class);
		when(support.getType()).thenReturn(type);
		when(support.createBaseConfiguration(any())).thenAnswer(call -> {
			GBaseDecisionModelConfig<GBaseDecisionModelChoice> config = new GBaseDecisionModelConfig<>();
			config.setModelTypeCode(HANDLER);
			config.setChoosedModel(choice(call.getArgument(0)));
			return config;
		});
		when(support.insertAndConfigureModel(any(), anyString())).thenAnswer(call -> {
			GBaseModelConfig<?> config = call.getArgument(0);
			config.setCode("decision-ollama-" + call.getArgument(1));
			return OperationStatus.of(config);
		});
		when(((IGDecisionModelConfigurationSupportServiceRepositoryPattern) mocks
				.get(IGDecisionModelConfigurationSupportServiceRepositoryPattern.class)).findByCode(HANDLER))
				.thenReturn(support);
	}

	static GBaseDecisionModelChoice choice(String code) {
		GBaseDecisionModelChoice choice = new GBaseDecisionModelChoice();
		choice.setCode(code);
		return choice;
	}

	static LLMSVendor ollamaWithDecisionPreset() {
		LLMSVendor vendor = new LLMSVendor();
		LLMSVendorInfo info = new LLMSVendorInfo();
		info.setVendorId("ollama");
		info.setName("Ollama");
		info.setRequiresCustomUrl(true);
		vendor.setVendorInfo(info);
		LLMSModelsPresets preset = new LLMSModelsPresets();
		preset.setType(ModelType.DECISION);
		preset.setServiceHandler(HANDLER);
		preset.setDoModelsLookup(true);
		LLMModelPresetChoice d1 = new LLMModelPresetChoice();
		d1.setCode("d1-omni:600m");
		d1.setDefaultChoice(true);
		preset.getChoices().add(d1);
		vendor.getPresets().add(preset);
		return vendor;
	}

	@SuppressWarnings("unchecked")
	@Test
	void aDefaultDecisionModelShowsInTheSetupStatus() throws Exception {
		GBaseDecisionModelConfig<GBaseDecisionModelChoice> config = new GBaseDecisionModelConfig<>();
		config.setDefaultModel(true);
		config.setChoosedModel(choice("d1-omni:600m"));
		IGConfigurableDecisionModel configured = mock(IGConfigurableDecisionModel.class);
		when(configured.getConfig()).thenReturn(config);
		when(configured.getType()).thenReturn(type);
		when(decisionDao.getConfigurations()).thenReturn(List.of(configured));

		ComponentLLMSStatus status = service.getLLMSSetupStatus();

		assertTrue(status.decisionModelSetup);
		assertEquals("d1-omni:600m", status.decisionModelCode);
		assertEquals("ollama", status.decisionModelProviderId, "the provider of the preset owning the handler");
		assertFalse(status.isSetup, "the decision model does not make an installation set up");
		assertTrue(service.getActualConfiguration().getDecisionModelExists());
	}

	@Test
	void noDecisionModelNoDecisionSlot() {
		ComponentLLMSStatus status = service.getLLMSSetupStatus();

		assertFalse(status.decisionModelSetup);
		assertNull(status.decisionModelCode);
	}

	@Test
	void theAutoconfigureCreatesTheDecisionModelThePresetOffers() throws Exception {
		when(support.getModelChoices(any())).thenReturn(OperationStatus.of(List.of(choice("d1-omni:600m"))));
		LLMAutoconfigureCreationData data = new LLMAutoconfigureCreationData();
		data.setVendorId("ollama");
		data.setSecretId("ollama-secret");
		data.setDecisionModel("d1-omni:600m");

		OperationStatus<List<GBaseModelConfig>> created = service.runAutoConfigure(data);

		assertFalse(created.isHasErrorMessages(), String.valueOf(created.getMessages()));
		assertEquals(1, created.getResult().size());
		GBaseModelConfig<?> decision = created.getResult().get(0);
		assertEquals("decision-ollama-d1-omni:600m", decision.getCode());
		assertTrue(decision.getDefaultModel(), "the decision model the wizard creates is the default one");
		assertEquals("ollama-secret", decision.getApiSecretCode());
	}

	@Test
	void theAutoconfigureCreatesNoDecisionModelUnlessAsked() throws Exception {
		LLMAutoconfigureCreationData data = new LLMAutoconfigureCreationData();
		data.setVendorId("ollama");
		data.setSecretId("ollama-secret");

		OperationStatus<List<GBaseModelConfig>> created = service.runAutoConfigure(data);

		assertTrue(created.getResult().isEmpty());
		verify(support, never()).insertAndConfigureModel(any(), anyString());
	}

	@Test
	void aPresetModelTheProviderDoesNotOfferIsReportedWithTheChoicesItOffers() throws Exception {
		when(support.getModelChoices(any())).thenReturn(OperationStatus.of(List.of(choice("d1-omni:2b"))));
		LLMCreateModelData request = new LLMCreateModelData();
		request.setType(ModelType.DECISION);
		request.setServiceHandler(HANDLER);
		request.setModelCode("d1-omni:600m");
		request.setSecretId("ollama-secret");
		request.setDoModelsLookup(true);
		request.setSetAsDefaultModel(true);

		OperationStatus<LLMSModelsCreationResult> creation = service.createLLMS(List.of(request));

		assertTrue(creation.getResult().getCreated().isEmpty());
		assertEquals(1, creation.getResult().getUnresolved().size());
		LLMSModelsCreationResult.LLMUnresolvedModel unresolved = creation.getResult().getUnresolved().get(0);
		assertEquals(ModelType.DECISION, unresolved.getType());
		assertEquals("d1-omni:600m", unresolved.getRequestedModelCode());
		assertEquals("d1-omni:2b", unresolved.getAvailableChoices().get(0).getCode());
		verify(support, never()).insertAndConfigureModel(any(), anyString());
		assertTrue(creation.getMessages().stream().noneMatch(m -> m.getSeverity() == GUserMessage.MsgServerity.error));
	}
}
