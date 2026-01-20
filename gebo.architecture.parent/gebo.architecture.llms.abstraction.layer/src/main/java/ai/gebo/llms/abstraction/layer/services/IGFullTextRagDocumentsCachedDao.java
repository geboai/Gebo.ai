package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.fulltext.model.MetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;

public interface IGFullTextRagDocumentsCachedDao {
	public RagDocumentsCachedDaoResult search(List<String> q, int topK, MetaDataFilter filter) throws FullTextException;

	public RagDocumentsCachedDaoResult search(String q, int topK, MetaDataFilter filter) throws FullTextException;
}
