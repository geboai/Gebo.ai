/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator;

import ai.gebo.application.messaging.IGBatchMessagesReceiver;

/**
 * AI generated comments
 * 
 * This interface defines a router for handling embedding operations within the RAG system.
 * It extends the IGBatchMessagesReceiver interface to receive batched messages for 
 * embedding processing. The embedding router is responsible for directing embedding
 * requests to appropriate embedding services or processors.
 */
public interface IGEmbeddingRouter extends IGBatchMessagesReceiver{
	
	
}