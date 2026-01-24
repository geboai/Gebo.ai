package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.fulltext.model.MetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.llms.abstraction.layer.model.AIDocumentsSet;

public interface IGFullTextRagDocumentsCachedDao {
	public AIDocumentsSet search(List<String> q, int topK, MetaDataFilter filter) throws FullTextException;

	public AIDocumentsSet search(String q, int topK, MetaDataFilter filter) throws FullTextException;
}
