package ai.gebo.architecture.graphrag.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.persistence.IKnowledgeGraphSearchService;
import ai.gebo.architecture.graphrag.persistence.model.GraphExtractionMatching;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentReferenceRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventObjectRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationObjectRepository;

@Service
public class KnowledgeGraphSearchServiceImpl extends AbstractGraphPersistenceService
		implements IKnowledgeGraphSearchService {

	public KnowledgeGraphSearchServiceImpl(GraphDocumentReferenceRepository docReferenceRepository,
			GraphDocumentChunkRepository docChunkRepository, GraphEntityObjectRepository entityObjectRepository,
			GraphEntityInDocumentChunkRepository entityInChunkRepository,
			GraphEventObjectRepository eventObjectRepository,
			GraphEventInDocumentChunkRepository eventInChunkRepository,
			GraphRelationObjectRepository relationObjectRepository,
			GraphRelationInDocumentChunkRepository relationInChunkRepository,
			GraphEventAliasObjectRepository eventAliasRepository,
			GraphEntityAliasObjectRepository entityAliasRepository,
			GraphEntityAliasInDocumentChunkRepository entityAliasChunkRepository,
			GraphEventAliasInDocumentChunkRepository eventAliasChunkRepository) {
		super(docReferenceRepository, docChunkRepository, entityObjectRepository, entityInChunkRepository,
				eventObjectRepository, eventInChunkRepository, relationObjectRepository, relationInChunkRepository,
				eventAliasRepository, entityAliasRepository, entityAliasChunkRepository, eventAliasChunkRepository);

	}

	@Override
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK,
			List<String> knowledgeBasesCodes) {
		GraphExtractionMatching matching = searchMatches(extraction);
		return null;
	}

	@Override
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK) {
		return knowledgeGraphSearch(extraction, topK, null);
	}

}
