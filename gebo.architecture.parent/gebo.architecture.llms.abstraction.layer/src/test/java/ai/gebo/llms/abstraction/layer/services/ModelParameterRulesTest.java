/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins the parameter rules of the models library: matched by provider and model, an
 * installation's rules first; the sampling parameters left out of the requests of a
 * model refusing them, while the saved configuration keeps them; the reasoning effort
 * sent only when the model lists it.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ModelParameterRulesTest {

	/** A model recording the configurations its requests are made from. */
	static class RecordingModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {
		final List<GBaseChatModelConfig> requestConfigs = new ArrayList<>();
		final GModelsLibraryDaoImpl library;

		RecordingModel(GModelsLibraryDaoImpl library) {
			super(null, null, usage(), ObservationRegistry.NOOP, library);
			this.library = library;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return new RecordingModel(library);
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) {
			requestConfigs.add(config);
			return mock(ChatModel.class);
		}

		GBaseChatModelConfig lastRequestConfig() {
			return requestConfigs.get(requestConfigs.size() - 1);
		}
	}

	private static IChatModelUsageAdvisorFactory usage() {
		IChatModelUsageAdvisorFactory usage = mock(IChatModelUsageAdvisorFactory.class);
		when(usage.create(any(), any(), any())).thenReturn(mock(IChatModelUsageAdvisor.class));
		return usage;
	}

	private static ModelParameterRules rule(String providerId, List<String> models, Sampling sampling,
			List<String> efforts) {
		ModelParameterRules rule = new ModelParameterRules();
		rule.setProviderId(providerId);
		rule.setModels(models);
		rule.setSampling(sampling);
		rule.setReasoningEfforts(efforts);
		return rule;
	}

	/** The bundled library of the tests. */
	private static GModelsLibraryDaoImpl library(List<ModelParameterRules> installation,
			List<ModelParameterRules> bundled) {
		ModelsLibraryConfig config = new ModelsLibraryConfig();
		config.setParameterRules(installation);
		GProviderModelsLibrary provider = new GProviderModelsLibrary();
		provider.setParameterRules(bundled);
		return new GModelsLibraryDaoImpl(config, List.of(provider));
	}

	private static GModelsLibraryDaoImpl openAiLibrary() {
		return library(List.of(), List.of(
				rule("openai", List.of("gpt-6.1-sol*"), Sampling.REJECTED, List.of("low", "medium", "high", "max")),
				rule("openai", List.of("gpt-6-luna*"), Sampling.WITHOUT_REASONING,
						List.of("none", "low", "medium", "high")),
				rule("openai", List.of("gpt-5*"), Sampling.REJECTED, List.of())));
	}

	private static GChatModelType type(String providerId) {
		GChatModelType type = new GChatModelType();
		type.setCode("chat-" + providerId);
		type.setProviderId(providerId);
		return type;
	}

	private static GBaseChatModelConfig config(String model, Double temperature, ChatModelThinkingOption thinking) {
		GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setCode("chat-" + model);
		GBaseChatModelChoice choice = new GBaseChatModelChoice();
		choice.setCode(model);
		config.setChoosedModel(choice);
		config.setTemperature(temperature);
		config.setThinking(thinking);
		return config;
	}

	@Test
	void aRuleCoversItsProvidersModelsOnly() {
		ModelParameterRules gptOss = rule("groq", List.of("openai/gpt-oss-*"), Sampling.ACCEPTED, List.of());
		assertTrue(gptOss.covers("groq", "openai/gpt-oss-20b"));
		assertFalse(gptOss.covers("openrouter.ai", "openai/gpt-oss-20b"), "another provider's same model id");
		assertFalse(gptOss.covers("groq", "openai/gpt-4.1"));
		ModelParameterRules exact = rule("openai", List.of("gpt-5"), Sampling.REJECTED, List.of());
		assertTrue(exact.covers("openai", "gpt-5"));
		assertFalse(exact.covers("openai", "gpt-5.5"), "no * matches the id only");
		assertTrue(rule("openai", List.of("*-mini"), null, List.of()).covers("openai", "gpt-5-mini"));
		assertTrue(rule("openai", List.of("gpt-6*luna*"), null, List.of()).covers("openai", "gpt-6-luna-2026"));
		assertFalse(rule("openai", List.of("gpt.5*"), null, List.of()).covers("openai", "gptx5"),
				"the dot is no wildcard");
	}

	@Test
	void anInstallationsRulesComeFirstThenTheFirstBundledOne() {
		ModelParameterRules installed = rule("openai", List.of("gpt-5.5"), Sampling.ACCEPTED, List.of());
		ModelParameterRules specific = rule("openai", List.of("gpt-5.5*"), Sampling.REJECTED, List.of("none"));
		ModelParameterRules family = rule("openai", List.of("gpt-5*"), Sampling.REJECTED, List.of());
		GModelsLibraryDaoImpl library = library(List.of(installed), List.of(specific, family));

		assertSame(installed, library.findParameterRules("openai", "gpt-5.5"));
		assertSame(specific, library.findParameterRules("openai", "gpt-5.5-pro"));
		assertSame(family, library.findParameterRules("openai", "gpt-5-mini"));
		assertNull(library.findParameterRules("openai", "gpt-4.1"));
		assertNull(library.findParameterRules("groq", "gpt-5.5"));
	}

	@Test
	void aModelRefusingSamplingGetsNoTemperatureButItsConfigurationKeepsIt() throws Exception {
		RecordingModel model = new RecordingModel(openAiLibrary());
		GBaseChatModelConfig config = config("gpt-6.1-sol", 0.7d, null);
		config.setTopP(0.9d);

		model.initialize(config, type("openai"));

		assertNull(model.lastRequestConfig().getTemperature());
		assertNull(model.lastRequestConfig().getTopP());
		assertEquals(0.7d, model.getConfig().getTemperature(), "the saved configuration is untouched");
		assertEquals(0.9d, model.getConfig().getTopP());
	}

	@Test
	void theDeterministicDefaultOnlyGoesWhereTheModelTakesATemperature() throws Exception {
		RecordingModel refusing = new RecordingModel(openAiLibrary());
		refusing.initialize(config("gpt-5", null, null), type("openai"));
		assertNull(refusing.getConfig().getTemperature(), "no temperature 0 written for a model refusing it");
		assertNull(refusing.lastRequestConfig().getTemperature());

		RecordingModel unknown = new RecordingModel(openAiLibrary());
		unknown.initialize(config("gpt-4.1", null, null), type("openai"));
		assertEquals(0.0d, unknown.lastRequestConfig().getTemperature(), "no rule: as before");

		RecordingModel noLibrary = new RecordingModel(null);
		noLibrary.initialize(config("gpt-5", null, null), type("openai"));
		assertEquals(0.0d, noLibrary.lastRequestConfig().getTemperature(), "no library: as before");
	}

	@Test
	void samplingWithoutReasoningGoesOnlyWithTheThinkingOff() throws Exception {
		RecordingModel off = new RecordingModel(openAiLibrary());
		off.initialize(config("gpt-6-luna", null, ChatModelThinkingOption.NO_THINKING), type("openai"));
		assertEquals(0.0d, off.lastRequestConfig().getTemperature());
		assertEquals("none", off.reasoningEffortFor(ChatModelThinkingOption.NO_THINKING));

		RecordingModel reasoning = new RecordingModel(openAiLibrary());
		reasoning.initialize(config("gpt-6-luna", 0.2d, ChatModelThinkingOption.LOW_THINKING), type("openai"));
		assertNull(reasoning.lastRequestConfig().getTemperature());
	}

	@Test
	void theEffortSentIsOneTheModelLists() throws Exception {
		RecordingModel sol = new RecordingModel(openAiLibrary());
		sol.initialize(config("gpt-6.1-sol", null, null), type("openai"));
		assertNull(sol.reasoningEffortFor(ChatModelThinkingOption.NO_THINKING), "sol takes no none");
		assertEquals("low", sol.reasoningEffortFor(ChatModelThinkingOption.LOW_THINKING));
		assertEquals("high", sol.reasoningEffortFor(ChatModelThinkingOption.HIGH_THINKING));
		assertNull(sol.reasoningEffortFor(ChatModelThinkingOption.AUTO));

		RecordingModel unknown = new RecordingModel(openAiLibrary());
		unknown.initialize(config("gpt-5-mini", null, null), type("openai"));
		assertEquals("medium", unknown.reasoningEffortFor(ChatModelThinkingOption.MEDIUM_THINKING),
				"efforts unknown: sent as asked");
		assertNull(unknown.reasoningEffortFor(ChatModelThinkingOption.NO_THINKING),
				"efforts unknown: no thinking has no portable value");
	}

	@Test
	void aModelKeepingTheProviderDefaultGetsNoDeterministicTemperature() throws Exception {
		GModelsLibraryDaoImpl library = library(List.of(),
				List.of(rule("google-vertex", List.of("gemini-3*"), Sampling.PROVIDER_DEFAULT, List.of())));

		RecordingModel unset = new RecordingModel(library);
		unset.initialize(config("gemini-3.8-flash", null, null), type("google-vertex"));
		assertNull(unset.getConfig().getTemperature(), "left at the provider default");
		assertNull(unset.lastRequestConfig().getTemperature());

		RecordingModel set = new RecordingModel(library);
		set.initialize(config("gemini-3.8-flash", 0.4d, null), type("google-vertex"));
		assertEquals(0.4d, set.lastRequestConfig().getTemperature(), "a temperature set is sent");

		RecordingModel older = new RecordingModel(library);
		older.initialize(config("gemini-2.5-flash", null, null), type("google-vertex"));
		assertEquals(0.0d, older.lastRequestConfig().getTemperature(), "no rule: as before");
	}

	/** A model whose models api told its rules, as OpenRouter does per model. */
	static class LiveRulesModel extends RecordingModel {
		final ModelParameterRules live;

		LiveRulesModel(GModelsLibraryDaoImpl library, ModelParameterRules live) {
			super(library);
			this.live = live;
		}

		@Override
		protected ModelParameterRules liveParameterRules(GBaseChatModelConfig config, GChatModelType type) {
			return live;
		}
	}

	@Test
	void whatTheModelsApiToldWinsOverTheLibrary() throws Exception {
		GModelsLibraryDaoImpl library = library(List.of(), List.of(rule("openrouter.ai", List.of("google/gemini-3*"),
				Sampling.PROVIDER_DEFAULT, List.of("none", "low", "high"))));
		ModelParameterRules mandatory = rule("openrouter.ai", List.of("google/gemini-3.8-flash"), null,
				List.of("high", "medium", "low"));

		LiveRulesModel model = new LiveRulesModel(library, mandatory);
		model.initialize(config("google/gemini-3.8-flash", null, ChatModelThinkingOption.NO_THINKING),
				type("openrouter.ai"));
		assertNull(model.reasoningEffortFor(ChatModelThinkingOption.NO_THINKING), "the live efforts lack none");
		assertEquals("medium", model.reasoningEffortFor(ChatModelThinkingOption.MEDIUM_THINKING));
		assertNull(model.lastRequestConfig().getTemperature(), "the library's provider default still applies");

		ModelParameterRules batch = rule("openrouter.ai", List.of("google/gemini-3.8-flash:batch"), Sampling.REJECTED,
				List.of());
		LiveRulesModel refusing = new LiveRulesModel(library, batch);
		refusing.initialize(config("google/gemini-3.8-flash:batch", 0.5d, null), type("openrouter.ai"));
		assertNull(refusing.lastRequestConfig().getTemperature(), "the api says it takes no temperature");
		assertEquals("low", refusing.reasoningEffortFor(ChatModelThinkingOption.LOW_THINKING),
				"no live efforts: the library's");

		LiveRulesModel noLibrary = new LiveRulesModel(null, batch);
		noLibrary.initialize(config("google/gemini-3.8-flash:batch", 0.5d, null), type("openrouter.ai"));
		assertNull(noLibrary.lastRequestConfig().getTemperature());
	}

	@Test
	void anAgentsTemperatureNeverReachesAModelRefusingIt() throws Exception {
		RecordingModel model = new RecordingModel(openAiLibrary());
		model.initialize(config("gpt-6.1-sol", null, null), type("openai"));

		RecordingModel agentCopy = (RecordingModel) model.cloneWithOptions("agent-",
				new ChatModelConfigOptions(0.3d, null, null, List.of(), mock(ToolCallingManager.class)));

		assertNull(agentCopy.lastRequestConfig().getTemperature());
		assertEquals(0.3d, agentCopy.getConfig().getTemperature());

		RecordingModel plainCopy = (RecordingModel) model.cloneWithOptions("plain-",
				new ChatModelConfigOptions(0.3d, null, null, List.of(), null));
		assertNull(plainCopy.lastRequestConfig().getTemperature());
	}
}
