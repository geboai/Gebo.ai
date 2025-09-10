package ai.gebo.architecture.graphrag.persistence.impl;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.persistence.IKnowledgeGraphPersistenceService;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

@Service
public class KnowledgeGraphPersistenceServiceImpl implements IKnowledgeGraphPersistenceService {

	public KnowledgeGraphPersistenceServiceImpl() {

	}

	@Override
	public void knowledgeGraphDelete(GDocumentReference documentReference) {
		

	}

	@Override
	public Consumer<KnowledgeExtractionData> knowledgeGraphUpdater(GDocumentReference documentReference) {
		
		return null;
	}

}
