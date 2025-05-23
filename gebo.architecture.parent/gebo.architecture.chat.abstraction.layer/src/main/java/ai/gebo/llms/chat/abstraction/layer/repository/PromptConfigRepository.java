/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.repository;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;

/**
 * Gebo.ai comment agent
 * 
 * Repository interface for handling operations related to {@link GPromptConfig}.
 * Extends the {@link IGBaseMongoDBRepository} to provide basic MongoDB 
 * repository functionalities for GPromptConfig entities.
 */
public interface PromptConfigRepository extends IGBaseMongoDBRepository<GPromptConfig> {

    /**
     * Returns the class type of the entity managed by this repository.
     * This method overrides the default implementation in IGBaseMongoDBRepository.
     *
     * @return the class type of GPromptConfig.
     */
    @Override
    default Class<GPromptConfig> getManagedType() {
        return GPromptConfig.class;
    }

    /**
     * Finds a list of {@link GPromptConfig} by model configuration reference code 
     * and class name.
     *
     * @param code the model configuration reference code.
     * @param className the model configuration reference class name.
     * @return a list of GPromptConfig matching the specified criteria.
     */
    List<GPromptConfig> findByModelConfigurationReferenceCodeAndModelConfigurationReferenceClassName(String code,
                                                                                                     String className);

    /**
     * Finds a list of {@link GPromptConfig} by default prompt status.
     *
     * @param defaultPrompt the default prompt status indicator.
     * @return a list of GPromptConfig that have the specified default prompt status.
     */
    List<GPromptConfig> findByDefaultPrompt(Boolean defaultPrompt);

    /**
     * Finds a list of {@link GPromptConfig} by RAG (retrieval-augmented generation) 
     * prompt status.
     *
     * @param ragPrompt the RAG prompt status indicator.
     * @return a list of GPromptConfig that have the specified RAG prompt status.
     */
    List<GPromptConfig> findByRagPrompt(Boolean ragPrompt);

    /**
     * Finds a list of {@link GPromptConfig} by both RAG prompt and default prompt status.
     *
     * @param ragPrompt the RAG prompt status indicator.
     * @param defaultPrompt the default prompt status indicator.
     * @return a list of GPromptConfig that match the specified RAG prompt and default prompt statuses.
     */
    List<GPromptConfig> findByRagPromptAndDefaultPrompt(Boolean ragPrompt, Boolean defaultPrompt);
}