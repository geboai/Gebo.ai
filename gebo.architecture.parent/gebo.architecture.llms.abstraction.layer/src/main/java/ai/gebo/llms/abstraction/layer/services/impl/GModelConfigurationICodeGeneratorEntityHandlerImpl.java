/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandler;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;

/**
 * Gebo.ai comment agent
 *
 * Implementation of the IGCodeGeneratorEntityHandler interface for handling
 * GBaseModelConfig entities in the context of code generation.
 */
@Service
public class GModelConfigurationICodeGeneratorEntityHandlerImpl
        implements IGCodeGeneratorEntityHandler<GBaseModelConfig> {

    /**
     * Returns the class type that this handler is responsible for.
     *
     * @return the class type of GBaseModelConfig.
     */
    @Override
    public Class<GBaseModelConfig> getAffectedClass() {
        return GBaseModelConfig.class;
    }

    /**
     * Specifies if this handler should also apply to classes extending the base
     * class.
     *
     * @return true if applicable to extending classes, false otherwise.
     */
    @Override
    public boolean isAppliedToExtendingClass() {
        return true; 
    }

    /**
     * Generates a unique code for the given GBaseModelConfig entity by
     * combining a prefix derived from the entity's model type code with a
     * pre-generated code.
     *
     * @param entity the GBaseModelConfig entity for which the code is
     *               generated.
     * @param pregeneratedCode the pre-generated code to be combined with the
     *                         prefix.
     * @return a string representing the generated code.
     */
    @Override
    public String generateCode(GBaseModelConfig entity, String pregeneratedCode) {
        String prefix = entity.getModelTypeCode();

        // Combine the model type code prefix with the pre-generated code
        return (prefix != null ? prefix + "-" : "") + pregeneratedCode;
    }

}