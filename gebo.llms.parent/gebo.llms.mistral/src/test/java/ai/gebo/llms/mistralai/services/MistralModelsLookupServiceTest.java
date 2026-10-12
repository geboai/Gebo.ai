package ai.gebo.llms.mistralai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.mistralai.model.GMistralChatModelChoice;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelChoice;
import ai.gebo.llms.mistralai.model.MistralBaseModelCard;
import ai.gebo.llms.mistralai.model.MistralModelCapabilities;

class MistralModelsLookupServiceTest {

	static MistralBaseModelCard card(String id, boolean chat) {
		MistralBaseModelCard card = new MistralBaseModelCard();
		card.setId(id);
		card.setMax_context_length(256000);
		MistralModelCapabilities capabilities = new MistralModelCapabilities();
		capabilities.setCompletion_chat(chat);
		capabilities.setFunction_calling(chat);
		capabilities.setVision(chat);
		capabilities.setReasoning(chat);
		card.setCapabilities(capabilities);
		return card;
	}

	@Test
	void onlyChatCapableModelsAreChatModels() {
		assertTrue(MistralModelsLookupService.offersChat(card("mistral-large-latest", true)));
		assertFalse(MistralModelsLookupService.offersChat(card("mistral-ocr-latest", false)));
		MistralBaseModelCard archived = card("ft:old", true);
		archived.setArchived(true);
		assertFalse(MistralModelsLookupService.offersChat(archived));
		MistralBaseModelCard unknown = card("x", true);
		unknown.setCapabilities(null);
		assertTrue(MistralModelsLookupService.offersChat(unknown));
	}

	@Test
	void theCardCapabilitiesAndDeprecationReachTheChoice() {
		MistralBaseModelCard card = card("magistral-medium-2509", true);
		card.setDeprecation("2026-07-31T12:00:00Z");
		card.setDeprecation_replacement_model("mistral-medium-latest");
		GMistralChatModelChoice choice = new GMistralChatModelChoice();

		MistralModelsLookupService.fill(choice, card, true);

		assertEquals("magistral-medium-2509 (deprecated 2026-07-31, replaced by mistral-medium-latest)",
				choice.getDescription());
		assertEquals(256000, choice.getContextLength());
		assertTrue(choice.getMetaInfos().getSupportsFunctionCalls());
		assertTrue(choice.getMetaInfos().getSupportsReasoning());
		assertEquals("mistralai", choice.getMetaInfos().getProviderId());
		assertEquals("mistral-medium-latest", choice.getMetaInfos().getReplacementModel());
	}

	@Test
	void anEmbeddingModelIsNotDescribedAsAChatModel() {
		GMistralEmbeddingModelChoice choice = new GMistralEmbeddingModelChoice();

		MistralModelsLookupService.fill(choice, card("mistral-embed", false), false);

		assertFalse(choice.getMetaInfos().getChatModel());
		assertTrue(choice.getMetaInfos().getEmbeddingModel());
	}
}
