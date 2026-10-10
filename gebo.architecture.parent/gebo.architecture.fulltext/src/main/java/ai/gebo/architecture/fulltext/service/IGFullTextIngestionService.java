package ai.gebo.architecture.fulltext.service;

import java.util.List;
import java.util.stream.Stream;

import ai.gebo.architecture.fulltext.model.FullTextChunk;
import ai.gebo.architecture.fulltext.model.FullTextDocument;

public interface IGFullTextIngestionService {
	public static final String KBSOURCE = "kbsource:";
	public void deleteDocuments(List<FullTextDocument> documents) throws FullTextException;

	public void upsert(List<FullTextChunk> chunks)  throws FullTextException;

	/**
	 * Deletes the chunks of the documents of a knowledge base: the ones indexed with
	 * it as their root knowledge base ({@code DocumentMetaInfos.KNOWLEDGEBASE_CODE},
	 * the document reference's root knowledge base code).
	 *
	 * @param knowledgeBaseCode the knowledge base code
	 * @return how many chunks were deleted
	 */
	public long deleteByKnowledgeBase(String knowledgeBaseCode) throws FullTextException;

	/**
	 * Deletes the chunks of the documents of a project: the ones indexed with it as
	 * their parent project ({@code DocumentMetaInfos.PROJECT_CODE}, the document
	 * reference's parent project code).
	 *
	 * @param projectCode the project code
	 * @return how many chunks were deleted
	 */
	public long deleteByProject(String projectCode) throws FullTextException;

	/**
	 * Deletes the chunks of the documents of a data source: the ones of its parent
	 * project whose code descends from the data source's root item
	 * ({@code <root knowledge base>/<project>/<data source>/...}).
	 *
	 * @param projectCode  the data source's parent project code
	 * @param endpointCode the data source's code
	 * @return how many chunks were deleted
	 */
	public long deleteByProjectEndpoint(String projectCode, String endpointCode) throws FullTextException;

}
