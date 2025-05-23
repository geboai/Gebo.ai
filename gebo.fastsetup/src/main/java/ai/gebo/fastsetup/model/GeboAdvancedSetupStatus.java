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
 * AI generated comments
 * The GeboAdvancedSetupStatus class is used to track the setup status 
 * of various components in the Gebo Advanced Setup. 
 * It contains boolean flags indicating whether specific setup steps 
 * have been completed and a tenant UUID for identification.
 */
public class GeboAdvancedSetupStatus {
	
	/** Indicates if the work directory needs to be set. */
	public boolean mustSetWorkDirectory = false;
	
	/** Indicates if the first setup has been completed. */
	public boolean firstSetupDone = false;
	
	/** Indicates if the first knowledge base setup has been completed. */
	public boolean firstKnowledgeBaseSetup = false;
	
	/** Indicates if the chat model setup has been completed. */
	public boolean chatModelSetup = false;
	
	/** Indicates if the embedded model setup has been completed. */
	public boolean embeddedModelSetup = false;
	
	/** Indicates if the language learning models (LLMs) setup has been completed. */
	public boolean llmsSetup = false;
	
	/** Indicates if the content modules setup has been completed. */
	public boolean hasContentModulesSetup = false;
	
	/** Indicates if the tenant setup has been completed. */
	public boolean tenantSetup = false;
	
	/** Indicates if the vector store setup has been completed. */
	public boolean vectorStoreSetup = false;
	
	/** Stores the unique identifier for the tenant. */
	public String tenantUUID = null;
}