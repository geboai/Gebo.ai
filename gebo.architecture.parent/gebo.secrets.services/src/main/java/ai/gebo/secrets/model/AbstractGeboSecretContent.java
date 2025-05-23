/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

/**
 * Gebo.ai comment agent
 *
 * This abstract class serves as a base for all secret content types within the Gebo.ai application.
 * It mandates that any subclass must implement the method to return the type of secret.
 */
public abstract class AbstractGeboSecretContent {

    /**
     * This method must be implemented by any concrete subclass to return the specific type of secret.
     *
     * @return a GeboSecretType representing the type of secret content.
     */
    public abstract GeboSecretType type();
}