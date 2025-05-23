/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import ai.gebo.core.analisys.model.GStatsHolder;
import ai.gebo.core.analisys.model.GStatsLabelValue;
import ai.gebo.core.analisys.services.IGAnalisysProvider;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;

/**
 * AI generated comments
 * Service implementation to provide document counting functionality
 * based on certain classification levels.
 */
@Service
public class DocumentsCountProvider implements IGAnalisysProvider {
    
    // Repository to perform operations with document references.
	@Autowired
	DocumentReferenceRepository dRepo;

	/**
	 * Default constructor.
	 */
	public DocumentsCountProvider() {

	}

	/**
	 * Returns the identifier of this provider.
	 * 
	 * @return the ID string of this provider.
	 */
	@Override
	public String getId() {
		return "DocumentsCountProvider";
	}

	/**
	 * Computes and updates the document count in the provided holder list.
	 * 
	 * @param holder a list of GStatsHolder objects that will be updated with the document counts.
	 */
	@Override
	public void compute(List<GStatsHolder> holder) {
		// Iterate over each stats holder to compute document counts.
		for (GStatsHolder sh : holder) {
			GDocumentReference doc = new GDocumentReference();
			
			// Determine the level key and set the appropriate code for document reference
			switch (sh.getLevelKey()) {
			
			case "ai.gebo.core.analisys.model.GeboInstallation": {
				// Logic for GeboInstallation level, currently does nothing specific.
			}
				break;

			case "ai.gebo.knlowledgebase.model.contents.GKnowledgeBase": {
				// Sets the root knowledgebase code from the dimension value of the stats holder.
				doc.setRootKnowledgebaseCode(sh.getDimensionValue().getCode());
			}
				break;
			
			case "ai.gebo.knlowledgebase.model.projects.GProject": {
				// Sets the parent project code from the dimension value of the stats holder.
				doc.setParentProjectCode(sh.getDimensionValue().getCode());
			}
			default:
				// Exit if no matching level key is found.
				return;
			}

			// Count the number of documents matching the example document reference.
			long howMany = dRepo.count(Example.of(doc));
			
			// Update the stats container with the count of documents.
			sh.getStatsContainer().put("countDocuments", List.of(new GStatsLabelValue("countDocuments", howMany)));
		}

	}

}