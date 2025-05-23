/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandler;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * AI generated comments
 * A service implementation for handling code generation for GProjectEndpoint entities.
 * This class is annotated with @Service to indicate that it's a service layer component.
 */
@Service
public class GProjectEndpointCodeGeneratorEntityHandler implements IGCodeGeneratorEntityHandler<GProjectEndpoint> {

    /**
     * Returns the class type that this handler is applicable to.
     * 
     * @return the class type GProjectEndpoint
     */
    @Override
    public Class<GProjectEndpoint> getAffectedClass() {
        return GProjectEndpoint.class;
    }

    /**
     * Indicates whether this handler should also apply to classes that extend the affected class.
     * 
     * @return true, indicating applicability to extending classes
     */
    @Override
    public boolean isAppliedToExtendingClass() {
        return true;
    }

    /**
     * Generates a code for a GProjectEndpoint entity.
     * The generated code is a combination of the parent project's code and the pregenerated code.
     * 
     * @param entity the GProjectEndpoint entity for which the code is generated
     * @param pregeneratedCode a pre-existing code segment to be appended
     * @return the generated code as a String
     */
    @Override
    public String generateCode(GProjectEndpoint entity, String pregeneratedCode) {
        String parentProject = entity.getParentProjectCode();
        // Concatenates parent project code with pregenerated code if it exists
        return (parentProject != null ? parentProject + "-" : "") + pregeneratedCode;
    }

}