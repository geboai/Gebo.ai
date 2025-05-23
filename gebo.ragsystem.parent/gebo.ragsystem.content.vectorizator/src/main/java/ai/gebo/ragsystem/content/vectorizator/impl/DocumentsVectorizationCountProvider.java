/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.core.analisys.model.GStatsHolder;
import ai.gebo.core.analisys.model.GStatsLabelValue;
import ai.gebo.core.analisys.services.IGAnalisysProvider;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;

/**
 * AI generated comments
 * 
 * Service responsible for providing analytics about the number of vectorized documents
 * in the system across different embedding models and organizational levels.
 * This provider contributes statistics about document vectorization counts for 
 * analysis purposes.
 */
@Service
public class DocumentsVectorizationCountProvider implements IGAnalisysProvider {
	/**
	 * Repository for accessing vectorized content data.
	 */
	@Autowired
	VectorizedContentRepository vect;
	
	/**
	 * Data access object for embedding model configurations.
	 */
	@Autowired
	IGEmbeddingModelRuntimeConfigurationDao embeddingDao;

	/**
	 * Default constructor for the provider.
	 */
	public DocumentsVectorizationCountProvider() {

	}

	/**
	 * Returns the unique identifier for this analysis provider.
	 * 
	 * @return The provider's unique identifier string
	 */
	@Override
	public String getId() {

		return "DocumentsVectorizationCountProvider";
	}

	/**
	 * Computes vectorization statistics for each provided stats holder.
	 * Counts vectorized documents by embedding model type at different organizational levels:
	 * - Installation level (global)
	 * - Knowledge base level
	 * - Project level
	 * 
	 * @param holder List of stats holders to be populated with vectorization counts
	 */
	@Override
	public void compute(List<GStatsHolder> holder) {
		// Get all available embedding model codes
		List<String> embeddingCodes = embeddingDao.getConfigurations().stream().map(x -> {
			return x.getConfig().getCode();
		}).toList();
		
		for (GStatsHolder sh : holder) {
			List<GStatsLabelValue> list = new ArrayList<GStatsLabelValue>();
			sh.getStatsContainer().put("embeddings", list);
			for (String embeddinCode : embeddingCodes) {
				// GVectorizedContent doc = new GVectorizedContent();
				// doc.setId(new GVectorizedContentId());
				// doc.getId().setVectorStoreId(embeddinCode);
				long howMany = 0;
				switch (sh.getLevelKey()) {
				case "ai.gebo.core.analisys.model.GeboInstallation": {
					// Count all documents for this embedding model at installation level
					howMany = vect.countByIdVectorStoreId(embeddinCode);
				}
					break;

				case "ai.gebo.knlowledgebase.model.contents.GKnowledgeBase": {
					// Count documents for this embedding model filtered by knowledge base
					howMany = vect.countByIdVectorStoreIdAndRootKnowledgebaseCode(embeddinCode,
							sh.getDimensionValue().getCode());
				}
					break;
				case "ai.gebo.knlowledgebase.model.projects.GProject": {
					// Count documents for this embedding model filtered by project
					howMany = vect.countByIdVectorStoreIdAndParentProjectCode(embeddinCode,
							sh.getDimensionValue().getCode());
				}
				default:
					return;

				}
				// Add count statistics for this embedding model
				list.add(new GStatsLabelValue(embeddinCode, howMany));
			}

		}

	}

}