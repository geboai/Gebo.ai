package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.security.repository.UserRepository.UserInfos;

/******************************************************************************
 * AI generated comments This interface can be implemented to restrict the
 * semantic search at user level with custom policies adding meta-data filtering
 * clauses to the vector database search
 */
public interface IGVectorSearchRestrictingFilterExpressionFactory {
	/****************************************************************************
	 * Creates additional meta-data filtering conditions to the RAG semantic search
	 * over a vector database, you can implement for example adding extensive cataloging/security meta data
	 * in the embedding phase and using same meta datas as filtering clause in all semantic searches
	 * 
	 * @param query
	 * @param userInfos
	 * @param knowledgeBases
	 * @param documentCodes
	 * @return
	 */
	public String createAdditionalFilterExpression(String query, UserInfos userInfos, List<String> knowledgeBases,
			List<String> documentCodes);
}
