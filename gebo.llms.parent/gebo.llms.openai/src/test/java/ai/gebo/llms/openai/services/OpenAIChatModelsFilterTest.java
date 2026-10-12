package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class OpenAIChatModelsFilterTest {

	@Test
	void onlyChatModelsAreOfferedForChat() {
		List<String> listed = List.of("gpt-6.1-sol", "gpt-5", "o3", "gpt-oss-120b", "gpt-4o-mini-tts", "tts-1-hd",
				"whisper-1", "gpt-transcribe", "dall-e-3", "gpt-image-2.5-flare", "omni-moderation",
				"gpt-realtime-2.1", "gpt-audio-1.5", "text-embedding-3-large", "babbage-002", "gpt-live-1");

		assertEquals(List.of("gpt-6.1-sol", "gpt-5", "o3", "gpt-oss-120b"),
				listed.stream().filter(OpenAIChatModelConfigurationSupportService::isChatModel).toList());
	}
}
