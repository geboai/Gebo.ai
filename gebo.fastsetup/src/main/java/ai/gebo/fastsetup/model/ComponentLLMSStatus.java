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
 * The ComponentLLMSStatus class extends ComponentSetupStatus and is used to track
 * the setup status of language learning models (LLMs) within a component.
 *
 * Gebo.ai Commentor AI generated comments
 */
public class ComponentLLMSStatus extends ComponentSetupStatus {

    // Boolean flag indicating whether the chat model setup is complete
    public boolean chatModelSetup;

    // Boolean flag indicating whether the embedded model setup is complete
    public boolean embeddedModelSetup;
}