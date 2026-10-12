package ai.gebo.system.ingestion.impl;

import java.io.IOException;

import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.IGLanguageDetector;
@Service
public class GLanguageDetectorImpl implements IGLanguageDetector {

	@Override
	public DetectedLanguage detect(String text) throws IOException {

		LanguageDetector detector = LanguageDetector.getDefaultLanguageDetector().loadModels();
		
		LanguageResult result = detector.detect(text);
		if (result == null)
			return DEFAULT_LANGUAGE;
		return new DetectedLanguage(result.getLanguage(), result.getRawScore());
	}

	

	@Override
	public void addLanguageMetaData(Document document) throws IOException {
		if (document.isText()) {
			String text = document.getText();
			DetectedLanguage detected = detect(text);
			document.getMetadata().put(DocumentMetaInfos.LANGUAGE, detected.getLanguage());
			document.getMetadata().put(DocumentMetaInfos.LANGUAGE_CONFIDENCE, detected.getConfidence());
		}

	}
}
