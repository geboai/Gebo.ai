package ai.gebo.system.ingestion;

import java.io.IOException;

import org.springframework.ai.document.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public interface IGLanguageDetector {
	@Getter
	@AllArgsConstructor
	@ToString
	public static class DetectedLanguage {
		private final String language;
		private final double confidence;
	}

	public static final DetectedLanguage DEFAULT_LANGUAGE = new DetectedLanguage("en", 0.3);

	public DetectedLanguage detect(String text) throws IOException;

	public void addLanguageMetaData(Document document) throws IOException;
}
