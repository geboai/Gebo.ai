package ai.gebo.architecture.rag.support.layer.services;

import java.util.List;

import ai.gebo.architecture.fulltext.model.MetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;

public interface IGFullTextSearchDocumentsCachedDao {
	public AIDocumentsSet search(List<String> q, int topK, MetaDataFilter filter) throws FullTextException;

	public AIDocumentsSet search(String q, int topK, MetaDataFilter filter) throws FullTextException;
}
