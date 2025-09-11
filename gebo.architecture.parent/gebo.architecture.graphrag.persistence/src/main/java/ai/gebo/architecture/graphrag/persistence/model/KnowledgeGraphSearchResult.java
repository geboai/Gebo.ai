package ai.gebo.architecture.graphrag.persistence.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;

import lombok.Data;
@Data
public class KnowledgeGraphSearchResult {
	private Document document = null;
	private List<GraphEntityObject> entities = new ArrayList<GraphEntityObject>();
	private List<GraphEventInDocumentChunk> events = new ArrayList<GraphEventInDocumentChunk>();
	private List<GraphRelationInDocumentChunk> relations = new ArrayList<GraphRelationInDocumentChunk>();
}