package ai.gebo.llms.deepsearch.service.impl;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.deepsearch.model.DeepSearchAnalyzedDocument;
import ai.gebo.llms.deepsearch.model.DeepSearchSourceType;

public class KnowledgeBaseDocRefUtil {
	private static final String GEBO_AI_KNOWLEDGE_BASE = "Gebo.ai knowledge base";

	static DeepSearchAnalyzedDocument create(GDocumentReference reference) {
		DeepSearchAnalyzedDocument doc = new DeepSearchAnalyzedDocument();
		doc.setCode(reference.getCode());
		doc.setUrl(doc.getUrl());
		doc.setName(doc.getName());
		doc.setSourceType(DeepSearchSourceType.KNOWLEDGE_BASE);
		doc.setDataSourceDescription(GEBO_AI_KNOWLEDGE_BASE);
		return doc;
	}

}
