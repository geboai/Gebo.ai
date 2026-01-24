package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.MetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.fulltext.service.IGFullTextIngestionService;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.llms.abstraction.layer.model.AIDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGFullTextRagDocumentsCachedDao;
import ai.gebo.model.ExtractedDocumentMetaData;
import lombok.AllArgsConstructor;

@ConditionalOnBean(value = IGFullTextSearchService.class)
@Service
@AllArgsConstructor
public class FullTextRagDocumentsCachedDaoImpl implements IGFullTextRagDocumentsCachedDao {
	private final IGFullTextSearchService searchService;

	@Override
	public AIDocumentsSet search(List<String> q, int topK, MetaDataFilter filter)
			throws FullTextException {
		List<FullTextChunkSearchHit> data = this.searchService.search(q, topK, filter);
		return toRagDocumentCachedDaoResult(data);
	}

	@Override
	public AIDocumentsSet search(String q, int topK, MetaDataFilter filter) throws FullTextException {
		List<FullTextChunkSearchHit> data = this.searchService.search(q, topK, filter);
		return toRagDocumentCachedDaoResult(data);
	}

	private AIDocumentsSet toRagDocumentCachedDaoResult(List<FullTextChunkSearchHit> data) {
		Map<String, AIDocumentReferenceItem> docsMap = new HashMap<String, AIDocumentReferenceItem>();
		for (FullTextChunkSearchHit chunk : data) {
			ExtractedDocumentMetaData extractedMeta = ExtractedDocumentMetaData.of(chunk.getChunk().getMetaData());
			Document document = new Document(chunk.getChunk().getId(), chunk.getChunk().getContent(),
					chunk.getChunk().getMetaData());
			String documentCode = chunk.getChunk().getDocument().getCode();
			if (documentCode.startsWith(IGFullTextIngestionService.KBSOURCE)) {
				documentCode = documentCode.substring(IGFullTextIngestionService.KBSOURCE.length());
			}
			AIDocumentFragment fragment = new AIDocumentFragment(document, extractedMeta);
			fragment.setChunkPosition((long) chunk.getChunk().getPosition());
			if (!docsMap.containsKey(documentCode)) {
				AIDocumentReferenceItem rItem = new AIDocumentReferenceItem(extractedMeta);
				docsMap.put(documentCode, rItem);
			}
			docsMap.get(documentCode).getFragments().add(fragment);
			docsMap.get(documentCode).recalculateSize();
		}
		AIDocumentsSet out = AIDocumentsSet.createDocumentsDaoResultFromMap(docsMap);
		out.recalculateSize();
		return out;
	}

}
