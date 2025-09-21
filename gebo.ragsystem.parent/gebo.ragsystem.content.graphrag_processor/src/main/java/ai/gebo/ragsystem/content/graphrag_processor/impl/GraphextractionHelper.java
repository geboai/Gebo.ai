package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

@Service
public class GraphextractionHelper {
	@Autowired
	IGraphDataExtractionService graphRagExtractionService;

	public KnowledgeExtractionData doProcessChunk(DocumentChunk chunk, GDocumentReference reference,
			Map<String, Object> cache) throws LLMConfigException {
		Document document = new Document(chunk.getId(), chunk.getChunkData(), chunk.getMetaData());
		LLMExtractionResult extraction = graphRagExtractionService.extract(document, reference, cache);
		return new KnowledgeExtractionData(extraction, document);
	}

}
