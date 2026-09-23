package ai.gebo.system.ingestion;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/************************************************************************
 * Interface to be implemented to customize additional documents meta data
 * extraction
 */
public interface IGCustomDocumentMetaDataEnricher {
	/****************************************
	 * Meta data informations
	 */
	public static interface EnrichingMetaData {
		public Map<String, Object> getMetaData();

		public String getAdditionalEmbeddingSection();

	}

	/**********************************************************************************************************
	 * Per document callback to enrich vector database meta-data to be stored and
	 * additional text added to embed document fragments.
	 * 
	 * @param documents
	 * @param reference
	 * @param knowledgeBase
	 * @param project
	 * @param endpoint
	 * @return
	 */
	public EnrichingMetaData generateAdditionalMetaData(List<Document> documents, GDocumentReference reference,
			GKnowledgeBase knowledgeBase, GProject project, GProjectEndpoint endpoint);
}
