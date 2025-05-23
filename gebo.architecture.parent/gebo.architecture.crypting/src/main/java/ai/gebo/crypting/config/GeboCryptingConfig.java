/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.crypting.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Gebo Crypting.
 * 
 * <p>This class is responsible for loading cryptographic configuration 
 * properties prefixed with "ai.gebo.crypting". It holds configurations 
 * related to the keystore used for cryptographic operations.</p>
 * 
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.crypting")
public class GeboCryptingConfig {
    // Configuration for the keystore, initially set to null.
    KeyStoreConfig keystoreConfig = null;

    /**
     * Default constructor for GeboCryptingConfig.
     */
    public GeboCryptingConfig() {

    }

    /**
     * Retrieves the current KeyStore configuration.
     * 
     * @return the current KeyStoreConfig object.
     */
    public KeyStoreConfig getKeystoreConfig() {
        return keystoreConfig;
    }

    /**
     * Sets a new KeyStore configuration.
     * 
     * @param keystoreConfig the KeyStoreConfig object to set.
     */
    public void setKeystoreConfig(KeyStoreConfig keystoreConfig) {
        this.keystoreConfig = keystoreConfig;
    }

}