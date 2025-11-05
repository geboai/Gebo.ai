/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments
 * Represents the setup data required for a fast installation process,
 * including user credentials and licence agreement acceptance.
 */
@Data
public class FastInstallationSetupData {

    // Username for the setup process, must not be null
    @NotNull
    private String username = null;

    // Password for the setup process, must not be null
    @NotNull
    private String password = null;

    // Confirmation password, can be null
    private String passwordC = null;

    // Licence agreement acceptance, must not be null
    @NotNull
    private String licenceAgreement = null;
    
    @NotNull
    private String lang=null;

  
}