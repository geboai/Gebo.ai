/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.setup.services;

import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.AUDIO;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.setup.config.LLMModelPresetChoice;
import ai.gebo.llms.setup.config.LLMSModelsPresets;
import ai.gebo.llms.setup.config.LLMSVendor;
import ai.gebo.llms.setup.config.LLMSVendorInfo;
import ai.gebo.llms.setup.config.LLMSVendorsSetupConfig;
import ai.gebo.llms.setup.config.ModelType;
import ai.gebo.llms.setup.model.LLMCreateModelData;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;

/**
 * A chat model the setup creates says what it accepts as input: the preset's
 * modalities when it gives them, else the chosen model's metadata; unset when
 * neither knows, which means text only.
 */
class ChatModelSetupInputModalitiesTest {
	static final String HANDLER = "chatmodel-test";

	final Map<Class<?>, Object> mocks = new HashMap<>();
	final List<GBaseChatModelConfig<?>> inserted = new ArrayList<>();
	GeboLLMSSetupService service;
	ModelMetaInfo chosenModelMeta;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeEach
	void setUp() throws Exception {
		final LLMSVendorsSetupConfig library = new LLMSVendorsSetupConfig();
		library.setVendors(List.of(vendor()));
		final Constructor<?> constructor = GeboLLMSSetupService.class.getConstructors()[0];
		final Object[] arguments = new Object[constructor.getParameterCount()];
		for (int i = 0; i < arguments.length; i++) {
			final Class<?> parameter = constructor.getParameterTypes()[i];
			arguments[i] = parameter == LLMSVendorsSetupConfig.class ? library
					: mocks.computeIfAbsent(parameter, org.mockito.Mockito::mock);
		}
		service = (GeboLLMSSetupService) constructor.newInstance(arguments);
		when(((IGSecurityAuditLoggerService) mocks.get(IGSecurityAuditLoggerService.class)).newSecurityEvent())
				.thenReturn(mock(SecurityEvent.class));
		final GChatModelType type = new GChatModelType();
		type.setCode(HANDLER);
		final IGChatModelConfigurationSupportService support = mock(IGChatModelConfigurationSupportService.class);
		when(support.getType()).thenReturn(type);
		when(support.createBaseConfiguration(any())).thenAnswer(call -> {
			final GBaseChatModelConfig<GBaseChatModelChoice> config = new GBaseChatModelConfig<>();
			config.setModelTypeCode(HANDLER);
			final GBaseChatModelChoice choice = new GBaseChatModelChoice();
			choice.setCode(call.getArgument(0));
			choice.setMetaInfos(chosenModelMeta);
			config.setChoosedModel(choice);
			return config;
		});
		when(support.insertAndConfigure(any())).thenAnswer(call -> {
			inserted.add(call.getArgument(0));
			return OperationStatus.of(call.getArgument(0));
		});
		when(((IGChatModelConfigurationSupportServiceRepositoryPattern) mocks
				.get(IGChatModelConfigurationSupportServiceRepositoryPattern.class)).findByCode(HANDLER))
				.thenReturn(support);
	}

	static LLMSVendor vendor() {
		final LLMSVendor vendor = new LLMSVendor();
		final LLMSVendorInfo info = new LLMSVendorInfo();
		info.setVendorId("test-vendor");
		info.setName("Test vendor");
		vendor.setVendorInfo(info);
		final LLMSModelsPresets preset = new LLMSModelsPresets();
		preset.setType(ModelType.CHAT);
		preset.setServiceHandler(HANDLER);
		final LLMModelPresetChoice listening = new LLMModelPresetChoice();
		listening.setCode("listening-model");
		listening.setInputModalities(List.of(TEXT, AUDIO));
		preset.getChoices().add(listening);
		final LLMModelPresetChoice plain = new LLMModelPresetChoice();
		plain.setCode("plain-model");
		preset.getChoices().add(plain);
		vendor.getPresets().add(preset);
		return vendor;
	}

	private List<GModelInputModality> created(String modelCode) {
		final LLMCreateModelData request = new LLMCreateModelData();
		request.setType(ModelType.CHAT);
		request.setServiceHandler(HANDLER);
		request.setModelCode(modelCode);
		request.setDoModelsLookup(false);
		request.setUses(List.of(ChatModelsUses.CHAT));
		inserted.clear();
		service.createLLMS(List.of(request));
		assertEquals(1, inserted.size(), modelCode);
		return inserted.get(0).getInputModalities();
	}

	@Test
	void thePresetsModalitiesFirst() {
		final ModelMetaInfo vision = new ModelMetaInfo();
		vision.setSupportsVision(true);
		chosenModelMeta = vision;
		assertEquals(List.of(TEXT, AUDIO), created("listening-model"), "the preset wins over the metadata");
	}

	@Test
	void elseTheChosenModelsMetadata() {
		final ModelMetaInfo vision = new ModelMetaInfo();
		vision.setSupportsVision(true);
		chosenModelMeta = vision;
		assertEquals(List.of(TEXT, IMAGE), created("plain-model"));
	}

	@Test
	void unsetWhenNeitherKnows() {
		chosenModelMeta = null;
		assertNull(created("plain-model"), "text only");
	}
}
