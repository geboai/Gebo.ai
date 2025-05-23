/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.model;

/**
 * Represents the setup status of the Gebo Knowledge Base including counts of specific components.
 * Extends the {@link ComponentSetupStatus} class to include specific details 
 * about knowledge bases, projects, endpoints, published endpoints, and document references.
 * 
 * AI generated comments
 */
public class GeboKnowledgeBaseSetupStatus extends ComponentSetupStatus {
	
	/** Number of knowledge bases setup in the system. */
	public long knowledgeBases = 0;

	/** Number of projects setup in the system. */
	public long projects = 0;

	/** Number of endpoints setup in the system. */
	public long endpoints = 0;

	/** Number of endpoints published and available in the system. */
	public long endpointsPublished = 0;

	/** Number of document references associated with the setup. */
	public long documentReferences = 0;

}