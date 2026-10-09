package ai.gebo.llms.google_vertex.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.google.genai.types.Model;

import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelChoice;

class GoogleVertexChatModelChoicesTest {

	@Test
	void onlyGeminiChatModelsBecomeChoices() {
		GGoogleVertexChatModelChoice choice = GoogleVertexChatModelConfigurationSupportService.toChoice(Model.builder()
				.name("publishers/google/models/gemini-3.7-flash").displayName("Gemini 3.7 Flash").build());
		assertEquals("gemini-3.7-flash", choice.getCode());
		assertEquals("Gemini 3.7 Flash (gemini-3.7-flash)", choice.getDescription());

		assertNull(GoogleVertexChatModelConfigurationSupportService
				.toChoice(Model.builder().name("publishers/google/models/gemini-embedding-001").build()));
		assertNull(GoogleVertexChatModelConfigurationSupportService
				.toChoice(Model.builder().name("publishers/google/models/imagen-4.0-generate-001").build()));
		assertNull(GoogleVertexChatModelConfigurationSupportService
				.toChoice(Model.builder().name("publishers/google/models/gemini-3.1-flash-lite-image").build()));
	}

	@Test
	void theFallbackListHasEachModelOnce() {
		assertEquals(GoogleVertexChatModelConfigurationSupportService.choices.size(),
				GoogleVertexChatModelConfigurationSupportService.choices.stream().map(c -> c.getCode()).distinct()
						.count());
	}
}
