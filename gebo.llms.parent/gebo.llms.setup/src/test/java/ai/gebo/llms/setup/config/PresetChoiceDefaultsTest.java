/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.setup.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;

/**
 * Pins the role a preset's thinking takes, and what the bundled library binds: a model
 * can think for chat and not for the internal services, and no preset writes a
 * temperature unless it says one.
 */
class PresetChoiceDefaultsTest {

	@Test
	void theInternalServicesTakeTheirOwnThinkingWhenThePresetGivesOne() {
		LLMModelPresetChoice choice = new LLMModelPresetChoice();
		choice.setThinking(ChatModelThinkingOption.MEDIUM_THINKING);
		choice.setInternalServicesThinking(ChatModelThinkingOption.NO_THINKING);

		assertEquals(ChatModelThinkingOption.NO_THINKING,
				choice.thinkingFor(List.of(ChatModelsUses.INTERNAL_SERVICES)));
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING, choice.thinkingFor(List.of(ChatModelsUses.CHAT)));
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING, choice.thinkingFor(null));

		choice.setInternalServicesThinking(null);
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING,
				choice.thinkingFor(List.of(ChatModelsUses.INTERNAL_SERVICES)), "no internal one: the thinking");
	}

	@Test
	void theBundledLibraryBindsTheRoleThinkingAndWritesNoTemperature() throws Exception {
		StandardEnvironment environment = new StandardEnvironment();
		environment.getPropertySources().addFirst(new GeboYamlPropertySourceFactory().createPropertySource(null,
				new EncodedResource(new ClassPathResource("llms-fast-setup-library/library.yml"))));
		LLMSVendorsSetupConfig library = Binder.get(environment)
				.bind("ai.gebo.llms.vendors.setup.config", LLMSVendorsSetupConfig.class).get();

		LLMModelPresetChoice deepseekFlash = library.getVendors().stream()
				.filter(v -> "deepseek".equals(v.getVendorInfo().getVendorId()))
				.flatMap(v -> v.getPresets().stream()).flatMap(p -> p.getChoices().stream())
				.filter(c -> "deepseek-flash".equals(c.getCode())).findFirst().orElseThrow();
		assertEquals(ChatModelThinkingOption.NO_THINKING,
				deepseekFlash.thinkingFor(List.of(ChatModelsUses.INTERNAL_SERVICES)));
		assertNull(deepseekFlash.thinkingFor(List.of(ChatModelsUses.CHAT)), "chat keeps the provider default");

		assertTrue(library.getVendors().stream().flatMap(v -> v.getPresets().stream())
				.filter(p -> p.getChoices() != null).flatMap(p -> p.getChoices().stream())
				.allMatch(c -> c.getTemperature() == null), "no preset writes a temperature");
	}

	@Test
	void theOpenAIChatPresetsReasonForTheAgenticChatNotForTheInternalServices() throws Exception {
		StandardEnvironment environment = new StandardEnvironment();
		environment.getPropertySources().addFirst(new GeboYamlPropertySourceFactory().createPropertySource(null,
				new EncodedResource(new ClassPathResource("llms-fast-setup-library/library.yml"))));
		LLMSVendorsSetupConfig library = Binder.get(environment)
				.bind("ai.gebo.llms.vendors.setup.config", LLMSVendorsSetupConfig.class).get();

		List<LLMModelPresetChoice> chat = library.getVendors().stream()
				.filter(v -> "openai".equals(v.getVendorInfo().getVendorId()))
				.flatMap(v -> v.getPresets().stream()).filter(p -> p.getType() == ModelType.CHAT)
				.flatMap(p -> p.getChoices().stream()).toList();

		assertEquals(List.of("gpt-5.2", "gpt-5.4-mini"), chat.stream().map(LLMModelPresetChoice::getCode).toList(),
				"gpt-4.1 and gpt-4.1-mini shut down on 2027-01-14");
		LLMModelPresetChoice main = chat.get(0);
		assertTrue(main.isDefaultChoice());
		assertEquals(List.of(ChatModelsUses.CHAT), main.getUses());
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING, main.thinkingFor(main.getUses()),
				"a chat model thinks medium");
		LLMModelPresetChoice services = chat.get(1);
		assertEquals(List.of(ChatModelsUses.INTERNAL_SERVICES), services.getUses());
		assertEquals(ChatModelThinkingOption.NO_THINKING, services.thinkingFor(services.getUses()),
				"short judgement calls, no reasoning");
		for (LLMModelPresetChoice choice : chat) {
			assertEquals(272000, choice.getContextWindow(), choice.getCode() + ": the input OpenAI accepts");
		}
	}

	@Test
	void everyPresetThinkingFollowsThePlatformRule() throws Exception {
		StandardEnvironment environment = new StandardEnvironment();
		environment.getPropertySources().addFirst(new GeboYamlPropertySourceFactory().createPropertySource(null,
				new EncodedResource(new ClassPathResource("llms-fast-setup-library/library.yml"))));
		LLMSVendorsSetupConfig library = Binder.get(environment)
				.bind("ai.gebo.llms.vendors.setup.config", LLMSVendorsSetupConfig.class).get();

		// a service model does not think or thinks low, a chat model thinks medium (or keeps
		// its provider's default where the preset names no level)
		library.getVendors().stream().flatMap(v -> v.getPresets().stream()).filter(p -> p.getType() == ModelType.CHAT)
				.filter(p -> p.getChoices() != null).flatMap(p -> p.getChoices().stream()).forEach(choice -> {
					final ChatModelThinkingOption service = choice.thinkingFor(List.of(ChatModelsUses.INTERNAL_SERVICES));
					final boolean servesInternals = choice.getUses() != null
							&& choice.getUses().contains(ChatModelsUses.INTERNAL_SERVICES);
					if (servesInternals && service != null) {
						assertTrue(service == ChatModelThinkingOption.NO_THINKING
								|| service == ChatModelThinkingOption.LOW_THINKING, choice.getCode() + ": " + service);
					}
					final ChatModelThinkingOption chat = choice.thinkingFor(List.of(ChatModelsUses.CHAT));
					if (choice.getUses() != null && choice.getUses().equals(List.of(ChatModelsUses.CHAT))) {
						assertTrue(chat == null || chat == ChatModelThinkingOption.MEDIUM_THINKING,
								choice.getCode() + ": " + chat);
					}
				});
	}

	@Test
	void theCloudVendorsDeclareTheCredentialsTheyTake() throws Exception {
		StandardEnvironment environment = new StandardEnvironment();
		environment.getPropertySources().addFirst(new GeboYamlPropertySourceFactory().createPropertySource(null,
				new EncodedResource(new ClassPathResource("llms-fast-setup-library/library.yml"))));
		LLMSVendorsSetupConfig library = Binder.get(environment)
				.bind("ai.gebo.llms.vendors.setup.config", LLMSVendorsSetupConfig.class).get();

		LLMSVendor bedrock = library.getVendors().stream()
				.filter(v -> "aws-bedrock".equals(v.getVendorInfo().getVendorId())).findFirst().orElseThrow();
		assertEquals(List.of(ai.gebo.secrets.model.GeboSecretType.AWS_CONNECTION), bedrock.getVendorInfo().getSecretTypes());
		assertEquals("aws-bedrock", bedrock.getVendorInfo().getApiKeySecretContext(), "the model editors' context");
		LLMSVendor vertex = library.getVendors().stream()
				.filter(v -> "google-vertex".equals(v.getVendorInfo().getVendorId())).findFirst().orElseThrow();
		assertEquals(List.of(ai.gebo.secrets.model.GeboSecretType.GOOGLE_CLOUD_JSON_CREDENTIALS, ai.gebo.secrets.model.GeboSecretType.OAUTH2_GOOGLE),
				vertex.getVendorInfo().getSecretTypes());
		assertTrue(library.getVendors().stream().filter(v -> "openai".equals(v.getVendorInfo().getVendorId()))
				.allMatch(v -> v.getVendorInfo().getSecretTypes().equals(List.of(ai.gebo.secrets.model.GeboSecretType.TOKEN))),
				"an api key vendor keeps TOKEN");
	}
}
