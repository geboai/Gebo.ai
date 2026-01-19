package ai.gebo.architecture.fulltext.service;

import java.util.List;
import java.util.stream.Stream;

import ai.gebo.architecture.fulltext.model.FullTextChunk;
import ai.gebo.architecture.fulltext.model.FullTextDocument;

public interface IGFullTextIngestionService {

	public void deleteDocuments(List<FullTextDocument> documents) throws FullTextException;

	public void upsert(List<FullTextChunk> chunks)  throws FullTextException;

}
