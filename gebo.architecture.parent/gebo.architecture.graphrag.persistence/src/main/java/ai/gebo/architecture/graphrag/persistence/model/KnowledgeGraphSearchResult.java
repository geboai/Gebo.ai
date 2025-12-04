package ai.gebo.architecture.graphrag.persistence.model;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.model.ExtractedDocumentMetaData;
import lombok.Data;

@Data
public class KnowledgeGraphSearchResult {
	private ExtractedDocumentMetaData extractedDocumentMetaData = null;
	private Document document = null;
	private String chunkId = null;
	private String documentReferenceId = null;
	private String knowledgebaseCode = null;
	private double score = 0.0;
	private List<String> contributions;
	private List matchedAnchorIds;

}