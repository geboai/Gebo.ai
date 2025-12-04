package ai.gebo.architecture.graphrag.persistence.model;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KnowledgeExtractionData {
	private final LLMExtractionResult extraction;
	private final List<Document> documentChunks;	
	private final boolean errorProcessing;
}
