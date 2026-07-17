/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.setup.model;

/**
 * The ComponentLLMSStatus class extends ComponentSetupStatus and is used to track
 * the setup status of language learning models (LLMs) within a component.
 *
 * Gebo.ai Commentor AI generated comments
 */
public class ComponentLLMSStatus extends ai.gebo.architecture.setup.model.ComponentSetupStatus {

    // Boolean flag indicating whether a default chat model (uses CHAT) is configured
    public boolean chatModelSetup;

    // Boolean flag indicating whether a chat model for internal services (uses
    // INTERNAL_SERVICES) is configured
    public boolean internalServicesChatModelSetup;

    // Boolean flag indicating whether a default embedding model is configured
    public boolean embeddedModelSetup;

    public boolean rankingModelSetup;

    public boolean imagesModelSetup;

    public boolean ttsModelSetup;

    public boolean transcriptModelSetup;

    // Model code of the current default (or, for the chat service slot, the
    // internal-services model) of each kind. Null when none is configured. Used by
    // the expert (Advanced) wizard tab to show what a new model would override.
    public String chatModelCode;

    public String internalServicesChatModelCode;

    public String embeddedModelCode;

    public String rankingModelCode;

    public String imagesModelCode;

    public String ttsModelCode;

    public String transcriptModelCode;

    // Provider (model type handler code) that owns each current default, shown next
    // to the model code in the expert (Advanced) wizard tab.
    public String chatModelProviderId;

    public String internalServicesChatModelProviderId;

    public String embeddedModelProviderId;

    public String rankingModelProviderId;

    public String imagesModelProviderId;

    public String ttsModelProviderId;

    public String transcriptModelProviderId;
}