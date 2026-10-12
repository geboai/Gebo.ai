package ai.gebo.llms.mistralai.model;

import lombok.Data;

@Data
public class MistralModelCapabilities {
	Boolean completion_chat = null;
	Boolean completion_fim = null;
	Boolean function_calling = null;
	Boolean fine_tuning = null;
	Boolean vision = null;
	Boolean classification = null;
	Boolean reasoning = null;
	Boolean ocr = null;
	Boolean moderation = null;
	Boolean audio = null;
	Boolean audio_transcription = null;
	Boolean audio_speech = null;
}